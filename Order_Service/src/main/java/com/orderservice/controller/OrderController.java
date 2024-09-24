package com.orderservice.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.orderservice.model.Address;
import com.orderservice.model.Cart;
import com.orderservice.model.Order;
import com.orderservice.model.OrderProduct;
import com.orderservice.model.User;
import com.orderservice.rabbitmq.MQConfig;
import com.orderservice.rabbitmq.MessageResponse;
import com.orderservice.repository.CartRepository;
import com.orderservice.repository.OrderRepository;
import com.orderservice.service.OrderService;

import java.util.Date;


@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderRepository os;
	@Autowired
	private RestTemplate rt;
	@Autowired
	private OrderService service;
	@Autowired
	private CartRepository cartrepo;
	@Autowired
	RabbitTemplate template;
	
ResponseEntity<String> authenticationResponse ;

String name;
	
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

		name=username;
		
		 authenticationResponse = rt.exchange(AUTHENTICATION_URL,
				HttpMethod.POST, request, String.class);
		if (authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
			{
				//String token = "Bearer " + authenticationResponse.getBody();
				
				return "Login Successfull and Got Token";
			}
			
		return "Invalid credential";
	}
	
	
	@PostMapping("/{name}")
	public String placeorder(@PathVariable String name,@RequestBody Address address) {
		
		if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
		{
			String token = "Bearer " + authenticationResponse.getBody();
			
			HttpHeaders headers2 = new HttpHeaders();
			headers2.set("Authorization", token);
		
			if(address.getStreet().equals(null))
			{
				throw new RuntimeException("address is required");
			}
			
		
		
		
		
		
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <OrderProduct> entity = new HttpEntity<OrderProduct>(headers);
		OrderProduct p=rt.exchange(
		         "http://localhost:8082/catalog/product/"+name, HttpMethod.GET, entity, OrderProduct.class).getBody();
		
		
		if(p==null)
			{return "product is out of stock";}
		else
			
		{ZoneId defaultZoneId = ZoneId.systemDefault();
		
		Date date = new Date();
	Instant instant = date.toInstant();
		
	
	LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
	Order order=new Order();
	order.setOrderProducts(p);
	order.setDateCreated(localDate);
	order.setStatus(new Random().nextBoolean()?"success":"pending");
	HttpHeaders headers3 = new HttpHeaders();
	headers3.set("Authorization", token);
	String CLIENT_URL="http://localhost:8081/api/users/user";
	
	HttpEntity<String> jwtEntity = new HttpEntity<String>(headers3);
	User user = rt.exchange(CLIENT_URL, HttpMethod.GET, jwtEntity,
			User.class).getBody();
	
	order.setUsername(user.getUsername());
	order.setEmail(user.getEmail());
    
    Address ad1=new Address(address.getStreet(),address.getPincode(),address.getCity(),address.getState());

	order.setAddress(ad1);
	
	
	if(order.getStatus().equals("success"))
	{
		os.insert(order);	
		
		String message="Order  placed with username"+order.getUsername();
		template.convertAndSend(MQConfig.EXCHANGE,MQConfig.ROUTING_KEY,message);
		
        return "Order Place Successfully";
        
        
		
	}
	
	else
	{
		Cart c1=new Cart();
		c1.setUsername(user.getUsername());
		c1.setEmail(user.getEmail());
		c1.setStatus("Payment Pending");
		c1.setProduct(p);
		cartrepo.insert(c1);
	
	
		
			
           return "Payment is expected  added to cart";}}
		
		}
		return "Login is Required";
		}

	@GetMapping("/OrderDetails")
    public ResponseEntity<?> productsGet() {

		if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
		{
			String token = "Bearer " + authenticationResponse.getBody();
			
			HttpHeaders headers2 = new HttpHeaders();
			headers2.set("Authorization", token);
		List<Order> products = service.findByusername(name);
		
		
		
		
		if(products.isEmpty())
		{
			return ResponseEntity.ok(new MessageResponse("No orders are present"));
		}
		
		else {
		return new ResponseEntity<List<Order>>(products,HttpStatus.OK);}
	}
		else
			return ResponseEntity.ok(new MessageResponse("Login is Required"));
	
	}
	@GetMapping("/cart")
	public ResponseEntity<?> cart() {

		if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
		{
			String token = "Bearer " + authenticationResponse.getBody();
			
			HttpHeaders headers2 = new HttpHeaders();
			headers2.set("Authorization", token);
		List<Cart> products = cartrepo.findByusername(name);
		if(products.isEmpty())
		{
			return ResponseEntity.ok(new MessageResponse("Cart is empty"));
		}
		
		else {
		return new ResponseEntity<List<Cart>>(products,HttpStatus.OK);}
	}
		else
			return ResponseEntity.ok(new MessageResponse("Login is Required"));
	
	}
	@DeleteMapping("/deletecart/{id}")
	public String removeFromCart(@PathVariable String id)
	{if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
	{
		String token = "Bearer " + authenticationResponse.getBody();
		
		HttpHeaders headers2 = new HttpHeaders();
		headers2.set("Authorization", token);
		cartrepo.deleteByid(id);
		return "Product is deleted from cart";
	}
	else
		return "Please Login";
		
	}
	@DeleteMapping("/deleteorder/{id}")
	public String cancelOrder(@PathVariable String id,@RequestParam String reason)
	{
		
		
		if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
		{
			String token = "Bearer " + authenticationResponse.getBody();
			
			HttpHeaders headers2 = new HttpHeaders();
			headers2.set("Authorization", token);
		if(reason.equals("accept"))
		{
		service.deleteByidn(id);
		
		return "Order is deleted";
		}
		else
		{
			return "please share the reason";
		}
		}
		return "Login is required";
	}
 @PostMapping("/orderFromCart")
  public String addToOrders(@RequestParam String id,@RequestBody Address address) {
	 
	 
	 if (authenticationResponse !=null && authenticationResponse.getStatusCode().equals(HttpStatus.OK)) 
		{
			String token = "Bearer " + authenticationResponse.getBody();
			
			HttpHeaders headers2 = new HttpHeaders();
			headers2.set("Authorization", token);
	 Cart car=cartrepo.findByid(id);
	 
	 if(car==null)
	 {
		 return "no product is found in cart"; 
	 }
	 else
		 {Order order=new Order();
		 order.setUsername(car.getUsername());
		 order.setEmail(car.getEmail());
		 ZoneId defaultZoneId = ZoneId.systemDefault();
			
			Date date = new Date();
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
	 
	   order.setDateCreated(localDate);
        order.setStatus("success");
        order.setOrderProducts(car.getProduct());
        order.setAddress(address);
        cartrepo.deleteByid(id);
        
        return "Order created successfull";
		
	   
		

		 }
		}
	 return "Please login";
}
}

