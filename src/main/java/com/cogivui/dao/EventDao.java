package com.cogivui.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cogivui.criteria.EventCriteria;
import com.cogivui.model.Event;
import com.cogivui.utils.DaoUtils;

@Repository("eventDao")
public class EventDao extends GenericDao<Event> {

	public EventDao() {
		setClazz(Event.class);
	}

	@Autowired
	SessionFactory sessionFactory;

	/*
	 * public boolean addCategory(long eventId, long categoryId) { session =
	 * sessionFactory.openSession(); tx = session.beginTransaction(); String sql
	 * =
	 * "INSERT INTO EVENT_CATEGORY SET EVENT_ID =:eventID, CATEGORY_ID =:categoryID"
	 * ; int executeUpdate = session.createSQLQuery(sql).setParameter("eventID",
	 * eventId).setParameter("categoryID", categoryId) .executeUpdate();
	 * tx.commit(); session.close(); return executeUpdate > 0; }
	 * 
	 * public boolean addTag(long eventId, long tagId) { session =
	 * sessionFactory.openSession(); tx = session.beginTransaction(); String sql
	 * = "INSERT INTO EVENT_TAG SET EVENT_ID =:eventID, TAG_ID =:tagID"; int
	 * executeUpdate = session.createSQLQuery(sql).setParameter("eventID",
	 * eventId).setParameter("tagID", tagId).executeUpdate(); tx.commit();
	 * session.close(); return executeUpdate > 0; }
	 */

	@SuppressWarnings("unchecked")
	public List<Event> getEvents() {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Event.class);
		criteria.createAlias("place", "place");
		criteria.createAlias("time", "time");

		criteria.addOrder(Order.asc("time.startDate"));

		List<Event> list = criteria.list();
		for (Event e : list) {
			Hibernate.initialize(e.getCategories());
			Hibernate.initialize(e.getTags());
		}
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Event> getEventsUpComing(EventCriteria eventCriteria) {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Event.class);
		criteria.createAlias("place", "place");
		criteria.createAlias("time", "time");

		if (eventCriteria.getCategoryId() > 0) {
			criteria.createAlias("categories", "categories");
		}
		
		criteria.add(Restrictions.eq("status", 1)); // APPROVED

		Date today = new Date();
		Date tomorrow = DateUtils.addDays(today, 1);

		criteria.add(Restrictions.or(
		        DaoUtils.greaterThanIgnoreTime("time.startDate", today),
		        Restrictions.and(DaoUtils.equalsOrLessThanIgnoreTime("time.startDate", tomorrow),
		                DaoUtils.equalsOrGreaterThanIgnoreTime("time.endDate", tomorrow))));

		if (eventCriteria.getCategoryId() > 0) {
			criteria.add(Restrictions.eq("categories.id", eventCriteria.getCategoryId()));
		}

		if (eventCriteria.getCityId() > 0) {
			criteria.add(Restrictions.eq("place.cityId", eventCriteria.getCityId()));
		}

		criteria.addOrder(Order.asc("time.startDate"));

		if (eventCriteria.getOffset() >= 0) {
			criteria.setFirstResult(eventCriteria.getOffset());
		}

		if (eventCriteria.getLimit() >= 0) {
			criteria.setMaxResults(eventCriteria.getLimit());
		}

		List<Event> list = criteria.list();
		for (Event e : list) {
			Hibernate.initialize(e.getCategories());
			Hibernate.initialize(e.getTags());
		}
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Event> getEventsToday(EventCriteria eventCriteria) {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Event.class);
		criteria.createAlias("place", "place");
		criteria.createAlias("time", "time");
		if (eventCriteria.getCategoryId() > 0) {
			criteria.createAlias("categories", "categories");
		}

		criteria.add(Restrictions.eq("status", 1)); // APPROVED
		
		Date today = new Date();

		criteria.add(DaoUtils.equalsOrLessThanIgnoreTime("time.startDate", today));
		criteria.add(DaoUtils.equalOrGreaterThan("time.endDate", today));
		criteria.addOrder(Order.asc("time.startDate"));
		if (eventCriteria.getCategoryId() > 0) {
			criteria.add(Restrictions.eq("categories.id", eventCriteria.getCategoryId()));
		}
		if (eventCriteria.getCityId() > 0) {
			criteria.add(Restrictions.eq("place.cityId", eventCriteria.getCityId()));
		}

		criteria.addOrder(Order.asc("time.startDate"));

		if (eventCriteria.getOffset() >= 0) {
			criteria.setFirstResult(eventCriteria.getOffset());
		}

		if (eventCriteria.getLimit() >= 0) {
			criteria.setMaxResults(eventCriteria.getLimit());
		}
		List<Event> list = criteria.list();
		for (Event e : list) {
			Hibernate.initialize(e.getCategories());
			Hibernate.initialize(e.getTags());
		}
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Event> getEventsTomorrow(EventCriteria eventCriteria) {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Event.class);
		criteria.createAlias("place", "place");
		criteria.createAlias("time", "time");
		if (eventCriteria.getCategoryId() > 0) {
			criteria.createAlias("categories", "categories");
		}
		
		criteria.add(Restrictions.eq("status", 1)); // APPROVED

		Date tomorrow = DateUtils.addDays(new Date(), 1);

		criteria.add(DaoUtils.equalsOrLessThanIgnoreTime("time.startDate", tomorrow));
		criteria.add(DaoUtils.equalOrGreaterThan("time.endDate", tomorrow));
		criteria.addOrder(Order.asc("time.startDate"));
		if (eventCriteria.getCategoryId() > 0) {
			criteria.add(Restrictions.eq("categories.id", eventCriteria.getCategoryId()));
		}

		if (eventCriteria.getCityId() > 0) {
			criteria.add(Restrictions.eq("place.cityId", eventCriteria.getCityId()));
		}

		criteria.addOrder(Order.asc("time.startDate"));

		if (eventCriteria.getOffset() >= 0) {
			criteria.setFirstResult(eventCriteria.getOffset());
		}

		if (eventCriteria.getLimit() >= 0) {
			criteria.setMaxResults(eventCriteria.getLimit());
		}
		List<Event> list = criteria.list();
		for (Event e : list) {
			Hibernate.initialize(e.getCategories());
			Hibernate.initialize(e.getTags());
		}
		session.close();
		return list;
	}
}
