Получить ресторан по айди

GET http://localhost:8080/api/admin/restaurants/1
Authorization: Basic admin@javaops.ru admin

Response: 200

{
"name": "BURO"
}

###

Получить все рестораны

GET http://localhost:8080/api/admin/restaurants
Authorization: Basic admin@javaops.ru admin

Response: 200

[
{
"name": "BURO"
},
{
"name": "Dante"
},
{
"name": "Lets carbonara"
}
]

###

Удаление ресторана

DELETE http://localhost:8080/api/admin/restaurants/1
Authorization: Basic admin@javaops.ru admin

Response:

<Response body is empty>

Response code: 204;

###

Тестируем удаление ресторана

GET http://localhost:8080/api/admin/restaurants
Authorization: Basic admin@javaops.ru admin

Response: 200

[
{
"name": "Dante"
},
{
"name": "Lets carbonara"
}
]

###

Создание ресторана

POST http://localhost:8080/api/admin/restaurants
Content-Type: application/json
Authorization: Basic admin@javaops.ru admin

{
"name": "Testing create"
}

Response: 201

{
"id": 4,
"name": "Testing create",
"menu": null
}

###

Тестирование создания ресторана

GET http://localhost:8080/api/admin/restaurants
Authorization: Basic admin@javaops.ru admin

Response:

[
{
"name": "BURO"
},
{
"name": "Dante"
},
{
"name": "Lets carbonara"
},
{
"name": "Testing create"
}
]

###

Редактирование ресторана

PUT http://localhost:8080/api/admin/restaurants/1
Content-Type: application/json
Authorization: Basic admin@javaops.ru admin

{
"id": 1,
"name": "Testing testing"
}

Response:

<Response body is empty>

Response code: 204;

###

Тестирование редактирования ресторана

GET http://localhost:8080/api/admin/restaurants
Authorization: Basic admin@javaops.ru admin

[
{
"name": "Dante"
},
{
"name": "Lets carbonara"
},
{
"name": "Testing testing"
}
]