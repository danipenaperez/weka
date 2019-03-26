package com.dppware.wekaExamplesApplication.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dppware.wekaExamplesApplication.bean.Model;
import com.dppware.wekaExamplesApplication.bean.Prototype;


/**
 * Store Models for the different tenants
 * @author dpena
 *
 */
@Service
public class ModelService {
	
	public static final String MODEL_ID_KEY = "id";
	
	@Autowired
	private PrototypeService prototypeService;
	
	Random r = new Random();
	
	private Map<Prototype, List<Model>> prototypeModelsMap = new HashMap<Prototype, List<Model>>();
	
	/**
	 * Return the models for the passed prototype
	 * @param prototype
	 * @return
	 */
	public List<Model> getPrototypeModels(Prototype prototype){
		return prototypeModelsMap.get(prototype);
	}
	
	/**
	 * Save a model.
	 * Validate againt prottotype
	 * @param prototypeId
	 * @param modelInfo
	 * @return 
	 * @throws Exception 
	 */
	public Model saveModel(Prototype prototype, Model model ) throws Exception {
		
		validate(prototype,model);
		//add Default fields
		model.setId(UUID.randomUUID().toString());
		
		//ensure estructure exists
		List<Model> models = prototypeModelsMap.get(prototype);
		if(models == null) {
			models = new ArrayList<Model>();
			prototypeModelsMap.put(prototype, models);
		}
		
		models.add(model);
		
		return model;
	}

	
	/**
	 * Update a model.
	 * Validate againt prototype
	 * @param prototypeId
	 * @param modelInfo
	 * @return 
	 * @throws Exception 
	 */
	public Model updateModel(Prototype prototype, Model model ) throws Exception {
		//Validations
		if(!prototypeModelsMap.get(prototype).contains(model)) {
			throw new Exception("The model does not exist for the passed prototype");
		}
		validate(prototype,model);
		prototypeModelsMap.get(prototype).add(model);
		return saveModel(prototype,model);
	}
	
	
	/**
	 * Validate a model against a prototype
	 * @param prototype
	 * @param model
	 * @throws Exception 
	 */
	private void validate(Prototype prototype, Model model ) throws Exception {
		//Validations
		Map<String,String> modelInfo = model.getAttributes();
		
		for (Map.Entry<String, String> entry : prototype.getAttributes().entrySet()) {
		    String modelValue= modelInfo.get(entry.getKey());
			if(modelValue == null || ! validate(entry.getValue(),modelValue )) {
		    	throw new Exception(String.format("Not accepted model value for field %s . Value= %s",entry.getKey(), modelValue));
		    }
		}
	}
	
	
    /**
     * Return a random sublist
     * 
     * @param size
     * @param acceptRepeat
     * @return
     * @throws Exception 
     */
    public List<Model> getRandom(Prototype prototypeId, int size, List<String> nonAcceptedIds) throws Exception {
    	List<Model> required = new ArrayList<Model>(); //Temp storage object
    	
    	List<Model> prototypeModels = prototypeModelsMap.get(prototypeId);//get current objects associated with this prototypeId
    	if(prototypeModels.size()< nonAcceptedIds.size()+size) {//iterate over it and verify no repeat
	    	while (required.size()<size) {
	    		int index = r.nextInt((prototypeModels.size() - 1));
	    		Model candidate = prototypeModels.get(index);
	    		if(!nonAcceptedIds.contains(candidate.getId())) {
	    			required.add(candidate);
	    			nonAcceptedIds.add(candidate.getId()); //Update nonAccepted with news to avoid repeats
	    		}
	    	}
    	}else {
    		throw new Exception ("No more models available");
    	}
    	
        
        return required;
    }
	
	
	
	
	private boolean validate(String regExp, String chain) {
	    return Pattern.compile(regExp).matcher(chain).matches();
	}


	public Map<Prototype, List<Model>> getPrototypeModelsMap() {
		return prototypeModelsMap;
	}
}
