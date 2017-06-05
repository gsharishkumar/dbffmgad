(function () {
    window.GATE = (window.GATE || {});
    window.GATE.Master = (window.GATE.Master|| {});
		
		window.GATE.Master.Initialize = function (){
			window.GATE.Master.RenderHeader();
			window.GATE.Master.NotificationCustomyzing();
		}
	
	window.GATE.Master.RenderHeader = function (){
		
		$('#GD-Header').append($('<div/>',{
				class: "gd-Header-Column1",
				id: "GD-Header-Column1"
		})).append($('<div/>',{
				class: "gd-Header-Column2",
				id: "GD-Header-Column2",
				}).append($('<div/>',{
					class: "gd-Navigation-Container",
					id: "GD-Navigation-Container"
				})));
		
		var uds = $('<div/>',{
			id: 'GD-UniLogoContainer',
			class: 'gd-uniLogoContainer'
			}).append( $('<a/>', {
				href: "http://www.uni-saarland.de/"
				}).append( $('<img/>', {
					src: 'Resources/Images/uds.png',
					width: '150',
					height: '38',
					border: '0'
					}))
				);
		
		var uks = $('<div/>',{
			id: 'GD-UksLogoContainer',
			class: 'gd-uksLogoContainer'
			}).append( $('<a/>', {
				href: "http://www.uniklinikum-saarland.de/de/"
				}).append( $('<img/>', {
					src: 'Resources/Images/uks.svg',
					width: '175',
					height: '49',
					border: '0'
					}))
				);
				
		var startLink = $('<a/>',{
						id: 'GD-Navigation-Link',
						class: 'gd-Navigation-Link',
						href: "Home.html"
						}).append( $('<div/>', {
							text: 'Home'
							})
						);
		var patientLink = $('<a/>',{
						id: 'GD-Navigation-Link',
						class: 'gd-Navigation-Link',
						href: "Patients.html"
						}).append( $('<div/>', {
							text: 'Patients'
							})
						);
		var medicalsLink = $('<a/>',{
						id: 'GD-Navigation-Link',
						class: 'gd-Navigation-Link',
						href: "Medicals.html"
						}).append( $('<div/>', {
							text: 'Medicals'
							})
						);
			
		$('#GD-Header-Column1').append(uks);
		$('#GD-Navigation-Container').append(startLink).append(patientLink).append(medicalsLink);
	}
	
    window.GATE.Master.GetURLParam = function ( name, url ) {
	  if (!url) url = location.href;

	  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
	  var regexS = "[\\?&]"+name+"=([^&#]*)";
	  var regex = new RegExp( regexS );
	  var results = regex.exec( url );

	  return results == null ? null : results[1];
	}
    window.GATE.Master.Notify = function( text , type ){
        $.notify(
			text, 
			{ style: type,
			  position:"bottom right", 
			  showAnimation: 'slideDown',
			  clickToHide: true
			}
		);
    }
    window.GATE.Master.NotificationCustomyzing = function (){
        $.notify.addStyle('yellow', {
		html: "<div><i class='fa fa-circle-o-notch fa-spin-custom'></i><span class='gd-notification-yellow' style='padding-left:15px' data-notify-text></span></div>",
		classes: {
			base: {
			"white-space": "nowrap",
			"color": "#a0800c",
			"font-size": '1.3em',
			"padding": "10 10 10 10",
			"box-shadow": "0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)",
			"background-color": "rgba(247, 210, 98,1.00)",
			}
		}
		});
		
		$.notify.addStyle('green', {
		html: "<div><i class='fa fa-check' ></i><span class='gd-notification-green' style='padding-left:15px' data-notify-text></span></div>",
		classes: {
			base: {
			"white-space": "nowrap",
			"color": "#28722d",
			"font-size": '1.0em',
			"padding": "10 10 10 10",
			"box-shadow": "0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)",
			"background-color": "rgba(115, 196, 121,1.00)",
			}
		}
		});
		
		$.notify.addStyle('red', {
		html: "<div><i class='fa fa-ban' ></i><span class='gd-notification-red' style='padding-left:15px' data-notify-text></span></div>",
		classes: {
			base: {
			"white-space": "nowrap",
			"color": "#913527",
			"font-size": '1.3em',
			"padding": "10 10 10 10",
			"box-shadow": "0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)",
			"background-color": "rgba(193, 111, 100,1.00)",
			}
		}
		});  
    }

})(jQuery);
$(document).ready(function() {
	window.GATE.Master.Initialize();
});