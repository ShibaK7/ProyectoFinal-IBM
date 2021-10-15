package com.ibm.ATMService.ServiceSearchATM.validate;

public interface ValidateI {
	public boolean validateGPS(String latitude, String longitude);
	public boolean validateZipCode(String zipCode);
	public boolean validateCity(String city);
}
