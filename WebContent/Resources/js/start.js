(function() {
	window.GATE = (window.GATE || {});
	window.GATE.Dashboard = (window.GATE.Dashboard || {});


	window.GATE.Dashboard.RearangeTiles = function() {
		var cardContainer;
		cardContainer = jQuery('#cardContainer');
		cardContainer.masonry({
			columnWidth : 150,
			gutter : 12,
			itemSelector : '.carditem'
		});
		cardContainer.css('visibility', 'visible').hide().fadeIn();
		jQuery(window).on('load', function() {
			cardContainer.masonry(); // reload to position properly
		});
	}

	window.GATE.Dashboard.RenderSearchContainerContent = function() {
		//window.GATE.Master.Notify("Loading patients...", 'yellow');
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", "patient/metadata/select-patients", false);
		xmlHttp.send(null);
		var patientData = xmlHttp.responseText;
	
		var count = 1;
		var xml = $.parseXML(patientData);
		if ($(xml).find("code").text() == "500") {
			//window.GATE.Master.ShowMessage($(xml).find("status").text(), '')
			window.GATE.Master.Notify($(xml).find("status").text(), "red");
		} else {
			
			var patientsSearchContainer = $('#search_fieldContainer');
			$(xml).find("patient").each(
					function() {
						var name = $(this).find("name").text();
						var id = $(this).find("id").text();
						var item = window.GATE.Dashboard.RenderSearchItem(name,
								id, count);
						patientsSearchContainer.append(item);
						count = count +1;
					});
					
			$('#GD-TotalUserCount').text(count-1);
			window.GATE.Master.Notify("Patients loaded...", "green");
		}
	}

	window.GATE.Dashboard.RenderSearchItem = function(name, id, count) {

		var item = "<a id='patient-" + count + "'  href='Overview.html?id="+ id + "' class='option'>"
					+ "<div class='patientName'>" + name + "</div>"
					+ "<div class='patientID'>" + id + "</div>" 
				  + "</a>";
		return item;
	}

	window.GATE.Dashboard.OnTheFlySearch = function() {

	}

})(jQuery);

$(document).ready(
		function() {
			window.GATE.Dashboard.RearangeTiles();
			window.GATE.Dashboard.RenderSearchContainerContent();

			$("#search_field").keyup(
					function() {
						var terms = $(this).val().toLowerCase();
						if (!terms) {
							$(".option").show();
						} else {
							$(".option").hide().filter(
									function() {
										return ($(this)
												.children(".patientName")
												.text().toLowerCase().indexOf(
														terms) > -1)
												|| ($(this).children(
														".patientID").text()
														.toLowerCase().indexOf(
																terms) > -1);
									}).show();
						}
					});

		});