CREATE TABLE pf_user
(
    id               bigserial
        constraint pf_user_pk
            primary key,
    name             varchar(100),
    description      varchar(100)                          not null,
    username         varchar(255)                          not null
        constraint pf_user_un
            unique,
    password         varchar(255)                          not null,
    email            varchar(255),
    cellphone        varchar(20),
    company_id       varchar(100),
    status_id        varchar(20)                           not null,
    -- 標準審計欄位 (Spring Data JPA Auditing)
    created_by       bigint                                not null,
    created_time     timestamp   default now()             not null,

    modified_by      bigint                                not null,
    modified_time    timestamp   default now()             not null,

    -- 自定義審計欄位
    created_company  varchar(100),
    modified_company varchar(100),
    created_unit     varchar(100),
    modified_unit    varchar(100),
    default_language varchar(20) default ''::character varying
);

CREATE TABLE customer
(
    id               bigserial
        constraint customer_pk
            primary key,
    customer_name    varchar(100)                          not null,
    job_title        varchar(100),
    -- 標準審計欄位 (Spring Data JPA Auditing)
    created_by       bigint                                not null,
    created_time     timestamp   default now()             not null,

    modified_by      bigint                                not null,
    modified_time    timestamp   default now()             not null,

    -- 自定義審計欄位
    created_company  varchar(100),
    modified_company varchar(100),
    created_unit     varchar(100),
    modified_unit    varchar(100),
    default_language varchar(20) default ''::character varying
);

-- 插入測試用戶
INSERT INTO pf_user (id, username, password, description, status_id, created_by, modified_by, created_company, created_unit) 
VALUES 
(1, 'user1', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'Test User 1', 'ACTIVE', 1, 1, 'Company A', 'Unit A'),
(2, 'user2', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'Test User 2', 'ACTIVE', 1, 1, 'Company B', 'Unit B');

-- 設置序列值
SELECT setval('pf_user_id_seq', 2, true);