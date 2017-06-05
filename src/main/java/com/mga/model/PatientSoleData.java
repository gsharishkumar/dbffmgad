package com.mga.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patient_sole_data", schema = "medical_gate_analysis")
public class PatientSoleData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "patient_id")
	private Integer patientId;

	@Column(name = "file")
	private String file;

	@Column(name = "time")
	private Timestamp time;
	
	@Column(name = "time_difference")
	private Integer timeDifference;

	@Column(name = "left_pressure0")
	private Float leftPressure0;

	@Column(name = "left_pressure1")
	private Float leftPressure1;

	@Column(name = "left_pressure2")
	private Float leftPressure2;

	@Column(name = "left_pressure3")
	private Float leftPressure3;

	@Column(name = "left_pressure4")
	private Float leftPressure4;

	@Column(name = "left_pressure5")
	private Float leftPressure5;

	@Column(name = "left_pressure6")
	private Float leftPressure6;

	@Column(name = "left_pressure7")
	private Float leftPressure7;

	@Column(name = "left_pressure8")
	private Float leftPressure8;

	@Column(name = "left_pressure9")
	private Float leftPressure9;

	@Column(name = "left_pressure10")
	private Float leftPressure10;

	@Column(name = "left_pressure11")
	private Float leftPressure11;

	@Column(name = "left_pressure12")
	private Float leftPressure12;

	@Column(name = "left_acceleration_x")
	private Float leftAccelerationX;

	@Column(name = "left_acceleration_y")
	private Float leftAccelerationY;

	@Column(name = "left_acceleration_z")
	private Float leftAccelerationZ;

	@Column(name = "left_total_force")
	private Float leftTotalForce;

	@Column(name = "left_cop_x")
	private Float leftCopX;

	@Column(name = "left_cop_y")
	private Float leftCopY;

	@Column(name = "left_temperature")
	private Float leftTemperature;

	@Column(name = "right_pressure0")
	private Float rightPressure0;

	@Column(name = "right_pressure1")
	private Float rightPressure1;

	@Column(name = "right_pressure2")
	private Float rightPressure2;

	@Column(name = "right_pressure3")
	private Float rightPressure3;

	@Column(name = "right_pressure4")
	private Float rightPressure4;

	@Column(name = "right_pressure5")
	private Float rightPressure5;

	@Column(name = "right_pressure6")
	private Float rightPressure6;

	@Column(name = "right_pressure7")
	private Float rightPressure7;

	@Column(name = "right_pressure8")
	private Float rightPressure8;

	@Column(name = "right_pressure9")
	private Float rightPressure9;

	@Column(name = "right_pressure10")
	private Float rightPressure10;

	@Column(name = "right_pressure11")
	private Float rightPressure11;

	@Column(name = "right_pressure12")
	private Float rightPressure12;

	@Column(name = "right_acceleration_x")
	private Float rightAccelerationX;

	@Column(name = "right_acceleration_y")
	private Float rightAccelerationY;

	@Column(name = "right_acceleration_z")
	private Float rightAccelerationZ;

	@Column(name = "right_total_force")
	private Float rightTotalForce;

	@Column(name = "right_cop_x")
	private Float rightCopX;

	@Column(name = "right_cop_y")
	private Float rightCopY;

	@Column(name = "right_temperature")
	private Float rightTemperature;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getLeftPressure0() {
		return leftPressure0;
	}

	public void setLeftPressure0(Float leftPressure0) {
		this.leftPressure0 = leftPressure0;
	}

	public Float getLeftPressure1() {
		return leftPressure1;
	}

	public void setLeftPressure1(Float leftPressure1) {
		this.leftPressure1 = leftPressure1;
	}

	public Float getLeftPressure2() {
		return leftPressure2;
	}

	public void setLeftPressure2(Float leftPressure2) {
		this.leftPressure2 = leftPressure2;
	}

	public Float getLeftPressure3() {
		return leftPressure3;
	}

	public void setLeftPressure3(Float leftPressure3) {
		this.leftPressure3 = leftPressure3;
	}

	public Float getLeftPressure4() {
		return leftPressure4;
	}

	public void setLeftPressure4(Float leftPressure4) {
		this.leftPressure4 = leftPressure4;
	}

	public Float getLeftPressure5() {
		return leftPressure5;
	}

	public void setLeftPressure5(Float leftPressure5) {
		this.leftPressure5 = leftPressure5;
	}

	public Float getLeftPressure6() {
		return leftPressure6;
	}

	public void setLeftPressure6(Float leftPressure6) {
		this.leftPressure6 = leftPressure6;
	}

	public Float getLeftPressure7() {
		return leftPressure7;
	}

	public void setLeftPressure7(Float leftPressure7) {
		this.leftPressure7 = leftPressure7;
	}

	public Float getLeftPressure8() {
		return leftPressure8;
	}

	public void setLeftPressure8(Float leftPressure8) {
		this.leftPressure8 = leftPressure8;
	}

	public Float getLeftPressure9() {
		return leftPressure9;
	}

	public void setLeftPressure9(Float leftPressure9) {
		this.leftPressure9 = leftPressure9;
	}

	public Float getLeftPressure10() {
		return leftPressure10;
	}

	public void setLeftPressure10(Float leftPressure10) {
		this.leftPressure10 = leftPressure10;
	}

	public Float getLeftPressure11() {
		return leftPressure11;
	}

	public void setLeftPressure11(Float leftPressure11) {
		this.leftPressure11 = leftPressure11;
	}

	public Float getLeftPressure12() {
		return leftPressure12;
	}

	public void setLeftPressure12(Float leftPressure12) {
		this.leftPressure12 = leftPressure12;
	}

	public Float getLeftAccelerationX() {
		return leftAccelerationX;
	}

	public void setLeftAccelerationX(Float leftAccelerationX) {
		this.leftAccelerationX = leftAccelerationX;
	}

	public Float getLeftAccelerationY() {
		return leftAccelerationY;
	}

	public void setLeftAccelerationY(Float leftAccelerationY) {
		this.leftAccelerationY = leftAccelerationY;
	}

	public Float getLeftAccelerationZ() {
		return leftAccelerationZ;
	}

	public void setLeftAccelerationZ(Float leftAccelerationZ) {
		this.leftAccelerationZ = leftAccelerationZ;
	}

	public Float getLeftTotalForce() {
		return leftTotalForce;
	}

	public void setLeftTotalForce(Float leftTotalForce) {
		this.leftTotalForce = leftTotalForce;
	}

	public Float getLeftCopX() {
		return leftCopX;
	}

	public void setLeftCopX(Float leftCopX) {
		this.leftCopX = leftCopX;
	}

	public Float getLeftCopY() {
		return leftCopY;
	}

	public void setLeftCopY(Float leftCopY) {
		this.leftCopY = leftCopY;
	}

	public Float getLeftTemperature() {
		return leftTemperature;
	}

	public void setLeftTemperature(Float leftTemperature) {
		this.leftTemperature = leftTemperature;
	}

	public Float getRightPressure0() {
		return rightPressure0;
	}

	public void setRightPressure0(Float rightPressure0) {
		this.rightPressure0 = rightPressure0;
	}

	public Float getRightPressure1() {
		return rightPressure1;
	}

	public void setRightPressure1(Float rightPressure1) {
		this.rightPressure1 = rightPressure1;
	}

	public Float getRightPressure2() {
		return rightPressure2;
	}

	public void setRightPressure2(Float rightPressure2) {
		this.rightPressure2 = rightPressure2;
	}

	public Float getRightPressure3() {
		return rightPressure3;
	}

	public void setRightPressure3(Float rightPressure3) {
		this.rightPressure3 = rightPressure3;
	}

	public Float getRightPressure4() {
		return rightPressure4;
	}

	public void setRightPressure4(Float rightPressure4) {
		this.rightPressure4 = rightPressure4;
	}

	public Float getRightPressure5() {
		return rightPressure5;
	}

	public void setRightPressure5(Float rightPressure5) {
		this.rightPressure5 = rightPressure5;
	}

	public Float getRightPressure6() {
		return rightPressure6;
	}

	public void setRightPressure6(Float rightPressure6) {
		this.rightPressure6 = rightPressure6;
	}

	public Float getRightPressure7() {
		return rightPressure7;
	}

	public void setRightPressure7(Float rightPressure7) {
		this.rightPressure7 = rightPressure7;
	}

	public Float getRightPressure8() {
		return rightPressure8;
	}

	public void setRightPressure8(Float rightPressure8) {
		this.rightPressure8 = rightPressure8;
	}

	public Float getRightPressure9() {
		return rightPressure9;
	}

	public void setRightPressure9(Float rightPressure9) {
		this.rightPressure9 = rightPressure9;
	}

	public Float getRightPressure10() {
		return rightPressure10;
	}

	public void setRightPressure10(Float rightPressure10) {
		this.rightPressure10 = rightPressure10;
	}

	public Float getRightPressure11() {
		return rightPressure11;
	}

	public void setRightPressure11(Float rightPressure11) {
		this.rightPressure11 = rightPressure11;
	}

	public Float getRightPressure12() {
		return rightPressure12;
	}

	public void setRightPressure12(Float rightPressure12) {
		this.rightPressure12 = rightPressure12;
	}

	public Float getRightAccelerationX() {
		return rightAccelerationX;
	}

	public void setRightAccelerationX(Float rightAccelerationX) {
		this.rightAccelerationX = rightAccelerationX;
	}

	public Float getRightAccelerationY() {
		return rightAccelerationY;
	}

	public void setRightAccelerationY(Float rightAccelerationY) {
		this.rightAccelerationY = rightAccelerationY;
	}

	public Float getRightAccelerationZ() {
		return rightAccelerationZ;
	}

	public void setRightAccelerationZ(Float rightAccelerationZ) {
		this.rightAccelerationZ = rightAccelerationZ;
	}

	public Float getRightTotalForce() {
		return rightTotalForce;
	}

	public void setRightTotalForce(Float rightTotalForce) {
		this.rightTotalForce = rightTotalForce;
	}

	public Float getRightCopX() {
		return rightCopX;
	}

	public void setRightCopX(Float rightCopX) {
		this.rightCopX = rightCopX;
	}

	public Float getRightCopY() {
		return rightCopY;
	}

	public void setRightCopY(Float rightCopY) {
		this.rightCopY = rightCopY;
	}

	public Float getRightTemperature() {
		return rightTemperature;
	}

	public void setRightTemperature(Float rightTemperature) {
		this.rightTemperature = rightTemperature;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getTimeDifference() {
		return timeDifference;
	}

	public void setTimeDifference(Integer timeDifference) {
		this.timeDifference = timeDifference;
	}

}
