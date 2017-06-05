package com.mga.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.mga.beans.UserCredential;

@Path("login")
public class LoginService {

	public String defaultUser;
	public String defaultPassword;

	Logger log = Logger.getLogger(LoginService.class.getName());

	@POST
	@Path("/authentication")
	// @Consumes({ MediaType.APPLICATION_XML })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response checkLogin(@FormParam("user") UserCredential user)
			throws IOException {
		// UserCredential user) throws IOException {

		log.debug("Login request received from UI");

		String username = user.getUsername();
		String password = user.getPassword();

		log.debug("Username - " + username);
		log.debug("Password - " + password);

		Properties properties = new Properties();
		InputStream in = getClass().getResourceAsStream("/user.properties");
		properties.load(in);
		in.close();
		// defaultUser = properties.getProperty("username");
		// defaultPassword = properties.getProperty("password");
		defaultUser = "username." + username;
		defaultPassword = properties.getProperty(defaultUser);

		String output = null;
		Integer statusCode = 200;

		if (password.equals(defaultPassword)) {
			output = "Login successful";
		} else {
			statusCode = 401;
			output = "Login failed";
		}

		return Response.status(statusCode).entity(output).build();

	}

	@POST
	@Path("/createuser")
	@Consumes({ MediaType.APPLICATION_XML })
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser(
	// @FormParam("user") UserCredential user) {
			UserCredential user) throws IOException {

		log.debug("Create a new user request received from UI");

		String username = user.getUsername();
		String password = user.getPassword();

		log.debug("Username - " + username);
		log.debug("Password - " + password);

		Properties properties = new Properties();
		OutputStream out = null;
		try {
			out = new FileOutputStream(this.getClass().getResource("/user.properties").getPath(),true);
			defaultUser = "username." + username;
			properties.setProperty(defaultUser, password);
			properties.store(out, null);
			out.close();
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		String output = "User succesfully created";

		return Response.status(200).entity(output).build();

	}

}
