(function() {
	window.GATE = (window.GATE || {});
	window.GATE.Overview = (window.GATE.Overview || {});
	
	// global variabels
	var patient = {
				name: null,
				id: null,
				birthdate: null,
				weight: null,
				height: null,
				bmi: null
			};
	var flag = 0;
	var IconTypes = [
		{key: "png" , value : "fa fa-file-image-o"},
		{key: "jpg" , value : "fa fa-file-image-o"},
		{key: "pdf" , value : "fa fa-file-pdf-o"},
		{key: "dcm" , value : "fa fa-file-excel-o"},
	];
	
	var accentMap = {
      "á": "a",
      "ö": "o",
	  	"ä": "a",
	  	"ü": "u"
    };
		
	var fsNames = ["Brain","Body","Fase","Ärger","arm","leg"];
	var fsValues = ["high","middle","low"]	
	
	
	
	
	
	window.GATE.Overview.Initialize = function() {
		
		$('#GD-FilterDatePickerFrom').datetimepicker({
			format: 'DD/MM/YYYY LT',
			showTodayButton : true,
			calendarWeeks: true,
			showClear: true,
			toolbarPlacement: 'top',
		});
		
		window.GATE.Overview.RenderMISCTable();
		
		$('#GD-FilterDatePickerTo').datetimepicker({
			format: 'DD/MM/YYYY LT',
			showTodayButton : true,
			calendarWeeks: true,
			showClear: true,
			toolbarPlacement: 'top',
		});
		
		$('#GD-ToggleChart').on("click", function(){
			$("#canvas").toggle();	
		});
		
		$('#GD-ClearFilter').on("click", function(){
			$('#GD-FilterDatePickerFrom').val('');
			$('#GD-FilterDatePickerTo').val('');
			
			// show all list entries again
			$(".option").show();
			$(".gd-Scores").show();
			
			// filter if search was applied
			window.GATE.Helper.SearchFunctionScore($('#GD-FunctionScore-Search'));
		});		
		
		//
		//load patient metadata
		//
		window.GATE.Overview.LoadPatientMetadata();
		
		//
		// Load and render patient related metadata
		//
		window.GATE.Overview.RenderRelatedFilesTable();
		window.GATE.Overview.RenderSoleTable();
		window.GATE.Overview.RenderMISCTable();
		window.GATE.Overview.RenderFunctionScoreTable();
		
		//
		// add Filtering
		//
		
		// Document Search Filtering
		$("#GD-Document-Search").keyup(function() {
				window.GATE.Overview.SearchDocuments(this);
		});
		
		// Function Score Search Filtering		
		$("#GD-FunctionScore-Search").keyup(function (){
				window.GATE.Helper.SearchFunctionScore(this);
				});
		
		// Date Time Filtering
		$("#GD-FilterDatePickerFrom").on("dp.change",function() {
							window.GATE.Helper.DatePickerFrom(this);
							});

		$("#GD-FilterDatePickerTo").on("dp.change",function() {
							window.GATE.Helper.DatePickerFrom(this);
		});

	}
  
	
	//
	// Patient Functions
	//
	window.GATE.Overview.LoadPatientMetadata = function() {
		patient.id = window.GATE.Master.GetURLParam('id', window.location.href);
		
		if (patient.id !== null && patient.id !== "" ) {
			//window.GATE.Master.Notify("Loading patient metadata...", "yellow") 
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.open("GET", "patient/metadata/select-patient?id="+ patient.id + "", false);
			xmlHttp.send(null);
			var patientMetadata = xmlHttp.responseText;
			var xml = $.parseXML(patientMetadata);

			if ($(xml).find("code").text() == "500" || $(xml).find("code").text() == "400") {
				window.GATE.Master.Notify($(xml).find("message").text(), "red");
			}else{
				$(xml).find("patient_metadata")
				.each(function() {
							patient.name = $(this).find("first_name").text() + ", " + $(this).find("last_name").text();
							patient.birthdate = new Date($(this).find("birth_date").text());
							patient.weight = parseFloat($(this).find("weight").text());
							patient.height = parseFloat($(this).find("height").text());
							patient.age = window.GATE.Overview.calculateAge(patient.birthdate);
							patient.bmi =  (patient.weight / (patient.height * patient.height));
						});
						
				window.GATE.Overview.ViewPatientMetadata(patient);
				window.GATE.Master.Notify("Patient metadata loaded...", "green");
			} 
		} 
		else {
			window.GATE.Master.Notify("The patient you have choosen is not available...", "red"); 	
			$(window).delay(5000);
			window.location.href = "Start.html";
		}	
	}
	
	
	//
	// 
	//
	window.GATE.Overview.calculateAge = function (date) {
		var ageDifferenceMs = Date.now() - date.getTime();
		var ageDate = new Date(ageDifferenceMs); 
		return Math.abs(ageDate.getUTCFullYear() - 1970);
	}
	
	
	//
	// 
	//
	window.GATE.Overview.ViewPatientMetadata = function(currentPatient) {
		$('#GD-UserInfo-Username').text(patient.name);
		$('#GD-UserInfo-Age').text(patient.age);
		$('#GD-UserInfo-Weight').text(patient.weight  + " kg");
		$('#GD-UserInfo-Height').text(patient.height  + " meters");
		$('#GD-UserInfo-BMI').text(patient.bmi);
		$('#GD-UserInfo-Edit').attr("href", "Patients.html?id=" + patient.id);
		$('#GD-UserInfo-ID').val(patient.id);
	}
	
	
	//
	// Function Scores Functions
	//
	window.GATE.Overview.RenderFunctionScoreTable = function() {
		// not modal just naming because of moving it from modal to site		
		$('#GD-Modal-FunctionScoreTabel').empty();
		
		if (patient.id !== null) {
			var $arr = new Array();
			var xmlHttp = new XMLHttpRequest();	
			xmlHttp.open("GET", "patient/analysis/select-functionscore?id="+ patient.id + "", false);
			xmlHttp.send(null);
			var patientFunctionScoredata = xmlHttp.responseText;

			if ($(patientFunctionScoredata).find("code").text() == "500") {
				window.GATE.Master.Notify($(patientFunctionScoredata).find("status").text(), "red");
			} 
			else {
			
				var xml = $.parseXML(patientFunctionScoredata);
				$(xml).find("patient_fscore").each(function() {
						var item = {};
						item.id = $(this).find("id").text();
						item.key = $(this).find("function_score_name").text();
						item.value = $(this).find("function_score_value").text();
						var temp = new Date($(this).find("date").text());
						item.date = moment(temp).format('YYYY-MM-DD');
						$arr.push(item);	
				});
				
				$arr.sort(function SortByDate(a, b){
					var aDate = a.date;
					var bDate = b.date; 
					return ((aDate > bDate) ? -1 : ((aDate < bDate) ? 1 : 0));
				});
				
				$arr.forEach(function(item){		
					window.GATE.Overview.CreateFunctionScoresItem(item);
				})
						
			}
		}
	}
	

	//
	// 
	//
	window.GATE.Overview.CreateFunctionScoresItem = function(tempItem) {
			
		var item = {};
		if (tempItem === undefined){
			item = {
				dBid: "0",
				key: "",
				value: "",
				date: moment().format("YYYY-MM-DD"),
				isGrouping: false
			}
		}
		else {
			item = tempItem; 
		}
		
		var $functionScoreItem = $('<tr/>',{
								 class : 'gd-Scores',
								 dBId : item.dBid
								 });
								 
		var column1 = $('<td/>').append(	
								$('<input/>', {
								 type: 'date',
								 class:'gd-FunctionScore-Date form-control custom-input',
								 id: 'GD-Item-CreationDate',
								 placeholder: 'Related Date',
								 value: item.date,
								 disabled : ''
					  }));
					  
		var column2 = $('<td/>').append(	
									$('<input/>', {
									class:'gd-FunctionScore-Name form-control custom-input',
									id: 'GD-Item-FunctionScoreName',
									type: 'text',
									placeholder: 'name',
									value: item.key,
									disabled : ''
									}).autocomplete({
											source: function( request, response ) {
											window.GATE.Helper.attachAutoComplition(request, response, fsNames);
											}
											})   
								 );
				  
		var column3 = $('<td/>').append(	
								$('<input/>', {
								 class:'gd-FunctionScore-value form-control custom-input',
								 id: 'GD-Item-FunctionScoreValue',
								 type: 'text',
								 placeholder: 'value',
								 value: item.value,
								 disabled : ''
					  		 }).autocomplete({
											source: function( request, response ) {
											window.GATE.Helper.attachAutoComplition(request, response, fsValues);
											}
											})   
								 );
					  
		var column4 = $('<td/>').append(	
								$('<i/>', {
								 class:'fa fa-times',
								 id: 'GD-Item-FunctionScoreValue',
								 title: 'remove Function Score'
							}).click(function(){
								window.GATE.Overview.RemoveFunctionScoreItem();
							})).append($('<span/>', {
										class:'gd-ToggleEdit',
										id: 'GD-Item-FunctionScoreValue',
										text: 'edit',
										title: 'remove Function Score'
								}).click(function(){
									window.GATE.Overview.EnableInputFields();
								})
							);
			
		$functionScoreItem.append(column1).append(column2).append(column3).append(column4);
		$('#GD-Modal-FunctionScoreTabel').append( $functionScoreItem);
		
		if (tempItem === undefined){
		 	window.GATE.Overview.EnableInputFields($functionScoreItem);
		 	$("#gd-FunctionScoreWrapper").animate({ scrollTop: $("#gd-FunctionScoreWrapper").height()-$(".gd-FunctionScoreTabel").height() }, 1500);
		}
		
	}
	
	//
	// Enable Input Fields
	//
	window.GATE.Overview.EnableInputFields = function(e) {
		e = e || window.event;
		var targ = e.target || e.srcElement;
		if (targ === undefined){
				parent = e 
				targ = parent.find('.gd-ToggleEdit');
		}
		else
			parent = targ.parentNode.parentNode;
			
		$(parent).find("input").each(function(){
			 $(this).removeAttr('disabled');
			 });
		$(parent).find("select").each(function(){
			 $(this).removeAttr('disabled');
			 });
			 
		$(targ).attr("style", "visibility:hidden");
	}

	//
	// 
	//
	window.GATE.Overview.RemoveFunctionScoreItem = function(e) {
		e = e || window.event;
		var targ = e.target || e.srcElement;
		if (targ.nodeType == 3) targ = targ.parentNode;
		var $item = $(targ.parentElement.parentElement);
		var xmlHttp = new XMLHttpRequest();
		var databaseID = $item.attr("dBid");
		if (databaseID !== "0"){
			xmlHttp.open("GET", "patient/analysis/deletefunctionscore?id=" + patient.id + "&functionscoreid=" + databaseID, false); 
			xmlHttp.send(null);
			var response = xmlHttp.responseText;
			if (xmlHttp.status == 200) {
				$item.remove();
				alert(response);
				window.GATE.Master.Notify(response, "green");
			}else if(xmlHttp.status == 500 || xmlHttp.status == 400){
				window.GATE.Master.Notify(response, "red");
			}
		}else{
			$item.remove();
		}
	}
	

	//
	// Misc Data Functions
	//
	window.GATE.Overview.RenderMISCTable = function() {
		$('#GD-Modal-MISCTabel').empty();
		
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", "patient/miscdata/select?id=" + patient.id + "",false);
		xmlHttp.send(null);
		var response = xmlHttp.responseText;		
			
		var $arr = new Array();
		var graphData = {
			vas : new Array(),
			nrs : new Array(),
			contentlvl: new Array(),
			dates : new Array()
		};

		
		$(response).find("patient_miscdata").each(function() {	
						var item = {};
						item.id = $(this).find("id").text();
						item.pain_nrs = $(this).find("pain_nrs").text();
						item.pain_vas = $(this).find("pain_vas").text();
						var temp = new Date($(this).find("related_date").text());
						item.date = moment(temp).format('YYYY-MM-DD');
						item.content_level= $(this).find("content_level").text();
						$arr.push(item);
						
						
					});
					
		$arr.sort(function SortByDate(a, b){
			var aName = a.date;
			var bName = b.date; 
			return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
		});
		$arr.forEach(function(item){
			window.GATE.Overview.CreateMISCItem(item);
			graphData.vas.push(item.pain_vas);
			graphData.nrs.push(item.pain_nrs);
			graphData.contentlvl.push(item.content_level);
			graphData.dates.push("");
		});
		window.GATE.Helper.DrawLineChart(graphData);								
	}
	
	
	//
	// 
	//
	window.GATE.Overview.CreateMISCItem = function(tempItem) {
		var disabled = "disabled";
		var visibility = "";
		
		var item = {};
		if (tempItem === undefined){
			item = {
				id: "0",
				pain_nrs: "",
				pain_vas: "",
				date: moment().format("YYYY-MM-DD"),
				content_level: ""
			}
			disabled = "";
			visibility = "hidden";	
		}
		else {
			item = tempItem; 
		}
		
		
		var $MiscItem = $('<tr/>',{
								 class : 'gd-Scores',
								 dBId : item.id
								 });
								 
		var column1 = $('<td/>').append(	
											$('<input/>', {
											type: 'date',
											class:'gd-Misc-Date form-control custom-input',
											id: 'GD-Item-CreationDate',
											placeholder: 'Related Date',
											value: item.date,
											disabled : ''
									}));
					  
		var column2 = $('<td/>').append(	
								$('<input/>', {
								 class:'gd-Misc-NRS form-control custom-input',
								 type: 'number',
								 placeholder: 'Pain NRS',
								 value: item.pain_nrs,
								 disabled : ''
					  }));
					  
		var column3 = $('<td/>').append(	
								$('<input/>', {
								 class:'gd-Misc-VAS form-control custom-input',
								 type: 'number',
								 placeholder: 'Pain VAS',
								 value: item.pain_vas,
								 disabled : ''
					  }));
						
		var column4 = $('<td/>');
		var $selectBox = $('<select/>', {
										class:'gd-Misc-Contentlevel form-control custom-input',
										disabled : ''
										});
		
		var values = [0 ,1, 2, 3, 4, 5];			
		for (var i in values) {
    	$selectBox.append($('<option/>').html(values[i]));
		}
		$selectBox.val(item.content_level)	
		column4.append($selectBox);
					  
		var column5 = $('<td/>').append(	
								$('<i/>', {
								 class:'fa fa-times',
								 title: 'remove MISC Data Item'
							}).click(function(){
								window.GATE.Overview.RemoveMISCDataItem();
							})).append($('<span/>', {
										class:'gd-ToggleEdit',
										id: 'GD-Item-FunctionScoreValue',
										text: 'edit',
										title: 'remove Function Score'
								}).click(function(){
									window.GATE.Overview.EnableInputFields();
								})
							);

		$MiscItem.append(column1).append(column2).append(column3).append(column4).append(column5);
			$('#GD-Modal-MISCTabel').append($MiscItem);	
		if (tempItem === undefined){
		  window.GATE.Overview.EnableInputFields($MiscItem);
			$("#gd-MiscWrapper").animate({ scrollTop: ($("#gd-MiscWrapper").height() - $(".GD-Modal-MISCTabel").height()) }, 500);
		}
	}
	
	
	//
	// 
	//
	window.GATE.Overview.SaveMISCData = function () {
		var $arr = [];
		$arr = window.GATE.Overview.GetMISCValues();

		// Add rest call here for misc Save in the $arr all misc new empty and updated are in there existing have a valide id the others 0
		// retrieve misc data values
		$($arr).each(
				function(index) {
					var miscdata = window.GATE.Overview.buildMiscXML(patient.id , $arr[index].dbID, $arr[index].pain_nrs, $arr[index].value, $arr[index].date, $arr[index].content_level);
					alert(miscdata);
					var xmlHttp = new XMLHttpRequest();
					xmlHttp.open("POST","patient/miscdata/insert", false);
					xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
					xmlHttp.send("patient-miscdata=" + miscdata + "");
					var code = xmlHttp.status;
					var status = xmlHttp.responseText;
					if (code == "500") {
						window.GATE.Master.Notify(status, "red");
					}else if (code == "400"){
						window.GATE.Master.Notify(status, "red");
					}else{
						window.GATE.Master.Notify(status, "green");
					}
				});		
		window.GATE.Overview.RenderMISCTable();
	}
	
	
	window.GATE.Overview.buildMiscXML = function(patientId, dbID, painnrs, value, date, contentLevel) {

		var patientMiscData = document.createElement("patient_misc_data");
		var Node = document.createElement("patient_miscdata");

		// patient id
		var patient_id = document.createElement("patient_id");
		var patientIdContent = document.createTextNode(patientId);
		patient_id.appendChild(patientIdContent);
		Node.appendChild(patient_id);
		
		// id
		var id = document.createElement("id");
		var idContent = document.createTextNode(dbID);
		id.appendChild(idContent);
		Node.appendChild(id);

		// pain_nrs
		var pain_nrs = document.createElement("pain_nrs");
		var painnrsContent = document.createTextNode(painnrs);
		pain_nrs.appendChild(painnrsContent);
		Node.appendChild(pain_nrs);

		// pain_vas
		var pain_vas = document.createElement("pain_vas");
		var painvasContent = document.createTextNode(value);
		pain_vas.appendChild(painvasContent);
		Node.appendChild(pain_vas);

		// related_date
		var related_date = document.createElement("related_date");
		var relatedDateContent = document.createTextNode(date);
		related_date.appendChild(relatedDateContent);
		Node.appendChild(related_date);

		// content_level
		var content_level = document.createElement("content_level");
		var contentLevelContent = document.createTextNode(contentLevel);
		content_level.appendChild(contentLevelContent);
		Node.appendChild(content_level);

		patientMiscData.appendChild(Node);

		return patientMiscData.innerHTML;
	}
	
	
	window.GATE.Overview.RemoveMISCDataItem = function (e) {
		e = e || window.event;
		var targ = e.target || e.srcElement;
		if (targ.nodeType == 3) targ = targ.parentNode;
		var $item = $(targ.parentElement.parentElement);
		var xmlHttp = new XMLHttpRequest();
		var databaseID = $item.attr("dBid");
		if (databaseID !== "0"){
			xmlHttp.open("GET", "patient/miscdata/delete?id=" + databaseID, false);
			xmlHttp.send(null);
			var response = xmlHttp.responseText;
			if (xmlHttp.status == 200) {
				$item.remove();
				window.GATE.Master.Notify(response, "green");
			}else if(xmlHttp.status == 500 || xmlHttp.status == 400){
				window.GATE.Master.Notify(response, "red");
			}
		}else{
			$item.remove();
		}
	}
	
	
	//
	// 
	//
	window.GATE.Overview.GetMISCValues = function() {
		var $arr = [];
		var $items = $('.gd-MISCTabel').find(".gd-Scores");
		$($items).each(function(index) {
			var _dbID	 = $($items[index]).attr("dBId");
			var inputs = $($items[index]).find('input');
			var _date = $(inputs[0]).val();
			var _pain_nrs = $(inputs[1]).val();
			var _pain_vas = $(inputs[2]).val();
			var _content_level = $($($items[index]).find('select')[0]).val();
			if(_dbID !== undefined && _pain_nrs !== undefined && _pain_vas !== undefined && _date !== undefined && _content_level !== undefined){
				var $item = {
					"dbID" : _dbID,
					"pain_nrs" : _pain_nrs,
					"value" : _pain_vas,
					"date" : _date,
					"content_level": _content_level 
				}
				$arr.push($item);
			}
		});

		return $arr;
	}
	
	
	//
	// load all function Sores to Jquery item
	//
	window.GATE.Overview.GetFunctionScoreValues = function() {
		var $arr = [];
		var $items = $('#GD-Modal-FunctionScoreTabel').find('tr');
		$($items).each(function(index) {
			var inputs = $($items[index]).find('input');
			var _id = $($items[index]).attr("dbid");
			var _key = $(inputs[1]).val();
			var _value = $(inputs[2]).val();
			var _date = $(inputs[0]).val();
			var $item = {
				"dbID" : _id,
				"date" : _date,
				"key" : _key,
				"value" : _value
			};

			$arr.push($item);
		});

		return $arr;
	}
	
	
	//
	// Analysis Functions
	//
	window.GATE.Overview.LoadSingleSolefileData = function(e) {
		var dbid = ""; // <-- database id of single sole data file 
		
		if (e === undefined || null){
			e = e || window.event;
			var targ = e.target || e.srcElement;
			if (targ.nodeType == 3) targ = targ.parentNode;
			dbid = $(targ).attr("dbid");  
		}	
		else 
			dbid = e;
						
		var soleDataFilesForAnalysisInput = document.createElement("soleDataFilesForAnalysisInput");
		var Node = document.createElement("sole_data_files");

		// Patient id
		var id = document.createElement("id");
		var patientId = document.createTextNode(patient.id);
		id.appendChild(patientId);
		Node.appendChild(id);

		// count
		var number_of_files = document.createElement("number_of_files");
		var count = document.createTextNode(1);
		number_of_files.appendChild(count);
		Node.appendChild(number_of_files);

		// file ids
		var file_ids = document.createElement("file_ids");
		var fileId = document.createTextNode(dbid);
		file_ids.appendChild(fileId);
		Node.appendChild(file_ids);
		
		// stress level
		var stress_threshold = document.createElement("stress_threshold");
		var stressLevel = document.createTextNode(document.getElementById("GD-StressLevelInput").value);
		stress_threshold.appendChild(stressLevel);
		Node.appendChild(stress_threshold);

		soleDataFilesForAnalysisInput.appendChild(Node);

		var soleDataInput = soleDataFilesForAnalysisInput.innerHTML;
		//window.GATE.Master.Notify("Loading Sole Data informations" , "yellow");
		
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("POST", "patient/analysis/fetchanalysisresult", false); 
		xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlHttp.send("sole-data-files-analysis-input=" + soleDataInput + "");
		
		var response = xmlHttp.responseText;
		var xml = $.parseXML(response);
		if ($(xml).find("code").text() == "500") {
			window.GATE.Master.Notify($(xml).find("message").text(), "red");
		}else if($(xml).find("code").text() == "400"){
			window.GATE.Master.Notify($(xml).find("message").text(), "red");
		}else{
			window.GATE.Master.Notify($(xml).find("message").text(), "green");
			window.GATE.Overview.FetchDataFromResponse(response);
		}
		
	}
	
	//
	//
	//
	window.GATE.Overview.FetchDataFromResponse = function(response) {
		var dataarr = [];
		var xml = $.parseXML(response);			
				dataarr = [
				{ key : 'Min_Pressure' , value: $(xml).find("patients > result > statistics > min_left_pressure").text() + ';' + $(xml).find("patients > result > statistics > min_right_pressure").text()  },		
				{ key : 'Max_Pressure' , value: $(xml).find("patients > result > statistics > max_left_pressure").text() + ';' + $(xml).find("patients > result > statistics > max_right_pressure").text() },
				{ key : 'Mean_Pressure' , value: $(xml).find("patients > result > statistics > mean_left_pressure").text() + ';' + $(xml).find("patients > result > statistics > mean_right_pressure").text()  },
				{ key : 'Variance_Pressure' , value: $(xml).find("patients > result > statistics > variance_left_pressure").text() + ';' + $(xml).find("patients > result > statistics > variance_right_pressure").text()  },
				{ key : 'Std_Dev_Pressure' , value: $(xml).find("patients > result > statistics > stddev_left_pressure").text() + ';' + $(xml).find("patients > result > statistics > stddev_right_pressure").text()  },
				{ key : 'Resting_Time_(min)' , value: $(xml).find("patients > result > resting_and_moa_time > resting_time_left").text() + ';' + $(xml).find("patients > result > resting_and_moa_time > resting_time_right").text() },
				{ key : 'Min_of_Activity_(min)' , value: $(xml).find("patients > result > resting_and_moa_time > min_of_activity_left").text() + ';' + $(xml).find("patients > result > resting_and_moa_time > min_of_activity_right").text() },
				{ key : 'Min_Exceeded_Threshold_('+$(xml).find("patients > result > stress_level_exceeded_time > stress_threshold").text()+')' , value: $(xml).find("patients > result > stress_level_exceeded_time > minutes_exceeded_left").text() + ';' + $(xml).find("patients > result > stress_level_exceeded_time > minutes_exceeded_right").text()}];

		window.GATE.Overview.RenderAnalysisTiles($('#GD-AnalysisContainer'),	dataarr );
	}
	
	
	//
	// 
	//
	window.GATE.Overview.LoadMultipleSolefileData = function() {
		
		var ids = window.GATE.Overview.getAllSelectedSoleData();
		var fromDate = document.getElementById("GD-FilterDatePickerFrom").value;
		var toDate = document.getElementById("GD-FilterDatePickerTo").value;
		var stressThreshold = document.getElementById("GD-StressLevelInput").value;
		
		if(ids.length === 0 && fromDate !== "" &&  toDate !== ""){
			//alert(document.getElementById("GD-FilterDatePickerFrom").value);
			//alert(document.getElementById("GD-FilterDatePickerTo").value);
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.open("GET", "patient/analysis/fetchfilteredanalysisresult?id=" + patient.id + "&start-date=" + fromDate + "&end-date=" + toDate + "&stress-threshold=" + stressThreshold, false); 
			xmlHttp.send(null);
			var response = xmlHttp.responseText;
			//alert(response);
			var xml = $.parseXML(response);
			if ($(xml).find("code").text() == "500") {
				window.GATE.Master.Notify($(xml).find("message").text(), "red");
			}else if($(xml).find("code").text() == "400"){
				window.GATE.Master.Notify($(xml).find("message").text(), "red");
			}else{
				window.GATE.Master.Notify($(xml).find("message").text(), "green");
				dataarr = [
							{ key : 'Min_Pressure' , value: $(xml).find("patients > result > statistics > min_left_pressure").text() + ';' + $(xml).find("patients > result > statistics > min_right_pressure").text()  },		
							{ key : 'Max_Pressure' , value: $(xml).find("patients > result > statistics > max_left_pressure").text() + ';' + $(xml).find("patients > result > statistics > max_right_pressure").text() },
							{ key : 'Mean_Pressure' , value: $(xml).find("patients > result > statistics > mean_left_pressure").text() + ';' + $(xml).find("patients > result > statistics > mean_right_pressure").text()  },
							{ key : 'Variance_Pressure' , value: $(xml).find("patients > result > statistics > variance_left_pressure").text() + ';' + $(xml).find("patients > result > statistics > variance_right_pressure").text()  },
							{ key : 'Std_Dev_Pressure' , value: $(xml).find("patients > result > statistics > stddev_left_pressure").text() + ';' + $(xml).find("patients > result > statistics > stddev_right_pressure").text()  },
							{ key : 'Resting_Time_(min)' , value: $(xml).find("patients > result > resting_and_moa_time > resting_time_left").text() + ';' + $(xml).find("patients > result > resting_and_moa_time > resting_time_right").text() },
							{ key : 'Min_of_Activity_(min)' , value: $(xml).find("patients > result > resting_and_moa_time > min_of_activity_left").text() + ';' + $(xml).find("patients > result > resting_and_moa_time > min_of_activity_right").text() },
							{ key : 'Min_Exceeded_Threshold_('+$(xml).find("patients > result > stress_level_exceeded_time > stress_threshold").text()+')' , value: $(xml).find("patients > result > stress_level_exceeded_time > minutes_exceeded_left").text() + ';' + $(xml).find("patients > result > stress_level_exceeded_time > minutes_exceeded_right").text()}];

				window.GATE.Overview.RenderAnalysisTiles($('#GD-AnalysisContainer'),	dataarr );
			}
		}else{
			var dataarr = new Array();
			
			if(ids.length === 0 || null || undefined ) return;
			if(ids.length === 1 ){
				 window.GATE.Overview.LoadSingleSolefileData(ids[0]);
				 return;
			}
					
			var soleDataFilesForAnalysisInput = document.createElement("soleDataFilesForAnalysisInput");
			var Node = document.createElement("sole_data_files");

			// Patient id
			var id = document.createElement("id");
			var patientId = document.createTextNode(patient.id);
			id.appendChild(patientId);
			Node.appendChild(id);

			// count
			var number_of_files = document.createElement("number_of_files");
			var count = document.createTextNode(ids.length);
			number_of_files.appendChild(count);
			Node.appendChild(number_of_files);

			// file ids
			var file_ids = document.createElement("file_ids");
			var fileId = document.createTextNode(ids.join(","));
			file_ids.appendChild(fileId);
			Node.appendChild(file_ids);
			
			// stress level
			var stress_threshold = document.createElement("stress_threshold");
			var stressLevel = document.createTextNode(document.getElementById("GD-StressLevelInput").value);
			stress_threshold.appendChild(stressLevel);
			Node.appendChild(stress_threshold);

			soleDataFilesForAnalysisInput.appendChild(Node);
			var soleDataInput = soleDataFilesForAnalysisInput.innerHTML;
			//alert(soleDataInput);
			//window.GATE.Master.Notify("Loading Sole Data informations for multiple files", "yellow");
			var xmlHttp = new XMLHttpRequest();
			xmlHttp.open("POST", "patient/analysis/fetchanalysisresult", false); 
			xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlHttp.send("sole-data-files-analysis-input=" + soleDataInput + "");
			var response = xmlHttp.responseText;
			alert(response);
			if ($(response).find("code").text() == "500") {
				window.GATE.Master.Notify($(response).find("message").text(), "red");
			}else if($(response).find("code").text() == "400"){
				window.GATE.Master.Notify($(response).find("message").text(), "red");
			}else{
				window.GATE.Master.Notify($(response).find("message").text(), "green");
				
				var xml = $.parseXML(response);
				$(xml).find("result").each(
							function() {
								dataarr.push([
								{ key : 'filename' , value: $(this).find("file").text() },
								{ key : 'date' , value: moment(new Date($(this).find("date").text())).format("D MMMM YYYY") },
								{ key : 'min_left_pressure' , value: $(this).find(" statistics > min_left_pressure").text() },
								{ key : 'min_right_pressure' , value:  $(this).find(" statistics > min_right_pressure").text() },			
								{ key : 'max_left_pressure' , value: $(this).find("statistics > max_left_pressure").text() },
								{ key : 'max_right_pressure' , value: $(this).find("statistics > max_right_pressure").text() },
								{ key : 'mean_left_pressure' , value: $(this).find(" statistics > mean_left_pressure").text()  },
								{ key : 'mean_right_pressure' , value: $(this).find("statistics > mean_right_pressure").text()  },
								{ key : 'variance_left_pressure' , value: $(this).find("statistics > variance_left_pressure").text()  },
								{ key : 'variance_right_pressure' , value: $(this).find("statistics > variance_right_pressure").text()  },
								{ key : 'stddev_left_pressure' , value: $(this).find("statistics > stddev_left_pressure").text()  },
								{ key : 'stddev_right_pressure' , value: $(this).find("statistics > stddev_right_pressure").text()  },
								{ key : 'resting_time_left_(min)' , value: $(this).find("resting_and_moa_time > resting_time_left").text()  },
								{ key : 'resting_time_right_(min)' , value: $(this).find("resting_and_moa_time > resting_time_right").text() },
								{ key : 'min_of_activity_left_(min)' , value: $(this).find(" resting_and_moa_time > min_of_activity_left").text()  },
								{ key : 'min_of_activity_right_(min)' , value: $(this).find(" resting_and_moa_time > min_of_activity_right").text() },
								{ key : 'stress_threshold' , value: $(this).find("stress_level_exceeded_time > stress_threshold").text() },
								{ key : 'minutes_exceeded_left_(min)' , value: $(this).find(" stress_level_exceeded_time > minutes_exceeded_left").text()  },
								{ key : 'minutes_exceeded_right_(min)' , value: $(this).find(" stress_level_exceeded_time > minutes_exceeded_right").text()}]
								);
							});
							
				window.GATE.Overview.RenderAnalysisTable( $('#GD-AnalysisContainer'), dataarr );
			}					
		}
	}
	
	
	//
	//
	//
	window.GATE.Overview.getAllSelectedSoleData = function(){
		var soleDataFileIds = new Array();
		$(".soleTableItem").find(":checkbox").each(function(){
			if(this.checked === true){
			 // get database id from current selected soledata attr dbid
			 soleDataFileIds.push($($(this.parentElement.parentElement).find("#GD-Item-CreationDate")[0]).attr("dbid"));
			}			
		});
		return soleDataFileIds;
	}
	
	
	//
	// 
	//
	window.GATE.Overview.RenderAnalysisTiles = function(container , dataArr) {
		container.empty();
		$.each(dataArr , function(index, item) {
					var tile = window.GATE.Overview.CreateAnalysisTile(item);
					if (tile != null)
						container.append(tile);
		});
	}
	
	
	//
	// 
	//
	window.GATE.Overview.CreateAnalysisTile = function(item) {
		
	var values = item.value.split(';');
	
	if (values[0] === '')
		return null;
	
	var tile = $('<div id="'+ item.key +'" class="gd-Analysis-Tile">'
							+ '<div class="gd-Analysis-Tile-Head">'
								+ '<span>'+ item.key.replace(/\_/g," ") + '</span>'
							+ '</div>'
							+ '<div class="gd-Analysis-Tile-Body">'
								+ '<div>'
									+ '<div class="gd-Analysis-Tile-Body-Label">left</div>'
									+ '<span>' + values[0] + '</span>'
								+ '</div>'
								+ '<div>'
									+ '<div class="gd-Analysis-Tile-Body-Label gd-Analysis-Tile-Body-Label-Addins">right</div>'
									+ '<span>' + values[1] + '</span>'
								+ '</div>'
							+ '</div>'
							+ '<div class="gd-TileFooter gd-Analysis-Tile-Footer">'
									
							+ '</div>'
						+ '</div>');

		return tile;
	}
	
	
	//
	//
	//
	window.GATE.Overview.RenderAnalysisTable = function(container , dataArr) {
		container.empty().append(window.GATE.Overview.RenderAnalysisTableBody());
	
		$.each(dataArr , function(index, item) {
					$("#GD-Analysis-Table-Body").append(window.GATE.Overview.CreateAnalysisRow(item));
		});
	}
	
	
	//
	//
	//
	window.GATE.Overview.RenderAnalysisTableBody = function (){
		
		var tablecontainer = $(					'<div class="gd-Analysis-Table-ScrollWrapper">'
													+'<table class="gd-Analysis-Table">'						
														+'<thead class="gd-Analysis-Table-Head">'
															+'<tr>'
									 							+'<th colspan="2"> <span>Pressure Min</span> </th>'
																+'<th colspan="2" class="left"> <span>Pressure Max</span> </th>'
																+'<th colspan="2" class="left"> <span>Pressure Mean</span> </th>'
																+'<th colspan="2" class="left"> <span>Variance</span> </th>'
																+'<th colspan="2" class="left"> <span>Standard  Deviation</span> </th>'
																+'<th colspan="2" class="left"> <span>Resting Time</span> </th> '
																+'<th colspan="2" class="left"> <span>Minutes of Activity</span> </th>'
																+'<th colspan="3" class="left"> <span>Stress Threshold </span> </th>'
															+'</tr>'
															+'<tr class="gd-Analysis-Table-Head-Sub">'
																+'<th>L</th> <th>R</th>'
																+'<th class="left">L</th> <th>R</th>'
																+'<th class="left">L</th> <th>R</th>'
																+'<th class="left">L</th> <th>R</th>'
																+'<th class="left">L</th> <th>R</th>'
																+'<th class="left">L</th> <th>R</th>'
																+'<th class="left">L</th> <th>R</th>'
																+'<th class="left">val</th>'
																+'<th>L</th> <th>R</th>'
															+'</tr>'
														+'</thead>'
														+'<tbody id="GD-Analysis-Table-Body" class="gd-Analysis-Table-Body" >'
														+'</tbody>'
														  
													+'</table>'
												+'</div>');										
		return tablecontainer;		
	}
	
	
	//
	//
	//
	window.GATE.Overview.CreateAnalysisRow = function(item) {
		var row = $(			'<tr class="gd-Analysis-Row-Header">'
								+'<span>Date</span>'
									+'<td colspan="17"> <div>' + item[1].value + ' </div></td>'
								+'</tr>'
								+'<tr class="gd-Analysis-Row">'
									//+'<span>Pressure Min</span>'
									+'<td class="" title="Min Pressure">' + item[2].value + '</td>'
									+'<td class="right" title="Min Pressure">' + item[3].value + '</td>'
									//+'<span>Pressure Max</span>'
									+'<td class="left" title="Max Pressure">' + item[4].value + '</td>'
									+'<td class="right"title="Max Pressure">' + item[5].value + '</td>'
									//+'<span>Pressure Mean</span>'
									+'<td class="left" title="Mean Pressure">' + item[6].value + '</td>'
									+'<td class="right" title="Mean Pressure">' + item[7].value + '</td>'
									//+'<span>Variance</span>'
									+'<td class="left" title="Variance">' + item[8].value + '</td>'
									+'<td class="right" title="Variance">' + item[9].value + '</td>'
									//+'<span>STDDEV</span>'
									+'<td class="left" title="Stddev">' + item[10].value + '</td>'
									+'<td class="right" title="Stddev">' + item[11].value + '</td>'
									//+'<span>Resting Time</span>'
									+'<td class="left" title="Resting Time">' + item[12].value + '</td>'
									+'<td class="right" title="Resting Time">' + item[13].value + '</td>'
									//+'<span>Minimum of Activity</span>'
									+'<td class="left" title="Min of Activity">' + item[14].value + '</td>'
									+'<td class="right" title="Min of Activity">' + item[15].value + '</td>'
									// Thresshold
									+'<td class="left" title="Thresshold value">' + item[16].value + '</td>'
									+'<td class="right" title="Thresshold">' + item[17].value + '</td>'
									+'<td class="right" title="Thresshold">' + item[18].value + '</td>'
								+'</tr>'
								+'<tr class="gd-Analysis-Row-Footer">'
									+'<td colspan="17"></td>'
								+'</tr>'
								
								);
		return row;
	}
	
	//
	// 
	//
	window.GATE.Overview.RenderSoleTable = function() {
		var tableContainer = $('#Gd-SoleDataItemsContainer');	
		var item = {};
		tableContainer.empty();

		//getting sole data list
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", "patient/analysis/getfiledatalist?id=" + patient.id + "", false);
		xmlHttp.send(null);
		var soleDataResponse = xmlHttp.responseText;
		if ($(soleDataResponse).find("code").text() == "500") {
			alert($(soleDataResponse).find("status").text(), '')
		} else {
			//alert(soleDataResponse);
			var xml = $.parseXML(soleDataResponse);
			var count = 1;

			$(xml).find("sole_data_list").each(
					function() {
						item.id = $(this).find("id").text();
						item.iconClass = "fa-bar-chart";
						item.creationDate = new Date($(this).find("date").text());
						item.filename = $(this).find("filename").text();
						item.Count = count;
						tableContainer.append(window.GATE.Overview.CreateSoleTableItem(item));
						count = count + 1;
					});
		}
	}
	
	
	//
	// Overview Tables Left Site
	//
	window.GATE.Overview.CreateSoleTableItem = function(item) {
		
		var currentItem = $('<tr/>', {
					class: 'option soleTableItem',
					dbid: item.id,
					filename : item.filename
				   });
		
		var column1 =  $('<td/>', {
						 }).append($('<i/>', {
							 		id: "GD-soleDataItem",
									class: "fa " + item.iconClass,
									}));
									
		var column2	=	$('<td/>', {
							id: "GD-Item-CreationDate",
							class: "soleTableItem-Text",
							dbid: item.id,
							text: item.creationDate.getMonth()+1 +"/"+  item.creationDate.getDate()  +"/"+ item.creationDate.getFullYear()
							}).click(function (){
								window.GATE.Overview.LoadSingleSolefileData();
							});
							
		var column3	=	$('<td/>', {
							}).append( $('<input/>',{
								type: "checkbox",
								class: "checkbox",
								name: "checkbox-"+ item.Count,
								id: "checkbox-" + item.Count 
							}));
							
		var column4 = 	$('<td/>', {
							class: "soleTableItem-Delete"
							}).append( $('<i/>',{
								class: 'fa fa-times',
							})).click(function (e){
								e = e || window.event;
								var targ = e.target || e.srcElement;
								if ( $(targ).is("td")) targ = targ.parentNode;
								if ( $(targ).is("i")) targ = targ.parentNode.parentNode;
								window.GATE.Overview.DeleteSoleTableItem(targ);
							});

		return currentItem.append(column1).append(column2).append(column3).append(column4);
	}
	
	//
	//
	//
	window.GATE.Overview.DeleteSoleTableItem = function(item) {
		var id = $(item).attr("dbid");
		var filename = $(item).attr("filename");
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("POST", "patient/analysis/deletesoledata?id=" +id+ "&patientid=" +patient.id+ "&file=" +filename, false);
		xmlHttp.send(null);
		var response = xmlHttp.responseText;
		if (xmlHttp.status == 200) {
			$(item).remove();
			window.GATE.Master.Notify(response, "green");
		}else if(xmlHttp.status == 500 || xmlHttp.status == 400){
			window.GATE.Master.Notify(response, "red");
		}else if(xmlHttp.status == 404){
			window.GATE.Master.Notify("Service is down, contact administrator.", "red");
		}
	}


	
	//
	// 
	//
	window.GATE.Overview.ToggleCheckBox = function() {
		if (flag === 0) {
			$('#select-all').prop('checked', true);
			flag = 1;
			$(":checkbox").each(function() {
			if ($(this.parentElement.parentElement).css("display") != "none") {
				this.checked = true;}})
			} 
			else if (flag == 1) {
				$('#select-all').prop('checked', false);
				flag = 0;
				$(":checkbox").each(function() {
					this.checked = false;
				})
		}
	}
	
	
	//
	// 
	//
	window.GATE.Overview.ToggleTable = function(e) {
		e = e || window.event;
		var targ = e.target || e.srcElement;
		if ($(targ).is('button') === true) targ = $(targ).find('i');
		var mainContainer  = targ.parentNode.parentNode.parentNode;
		
		$(mainContainer).find( ".gd-toggleable" ).toggle( "fast", function() {	});
			if ($(targ).attr("class") === "fa fa-2x fa-angle-up") 
				$(targ).attr("class" , "fa fa-2x fa-angle-down")
			else 
				$(targ).attr("class" , "fa fa-2x fa-angle-up")
	}
	
	
	//
	//
	//
	window.GATE.Overview.ToggleCustomModal = function(){
		var el = document.getElementById("Dicom-Modal");
		el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
	}


	//
	// 
	//
	window.GATE.Overview.RenderRelatedFilesTable = function() {
		
		var item = {}
		//getting other doc list
		var tableContainer = $('#GD-RelatedFilesContainer');
		tableContainer.empty();
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", "patient/analysis/getotherfilelist?id=" + patient.id, false); // false for synchronous request
		xmlHttp.send(null);
		var otherFilesList = xmlHttp.responseText;
		if ($(otherFilesList).find("code").text() == "500") {
			alert($(otherFilesList).find("status").text(), '')
		} else {
			//alert(otherFilesList);
			var xml = $.parseXML(otherFilesList);

			$(xml).find("other_data_list").each(
				function() {
						item.name = $(this).find("filename").text();
						item.date = $(this).find("date").text();
						item.id = $(this).find("id").text();
						item.creationDate = $(this).find("date").text();
						tableContainer.append(window.GATE.Overview.CreateFileTableItem(item));
					});
		}
	}
	
	//
	// function to fetch the file in base64 String
	//
	window.GATE.Overview.GetFile = function(dbid) {
		//window.GATE.Master.Notify('Loading DICOM-Image from database ...', "yellow");
		var xmlHttp = new XMLHttpRequest();
		if (dbid !== undefined){
			alert(dbid);
			xmlHttp.open("GET", "patient/analysis/getfile?id=" + dbid, false); 
			//xmlHttp.responseType = 'arraybuffer';
			xmlHttp.send(null);

			var response = xmlHttp.response;
			//var byteArray = new Uint8Array(response);
			var blob = new Blob([response], {type: "application/octet-stream"});
			alert(response);
			window.GATE.Master.Notify('DICOM-Image loaded', "green");
			return blob;
		}
		else{
			window.GATE.Master.Notify("DICOM File not found in the local database", "red")
			return null;
		}
	}

	
	//
	// 
	//
	window.GATE.Overview.CreateFileTableItem = function(item) {
		
		item = window.GATE.Overview.GetFileType(item);
		item.Icon = window.GATE.Overview.GetArrayValuebyKey(item.type, IconTypes)
		item.Icon = (item.Icon == null) ? "fa-file-text-o" : item.Icon ;

		var main = $('<tr/>', {
					class: 'gd-document',
					dicomId: item.id
				   });
		
		var column1 =  $('<td/>', {
						 class: ""
						 }).append($('<i/>', {
									class: "fa " + item.Icon,
									}));
									
		var column2	=	$('<td/>', {
							class: "gd-document-Name",
							text: item.name
							});
		var column3	=	$('<td/>', {
							class: "gd-document-Date",
							text: item.date
							});
		var column4 = 	$('<td/>', {
							class: "gd-document-Delete",
							}).append( $('<i/>',{
								class: 'fa fa-times',
							})).click(function (e){
								e = e || window.event;
								var targ = e.target || e.srcElement;
								if ( $(targ).is("td")) targ = targ.parentNode;
								if ( $(targ).is("i")) targ = targ.parentNode.parentNode;
								window.GATE.Overview.DeleteAdditionalFiles(targ)
							});
							
		if (item.type === "dcm"){
			column2.click(function (e) {
				e = e || window.event;
				var targ = e.target || e.srcElement;
				if ( $(targ).is("td")) targ = targ.parentNode;
				var id = $(targ).attr("dicomid");
				window.GATE.Dicom.LoadImage(id)
				$('#mrbottomleft').text($(targ).find('.gd-document-Name').text());
				}
			);
		}
	
		return  main.append(column1).append(column2).append(column3).append(column4);
	}
	
	window.GATE.Overview.SearchDocuments = function(i) {
			var terms = $(i).val().toLowerCase();
			if (!terms) {
					$(".gd-document").show();
			} else {
				$(".gd-document").hide().filter(function() {
							return ($(this).children(".gd-document-Name").text().toLowerCase().indexOf(terms) > -1);
				}).show();
			}
	}
	
	//
	//
	//
	window.GATE.Overview.GetFileType = function(item) {
		var temparray = item.name.split('.');
		item.type = temparray[temparray.length - 1];
		return item
	}
	
	//
	//
	//
	window.GATE.Overview.GetArrayValuebyKey = function(type, currentArr) {
		var result = $(currentArr).filter(function(v) {
   		 return currentArr[v].key === type; // Filter out the appropriate one
		})[0];
		
		if (result != null && result != undefined){
			return result.value;
		}
		else
			return null	
	}
	
	//
	// Delete Additional Files
	//
	window.GATE.Overview.DeleteAdditionalFiles = function(item) {
		var id = $(item).attr("dicomid");		
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("POST", "patient/analysis/deleteotherfile?id=" +id+ "&patientid=" +patient.id, false);
		xmlHttp.send(null);
		var response = xmlHttp.responseText;
		if (xmlHttp.status == 200) {
			$(item).remove();
			window.GATE.Master.Notify(response, "green");
		}else if(xmlHttp.status == 500 || xmlHttp.status == 400){
			window.GATE.Master.Notify(response, "red");
		}else if(xmlHttp.status == 404){
			window.GATE.Master.Notify("Service is down, contact administrator.", "red");
		}
	}

	//
	// Modal Dialog Functions
	//
	window.GATE.Overview.UploadAdditionalFiles = function() {
		//window.GATE.Master.Notify("Uploading addition file to database", "yellow");
		document.getElementById("gd-other-file-pid").value = patient.id;
		var additionalfileupload = document.getElementById("additionalfileupload");
		var formData = new FormData(additionalfileupload);
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("POST","patient/analysis/uploadotherfile", false);
		xmlHttp.send(formData);
		var response = xmlHttp.responseText;
		if (xmlHttp.status == 200) {
			window.GATE.Master.Notify(response, "green");
			window.GATE.Overview.RenderRelatedFilesTable();
		}else if(xmlHttp.status == 500 || xmlHttp.status == 400){
			window.GATE.Master.Notify(response, "red");
			
		}
	}


	window.GATE.Overview.UploadSoleData = function() {
		
		var handleData = (function (data) {
			var xml = $.parseXML(data);
			if ($(xml).find("code").text() == "500") {
				window.GATE.Master.Notify($(xml).find("message").text(), "red");
			}else if($(xml).find("code").text() == "400"){
				window.GATE.Master.Notify($(xml).find("message").text(), "red");
			}else{
				window.GATE.Master.Notify($(xml).find("message").text(), "green");
				window.GATE.Overview.FetchDataFromResponse(data);
				window.GATE.Overview.RenderSoleTable();
			}
	      }).bind(this);
		
		document.getElementById("pid").value = patient.id;
		var soalupload = document.getElementById("soleupload");
		var formData = new FormData(soalupload);
		var xmlHttp = new XMLHttpRequest();
		
		window.GATE.Master.Notify("Uploading sole data to database", "yellow");
		
		//xmlHttp.open("POST", "patient/analysis/uploadsoledata", false);
		xmlHttp.open("POST", "patient/analysis/uploadsoledata", true);
		xmlHttp.onload = (function () {
	          handleData(xmlHttp.responseText);
	        }).bind(this);
		xmlHttp.send(formData);
		//var response = xmlHttp.responseText;
	}

	window.GATE.Overview.SaveFunctionScoreData = function() {
		// load all function Sores to Jquery item
		var $arr = [];
		$arr = window.GATE.Overview.GetFunctionScoreValues();
		
		// retrieve functional score values
		$($arr).each(
				function(index) {

					var funcdata = window.GATE.Overview.buildXML(patient.id , $arr[index].dbID, $arr[index].key, $arr[index].value , $arr[index].date);
					//alert("Input FS - "+funcdata);
					var xmlHttp = new XMLHttpRequest();
					xmlHttp.open("POST","patient/analysis/insertfunctionscore", false); // false for synchronous request
					xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
					xmlHttp.send("patient-funcdata=" + funcdata + "");
					var response = xmlHttp.responseText;
					if (xmlHttp.status == 200) {
						window.GATE.Master.Notify(response, "green");
					}else if(xmlHttp.status == 500 || xmlHttp.status == 400){
						window.GATE.Master.Notify(response, "red");
					}

				});

		window.GATE.Overview.RenderFunctionScoreTable();
	}

	
	window.GATE.Overview.buildXML = function(patientId, dbID, fskey, fsvalue, fsdate) {
		var patientFunctionScoredata = document.createElement("functionscoredetails");

		var Node = document.createElement("patient_functionscore");
		// patient id
		var patient_id = document.createElement("patient_id");
		var patientIdContent = document.createTextNode(patientId);
		patient_id.appendChild(patientIdContent);
		Node.appendChild(patient_id);
		
		// id
		var id = document.createElement("id");
		var idContent = document.createTextNode(dbID);
		id.appendChild(idContent);
		Node.appendChild(id);

		// func key
		var func_name = document.createElement("function_key");
		var functionNameContent = document.createTextNode(fskey);
		func_name.appendChild(functionNameContent);
		Node.appendChild(func_name);

		// func value
		var func_val = document.createElement("function_value");
		var functionValueContent = document.createTextNode(fsvalue);
		func_val.appendChild(functionValueContent);
		Node.appendChild(func_val);
		
		// func value
		var func_date = document.createElement("function_date");
		var functionDateContent = document.createTextNode(fsdate);
		func_date.appendChild(functionDateContent);
		Node.appendChild(func_date);

		patientFunctionScoredata.appendChild(Node);

		return patientFunctionScoredata.innerHTML;
	}
	
	
	window.GATE = (window.GATE || {});
	window.GATE.Helper = (window.GATE.Helper || {});
	
	window.GATE.Helper.DrawLineChart = function(data){
		
		Chart.types.Line.extend({
    name: "LineAlt",
    draw: function () {
        Chart.types.Line.prototype.draw.apply(this, arguments);
        
        var ctx = this.chart.ctx;
        ctx.save();
        // text alignment and color
        ctx.textAlign = "center";
        ctx.textBaseline = "bottom";
        ctx.fillStyle = this.options.scaleFontColor;
        // position
        var x = this.scale.xScalePaddingLeft * 0.4;
        var y = this.chart.height / 2;
        // change origin
        ctx.translate(x, y)
        // rotate text
        ctx.rotate(-90 * Math.PI / 180);
        ctx.fillText(this.datasets[0].label, 0, 0);
        ctx.restore();
    	}
		});
		
		var lineChartData = {
			labels : data.dates,
			datasets : [
				{
					label: "Summary",
					fillColor : "rgba(252, 148, 50,0.2)",
					strokeColor : "rgba(252, 148, 50,1)",
					pointColor : "rgba(252, 148, 50,1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(252, 148, 50,1)",
					data : data.nrs
				},
				{
					label: "PAIN VAS",
					fillColor : "rgba(151,187,205,0.2)",
					strokeColor : "rgba(151,187,205,1)",
					pointColor : "rgba(151,187,205,1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(151,187,205,1)",
					data : data.vas
				},
				{
					label: "Content Level",
					fillColor : "rgba(73, 147, 8,0.2)",
					strokeColor : "rgba(73, 147, 8,0.7)",
					pointColor : "rgba(73, 147, 8,0.7)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(73, 147, 8,1)",
					data : data.contentlvl
				}
			]
			



		}
		var ctx = document.getElementById("canvas").getContext("2d");
		window.myLine = new Chart(ctx).LineAlt(lineChartData, {
			responsive: true,
			pointDot : false,
			scaleShowGridLines : false,
			bezierCurve : true,
			scaleLabel: "          <%=value%>",
		});
	}
		
		
	
	window.GATE.Helper.SearchFunctionScore = function(i) {
						var terms = $(i).val().toLowerCase();
						if (!terms) {
							window.GATE.Helper.DatePickerFrom($("#GD-FilterDatePickerFrom"));
						} else {
							$('.gd-FunctionScoreContainer').find($(".gd-Scores")).hide().filter(
									function() {
										return ($(this).find(".gd-FunctionScore-Name").val().toLowerCase().indexOf(terms) > -1);
									}).show();
						}
						
						
						
	}

	
	window.GATE.Helper.DatePickerFrom = function(i) {
							var tempLowerValue = $('#GD-FilterDatePickerFrom').val().toLowerCase().split(" ")[0];
							var tempUpperValue = $('#GD-FilterDatePickerTo').val().toLowerCase().split(" ")[0]
							
							var lowerValue = (tempLowerValue != "" ) ? moment(tempLowerValue,'DD/MM/YYYY')._d : moment("01/01/1900", "DD/MM/YYYY")._d;
							var upperTerm = (tempUpperValue != "") ? moment(tempUpperValue,'DD/MM/YYYY')._d : upperTerm = lowerValue;

							if (!lowerValue || (tempLowerValue === '' && tempUpperValue === '')) {
								$(".option").show();
								$(".gd-Scores").show();
							} else {
								$(".option").hide().filter(function() {
													var i = new Date($(this).find("#GD-Item-CreationDate").text());
													if (upperTerm) {
														return (i >= lowerValue && i <= upperTerm);
													} else {
														return (i >= lowerValue);
													}
												}).show();
												
								$(".gd-Scores").hide().filter(function() {
													var i = new Date($(this).find("#GD-Item-CreationDate").val());
													i.setHours(0);
													if (upperTerm) {
														return (i >= lowerValue && i <= upperTerm);
													} else {
														return (i >= lowerValue);
													}
												}).show();										
						}
	}
	
})(jQuery);

$(document).ready(function() {
	window.GATE.Overview.Initialize();
	
});
