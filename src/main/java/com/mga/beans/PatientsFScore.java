package com.mga.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patientsFScore", propOrder = { "code", "status", "count",
		"patientFScore" })
@XmlRootElement(name = "patients")
public class PatientsFScore {

	@XmlElement(name = "code", required = true)
	private Integer code;

	@XmlElement(name = "status", required = true)
	private String status;

	@XmlElement(name = "count", required = false)
	private int count;

	@XmlElement(name = "patient_fscore", required = false)
	private List<PatientFScore> patientFScore;

	public List<PatientFScore> getpatientFScore() {
		return patientFScore;
	}

	public void setPatientFScore(List<PatientFScore> patientFScore) {
		this.patientFScore = patientFScore;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
