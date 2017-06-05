package com.mga.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patientFScore", propOrder = { "id", "FunctionName",
		"functionValue" })
@XmlRootElement(name = "patient")
public class PatientFScore {

	@XmlElement(name = "id", required = true)
	private Integer id;

	@XmlElement(name = "function_score_name", required = true)
	private String FunctionName;

	@XmlElement(name = "function_score_value", required = true)
	private String functionValue;

	public String getFunctionName() {
		return FunctionName;
	}

	public void setFunctionName(String functionName) {
		FunctionName = functionName;
	}

	public String getFunctionValue() {
		return functionValue;
	}

	public void setFunctionValue(String functionValue) {
		this.functionValue = functionValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
