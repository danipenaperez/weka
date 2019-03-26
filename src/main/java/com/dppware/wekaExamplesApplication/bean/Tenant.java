package com.dppware.wekaExamplesApplication.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString @NoArgsConstructor
public class Tenant {
	private String id;
	private String email;
	
	public Tenant(String id, String email) {
		this.id=id;
		this.email=email;
	}
	
}
