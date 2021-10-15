package com.ibm.CardService.ServiceGetCard.globalHandlerException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ibm.CardService.ServiceGetCard.constants.Constant;
import com.ibm.CardService.ServiceGetCard.exceptions.EmptyFieldException;
import com.ibm.CardService.ServiceGetCard.exceptions.InvalidFieldException;
import com.ibm.CardService.ServiceGetCard.models.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalHandlerException {
	
	@ExceptionHandler(EmptyFieldException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	ErrorResponse handleEmptyFieldException(EmptyFieldException emptyFieldException){
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(emptyFieldException.getErrorCode());
		errorResponse.setType(emptyFieldException.getErrorType());
		setLog(errorResponse.getCode(), errorResponse.getType());
		return errorResponse;
	}
	
	@ExceptionHandler(InvalidFieldException.class)
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	ErrorResponse handleNullObjectException(InvalidFieldException nullObjectException){
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(nullObjectException.getErrorCode());
		errorResponse.setType(nullObjectException.getErrorType());
		setLog(errorResponse.getCode(), errorResponse.getType());
		return errorResponse;
	}
	
	public void setLog(int code, String type) {
		log.error(Constant.ERROR_CODE + code + Constant.ERROR_TYPE + type);
	}
}
