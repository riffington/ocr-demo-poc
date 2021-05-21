package com.ocr.demo.repository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ocr.demo.dao.TaskDao;
import com.ocr.demo.domain.Task;
import com.ocr.demo.domain.TaskType;
import com.ocr.demo.entity.TaskEntity;

@Service
public class TaskRepositoryImpl {

    @Autowired
    private TaskDao taskDao;
	
    @Autowired
    private ModelMapper modelMapper;
    
	@Transactional
    public boolean saveTask(TaskEntity task) {
        try {
        	taskDao.save(task);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

	@Transactional
	public Task update(TaskEntity task) {
		TaskEntity entity = null;
        try {
        	entity = taskDao.update(task);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        Task updatedTask = modelMapper.map(entity, Task.class);
        return updatedTask;
	}
	
	@Transactional
    public Task getTaskById(long taskId) {
		TaskEntity entity = taskDao.getTask(taskId);
        Task task = modelMapper.map(entity, Task.class);
        return task;
    }

	@Transactional
	public long getCountOfTasks(TaskType taskType, String contentType) {
		return taskDao.getCountOfTasks(taskType, contentType);
	}

	@Transactional
    public Task getTaskByType(TaskType taskType, String contentType) {
		TaskEntity entity = taskDao.getTaskByType(taskType, contentType);
        Task task = modelMapper.map(entity, Task.class);
        task.setDocumentId(entity.getDocument().getId());
        return task;
    }
	
	@Transactional
	public TaskEntity getTaskEntityByTypeAndRelatedId(TaskType taskType, Long relatedId) {
		TaskEntity entity = taskDao.getTaskByTypeAndRelatedId(taskType, relatedId);
        return entity;
	}
}
