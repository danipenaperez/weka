package com.dppware.wekaExamplesApplication.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dppware.wekaExamplesApplication.bean.Model;
import com.dppware.wekaExamplesApplication.bean.Prototype;
import com.dppware.wekaExamplesApplication.service.ModelService;

import lombok.extern.slf4j.Slf4j;

/**
 * Multitenant c
 * @author dpena
 *
 */
@RestController
@RequestMapping("play")
@Slf4j
public class PlayController {

	private final String SESSION_ID_HEADER_NAME = "sessionId";
	private final String ALREADY_SHOW_MODELS_LIST_KEY = "alreadyShowModelsListKey";
	
	@Autowired
	private ModelService modelService;
	
	private Map<String,Map<String,Object>> sessionObjects= new HashMap<String,Map<String,Object>>();
	
	
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> start(@RequestBody String email) {
    	HttpHeaders responseHeaders = new HttpHeaders();
    	String sessionId = UUID.randomUUID().toString();
    	sessionObjects.put(sessionId, new HashMap<String,Object>());
    	sessionObjects.get(sessionId).put("created", new Date());
    	sessionObjects.get(sessionId).put("email", "email");
        responseHeaders.set(SESSION_ID_HEADER_NAME,sessionId);
        return ResponseEntity.ok().headers(responseHeaders).body(sessionId);

    }
	
	/**
     * Create a model for the passed PrototypeId
     * @param person
     * @throws Exception 
     */
    @GetMapping(value = "/prototype/{prototypeId}/model", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Model> getModels(
    		@RequestHeader(name = SESSION_ID_HEADER_NAME, required = false, defaultValue="nolose") String sessionId,
    		@PathVariable("prototypeId") String prototypeId,
    		@RequestParam(value="mode", defaultValue="random") String mode,
    		@RequestParam(value="size", defaultValue="3") int size) throws Exception {

    	List<Model> alreadyShownOnThisSession = (List<Model>) sessionObjects.get(sessionId).get(ALREADY_SHOW_MODELS_LIST_KEY);
    	List<String> nonAcceptedIs = new ArrayList<String>();
    	for(Model model:alreadyShownOnThisSession) {
    		nonAcceptedIs.add(model.getId());
    	}
    	return modelService.getRandom(new Prototype().setId(prototypeId), size, nonAcceptedIs);
    }
    
   
}
