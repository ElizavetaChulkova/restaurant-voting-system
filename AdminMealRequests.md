Получение всей еды

GET http://localhost:8080/api/admin/restaurants/meals
Authorization: Basic admin@javaops.ru admin

Response: 200

[
{
"price": 500,
"dishName": "meat balls"
},
{
"price": 800,
"dishName": "shrimps carry"
},
{
"price": 650,
"dishName": "carbonara"
},
{
"price": 500,
"dishName": "grilled bri with mango sauce"
},
{
"price": 600,
"dishName": "best cocktail"
}
]

###

Удаление еды по айди

DELETE http://localhost:8080/api/admin/restaurants/meals/1
Authorization: Basic admin@javaops.ru admin

Response:
<Response body is empty>

Response code: 204;

###

Получение всей еды конкретного ресторана

GET http://localhost:8080/api/admin/restaurants/1/meals
Authorization: Basic admin@javaops.ru admin

Response: 200

[
{
"price": 500,
"dishName": "meat balls"
},
{
"price": 800,
"dishName": "shrimps carry"
}
]

###

Создание новой еды в ресторане

POST http://localhost:8080/api/admin/restaurants/1/new-meal
Authorization: Basic admin@javaops.ru admin
Content-Type: application/json

{
"price": 1000,
"dishName": "new test dish",
"menuDate": "2023-04-07"
}

Response: 201

{
"price": 1000,
"dishName": "new test dish"
}

###
Тестируем создание новой еды

GET http://localhost:8080/api/admin/restaurants/1/meals
Authorization: Basic admin@javaops.ru admin

Response: 200

[
{
"price": 500,
"dishName": "meat balls"
},
{
"price": 800,
"dishName": "shrimps carry"
},
{
"price": 1000,
"dishName": "new test dish"
}
]

###

Редактирование еды 

PUT http://localhost:8080/api/admin/restaurants/1/meals/1
Content-Type: application/json
Authorization: Basic admin@javaops.ru admin

{
"price": 777,
"dishName": "testing dish",
"menuDate": "2023-04-08"
}

Response:

<Response body is empty>
Response code: 204;

###
