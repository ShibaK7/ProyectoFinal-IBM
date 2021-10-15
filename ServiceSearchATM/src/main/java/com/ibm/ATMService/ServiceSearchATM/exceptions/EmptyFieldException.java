package com.ibm.ATMService.ServiceSearchATM.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmptyFieldException extends RuntimeException {
	private static final long serialVersionUID = -147852369L;
	private int errorCode;
	private String errorType;
}
