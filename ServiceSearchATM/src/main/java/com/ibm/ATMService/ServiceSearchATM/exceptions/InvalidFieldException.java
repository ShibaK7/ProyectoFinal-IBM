package com.ibm.ATMService.ServiceSearchATM.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidFieldException extends RuntimeException {
	private static final long serialVersionUID = -159378624L;
	private int errorCode;
	private String errorType;
}
