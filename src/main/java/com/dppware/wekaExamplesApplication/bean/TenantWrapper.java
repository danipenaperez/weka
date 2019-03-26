package com.dppware.wekaExamplesApplication.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @ToString
public class TenantWrapper {

	
	private Tenant tenant;
	private Map<Prototype,List<Model>> prototypeModels = new HashMap<Prototype,List<Model>>();
	
	
	
	
}
