DROP TABLE IF EXISTS follow;
DROP TABLE IF EXISTS team_user;
DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS question_like;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS bundle;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    user_id            BIGINT       NOT NULL AUTO_INCREMENT,
    username           VARCHAR(20)  NOT NULL UNIQUE,
    user_password      VARCHAR(255) NOT NULL,
    user_role          VARCHAR(10)  NOT NULL,
    user_real_name     VARCHAR(20)  NOT NULL,
    user_created_date  DATE         NOT NULL,
    user_profile_image VARCHAR(255) NULL,
    about_me           VARCHAR(255) NULL,
    interests          VARCHAR(255) NULL,
    career             VARCHAR(255) NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE follow
(
    follow_id   BIGINT NOT NULL AUTO_INCREMENT,
    create_date DATE   NOT NULL,
    user_from   BIGINT NOT NULL,
    user_to     BIGINT NOT NULL,
    PRIMARY KEY (follow_id)
);


create table tag
(
    tag_id      BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    create_date DATE,
    color       VARCHAR(255) NOT NULL,
    image       VARCHAR(255) NULL,
    primary key (tag_id)
);

CREATE TABLE team
(
    team_id     BIGINT       NOT NULL AUTO_INCREMENT,
    team_name   VARCHAR(20)  NOT NULL,
    description VARCHAR(255) NULL,
    user_id     BIGINT       NOT NULL,
    PRIMARY KEY (team_id)
);

CREATE TABLE team_user
(
    team_user_id BIGINT NOT NULL AUTO_INCREMENT,
    team_id      BIGINT NOT NULL,
    user_id      BIGINT NOT NULL,
    PRIMARY KEY (team_user_id)
);

CREATE TABLE bundle
(
    bundle_id    BIGINT       NOT NULL AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    access_level VARCHAR(255) NOT NULL,
    create_date  DATE         NOT NULL,
    user_id      BIGINT       NOT NULL,
    tag_id       BIGINT       NOT NULL,
    team_id      BIGINT       NULL,
    PRIMARY KEY (bundle_id)
);

create table question
(
    question_id      BIGINT  NOT NULL AUTO_INCREMENT,
    title            VARCHAR(255),
    content          TEXT,
    view             INTEGER NOT NULL,
    create_time      DATETIME,
    last_modify_time DATETIME,
    status           VARCHAR(255),
    user_id          BIGINT,
    bundle_id        BIGINT,
    tag_id           BIGINT,
    PRIMARY KEY (question_id)
);

create table question_like
(
    question_like_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id          BIGINT NOT NULL,
    question_id      BIGINT NOT NULL,
    PRIMARY KEY (question_like_id)
);

create table answer
(
    answer_id        BIGINT  NOT NULL AUTO_INCREMENT,
    content          VARCHAR(512),
    create_time      DATETIME,
    last_modify_time DATETIME,
    adopted          BOOLEAN NOT NULL,
    user_id          BIGINT,
    question_id      BIGINT,
    PRIMARY KEY (answer_id)
);

ALTER TABLE follow
    ADD CONSTRAINT FK_user_from
        FOREIGN KEY (user_from)
            REFERENCES user (user_id)
            ON DELETE CASCADE,
    ADD CONSTRAINT FK_user_to
        FOREIGN KEY (user_to)
            REFERENCES user (user_id)
            ON DELETE CASCADE;

ALTER TABLE team
    ADD CONSTRAINT FK_team_admin
        FOREIGN KEY (user_id)
            REFERENCES user (user_id)
            ON DELETE CASCADE;

ALTER TABLE team_user
    ADD CONSTRAINT FK_team_user_team
        FOREIGN KEY (team_id)
            REFERENCES team (team_id)
            ON DELETE CASCADE,
    ADD CONSTRAINT FK_team_user_user
        FOREIGN KEY (user_id)
            REFERENCES user (user_id)
            ON DELETE CASCADE;

ALTER TABLE bundle
    ADD CONSTRAINT FK_bundle_user
        FOREIGN KEY (user_id)
            REFERENCES user (user_id),
    ADD CONSTRAINT FK_bundle_team
        FOREIGN KEY (team_id)
            REFERENCES team (team_id),
    ADD CONSTRAINT FK_bundle_tag
        FOREIGN KEY (tag_id)
            REFERENCES tag (tag_id);

ALTER TABLE answer
    ADD CONSTRAINT FK_answer_user
        FOREIGN KEY (user_id)
            REFERENCES user (user_id),
    ADD CONSTRAINT FK_answer_question
        FOREIGN KEY (question_id)
            REFERENCES question (question_id);

ALTER TABLE question
    ADD CONSTRAINT FK_user_question
        FOREIGN KEY (user_id)
            REFERENCES user (user_id),
    ADD CONSTRAINT FK_bundle_question
        FOREIGN KEY (bundle_id)
            REFERENCES bundle (bundle_id),
    ADD CONSTRAINT FK_tag_question
        FOREIGN KEY (tag_id)
            REFERENCES tag (tag_id);

ALTER TABLE question_like
    ADD CONSTRAINT FK_user_questionLike
        FOREIGN KEY (user_id)
            REFERENCES user (user_id),
    ADD CONSTRAINT FK_question_questionLike
        FOREIGN KEY (question_id)
            REFERENCES question (question_id);