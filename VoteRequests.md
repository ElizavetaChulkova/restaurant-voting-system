Голосование возможно только для нового юзера
Если голосовать для user@gmail.com после 11, то получаем forbidden

POST http://localhost:8080/api/account/vote/3
Authorization: Basic test@test.com test

Response:
{
"restaurantName": "Dante",
"voteDateTime": "2023-04-20T17:00:45.3292162"
}
201

###

Change vote at 18:00

POST http://localhost:8080/api/account/vote/3
Authorization: Basic test@test.com test

Response:
{
"restaurantName": "Dante",
"voteDateTime": "2023-04-07T18:15:05.4878309"
}

403 Forbidden

###

Запрос всех голосов конкретного юзера

GET http://localhost:8080/api/account/vote/all-votes
Content-Type: application/json
Authorization: Basic user@gmail.com password

Response:
[
{
"restaurantName": "BURO",
"voteDateTime": "2023-04-20T16:56:48"
}
]

###
