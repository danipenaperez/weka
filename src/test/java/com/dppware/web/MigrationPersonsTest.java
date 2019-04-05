package com.dppware.web;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.dppware.wekaExamplesApplication.bean.Model;
import com.dppware.wekaExamplesApplication.bean.Person;
import com.dppware.wekaExamplesApplication.bean.Prototype;
import com.dppware.wekaExamplesApplication.bean.Tenant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Slf4j
public class MigrationPersonsTest {


	private final String ROOT = "http://localhost:8080/";
	
	RestTemplate testRestTemplate = new RestTemplate();
	
	//Paths
	private final Map<String,String> PATHS = new HashMap<String,String>(){{
		this.put("tenant", ROOT+ "tenant");
		this.put("prototype", ROOT+"tenant/%s/prototype");
		this.put("model", ROOT+"tenant/%s/prototype/%s/model");
		this.put("administration", ROOT+"administration");
	}};
	
	/**
	 * 1. Create tenant
	 * 2. Create prototype
	 * 3. Create Model
	 * 4. Delete Model
	 * 5. Delete Prototype
	 * 6. Delete tenant
	 */
	@Test
	public void createPrototype() {
		/**
		 * Get All Tenant 
		 */
		
		ResponseEntity<List<Tenant>> responsetenant = testRestTemplate.exchange(String.format(PATHS.get("tenant")),  HttpMethod.GET, null, new ParameterizedTypeReference<List<Tenant>>() {});
		Assert.isTrue(responsetenant.getStatusCode() == HttpStatus.OK);
		log.info(responsetenant.toString());
		
		Tenant tenant = responsetenant.getBody().get(0);
		
		/**
		 * Get tenant prototypes
		 */
		log.info("GET from to "+ String.format(PATHS.get("prototype"),tenant.getId()));
		ResponseEntity<List<Prototype>> responsePrototypes = testRestTemplate.exchange(String.format(PATHS.get("prototype"),tenant.getId()),  HttpMethod.GET, null, new ParameterizedTypeReference<List<Prototype>>() {});
		Assert.isTrue(responsePrototypes.getStatusCode() == HttpStatus.OK);
		log.info(responsePrototypes.toString());
		
		Prototype targetPrototype = responsePrototypes.getBody().get(0);
		/**
		 * Read persons
		 */
		List<Person> inMemoryStore = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			Path path = Paths.get("./data/persons.dat");
	        if (path.toFile().exists()) {
	            String content = String.join("", Files.readAllLines(path));
	            inMemoryStore = mapper.readValue(content, new TypeReference<List<Person>>() {
	            });
	        } else {
	            path.toFile().createNewFile();
	        }
		}catch (Exception e) {
			System.out.println(e);
		}
		
		/**
		 * For each person create model
		 */
		for(Person p: inMemoryStore) {
			Map<String,String> modelInfo= new HashMap<String,String>(){{
				this.put("genre", p.getGenre());
				this.put("hairColour", p.getHairColour());
				this.put("isSpanish", p.getIsSpanish().toString());
				this.put("profession", p.getProfession());
				//additionals
				this.put("name", p.getName());
				this.put("imgUrl", p.getImageURL());
			}};
			HttpEntity<?> bodyModel = new HttpEntity<>(modelInfo, null);
			log.info("POST to "+ String.format(PATHS.get("model"),tenant.getId(),targetPrototype.getId()));
			log.info(bodyModel.toString());
			ResponseEntity<Model> responseCreateModel = testRestTemplate.exchange(String.format(PATHS.get("model"),tenant.getId(),targetPrototype.getId()),  HttpMethod.POST, bodyModel, Model.class);
			log.info(responseCreateModel.getBody().toString());
			
			
			
		}
		
		/**
		 * GET CREATED MODELS
		 */
		log.info("GET from "+String.format(PATHS.get("model"),tenant.getId(),targetPrototype.getId()) );
		ResponseEntity<List<Model>> responseModels = testRestTemplate.exchange(String.format(PATHS.get("model"),tenant.getId(),targetPrototype.getId()),  HttpMethod.GET, null, new ParameterizedTypeReference<List<Model>>() {});
		Assert.isTrue(responseModels.getStatusCode() == HttpStatus.OK);
		log.info(responseModels.getBody().toString());
		
		
	}

	
	


}
