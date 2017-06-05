(function() {
	window.GATE = (window.GATE || {});
	window.GATE.Medicals = (window.GATE.Medicals || {});

	window.GATE.Medicals.Initialize = function() {
		$('#BackButtonPageHead').attr('href', document.referrer);
	}
	
	window.GATE.Medicals.Add = function() {
		//window.GATE.Master.Notify('Saving new medical data. Do not close this page... ', "yellow");
		var doctor = window.GATE.Medicals.BuildDoctorXML();
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("POST", "patient/doctormetadata/insert", false); 
		xmlHttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlHttp.send("doctor-metadata=" + doctor + "");
		var code = xmlHttp.status;
		var status = xmlHttp.responseText;
		if (code == "500") {
			window.GATE.Master.Notify(status, "red");
		}else{
			window.GATE.Master.Notify(status, "green");
		}
		document.location.href = "Medicals.html";
	}

	
	window.GATE.Medicals.Update = function() {
		//window.GATE.Master.Notify('Updating medical data. Do not close this page... ', "yellow");
		var doctorMetadata = window.GATE.Medicals.BuildDoctorXML();
		var doctorId = window.GATE.Master.GetURLParam('id',window.location.href);
		var xmlHttp = new XMLHttpRequest();
		var params = "doctor-metadata="+ doctorMetadata + "";
		xmlHttp.open("POST", "patient/doctormetadata/update", false);
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.send(params);
		var code = xmlHttp.status;
		status = xmlHttp.responseText;
		if (code == "500") {
			color = "red";
			window.GATE.Master.Notify(status, "red");
		}else{
			color = "green";
			window.GATE.Master.Notify(status, "green");
		}
		
		document.location.href = "Medicals.html";
	}

	
	window.GATE.Medicals.Delete = function() {
		var result = confirm("Are you sure you want to delete?");
		if (result) {
			//window.GATE.Master.Notify('Deleting medical data...', "yellow");
			var doctorID = $('#GD-DoctorId').val();
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.open("POST", "patient/doctormetadata/delete?id=" + doctorID + "",
					false);
			xmlHttp.send(null);
			var code = xmlHttp.status;
			var status = xmlHttp.responseText;
			if (code == "500") {
				window.GATE.Master.Notify(status, "red");
			}else{
				window.GATE.Master.Notify(status, "green");
			}
			window.GATE.Medicals.ClearForm();
		}
	}
	
	window.GATE.Medicals.ClearForm = function(){
		$('input').val('');
		$('textarea').val('');
	}

	
	window.GATE.Medicals.FillMetadataFields = function(id) {
		if(id !== null){
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.open("GET", "patient/doctormetadata/select-doctor?id="+ id + "", false);
			xmlHttp.send(null);
			var doctorMetadata = xmlHttp.responseText;

			var xml = $.parseXML(doctorMetadata);
			
			$(xml).find("doctor_metadata").each(function() {
				$('#GD-DoctorId').val($(this).find("doctor_id").text());
				$('#GD-FirstName').val($(this).find("first_name").text());
				$('#GD-LastName').val($(this).find("last_name").text());
				$('#GD-MedicalCenter').val($(this).find("medical_center").text());
				$('#GD-Notes').val($(this).find("notes").text());
				//document.getElementsByName("patient-database-id")[0].value = patientID;
			});

		}
		$('#updateButton').css("visibility", "visible");
		$('#deleteButton').css("visibility", "visible");
		$('#createButton').css("visibility", "hidden");
	}

	
	window.GATE.Medicals.GetAvailableMedicals = function() {
		var xmlHttp = new XMLHttpRequest();
	    xmlHttp.open( "GET", "patient/doctormetadata/select-doctors", false );
	    xmlHttp.send( null );
	    var doctorData = xmlHttp.responseText;
	    var xml = $.parseXML(doctorData);
	    if ($(xml).find("code").text() == "500") {
			//window.GATE.Master.ShowMessage($(xml).find("status").text(), '')
			window.GATE.Master.Notify($(xml).find("status").text(), "red");
	    }else{
	    	var count = 1;
			var resultContainer = $('#MedicalsSearchResults');

			$(xml).find("doctor").each(
					function() {
						var name = $(this).find("name").text();
						var id = $(this).find("id").text();
						var department = $(this).find("medicalCenter").text();
						var notes = $(this).find("notes").text();
						var item = window.GATE.Medicals.RenderMedicalItem(name, id,
								department, notes, count);
						resultContainer.append(item);
						count++;
					});
			window.GATE.Master.Notify("Medicals loaded...", "green");
	    }
	}

	
	window.GATE.Medicals.RenderMedicalItem = function(name, id, department,
			notes, count) {
		var item = "<div id='medical-"
				+ count
				+ "' class='col-md-8 medicals' onclick='window.GATE.Medicals.FillMetadataFields("
				+ id + ")'>" + "<div class='col-md-3 medicalsName'>" + name
				+ "</div>" + "<div class='col-md-1 medicalsID'>" + id
				+ "</div>" + "<div class='col-md-3 medicalsDepartment'>"
				+ department + "</div>"
				+ "<div class='col-md-4 medicalsNotes'>" + notes + "</div>"
				+ "</div>";
		return item;
	}

	
	window.GATE.Medicals.BuildDoctorXML = function() {
		var doctorMetadata = document.createElement("doctorMetadata1");
		var Node = document.createElement("doctor_metadata");

		// doctor id
		var doctor_id = document.createElement("doctor_id");
		var doctorIdContent = document.createTextNode(document
				.getElementById("GD-DoctorId").value);
		doctor_id.appendChild(doctorIdContent);
		Node.appendChild(doctor_id);

		// first name
		var first_name = document.createElement("first_name");
		var fnameContent = document.createTextNode(document
				.getElementById("GD-FirstName").value);
		first_name.appendChild(fnameContent);
		Node.appendChild(first_name);

		// last name
		var last_name = document.createElement("last_name");
		var lnameContent = document.createTextNode(document
				.getElementById("GD-LastName").value);
		last_name.appendChild(lnameContent);
		Node.appendChild(last_name);

		// medical center
		var medical_center = document.createElement("medical_center");
		var medicalCenterContent = document.createTextNode(document
				.getElementById("GD-MedicalCenter").value);
		medical_center.appendChild(medicalCenterContent);
		Node.appendChild(medical_center);

		// notes
		var notes = document.createElement("notes");
		var notesContent = document.createTextNode(document
				.getElementById("GD-Notes").value);
		notes.appendChild(notesContent);
		Node.appendChild(notes);
		doctorMetadata.appendChild(Node);

		return doctorMetadata.innerHTML;
	}
})();


$(document).ready(function() {
	window.GATE.Medicals.Initialize();
	window.GATE.Medicals.GetAvailableMedicals();
});