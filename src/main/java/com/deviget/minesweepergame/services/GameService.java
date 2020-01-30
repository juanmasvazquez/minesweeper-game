package com.deviget.minesweepergame.services;

import com.deviget.minesweepergame.GameUtils;
import com.deviget.minesweepergame.dto.ActionDTO;
import com.deviget.minesweepergame.dto.GameDTO;
import com.deviget.minesweepergame.exceptions.NotFoundException;
import com.deviget.minesweepergame.model.Cell;
import com.deviget.minesweepergame.model.Game;
import com.deviget.minesweepergame.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {

	private GameRepository gameRepository;

	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	public GameDTO createGame(String username, GameDTO game) {
		Game newGame = Game.builder()
				.rows(game.getRows())
				.columns(game.getColumns())
				.mines(game.getMines())
				.startTime(new Date())
				.status(Game.GameStatus.PLAYING)
				.username(username)
				.cells(GameUtils.initCells(game.getRows(), game.getColumns(), game.getMines()))
				.build();
		gameRepository.save(newGame);
		return GameUtils.convertGameToGameDTO(newGame);
	}

	public GameDTO pauseGame(String username, String gameId) throws NotFoundException {
		Game currentGame = gameRepository.findByUsernameAndId(username, gameId).orElseThrow(NotFoundException::new);
		if (currentGame.getStatus() == Game.GameStatus.PLAYING) {
			currentGame.setStatus(Game.GameStatus.PAUSED);
			long seconds = (new Date().getTime() - currentGame.getStartTime().getTime()) / 1000;
			currentGame.setSeconds((int) (currentGame.getSeconds() + seconds));
			currentGame.setStartTime(null);
			gameRepository.save(currentGame);
		}
		return GameUtils.convertGameToGameDTO(currentGame);
	}

	public GameDTO playGame(String username, String gameId) throws NotFoundException {
		Game currentGame = gameRepository.findByUsernameAndId(username, gameId).orElseThrow(NotFoundException::new);
		if (currentGame.getStatus() == Game.GameStatus.PAUSED) {
			currentGame.setStatus(Game.GameStatus.PLAYING);
			currentGame.setStartTime(new Date());
			gameRepository.save(currentGame);
		}
		return GameUtils.convertGameToGameDTO(currentGame);
	}

	public GameDTO revealCell(String username, String gameId, String row, String column) throws NotFoundException {
		Game currentGame = gameRepository.findByUsernameAndId(username, gameId).orElseThrow(NotFoundException::new);
		if (currentGame.getStatus() == Game.GameStatus.PLAYING) {
			revealCell(currentGame, Integer.parseInt(row), Integer.parseInt(column));
			gameRepository.save(currentGame);
		}
		return GameUtils.convertGameToGameDTO(currentGame);
	}

	public GameDTO markCell(String username, String gameId, String row, String column) throws NotFoundException {
		Game currentGame = gameRepository.findByUsernameAndId(username, gameId).orElseThrow(NotFoundException::new);
		if (currentGame.getStatus() == Game.GameStatus.PLAYING) {
			assignQuestionMarkToCell(currentGame, Integer.parseInt(row), Integer.parseInt(column));
			gameRepository.save(currentGame);
		}
		return GameUtils.convertGameToGameDTO(currentGame);
	}

	public GameDTO executeAction(String username, String gameId, ActionDTO actionDTO) throws NotFoundException {
		Game currentGame = gameRepository.findByUsernameAndId(username, gameId).orElseThrow(NotFoundException::new);
		if (currentGame.getStatus() == Game.GameStatus.PLAYING) {
			if (actionDTO.getActionType() == ActionDTO.ActionType.Q_MARK) {
				assignQuestionMarkToCell(currentGame, Integer.parseInt(actionDTO.getRow()), Integer.parseInt(actionDTO.getColumn()));
			} else if (actionDTO.getActionType() == ActionDTO.ActionType.REVEAL) {
				revealCell(currentGame, Integer.parseInt(actionDTO.getRow()), Integer.parseInt(actionDTO.getColumn()));
			}
			gameRepository.save(currentGame);
		}
		return GameUtils.convertGameToGameDTO(currentGame);
	}

	private void revealCell(Game currentGame, int row, int column) {
		Cell targetCell = currentGame.getCells()[row][column];
		if (targetCell.getCellContentType() == Cell.CellContentType.MINE) {
			currentGame.setStatus(Game.GameStatus.GAME_OVER);
			long seconds = (new Date().getTime() - currentGame.getStartTime().getTime()) / 1000;
			currentGame.setSeconds((int) (currentGame.getSeconds() + seconds));
			currentGame.setStartTime(null);
			revealMineCells(currentGame);
		} else if (targetCell.getCellStatus() != Cell.CellStatus.REVEALED) {
			revealCells(targetCell, currentGame.getCells());
			if (GameUtils.areAllCellsRevealed(currentGame)) {
				currentGame.setStatus(Game.GameStatus.WINNER);
				long seconds = (new Date().getTime() - currentGame.getStartTime().getTime()) / 1000;
				currentGame.setSeconds((int) (currentGame.getSeconds() + seconds));
				currentGame.setStartTime(null);
			}
		}
	}

	private void revealMineCells(Game currentGame) {
		Cell[][] board = currentGame.getCells();
		for (Cell[] cells : board) {
			Arrays.stream(cells).filter(cell -> cell.getCellContentType() == Cell.CellContentType.MINE)
					.collect(Collectors.toList()).forEach(cellMine -> cellMine.setCellStatus(Cell.CellStatus.REVEALED));
		}
	}

	private void revealCells(Cell cell, Cell[][] cells) {

		if (cell == null
				|| cell.getCellContentType() == Cell.CellContentType.MINE
				|| cell.getCellStatus() == Cell.CellStatus.REVEALED
				|| cell.getCellStatus() == Cell.CellStatus.MARKED) {
			return;
		}

		if (cell.getCellContentType() == Cell.CellContentType.TOTAL_MINES) {
			cell.setCellStatus(Cell.CellStatus.REVEALED);
		} else if (cell.getCellContentType() == Cell.CellContentType.BLANK) {
			cell.setCellStatus(Cell.CellStatus.REVEALED);
			revealCells(GameUtils.getTop(cell.getRow(), cell.getColumn(), cells), cells);
			revealCells(GameUtils.getTopRight(cell.getRow(), cell.getColumn(), cells), cells);
			revealCells(GameUtils.getRight(cell.getRow(), cell.getColumn(), cells), cells);
			revealCells(GameUtils.getRightBottom(cell.getRow(), cell.getColumn(), cells), cells);
			revealCells(GameUtils.getBottom(cell.getRow(), cell.getColumn(), cells), cells);
			revealCells(GameUtils.getBottomLeft(cell.getRow(), cell.getColumn(), cells), cells);
			revealCells(GameUtils.getLeft(cell.getRow(), cell.getColumn(), cells), cells);
			revealCells(GameUtils.getLeftTop(cell.getRow(), cell.getColumn(), cells), cells);
		}
	}

	private Game assignQuestionMarkToCell(Game currentGame, int row, int column) {
		Cell targetCell = currentGame.getCells()[row][column];
		if (targetCell.getCellStatus() != Cell.CellStatus.REVEALED) {
			targetCell.setCellStatus(targetCell.getCellStatus() == Cell.CellStatus.MARKED ? Cell.CellStatus.HIDE : Cell.CellStatus.MARKED);
		}
		return currentGame;
	}

	public List<GameDTO> getGames(String username) {
		return this.gameRepository.findByUsername(username).stream().map(GameUtils::convertGameToGameDTO).collect(Collectors.toList());
	}

	public GameDTO getGame(String username, String gameId) throws NotFoundException {
		return GameUtils.convertGameToGameDTO(this.gameRepository.findByUsernameAndId(username, gameId).orElseThrow(NotFoundException::new));
	}
}


