package com.ocr.demo.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.ocr.demo.domain.DocumentCreationRequest;
import com.ocr.demo.domain.TaskType;
import com.ocr.demo.entity.AreaEntity;
import com.ocr.demo.entity.DocumentEntity;
import com.ocr.demo.entity.LineEntity;
import com.ocr.demo.entity.PageEntity;
import com.ocr.demo.entity.ParagraphEntity;
import com.ocr.demo.entity.TaskEntity;
import com.ocr.demo.entity.WordEntity;
import com.ocr.demo.repository.DocumentRepositoryImpl;
import com.ocr.demo.repository.TaskRepositoryImpl;
import com.ocr.demo.utils.OcrParserHandler;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class PublishingServiceImpl {
	private static final String ISO_8859_1 = "ISO-8859-1";

	@Autowired
	MainServiceImpl mainService;
	
	@Autowired
	OcrServiceImpl ocrService;
	
	@Autowired
	DocumentRepositoryImpl documentRepo;

	@Autowired
	private TaskRepositoryImpl taskRepo;

	@Value("${spellcheck.confidence.threshold}")
	private int confidenceThreshold;

	private SAXParserFactory factory;
	private SAXParser saxParser;
	private OcrParserHandler handler;
	
	public String createDocument(DocumentCreationRequest request) {
		DocumentEntity document = new DocumentEntity(request.getGuid(), request.getContentType());
		document = documentRepo.saveDocument(document);
		return document.getGuid();
	}
	
	public boolean parseAndPublishTestDocument(String docGuid, String pageName) {
		// get related document, then parse file and associate to doc
		DocumentEntity document = documentRepo.getDocumentEntityByGuid(docGuid);
		if (document == null) {
			return false;
		}
		
		PageEntity page = parseAndPublishPageImage(pageName, document);
		createSpellcheckTasksForPage(document, page);
		return true;
	}
	
	public PageEntity parseAndPublishPageImage(String fileName, DocumentEntity document) {
		PageEntity page = null;

		String ocrStringResult = ocrService.performOCR(fileName);

		if (StringUtils.isNotEmpty(ocrStringResult)) {
			// remove DOCTYPE header (not valid for our xml parsing)
			ocrStringResult = ocrStringResult.replaceAll("(?i)<!DOCTYPE[^<>]*>", "");
			
			try {
				factory = SAXParserFactory.newInstance();
				factory.setValidating(false);
				saxParser = factory.newSAXParser();
				handler = new OcrParserHandler();

				InputStream inputStream = new ByteArrayInputStream(ocrStringResult.getBytes());
				InputSource inputSource = new InputSource(inputStream);
				inputSource.setEncoding(ISO_8859_1);

				saxParser.parse(inputSource, handler);

				page = handler.getPage();
				page.setDocument(document);
			} catch (Exception e) {
				System.out.println("Error during ocr result parsing to objects. " + e.getMessage());
				return null;
			}
		}

		return documentRepo.savePage(page);
	}
	
	public void createSpellcheckTasksForPage(DocumentEntity document, PageEntity page) {
		String imageName = page.getImageName();
		for (AreaEntity a : page.getAreas())
		{
			for (ParagraphEntity p : a.getParagraphs())
			{
				for (LineEntity l : p.getLines())
				{
					boolean needsSpellchecking = false;
					for (WordEntity w : l.getWords())
					{
						if (w.getConfidence() < confidenceThreshold) {
							needsSpellchecking = true;
							break;
						}
					}
					if (needsSpellchecking) {
						TaskEntity task = new TaskEntity(TaskType.SPELLCHECK.name(), document, l.getId(), imageName);
						taskRepo.saveTask(task);
					}
				}
			}
		}
	}
}
