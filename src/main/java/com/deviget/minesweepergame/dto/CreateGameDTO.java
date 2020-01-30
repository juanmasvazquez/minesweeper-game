package com.deviget.minesweepergame.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateGameDTO {
	private String columns;
	private String rows;
	private String mines;
	private String username;
}
