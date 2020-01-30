package com.deviget.minesweepergame.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDTO {
	private String id;
	private int columns;
	private int rows;
	private int mines;
	private String status;
	private String startedTime;
	private int seconds;
	private String[][] cells;
	private String[] printable;
}
