--Добавить тестовых сотрудников
INSERT INTO EMPLOYEE
(NAME, PASSWORD, TITLE)
VALUES
('Emoloyee1', '123', 'WORKER'),
('Emoloyee2', '123', 'WORKER'),
('Emoloyee3', '123', 'MANAGER');

--Добавить статусы
INSERT INTO STATUSES
(ID, NAME)
VALUES
(1, 'CREATED'),
(2, 'ON_REVIEW'),
(3, 'ACCEPTED'),
(4, 'SUSPEND'),
(5, 'DECLINE');
