package com.mga.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result", propOrder = { "file", "date", "statistics",
		"restingAndMoaTime", "stressLevelExceededTime" })
@XmlRootElement(name = "result")
public class PatientAnalysisResult {

	/*@XmlElement(name = "id", required = true)
	private Integer id;*/

	@XmlElement(name = "file", required = false)
	private String file;
	
	@XmlElement(name = "date", required = false)
	private String date;
	

	@XmlElement(name = "statistics", required = false)
	private Statistics statistics;

	@XmlElement(name = "resting_and_moa_time", required = false)
	private PatientRestingAndMinOfActivityTime restingAndMoaTime;
	
	@XmlElement(name = "stress_level_exceeded_time", required = false)
	private PatientStressLevelMinExceeded stressLevelExceededTime;

/*	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}*/

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public PatientRestingAndMinOfActivityTime getRestingAndMoaTime() {
		return restingAndMoaTime;
	}

	public void setRestingAndMoaTime(
			PatientRestingAndMinOfActivityTime restingAndMoaTime) {
		this.restingAndMoaTime = restingAndMoaTime;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public PatientStressLevelMinExceeded getStressLevelExceededTime() {
		return stressLevelExceededTime;
	}

	public void setStressLevelExceededTime(
			PatientStressLevelMinExceeded stressLevelExceededTime) {
		this.stressLevelExceededTime = stressLevelExceededTime;
	}

}
