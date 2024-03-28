package com.purnima.jain.folder.stats.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purnima.jain.folder.stats.business.service.FolderStatsService;
import com.purnima.jain.folder.stats.rest.request.dto.FolderStatsRequest;
import com.purnima.jain.folder.stats.rest.response.dto.FolderStatsResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/folder-stats")
@Slf4j
public class FolderStatsController {

	@Autowired
	FolderStatsService folderStatsService;

	@GetMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FolderStatsResponse> getFolderStats(@RequestBody FolderStatsRequest folderStatsRequest) {
		log.info("Entering FolderStatsController.getFolderStats() with folderStatsRequest :: {}", folderStatsRequest);
		FolderStatsResponse folderStatsResponse = folderStatsService.getFolderStats(folderStatsRequest);
		return ResponseEntity.ok().body(folderStatsResponse);
	}
	
	

}
