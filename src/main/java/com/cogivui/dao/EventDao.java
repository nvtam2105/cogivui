package com.cogivui.dao;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
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

		Date today = new Date();
		criteria.add(Restrictions.or(
		        DaoUtils.equalsOrGreaterThanIgnoreTime("time.startDate", today),
		        Restrictions.and(DaoUtils.lessThanIgnoreTime("time.startDate", today),
		                DaoUtils.equalsOrGreaterThanIgnoreTime("time.endDate", today))));

		criteria.addOrder(Order.asc("status"));
		criteria.addOrder(Order.asc("time.startDate"));

		List<Event> list = criteria.list();
		for (Event e : list) {
			e.setStartDate(e.getTime().getStartDate());
			e.setPlaceAddress(e.getPlace().getAddress());
			if (e.getPlace().getCityId() == 1) {
				e.setCityName("SAI GON");
			} else if (e.getPlace().getCityId() == 2) {
				e.setCityName("HA NOI");
			} else {
				e.setCityName("DA NANG");
			}
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

		if (!eventCriteria.getCategoryIds().isEmpty()) {
			criteria.createAlias("categories", "categories");
		}

		criteria.add(Restrictions.eq("status", 1)); // APPROVED
		Date dateAfterTom = DateUtils.addDays(new Date(), 2);

		criteria.add(Restrictions.or(
		        DaoUtils.equalsOrGreaterThanIgnoreTime("time.startDate", dateAfterTom),
		        Restrictions.and(DaoUtils.lessThanIgnoreTime("time.startDate", dateAfterTom),
		                DaoUtils.equalsOrGreaterThanIgnoreTime("time.endDate", dateAfterTom))));

		if (!eventCriteria.getCategoryIds().isEmpty()) {
			criteria.add(Restrictions.in("categories.id", eventCriteria.getCategoryIds()));
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
			e.setStartDate(e.getTime().getStartDate());
			e.setPlaceAddress(e.getPlace().getAddress());
			if (e.getPlace().getCityId() == 1) {
				e.setCityName("SAI GON");
			} else if (e.getPlace().getCityId() == 2) {
				e.setCityName("HA NOI");
			} else {
				e.setCityName("DA NANG");
			}

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

		if (!eventCriteria.getCategoryIds().isEmpty()) {
			criteria.createAlias("categories", "categories");
		}

		criteria.add(Restrictions.eq("status", 1)); // APPROVED

		Date today = new Date();

		criteria.add(DaoUtils.equalsOrLessThanIgnoreTime("time.startDate", today));
		criteria.add(DaoUtils.equalsOrGreaterThanIgnoreTime("time.endDate", today));

		criteria.addOrder(Order.asc("time.startDate"));
		if (!eventCriteria.getCategoryIds().isEmpty()) {
			criteria.add(Restrictions.in("categories.id", eventCriteria.getCategoryIds()));
		}
		if (eventCriteria.getCityId() > 0) {
			criteria.add(Restrictions.eq("place.cityId", eventCriteria.getCityId()));
		}

		if (eventCriteria.getOffset() >= 0) {
			criteria.setFirstResult(eventCriteria.getOffset());
		}

		if (eventCriteria.getLimit() >= 0) {
			criteria.setMaxResults(eventCriteria.getLimit());
		}
		List<Event> list = criteria.list();
		for (Event e : list) {
			e.setStartDate(e.getTime().getStartDate());
			e.setPlaceAddress(e.getPlace().getAddress());

			if (e.getPlace().getCityId() == 1) {
				e.setCityName("SAI GON");
			} else if (e.getPlace().getCityId() == 2) {
				e.setCityName("HA NOI");
			} else {
				e.setCityName("DA NANG");
			}

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

		if (!eventCriteria.getCategoryIds().isEmpty()) {
			criteria.createAlias("categories", "categories");
		}

		criteria.add(Restrictions.eq("status", 1)); // APPROVED

		Date tomorrow = DateUtils.addDays(new Date(), 1);

		criteria.add(DaoUtils.equalsOrLessThanIgnoreTime("time.startDate", tomorrow));
		criteria.add(DaoUtils.equalsOrGreaterThanIgnoreTime("time.endDate", tomorrow));
		criteria.addOrder(Order.asc("time.startDate"));
		if (!eventCriteria.getCategoryIds().isEmpty()) {
			criteria.add(Restrictions.in("categories.id", eventCriteria.getCategoryIds()));
		}

		if (eventCriteria.getCityId() > 0) {
			criteria.add(Restrictions.eq("place.cityId", eventCriteria.getCityId()));
		}

		if (eventCriteria.getOffset() >= 0) {
			criteria.setFirstResult(eventCriteria.getOffset());
		}

		if (eventCriteria.getLimit() >= 0) {
			criteria.setMaxResults(eventCriteria.getLimit());
		}
		List<Event> list = criteria.list();
		for (Event e : list) {
			e.setStartDate(e.getTime().getStartDate());
			e.setPlaceAddress(e.getPlace().getAddress());
			if (e.getPlace().getCityId() == 1) {
				e.setCityName("SAI GON");
			} else if (e.getPlace().getCityId() == 2) {
				e.setCityName("HA NOI");
			} else {
				e.setCityName("DA NANG");
			}

			Hibernate.initialize(e.getCategories());
			Hibernate.initialize(e.getTags());
		}
		session.close();
		return list;
	}

	public boolean eventIsExist(Event event) throws HibernateException {
		session = sessionFactory.openSession();
		String sql = " SELECT COUNT(*) "
		        + " FROM EVENT e INNER JOIN TIME t ON e.TIME_ID = t.TIME_ID "
		        + " WHERE e.NAME = :name "
		        + " AND DATE(t.START_DATE) = DATE(:startDate) AND t.HOUR_START_DATE = :hourStartDate AND t.MIN_START_DATE = :minStartDate AND t.START_PERIOD = :startPeriod"
		        + " AND DATE(t.END_DATE) = DATE(:endDate) AND t.HOUR_END_DATE = :hourEndDate AND t.MIN_END_DATE = :minEndDate AND t.END_PERIOD = :endPeriod ";

		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter("name", event.getName());
		query.setParameter("startDate", event.getTime().getStartDate());
		query.setParameter("hourStartDate", event.getTime().getHourStartDate());
		query.setParameter("minStartDate", event.getTime().getMinStartDate());
		query.setParameter("startPeriod", event.getTime().getStartPeriod().name());
		query.setParameter("endDate", event.getTime().getEndDate());
		query.setParameter("hourEndDate", event.getTime().getHourEndDate());
		query.setParameter("minEndDate", event.getTime().getMinEndDate());
		query.setParameter("endPeriod", event.getTime().getEndPeriod().name());

		BigInteger size = (BigInteger) query.uniqueResult();
		session.close();
		return size != null ? size.intValue() > 0 : false;
	}

	public void saveNewEvents(List<Event> events) throws HibernateException {
		for (Event e : events) {
			if (!eventIsExist(e)) {
				saveOrUpdate(e);
			}
		}
	}

	public void saveOrUpdateOrCloneEvent(Event event) {
		session = sessionFactory.openSession();
		Date startDate = event.getTime().getStartDate();
		Date endDate = event.getTime().getEndDate();
		if (!DateUtils.isSameDay(startDate, endDate)) {
			Calendar start = Calendar.getInstance();
			start.setTime(DateUtils.addDays(startDate,1));
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);
			for (Date date = start.getTime(); start.equals(end) || start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
				Event temp = Event.copy(event);
				temp.getTime().setId(0);
				temp.getTime().setStartDate(date);
				temp.getTime().setEndDate(date);
				saveOrUpdate(temp);
				System.out.println(date);
			}
		} else {
			saveOrUpdate(event);
		}
		session.close();

	}
}
