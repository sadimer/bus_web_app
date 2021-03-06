INSERT INTO "users" (user_name, user_email, user_phone, user_passwd, is_admin, user_login) VALUES
('Филипп Фролович Куликов', 'design2@gmail.com', '+79958566336', 'newspaper', True, 'join4'),
('Князев Владлен Изотович', 'time7@gmail.com', '8 (566) 420-47-01', 'task2', False, 'doctor1'),
('Панова Лариса Вячеславовна', 'woman592@gmail.com', '+7 (153) 675-6532', 'clear9', True, 'significant8'),
('Филатов Никита Анатольевич', 'foot@gmail.com', '+7 520 215 6610', 'hotel.1', False, 'especially'),
('Олег Харитонович Лобанов', 'white997@gmail.com', '+7 (382) 857-8070', 'public4', False, 'low'),
('Романова Алла Макаровна', 'local@gmail.com', '+7 396 642 6068', 'wide6', False, 'many.'),
('Носов Вышеслав Германович', 'minute@gmail.com', '88532088712', 'visit.3', True, 'long3'),
('Изяслав Ефимьевич Голубев', 'more863@gmail.com', '8 (580) 090-4494', 'describe9', False, 'song'),
('Галина Кузьминична Фокина', 'magazine1@gmail.com', '8 (887) 503-0157', 'subject', False, 'Left'),
('Котова Евпраксия Мироновна', 'movement9@gmail.com', '8 (472) 367-03-80', 'upon7', True, 'time7');

INSERT INTO "company" (company_name) VALUES
('Шилов Инк'),
('РАО «Шубин»'),
('Орехова Инкорпорэйтед'),
('Трансойл'),
('ООО «Борисов-Маслова»'),
('Борисова Лтд'),
('Зимина Инк'),
('ЦЭНКИ'),
('Норникель'),
('Артемьев Инк');

INSERT INTO "routes" (route_name, company_id) VALUES
('9K', 1),
('180', 2),
('M2', 3),
('110M', 4),
('999', 5),
('123', 5),
('1T', 6),
('AM', 6),
('10', 6),
('9', 9);

INSERT INTO "stations" (st_name, city) VALUES
('Парк', 'Москва'),
('Вокзал', 'Москва'),
('Аэропорт', 'Москва'),
('Университет', 'Москва'),
('Ул. Первомайская', 'Москва'),
('Ул. Революционная', 'Москва'),
('Площадь Ленина', 'Пенза'),
('Парк', 'Пенза'),
('Универмаг', 'Саратов'),
('Парк', 'Саратов');

INSERT INTO "subroutes" (arrival_st_id, depart_st_id, route_id) VALUES
(1, 2, 1),
(1, 3, 2),
(1, 4, 2),
(1, 5, 2),
(1, 6, 4),
(3, 4, 2),
(3, 5, 2),
(4, 5, 2),
(7, 8, 8),
(9, 10, 7);

INSERT INTO "tickets" (user_id, sub_id, seats, price_rub) VALUES
(1, 1, 10, 100),
(1, 2, 1, 50),
(2, 2, 99, 100),
(2, 5, 5, 100),
(3, 5, 15, 50),
(4, 8, 7, 100),
(5, 8, 8, 50),
(6, 8, 9, 50),
(6, 9, 1, 50),
(6, 9, 1, 25);

INSERT INTO "stations_of_route" (route_id, st_id, arrival_time, depart_time, st_index) VALUES
(2, 1, '09.10.2021 12:00', '09.10.2021 12:10', 1),
(2, 3, '09.10.2021 12:20', '09.10.2021 12:40', 2),
(2, 4, '09.10.2021 13:00', '09.10.2021 13:10', 3),
(2, 5, '09.10.2021 13:15', '09.10.2021 13:25', 4),
(3, 1, '09.10.2021 15:00', '09.10.2021 15:10', 4),
(3, 3, '09.10.2021 14:15', '09.10.2021 14:20', 3),
(3, 4, '09.10.2021 13:45', '09.10.2021 13:55', 2),
(3, 5, '09.10.2021 13:15', '09.10.2021 13:25', 1),
(7, 9, '01.01.2022 10:00', '01.01.2022 10:20', 1),
(7, 10, '01.01.2022 10:40', '01.01.2022 11:00', 2);
