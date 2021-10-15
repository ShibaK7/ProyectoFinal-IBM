package com.ibm.ATMService.ServiceSearchATM.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibm.ATMService.ServiceSearchATM.constants.Constant;
import com.ibm.ATMService.ServiceSearchATM.feing.ATMFeignI;
import com.ibm.ATMService.ServiceSearchATM.models.ATM;

@Component
public class ATMJsonParse {
	
	@Autowired
	private ATMFeignI atmFeignI;
	private List<ATM> atmList = new LinkedList<ATM>();
	
	public List<ATM> getATM() throws IOException{ 
		String json = atmFeignI.getJsonCallBack().substring(13, atmFeignI.getJsonCallBack().length()-2);
		
		JSONParser parser = new JSONParser();
		JSONObject response;
		
		try {
			
			response = (JSONObject) parser.parse(json);
			response = (JSONObject) response.get(Constant.NAME_JSON);
			
			Collection<JSONArray> respuesta = new LinkedList<JSONArray>();
			respuesta = response.values();

			respuesta.forEach((Object jsonObjectAux)-> {
				
				Collection<JSONArray> firstNivel = new LinkedList<JSONArray>();
				firstNivel =  ((HashMap) jsonObjectAux).values();
				
				firstNivel.forEach((Object objAux) -> {
					Collection<JSONArray> childs = new LinkedList<JSONArray>();
					childs = ((HashMap) objAux).values();
					
					childs.forEach((Object objATM) -> {
						Object[] arrayATM = ((ArrayList) objATM).toArray();
						String[] dataATM = arrayATM[4].toString().split(", ");
						
						if(dataATM.length>3) {
							ATM atmAux = new ATM();
							atmAux.setSuburb(dataATM[0]);
							atmAux.setCity(dataATM[1]);
							atmAux.setZipCode(dataATM[2].substring(4).trim());
							atmAux.setStreet(arrayATM[3].toString().substring(0,arrayATM[3].toString().length()-1));
							atmAux.setReference(arrayATM[6].toString());
							if(arrayATM[13].toString().equals(arrayATM[14].toString())) {
								atmAux.setSchedule("Schedule: " + arrayATM[13].toString());
							}
							else {
								atmAux.setSchedule("Opening: " + arrayATM[13].toString() + " Hrs Closing: "+ arrayATM[14].toString() + " Hrs");
							}
							atmAux.setLongitude(arrayATM[15].toString());
							atmAux.setLatitude(arrayATM[16].toString());
							atmAux.setState(arrayATM[17].toString());
							atmAux.setType(arrayATM[19].toString());
							atmList.add(atmAux);						
						}
					});
				});
			});
		} catch (ParseException parseException) {
			parseException.printStackTrace();
		}
		return atmList;
	}
}
