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
    created_name     varchar(100),
    created_username varchar(100),
    created_display_name varchar(100),

    modified_by      bigint                                not null,
    modified_time    timestamp   default now()             not null,
    modified_name    varchar(100),
    modified_username varchar(100),
    modified_display_name varchar(100),

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
    created_name     varchar(100),
    created_username varchar(100),
    created_display_name varchar(100),
    
    modified_by      bigint                                not null,
    modified_time    timestamp   default now()             not null,
    modified_name    varchar(100),
    modified_username varchar(100),
    modified_display_name varchar(100),
    
    -- 自定義審計欄位
    created_company  varchar(100),
    modified_company varchar(100),
    created_unit     varchar(100),
    modified_unit    varchar(100),
    default_language varchar(20) default ''::character varying
);

-- 插入測試用戶
INSERT INTO pf_user (id, username, password, description, status_id, created_by, modified_by, created_name, modified_name, created_company, modified_company, created_unit, modified_unit, default_language) 
VALUES 
(1, 'user1', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'Test User 1', 'ACTIVE', 1, 1, 'admin', 'admin', 'Company A', 'Company A', 'Unit A', 'Unit A', 'zh-TW'),
(2, 'user2', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'Test User 2', 'ACTIVE', 1, 1, 'admin', 'admin', 'Company B', 'Company B', 'Unit B', 'Unit B', 'en-US');

-- 設置序列值
SELECT setval('pf_user_id_seq', 2, true);

-- 創建僅包含創建審計欄位的簡單日誌表
CREATE TABLE simple_log
(
    id               bigserial
        constraint simple_log_pk
            primary key,
    event_type       varchar(50)                           not null,
    message          text                                  not null,
    event_time       timestamp,
    
    -- 只有創建審計欄位，沒有修改審計欄位
    created_by       bigint                                not null,
    created_time     timestamp   default now()             not null,
    created_name     varchar(100),
    created_username varchar(100),
    created_display_name varchar(100),
    created_company  varchar(100),
    created_unit     varchar(100),
    default_language varchar(20) default ''::character varying
);
