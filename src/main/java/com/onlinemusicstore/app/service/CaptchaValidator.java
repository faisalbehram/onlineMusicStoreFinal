package com.onlinemusicstore.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.onlinemusicstore.app.models.CaptchaResponse;

@Service
public class CaptchaValidator {


	  @Value("${google.recaptcha.secret}") 
	  String recaptchaSecret;
	   
	  private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
	    "https://www.google.com/recaptcha/api/siteverify";
	   
	  @Autowired
	  RestTemplateBuilder restTemplateBuilder;
	 
	  @Autowired
	  RestTemplate restTemplate;
	  
	  public String verifyRecaptcha(String ip, String recaptchaResponse){
	    Map<String, String> body = new HashMap<>();
	    body.put("secret", recaptchaSecret);
	    body.put("response", recaptchaResponse);
	    body.put("remoteip", ip);
	   
	    String params = "\"?secret={6Ld_WdYUAAAAAIdDud904TQGINF5P0IzbRAWoEAK}&response="+recaptchaResponse;
	    CaptchaResponse captchaResponse = restTemplate.exchange(GOOGLE_RECAPTCHA_VERIFY_URL+params, HttpMethod.POST,  null, CaptchaResponse.class).getBody();
	    
	    
	    ResponseEntity<Map> recaptchaResponseEntity = 
	      restTemplateBuilder.build()
	        .postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL+
	          "?secret={6Ld_WdYUAAAAAIdDud904TQGINF5P0IzbRAWoEAK}&response={response}&remoteip={remoteip}", 
	          body, Map.class, body);
	  
	    Map<String, Object> responseBody = 
	      recaptchaResponseEntity.getBody();
	       
	    boolean recaptchaSucess = (Boolean)responseBody.get("success");
	    if ( !recaptchaSucess) {
	      List<String> errorCodes = 
	        (List)responseBody.get("error-codes");
	       
	      String errorMessage = "error";
	           
	      return errorMessage;
	    }else {
	      return "success";
	    }
	  }
	  
	  
	  public Boolean verfication(String recaptchaResponse) {
		  
		  String params = "?secret={6Ld_WdYUAAAAAIdDud904TQGINF5P0IzbRAWoEAK}&response="+recaptchaResponse;
		    CaptchaResponse captchaResponse = restTemplate.exchange(GOOGLE_RECAPTCHA_VERIFY_URL+params, HttpMethod.POST,  null, CaptchaResponse.class).getBody();
		    if(captchaResponse.getSuccess()) {
		    	return true;
		    }
		    else {
		    	return false;
		    }
		    
	  }
	 }