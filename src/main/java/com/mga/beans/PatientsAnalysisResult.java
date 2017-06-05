package com.mga.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patientsanalysis", propOrder = { "patientId", "status", "date", 
		"patientAnalysisResult" })
@XmlRootElement(name = "patients")
public class PatientsAnalysisResult {
	
	@XmlElement(name = "patient_id", required = true)
	private Integer patientId;

	@XmlElement(name = "status", required = true)
	private Status status;
	
	@XmlElement(name = "date", required = false)
	private DatePeriod date;
	
	@XmlElement(name = "result", required = false)
	private List<PatientAnalysisResult> patientAnalysisResult;

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<PatientAnalysisResult> getPatientAnalysisResult() {
		return patientAnalysisResult;
	}

	public void setPatientAnalysisResult(
			List<PatientAnalysisResult> patientAnalysisResult) {
		this.patientAnalysisResult = patientAnalysisResult;
	}

	public DatePeriod getDate() {
		return date;
	}

	public void setDate(DatePeriod date) {
		this.date = date;
	}

}
