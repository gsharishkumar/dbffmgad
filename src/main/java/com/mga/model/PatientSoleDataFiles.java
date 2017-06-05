package com.mga.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patient_sole_data_files", schema = "medical_gate_analysis")
public class PatientSoleDataFiles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	
	@Column(name = "patient_id")
	private Integer patientId;
	
	@Column(name = "sole_data_txt_file")
	private String soleDataTxtFile;
			
	@Column(name = "sole_data_txt_file_insert_timestamp")
	private Timestamp soleDataTxtFileInsertTimestamp;
	
	@Column(name = "file_location")
	private String fileLocation;
	
	@Column(name = "file_timestamp")
	private Timestamp fileTimestamp;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "min_left_pressure")
    private float minLeftPressure;
	
	@Column(name = "min_right_pressure")
    private float minRightPressure;
	
	@Column(name = "max_left_pressure")
    private float maxLeftPressure;
	
	@Column(name = "max_right_pressure")
    private float maxRightPressure;
	
	@Column(name = "mean_left_pressure")
    private float meanLeftPressure;
	
	@Column(name = "mean_right_pressure")
    private float meanRightPressure;
	
	@Column(name = "var_left_pressure")
    private float varLeftPressure;
	
	@Column(name = "var_right_pressure")
    private float varRightPressure;
	
	@Column(name = "std_dev_left_pressure")
    private float stdDevLeftPressure;
	
	@Column(name = "std_dev_right_pressure")
    private float stdDevRightPressure;
	
	@Column(name = "resting_time_left")
    private float restingTimeLeft;
	
	@Column(name = "resting_time_right")
    private float restingTimeRight;
	
	@Column(name = "min_of_activity_left")
    private float minOfActivityLeft;
	
	@Column(name = "min_of_activity_right")
    private float minOfActivityRight;
	
	@Column(name = "file_content")
    private byte[] fileContent;

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getSoleDataTxtFile() {
		return soleDataTxtFile;
	}

	public void setSoleDataTxtFile(String soleDataTxtFile) {
		this.soleDataTxtFile = soleDataTxtFile;
	}

	public Timestamp getSoleDataTxtFileInsertTimestamp() {
		return soleDataTxtFileInsertTimestamp;
	}

	public void setSoleDataTxtFileInsertTimestamp(
			Timestamp soleDataTxtFileInsertTimestamp) {
		this.soleDataTxtFileInsertTimestamp = soleDataTxtFileInsertTimestamp;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getFileTimestamp() {
		return fileTimestamp;
	}

	public void setFileTimestamp(Timestamp fileTimestamp) {
		this.fileTimestamp = fileTimestamp;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public float getMinLeftPressure() {
		return minLeftPressure;
	}

	public void setMinLeftPressure(float minLeftPressure) {
		this.minLeftPressure = minLeftPressure;
	}

	public float getMinRightPressure() {
		return minRightPressure;
	}

	public void setMinRightPressure(float minRightPressure) {
		this.minRightPressure = minRightPressure;
	}

	public float getMaxLeftPressure() {
		return maxLeftPressure;
	}

	public void setMaxLeftPressure(float maxLeftPressure) {
		this.maxLeftPressure = maxLeftPressure;
	}

	public float getMaxRightPressure() {
		return maxRightPressure;
	}

	public void setMaxRightPressure(float maxRightPressure) {
		this.maxRightPressure = maxRightPressure;
	}

	public float getMeanLeftPressure() {
		return meanLeftPressure;
	}

	public void setMeanLeftPressure(float meanLeftPressure) {
		this.meanLeftPressure = meanLeftPressure;
	}

	public float getMeanRightPressure() {
		return meanRightPressure;
	}

	public void setMeanRightPressure(float meanRightPressure) {
		this.meanRightPressure = meanRightPressure;
	}

	public float getStdDevLeftPressure() {
		return stdDevLeftPressure;
	}

	public void setStdDevLeftPressure(float stdDevLeftPressure) {
		this.stdDevLeftPressure = stdDevLeftPressure;
	}

	public float getStdDevRightPressure() {
		return stdDevRightPressure;
	}

	public void setStdDevRightPressure(float stdDevRightPressure) {
		this.stdDevRightPressure = stdDevRightPressure;
	}

	public float getRestingTimeLeft() {
		return restingTimeLeft;
	}

	public void setRestingTimeLeft(float restingTimeLeft) {
		this.restingTimeLeft = restingTimeLeft;
	}

	public float getRestingTimeRight() {
		return restingTimeRight;
	}

	public void setRestingTimeRight(float restingTimeRight) {
		this.restingTimeRight = restingTimeRight;
	}

	public float getMinOfActivityLeft() {
		return minOfActivityLeft;
	}

	public void setMinOfActivityLeft(float minOfActivityLeft) {
		this.minOfActivityLeft = minOfActivityLeft;
	}

	public float getMinOfActivityRight() {
		return minOfActivityRight;
	}

	public void setMinOfActivityRight(float minOfActivityRight) {
		this.minOfActivityRight = minOfActivityRight;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public float getVarLeftPressure() {
		return varLeftPressure;
	}

	public void setVarLeftPressure(float varLeftPressure) {
		this.varLeftPressure = varLeftPressure;
	}

	public float getVarRightPressure() {
		return varRightPressure;
	}

	public void setVarRightPressure(float varRightPressure) {
		this.varRightPressure = varRightPressure;
	}

	
	
}
