package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import com.catalog.Catalog2Application;
import com.catalog.MongoConfig;
import com.catalog.model.Product;
import com.catalog.repository.ProductRepository;
import com.catalog.service.ProductService;
import com.netflix.discovery.shared.Application;





@SpringBootTest
@Import(MongoConfig.class)
@AutoConfigureMockMvc

@SpringBootConfiguration
public class Catalog2ApplicationTests {
	
	@Test
	void contextLoads() {
	}
}
