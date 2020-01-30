package com.deviget.minesweepergame.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "games")
public class Game {

	public enum GameStatus {
		PLAYING, PAUSED, GAME_OVER, WINNER
	}

	@Id
	private String id;
	private int columns;
	private int rows;
	private int mines;
	private String username;
	private GameStatus status;
	private Date startTime;
	private int seconds;
	private Cell[][] cells;
}
