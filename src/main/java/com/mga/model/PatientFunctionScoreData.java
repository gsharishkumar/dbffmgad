package com.mga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patient_function_scores_data", schema = "medical_gate_analysis")
public class PatientFunctionScoreData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "patient_id")
	private Integer patientId;

	@Column(name = "function_score_name")
	private String functionScoreName;

	@Column(name = "function_score_value")
	private String functionScoreValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFunctionScoreName() {
		return functionScoreName;
	}

	public void setFunctionScoreName(String functionScoreName) {
		this.functionScoreName = functionScoreName;
	}

	public String getFunctionScoreValue() {
		return functionScoreValue;
	}

	public void setFunctionScoreValue(String functionScoreValue) {
		this.functionScoreValue = functionScoreValue;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

}
