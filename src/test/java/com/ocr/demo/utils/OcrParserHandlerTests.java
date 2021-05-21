package com.ocr.demo.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import com.ocr.demo.entity.PageEntity;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class OcrParserHandlerTests {
	private SAXParserFactory factory;
	private SAXParser saxParser;
	private OcrParserHandler handler;
	
	@BeforeAll
	public void setup() throws Exception {
		factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		saxParser = factory.newSAXParser();
		handler = new OcrParserHandler();
	}

	@Test
	public void ocrParser_parsesToExpectedObjects() throws IOException, SAXException, ParserConfigurationException {

        saxParser.parse("src/test/resources/sax/sampleOcrOutputData.xml", handler);

        PageEntity result = handler.getPage();

        assertNotNull(result);
    }
}
