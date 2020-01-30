package com.deviget.minesweepergame.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionDTO {

	public enum ActionType {
		Q_MARK, REVEAL
	}

	private String column;
	private String row;
	private ActionType actionType;

}
