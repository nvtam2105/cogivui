package com.cogivui.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cogivui.model.Category;

@Repository("categoryDao")
public class CategoryDao extends GenericDao<Category> {

	public CategoryDao() {
		setClazz(Category.class);
	}

	@Autowired
	SessionFactory sessionFactory;

}
