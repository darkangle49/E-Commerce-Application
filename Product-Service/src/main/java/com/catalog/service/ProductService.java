package com.catalog.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.BsonNull;
import org.bson.conversions.Bson;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catalog.model.Product;
import com.catalog.repository.ProductRepository;


@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MongoTemplate mt;
    

    @Transactional
    public List<Product> findAll() {
	return productRepository.findAll();
    }
    
    @Transactional
    public Product findbyName(final String productId) {
	return (Product) productRepository.findByname(productId);
    }
    @Transactional
    public List<Product> findByCategory(final String cat)
    {Query que=new Query();
    que.addCriteria(Criteria.where("category").is(cat));
    List<Product> pt = mt.find(que, Product.class);
    
    	return pt;
    }
    @Transactional
    public List<Product> findByrange(BigDecimal max, BigDecimal min)
    { Query query = new Query();
    query.addCriteria(Criteria.where("price").lt(max).gt(min));
    List<Product> pt = mt.find(query,Product.class);
    return pt;}
    @Transactional 
    public List<Product> findByreg(final String c1)

    {Query query = new Query();
    query.addCriteria(Criteria.where("name").regex("^"+c1.charAt(0)));
    List<Product> pt1 =  mt.find(query,Product.class);
    return pt1;
    }
    @Transactional
    public Product addProduct(Product pro)
    {Product p1=new Product(pro.getName(),pro.getInfo(),pro.getPrice(),pro.getCategory());
    	return productRepository.save(p1);
    	}

	public void deletebyname(String name) {
		productRepository.deleteByName(name);
		
	}
}