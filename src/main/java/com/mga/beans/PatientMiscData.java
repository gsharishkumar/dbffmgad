package com.mga.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patient_miscdata", propOrder = { "patientId", "id", "painNrs",
		"painVas", "relatedDate", "contentLevel"

})
@XmlRootElement(name = "patient_miscdata")
public class PatientMiscData {

	@XmlElement(name = "patient_id", required = true)
	private Integer patientId;

	@XmlElement(name = "id", required = false)
	private Integer id;

	@XmlElement(name = "pain_nrs", required = false)
	private Integer painNrs;

	@XmlElement(name = "pain_vas", required = false)
	private Integer painVas;

	@XmlElement(name = "related_date", required = false)
	private String relatedDate;

	@XmlElement(name = "content_level", required = false)
	private Integer contentLevel;

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Integer getPainVas() {
		return painVas;
	}

	public void setPainVas(Integer painVas) {
		this.painVas = painVas;
	}

	public Integer getPainNrs() {
		return painNrs;
	}

	public void setPainNrs(Integer painNrs) {
		this.painNrs = painNrs;
	}

	public String getRelatedDate() {
		return relatedDate;
	}

	public void setRelatedDate(String relatedDate) {
		this.relatedDate = relatedDate;
	}

	public Integer getContentLevel() {
		return contentLevel;
	}

	public void setContentLevel(Integer contentLevel) {
		this.contentLevel = contentLevel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
