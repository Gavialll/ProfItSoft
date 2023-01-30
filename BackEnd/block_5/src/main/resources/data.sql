insert into profession (id, name) values
    (1, 'Doctor'),
    (2, 'Law'),
    (3, 'Driver');

insert into person (id, name, age, profession_id)
values
    (default, 'Ivan', 25, 1),
    (default, 'Taras', 35, 2),
    (default, 'Roman', 48, 3),
    (default, 'Semen', 20, 1),
    (default, 'Ivan', 19, 1);