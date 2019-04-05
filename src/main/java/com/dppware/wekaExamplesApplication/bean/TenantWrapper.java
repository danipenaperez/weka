package com.dppware.wekaExamplesApplication.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dppware.wekaExamplesApplication.util.PrototypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class TenantWrapper {

	
	private Tenant tenant;
	@JsonDeserialize(keyUsing = PrototypeDeserializer.class)
	private Map<Prototype,List<Model>> prototypeModels = new HashMap<Prototype,List<Model>>();
	
	
	
	
}
