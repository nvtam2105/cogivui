package com.cogivui.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.deser.std.DateDeserializer;

@Entity
@Table(name = "TIME")
@AttributeOverride(name = "id", column = @Column(name = "TIME_ID"))
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Time extends BaseEntity {

	private static final long serialVersionUID = 6851448667295051157L;
	
	private Date startDate;
	private int hourStartDate;
	private int minStartDate;
	private PeriodTime startPeriod = PeriodTime.AM;

	private Date endDate;
	private int hourEndDate;
	private int minEndDate;
	private PeriodTime endPeriod = PeriodTime.AM;
	
	public Time() {
		
	}
	
	public Time (String time) {
		
	}

	@Column(name = "START_DATE", nullable = false)
	@JsonDeserialize(using = DateDeserializer.class)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "HOUR_START_DATE")
	public int getHourStartDate() {
		return hourStartDate;
	}

	public void setHourStartDate(int hourStartDate) {
		this.hourStartDate = hourStartDate;
	}

	@Column(name = "MIN_START_DATE")
	public int getMinStartDate() {
		return minStartDate;
	}

	public void setMinStartDate(int minStartDate) {
		this.minStartDate = minStartDate;
	}

	@Column(name = "END_DATE")
	@JsonDeserialize(using = DateDeserializer.class)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "HOUR_END_DATE")
	public int getHourEndDate() {
		return hourEndDate;
	}

	public void setHourEndDate(int hourEndDate) {
		this.hourEndDate = hourEndDate;
	}

	@Column(name = "MIN_END_DATE")
	public int getMinEndDate() {
		return minEndDate;
	}

	public void setMinEndDate(int minEndDate) {
		this.minEndDate = minEndDate;
	}

	@Column(name = "START_PERIOD")
	@Enumerated(EnumType.STRING)
	public PeriodTime getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(PeriodTime startPeriod) {
		this.startPeriod = startPeriod;
	}

	@Column(name = "END_PERIOD")
	@Enumerated(EnumType.STRING)
	public PeriodTime getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(PeriodTime endPeriod) {
		this.endPeriod = endPeriod;
	}

}
