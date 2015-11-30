package com.model;

import java.io.File;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	
	static Logger log = Logger.getLogger(HibernateUtil.class.getName());
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			File file = new File("E:/Masters/Workspace/SE/Dummy/PatientMetadataInteraction/hibernate.cfg.xml");
			return new AnnotationConfiguration().configure(
					file).buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			log.error("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}
