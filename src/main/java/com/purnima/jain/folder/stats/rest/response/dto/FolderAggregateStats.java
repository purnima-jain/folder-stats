package com.purnima.jain.folder.stats.rest.response.dto;

import lombok.Data;

@Data
public class FolderAggregateStats {
	
	private String fileExtension;
	private Long countOfFilesWithThisExtension;
	private Long linesOfCodeOfFilesWithThisExtension;

}
