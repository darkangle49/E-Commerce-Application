package com.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.catalog.model.Category;

public interface CategoryRepository extends MongoRepository<Category,String> {

}
