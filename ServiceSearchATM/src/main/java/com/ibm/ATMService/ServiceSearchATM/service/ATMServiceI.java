package com.ibm.ATMService.ServiceSearchATM.service;

import java.util.List;

import com.ibm.ATMService.ServiceSearchATM.models.ATM;

public interface ATMServiceI {
	public List<ATM> searchByGPS(float latitude, float longitude);
	public List<ATM> searchByZipCode(int zipcode);
	public List<ATM> searchByCity(String city);
}
