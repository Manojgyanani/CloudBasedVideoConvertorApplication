package dao;

import org.hibernate.Query;
import org.hibernate.Session;  
import org.hibernate.Transaction;   

import dto.User;
import utils.HibernateUtil;

public class UserDao extends HibernateUtil{	
	public User queryUser(String email, String password){      
		Session session = HibernateUtil.createSessionFactory().getCurrentSession();           
		Transaction t=session.beginTransaction();  
		Query query=session.createQuery("from User where id=:id and password=:password");
		query.setString(email, password);
		User user = (User) query.uniqueResult();
		t.commit();  
		session.close();           
		return user;  		  
	}
	
	public int saveUser(User u){      
		Session session = HibernateUtil.createSessionFactory().getCurrentSession();           
		Transaction t=session.beginTransaction();  
		int i=(Integer)session.save(u);  
		t.commit();  
		session.close();           
		return i;  		  
	}
	
	public void updateUser(User u){
		Session session = HibernateUtil.createSessionFactory().getCurrentSession();           
		Transaction t=session.beginTransaction();  
		session.update(u);
		t.commit();  
		session.close();           
	}
	
	public static void deleteUser(User u){
		Session session = HibernateUtil.createSessionFactory().getCurrentSession();           
		Transaction t=session.beginTransaction();  
		session.delete(u);  
		t.commit();  
		session.close();           
	}	
}
