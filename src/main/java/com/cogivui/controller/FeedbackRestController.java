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
import com.cogivui.model.Feedback;
import com.cogivui.service.FeedbackService;

@Controller
@RequestMapping("/feedback")
public class FeedbackRestController {

	static final Logger logger = Logger.getLogger(FeedbackRestController.class);

	@Autowired
	FeedbackService feedbackService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addFeedback(@RequestBody Feedback feedback) {
		try {
			feedbackService.saveOrUpdate(feedback);
			return new Status(1, "Feedback added successfully !");
		} catch (Exception e) {
			return new Status(0, e.toString());
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Feedback getFeedback(@PathVariable("id") long id) {
		Feedback feedback = null;
		try {
			feedback = feedbackService.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feedback;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	List<Feedback> getFeedbacks() {
		List<Feedback> feedbacks = null;
		try {
			feedbacks = feedbackService.getAll();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return feedbacks;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Status deleteFeedback(@PathVariable("id") long id) {
		try {
			feedbackService.deleteById(id);
			return new Status(1, "Event deleted successfully !");
		} catch (Exception e) {
			return new Status(0, e.toString());
		}

	}
}
