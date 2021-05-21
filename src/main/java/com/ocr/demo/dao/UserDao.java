package com.ocr.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ocr.demo.entity.UserEntity;

@Repository
public class UserDao {
    
    @PersistenceContext
    EntityManager entityManager;
    
    public UserEntity saveOrUpdate(final UserEntity entity) {
    	entityManager.persist(entity);
        return entity;
    }
	
    @SuppressWarnings("unchecked")
	public List<UserEntity> getAllUsers() {
    	List<UserEntity> entities = (List<UserEntity>) entityManager.createQuery(
    			"from UserEntity")
        .getResultList();
        return entities;
    }
    
    public UserEntity getUserByUsername(String username) {
    	UserEntity entity = (UserEntity) entityManager.createQuery(
    			"from UserEntity where creds_username = :username")
        .setParameter("username", username)
        .getSingleResult();
        return entity;
    }
}
