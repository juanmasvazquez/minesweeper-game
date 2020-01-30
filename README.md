### **Minesweeper - Deviget**

**Technical details**
-
- Java 8
- Spring Boot
- MongoDB
- Maven
- Docker

NOTE: MongoDB instance is embedded, so if the server is reset all the data is gonna be lost, this just for testing purposes.

**API Endpoints**
-
- Create new Game
`POST /api/game`

    - Request Body: `{
                	"columns": Number,
                	"rows": Number,
                	"mines": Number
                }`
    - Response Body: `GameResponse`            

- Get All Games
`GET /api/game`

    - Response Body: `[GameResponse, ...]`

- Get Game
`GET /api/game/{gameId}`

    - Response Body: `GameResponse` 
                
- Reveal Cell
`PUT /api/game/{gameId}/cell/{row}/{col}/reveal`
                      
   - Response Body: `GameResponse` 
   
- Mark Cell
`PUT /api/game/{gameId}/cell/{row}/{col}/mark`
                      
   - Response Body: `GameResponse`    

- Pause Game
`PUT /api/game/pause/{gameId}`

    - Response Body: `GameResponse`                
                
- Play Game
`PUT /api/game/play/{gameId}`

    - Response Body: `GameResponse` 

    
    GameResponse
    
    {
        "id": String,
        "columns": Number,
        "rows": Number,
        "mines": Number,
        "status": String // PLAYING, PAUSED, GAME_OVER, WINNER,
        "seconds": Number,
        "cells": [],
        "printable": String
     }                
                
                
- Headers `'username: juanmasvazquez'` simulates a token based security
                                                           