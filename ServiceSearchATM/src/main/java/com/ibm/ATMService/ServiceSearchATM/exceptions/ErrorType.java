package com.ibm.ATMService.ServiceSearchATM.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {
	EMPTY_FIELD(400),
	INVALID_FIELD(406);

	private int code;
}
