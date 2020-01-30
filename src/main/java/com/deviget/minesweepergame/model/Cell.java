package com.deviget.minesweepergame.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cell {

	public enum CellStatus{
		REVEALED, MARKED, HIDE
	}

	public enum CellContentType{
		BLANK, TOTAL_MINES, MINE
	}

	private int row;
	private int column;
	private int totalMines;
	private CellContentType cellContentType;
	private CellStatus cellStatus;
}
