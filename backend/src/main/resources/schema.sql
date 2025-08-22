CREATE TABLE IF NOT EXISTS user
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT uq_user_email UNIQUE (email)
    );

CREATE TABLE IF NOT EXISTS category
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT       NOT NULL,
    name    VARCHAR(255) NOT NULL,
    CONSTRAINT fk_category_user_id FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS task
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT                            NOT NULL,
    category_id     BIGINT                            NOT NULL,
    title           VARCHAR(255)                      NOT NULL,
    content         VARCHAR(2550)                     NULL,
    status          ENUM ('DONE', 'NONE', 'PROGRESS') NOT NULL,
    permission      ENUM ('EDIT', 'VIEW')             NULL,
    shared          BIT                               NULL,
    shared_link     VARCHAR(255)                      NULL,
    start_date      DATE                              NOT NULL,
    end_date        DATE                              NOT NULL,
    expiration_date DATE                              NULL,

    CONSTRAINT fk_task_user_id
    FOREIGN KEY (user_id) REFERENCES user (id)
    ON DELETE CASCADE,

    CONSTRAINT fk_task_category_id
    FOREIGN KEY (category_id) REFERENCES category (id)
    ON DELETE CASCADE
    );
