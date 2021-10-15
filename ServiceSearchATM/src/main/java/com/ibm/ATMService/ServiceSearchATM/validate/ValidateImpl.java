package com.ibm.ATMService.ServiceSearchATM.validate;

import org.springframework.stereotype.Component;

import com.ibm.ATMService.ServiceSearchATM.constants.Constant;
import com.ibm.ATMService.ServiceSearchATM.exceptions.EmptyFieldException;
import com.ibm.ATMService.ServiceSearchATM.exceptions.ErrorType;
import com.ibm.ATMService.ServiceSearchATM.exceptions.InvalidFieldException;

@Component
public class ValidateImpl implements ValidateI{

	@Override
	public boolean validateGPS(String latitude, String longitude) {
		
		if(latitude.isEmpty() || longitude.isEmpty()) {
			throw new EmptyFieldException(ErrorType.EMPTY_FIELD.getCode(), Constant.EMPTY_ERROR);
		}
		
		try {
			Float auxLatitude = Float.parseFloat(latitude);
			Float auxLongitude = Float.parseFloat(longitude);
			
			if( auxLatitude >= 1000 || auxLatitude <= -1000 ||  
				auxLongitude >= 1000 || auxLongitude <= -1000) {
					throw new InvalidFieldException(ErrorType.INVALID_FIELD.getCode(), Constant.INVALID_ERROR);
			}
			
        } catch (NumberFormatException excepcion) {
        	throw new InvalidFieldException(ErrorType.INVALID_FIELD.getCode(), Constant.INVALID_ERROR);
        }
		
		return true;
	}
	
	@Override
	public boolean validateZipCode(String zipCode) {
		if(zipCode.isEmpty()) {
			throw new EmptyFieldException(ErrorType.EMPTY_FIELD.getCode(), Constant.EMPTY_ERROR);
		}
		
		if(zipCode.length() != 5 || zipCode == null) {
			throw new InvalidFieldException(ErrorType.INVALID_FIELD.getCode(), Constant.INVALID_ERROR);
		}
		
		return true;
	}
	
	@Override
	public boolean validateCity(String city) {
		if(city.isEmpty()) {
			throw new EmptyFieldException(ErrorType.EMPTY_FIELD.getCode(), Constant.EMPTY_ERROR);
		}
		else if(city == null || city.length() < 5) {
			throw new InvalidFieldException(ErrorType.INVALID_FIELD.getCode(), Constant.INVALID_ERROR);
		}
		
		return true;
	}

}
