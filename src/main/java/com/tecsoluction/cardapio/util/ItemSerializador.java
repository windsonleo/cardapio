package com.tecsoluction.cardapio.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tecsoluction.cardapio.entidade.Item;




public class ItemSerializador  extends JsonSerializer<Item> {

    private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public void serialize(Item value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, org.codehaus.jackson.JsonProcessingException {

		 jgen.writeFieldName(mapper.writeValueAsString(value));
		
		
	}



   
}

