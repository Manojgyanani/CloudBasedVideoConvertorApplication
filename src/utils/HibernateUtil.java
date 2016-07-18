package utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
 
public class HibernateUtil {
 
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
//	private static final File TS_ENTERTAINMENT_FILE =
//	        new File("/WebContent/hibernate.cfg.xml");

	public static SessionFactory createSessionFactory() {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	            configuration.getProperties()).build();
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory;
	}
	
	public static void main(String arg[]){
		createSessionFactory();
	}
}