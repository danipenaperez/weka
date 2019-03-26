package com.dppware.wekaExamplesApplication.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dppware.wekaExamplesApplication.bean.Tenant;

@Service
public class TenantService {

	private Map<String,Tenant> tenants = new HashMap<String,Tenant>() ;
	
	/**
	 * Create a tenant
	 * @param tenant
	 * @return
	 * @throws Exception
	 */
	public Tenant create(Tenant tenant) throws Exception {
		if(tenants.containsKey(tenant.getEmail())) {
			throw new Exception ("The email is already registered");
		}
		tenant.setId(UUID.randomUUID().toString());
		tenants.put(tenant.getId(), tenant);
		return tenant;
	}
	
	/**
	 * Create a tenant
	 * @param tenant
	 * @return
	 * @throws Exception
	 */
	public void delete(String tenantId) throws Exception {
		tenants.remove(tenantId);
	}
	
	
	
	
	public List<Tenant> getAllTenants() {
		//TODO:securize
		return new ArrayList(tenants.values());
	}
}
