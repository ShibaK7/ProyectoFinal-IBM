package com.ibm.ATMService.ServiceSearchATM.models;

import com.ibm.ATMService.ServiceSearchATM.constants.Constant;
import com.ibm.ATMService.ServiceSearchATM.exceptions.ErrorType;
import com.ibm.ATMService.ServiceSearchATM.exceptions.InvalidFieldException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ATMRequest {
	private String latitude;
	private String longitude;
	private String zipCode;
	private String city;
	
	public boolean validateRequest() {
		if(this.latitude == null || this.longitude == null || this.zipCode == null || this.city == null) {
			throw new InvalidFieldException(ErrorType.INVALID_FIELD.getCode(), Constant.INVALID_ERROR);
		}
		
		return true;
	}
}
