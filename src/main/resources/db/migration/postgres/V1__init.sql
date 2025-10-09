
create type user_role_enum as enum (
    'ADMIN',
    'USER'
);

create type user_status_enum as enum (
    'ACTIVE',
    'PENDING_VERIFICATION',
    'BANNED'
);

create table if not exists user_account (
    id uuid primary key,
    username varchar(32) not null unique,
    email varchar(256) not null unique,
    password varchar(128) not null,
    user_role user_role_enum not null,
    user_status user_status_enum not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table if not exists email_verify_token (
    id uuid primary key,
    user_account_id uuid not null,
    token varchar(128) not null unique,
    expires_at timestamp not null,
    used boolean not null default false,
    created_at timestamp not null,
    foreign key (user_account_id) references user_account(id) on delete cascade
);


create table if not exists password_reset (
    id uuid primary key,
    user_account_id uuid not null,
    token varchar(128),
    expires_at timestamp,
    used boolean,
    created_at timestamp not null,
    foreign key (user_account_id) references user_account(id) on delete cascade
);