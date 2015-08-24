package com.cogivui.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cogivui.model.Feedback;

@Repository("feedbackDao")
public class FeedbackDao extends GenericDao<Feedback> {

	public FeedbackDao() {
		setClazz(Feedback.class);
	}

	@Autowired
	SessionFactory sessionFactory;

}
