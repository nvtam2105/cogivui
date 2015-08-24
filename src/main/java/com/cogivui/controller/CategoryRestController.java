package com.cogivui.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cogivui.message.Status;
import com.cogivui.model.Category;
import com.cogivui.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryRestController {

	static final Logger logger = Logger.getLogger(CategoryRestController.class);

	@Autowired
	CategoryService categoryService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	List<Category> getCategory() {
		List<Category> categories = null;
		try {
			categories = categoryService.getAll();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status udpateCategory(@PathVariable("id") long id, @RequestBody Category ca) {
		try {
			ca.setId(id);
			categoryService.saveOrUpdate(ca);
			return new Status(1, "Category updated successfully !");
		} catch (Exception e) {
			return new Status(0, e.toString());
		}

	}

}
