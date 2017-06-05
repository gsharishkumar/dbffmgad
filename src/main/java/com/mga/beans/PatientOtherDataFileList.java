package com.mga.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientOtherDataFileList", propOrder = { "code", "status",
		"OtherDataList"

})
@XmlRootElement(name = "patient_other_data_files_list")
public class PatientOtherDataFileList {

	@XmlElement(name = "code", required = true)
	private Integer code;

	@XmlElement(name = "status", required = true)
	private String status;
	
	@XmlElement(name = "other_data_list", required = false)
	private List<OtherDataList> OtherDataList;

	public List<OtherDataList> getOtherDataList() {
		return OtherDataList;
	}

	public void setOtherDataList(List<OtherDataList> otherDataList) {
		this.OtherDataList = otherDataList;
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
