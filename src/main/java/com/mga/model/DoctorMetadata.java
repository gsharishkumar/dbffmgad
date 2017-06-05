package com.mga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doctor_metadata", schema = "medical_gate_analysis")
public class DoctorMetadata {

	@Id
	@Column(name = "doctor_id")
	private String id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "medical_center")
	private String medicalCenter;

	@Column(name = "notes")
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

	public String getMedicalCenter() {
		return medicalCenter;
	}

	public void setMedicalCenter(String medicalCenter) {
		this.medicalCenter = medicalCenter;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
