package com.gateway;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
@EnableAutoConfiguration
public class DocumentController implements SwaggerResourcesProvider {

   @Override
   public List<SwaggerResource> get() {
      List<SwaggerResource> resources = new ArrayList<>();
      resources.add(swaggerResource("CATALOG", "/catalog/api-docs", "2.0"));
      resources.add(swaggerResource("ORDER-SERVICE","/orders/api-docs", "2.0"));
      resources.add(swaggerResource("USER-SERVICE", "/users/api-docs", "2.0"));
      
      return resources;
   }

   private SwaggerResource swaggerResource(String name, String location, String version) {
      SwaggerResource swaggerResource = new SwaggerResource();
      swaggerResource.setName(name);
      swaggerResource.setLocation(location);
      swaggerResource.setSwaggerVersion(version);
      return swaggerResource;
   }

}
