package com.dppware.wekaExamplesApplication.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dppware.wekaExamplesApplication.bean.Model;
import com.dppware.wekaExamplesApplication.bean.Prototype;
import com.dppware.wekaExamplesApplication.bean.Tenant;
import com.dppware.wekaExamplesApplication.service.ModelService;
import com.dppware.wekaExamplesApplication.service.PrototypeService;
import com.dppware.wekaExamplesApplication.service.TenantService;

import lombok.extern.slf4j.Slf4j;

/**
 * Multitenant c
 * @author dpena
 *
 */
@RestController
@RequestMapping("tenant")
@Slf4j
public class TenantController {

	@Autowired
	private TenantService tenantService;
	@Autowired
	private PrototypeService prototypeService;
	@Autowired
	private ModelService modelService;
	
	
	@GetMapping(value = "/examples")
    @ResponseBody
    public Map<String,String> examples() {
		Map<String,String> prototypes = new HashMap<String,String>();
		prototypes.put("id", "w+");
		prototypes.put("name", "juan|luis|mariano");
		prototypes.put("edad", "\\d");
		prototypes.put("edad", "yes|no");
        return prototypes;
    }
    
	
	/**
     * Create a tenant
     * @param person
	 * @throws Exception 
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Tenant createTenant(@RequestBody Tenant tenant) throws Exception {
    	return tenantService.create(tenant);
    }
    
    /**
     * Get all tenant
     * @param person
	 * @throws Exception 
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Collection<Tenant> getTenants() throws Exception {
    	return tenantService.getAllTenants();
    }
    
    /**
     * Delete a tenant
     * @param person
	 * @throws Exception 
     */
    @PostMapping(value="{id}" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deleteTenant(@PathVariable("tenantId") String tenantId) throws Exception {
    	tenantService.delete(tenantId);
    }
	
	/**
	 * Return the prototype definition of a tenant account
	 * @param tenantId
	 * @return
	 */
    @GetMapping(value = "{id}/prototype")
    @ResponseBody
    public List<Prototype> getTenantPrototypes(@PathVariable("id") String tenantId) {
        return prototypeService.getTenantPrototypes(tenantId);
    }
    
    /**
     * Create a prototype of a tenant
     * @param person
     * @throws IOException
     */
    @PostMapping(value = "/{id}/prototype", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Prototype createPrototype(@PathVariable("id") String tenantId, @RequestBody Map<String,String> prototypeInfo) throws IOException {
    	return prototypeService.saveTenantPrototype(tenantId, new Prototype().setAttributes(prototypeInfo));
    }
    
    
    /**
     * Get a prototype that already exist on tenant account
     * @param person
     * @throws Exception 
     */
    @GetMapping(value = "{tenantId}/prototype/{prototypeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Prototype getTenantPrototype(@PathVariable("tenantId") String tenantId,@PathVariable("prototypeId") String prototypeId) throws Exception {
    	return prototypeService.getTenantPrototype(tenantId, new Prototype().setId(prototypeId));
    }
    
    /**
     * Update a prototype that already exist on tenant account
     * @param person
     * @throws Exception 
     */
    @PutMapping(value = "{tenantId}/prototype/{prototypeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Prototype udpatePrototype(@PathVariable("tenantId") String tenantId,@PathVariable("prototypeId") String prototypeId, @RequestBody Map<String,String> prototypeInfo) throws Exception {
    	//TODO: Verify prototypeId owns to tenant
    	return prototypeService.updateTenantPrototype(tenantId, prototypeId, new Prototype().setId(prototypeId).setAttributes(prototypeInfo));
    }
    
    
    
    
    
    /**
	 * Return the models of a prototype definition of a tenant account
	 * @param tenantId
	 * @return
	 */
    @GetMapping(value = "{id}/prototype/{prototypeId}/model")
    @ResponseBody
    public List<Model> getPrototypeModles(@PathVariable("id") String tenantId,@PathVariable("prototypeId") String prototypeId) {
        return modelService.getPrototypeModels(new Prototype().setId(prototypeId));
    }
    
    /**
     * Create a model for the passed PrototypeId
     * @param person
     * @throws Exception 
     */
    @PostMapping(value = "{id}/prototype/{prototypeId}/model", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Model createPrototypeModel(@PathVariable("id") String tenantId,@PathVariable("prototypeId") String prototypeId, @RequestBody Map<String,String> modelInfo) throws Exception {
    	//TODO: Verify prototypeId owns to tenant
    	return modelService.saveModel(new Prototype().setId(prototypeId), new Model().setAttributes(modelInfo));
    }

   
    
}
