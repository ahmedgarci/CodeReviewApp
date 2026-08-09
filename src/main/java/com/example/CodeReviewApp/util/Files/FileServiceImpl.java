package com.example.CodeReviewApp.util.Files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.CodeReviewApp.Models.CodeFile;
import com.example.CodeReviewApp.exceptions.FileStorageException;
import com.example.CodeReviewApp.mapper.CodeFileFactory;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl  implements FileService{

    private final static String BASE_DIR="submissions/temp";

    private final static String UPLOAD_DIR="submissions/upload";


    private final CodeFileFactory codeFileFactory;

    @Override
    public List<CodeFile> uploadFiles(Long submissionId, List<MultipartFile> files) {

        Path submissionFolder = Paths.get(BASE_DIR, submissionId.toString());

        List<Path> savedFiles = new ArrayList<>();

        List<CodeFile> codeFiles = new ArrayList<>();

        try {

            Files.createDirectories(submissionFolder);
            
            for (MultipartFile file : files) {

                String pathToSave = file.getOriginalFilename();             

                final String extension = getFileExtension(pathToSave);

                Path filePath = submissionFolder.resolve(pathToSave);

                Files.copy(file.getInputStream(), filePath);

                CodeFile codeFile = codeFileFactory.toCodeFile(file, Paths.get(submissionId.toString(), pathToSave).toString(),extension);

                codeFile.setSubmission_id(submissionId);

                codeFiles.add(codeFile);

                savedFiles.add(filePath);

            }

            return codeFiles;

        } catch (Exception e) {

            deleteSubmissionFiles(submissionId);
            
            try {

                Files.deleteIfExists(submissionFolder);

            } catch (IOException ignored) {}

            throw new FileStorageException("Failed to store uploaded files.",e);
        
        }
    }
            
            
    @Override
    public byte[] readFile(String filePath) {

        Path path = Paths.get(UPLOAD_DIR, filePath);

        if(!Files.exists(path)){

            throw new FileStorageException("File does not exist on server");

        }

        try {

            return Files.readAllBytes(path);

        } catch (Exception e) {

            throw new RuntimeException("Failed to read file",e);

        }

    }

    
    public void deleteSubmissionFiles(Long submissionId) {

        Path folder = Paths.get(BASE_DIR, submissionId.toString());

        if(!Files.exists(folder)) return;

        try {
            
            Files.walk(folder).sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    
                    Files.delete(path);
                }
                 catch(IOException e){}
            
                });

        } catch (Exception e) {
            throw new RuntimeException(
                "Cannot delete files",
                e
            );        
        }

    }


    @Override
    public void moveFilesToPermanentStorage(Long submissionId) {

        Path temp = Paths.get(BASE_DIR, submissionId.toString()); 

        Path uploadDir = Paths.get(UPLOAD_DIR,submissionId.toString());

        try {
            
            Files.createDirectories(uploadDir);

            Files.walk(temp).filter(Files::isRegularFile).forEach(source ->{
                try{
                    
                    Path destination = uploadDir.resolve(source.getFileName());
                    
                    Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
                
                }catch(Exception e){
                    throw new RuntimeException(e);

                }
    
            });
            Files.deleteIfExists(temp);

        } catch (Exception e) {
        
            throw new FileStorageException("Failed to move files",e);        
        
        }

    }

    public String resolveSubmissionUploadDir(@NotNull Long submissionId){

        return Paths.get(UPLOAD_DIR, submissionId.toString()).toString();

    }
    
    private String getFileExtension(String fileName){
        int index = fileName.lastIndexOf('.');
        if(index == -1 ) throw new IllegalArgumentException("no file extension");

        return fileName.substring(index+1);

    }
}
