package com.ibm.CardService.ServiceGetCard.daos;

import java.util.List;

import com.ibm.CardService.ServiceGetCard.models.CardRequest;

public interface ICardDao {
	public List<String> getCards(CardRequest cardRequest);
}
