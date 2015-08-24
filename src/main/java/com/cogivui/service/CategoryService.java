package com.cogivui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cogivui.dao.CategoryDao;
import com.cogivui.dao.GenericDao;
import com.cogivui.model.Category;

@Service("categoryService")
@Transactional
public class CategoryService extends GenericService<Category> {

	@Autowired
	private CategoryDao categoryDao;

	@Override
	public GenericDao<Category> getDefaultDao() {
		return categoryDao;
	}

}
