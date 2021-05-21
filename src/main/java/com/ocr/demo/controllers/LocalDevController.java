package com.ocr.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ocr.demo.domain.DocumentCreationRequest;
import com.ocr.demo.domain.SimpleResponse;
import com.ocr.demo.services.MainServiceImpl;
import com.ocr.demo.services.PublishingServiceImpl;

import io.micrometer.core.instrument.util.StringUtils;

/**
 * This controller is used for the POC in order to publish individual images and have
 * a variety of endpoints used to help verify processes/results/etc.
 */
@Controller
public class LocalDevController {
	@Autowired
	MainServiceImpl mainService;

	@Autowired
	PublishingServiceImpl publishingService;
	
	@RequestMapping(value = "/api/loadtestdata/document", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<SimpleResponse> createDocument(@RequestBody DocumentCreationRequest request) {
		String guid = publishingService.createDocument(request);
		HttpStatus responseStatus = StringUtils.isNotBlank(guid) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
	    ResponseEntity<SimpleResponse> responseEntity = new ResponseEntity<>(new SimpleResponse(guid), null, responseStatus);
	    return responseEntity;
	}
	
	@RequestMapping(value = "/api/loadtestdata/{guid}/{pageName}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<SimpleResponse> loadTestDocData(@PathVariable("guid") String docGuid, @PathVariable("pageName") String pageName) {
		boolean success = publishingService.parseAndPublishTestDocument(docGuid, pageName);
		String responseText = success ? "Test load successfully completed" : "Test load failed";
		HttpStatus responseStatus = success ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
	    ResponseEntity<SimpleResponse> responseEntity = new ResponseEntity<>(new SimpleResponse(responseText), null, responseStatus);
	    return responseEntity;
	}
		
	@RequestMapping(value = "/api/text/{name}", method = RequestMethod.GET)
	@ResponseBody
	public String getText(@PathVariable("name") String imageName) {
		return mainService.getTestText(imageName);
	}
}
