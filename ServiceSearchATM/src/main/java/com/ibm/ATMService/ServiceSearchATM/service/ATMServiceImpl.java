package com.ibm.ATMService.ServiceSearchATM.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.ATMService.ServiceSearchATM.models.ATM;

@Service
public class ATMServiceImpl implements ATMServiceI {
	@Autowired
	ATMJsonParse atmJsonParse;
	private List<ATM> atmList = new LinkedList<ATM>();
	private List<ATM> response = new LinkedList<ATM>();

	public boolean checkATM(ATM atmObject) {
		boolean flag = false;
		
		for(ATM atmAux : response) {
			if(atmAux.getLatitude().equals(atmObject.getLatitude()) && 
			   atmAux.getLongitude().equals(atmObject.getLongitude()) && 
			   atmAux.getType().equals(atmObject.getType()) ) {
				flag = true;
			}
		}
		return flag;
	}
	
	@Override
	public List<ATM> searchByGPS(float latitude, float longitude) {
		response.clear();
		
		try {
			atmList = atmJsonParse.getATM();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		atmList.forEach((ATM atmObject) -> {
			if(atmObject.checkGPS(latitude, longitude) && !checkATM(atmObject)) {
				response.add(atmObject);
			}
		});
		return response;
	}
	
	@Override
	public List<ATM> searchByZipCode(int zipcode) {
		response.clear();
		
		try {
			atmList = atmJsonParse.getATM();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		atmList.forEach((ATM atmObject) -> {
			if(atmObject.checkZipCode(Integer.toString(zipcode)) && !checkATM(atmObject)) {
				response.add(atmObject);	
			}
		});
		return response;
	}
	
	@Override
	public List<ATM> searchByCity(String city) {
		response.clear();
		
		try {
			atmList = atmJsonParse.getATM();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		atmList.forEach((ATM atmObject) -> {
			if(atmObject.checkCity(city) && !checkATM(atmObject)) {
				response.add(atmObject);
			}
		});
		return response;
	}

}
