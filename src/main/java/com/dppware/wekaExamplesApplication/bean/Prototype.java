package com.dppware.wekaExamplesApplication.bean;

import java.util.HashMap;
import java.util.Map;

import com.dppware.wekaExamplesApplication.bean.Tenant.TenantBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Prototype {

	private String id;
	
	private Map<String,String> attributes = new HashMap<String,String>();

	public String getId() {
		return id;
	}

	public Prototype setId(String id) {
		this.id = id;
		return this;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public Prototype setAttributes(Map<String, String> attributes) {
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
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.id.hashCode();
	}
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		String value = null;
		try {
			value = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
}
