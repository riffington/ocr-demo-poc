package com.ocr.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ocr.demo.domain.TaskType;
import com.ocr.demo.entity.TaskEntity;

@Repository
public class TaskDao {
    @PersistenceContext
    EntityManager entityManager;
    
    public TaskEntity save(final TaskEntity entity) {
    	entityManager.persist(entity);
        return entity;
    }

    public TaskEntity update(final TaskEntity entity) {
    	return entityManager.merge(entity);
    }
    
    public TaskEntity getTask(long taskId) {
    	TaskEntity entity = (TaskEntity) entityManager.createQuery(
    			"from TaskEntity where id = :taskId")
        .setParameter("taskId", taskId)
        .getSingleResult();
        return entity;
    }
    
    /**
     * Gives a count of INCOMPLETE tasks for a given task type and content type.
     * @param taskType
     * @param contentType
     * @return
     */
	public long getCountOfTasks(TaskType taskType, String contentType) {
    	Long count = (Long) entityManager.createQuery(
    			"select count(t) "
    			+ "from TaskEntity t JOIN DocumentEntity d ON t.document.id = d.id "
    			+ "where d.contentType = :contentType "
    			+ "and t.type = :taskType "
    			+ "and t.isComplete = 0")
        .setParameter("contentType", contentType)
        .setParameter("taskType", taskType.name())
        .getSingleResult();
        return count;
    }
	
	/**
	 * Grabs the first available (incomplete) task for the given types.
	 * @param taskType
	 * @param contentType
	 * @return
	 */
	public TaskEntity getTaskByType(TaskType taskType, String contentType) {
    	TaskEntity entity = (TaskEntity) entityManager.createQuery(
    			"select t "
    			+ "from TaskEntity t JOIN DocumentEntity d ON t.document.id = d.id "
    			+ "where d.contentType = :contentType "
    			+ "and t.type = :taskType "
    			+ "and t.isComplete = 0")
    	.setParameter("contentType", contentType)
    	.setParameter("taskType", taskType.name())
    	.setMaxResults(1)
        .getSingleResult();
        return entity;
	}
	
	/**
	 * Gets a task given a type and relatedId (e.g. lineId)
	 * 
	 * @param taskType
	 * @param relatedId
	 * @return
	 */
	public TaskEntity getTaskByTypeAndRelatedId(TaskType taskType, Long relatedId) {
    	TaskEntity entity = (TaskEntity) entityManager.createQuery(
    			"from TaskEntity "
    			+ "where type = :taskType "
    			+ "and relatedId = :relatedId")
    	.setParameter("taskType", taskType.name())
    	.setParameter("relatedId", relatedId)
    	.setMaxResults(1)
        .getSingleResult();
        return entity;
	}
}
