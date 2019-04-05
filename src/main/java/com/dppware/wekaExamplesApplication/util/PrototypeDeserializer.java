package com.dppware.wekaExamplesApplication.util;

import java.io.IOException;

import com.dppware.wekaExamplesApplication.bean.Prototype;
import com.dppware.wekaExamplesApplication.bean.TenantWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PrototypeDeserializer extends KeyDeserializer {
	 
  @Override
  public Prototype deserializeKey (String key,  DeserializationContext ctxt) throws IOException,   JsonProcessingException {
       System.out.println("aqui");
       ObjectMapper objectMapper = new ObjectMapper();
       
      return  objectMapper.readValue(key, Prototype.class);
    }
}