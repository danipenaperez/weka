package com.dppware.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.dppware.wekaExamplesApplication.bean.Model;
import com.dppware.wekaExamplesApplication.bean.Prototype;
import com.dppware.wekaExamplesApplication.bean.Tenant;
import com.dppware.wekaExamplesApplication.bean.TenantWrapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {WekaExamplesApplication.class})
@ActiveProfiles("test")
@Slf4j
public class PrototypeTest {

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
		 * Create Tenant
		 */
		Tenant tenant = Tenant.builder().email("tenant1@tenant.com").build();
		log.info("POST to "+ PATHS.get("tenant") );
		ResponseEntity<Tenant> response = testRestTemplate.postForEntity(PATHS.get("tenant") ,tenant,  Tenant.class);
		Assert.isTrue(response.getStatusCode() == HttpStatus.CREATED);
		log.info(response.toString());
		tenant = response.getBody();
		
		/**
		 * Get Prototypes
		 */
		log.info("GET from to "+ String.format(PATHS.get("prototype"),tenant.getId()));
		ResponseEntity<List<Prototype>> responsePrototypes = testRestTemplate.exchange(String.format(PATHS.get("prototype"),tenant.getId()),  HttpMethod.GET, null, new ParameterizedTypeReference<List<Prototype>>() {});
		Assert.isTrue(responsePrototypes.getStatusCode() == HttpStatus.OK);
		log.info(responsePrototypes.toString());
		Assert.isNull(responsePrototypes.getBody());
		
		/**
		 * Create Prototype
		 * 	@attribute genre {man,woman}
			@attribute hairColour {blonde,red,brown,nohair}
			@attribute isSpanish {false,true}
			@attribute profession {actor,sport,tv,politician,programmer}
			@attribute class {false,true,quizas}
		 */
		Map<String,String> prototypeInfo= new HashMap<String,String>(){{
			this.put("genre", "man|woman");
			this.put("hairColour", "blonde|red|brown|nohair");
			this.put("isSpanish", "true|false");
			this.put("profession", "actor|sport|tv|politician|programmer");
		}};
		HttpEntity<?> body = new HttpEntity<>(prototypeInfo, null);
		log.info("POST to "+ String.format(PATHS.get("prototype"),tenant.getId()));
		log.info(body.toString());
		ResponseEntity<Prototype> responseCreatePrototype = testRestTemplate.exchange(String.format(PATHS.get("prototype"),tenant.getId()),  HttpMethod.POST, body, Prototype.class);
		log.info(responseCreatePrototype.toString());
		Assert.notNull(responseCreatePrototype.getBody());
		Prototype prototypeCreated= responseCreatePrototype.getBody();
		Assert.notNull(prototypeCreated.getId());
		
		/**
		 * Fetch Tentant Prototypes
		 */
		log.info("GET from to "+ String.format(PATHS.get("prototype"),tenant.getId()));
		responsePrototypes = testRestTemplate.exchange(String.format(PATHS.get("prototype"),tenant.getId()),  HttpMethod.GET, null, new ParameterizedTypeReference<List<Prototype>>() {});
		Assert.isTrue(responsePrototypes.getStatusCode() == HttpStatus.OK);
		log.info(responsePrototypes.toString());
		Assert.notNull(responsePrototypes.getBody());
		
		
		/**
		 * Create Model for the created Prototype
		 * 	@attribute genre {man,woman}
			@attribute hairColour {blonde,red,brown,nohair}
			@attribute isSpanish {false,true}
			@attribute profession {actor,sport,tv,politician,programmer}
			@attribute class {false,true,quizas}
		 */
		if(false) {
		Map<String,String> modelInfo= new HashMap<String,String>(){{
			this.put("genre", "man");
			this.put("hairColour", "red");
			this.put("isSpanish", "false");
			this.put("profession", "actor");
			//additionals
			this.put("name", "Pelirrojete");
			this.put("imgUrl", "http://localhost:8080/images/1");
		}};
		
		HttpEntity<?> bodyModel = new HttpEntity<>(modelInfo, null);
		log.info("POST to "+ String.format(PATHS.get("model"),tenant.getId(),prototypeCreated.getId()));
		log.info(bodyModel.toString());
		ResponseEntity<Model> responseCreateModel = testRestTemplate.exchange(String.format(PATHS.get("model"),tenant.getId(),prototypeCreated.getId()),  HttpMethod.POST, bodyModel, Model.class);
		log.info(responseCreateModel.getBody().toString());
		Assert.notNull(responseCreateModel.getBody().getAttributes());
		
		log.info("GET from "+String.format(PATHS.get("model"),tenant.getId(),prototypeCreated.getId()) );
		ResponseEntity<List<Model>> responseModels = testRestTemplate.exchange(String.format(PATHS.get("model"),tenant.getId(),prototypeCreated.getId()),  HttpMethod.GET, null, new ParameterizedTypeReference<List<Model>>() {});
		Assert.isTrue(responseModels.getStatusCode() == HttpStatus.OK);
		log.info(responseModels.getBody().toString());
		
		
		//download all info
		//ResponseEntity<List<Prototype>> responsePrototypes = testRestTemplate.exchange(String.format(PATHS.get("prototype"),tenant.getId()),  HttpMethod.GET, null, new ParameterizedTypeReference<List<Prototype>>() {});
		log.info("GET FROM "+ String.format(PATHS.get("administration")));
		ResponseEntity<TenantWrapper> tenantWrapperInfo = testRestTemplate.exchange(String.format(PATHS.get("administration")),  HttpMethod.GET, null, TenantWrapper.class);
		System.out.println(tenantWrapperInfo.getBody());
		}
		
	}
	
	@Test
	public void testModels() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//		
//		File f = new File("./data/person_attrs_new.arff");
//		System.out.println(f.exists());
//		body.add("file", new File("./data/person_attrs_new.arff"));
//		
//		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//		 
//		String serverUrl = "http://localhost:8080/administration";
//		 
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> response = restTemplate .postForEntity(serverUrl, requestEntity, String.class);
//		System.out.println(response);
	}
	
	
	
}
