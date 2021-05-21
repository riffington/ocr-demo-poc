package com.ocr.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ocr.demo.dao.DocumentDao;
import com.ocr.demo.domain.CoordinatesType;
import com.ocr.demo.domain.FilterRequest;
import com.ocr.demo.domain.Line;
import com.ocr.demo.domain.Page;
import com.ocr.demo.domain.PageListRequest;
import com.ocr.demo.entity.DocumentEntity;
import com.ocr.demo.entity.LineEntity;
import com.ocr.demo.entity.PageEntity;
import com.ocr.demo.entity.WordEntity;
import com.ocr.demo.utils.BoundingBoxCoordinatesUtil;

@Service
public class DocumentRepositoryImpl {

    @Autowired
    private DocumentDao documentDao;
	
    @Autowired
    private ModelMapper modelMapper;
    
    @Transactional
    public DocumentEntity saveDocument(DocumentEntity document) {
    	DocumentEntity savedEntity = null;
		try {
        	savedEntity = documentDao.saveDocument(document);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return savedEntity;
    }
    
	@Transactional
    public PageEntity savePage(PageEntity page) {
		PageEntity savedPage = null;
        try {
        	savedPage = documentDao.savePage(page);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return savedPage;
    }
	
	@Transactional
	public List<WordEntity> updateWords(List<WordEntity> entities) {
		List<WordEntity> savedEntities = new ArrayList<>();
        try {
        	for (WordEntity entity : entities) {
            	savedEntities.add(documentDao.updateWord(entity));
        	}
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.out.println(e);
        }
        return savedEntities;
	}
	
	@Transactional
    public Page getPageById(long pageId, FilterRequest filter) {
		PageEntity entity = documentDao.getPage(pageId);
        Page page = modelMapper.map(entity, Page.class);
        page.setCoordinatesType(filter.getCoordinatesType());
        page.setBoundingBoxCoordinates(BoundingBoxCoordinatesUtil.getBoundingBoxCoordinatesForType(page.getAreas(), filter.getCoordinatesType()));
        return page;
    }
	
	@Transactional
	public Page getPageByDocIdAndPageName(long documentId, String pageName) {
		PageEntity entity = documentDao.getPageByDocIdAndPageName(documentId, pageName);
        Page page = modelMapper.map(entity, Page.class);
        return page;
	}

	@Transactional
	public Line getLineById(Long lineId) {
		LineEntity entity = documentDao.getLine(lineId);
        Line line = modelMapper.map(entity, Line.class);
        return line;
	}

	@Transactional
	public Page getPageByTitle(String title) {
		PageEntity entity = documentDao.getPageByTitle(title);
        return modelMapper.map(entity, Page.class);
	}

	@Transactional
	public long getPageTotalCountByDocumentId(long documentId) {
		return documentDao.getTotalCountByDocumentId(documentId);
	}
	
	@Transactional
	public List<Page> getPageListByDocumentId(PageListRequest request) {

		// TODO Switch to use Pagable for pagination in queries?
		List<PageEntity> entities = documentDao.getPageListByDocumentId(
				request.getDocumentId(), request.getPageNumber(), request.getPageSize());

        List<Page> pages = entities.stream()
        		.map(entity -> convertEntityToPage(entity, request.getCoordinatesType()))
        		.collect(Collectors.toList());

        return pages;
	}
	
	@Transactional
	public List<WordEntity> getWordEntitiesByIds(List<Long> ids) {
		return documentDao.getWordsByIds(ids);
	}

	private Page convertEntityToPage(PageEntity entity, CoordinatesType type) {
		Page page = modelMapper.map(entity, Page.class);
		page.setCoordinatesType(type);
		page.setBoundingBoxCoordinates(BoundingBoxCoordinatesUtil.getBoundingBoxCoordinatesForType(page.getAreas(), type));
		return page;
	}
	
	// special one to return an Entity object (used for Publishing process)
	@Transactional
    public DocumentEntity getDocumentEntityByGuid(String docGuid) {
		DocumentEntity entity = documentDao.getDocumentByGuid(docGuid);
        return entity;
    }
}
