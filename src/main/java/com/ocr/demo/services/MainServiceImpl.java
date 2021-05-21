package com.ocr.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ocr.demo.domain.FilterRequest;
import com.ocr.demo.domain.Line;
import com.ocr.demo.domain.Page;
import com.ocr.demo.domain.PageListRequest;
import com.ocr.demo.domain.SpellcheckLineResponse;
import com.ocr.demo.domain.SpellcheckLineUpdateRequest;
import com.ocr.demo.domain.Task;
import com.ocr.demo.domain.TaskCountsResponse;
import com.ocr.demo.domain.TaskType;
import com.ocr.demo.domain.UserInfo;
import com.ocr.demo.domain.Word;
import com.ocr.demo.entity.TaskEntity;
import com.ocr.demo.entity.WordEntity;
import com.ocr.demo.repository.DocumentRepositoryImpl;
import com.ocr.demo.repository.TaskRepositoryImpl;
import com.ocr.demo.repository.UserRepositoryImpl;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class MainServiceImpl {
	@Autowired
	OcrServiceImpl ocrService;

	@Autowired
	UserRepositoryImpl userRepository;

	@Autowired
	TaskRepositoryImpl taskRepository;
	
	@Autowired
	DocumentRepositoryImpl documentRepository;

	@Value("${spellcheck.confidence.threshold}")
	private int confidenceThreshold;
	
	public Page getPageById(long pageId, FilterRequest filter) {
		return documentRepository.getPageById(pageId, filter);
	}
	
	public long getPageTotalCountByDocumentId(long documentId) {
		return documentRepository.getPageTotalCountByDocumentId(documentId);
	}
	
	public List<Page> getPageListByDocumentId(PageListRequest request) {
		return documentRepository.getPageListByDocumentId(request);
	}
	
	public TaskCountsResponse getCountOfTasks(String contentType) {
		Long spellcheckCount = taskRepository.getCountOfTasks(TaskType.SPELLCHECK, contentType);
		Long labelCount = taskRepository.getCountOfTasks(TaskType.LABEL, contentType);
		return new TaskCountsResponse(spellcheckCount, labelCount);
	}

	public SpellcheckLineResponse getLineForSpellChecking(String contentType) {
		// check task repository for next available spell-check task
		Task task = taskRepository.getTaskByType(TaskType.SPELLCHECK, contentType);
		
		// get page info (for bounding box)
		Page page = documentRepository.getPageByDocIdAndPageName(task.getDocumentId(), task.getImageRef());
		
		// get the line for the task
		Line line = documentRepository.getLineById(task.getRelatedId());
		
		// limit words in line to only those needing to be checked
		List<Word> words = line.getWords().stream().filter(w -> w.getConfidence() < confidenceThreshold)
				.collect(Collectors.toList());
		
		SpellcheckLineResponse response = new SpellcheckLineResponse(
				line.getId(), line.getBoundingBox(), words, task.getImageRef(), page.getBoundingBox());
		
		return response;
	}

	public boolean updateLineForSpellChecking(SpellcheckLineUpdateRequest request) {
		// check that the line exists
		Line line = documentRepository.getLineById(request.getId());
		if (line == null) {
			return false;
		}
		
		// check that the line has a spell-check task
		TaskEntity task = taskRepository.getTaskEntityByTypeAndRelatedId(TaskType.SPELLCHECK, line.getId());
		if (task == null) {
			return false;
		}
		
		// update words with new text
		List<Word> requestWords = request.getWords();
		List<Long> wordIds = requestWords.stream().map(x -> x.getId()).collect(Collectors.toList());
		List<WordEntity> words = documentRepository.getWordEntitiesByIds(wordIds);
		for(WordEntity entity : words) {
			Optional<Word> found = requestWords.stream().filter(w -> w.getId().equals(entity.getId())).findFirst();
			if (found.isPresent() && StringUtils.isNotBlank(found.get().getCorrectedText())) {
				entity.setCorrectedText(found.get().getCorrectedText());
			}
		}
		List<WordEntity> updatedWords = documentRepository.updateWords(words);
		if (updatedWords.size() == 0) {
			return false;
		}
		
		// update task to say it is complete
		task.setComplete(true);
		taskRepository.update(task);
		
		return true;
	}
	
	public UserInfo getUserInfoByUsername(String username) {
		return userRepository.getUserInfoByUsername(username);
	}
	

	// TODO remove when no longer needed
	public String getTestText(String fileName) {
		String ocrResult = ocrService.performOCR(fileName);
		return StringUtils.isNotEmpty(ocrResult) ? ocrResult : "No OCR results to show.";
	}
	
	public Page getTestPage(String fileName) {
		Page page = null;

		page = documentRepository.getPageByTitle(fileName);

		return page;
	}
	// END TODO
}
