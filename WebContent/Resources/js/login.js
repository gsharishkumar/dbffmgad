(function() {
	window.GATE = (window.GATE || {});
	window.GATE.login = (window.GATE.login || {});

	window.GATE.login.check = function() {
		var user = window.GATE.login.BuildUserXML();
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open("POST", "patient/login/authentication", false); // false
																		// for
		// synchronous
		// request
		xmlHttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlHttp.send("user=" + user + "");
		var response = xmlHttp.responseText;
		if (xmlHttp.status == 200) {
			window.location.href = "Start.html";
		} else if (xmlHttp.status == 401) {
			window.GATE.Master
					.ShowMessage(
							'Invalid Credential... Enter a valid Username and Password...',
							'danger')
			var elements = document.getElementsByTagName("input");
			for (var i = 0; i < elements.length; i++) {
				if (elements[i].type == "text" || elements[i].type == "password") {
					elements[i].value = "";
				}
			}
		} else {
			alert(response);
		}
	}

	window.GATE.login.BuildUserXML = function() {
		var user = document.createElement("userCredentials");
		var Node = document.createElement("user");

		// username
		var username = document.createElement("username");
		var usernameContent = document.createTextNode(document
				.getElementById("GD-UserName").value);
		username.appendChild(usernameContent);
		Node.appendChild(username);

		// password
		var password = document.createElement("password");
		var passwordContent = document.createTextNode(document
				.getElementById("GD-Password").value);
		password.appendChild(passwordContent);
		Node.appendChild(password);

		user.appendChild(Node);

		return user.innerHTML;
	}

})(jQuery);