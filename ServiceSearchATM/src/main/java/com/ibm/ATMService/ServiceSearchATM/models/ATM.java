package com.ibm.ATMService.ServiceSearchATM.models;

import java.text.Normalizer;

import com.ibm.ATMService.ServiceSearchATM.constants.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ATM {
	private String city;
	private String street;
	private String suburb;
	private String zipCode;
	private String state;
	private String reference;
	private String latitude;
	private String longitude;
	private String schedule;
	private String type;
	
	public boolean checkGPS(float latitude, float longitude) {
		if((Float.parseFloat(this.latitude) > (latitude - 0.03f) && 
			Float.parseFloat(this.latitude) < (latitude + 0.03f)) &&  
			(Float.parseFloat(this.longitude) > (longitude - 0.03f) && 
			Float.parseFloat(this.longitude) < (longitude + 0.03f))) {
			return true;
		}
			
		return false;
	}
	
	public boolean checkZipCode(String zipCode) {
		if(this.zipCode.substring(0,3).equals(zipCode.substring(0, 3))) {
			return true;
		}

		return false;
	}
	
	public boolean checkCity(String city) {
		city = Normalizer.normalize(city, Normalizer.Form.NFD);
		city = city.replaceAll(Constant.DIACRITIC, "");
		
		String cityATM = Normalizer.normalize(this.city, Normalizer.Form.NFD);
		cityATM = cityATM.replaceAll(Constant.DIACRITIC, "");
		
		if(cityATM.equalsIgnoreCase(city) || cityATM.contains(city)) {
			return true;
		}
		
		return false;
	}
	
}
