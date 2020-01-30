package com.deviget.minesweepergame.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionDTO {

	public enum ActionType {
		Q_MARK, REVEAL
	}

	private String column;
	private String row;
	private ActionType actionType;

}
