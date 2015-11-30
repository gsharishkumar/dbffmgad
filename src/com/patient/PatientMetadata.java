package com.patient;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "gender",
    "dob",
    "age",
    "weight",
    "height"
})
@XmlRootElement(name = "patient")
public class PatientMetadata {
	
	@XmlElement(name = "name", required = true)
	private String name;
	@XmlElement(name = "gender", required = true) 
	private String gender;
	@XmlElement(name = "dob", required = true) 
	private Date dob;
	@XmlElement(name = "age", required = true) 
	private Integer age;
	@XmlElement(name = "weight", required = true) 
	private float weight;
	@XmlElement(name = "height", required = true) 
	private float height;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
}