package com.cogivui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cogivui.dao.FeedbackDao;
import com.cogivui.dao.GenericDao;
import com.cogivui.model.Feedback;

@Service("feedbackService")
@Transactional
public class FeedbackService extends GenericService<Feedback> {

	@Autowired
	private FeedbackDao feedbackDao;

	@Override
	public GenericDao<Feedback> getDefaultDao() {
		return feedbackDao;
	}
}
