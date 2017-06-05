package com.mga.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sole_data_files", propOrder = { "patientId", "numberOfFiles",
		"fileIds", "stressThreshold" })
@XmlRootElement(name = "sole_data_files")
public class SoleDataFilesForAnalysisInput {

	@XmlElement(name = "id", required = true)
	private Integer patientId;

	@XmlElement(name = "number_of_files", required = true)
	private Integer numberOfFiles;

	@XmlElement(name = "file_ids", required = false)
	private String fileIds;
	
	@XmlElement(name = "stress_threshold", required = false)
	private float stressThreshold;

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public Integer getNumberOfFiles() {
		return numberOfFiles;
	}

	public void setNumberOfFiles(Integer numberOfFiles) {
		this.numberOfFiles = numberOfFiles;
	}

	public float getStressThreshold() {
		return stressThreshold;
	}

	public void setStressThreshold(float stressThreshold) {
		this.stressThreshold = stressThreshold;
	}

}
