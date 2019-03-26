package com.dppware.wekaExamplesApplication.bean;

import java.util.HashMap;
import java.util.Map;

public class Model {


	private String id;
	
	private Map<String,String> attributes = new HashMap<String,String>();

	public String getId() {
		return id;
	}

	public Model setId(String id) {
		this.id = id;
		return this;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public Model setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
		return this;
	}
	@Override
	public boolean equals(Object obj) {
		boolean eq=false;
		if(obj instanceof Prototype) {
			Prototype p = (Prototype)obj;
			eq= this.getId().equals(p.getId());
		}
		return eq;
	}
	


}
