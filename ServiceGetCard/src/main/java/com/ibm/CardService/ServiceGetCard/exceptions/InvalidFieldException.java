package com.ibm.CardService.ServiceGetCard.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidFieldException extends RuntimeException {
	private static final long serialVersionUID = -789321465L;
	private int errorCode;
	private String errorType;
}
