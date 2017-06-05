package com.mga.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "doctor", propOrder = {"id", "name", "medicalCenter", "notes" })
@XmlRootElement(name = "doctor")
public class Doctor {
	
	@XmlElement(name = "id", required = true)
	private String id;

	@XmlElement(name = "name", required = true)
	private String name;

	@XmlElement(name = "medicalCenter", required = true)
	private String medicalCenter;

	@XmlElement(name = "notes", required = true)
	private String notes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getmedicalCenter() {
		return medicalCenter;
	}

	public void setmedicalCenter(String medicalCenter) {
		this.medicalCenter = medicalCenter;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getMedicalCenter() {
		return medicalCenter;
	}

	public void setMedicalCenter(String medicalCenter) {
		this.medicalCenter = medicalCenter;
	}

}
