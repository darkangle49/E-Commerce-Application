package com.userservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.userservice.model.User;
import com.userservice.model.UserRes;
import com.userservice.payload.MessageResponse;
import com.userservice.payload.SignupRequest;
import com.userservice.service.UserService;







@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RestTemplate template;
	
	ResponseEntity<String> authenticationResponse ;
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String logintoMCs(@RequestParam String username,@RequestParam String password)
	{
		String AUTHENTICATION_URL="http://localhost:8081/api/auth/signin";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("username", username);
		map.add("password", password);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		//ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
		
		 authenticationResponse = template.exchange(AUTHENTICATION_URL,
				HttpMethod.POST, request, String.class);
		if (authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
			{
				//String token = "Bearer " + authenticationResponse.getBody();
				
				return "Login Successfull and Got Token";
			}
			
		return "Invalid credential";
	}
	@PostMapping("/add")
	public ResponseEntity<User> save(@RequestBody User user)
	{
		try {
	
	      User u1 = userService.addUser(user);
	      return new ResponseEntity<>(u1, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@GetMapping("/")
	public ResponseEntity<?> details()
	{if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
	{
		String token = "Bearer " + authenticationResponse.getBody();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		String CLIENT_URL="http://localhost:8081/api/users/user";
		
		HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
		User user = template.exchange(CLIENT_URL, HttpMethod.GET, jwtEntity,
				User.class).getBody();
		
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	return ResponseEntity.ok(new MessageResponse("please login"));
	
	
	}
	@GetMapping("/all")
	public ResponseEntity<?> allUsers(){
	
				
			if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
			{
				String token = "Bearer " + authenticationResponse.getBody();
				
				HttpHeaders headers = new HttpHeaders();
				headers.set("Authorization", token);
				String url="http://localhost:8081/api/users/admin";
				
				HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
				ResponseEntity<List<UserRes>> responseEntity = 
						  template.exchange(
						    url,
						    HttpMethod.GET,
						    jwtEntity,
						    new ParameterizedTypeReference<List<UserRes>>() {}
						  );
						List<UserRes> users = responseEntity.getBody();
						return (ResponseEntity<?>) users.stream()
						  .map(UserRes::getUsername)
						  .collect(Collectors.toList());
				
				
				
			}
			return ResponseEntity.ok(new MessageResponse("Admin login is required"));

		
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(@RequestBody SignupRequest sign)
	{
		String url="http://localhost:8081/api/auth/signup";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
	

		 
	
		 HttpEntity<SignupRequest> entity = new HttpEntity<SignupRequest>(sign, headers);

			
				
				return template.exchange(
				         url, HttpMethod.POST, entity, String.class).getBody();
		
		
			
			
				
				
			
	
	}
	
}
