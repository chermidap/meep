package com.meep.vehiclesupdate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MobilityResourceUpdaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobilityResourceUpdaterApplication.class, args);
	}

}
