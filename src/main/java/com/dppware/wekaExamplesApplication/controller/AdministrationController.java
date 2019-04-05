package com.dppware.wekaExamplesApplication.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dppware.wekaExamplesApplication.bean.Prototype;
import com.dppware.wekaExamplesApplication.bean.Tenant;
import com.dppware.wekaExamplesApplication.bean.TenantWrapper;
import com.dppware.wekaExamplesApplication.service.ModelService;
import com.dppware.wekaExamplesApplication.service.PrototypeService;
import com.dppware.wekaExamplesApplication.service.TenantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * Multitenant c
 * @author dpena
 *
 */
@RestController
@RequestMapping("administration")
@Slf4j
public class AdministrationController {

	@Autowired
	private TenantService tenantService;
	@Autowired
	private PrototypeService prototypeService;
	@Autowired
	private ModelService modelService;
	
	
	/**
     * Create a tenant
     * @param person
	 * @throws Exception 
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadTenantInfo(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
    	return "todomuybien";
    }
    
    
    
    
    
    
    @GetMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String download() throws Exception {
    	TenantWrapper wrapper = new TenantWrapper();
    	
    	Tenant t = tenantService.getAllTenants().get(0);
    	wrapper.setTenant(t);
    	
    	for(Prototype pro:prototypeService.getTenantPrototypes(t.getId())) {
    		wrapper.getPrototypeModels().put(pro, modelService.getPrototypeModels(pro));
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
       	
    	return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);
    }
}

