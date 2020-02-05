CREATE TABLE IF NOT EXISTS users (
                                     id       INTEGER auto_increment primary key,
                                     user_name     VARCHAR(100),
                                     user_surname  VARCHAR(100),
                                     language_level      VARCHAR (20),
                                     email    VARCHAR(100),
                                     birthday DATE,
                                     sex      VARCHAR (10),
                                     photo_link    VARCHAR(100),
                                     status   VARCHAR(10)
);

INSERT INTO users(ID, USER_NAME, USER_SURNAME, LANGUAGE_LEVEL, EMAIL, SEX, STATUS)
values(0, 'User', 'UserSurname', 'BEGINNER', 'user@mail.com', 'MALE', 'CONFIRMED');

INSERT INTO users(ID, USER_NAME, USER_SURNAME, LANGUAGE_LEVEL, EMAIL, SEX, STATUS)
values(1, 'Admin', 'AdminSurname', 'BEGINNER', 'admin@mail.com', 'MALE', 'CONFIRMED');

INSERT INTO users(ID, USER_NAME, USER_SURNAME, LANGUAGE_LEVEL, EMAIL, SEX, STATUS)
values(2, 'FakeUser1', 'FakeSurname', 'BEGINNER', 'fakeuser1@mail.com', 'MALE', 'CONFIRMED');

INSERT INTO users(ID, USER_NAME, USER_SURNAME, LANGUAGE_LEVEL, EMAIL, SEX, STATUS)
values(3, 'FakeUser2', 'FakeSurname', 'BEGINNER', 'fakeuser2@mail.com', 'MALE', 'CONFIRMED');

INSERT INTO users(ID, USER_NAME, USER_SURNAME, LANGUAGE_LEVEL, EMAIL, SEX, STATUS)
values(4, 'FakeUser3', 'FakeSurname', 'BEGINNER', 'fakeuser3@mail.com', 'MALE', 'CONFIRMED');

INSERT INTO users(ID, USER_NAME, USER_SURNAME, LANGUAGE_LEVEL, EMAIL, SEX, STATUS)
values(5, 'FakeUser4', 'FakeSurname', 'BEGINNER', 'fakeuser4@mail.com', 'MALE', 'CONFIRMED');