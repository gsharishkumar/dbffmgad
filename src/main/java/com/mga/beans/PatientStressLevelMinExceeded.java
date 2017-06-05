package com.mga.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patient", propOrder = {
    "code",
    "status",
    "stressThreshold",
    "minutesExceededLeft",
    "minutesExceededRight"
})
@XmlRootElement(name = "patient")
public class PatientStressLevelMinExceeded {
	
	@XmlElement(name = "code", required = false)
	private Integer code;
	
	@XmlElement(name = "status", required = false)
	private String status;
	
	@XmlElement(name = "stress_threshold", required = false)
	private float stressThreshold;
	
	@XmlElement(name = "minutes_exceeded_left", required = false)
	private float minutesExceededLeft;
	
	@XmlElement(name = "minutes_exceeded_right", required = false)
	private float minutesExceededRight;

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

	public float getMinutesExceededLeft() {
		return minutesExceededLeft;
	}

	public void setMinutesExceededLeft(float minutesExceededLeft) {
		this.minutesExceededLeft = minutesExceededLeft;
	}

	public float getMinutesExceededRight() {
		return minutesExceededRight;
	}

	public void setMinutesExceededRight(float minutesExceededRight) {
		this.minutesExceededRight = minutesExceededRight;
	}

	public float getStressThreshold() {
		return stressThreshold;
	}

	public void setStressThreshold(float stressThreshold) {
		this.stressThreshold = stressThreshold;
	}


}
