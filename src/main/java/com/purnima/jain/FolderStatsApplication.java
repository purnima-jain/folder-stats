package com.purnima.jain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class FolderStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FolderStatsApplication.class, args);
		log.info("Starting FolderStatsApplication.......");
	}

}
