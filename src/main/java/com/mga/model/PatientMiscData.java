package com.mga.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patient_misc_data", schema = "medical_gate_analysis")
public class PatientMiscData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	
	@Column(name = "patient_id")
	private Integer patientId;
	
	@Column(name = "pain_level_nrs")
	private Integer painLevelNRS;
	
	@Column(name = "pain_level_vas")
	private Integer painLevelVAS;
			
	@Column(name = "date")
	private Date date;
	
	@Column(name = "content_level")
	private Integer contentLevel;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Integer getPainLevelNRS() {
		return painLevelNRS;
	}

	public void setPainLevelNRS(Integer painLevelNRS) {
		this.painLevelNRS = painLevelNRS;
	}

	public Integer getPainLevelVAS() {
		return painLevelVAS;
	}

	public void setPainLevelVAS(Integer painLevelVAS) {
		this.painLevelVAS = painLevelVAS;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getContentLevel() {
		return contentLevel;
	}

	public void setContentLevel(Integer contentLevel) {
		this.contentLevel = contentLevel;
	}

}
