package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;

import com.catalog.model.Product;
import com.catalog.repository.ProductRepository;
import com.catalog.service.ProductService;

@SpringBootConfiguration
public class ProductServicetest {
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	ProductRepository productRepository;
	

	
	 ProductServicetest productServiceImplMock = mock(ProductServicetest.class);
	 ProductRepository pr= mock(ProductRepository.class);
	@Test
	
	
	public  void Test1() {
		 Product product = new Product();
	        
	        product.setName("iphone12");
	        product.setInfo("apple product 128gb variant");
	        product.setPrice(new BigDecimal(52999));
	        product.setCategory("Electronics");

	        Mockito.when(productRepository.findByname("iphone12"));
	 
	        Product pro=productService.findbyName("iphone12");
	       assertEquals(product, pro);
	}
	@Test
	public  void Test2() {
		 Product product = new Product();
	        
	        product.setName("Mi10");
	        product.setInfo("xioami product");
	        product.setPrice(new BigDecimal(37999));
	        product.setCategory("Electronics");

	        assertEquals(product, productRepository.save(product));
	}
	@Test
	public  void Test3() throws Exception {
		 Product product = new Product();
	        
	        product.setName("pepe jeans");
	        product.setInfo("size is 38");
	        product.setPrice(new BigDecimal(2500));
	        product.setCategory("Fashion");
	        
	        productRepository.save(product);
	        
	        Product product2=productRepository.findByname("pepe jeans");

	        assertEquals(product, product2);
	}
	@Test
	public  void Test4() {
		 Product product = new Product();
	        
	        product.setName("alchemist");
	        product.setInfo("very good book");
	        product.setPrice(new BigDecimal(999));
	        product.setCategory("Books");

	        assertEquals(product, productRepository.insert(product));
	}
	@Test
	public  void Test5() {
		 Product product = new Product();
	        
	        product.setName("t-shirt");
	        product.setInfo("size is xxl");
	        product.setPrice(new BigDecimal(1500));
	        product.setCategory("Fashion");

	        assertEquals(product, productRepository.insert(product));
	}
	@Test
	public  void Test6() {
		 
	        
	        Product pro=new Product("t-shirt","size is xxl",new BigDecimal(1500),"Fashion");
	        productRepository.insert(pro);

	        assertEquals(pro, productRepository.findByname("t-shirt"));
	}
	@Test
	public  void Test7() {
		 
	        
	        Product pro=new Product("formal shirt","size is xxl",new BigDecimal(1500),"Fashion");
	        productRepository.insert(pro);

	        assertEquals(pro, productRepository.findByname("t-shirt"));
	}
	@Test
	public  void Test8() {
		 
	        
	        Product pro=new Product("formal shirt","size is xxl",new BigDecimal(1500),"Fashion");
	        productRepository.insert(pro);

	        assertEquals(pro, productRepository.findByname("formal shirt"));
	}
	
	@Test
	public  void Test9() {
		 
	        
	        Product pro=new Product("note book","notebooks is good",new BigDecimal(1500),"Books");
	        productRepository.insert(pro);

	        assertEquals(pro, productRepository.findByname("t-shirt"));
	}
	@Test
	
	public void Test10() {
	Product pro=new Product("note book","notebooks is good",new BigDecimal(1500),"Books");
    productRepository.insert(pro);

    assertEquals(pro, productRepository.findBycategory("Books"));
	
	}


}
