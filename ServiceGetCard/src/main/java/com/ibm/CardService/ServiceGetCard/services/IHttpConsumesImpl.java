package com.ibm.CardService.ServiceGetCard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.CardService.ServiceGetCard.daos.ICardDao;
import com.ibm.CardService.ServiceGetCard.models.CardRequest;

@Service
public class IHttpConsumesImpl implements IHttpConsumes{
	@Autowired
	ICardDao cardDao;
	
	@Override
	public List<String> sendGetCards(CardRequest cardRequest) {
		return cardDao.getCards(cardRequest);
	}

}
