package com.universidad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableCaching
public class MiProyectoSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiProyectoSpringBootApplication.class, args);
	}
}

//mvn spring-boot:run
//java -jar target/mi-proyecto-spring-boot-0.0.1-SNAPSHOT.jar
//rbn = hibernate
//http://localhost:8080/api/estudiantes/buscar?nombre=Ana