package com.mga.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patient_time", propOrder = {
    "restingTimeLeft",
    "restingTimeRight",
    "minOfActivityLeft",
    "minOfActivityRight"
})
@XmlRootElement(name = "patient_time")
public class PatientRestingAndMinOfActivityTime {
	
	@XmlElement(name = "resting_time_left", required = false)
	private float restingTimeLeft;
	
	@XmlElement(name = "resting_time_right", required = false)
	private float restingTimeRight;
	
	@XmlElement(name = "min_of_activity_left", required = false)
	private float minOfActivityLeft;
	
	@XmlElement(name = "min_of_activity_right", required = false)
	private float minOfActivityRight;

	/*public Integer getCode() {
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
*/
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

}
