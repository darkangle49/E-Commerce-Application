package com.catalog.controller;

import java.math.BigDecimal;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.catalog.model.Category;
import com.catalog.model.MessageResponse;
import com.catalog.model.Product;
import com.catalog.repository.CategoryRepository;
import com.catalog.service.ProductService;









@RestController
@RequestMapping(value = "/catalog")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	CategoryRepository catRepo;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/products")
	public ResponseEntity<List<Product>> productsGet() {

		
		List<Product> products = productService.findAll();
		
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/product/{name}")
	public ResponseEntity<?> findByname(@PathVariable  String name) {

	
	
		Product product = productService.findbyName(name);
		
		
		
		if(product.getName().isEmpty())
		{
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("product not found"));
		}
		else
		{
		
		 
		
		return new ResponseEntity<Product>(product,HttpStatus.OK);}
	}
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/categories/{category}")
	
	public ResponseEntity<List<Product>> category(@PathVariable String category)
	{
List<Product> products = productService.findByCategory(category);
		
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	
	@GetMapping(value = "/range/")
	public ResponseEntity<List<Product>> range(@RequestParam BigDecimal max,@RequestParam BigDecimal min){
List<Product> product2 = productService.findByrange(max,min);

boolean val=product2.isEmpty();
if(val==true)
{

	throw new RuntimeException("no products found");

}
else
 return new ResponseEntity<List<Product>>(product2, HttpStatus.OK);
	}
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/productreg/{p}")
	public ResponseEntity<?> productsbyreg(@PathVariable  String p) 
	{
		List<Product> product = productService.findByreg(p);
		if(product.isEmpty())
		{
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("product not found"));
					
		}
		return new ResponseEntity<List<Product>>(product,HttpStatus.OK);
		
	}
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
		RestTemplate restTemplate = new RestTemplate();
		 authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL,
				HttpMethod.POST, request, String.class);
		if (authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
			{
				//String token = "Bearer " + authenticationResponse.getBody();
				
				return "Login Successfull and Got Token";
			}
			
		return "Invalid credential";
	}
	
	@PostMapping("/add")
	public String save(@RequestBody Product p1)
	{
		if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
		{
			String token = "Bearer " + authenticationResponse.getBody();
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
		
		  productService.addProduct(p1);
		  return "product is added";}
		else
			return "Admin login is required";
}
	@DeleteMapping("/delete/{name}")
	public String delete(@PathVariable String name) {
		
		if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
		{
			String token = "Bearer " + authenticationResponse.getBody();
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
		productService.deletebyname(name);
		return "product is deleted";
		}
		else
			return "Admin login is required";
	}
	@GetMapping
	public List<Category> categories()
	{
		List<Category> cat=catRepo.findAll();
		return cat;
	}
}