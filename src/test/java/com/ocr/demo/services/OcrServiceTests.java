package com.ocr.demo.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OcrServiceTests {
	private static final String TEST_FILENAME = "page-000.png";
	
//	@InjectMocks
	private final OcrServiceImpl service = new OcrServiceImpl();

	@Test
	public void performOCR_compareConfidenceLevels() {
		// act
		String response = service.performOCR(TEST_FILENAME);
		
		// assert
		assertNotNull(response);
	}
}
