package com.mga.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "doctor_metadata", propOrder = { "code", "status", "doctorId", "firstName",
		"lastName", "medicalCenter", "notes" })
@XmlRootElement(name = "doctor_metadata")
public class DoctorMetadata {

	@XmlElement(name = "code", required = false)
	private Integer code;
	
	@XmlElement(name = "status", required = false)
	private String status;
	
	@XmlElement(name = "doctor_id", required = true)
	private String doctorId;

	@XmlElement(name = "first_name", required = true)
	private String firstName;

	@XmlElement(name = "last_name", required = true)
	private String lastName;

	@XmlElement(name = "medical_center", required = true)
	private String medicalCenter;

	@XmlElement(name = "notes", required = false)
	private String notes;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getMedicalCenter() {
		return medicalCenter;
	}

	public void setMedicalCenter(String medicalCenter) {
		this.medicalCenter = medicalCenter;
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
