package com.ocr.demo.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ocr.demo.domain.FilterRequest;
import com.ocr.demo.domain.Page;
import com.ocr.demo.domain.PageListRequest;
import com.ocr.demo.domain.PageListResponse;
import com.ocr.demo.domain.Pagination;
import com.ocr.demo.domain.SimpleResponse;
import com.ocr.demo.domain.SpellcheckLineResponse;
import com.ocr.demo.domain.SpellcheckLineUpdateRequest;
import com.ocr.demo.domain.TaskCountsResponse;
import com.ocr.demo.services.MainServiceImpl;

@Controller
public class ApiController {
	@Autowired
	MainServiceImpl mainService;

	
	@RequestMapping(value = "/api/pages/{pageId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Page> getPageById(@PathVariable("pageId") long pageId,
				@RequestBody FilterRequest filter) throws Exception {
		Page page = mainService.getPageById(pageId, filter);
	    return new ResponseEntity<Page>(page, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/{user}/trainingData", method = RequestMethod.GET)
	@ResponseBody
	public Page getPageForUser(@PathVariable("user") String userName) {
		// TODO obviously this logic is for testing
		String pageName = "";
		switch(userName) {
		case "user1":
			pageName = "page-000.png";
			break;
		case "user2":
			pageName = "page-001.png";
			break;
		case "admin":
			pageName = "page-003.png";
			break;
		default:
			pageName = "page-009.png";
		}
		return mainService.getTestPage(pageName);	
	}
	
	@RequestMapping(value = "/api/image/{name}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable("name") String imageName) throws IOException {
	    PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(String.format("%s/%s", "classpath:**", imageName));
        
        if (CollectionUtils.isEmpty(Arrays.asList(resources))) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
	    InputStream in = resources[0].getInputStream();
	    byte[] media = IOUtils.toByteArray(in);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
	    headers.setContentType(MediaType.IMAGE_PNG);
	    
	    ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
	    return responseEntity;
	}
	
	@RequestMapping(value = "/api/documents/{documentId}/pages", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageListResponse> getPagesForDocument(@PathVariable("documentId") long documentId,
				@RequestBody PageListRequest request) throws Exception {

		// get total count for pagination
		long totalCount = mainService.getPageTotalCountByDocumentId(documentId);
		Pagination pagination = new Pagination(request.getPageNumber(), request.getPageSize(), totalCount);
		
		// get pages
		request.setDocumentId(documentId);
		List<Page> pages = mainService.getPageListByDocumentId(request);
		
		PageListResponse response = new PageListResponse(pagination, pages);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/task/counts", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<TaskCountsResponse> getCountOfTasks(@RequestParam(name="contentType", required=true) String contentType) throws Exception {
		TaskCountsResponse response = mainService.getCountOfTasks(contentType);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/spellcheck/line", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<SpellcheckLineResponse> getLineForSpellcheck(@RequestParam(name="contentType", required=true) String contentType) throws Exception {
		SpellcheckLineResponse response = mainService.getLineForSpellChecking(contentType);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/spellcheck/line", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<SimpleResponse> updateLineForSpellcheck(@RequestBody SpellcheckLineUpdateRequest updateRequest) throws Exception {
		boolean success = mainService.updateLineForSpellChecking(updateRequest);
		String responseText = success ? "Data updated" : "Update Failed";
		HttpStatus responseStatus = success ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
	    return new ResponseEntity<>(new SimpleResponse(responseText), null, responseStatus);
	}
}
