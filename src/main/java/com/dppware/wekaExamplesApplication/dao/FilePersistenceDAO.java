package com.dppware.wekaExamplesApplication.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dppware.wekaExamplesApplication.bean.Model;
import com.dppware.wekaExamplesApplication.bean.Prototype;
import com.dppware.wekaExamplesApplication.bean.Tenant;
import com.dppware.wekaExamplesApplication.bean.TenantWrapper;
import com.dppware.wekaExamplesApplication.service.ModelService;
import com.dppware.wekaExamplesApplication.service.PrototypeService;
import com.dppware.wekaExamplesApplication.service.TenantService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * Persist all info into files or initialize info from files on startup and shutdown app  
 * @author dpena
 *
 */
@Repository
@Slf4j
public class FilePersistenceDAO {

	String directory="./download_tmp";
	
	
	@Autowired
	private TenantService tenantService;
	@Autowired
	private PrototypeService prototypeService;
	@Autowired
	private ModelService modelService;
	
	boolean initialized = false;
	
	/**
	 * Load info from files
	 * @throws IOException 
	 */
	@PostConstruct
	public void initializeFromFiles() throws IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		File folder = new File(directory);
		for (final File fileEntry : folder.listFiles()) {
			Path file = Paths.get(fileEntry.toURI());
			byte[] fileBytes = Files.readAllBytes(file);
			String data = new String(fileBytes);
			
			TenantWrapper wrapper = objectMapper.readValue(data, TenantWrapper.class);
			
			
	    	tenantService.getAllTenants().add(wrapper.getTenant());
	    	for(Entry<Prototype, List<Model>> entry: wrapper.getPrototypeModels().entrySet()) {
	    		try {
		    		prototypeService.getTenantPrototype(wrapper.getTenant().getId(), entry.getKey());
		    		prototypeService.getPrototypes().put(entry.getKey().getId(), entry.getKey());
		    		
		    		modelService.getPrototypeModelsMap().put(entry.getKey(), entry.getValue());
	    		}catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    	}
	    	
	    	
			
	    }
		initialized=true;
		
		 
	}
	
	

	
	
	
	
	
	/**
	 * Save current info on files
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@PreDestroy
	public void persistToFiles() throws JsonGenerationException, JsonMappingException, IOException {
		if(initialized) {
			TenantWrapper wrapper = new TenantWrapper();
	    	
	    	Tenant t = tenantService.getAllTenants().get(0);
	    	wrapper.setTenant(t);
	    	
	    	List<Prototype> tenantPrototypes = prototypeService.getTenantPrototypes(t.getId());
	    	if(tenantPrototypes!=null && !tenantPrototypes.isEmpty()) {
	    		for(Prototype pro:tenantPrototypes) {
	    			List<Model> prototypeModels = modelService.getPrototypeModels(pro);
	    			if(prototypeModels!=null) {
	    				wrapper.getPrototypeModels().put(pro, prototypeModels);
	    			}
	    		}
	    	}
	    	String fileName = "./download_tmp/"+t.getId()+".dat";
	    	/**
	    	String content = new Gson().toJson(wrapper);
	    	System.out.println(content);
	    	
	    	Path path = Paths.get(fileName);
	    	Files.write(path, content.getBytes());
	    	**/
	    	
	    	
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	objectMapper.writeValue(new File(fileName), wrapper);
    	}
	}
	
}
