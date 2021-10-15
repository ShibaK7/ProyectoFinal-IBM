package com.ibm.CardService.ServiceGetCard.services;

import java.util.List;

import com.ibm.CardService.ServiceGetCard.models.CardRequest;

public interface IHttpConsumes {
	public List<String> sendGetCards(CardRequest cardRequest);
}
