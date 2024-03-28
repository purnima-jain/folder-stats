package com.purnima.jain.folder.stats.rest.response.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class FolderStatsResponse {
	
	private List<RepoFile> repoFiles;
	
	private Long totalFilecount;
	
	private Map<String, FolderAggregateStats> folderAggregateStatsMap;

}
