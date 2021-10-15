package com.ibm.CardService.ServiceGetCard.controller;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.CardService.ServiceGetCard.constants.Constant;
import com.ibm.CardService.ServiceGetCard.models.CardRequest;
import com.ibm.CardService.ServiceGetCard.services.IHttpConsumes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("${controller.card.mapping}")
public class CardRestController {
	
	@Autowired
	IHttpConsumes httpConsumes;
	List<String> listCards = new LinkedList<String>();
	
	@GetMapping(value="${controller.card.get}", consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public ResponseEntity<List<String>> getCards(@RequestBody CardRequest cardRequest) {
		listCards.clear();
		if(cardRequest.validateRequest()) {
			listCards = httpConsumes.sendGetCards(cardRequest);
		}

		setLog();
		
		if(!listCards.isEmpty()) {
			return new ResponseEntity<List<String>>(listCards, HttpStatus.OK);
		}
		return new ResponseEntity<List<String>>(listCards, HttpStatus.NOT_FOUND); 
	}
	
	public void setLog() {
		log.info(listCards.size() + Constant.INFO_CARD);
	}
	
}
