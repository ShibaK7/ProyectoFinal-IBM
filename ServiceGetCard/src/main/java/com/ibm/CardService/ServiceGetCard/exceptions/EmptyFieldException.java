package com.ibm.CardService.ServiceGetCard.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmptyFieldException extends RuntimeException {
	private static final long serialVersionUID = -123789456L;
	private int errorCode;
	private String errorType;
}
