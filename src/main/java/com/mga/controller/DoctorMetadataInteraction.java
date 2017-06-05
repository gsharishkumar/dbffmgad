package com.mga.controller;

import java.util.ArrayList;
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

import com.mga.controller.DoctorMetadataInteraction;
import com.mga.beans.Doctor;
import com.mga.beans.DoctorMetadata;
import com.mga.beans.Doctors;
import com.mga.model.HibernateUtil;
import java.util.Iterator;

@Path("doctormetadata")
public class DoctorMetadataInteraction {

	Logger log = Logger.getLogger(DoctorMetadataInteraction.class.getName());

	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//@Consumes({ MediaType.APPLICATION_XML })
	public Response insertDoctorMetadata(
			@FormParam("doctor-metadata") DoctorMetadata data) {
			//DoctorMetadata data) {

		log.debug("Request received from UI to create a doctor metadata");

		Integer statusCode = 200;
		String statusMsg = null;

		if (data.getFirstName().equals(null) || data.getFirstName().equals("")
				|| data.getLastName().equals(null)
				|| data.getLastName().equals("")
				|| data.getDoctorId().equals(null)
				|| data.getDoctorId().equals("")
				|| data.getMedicalCenter().equals(null)
				|| data.getMedicalCenter().equals("")) {
			log.debug("Bad request");
			statusCode = 400;
			statusMsg = "Bad Request. Mandatory fields are sent empty or null.";
		} else {
			log.debug("Request received from UI for Doctor "
					+ data.getFirstName() + ", " + data.getLastName()
					+ " Metadata Create ");
			try
			{
			 Integer i = Integer.parseInt(data.getDoctorId());	
			 if(i<0)
				 throw new NumberFormatException();
			}
			catch(NumberFormatException e){
				statusCode = 400;
				statusMsg = "Bad Request. Please enter a valid id.";
				return Response.status(statusCode).entity(statusMsg).build();
			}

			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			
			try {
				transaction = session.beginTransaction();
				com.mga.model.DoctorMetadata dbDoctorMetadata = new com.mga.model.DoctorMetadata();
				dbDoctorMetadata.setId(data.getDoctorId());
				dbDoctorMetadata.setFirstName(data.getFirstName());
				dbDoctorMetadata.setLastName(data.getLastName());
				dbDoctorMetadata.setMedicalCenter(data.getMedicalCenter());
				dbDoctorMetadata.setNotes(data.getNotes());
				session.save(dbDoctorMetadata);
				transaction.commit();
				HibernateUtil.shutdown();
			} catch (HibernateException e) {
				statusCode = 500;
				statusMsg = "Internal Server Error, contact administrator.";
				log.error("Error while inserting the Doctor Metadata - "
						+ e.getMessage());
				e.printStackTrace();
				return Response.status(statusCode).entity(statusMsg).build();
			} finally {
				session.close();
			}

			statusMsg = data.getFirstName() + ", " + data.getLastName()
					+ " doctor metadata inserted.";

		}

		return Response.status(statusCode).entity(statusMsg).build();
	}

	@POST
	@Path("/delete")
	public Response deletePatientMetadata(@QueryParam(value = "id") String id) {

		log.debug("Request received from UI for doctor with ID " + id
				+ " metadata delete");

		Integer statusCode = 200;
		String statusMsg = null;

		if (id.equals("0") || id.equals("") || id == null) {
			log.debug("Bad request");
			statusCode = 400;
			statusMsg = "Bad Request. Mandatory fields are sent empty or null.";
		} else {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				String hql = "DELETE FROM DoctorMetadata " + "WHERE id = :id";
				Query query = session.createQuery(hql);
				query.setParameter("id", id);
				int result = query.executeUpdate();
				log.debug("Doctor " + " with ID " + id
						+ " metadata deleted from DB");
				transaction.commit();
			} catch (HibernateException e) {
				// transaction.rollback();
				statusCode = 500;
				statusMsg = "Internal Server Error, contact administrator.";
				log.error("Error while deleting the Doctor Metadata - "
						+ e.getMessage());
				e.printStackTrace();
				return Response.status(statusCode).entity(statusMsg).build();
			} finally {
				session.close();
			}

			statusMsg = "Requested doctor metadata deleted succesfully.";
		}

		return Response.status(statusCode).entity(statusMsg).build();
	}

	@GET
	@Path("/select-doctors")
	@Produces({ MediaType.APPLICATION_XML })
	public Doctors selectPatients() {

		log.debug("Request received from UI for doctor Search");

		Doctors docs = new Doctors();
		List<Doctor> docList = new ArrayList<Doctor>();

		Integer statusCode = 200;
		String statusMsg = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			String hql = "Select id, firstName, lastName, medicalCenter, notes FROM DoctorMetadata ORDER BY firstName";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<com.mga.model.DoctorMetadata> results = query.list();
			log.debug("Patients ID-Name fetched from DB");
			log.debug("Patients fetched Result:");
			for (Iterator it = results.iterator(); it.hasNext();) {
				Doctor doc = new Doctor();
				Object[] myResult = (Object[]) it.next();
				String id = (String) myResult[0];
				String firstName = (String) myResult[1];
				String lastName = (String) myResult[2];
				String medicalCenter = (String) myResult[3];
				String notes = (String) myResult[4];
				doc.setId(id);
				doc.setName(firstName + ", " + lastName);
				doc.setmedicalCenter(medicalCenter);
				doc.setNotes(notes);
				docList.add(doc);
				log.debug(id + " - " + firstName + ", " + lastName);
			}
			docs.setDoctor(docList);
			transaction.commit();
			statusMsg = "Success";

		} catch (HibernateException e) {
			// transaction.rollback();
			statusCode = 500;
			statusMsg = "Internal Server Error, contact administrator.";
			log.error("Error while selecting the Patients - " + e.getMessage());
			e.printStackTrace();
			docs.setCode(statusCode);
			docs.setStatus(statusMsg);
			return docs;
		} finally {
			session.close();
		}

		docs.setCode(statusCode);
		docs.setStatus(statusMsg);

		return docs;
	}

	@GET
	@Path("/select-doctor")
	@Produces({ MediaType.APPLICATION_XML })
	public DoctorMetadata selectDoctor(@QueryParam(value = "id") String id) {

		log.debug("Request received from UI to fetch Doctor Metadata with ID "
				+ id);
		Doctors docs = new Doctors();
		List<Doctor> docList = new ArrayList<Doctor>();

		Integer statusCode = 200;
		String statusMsg = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		DoctorMetadata doc = new DoctorMetadata();
		try {
			transaction = session.beginTransaction();
			String hql = "Select firstName, lastName, medicalCenter, notes FROM DoctorMetadata WHERE id = :id ";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<com.mga.model.DoctorMetadata> results = query.list();
			log.debug("Patients ID-Name fetched from DB");
			log.debug("Patients fetched Result:");
			for (Iterator it = results.iterator(); it.hasNext();) {
				Object[] myResult = (Object[]) it.next();
				String firstName = (String) myResult[0];
				String lastName = (String) myResult[1];
				String medicalCenter = (String) myResult[2];
				String notes = (String) myResult[3];
				doc.setDoctorId(id);
				doc.setFirstName(firstName);
				doc.setLastName(lastName);
				doc.setMedicalCenter(medicalCenter);
				doc.setNotes(notes);
				// docList.add(doc);
				log.debug(id + " - " + firstName + ", " + lastName);
			}
			// docs.setDoctor(docList);;

			transaction.commit();
			statusMsg = "Success";

		} catch (HibernateException e) {
			// transaction.rollback();
			statusCode = 500;
			statusMsg = "Internal Server Error, contact administrator.";
			log.error("Error while selecting the Patients - " + e.getMessage());
			e.printStackTrace();
			doc.setCode(statusCode);
			doc.setStatus(statusMsg);
			return doc;
		} finally {
			session.close();
		}

		doc.setCode(statusCode);
		doc.setStatus(statusMsg);

		return doc;

	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//@Consumes({ MediaType.APPLICATION_XML })
	public Response updateDoctorMetadata(
			@FormParam("doctor-metadata") DoctorMetadata data) {
			//DoctorMetadata data) {

		log.debug("Request received from UI for Doctor metadata update ");

		Integer statusCode = 200;
		String statusMsg = null;

		if (data.getFirstName().equals(null) || data.getFirstName().equals("")
				|| data.getLastName().equals(null)
				|| data.getLastName().equals("")
				|| data.getDoctorId().equals(null)
				|| data.getDoctorId().equals("")
				|| data.getMedicalCenter().equals(null)
				|| data.getMedicalCenter().equals("")) {
			log.debug("Bad request");
			statusCode = 400;
			statusMsg = "Bad Request. Mandatory fields are sent empty or null.";
		} else {
			log.debug("Request received from UI for Doctor "
					+ data.getFirstName() + ", " + data.getLastName()
					+ " with ID " + data.getDoctorId() + " metadata update ");

			try
			{
			 Integer i = Integer.parseInt(data.getDoctorId());	
			 if(i<0)
				 throw new NumberFormatException();
			}
			catch(NumberFormatException e){
				statusCode = 400;
				statusMsg = "Bad Request. Please enter a valid id";
				return Response.status(statusCode).entity(statusMsg).build();
			}
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				String hql = "UPDATE DoctorMetadata set firstName = :firstName, lastName = :lastName, medicalCenter = :medicalCenter, notes = :notes "
						+ "WHERE id = :id";
				Query query = session.createQuery(hql);
				query.setParameter("id", data.getDoctorId());
				query.setParameter("firstName", data.getFirstName());
				query.setParameter("lastName", data.getLastName());
				query.setParameter("medicalCenter", data.getMedicalCenter());
				query.setParameter("notes", data.getNotes());
				int result = query.executeUpdate();
				log.debug("Number of doctor records updated are - " + result);
				log.debug(data.getFirstName() + ", " + data.getLastName()
						+ " with ID " + data.getDoctorId()
						+ " doctor metadata updated in DB");
				transaction.commit();
			} catch (HibernateException e) {
				// transaction.rollback();
				statusCode = 500;
				statusMsg = "Internal Server Error, contact administrator.";
				log.error("Error while updating the Doctor Metadata - "
						+ e.getMessage());
				e.printStackTrace();
				return Response.status(statusCode).entity(statusMsg).build();
			} finally {
				session.close();
			}

			statusMsg = data.getFirstName() + ", " + data.getLastName()
					+ " doctor metadata updated.";

		}

		return Response.status(statusCode).entity(statusMsg).build();
	}
}
