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
