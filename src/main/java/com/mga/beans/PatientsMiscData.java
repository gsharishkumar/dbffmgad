package com.mga.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patientsmisc", propOrder = { "code", "status", "count", "patient" })
@XmlRootElement(name = "patients")
public class PatientsMiscData {

	@XmlElement(name = "code", required = true)
	private Integer code;

	@XmlElement(name = "status", required = true)
	private String status;

	@XmlElement(name = "count", required = true)
	private Integer count;

	@XmlElement(name = "patient_miscdata", required = false)
	private List<PatientMiscData> patient;

	public List<PatientMiscData> getPatient() {
		return patient;
	}

	public void setPatient(List<PatientMiscData> patient) {
		this.patient = patient;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
