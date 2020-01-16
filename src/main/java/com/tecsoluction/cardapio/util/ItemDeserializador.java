package com.tecsoluction.cardapio.util;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.tecsoluction.cardapio.entidade.Item;


public class ItemDeserializador  extends KeyDeserializer {

	
	
	
	@Override
	public Item deserializeKey(String key, DeserializationContext ctxt) throws IOException,JsonProcessingException  {
		// TODO Auto-generated method stub
		
		Item it = new Item();
		it.setId(UUID.fromString(key));
		
		return it;
	}
	
	
	

}
