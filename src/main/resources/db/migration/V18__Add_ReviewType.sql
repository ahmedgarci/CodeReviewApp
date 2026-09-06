alter table submission
add column review_type varchar(40) default 'HUMAN' not null
constraint review_type_check check (review_type in ('HUMAN', 'SONARQUBE'));