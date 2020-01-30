package com.deviget.minesweepergame.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDTO {
	private String id;
	private int columns;
	private int rows;
	private int mines;
	private String status;
	private String startedTime;
	private int seconds;
	private String[][] cells;
}
