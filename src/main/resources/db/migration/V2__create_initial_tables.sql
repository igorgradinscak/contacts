create table public.contact
(
    id                  serial                      not null    constraint contact_pk primary key,
    username            varchar(256)                not null,
    first_name          varchar(256)                not null,
    last_name           varchar(256)                not null,
    email               varchar(256)                not null,
    created             timestamp with time zone    not null,
    updated             timestamp with time zone    not null,
    deleted             timestamp with time zone
);

create table public.phone_number
(
    id                  serial                      not null    constraint phone_number_pk primary key,
    call_number         varchar(64)                 not null,
    telephone_number    varchar(256)                not null,
    contact_id          int                         not null,
    deleted             timestamp with time zone
);

ALTER table public.phone_number
    ADD CONSTRAINT fk_phone_number
        FOREIGN KEY (contact_id)
            REFERENCES public.contact (id);