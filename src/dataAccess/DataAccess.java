package dataAccess;


import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import modelo.HibernateUtil;
//import domain.Booking;
import modelo.dominio.Offer;
import modelo.dominio.RuralHouse;
import exceptions.OverlappingOfferExists;

public class DataAccess  {

	public DataAccess()  {
}
	
	
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) {
		System.out.println(">> DataAccess: createOffer=> ruralHouse= "+ruralHouse+" firstDay= "+firstDay+" lastDay="+lastDay+" price="+price);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
		session.beginTransaction();
		try {	
			List<RuralHouse> res = session.createQuery("from RuralHouse where HOUSENUMBER = '"+ruralHouse.getHouseNumber()+"'").list(); 
			RuralHouse rh = res.get(0);	
			Offer o = rh.createOffer(firstDay, lastDay, price);
			session.save(o);
			System.out.println(rh.offers);
			session.getTransaction().commit();
			return o;
	
		}
		catch (Exception e){
			System.out.println("Offer not created: "+e.toString());
			return null;
		}
		}
	
	
	public HashSet<RuralHouse> getAllRuralHouses()  {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
        session.beginTransaction(); 
        List result = session.createQuery("from RuralHouse").list();
        HashSet<RuralHouse> result2 = new HashSet();
        
        for(int indice = 0;indice<result.size();indice++)
        {
            result2.add((RuralHouse)result.get(indice));
        }
        session.close();

		return result2;

	}

	
public HashSet<Offer> getOffers( RuralHouse rh, Date firstDay,  Date lastDay) {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
    session.beginTransaction();
    List<RuralHouse> res = session.createQuery("from RuralHouse where HOUSENUMBER ='"+rh.getHouseNumber()+"'").list(); 
    HashSet<Offer> ahora = new HashSet<Offer>();
    for (Offer o: res.get(0).getOffers(firstDay, lastDay)) {
    	ahora.add(o);
    }
    return ahora;


}	
	

	public boolean existsOverlappingOffer(RuralHouse rh,Date firstDay, Date lastDay) throws  OverlappingOfferExists{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
	    session.beginTransaction();
		try{
			List<RuralHouse> res = session.createQuery("from RuralHouse where HOUSENUMBER ='"+rh.getHouseNumber()+"'").list();
			if (res.get(0).overlapsWith(firstDay,lastDay)!=null) {System.out.println("Devuelve true"); return true;}
		} catch (Exception e){
				System.out.println("Error: "+e.toString());
				return true;
		}
	return false;
	}


	public void close(){
//		session.close();
//		System.out.println("DataBase closed");
	}
	
	public Offer returnOffer(String x) {
		Offer tmp = null;
		try {
		Long id = Long.parseLong(x);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
	    session.beginTransaction();
		List<Offer> res = session.createQuery("from Offer where offerNumber ='"+id+"'").list();
		if (res.size() ==1) {
		tmp = res.get(0);
		return tmp;
		}
		} catch (Exception e) {
			return null;
		}
		return null;
	}


	public void reservarOferta(Offer o, String nombre) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
	    session.beginTransaction();
		o.setReservadaPor(nombre);
		session.update(o);
        session.getTransaction().commit();
	}
}

