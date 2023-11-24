create table public.phone_number
(
    id                  serial                      not null    constraint phone_number_pk primary key,
    call_number         varchar(64)                 not null,
    telephone_number    varchar(256)                not null,
    deleted             timestamp with time zone
);

create table public.contact
(
    id                  serial                      not null    constraint contact_pk primary key,
    username            varchar(256)                not null,
    first_name          varchar(256)                not null,
    last_name           varchar(256)                not null,
    email               varchar(256)                not null,
    phone_number_ids    integer[]                   not null   constraint contact_phone_number_ids_fk references public.phone_number(id),
    created             timestamp with time zone    not null,
    updated             timestamp with time zone    not null,
    deleted             timestamp with time zone
);