package com.ocr.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ocr.demo.entity.DocumentEntity;
import com.ocr.demo.entity.LineEntity;
import com.ocr.demo.entity.PageEntity;
import com.ocr.demo.entity.WordEntity;

@Repository
public class DocumentDao {

    @PersistenceContext
    EntityManager entityManager;

    public DocumentEntity saveDocument(DocumentEntity entity) {
    	entityManager.persist(entity);
        return entity;
    }
    
    public PageEntity savePage(PageEntity entity) {
    	entityManager.persist(entity);
        return entity;
    }
    
    public WordEntity updateWord(WordEntity entity) {
    	return entityManager.merge(entity);
    }
    
    public PageEntity getPage(long pageId) {
    	PageEntity entity = (PageEntity) entityManager.createQuery(
    			"from PageEntity where id = :pageId")
        .setParameter("pageId", pageId)
        .getSingleResult();
        return entity;
    }
    
    public PageEntity getPageByDocIdAndPageName(long documentId, String pageName) {
    	PageEntity entity = (PageEntity) entityManager.createQuery(
    			"from PageEntity where document.id = :documentId and imageName = :pageName")
        .setParameter("documentId", documentId)
        .setParameter("pageName", pageName)
        .getSingleResult();
        return entity;
    }
    
	public LineEntity getLine(Long lineId) {
    	LineEntity entity = (LineEntity) entityManager.createQuery(
    			"from LineEntity where id = :lineId")
        .setParameter("lineId", lineId)
        .getSingleResult();
        return entity;
    }
    
    public PageEntity getPageByTitle(String title) {
    	PageEntity entity = (PageEntity) entityManager.createQuery(
    			"from PageEntity where title = :title")
        .setParameter("title", title)
        .getSingleResult();
        return entity;
    }
    
	public long getTotalCountByDocumentId(long documentId) {
    	Long count = (Long) entityManager.createQuery(
    			"select count(*) from PageEntity where document.id = :documentId")
        .setParameter("documentId", documentId)
        .getSingleResult();
        return count;
    }

	// TODO Switch to use Pagable for pagination in queries?
    @SuppressWarnings("unchecked")
	public List<PageEntity> getPageListByDocumentId(long documentId, int pageNumber, int pageSize) {
    	int adjustedPageNumber = pageNumber - 1; //(1-based to 0-based) PROBABLY should do this elsewhere... not in the DAO
    	List<PageEntity> entities = (List<PageEntity>) entityManager.createQuery(
    			"from PageEntity where document.id = :documentId order by title")
        .setParameter("documentId", documentId)
        .setFirstResult(adjustedPageNumber * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
        return entities;
    }
    
    public DocumentEntity getDocumentByGuid(String docGuid) {
    	DocumentEntity entity = (DocumentEntity) entityManager.createQuery(
    			"from DocumentEntity where guid = :docGuid")
        .setParameter("docGuid", docGuid)
        .getSingleResult();
        return entity;
    }

	@SuppressWarnings("unchecked")
	public List<WordEntity> getWordsByIds(List<Long> ids) {
    	List<WordEntity> entities = (List<WordEntity>) entityManager.createQuery(
    			"from WordEntity where id in :ids")
        .setParameter("ids", ids)
        .getResultList();
        return entities;
    }
}
