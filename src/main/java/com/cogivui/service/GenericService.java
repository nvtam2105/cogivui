package com.cogivui.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;

import com.cogivui.dao.GenericDao;

@Transactional
public class GenericService<T extends Serializable> {

	protected GenericDao<T> defaultDao;

	public T saveOrUpdate(T entity) throws HibernateException {
		return getDefaultDao().saveOrUpdate(entity);
	}

	public void saveOrUpdate(List<T> entities) throws HibernateException {
		getDefaultDao().saveOrUpdate(entities);
	}

	public T getById(long id) throws HibernateException {
		return getDefaultDao().getById(id);
	}

	public List<T> getAll() throws HibernateException {
		return getDefaultDao().getAll();
	}

	public void deleteById(long id) throws HibernateException {
		getDefaultDao().deleteById(id);
	}

	public void deleteAll() throws HibernateException {
		getDefaultDao().deleteAll();
	}

	// Need to be override in class implementation.
	public GenericDao<T> getDefaultDao() {
		return defaultDao;
	}

	public void setDefaultDao(GenericDao<T> defaultDao) {
		this.defaultDao = defaultDao;
	}

}
