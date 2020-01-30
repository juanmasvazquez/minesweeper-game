package com.deviget.minesweepergame;

import com.deviget.minesweepergame.dto.GameDTO;
import com.deviget.minesweepergame.model.Cell;
import com.deviget.minesweepergame.model.Game;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public final class GameUtils {

	private GameUtils() {
	}

	private static Random random = new Random();
	private static final String MINE_CHAR = "X";
	private static final String BLANK_CHAR = "-";
	private static final String HIDE_CHAR = "";
	private static final String Q_MARK_CHAR = "?";

	public static GameDTO convertGameToGameDTO(Game game) {
		GameDTO gameDTO = GameDTO.builder()
				.id(game.getId())
				.seconds(calculateTime(game.getSeconds(), game.getStartTime()))
				.status(game.getStatus().name())
				.mines(game.getMines())
				.rows(game.getRows())
				.columns(game.getColumns())
				.cells(convertCells(game.getCells()))
				.build();

		gameDTO.setPrintable(createPrintable(gameDTO));
		printGameCells(gameDTO);
		return gameDTO;
	}

	private static String[] createPrintable(GameDTO gameDTO) {
		String[][] gameCells = gameDTO.getCells();
		List<String> printLines = new ArrayList<>();
		printLines.add("Status: " + gameDTO.getStatus());
		printLines.add("Time: " +  gameDTO.getSeconds());
		printLines.add("Mines: " +  gameDTO.getMines());
		for (String[] gameCell : gameCells) {
			StringBuilder stringBuilder = new StringBuilder();
			for (int j = 0; j < gameCell.length; j++) {
				String cell = gameCell[j];
				stringBuilder.append(StringUtils.leftPad(cell, 1, " ")).append("|");
			}
			printLines.add(stringBuilder.toString());
		}
		return printLines.toArray(new String[0]);
	}

	private static int calculateTime(int seconds, Date startTime) {
		int time = seconds;
		if(startTime != null){
			time += (new Date().getTime() - startTime.getTime()) / 1000;
		}
		return time;
	}

	private static String[][] convertCells(Cell[][] cells) {

		String[][] gameCells = new String[cells.length][];
		for (int i = 0; i < cells.length; i++) {
			gameCells[i] = new String[cells[i].length];
			for (int j = 0; j < cells[i].length; j++) {
				Cell cell = cells[i][j];
				String content = HIDE_CHAR;
				if (cell.getCellStatus() == Cell.CellStatus.REVEALED) {
					if (cell.getCellContentType() == Cell.CellContentType.MINE) {
						content = MINE_CHAR;
					} else if (cell.getCellContentType() == Cell.CellContentType.TOTAL_MINES) {
						content = cell.getTotalMines() + "";
					} else if (cell.getCellContentType() == Cell.CellContentType.BLANK) {
						content = BLANK_CHAR;
					}
				} else if (cell.getCellStatus() == Cell.CellStatus.MARKED) {
					content = Q_MARK_CHAR;
				}
				gameCells[i][j] = content;
			}
		}
		return gameCells;
	}

	private static void printGameCells(GameDTO gameDTO) {
		String[][] gameCells = gameDTO.getCells();
		System.out.println("Status: " + gameDTO.getStatus());
		System.out.println("Time: " + gameDTO.getSeconds());
		System.out.println("Mines: " + gameDTO.getMines());
		for (String[] gameCell : gameCells) {
			for (int j = 0; j < gameCell.length; j++) {
				String cell = gameCell[j];
				System.out.print(cell + " | ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}

	private static Cell getCell(int row, int column, Cell[][] cells) {
		if (row >= 0 && row < cells.length && column >= 0 && column < cells[row].length) {
			return cells[row][column];
		}
		return null;
	}

	public static Cell getTop(int row, int column, Cell[][] cells) {
		return getCell(row - 1, column, cells);
	}

	public static Cell getTopRight(int row, int column, Cell[][] cells) {
		return getCell(row - 1, column + 1, cells);
	}

	public static Cell getRight(int row, int column, Cell[][] cells) {
		return getCell(row, column + 1, cells);
	}

	public static Cell getRightBottom(int row, int column, Cell[][] cells) {
		return getCell(row + 1, column + 1, cells);
	}

	public static Cell getBottom(int row, int column, Cell[][] cells) {
		return getCell(row + 1, column, cells);
	}

	public static Cell getBottomLeft(int row, int column, Cell[][] cells) {
		return getCell(row + 1, column - 1, cells);
	}

	public static Cell getLeft(int row, int column, Cell[][] cells) {
		return getCell(row, column - 1, cells);
	}

	public static Cell getLeftTop(int row, int column, Cell[][] cells) {
		return getCell(row - 1, column - 1, cells);
	}


	public static void assignRandomMine(int rows, int columns, int mines, Cell[][] board) {
		for (int i = 0; i < mines; i++) {
			int randomRow = random.nextInt(rows);
			int randomCol = random.nextInt(columns);
			while (board[randomRow][randomCol].getCellContentType() != Cell.CellContentType.BLANK) {
				randomRow = random.nextInt(rows);
				randomCol = random.nextInt(columns);
			}
			board[randomRow][randomCol].setCellContentType(Cell.CellContentType.MINE);
		}
	}

	public static void assignRandomMine(Cell[][] board) {
		board[0][0].setCellContentType(Cell.CellContentType.MINE);
		board[5][0].setCellContentType(Cell.CellContentType.MINE);
		board[5][1].setCellContentType(Cell.CellContentType.MINE);
		board[5][3].setCellContentType(Cell.CellContentType.MINE);
		board[5][6].setCellContentType(Cell.CellContentType.MINE);
		board[3][4].setCellContentType(Cell.CellContentType.MINE);
		board[3][5].setCellContentType(Cell.CellContentType.MINE);
		board[4][6].setCellContentType(Cell.CellContentType.MINE);
		board[6][3].setCellContentType(Cell.CellContentType.MINE);
		board[7][3].setCellContentType(Cell.CellContentType.MINE);
	}

	public static boolean isMine(Cell targetCell) {
		return targetCell != null && targetCell.getCellContentType() == Cell.CellContentType.MINE;
	}

	public static boolean areAllCellsRevealed(Game currentGame) {
		Cell[][] board = currentGame.getCells();
		int totalMinesToReveal = 0;
		for (Cell[] cells : board) {
			totalMinesToReveal += Arrays.stream(cells).filter(cell -> cell.getCellStatus() != Cell.CellStatus.REVEALED).count();
		}
		return currentGame.getMines() == totalMinesToReveal;
	}

	public static Cell[][] initCells(int rows, int columns, int mines) {
		Cell[][] gameCells = new Cell[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				gameCells[i][j] = Cell.builder()
						.row(i)
						.column(j)
						.cellContentType(Cell.CellContentType.BLANK)
						.cellStatus(Cell.CellStatus.HIDE)
						.build();
			}
		}

		assignRandomMine(rows, columns, mines, gameCells);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				assignCellContent(gameCells[i][j], gameCells);
			}
		}
		return gameCells;
	}

	private static void assignCellContent(Cell target, Cell[][] cells) {

		if (target.getCellContentType() == Cell.CellContentType.MINE)
			return;

		int totalMines = 0;

		if (GameUtils.isMine(GameUtils.getTop(target.getRow(), target.getColumn(), cells))) {
			totalMines++;
		}
		if (GameUtils.isMine(GameUtils.getTopRight(target.getRow(), target.getColumn(), cells))) {
			totalMines++;
		}
		if (GameUtils.isMine(GameUtils.getRight(target.getRow(), target.getColumn(), cells))) {
			totalMines++;
		}
		if (GameUtils.isMine(GameUtils.getRightBottom(target.getRow(), target.getColumn(), cells))) {
			totalMines++;
		}
		if (GameUtils.isMine(GameUtils.getBottom(target.getRow(), target.getColumn(), cells))) {
			totalMines++;
		}
		if (GameUtils.isMine(GameUtils.getBottomLeft(target.getRow(), target.getColumn(), cells))) {
			totalMines++;
		}
		if (GameUtils.isMine(GameUtils.getLeft(target.getRow(), target.getColumn(), cells))) {
			totalMines++;
		}
		if (GameUtils.isMine(GameUtils.getLeftTop(target.getRow(), target.getColumn(), cells))) {
			totalMines++;
		}

		if (totalMines == 0) {
			target.setCellContentType(Cell.CellContentType.BLANK);
		} else {
			target.setCellContentType(Cell.CellContentType.TOTAL_MINES);
			target.setTotalMines(totalMines);
		}
	}

}
