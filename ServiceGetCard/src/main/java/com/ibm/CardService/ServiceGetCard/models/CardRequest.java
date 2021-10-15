package com.ibm.CardService.ServiceGetCard.models;

import com.ibm.CardService.ServiceGetCard.constants.Constant;
import com.ibm.CardService.ServiceGetCard.exceptions.ErrorType;
import com.ibm.CardService.ServiceGetCard.exceptions.InvalidFieldException;
import com.ibm.CardService.ServiceGetCard.exceptions.EmptyFieldException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardRequest {
	
	private String passion;
	private double salary;
	private int age;
	
	public boolean validateRequest() {
		if(this.passion == null || this.salary <= 0 || this.age < 18) {
			throw new InvalidFieldException(ErrorType.INVALID_FIELD.getCode(), Constant.INVALID_ERROR);
		}
		
		if(this.passion.isEmpty()) {
			throw new EmptyFieldException(ErrorType.EMPTY_FIELD.getCode(), Constant.EMPTY_ERROR);
		}
		
		return true;
	}
}
