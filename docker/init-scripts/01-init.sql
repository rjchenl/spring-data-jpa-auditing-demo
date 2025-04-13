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