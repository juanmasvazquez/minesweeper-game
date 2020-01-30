package com.deviget.minesweepergame.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGameDTO {
	private String columns;
	private String rows;
	private String mines;
	private String username;
}
