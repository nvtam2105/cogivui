package com.cogivui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cogivui.criteria.EventCriteria;
import com.cogivui.dao.EventDao;
import com.cogivui.dao.GenericDao;
import com.cogivui.model.Event;

@Service("eventService")
@Transactional
public class EventService extends GenericService<Event> {

	@Autowired
	private EventDao eventDao;

	@Override
	public GenericDao<Event> getDefaultDao() {
		return eventDao;
	}

	public List<Event> getEvents() {
		return eventDao.getEvents();
	}

	public List<Event> getEventsUpComing(EventCriteria eventCriteria) {
		return eventDao.getEventsUpComing(eventCriteria);
	}

	public List<Event> getEventsToday(EventCriteria eventCriteria) {
		return eventDao.getEventsToday(eventCriteria);
	}

	public List<Event> getEventsTomorrow(EventCriteria eventCriteria) {
		return eventDao.getEventsTomorrow(eventCriteria);
    }
}
