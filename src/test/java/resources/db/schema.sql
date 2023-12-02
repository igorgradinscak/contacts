-- Sequences
create sequence if not exists public.contact_id_seq start with 4 increment by 1;
create sequence if not exists public.phone_number_id_seq start with 5 increment by 1;
-- Tables
create table if not exists public.contact
(
    id                  integer auto_increment      not null    constraint contact_pk primary key,
    username            varchar(256)                not null,
    first_name          varchar(256)                not null,
    last_name           varchar(256)                not null,
    email               varchar(256)                not null,
    created             timestamp with time zone    not null,
    updated             timestamp with time zone    not null,
    deleted             timestamp with time zone
);
create table if not exists public.phone_number
(
    id                  integer auto_increment      not null    constraint phone_number_pk primary key,
    call_number         varchar(64)                 not null,
    telephone_number    varchar(256)                not null,
    contact_id          int                         not null    constraint phone_number_contact_id references public.contact,
    created             timestamp with time zone    not null,
    updated             timestamp with time zone    not null,
    deleted             timestamp with time zone
);