package com.ampcus.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class LoginApplication {

	public static void main(String[] args) {

        SpringApplication.run(LoginApplication.class, args);
        System.out.println("Starting Code");
	}


}
