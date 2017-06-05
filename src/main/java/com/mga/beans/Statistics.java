package com.mga.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statistics", propOrder = { "minLeftPressure",
		"minRightPressure", "maxLeftPressure", "maxRightPressure",
		"meanLeftPressure", "meanRightPressure", "varLeftPressure", "varRightPressure", "sdLeftPressure",
		"sdRightPressure" })
@XmlRootElement(name = "statistics")
public class Statistics {

	@XmlElement(name = "min_left_pressure", required = false)
	private float minLeftPressure;

	@XmlElement(name = "min_right_pressure", required = false)
	private float minRightPressure;

	@XmlElement(name = "max_left_pressure", required = false)
	private float maxLeftPressure;

	@XmlElement(name = "max_right_pressure", required = false)
	private float maxRightPressure;

	@XmlElement(name = "mean_left_pressure", required = false)
	private float meanLeftPressure;

	@XmlElement(name = "mean_right_pressure", required = false)
	private float meanRightPressure;

	@XmlElement(name = "variance_left_pressure", required = false)
	private float varLeftPressure;

	@XmlElement(name = "variance_right_pressure", required = false)
	private float varRightPressure;
	
	@XmlElement(name = "stddev_left_pressure", required = false)
	private float sdLeftPressure;

	@XmlElement(name = "stddev_right_pressure", required = false)
	private float sdRightPressure;

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

	public float getSdLeftPressure() {
		return sdLeftPressure;
	}

	public void setSdLeftPressure(float sdLeftPressure) {
		this.sdLeftPressure = sdLeftPressure;
	}

	public float getSdRightPressure() {
		return sdRightPressure;
	}

	public void setSdRightPressure(float sdRightPressure) {
		this.sdRightPressure = sdRightPressure;
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
