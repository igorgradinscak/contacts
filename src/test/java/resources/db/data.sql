insert into public.contact (id, username, first_name, last_name, email,created, updated, deleted)
values (1, 'Igradinscak','Igor', 'Grad', 'igor@example.com', '2020-01-01 00:00:00.000', '2021-01-01 00:00:00.000', null),
       (2, 'usertest','Test', 'User', 'test@example.com', '2021-01-01 00:00:00.000', '2022-01-01 00:00:00.000', null),
       (3, 'JonDoe','Jon', 'Doe', 'jon.doe@example.com', '2021-01-01 00:00:00.000', '2022-01-01 00:00:00.000', '2023-01-01 00:00:00.000');

insert into public.uploaded_file (id, call_number, telephone_number, contact_id, created, updated, deleted)
values (1, '+385', '11-111-1111', 1, '2020-01-01 00:00:00.000', '2021-01-01 00:00:00.000', null),
       (2, '+385', '11-222-1111', 2, '2020-01-01 00:00:00.000', '2021-01-01 00:00:00.000', null),
       (3, '+385', '11-333-1111', 3 , '2021-01-01 00:00:00.000', '2022-01-01 00:00:00.000', '2023-01-01 00:00:00.000'),
       (4, '+385', '11-222-2222', 2, '2020-01-01 00:00:00.000', '2021-01-01 00:00:00.000', null);
