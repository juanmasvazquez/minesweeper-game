package com.deviget.minesweepergame.controllers;

import com.deviget.minesweepergame.dto.ActionDTO;
import com.deviget.minesweepergame.dto.GameDTO;
import com.deviget.minesweepergame.exceptions.NotFoundException;
import com.deviget.minesweepergame.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/game")
public class GameController {

	private GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@PostMapping
	public ResponseEntity<GameDTO> createGame(@RequestHeader("username") String username, @RequestBody GameDTO game)  {
		GameDTO newGame = this.gameService.createGame(username, game);
		return new ResponseEntity<>(newGame, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<GameDTO>> getActiveGames(@RequestHeader("username") String username) {
		List<GameDTO> games = this.gameService.getGames(username);
		return new ResponseEntity<>(games, HttpStatus.OK);
	}

	@GetMapping("/{gameId}")
	public ResponseEntity<GameDTO> getGame(@RequestHeader("username") String username, @PathVariable("gameId") String gameId) throws NotFoundException {
		GameDTO newGame = this.gameService.getGame(username, gameId);
		return new ResponseEntity<>(newGame, HttpStatus.OK);
	}

	@PutMapping("/action/{gameId}")
	public ResponseEntity<GameDTO> executeAction(@RequestHeader("username") String username, @PathVariable("gameId") String gameId, @RequestBody ActionDTO action) throws NotFoundException {
		GameDTO newGame = this.gameService.executeAction(username, gameId, action);
		return new ResponseEntity<>(newGame, HttpStatus.OK);
	}

	@PutMapping("/pause/{gameId}")
	public ResponseEntity<GameDTO> pauseGame(@RequestHeader("username") String username, @PathVariable("gameId") String gameId) throws NotFoundException {
		GameDTO newGame = this.gameService.pauseGame(username, gameId);
		return new ResponseEntity<>(newGame, HttpStatus.OK);
	}

	@PutMapping("/play/{gameId}")
	public ResponseEntity<GameDTO> playGame(@RequestHeader("username") String username, @PathVariable("gameId") String gameId) throws NotFoundException {
		GameDTO newGame = this.gameService.playGame(username, gameId);
		return new ResponseEntity<>(newGame, HttpStatus.OK);
	}

}
