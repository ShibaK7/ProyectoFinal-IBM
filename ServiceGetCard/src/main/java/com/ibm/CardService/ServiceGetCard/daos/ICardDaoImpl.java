package com.ibm.CardService.ServiceGetCard.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ibm.CardService.ServiceGetCard.constants.Constant;
import com.ibm.CardService.ServiceGetCard.models.CardRequest;

@Repository
public class ICardDaoImpl implements ICardDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<String> getCards(CardRequest cardRequest) {
		String query = Constant.QUERY;
		return this.jdbcTemplate.queryForList(query, String.class, 
				cardRequest.getAge(), 
				cardRequest.getAge(), 
				cardRequest.getSalary(), 
				cardRequest.getSalary(), 
				cardRequest.getPassion());
	}
	
}
