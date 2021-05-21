package com.ocr.demo.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ocr.demo.entity.AreaEntity;
import com.ocr.demo.entity.BaselineTupleEntity;
import com.ocr.demo.entity.BoundingBoxEntity;
import com.ocr.demo.entity.LineEntity;
import com.ocr.demo.entity.PageEntity;
import com.ocr.demo.entity.ParagraphEntity;
import com.ocr.demo.entity.WordEntity;

import io.micrometer.core.instrument.util.StringUtils;

public class OcrParserHandler extends DefaultHandler {
	private static final String DIV = "div";
	private static final String PARA = "p";
	private static final String SPAN = "span";

	private static final String ATTR_ID = "id";
	private static final String ATTR_CLASS = "class";
	private static final String ATTR_TITLE = "title";
	private static final String ATTR_LANG = "lang";

	private static final String ATTR_BBOX = "bbox";
	private static final String ATTR_IMAGE = "image";
	private static final String ATTR_PAGENUM = "ppageno";
	private static final String ATTR_CONFIDENCE = "x_wconf";
	private static final String ATTR_BASELINE = "baseline";
	private static final String ATTR_SIZE = "x_size";
	private static final String ATTR_DESCENDERS = "x_descenders";
	private static final String ATTR_ASCENDERS = "x_ascenders";

	private static final String CLASS_TYPE_PAGE = "ocr_page";
	private static final String CLASS_TYPE_AREA = "ocr_carea";
	private static final String CLASS_TYPE_PARAGRAPH = "ocr_par";
	private static final String CLASS_TYPE_LINE = "ocr_line";
	private static final String CLASS_TYPE_WORD = "ocrx_word";

	private PageEntity page;
	private StringBuilder elementValue;
	private AreaEntity currentArea;
	private ParagraphEntity currentParagraph;
	private LineEntity currentLine;
	private WordEntity currentWord;

	public PageEntity getPage() {
		return page;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (elementValue == null) {
			elementValue = new StringBuilder();
		} else {
			elementValue.append(ch, start, length);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (attributes != null && StringUtils.isNotEmpty(attributes.getValue(ATTR_CLASS))) {
			String classType = attributes.getValue(ATTR_CLASS);

			switch (classType) {
			case CLASS_TYPE_PAGE:
				handlePageStart(attributes);
				break;
			case CLASS_TYPE_AREA:
				handleAreaStart(attributes);
				break;
			case CLASS_TYPE_PARAGRAPH:
				handleParagraphStart(attributes);
				break;
			case CLASS_TYPE_LINE:
				handleLineStart(attributes);
				break;
			case CLASS_TYPE_WORD:
				handleWordStart(attributes);
				break;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
		case DIV:
			handleDivEnd();
			break;
		case PARA:
			handleParagraphEnd();
			break;
		case SPAN:
			handleSpanEnd();
			break;
		}
	}

	private void handlePageStart(Attributes attributes) {
		String id = attributes.getValue(ATTR_ID);
		Map<String, String> titleFields = parseFieldsFromTitle(attributes.getValue(ATTR_TITLE),
				Arrays.asList(ATTR_IMAGE, ATTR_BBOX, ATTR_PAGENUM));
		
		String[] imageNameSections = titleFields.getOrDefault(ATTR_IMAGE, "").split(Pattern.quote("\\"));
		String dirtyImageName = imageNameSections[imageNameSections.length-1];
		String cleanedImageName = dirtyImageName.replaceAll("\"", "");
		int pageNumber = Integer.parseInt(titleFields.getOrDefault(ATTR_PAGENUM, "0")); // This doesn't seem to work with files we are using
		BoundingBoxEntity box = parseBoxData(titleFields.getOrDefault(ATTR_BBOX, null));

		page = new PageEntity(id, cleanedImageName, pageNumber, box);
	}

	private void handleAreaStart(Attributes attributes) {
		String id = attributes.getValue(ATTR_ID);
		BoundingBoxEntity box = parseBoxData(attributes.getValue(ATTR_TITLE));

		currentArea = new AreaEntity(id, box);
	}

	private void handleParagraphStart(Attributes attributes) {
		String id = attributes.getValue(ATTR_ID);
		String lang = attributes.getValue(ATTR_LANG);
		BoundingBoxEntity box = parseBoxData(attributes.getValue(ATTR_TITLE));

		currentParagraph = new ParagraphEntity(id, lang, box);
	}

	private void handleLineStart(Attributes attributes) {
		String id = attributes.getValue(ATTR_ID);
		Map<String, String> titleFields = parseFieldsFromTitle(attributes.getValue(ATTR_TITLE),
				Arrays.asList(ATTR_BBOX, ATTR_BASELINE, ATTR_SIZE, ATTR_DESCENDERS, ATTR_ASCENDERS));

		BaselineTupleEntity baseline = parseBaseline(titleFields.getOrDefault(ATTR_BASELINE, null));
		float size = Float.parseFloat(titleFields.getOrDefault(ATTR_SIZE, "0"));
		float descenders = Float.parseFloat(titleFields.getOrDefault(ATTR_DESCENDERS, "0"));
		float ascenders = Float.parseFloat(titleFields.getOrDefault(ATTR_ASCENDERS, "0"));
		BoundingBoxEntity box = parseBoxData(titleFields.getOrDefault(ATTR_BBOX, null));

		currentLine = new LineEntity(id, baseline, size, descenders, ascenders, box);
	}

	private void handleWordStart(Attributes attributes) {
		String id = attributes.getValue(ATTR_ID);
		Map<String, String> titleFields = parseFieldsFromTitle(attributes.getValue(ATTR_TITLE),
				Arrays.asList(ATTR_CONFIDENCE, ATTR_BBOX));

		int confidence = Integer.parseInt(titleFields.getOrDefault(ATTR_CONFIDENCE, "0"));
		BoundingBoxEntity box = parseBoxData(titleFields.getOrDefault(ATTR_BBOX, null));

		currentWord = new WordEntity(id, confidence, box);
		elementValue = new StringBuilder();
	}

	private void handleSpanEnd() {
		// SPAN can be a Word or Line
		if (currentWord != null && currentLine != null) {
			currentWord.setText(elementValue.toString());
			currentLine.addWord(currentWord);
			currentWord = null;
		} else if (currentLine != null && currentParagraph != null) {
			currentParagraph.addLine(currentLine);
			currentLine = null;
		}
	}

	private void handleParagraphEnd() {
		if (currentParagraph != null && currentArea != null) {
			currentArea.addParagraph(currentParagraph);
			currentParagraph = null;
		}
	}

	private void handleDivEnd() {
		// DIV can be an Area or Page
		if (currentArea != null && page != null) {
			page.addArea(currentArea);
			currentArea = null;
		}
	}

	public Map<String, String> parseFieldsFromTitle(String titleValue, List<String> fields) {
		Map<String, String> responseMap = new HashMap<>();
		String[] titleSections = titleValue.split(";");

		for (String section : titleSections) {
			section = section.trim();
			String name = section.substring(0, section.indexOf(" "));

			if (fields.contains(name)) {
				String value = section.replace(name, "").trim();
				responseMap.put(name, value);
			}
		}

		return responseMap;
	}

	private BoundingBoxEntity parseBoxData(String boxValuesString) {
		BoundingBoxEntity boxData = null;

		if (StringUtils.isNotEmpty(boxValuesString)) {
			boxValuesString = boxValuesString.trim();

			if (boxValuesString.contains(ATTR_BBOX)) {
				boxValuesString = boxValuesString.replace(ATTR_BBOX, "").trim();
			}

			// values are top-left-x, top-left-y, bottom-right-x, and bottom-right-y (in that order, each separated by a space)
			String[] coordinates = boxValuesString.split(" ");
			int topLeftX = Integer.parseInt(coordinates[0]);
			int topLeftY = Integer.parseInt(coordinates[1]);
			int bottomRightX = Integer.parseInt(coordinates[2]);
			int bottomRightY = Integer.parseInt(coordinates[3]);

			boxData = new BoundingBoxEntity(topLeftX, topLeftY, bottomRightX, bottomRightY);
		}
		return boxData;
	}

	private BaselineTupleEntity parseBaseline(String baselineValuesString) {
		BaselineTupleEntity baseline = null;

		if (StringUtils.isNotEmpty(baselineValuesString)) {
			baselineValuesString = baselineValuesString.trim();

			if (baselineValuesString.contains(ATTR_BASELINE)) {
				baselineValuesString = baselineValuesString.replace(ATTR_BASELINE, "").trim();
			}

			// values are x and y (in that order, separated by a space)
			String[] coordinates = baselineValuesString.split(" ");
			float x = Float.parseFloat(coordinates[0]);
			float y = Float.parseFloat(coordinates[1]);

			baseline = new BaselineTupleEntity(x, y);
		}
		return baseline;
	}
}
