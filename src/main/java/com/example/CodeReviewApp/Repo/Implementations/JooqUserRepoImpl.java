package com.example.CodeReviewApp.Repo.Implementations;

import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.CodeReviewApp.Models.User;
import com.example.CodeReviewApp.Repo.UserRepository;

import lombok.RequiredArgsConstructor;

import static com.example.jooq.Tables.USERS;;

@RequiredArgsConstructor
@Repository
public class JooqUserRepoImpl implements UserRepository {


    private final DSLContext dsl;


    @Override
    public Optional<User> findByEmail(String email) {


        User user = dsl.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOneInto(User.class);
        return Optional.ofNullable(user);
    }


    @Override
    public void insertUser(User user) {

        dsl.insertInto(USERS).columns(USERS.USERNAME,USERS.EMAIL,USERS.PASSWORD).values(user.getUsername(),user.getEmail(),user.getPassword()).execute();

    }


    @Override
    public boolean checkExistsByEmail(String email) {

        boolean exists = dsl.fetchExists(USERS,USERS.EMAIL.eq(email));

        return exists;
    
    }

     public boolean usersExist(List<Long> userIds) {

        if (userIds == null || userIds.isEmpty()) {
            return false;
        }

        int count = dsl.selectCount()
                .from(USERS)
                .where(USERS.ID.in(userIds))
                .fetchOne(0, int.class);


        return count == userIds.size();
    }


     @Override
     public String getUserEmailById(Long id) {

        return dsl.select(USERS.EMAIL).from(USERS).where(USERS.ID.eq(id)).fetchOneInto(String.class);

    }

}