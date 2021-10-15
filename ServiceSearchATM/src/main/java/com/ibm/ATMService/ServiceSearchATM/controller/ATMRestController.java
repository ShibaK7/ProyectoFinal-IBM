package com.ibm.ATMService.ServiceSearchATM.controller;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.ATMService.ServiceSearchATM.constants.Constant;
import com.ibm.ATMService.ServiceSearchATM.models.ATM;
import com.ibm.ATMService.ServiceSearchATM.models.ATMRequest;
import com.ibm.ATMService.ServiceSearchATM.service.ATMServiceI;
import com.ibm.ATMService.ServiceSearchATM.validate.ValidateI;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("${controller.atm.mapping}")
public class ATMRestController {

	@Autowired
	private ATMServiceI atmServiceI;
	@Autowired ValidateI validateI;
	private List<ATM> response = new LinkedList<ATM>();
	
	@GetMapping(value="${controller.atm.getByPriority}", 
				consumes = MediaType.APPLICATION_JSON,
				produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ATM>> searchATMPriority(@RequestBody ATMRequest atmRequest){
		response.clear();
		if(atmRequest.validateRequest()) {
			if(!atmRequest.getLatitude().isEmpty() && !atmRequest.getLongitude().isEmpty()) {
				response = atmServiceI.searchByGPS(Float.parseFloat(atmRequest.getLatitude()), Float.parseFloat(atmRequest.getLongitude()));
			}
			else if(!atmRequest.getZipCode().isEmpty()) {
				response = atmServiceI.searchByZipCode(Integer.parseInt(atmRequest.getZipCode()));
			}
			else if(!atmRequest.getCity().isEmpty()) {
				response = atmServiceI.searchByCity(atmRequest.getCity());
			}
		}
		
		return evaluateResponse();
	}
	
	@GetMapping(value="${controller.atm.getByGPS}", params={Constant.LATITUDE, Constant.LONGITUDE},
			produces=MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ATM>> searchATMGPS(@RequestParam String latitude, String longitude) {
		response.clear();
		if(validateI.validateGPS(latitude, longitude)) {
			response = atmServiceI.searchByGPS(Float.parseFloat(latitude), Float.parseFloat(longitude));	
		}
		
		return evaluateResponse();
	}
	
	@GetMapping(value="${controller.atm.getByZipCode}", params=Constant.ZIP,
			produces=MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ATM>> searchATMZipCode(@RequestParam String zipCode) {
		response.clear();
		if(validateI.validateZipCode(zipCode)) {
			response = atmServiceI.searchByZipCode(Integer.parseInt(zipCode));	
		}
		return evaluateResponse();
	}
	
	@GetMapping(value="${controller.atm.getByCity}", params=Constant.CITY,
			produces=MediaType.APPLICATION_JSON)
	public ResponseEntity<List<ATM>> searchATMCity(@RequestParam String city) {
		response.clear();
		if(validateI.validateCity(city)) {
			response = atmServiceI.searchByCity(city);	
		}
		
		return evaluateResponse();
	}
	
	public ResponseEntity<List<ATM>> evaluateResponse() {
		setLog();
		if(!response.isEmpty()) {
			return new ResponseEntity<List<ATM>>(response, HttpStatus.OK);
		}
		return new ResponseEntity<List<ATM>>(response, HttpStatus.NOT_FOUND);
	}
	
	public void setLog() {
		log.info(response.size() + Constant.INFO_ATM);
	}
}
