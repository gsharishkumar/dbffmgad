package com.mga.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dateperiod", propOrder = { "startDate", "endDate" })
@XmlRootElement(name = "date")
public class DatePeriod {
	
	@XmlElement(name = "start_date", required = true)
	private String startDate;
	
	@XmlElement(name = "end_date", required = true)
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate2) {
		this.startDate = startDate2;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
