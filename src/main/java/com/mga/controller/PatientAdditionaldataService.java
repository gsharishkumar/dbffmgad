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

import com.mga.beans.PatientMiscData;
import com.mga.beans.PatientsMiscData;
import com.mga.model.HibernateUtil;

@Path("miscdata")
public class PatientAdditionaldataService {
	Logger log = Logger
			.getLogger(PatientAdditionaldataService.class.getName());

	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response insertPatientMiscdata(
			@FormParam("patient-miscdata") PatientMiscData uiPatientMiscdata) {
		//System.out.println("inside insert data");
		log.debug("Request received from UI for Patient "
				+ uiPatientMiscdata.getPatientId() + ", " + " MiscData Create ");

		Integer statusCode = 200;
		String statusMsg = null;

		if (uiPatientMiscdata.getPainNrs() == null
				|| uiPatientMiscdata.getPainNrs().equals("")
				|| uiPatientMiscdata.getPainVas()== null
				|| uiPatientMiscdata.getPainVas().equals("")
				|| uiPatientMiscdata.getRelatedDate()== null) {
			log.debug("Bad request");
			statusCode = 400;
			statusMsg = "ERROR CONDITION";
			return Response.status(statusCode).entity(statusMsg).build();
		} else {
			Session session = null;
			Transaction transaction = null;
			try {
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				if (uiPatientMiscdata.getId() == 0) {
					com.mga.model.PatientMiscData dbPatientMiscdata = new com.mga.model.PatientMiscData();
					dbPatientMiscdata.setPatientId(uiPatientMiscdata
							.getPatientId());
					dbPatientMiscdata.setPainLevelNRS(uiPatientMiscdata
							.getPainNrs());
					dbPatientMiscdata.setPainLevelVAS(uiPatientMiscdata
							.getPainVas());
					dbPatientMiscdata.setContentLevel(uiPatientMiscdata
							.getContentLevel());
					dbPatientMiscdata.setId(uiPatientMiscdata.getId());
					dbPatientMiscdata.setDate(new java.sql.Date(MGAUtil
							.birthDateStringToDate(
									uiPatientMiscdata.getRelatedDate(),
									"Misc").getTime()));
					session.save(dbPatientMiscdata);
					log.debug(uiPatientMiscdata.getPatientId()
							+ ", miscdata created in DB ");
					statusMsg = "Miscellaneous data created successfully";
				} else {
					String hql = "UPDATE PatientMiscData set painLevelNRS = :painnrs, painLevelVAS = :painvas, date = :date, contentLevel = :clevel "
							+ "WHERE id = :id";
					Query query = session.createQuery(hql);
					query.setParameter("painnrs",
							uiPatientMiscdata.getPainNrs());
					query.setParameter("painvas",
							uiPatientMiscdata.getPainVas());

					query.setParameter(
							"date",
							new java.sql.Date(MGAUtil.birthDateStringToDate(
									uiPatientMiscdata.getRelatedDate(),
									"Misc").getTime()));

					query.setParameter("clevel",
							uiPatientMiscdata.getContentLevel());
					query.setParameter("id", uiPatientMiscdata.getId());
					int result = query.executeUpdate();
					log.debug(uiPatientMiscdata.getPatientId() + ", "
							+ " with ID " + uiPatientMiscdata.getId()
							+ " miscdata updated in DB");
					statusMsg = "Miscellaneous data updated successfully";
				}
				transaction.commit();
				HibernateUtil.shutdown();

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

		}

		return Response.status(statusCode).entity(statusMsg).build();
	}

	@GET
	@Path("/select")
	@Produces({ MediaType.APPLICATION_XML })
	public PatientsMiscData getPatientMiscData(
			@QueryParam(value = "id") Integer id) {

		log.debug("Request to select the misc details of the patient " + id
				+ " recieved from UI");

		Integer statusCode = 200;
		String statusMsg = null;

		List<PatientMiscData> miscDataList = new ArrayList<PatientMiscData>();
		PatientsMiscData patients = new PatientsMiscData();
		if (id == 0 || id == null) {
			log.debug("Bad request");
			statusCode = 400;
			statusMsg = "Bad Request. Mandatory fields are sent empty or null.";
		} else {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				int count = 0;
				transaction = session.beginTransaction();
				String hql = " select id, painLevelNRS, painLevelVAS, date, contentLevel FROM PatientMiscData  WHERE patient_id= :pid";
				Query query = session.createQuery(hql);
				query.setParameter("pid", id);
				@SuppressWarnings("unchecked")
				List<com.mga.model.PatientMiscData> results = query.list();
				count = results.size();
				for (Iterator it = results.iterator(); it.hasNext();) {
					com.mga.beans.PatientMiscData miscdata = new com.mga.beans.PatientMiscData();
					Object[] result = (Object[]) it.next();
					Integer priKey = (Integer) result[0];
					Integer painnrs = (Integer) result[1];
					Integer painvas = (Integer) result[2];
					Date date = (Date) result[3];
					Integer contentlevel = (Integer) result[4];
					miscdata.setId(priKey);
					miscdata.setPainNrs(painnrs);
					miscdata.setPainVas(painvas);
					miscdata.setRelatedDate(MGAUtil.birthDateDateToString(date));
					miscdata.setContentLevel(contentlevel);
					miscDataList.add(miscdata);
				}
				patients.setPatient(miscDataList);
				patients.setCount(count);
				transaction.commit();
				patients.setCode(statusCode);
				statusMsg = "Success";
				patients.setStatus(statusMsg);
			} catch (HibernateException e) {
				statusCode = 500;
				statusMsg = "Internal Server Error, contact administrator.";
				log.error("Error while selecting the Patient Miscdata - "
						+ e.getMessage());
				e.printStackTrace();
				patients.setCode(statusCode);
				patients.setStatus(statusMsg);
				e.printStackTrace();
				return patients;
			} finally {
				session.close();
			}
		}

		return patients;

	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updatePatientMiscdata(
			@FormParam(value = "patient-database-id") Integer id,
			@FormParam(value = "patient-miscdata") PatientMiscData uiPatientMiscdata) {

		log.debug("Request received from UI for Patient "
				+ uiPatientMiscdata.getPatientId() + ", " + " with ID " + id
				+ " miscdata update ");

		Integer statusCode = 200;
		String statusMsg = null;

		if (id == 0 || id == null
				|| uiPatientMiscdata.getPainNrs().equals(null)
				|| uiPatientMiscdata.getPainNrs().equals("")
				|| uiPatientMiscdata.getPainVas().equals(null)
				|| uiPatientMiscdata.getPainVas().equals("")
				|| uiPatientMiscdata.getRelatedDate().equals(null)
				|| uiPatientMiscdata.getRelatedDate().equals("")
				|| uiPatientMiscdata.getContentLevel().equals(null)
				|| uiPatientMiscdata.getContentLevel().equals("")) {
			log.debug("Bad request");
			statusCode = 400;
			statusMsg = "Bad Request. Mandatory fields are sent empty or null.";
		} else {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				String hql = "UPDATE PatientMiscData set painLevelNRS = :painnrs, painLevelVAS = :painvas, date = :date, contentLevel = :clevel "
						+ "WHERE id = :id";
				Query query = session.createQuery(hql);
				query.setParameter("painnrs", uiPatientMiscdata.getPainNrs());
				query.setParameter("painvas", uiPatientMiscdata.getPainVas());

				query.setParameter(
						"date",
						new java.sql.Date(MGAUtil.birthDateStringToDate(
								uiPatientMiscdata.getRelatedDate(), "Update")
								.getTime()));

				query.setParameter("clevel",
						uiPatientMiscdata.getContentLevel());
				query.setParameter("id", id);
				int result = query.executeUpdate();
				log.debug(uiPatientMiscdata.getPatientId() + ", " + " with ID "
						+ id + " miscdata updated in DB");
				transaction.commit();
				statusCode = 200;

				if (result == 0) {
					statusMsg = "Nothing updated ";
				} else {
					statusMsg = uiPatientMiscdata.getPatientId() + ", "
							+ " patient miscdata updated.";
				}

			} catch (HibernateException e) {
				transaction.rollback();
				statusCode = 500;
				statusMsg = "Internal Server Error, contact administrator.";
				log.error("Error while updating the Patient Miscdata - "
						+ e.getMessage());
				e.printStackTrace();
				return Response.status(statusCode).entity(statusMsg).build();
			} finally {
				session.close();
			}
		}
		return Response.status(statusCode).entity(statusMsg).build();
	}

	@GET
	@Path("/delete")
	public Response deletePatientMiscdata(@QueryParam(value = "id") Integer id) {

		log.debug("Request received from UI for Patient " + " with misc ID " + id
				+ " miscdata delete");

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
				String hql = "DELETE FROM PatientMiscData WHERE id = :id";
				Query query = session.createQuery(hql);
				query.setParameter("id", id);
				int result = query.executeUpdate();
				log.debug("Patient with misc ID " + id
						+ " miscdata deleted from DB");
				transaction.commit();
			} catch (HibernateException e) {
				transaction.rollback();
				statusCode = 500;
				statusMsg = "Internal Server Error, contact administrator.";
				log.error("Error while deleting the Patient Metadata - "
						+ e.getMessage());
				e.printStackTrace();
				return Response.status(statusCode).entity(statusMsg).build();
			} finally {
				session.close();
			}

			statusMsg = "Requested patient miscdata deleted succesfully.";
		}

		return Response.status(statusCode).entity(statusMsg).build();
	}

}
