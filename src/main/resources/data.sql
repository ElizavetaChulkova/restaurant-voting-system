INSERT INTO USERS (EMAIL, NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'password'),
       ('admin@javaops.ru', 'Admin_First', 'admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (NAME)
VALUES ( 'BURO' );

INSERT INTO MEAL (DISH_NAME, MENU_DATE, PRICE, RESTAURANT_ID)
VALUES ('meat balls', CURRENT_DATE, 500, 3);

INSERT INTO VOTE (DATE_TIME, RESTAURANT_ID, USER_ID)
VALUES ( now(), 3, 1);

