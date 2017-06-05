package com.mga.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientSoleDataFileList", propOrder = { "code", "status",
		"SoleDataList"

})
@XmlRootElement(name = "patient_sole_data_files_list")
public class PatientSoleDataFileList {

	@XmlElement(name = "code", required = true)
	private Integer code;

	@XmlElement(name = "status", required = true)
	private String status;

	@XmlElement(name = "sole_data_list", required = true)
	private List<SoleDataList> SoleDataList;

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

	public List<SoleDataList> getSoleDataList() {
		return SoleDataList;
	}

	public void setSoleDataList(List<SoleDataList> soleDataList) {
		SoleDataList = soleDataList;
	}

}
