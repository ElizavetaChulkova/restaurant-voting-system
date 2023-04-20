Получить всех зарегестрированных юзеров

GET http://localhost:8080/api/admin/users
Authorization: Basic admin@javaops.ru admin

Response: 200

[
{
"id": 1,
"name": "User_First",
"email": "user@gmail.com"
},
{
"id": 2,
"name": "Admin_First",
"email": "admin@javaops.ru"
}
]

###

Получить юзера по айди

GET http://localhost:8080/api/admin/users/1
Authorization: Basic admin@javaops.ru admin

Response: 200

{
"id": 1,
"name": "User_First",
"email": "user@gmail.com"
}

###

Удалить юзера

DELETE http://localhost:8080/api/admin/users/1
Authorization: Basic admin@javaops.ru admin

Response:

<Response body is empty>

Response code: 204;

###

Создать юзера

POST http://localhost:8080/api/admin/users
Authorization: Basic admin@javaops.ru admin
Content-Type: application/json

{
"email": "email@email.ru",
"name": "Test",
"password": "test"
}

Response: 201

{
"id": 3,
"name": "Test",
"email": "email@email.ru"
}

###

Тестируем создание юзера

GET http://localhost:8080/api/admin/users
Authorization: Basic admin@javaops.ru admin

Response: 200

[
{
"id": 1,
"name": "User_First",
"email": "user@gmail.com"
},
{
"id": 2,
"name": "Admin_First",
"email": "admin@javaops.ru"
},
{
"id": 3,
"name": "Test",
"email": "email@email.ru"
}
]

###

Редактируем юзера

PUT http://localhost:8080/api/admin/users/1
Authorization: Basic admin@javaops.ru admin
Content-Type: application/json

{
"email": "email@email.ru",
"name": "Test update",
"password": "test"
}

Response:

<Response body is empty>
Response code: 204;

###

Тестируем редактирование

GET http://localhost:8080/api/admin/users/1
Authorization: Basic admin@javaops.ru admin

Response: 200

{
"id": 1,
"name": "Test update",
"email": "email@email.ru"
}

