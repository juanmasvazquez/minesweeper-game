**API Endpoints**

- Create new Game
`POST /api/game`

    - Request Body: `{
                	"columns": 8,
                	"rows": 8,
                	"mines": 10
                }`
    - Response Body: ```json
    {
                     "id": String,
                    "columns": Number,  
                           "rows": Number,
                            "mines": Number,
                            "status": String // PLAYING, PAUSED, GAME_OVER, WINNER,
                            "seconds": Number,
                            "cells": []
                }```               

- Create new Game
`GET /api/game`

    - Response Body: `{
                	 "id": String,
                    "columns": Number,
                            "rows": Number,
                            "mines": Number,
                            "status": String // PLAYING, PAUSED, GAME_OVER, WINNER,
                            "seconds": Number,
                            "cells": []
                }`
