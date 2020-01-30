**API Endpoints**

- Create new Game
`POST /api/game`

    - Request Body: `{
                	"columns": Number,
                	"rows": Number,
                	"mines": Number
                }`
    - Response Body: `{
                     "id": String,
                    "columns": Number,`  
                           ` "rows": Number,
                            "mines": Number,
                            "status": String // PLAYING, PAUSED, GAME_OVER, WINNER,
                            "seconds": Number,
                            "cells": []
                }`                

- Get All Games
`GET /api/game`

    - Response Body: `[{
                	 "id": String,
                    "columns": Number,
                            "rows": Number,
                            "mines": Number,
                            "status": String // PLAYING, PAUSED, GAME_OVER, WINNER,
                            "seconds": Number,
                            "cells": []
                }]`

- Get Game
`GET /api/game/{gameId}`

    - Response Body: `{
                	 "id": String,
                    "columns": Number,
                            "rows": Number,
                            "mines": Number,
                            "status": String // PLAYING, PAUSED, GAME_OVER, WINNER,
                            "seconds": Number,
                            "cells": []
                }`
                
- Execute Action
`PUT /api/game/action/{gameId}`

    - Request Body: `{
                     	"column": Number,
                     	"row": Number,
                     	"actionType": String // Q_MARK, REVEAL
                     }`
    - Response Body: `{
                	 "id": String,
                    "columns": Number,
                            "rows": Number,
                            "mines": Number,
                            "status": String // PLAYING, PAUSED, GAME_OVER, WINNER,
                            "seconds": Number,
                            "cells": []
                }` 

- Pause Game
`PUT /api/game/pause/{gameId}`

    - Response Body: `{
                	 "id": String,
                    "columns": Number,
                            "rows": Number,
                            "mines": Number,
                            "status": String // PLAYING, PAUSED, GAME_OVER, WINNER,
                            "seconds": Number,
                            "cells": []
                }`                
                
- Play Game
`PUT /api/game/play/{gameId}`

    - Response Body: `{
                	 "id": String,
                    "columns": Number,
                            "rows": Number,
                            "mines": Number,
                            "status": String // PLAYING, PAUSED, GAME_OVER, WINNER,
                            "seconds": Number,
                            "cells": []
                }`                                                               