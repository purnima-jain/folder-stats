package com.purnima.jain.folder.stats.business.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.purnima.jain.folder.stats.rest.request.dto.FolderStatsRequest;
import com.purnima.jain.folder.stats.rest.response.dto.FolderAggregateStats;
import com.purnima.jain.folder.stats.rest.response.dto.FolderStatsResponse;
import com.purnima.jain.folder.stats.rest.response.dto.RepoFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FolderStatsService {

	// private static final List<String> IGNORE_FOLDERS = Arrays.asList();
	private static final List<String> IGNORE_FOLDERS = Arrays.asList(".git", ".settings", "gradle", "doc", "target");
	private static final List<String> IGNORE_FILENAMES = Arrays.asList("README.md");
	private static final List<String> IGNORE_FILE_EXTENSIONS = Arrays.asList("class", "classpath", "gitignore", "project");

	public FolderStatsResponse getFolderStats(FolderStatsRequest folderStatsRequest) {
		log.info("Entering FolderStatsService.getFolderStats() with folderStatsRequest :: {}", folderStatsRequest);

		List<RepoFile> repoFileList = new ArrayList<>();
		traverseFolder(repoFileList, new File(folderStatsRequest.getFolderPath()));
		log.info("Size of repoFileList :: {}", repoFileList.size());

		FolderStatsResponse folderStatsResponse = new FolderStatsResponse();
		folderStatsResponse.setRepoFiles(repoFileList);
		folderStatsResponse.setTotalFilecount(Long.valueOf(repoFileList.size()));
		folderStatsResponse.setFolderAggregateStatsMap(initializeAndPopulateFolderAggregateStatsMap(repoFileList));
		return folderStatsResponse;
	}

	private Map<String, FolderAggregateStats> initializeAndPopulateFolderAggregateStatsMap(List<RepoFile> repoFileList) {
		Map<String, FolderAggregateStats> folderAggregateStatsMap = new HashMap<>();

		for (RepoFile repoFile : repoFileList) {
			if (folderAggregateStatsMap.get(repoFile.getFileExtension()) == null) {
				FolderAggregateStats folderAggregateStats = new FolderAggregateStats();
				
				folderAggregateStats.setFileExtension(repoFile.getFileExtension());
				
				folderAggregateStats.setCountOfFilesWithThisExtension(1L);
				
				folderAggregateStats.setLinesOfCodeOfFilesWithThisExtension(repoFile.getLineCountOfFile());
				
				folderAggregateStatsMap.put(repoFile.getFileExtension(), folderAggregateStats);
			} else {
				FolderAggregateStats folderAggregateStats = folderAggregateStatsMap.get(repoFile.getFileExtension());
				
				folderAggregateStats.setCountOfFilesWithThisExtension(folderAggregateStats.getCountOfFilesWithThisExtension() + 1);
				
				folderAggregateStats.setLinesOfCodeOfFilesWithThisExtension(folderAggregateStats.getLinesOfCodeOfFilesWithThisExtension() + repoFile.getLineCountOfFile());
				
				folderAggregateStatsMap.put(repoFile.getFileExtension(), folderAggregateStats);
			}
		}

		return folderAggregateStatsMap;
	}

	public static void traverseFolder(List<RepoFile> repoFileList, File node) {

		if (IGNORE_FOLDERS.contains(node.getName())) {
			log.info("Ignoring " + node.getAbsoluteFile());
			return;
		}

		if (node.isFile() && !(IGNORE_FILENAMES.contains(node.getName())) && !(IGNORE_FILE_EXTENSIONS.contains(node.getName().substring(node.getName().lastIndexOf(".") + 1)))) {
			log.info("" + node.getAbsoluteFile());
			RepoFile repoFile = new RepoFile();

			repoFile.setPathWithFileName(Path.of(node.getAbsoluteFile().toString()));

			repoFile.setFileName(node.getName());

			repoFile.setFileExtension(node.getName().substring(node.getName().lastIndexOf(".") + 1));

			repoFile.setLineCountOfFile(getLineCountOfFile(node));

			repoFileList.add(repoFile);
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				traverseFolder(repoFileList, new File(node, filename));
			}
		}

	}

	private static Long getLineCountOfFile(File node) {
		Long noOfLines = 0L;
		try (Scanner scanner = new Scanner(new FileReader(node.getAbsoluteFile().toString()))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line != null && line.trim() != "") {
					noOfLines++;
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		return noOfLines;
	}

}
