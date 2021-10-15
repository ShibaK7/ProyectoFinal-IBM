package com.ibm.ATMService.ServiceSearchATM.constants;

public class Constant {
	
	private Constant() {
		throw new IllegalStateException(Constant.MESSAGE_CLASS);
	}
	
	public static final String NAME_JSON = "Servicios";
	
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ZIP = "zipCode";
	public static final String CITY = "city";
	
	public static final String DIACRITIC = "[\\p{InCombiningDiacriticalMarks}]";
	
	public static final String EMPTY_ERROR = "Empty values ​​cannot be processed";
	public static final String INVALID_ERROR = "Invalid values ​​cannot be processed";
	
	public static final String INFO_ATM = " ATM´s have been obtained with the search";
	
	public static final String ERROR_CODE = "Error Code: ";
	public static final String ERROR_TYPE = " Error Type: ";
	
	public static final String MESSAGE_CLASS = "Utility class";
			
}
