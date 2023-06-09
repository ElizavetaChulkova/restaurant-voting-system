INSERT INTO USERS (EMAIL, NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', '{noop}password'),
       ('admin@javaops.ru', 'Admin_First', '{noop}admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (NAME, MENU_DATE)
VALUES ( 'BURO', CURRENT_DATE),
       ('Lets carbonara', CURRENT_DATE),
       ('Dante', CURRENT_DATE);

INSERT INTO MEAL (DISH_NAME, DATE, PRICE, RESTAURANT_ID)
VALUES ('meat balls', CURRENT_DATE, 500, 1),
       ('shrimps carry', '2023-04-18', 800, 1),
       ('carbonara', CURRENT_DATE, 650, 2),
       ('grilled bri with mango sauce', '2023-04-18', 500, 2),
       ('best cocktail', CURRENT_DATE, 600, 3);

INSERT INTO VOTE (VOTE_DATE, VOTE_TIME, RESTAURANT_ID, USER_ID)
VALUES ( '2023-02-10', CURRENT_TIME(), 1, 1),
       ( '2023-02-10', CURRENT_TIME(), 3, 2),
       (CURRENT_DATE, CURRENT_TIME(), 1, 2);

