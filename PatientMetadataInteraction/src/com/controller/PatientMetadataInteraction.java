package com.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.model.HibernateUtil;
import com.patient.PatientMetadata;

import org.apache.log4j.Logger;

@Path("metadata")
public class PatientMetadataInteraction {

	Logger log = Logger.getLogger(PatientMetadataInteraction.class.getName());

	@POST
	@Path("/insert")
	//@Consumes({ MediaType.APPLICATION_XML })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response insertPatientMetadata(@FormParam("patientMetadata") PatientMetadata data) {
		log.info("Inputs - " + data.getName() + data.getGender()
				+ data.getAge() + data.getDob() + data.getWeight()
				+ data.getHeight());

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		com.model.PatientMetadata patientMetadata = new com.model.PatientMetadata();
		patientMetadata.setName(data.getName());
		patientMetadata.setGender(data.getGender());
		patientMetadata.setDob(new java.sql.Date(data.getDob().getTime()));
		patientMetadata.setAge(data.getAge());
		patientMetadata.setWeight(data.getWeight());
		patientMetadata.setHeight(data.getHeight());

		// Save the patientMetadata in database
		session.save(patientMetadata);

		// Commit the transaction
		session.getTransaction().commit();
		HibernateUtil.shutdown();

		String output = "Patient metadata created.";
		// Simply return the parameter passed as message
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/select")
	@Produces({ MediaType.APPLICATION_XML })
	public PatientMetadata selectPatientMetadata(
			@QueryParam(value = "name") String name) {
		PatientMetadata data = new PatientMetadata();

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hql = "FROM PatientMetadata WHERE name= :name";
			Query query = session.createQuery(hql);
			query.setParameter("name", name);
			List<com.model.PatientMetadata> results = query.list();
			if (results.iterator().hasNext()) {
				com.model.PatientMetadata result = (com.model.PatientMetadata) results
						.iterator().next();
				log.info("Result");
				data.setName(result.getName());
				data.setGender(result.getGender());
				data.setDob(result.getDob());
				data.setAge(result.getAge());
				data.setWeight(result.getWeight());
				data.setHeight(result.getHeight());
			} else {
				log.info("No Result");
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return data;
	}

	@PUT
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_XML })
	public Response updatePatientMetadata(PatientMetadata data) {
		System.out.println("Inputs - " + data.getName() + data.getGender()
				+ data.getAge() + data.getDob() + data.getWeight()
				+ data.getHeight());

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hql = "UPDATE PatientMetadata set weight = :weight "
					+ "WHERE name = :name";
			Query query = session.createQuery(hql);
			query.setParameter("weight", data.getWeight());
			query.setParameter("name", data.getName());
			int result = query.executeUpdate();
			log.info("Rows affected: " + result);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		String output = "Patient metadata updated.";
		// Simply return the parameter passed as message
		return Response.status(200).entity(output).build();
	}

	@DELETE
	@Path("/delete")
	public Response deletePatientMetadata(
			@QueryParam(value = "name") String name) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hql = "DELETE FROM PatientMetadata " + "WHERE name = :name";
			Query query = session.createQuery(hql);
			query.setParameter("name", name);
			int result = query.executeUpdate();
			log.info("Rows affected: " + result);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		String output = "Patient metadata deleted.";
		// Simply return the parameter passed as message
		return Response.status(200).entity(output).build();
	}

}