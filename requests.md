**AccountController**

GET http://localhost:8080/api/account
Authorization: Basic user@gmail.com password

{
"id": 1,
"email": "user@gmail.com",
"name": "User_First",
"roles": [
"USER"
]
}

###

PUT http://localhost:8080/api/account
Content-Type: application/json
Authorization: Basic user@gmail.com password

{
"email": "user@gmail.com",
"firstName": "New_First",
"lastName": "New_Last"
}

###

**RootController**

POST http://localhost:8080/api/root/register
Content-Type: application/json

{
"email": "test@test.com",
"name": "Test",
"password": "test"
}

{
"id": 11,
"email": "test@test.com",
"name": "Test",
"roles": [
"USER"
]
}
200
###

GET http://localhost:8080/api/root/restaurants
Content-Type: application/json

[
{
"id": 1,
"name": "BURO",
"menu": [
{
"id": 1,
"price": 500,
"dishName": "meat balls",
"menuDate": "2023-04-07"
},
{
"id": 2,
"price": 800,
"dishName": "shrimps carry",
"menuDate": "2023-04-07"
}
]
},
{
"id": 3,
"name": "Dante",
"menu": [
{
"id": 5,
"price": 600,
"dishName": "best cocktail",
"menuDate": "2023-04-07"
}
]
},
{
"id": 2,
"name": "Lets carbonara",
"menu": [
{
"id": 3,
"price": 650,
"dishName": "carbonara",
"menuDate": "2023-04-07"
},
{
"id": 4,
"price": 500,
"dishName": "grilled bri with mango sauce",
"menuDate": "2023-04-07"
}
]
}
]

**VoteController**

POST http://localhost:8080/api/account/vote/3
Authorization: Basic test@test.com test

{
"restaurantName": "Dante",
"voteDateTime": "2023-04-07T17:56:17.2746249"
}
200
###
Change vote at 18:00

POST http://localhost:8080/api/account/vote/3
Authorization: Basic test@test.com test

{
"restaurantName": "Dante",
"voteDateTime": "2023-04-07T18:15:05.4878309"
}

403 Forbidden

###

GET http://localhost:8080/api/account/vote/all-votes
Content-Type: application/json
Authorization: Basic user@gmail.com password

[
{
"restaurantName": "BURO",
"voteDateTime": "2023-04-07T18:19:00"
}
]
200

###

**AdminMealController**

GET http://localhost:8080/api/admin/restaurants/1/meals
Authorization: Basic admin@javaops.ru admin

[
{
"id": 1,
"price": 500,
"dishName": "meat balls",
"menuDate": "2023-04-07"
},
{
"id": 2,
"price": 800,
"dishName": "shrimps carry",
"menuDate": "2023-04-07"
}
]
200

###

DELETE http://localhost:8080/api/admin/restaurants/1/meals/2
Authorization: Basic admin@javaops.ru admin

<Response body is empty>

Response code: 204;

###

POST http://localhost:8080/api/admin/restaurants/1/new-meal
Authorization: Basic admin@javaops.ru admin
Content-Type: application/json

{
"price": 1000,
"dishName": "new test dish",
"menuDate": "2023-04-07"
}

{
"id": 6,
"price": 1000,
"dishName": "new test dish",
"menuDate": "2023-04-07"
}

201 CREATED

TEST
GET http://localhost:8080/api/admin/restaurants/1/meals
Authorization: Basic admin@javaops.ru admin
[
{
"id": 1,
"price": 500,
"dishName": "meat balls",
"menuDate": "2023-04-07"
},
{
"id": 2,
"price": 800,
"dishName": "shrimps carry",
"menuDate": "2023-04-07"
},
{
"id": 6,
"price": 1000,
"dishName": "new test dish",
"menuDate": "2023-04-07"
}
]

###

GET http://localhost:8080/api/admin/meals
Authorization: Basic admin@javaops.ru admin

[
{
"id": 1,
"price": 500,
"dishName": "meat balls",
"menuDate": "2023-04-08"
},
{
"id": 2,
"price": 800,
"dishName": "shrimps carry",
"menuDate": "2023-04-08"
},
{
"id": 3,
"price": 650,
"dishName": "carbonara",
"menuDate": "2023-04-08"
},
{
"id": 4,
"price": 500,
"dishName": "grilled bri with mango sauce",
"menuDate": "2023-04-08"
},
{
"id": 5,
"price": 600,
"dishName": "best cocktail",
"menuDate": "2023-04-08"
}
]
200

###

PUT http://localhost:8080/api/admin/meals/2
Content-Type: application/json
Authorization: Basic admin@javaops.ru admin

{
"id": 2,
"price": 777,
"dishName": "testing dish",
"menuDate": "2023-04-08"
}

<Response body is empty>

Response code: 204

**AdminRestaurantController**

GET http://localhost:8080/api/admin/restaurants/1
Authorization: Basic admin@javaops.ru admin

{
"restaurantName": "BURO"
}
200

GET http://localhost:8080/api/admin/restaurants
Authorization: Basic admin@javaops.ru admin
[
{
"restaurantName": "BURO"
},
{
"restaurantName": "Dante"
},
{
"restaurantName": "Lets carbonara"
}
]
200

DELETE http://localhost:8080/api/admin/restaurants/1
Authorization: Basic admin@javaops.ru admin

<Response body is empty>

Response code: 204

TEST
[
{
"restaurantName": "Dante"
},
{
"restaurantName": "Lets carbonara"
}
]

