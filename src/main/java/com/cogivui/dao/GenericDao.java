package com.cogivui.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public abstract class GenericDao<T extends Serializable> {

	@Autowired
	private SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	private Class<T> clazz;

	public void setClazz(final Class<T> clazzToSet) {
		clazz = clazzToSet;
	}

	public T getById(final long id) throws HibernateException {
		session = sessionFactory.openSession();
		T entity = (T) session.get(clazz.getName(), id);
		tx = session.getTransaction();
		session.beginTransaction();
		tx.commit();
		return entity;

	}

	public List<T> getAll() throws HibernateException {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		// List<T> list = session.createCriteria(clazz.getName()).list();
		List<T> list = session.createQuery("from " + clazz.getName()).list();
		tx.commit();
		session.close();
		return list;

	}

	public T saveOrUpdate(final T entity) throws HibernateException {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		session.saveOrUpdate(entity);
		tx.commit();
		session.close();
		return entity;
	}

	public void saveOrUpdate(final List<T> entities) throws HibernateException {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		for (T e : entities) {
			session.saveOrUpdate(e);
		}
		tx.commit();
		session.close();
	}

	public void deleteAll() throws HibernateException {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		String hql = "DELETE FROM " + clazz.getName();
		session.createQuery(hql).executeUpdate();
		tx.commit();
		session.close();
	}

	public void deleteById(final long id) throws HibernateException {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		String hql = "DELETE FROM " + clazz.getName() + " WHERE id= :id";
		session.createQuery(hql).setParameter("id", id).executeUpdate();
		tx.commit();
		session.close();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}