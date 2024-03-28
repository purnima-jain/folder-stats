package com.purnima.jain.folder.stats.rest.response.dto;

import java.nio.file.Path;

import lombok.Data;

@Data
public class RepoFile {
	
	private String fileName;
	private String fileExtension;
	private Path pathWithFileName;
	private Long lineCountOfFile;

}
