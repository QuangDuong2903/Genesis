package com.genesis.queryingservice;

import com.genesis.commons.mapper.MapperConfig;
import com.genesis.commons.persistence.AuditingConfig;
import com.genesis.commons.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
		SecurityConfig.class,
		MapperConfig.class
})
@SpringBootApplication
public class QueryingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueryingServiceApplication.class, args);
	}

}
