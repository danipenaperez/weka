package com.dppware.wekaExamplesApplication.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dppware.wekaExamplesApplication.bean.Prototype;
import com.dppware.wekaExamplesApplication.dao.FilePersistenceDAO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Manage all prototype of all Tenant
 * @author dpena
 *
 */
@Service
public class PrototypeService {

	public static final String PROTOTYPE_ID_KEY = "prototypeId";
	
	private Map<String,List<Prototype>> tenantPrototypes = new HashMap<String,List<Prototype>>();
	
	private Map<String, Prototype> prototypes = new HashMap<String,Prototype>();
	
	@Autowired
	private FilePersistenceDAO persistence;
	
	
	/**
	 * Return the prototypes list for this tenant
	 * @param tenantId
	 * @return
	 */
	public List<Prototype> getTenantPrototypes(String tenantId){
		return tenantPrototypes.get(tenantId);
	}
	
	/**
	 * Return the prototype object created for this tenant
	 * @param tenantId
	 * @return
	 * @throws Exception 
	 */
	public Prototype getTenantPrototype(String tenantId, Prototype toSearch) throws Exception{
		if(!tenantPrototypes.get(tenantId).contains(toSearch)) {
			throw new Exception(String.format("PrototypeID %s not exist for this tenant.", toSearch.getId()));
		}
		
		return prototypes.get(tenantId);
	}
	
	/**
	 * Return the prototype object by id
	 * @param tenantId
	 * @return
	 * @throws Exception 
	 */
	public Prototype getPrototypeById(String prototypeId) throws Exception{
		return prototypes.get(prototypeId);
	}
	
	/**
	 * Return the prototype object created for this tenant
	 * @param tenantId
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public Prototype saveTenantPrototype(String tenantId, Prototype prototypeInfo) throws JsonGenerationException, JsonMappingException, IOException{
		prototypeInfo.setId(UUID.randomUUID().toString() );
		prototypes.put(prototypeInfo.getId(), prototypeInfo);
		//asociate to tenant
		List<Prototype> tenantPrototypeIds = tenantPrototypes.get(tenantId);
		if(tenantPrototypeIds== null) {
			tenantPrototypeIds = new ArrayList<Prototype>();
			tenantPrototypes.put(tenantId, tenantPrototypeIds);
		}
		tenantPrototypeIds.add(prototypeInfo);
		
		persistence.persistToFiles();
		
		return prototypeInfo;
	}
	
	/**
	 * Return the prototypeInfo associate to prototypeId
	 * @param prototypeId
	 * @param prototypeInfo
	 * @return
	 * @throws Exception
	 */
	public Prototype updateTenantPrototype(String tenantId, String prototypeId, Prototype prototypeInfo) throws Exception {
		if(!tenantPrototypes.get(tenantId).contains(prototypeInfo)) {
			throw new Exception(String.format("PrototypeID %s not exist for this tenant.", prototypeId));
		}
		prototypes.put(prototypeId, prototypeInfo);
		
		persistence.persistToFiles();
		
		return prototypeInfo;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * TODO securize access
	 * @return
	 */
	
	
	public Map<String, List<Prototype>> getTenantPrototypes() {
		return tenantPrototypes;
	}



	public Map<String, Prototype> getPrototypes() {
		return prototypes;
	}


	
}
