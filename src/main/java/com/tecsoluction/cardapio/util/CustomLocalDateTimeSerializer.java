package com.tecsoluction.cardapio.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomLocalDateTimeSerializer extends StdSerializer<Date> {
	
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat  formatter = 
			new SimpleDateFormat("HH:mm:ss");
	 
	 
	 public CustomLocalDateTimeSerializer() {
	        this(null);
	    }
	  
	    public CustomLocalDateTimeSerializer(Class<Date> t) {
	        super(t);
	    }

		@Override
		public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {

			gen.writeString(formatter.format(value));
			
			
			
		}

}
