package com.mga.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlType;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patient_metadata", propOrder = { "code", "status", "firstName", "lastName",
		"gender", "birthDate", "age", "contactNumber", "emailId",
		"houseNumber", "street", "postalCode", "city", "country", "weight",
		"height", "notes" })
@XmlRootElement(name = "patient_metadata")
public class PatientMetadata {
	
	@XmlElement(name = "code", required = true)
	private Integer code;
	
	@XmlElement(name = "status", required = true)
	private String status;

	@XmlElement(name = "first_name", required = true)
	private String firstName;

	@XmlElement(name = "last_name", required = true)
	private String lastName;

	@XmlElement(name = "gender", required = true)
	private String gender;

	// @XmlElement(name = "birth_date")
	// private Date birthDate;

	@XmlElement(name = "birth_date")
	private String birthDate;

	@XmlElement(name = "age", required = true)
	private Integer age;

	@XmlElement(name = "contact_number")
	private String contactNumber;

	@XmlElement(name = "email_id")
	private String emailId;

	@XmlElement(name = "house_number")
	private String houseNumber;

	@XmlElement(name = "street")
	private String street;

	@XmlElement(name = "postal_code")
	private String postalCode;

	@XmlElement(name = "city", required = true)
	private String city;

	@XmlElement(name = "country", required = true)
	private String country;

	@XmlElement(name = "weight", required = true)
	private float weight;

	@XmlElement(name = "height", required = true)
	private float height;

	@XmlElement(name = "notes")
	private String notes;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/*
	 * public Date getBirthDate() { return birthDate; }
	 * 
	 * public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
	 */

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	

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

}