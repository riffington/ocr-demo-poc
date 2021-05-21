package com.ocr.demo.services;

import java.io.File;

import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class OcrServiceImpl {
	private static final String TEST_IMAGE_LOC = "src/main/resources/images/trainingDocs";
	private static final String DPI_SETTING_NAME = "user_defined_dpi";
	private static final String OCR_TESSDATA_LOC = "src/main/resources/tessdata";
	private static final String ENG_LANG = "eng";
	private static final int DPI = 75;

	public String performOCR(String fileName) {
		String result = null;
		String imagePath = String.format("%s/%s/%s", TEST_IMAGE_LOC, DPI, fileName);
		try {
			File image = new File(imagePath);
			Tesseract tesseract = new Tesseract();
			tesseract.setDatapath(OCR_TESSDATA_LOC);
			tesseract.setLanguage(ENG_LANG);
			tesseract.setTessVariable(DPI_SETTING_NAME, Integer.toString(300));
			tesseract.setPageSegMode(1);
			tesseract.setOcrEngineMode(1);
			tesseract.setHocr(true);
			result = tesseract.doOCR(image);
		} catch (TesseractException e) {
			System.out.println("Error performing OCR. " + e.getMessage());
		}
		return result;
	}
}
