package com.cogivui.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public final class DaoUtils {

    private DaoUtils() {
    }

    public static Criterion equal(String propertyName, Date queryDate) {
	return Restrictions.eq(propertyName, queryDate);
    }

    public static Criterion equalOrGreaterThan(String propertyName, Date queryDate) {
	return Restrictions.ge(propertyName, queryDate);
    }

    public static Criterion equalOrLessThan(String propertyName, Date queryDate) {
	return Restrictions.le(propertyName, queryDate);
    }

    public static Criterion lessThanIgnoreTime(String propertyName, Date queryDate) {
	Date dateNoTime = DateUtils.truncate(queryDate, Calendar.DATE);
	return Restrictions.lt(propertyName, dateNoTime);
    }

    public static Criterion greaterThanIgnoreTime(String propertyName, Date queryDate) {
	Date dateNoTime = DateUtils.truncate(queryDate, Calendar.DATE);
	return Restrictions.gt(propertyName, dateNoTime);
    }

    public static Criterion equalIgnoreTime(String propertyName, Date queryDate) {
	Date dateNoTime = DateUtils.truncate(queryDate, Calendar.DATE);
	return Restrictions.eq(propertyName, dateNoTime);
    }

    public static Criterion equalsOrGreaterThanIgnoreTime(String propertyName, Date queryDate) {
	Date dateNoTime = DateUtils.truncate(queryDate, Calendar.DATE);
	return Restrictions.ge(propertyName, dateNoTime);
    }

    public static Criterion equalsOrLessThanIgnoreTime(String propertyName, Date queryDate) {
	Date dateNoTime = DateUtils.truncate(queryDate, Calendar.DATE);
	return Restrictions.le(propertyName, dateNoTime);
    }

}