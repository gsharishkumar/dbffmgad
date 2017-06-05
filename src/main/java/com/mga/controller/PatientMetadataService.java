package com.mga.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mga.model.HibernateUtil;
import com.mga.beans.Patient;
import com.mga.beans.PatientMetadata;
import com.mga.beans.Patients;

@Path("metadata")
public class PatientMetadataService {

	Logger log = Logger.getLogger(PatientMetadataService.class.getName());

	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response insertPatientMetadata(
			@FormParam("patient-metadata") PatientMetadata uiPatientMetadata) {

		log.debug("Request received from UI for Patient "
				+ uiPatientMetadata.getFirstName() + ", "
				+ uiPatientMetadata.getLastName() + " Metadata Create ");

		Integer statusCode = 200;
		String statusMsg = null;

		if (uiPatientMetadata.getFirstName()==null
				|| uiPatientMetadata.getFirstName().isEmpty()
				|| uiPatientMetadata.getLastName()== null
				|| uiPatientMetadata.getLastName().isEmpty()
				|| uiPatientMetadata.getGender() == null
				|| uiPatientMetadata.getGender().isEmpty()
				|| uiPatientMetadata.getCity()==null
				|| uiPatientMetadata.getCity().isEmpty()
				|| uiPatientMetadata.getCountry()== null
				|| uiPatientMetadata.getCountry().isEmpty()) {
			log.debug("Bad request");
			
			statusCode = 400;
			statusMsg = "Bad Request. Mandatory fields are sent empty or null.";
		} else {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				com.mga.model.PatientMetadata dbPatientMetadata = new com.mga.model.PatientMetadata();
				dbPatientMetadata
						.setFirstName(uiPatientMetadata.getFirstName());
				dbPatientMetadata.setLastName(uiPatientMetadata.getLastName());
				dbPatientMetadata.setGender(uiPatientMetadata.getGender());
				if(uiPatientMetadata.getBirthDate() != null && !uiPatientMetadata.getBirthDate().isEmpty())
				{
				dbPatientMetadata.setBirthDate(new java.sql.Date(MGAUtil
						.birthDateStringToDate(
								uiPatientMetadata.getBirthDate(), "Insert")
						.getTime()));
				}
				else
				{
					dbPatientMetadata.setBirthDate(null);
				}
				dbPatientMetadata.setContactNumber(MGAUtil
						.contactNoPlusConversion(uiPatientMetadata
								.getContactNumber()));
				dbPatientMetadata.setEmailId(uiPatientMetadata.getEmailId());
				dbPatientMetadata.setHouseNumber(uiPatientMetadata
						.getHouseNumber());
				dbPatientMetadata.setStreet(uiPatientMetadata.getStreet());
				dbPatientMetadata.setPostalCode(uiPatientMetadata
						.getPostalCode());
				dbPatientMetadata.setCity(uiPatientMetadata.getCity());
				dbPatientMetadata.setCountry(uiPatientMetadata.getCountry());
				dbPatientMetadata.setWeight(uiPatientMetadata.getWeight());
				dbPatientMetadata.setHeight(uiPatientMetadata.getHeight());
				dbPatientMetadata.setNotes(uiPatientMetadata.getNotes());
				Date currentDate = new Date();
				dbPatientMetadata.setCreationDate(new java.sql.Date(currentDate
						.getTime()));

				session.save(dbPatientMetadata);
				transaction.commit();
				HibernateUtil.shutdown();

				log.debug(uiPatientMetadata.getFirstName() + ", "
						+ uiPatientMetadata.getLastName()
						+ " metadata created in DB ");

			} catch (HibernateException e) {
				statusCode = 500;
				statusMsg = "Internal Server Error, contact administrator.";
				log.error("Error while inserting the Patient Metadata - "
						+ e.getMessage());
				e.printStackTrace();
				return Response.status(statusCode).entity(statusMsg).build();
			} finally {
				session.close();
			}

			statusMsg = uiPatientMetadata.getFirstName() + ", "
					+ uiPatientMetadata.getLastName()
					+ " patient metadata created.";
		}

		return Response.status(statusCode).entity(statusMsg).build();
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updatePatientMetadata(
			@FormParam(value = "patient-database-id") Integer id,
			@FormParam(value = "patient-metadata") PatientMetadata uiPatientMetadata) {

		log.debug("Request received from UI for Patient "
				+ uiPatientMetadata.getFirstName() + ", "
				+ uiPatientMetadata.getLastName() + " with ID " + id
				+ " metadata update ");

		Integer statusCode = 200;
		String statusMsg = null;

		if (id == 0 || id == null
	            ||uiPatientMetadata.getFirstName()==null
				|| uiPatientMetadata.getFirstName().isEmpty()
				|| uiPatientMetadata.getLastName()== null
				|| uiPatientMetadata.getLastName().isEmpty()
				|| uiPatientMetadata.getGender() == null
				|| uiPatientMetadata.getGender().isEmpty()
				|| uiPatientMetadata.getCity()==null
				|| uiPatientMetadata.getCity().isEmpty()
				|| uiPatientMetadata.getCountry()== null
				|| uiPatientMetadata.getCountry().isEmpty()) {
			log.debug("Bad request");
			statusCode = 400;
			statusMsg = "Bad Request. Mandatory fields are sent empty or null.";
		} else {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				String hql = "UPDATE PatientMetadata set firstName = :firstName, lastName = :lastName, gender = :gender, birthDate = :birthDate, contactNumber = :contactNumber, emailId = :emailId, houseNumber = :houseNumber, street = :street, postalCode = :postalCode, city = :city, country = :country, weight = :weight, height = :height, notes = :notes, lastUpdate = :lastUpdate "
						+ "WHERE id = :id";
				Query query = session.createQuery(hql);
				query.setParameter("firstName",
						uiPatientMetadata.getFirstName());
				query.setParameter("lastName", uiPatientMetadata.getLastName());
				query.setParameter("gender", uiPatientMetadata.getGender());
				if(uiPatientMetadata.getBirthDate() != null && !uiPatientMetadata.getBirthDate().isEmpty())
				{
				query.setParameter(
						"birthDate",
						new java.sql.Date(MGAUtil.birthDateStringToDate(
								uiPatientMetadata.getBirthDate(), "Update")
								.getTime()));
				}else
				{
					query.setParameter(	"birthDate",null);
				}
				query.setParameter("contactNumber", MGAUtil
						.contactNoPlusConversion(uiPatientMetadata
								.getContactNumber()));
				query.setParameter("emailId", uiPatientMetadata.getEmailId());
				query.setParameter("houseNumber",
						uiPatientMetadata.getHouseNumber());
				query.setParameter("street", uiPatientMetadata.getStreet());
				query.setParameter("postalCode",
						uiPatientMetadata.getPostalCode());
				query.setParameter("city", uiPatientMetadata.getCity());
				query.setParameter("country", uiPatientMetadata.getCountry());
				query.setParameter("weight", uiPatientMetadata.getWeight());
				query.setParameter("height", uiPatientMetadata.getHeight());
				query.setParameter("notes", uiPatientMetadata.getNotes());
				query.setParameter("id", id);
				Date currentDate = new Date();
				query.setParameter("lastUpdate",
						new java.sql.Date(currentDate.getTime()));
				int result = query.executeUpdate();
				log.debug(uiPatientMetadata.getFirstName() + ", "
						+ uiPatientMetadata.getLastName() + " with ID " + id
						+ " metadata updated in DB");
				transaction.commit();
			} catch (HibernateException e) {
				//transaction.rollback();
				statusCode = 500;
				statusMsg = "Internal Server Error, contact administrator.";
				log.error("Error while updating the Patient Metadata - "
						+ e.getMessage());
				e.printStackTrace();
				return Response.status(statusCode).entity(statusMsg).build();
			} finally {
				session.close();
			}

			statusMsg = uiPatientMetadata.getFirstName() + ", "
					+ uiPatientMetadata.getLastName()
					+ " patient metadata updated.";

		}
		return Response.status(statusCode).entity(statusMsg).build();
	}

	@POST
	@Path("/delete")
	public Response deletePatientMetadata(@QueryParam(value = "id") Integer id) {

		log.debug("Request received from UI for Patient " + " with ID " + id
				+ " metadata delete");

		Integer statusCode = 200;
		String statusMsg = null;

		if (id == 0 || id == null) {
			log.debug("Bad request");
			statusCode = 400;
			statusMsg = "Bad Request. Mandatory fields are sent empty or null.";
		} else {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				String hql = "DELETE FROM PatientOtherFiles WHERE patientId = :id";
				Query query = session.createQuery(hql);
				query.setParameter("id", id);
				int result = query.executeUpdate();
				log.debug("Records deleted from PatientOtherFiles - "+result);
				hql = "DELETE FROM PatientSoleDataFiles WHERE patientId = :id";
				query = session.createQuery(hql);
				query.setParameter("id", id);
				result = query.executeUpdate();
				log.debug("Records deleted from PatientSoleDataFiles - "+result);
				hql = "DELETE FROM PatientSoleData WHERE patientId = :id";
				query = session.createQuery(hql);
				query.setParameter("id", id);
				result = query.executeUpdate();
				log.debug("Records deleted from PatientSoleData - "+result);
				hql = "DELETE FROM PatientMiscData WHERE patientId = :id";
				query = session.createQuery(hql);
				query.setParameter("id", id);
				result = query.executeUpdate();
				log.debug("Records deleted from PatientMiscData - "+result);
				hql = "DELETE FROM PatientFunctionScoreData WHERE patientId = :id";
				query = session.createQuery(hql);
				query.setParameter("id", id);
				result = query.executeUpdate();
				log.debug("Records deleted from PatientFunctionScoreData - "+result);
				hql = "DELETE FROM PatientMetadata WHERE id = :id";
				query = session.createQuery(hql);
				query.setParameter("id", id);
				result = query.executeUpdate();
				log.debug("Records deleted from PatientMetadata - "+result);
				log.debug("Patient " + " with ID " + id
						+ " metadata deleted from DB");
				transaction.commit();
			} catch (HibernateException e) {
				// transaction.rollback();
				statusCode = 500;
				statusMsg = "Internal Server Error, contact administrator.";
				log.error("Error while deleting the Patient Metadata - "
						+ e.getMessage());
				e.printStackTrace();
				return Response.status(statusCode).entity(statusMsg).build();
			} finally {
				session.close();
			}

			statusMsg = "Requested patient metadata deleted succesfully.";
		}

		return Response.status(statusCode).entity(statusMsg).build();
	}

	@GET
	@Path("/select-patients")
	@Produces({ MediaType.APPLICATION_XML })
	public Patients selectPatients() {

		log.debug("Request received from UI for Patients Search");

		Patients patients = new Patients();
		List<Patient> patientList = new ArrayList<Patient>();

		Integer statusCode = 200;
		String statusMsg = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hql = "Select id, firstName, lastName FROM PatientMetadata ORDER BY firstName";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<com.mga.model.PatientMetadata> results = query.list();
			log.debug("Patients ID-Name fetched from DB");
			log.debug("Patients fetched Result:");
			for (Iterator it = results.iterator(); it.hasNext();) {
				Patient patient = new Patient();
				Object[] myResult = (Object[]) it.next();
				Integer id = (Integer) myResult[0];
				String firstName = (String) myResult[1];
				String lastName = (String) myResult[2];
				patient.setId(id);
				patient.setName(firstName + ", " + lastName);
				patientList.add(patient);
				log.debug(id + " - " + firstName + ", " + lastName);
			}

			patients.setPatient(patientList);
			transaction.commit();
			statusMsg = "Success";
			patients.setCode(statusCode);
			patients.setStatus(statusMsg);
		} catch (HibernateException e) {
			// transaction.rollback();
			statusCode = 500;
			statusMsg = "Internal Server Error, contact administrator.";
			log.error("Error while selecting the Patients - " + e.getMessage());
			e.printStackTrace();
			patients.setCode(statusCode);
			patients.setStatus(statusMsg);
			return patients;
		} finally {
			session.close();
		}

		return patients;

	}

	@GET
	@Path("/select-patient")
	@Produces({ MediaType.APPLICATION_XML })
	public PatientMetadata selectPatient(@QueryParam(value = "id") Integer id) {

		log.debug("Request received from UI for Patient Metadata with ID " + id);

		Integer statusCode = 200;
		String statusMsg = null;

		PatientMetadata patient = new PatientMetadata();

		if (id == 0 || id == null) {
			log.debug("Bad request");
			statusCode = 400;
			statusMsg = "Bad Request. Mandatory fields are sent empty or null.";
		} else {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				String hql = "select firstName, lastName, gender, DATE_FORMAT(birthDate,'%m/%d/%Y') as birthDate, contactNumber, emailId, houseNumber, street, postalCode, city, country, weight, height, notes FROM PatientMetadata WHERE id= :id";
				Query query = session.createQuery(hql);
				query.setParameter("id", id);
				@SuppressWarnings("unchecked")
				List<com.mga.model.PatientMetadata> results = query.list();
				log.debug("Patient Metadata with ID " + id
						+ " is fetched from DB");
				log.debug("Patient Metadata with ID " + id + " result:");
				Integer age = 0;
				for (Iterator it = results.iterator(); it.hasNext();) {
					Object[] result = (Object[]) it.next();
					String firstName = (String) result[0];
					String lastName = (String) result[1];
					String gender = (String) result[2];
					String dob = (String) result[3];
					age = MGAUtil.calculateAge(dob);
					String contactNo = (String) result[4];
					String emailId = (String) result[5];
					String houseNo = (String) result[6];
					String street = (String) result[7];
					String postalCode = (String) result[8];
					String city = (String) result[9];
					String country = (String) result[10];
					float wt = (float) result[11];
					float ht = (float) result[12];
					String notes = (String) result[13];

					log.debug("ID - " + id + " || " + "Name - " + firstName
							+ ", " + lastName + " || " + "Gender - " + gender
							+ " || " + "DOB - " + dob + " || " + "Age - " + age
							+ " || " + "Contact No - " + contactNo + " || "
							+ "Email Id - " + emailId + " || " + "House No - "
							+ houseNo + " || " + "Street - " + street + " || "
							+ "Postal Code - " + postalCode + " || "
							+ "City - " + city + " || " + "Country - "
							+ country + " || " + "Weight - " + wt + " || "
							+ "Height - " + ht + " || " + "Notes - " + notes);

					patient.setFirstName(firstName);
					patient.setLastName(lastName);
					patient.setGender(gender);
					patient.setBirthDate(dob);
					patient.setAge(age);
					patient.setContactNumber(contactNo);
					patient.setEmailId(emailId);
					patient.setHouseNumber(houseNo);
					patient.setStreet(street);
					patient.setPostalCode(postalCode);
					patient.setCity(city);
					patient.setCountry(country);
					patient.setWeight(wt);
					patient.setHeight(ht);
					patient.setNotes(notes);
				}
				transaction.commit();
				patient.setCode(statusCode);
				statusMsg = "Success";
				patient.setStatus(statusMsg);
			} catch (HibernateException e) {
				// transaction.rollback();
				statusCode = 500;
				statusMsg = "Internal Server Error, contact administrator.";
				log.error("Error while selecting the Patient - "
						+ e.getMessage());
				e.printStackTrace();
				patient.setCode(statusCode);
				patient.setStatus(statusMsg);
				e.printStackTrace();
				return patient;
			} finally {
				session.close();
			}

		}
		return patient;

	}

}