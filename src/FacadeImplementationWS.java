

import java.io.File;


import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.hibernate.Session;


//import domain.Booking;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;
import modelo.dominio.Offer;
import modelo.dominio.RuralHouse;

//Service Implementation
@WebService(endpointInterface = "ApplicationFacadeInterfaceWS")
public class FacadeImplementationWS  implements ApplicationFacadeInterfaceWS {

	/**
	 * 
	 */

	public FacadeImplementationWS()  {
		

	}
	

	/**
	 * This method creates an offer with a house number, first day, last day and price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return the created offer, or null, or an exception
	 */
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay,
			float price) throws OverlappingOfferExists, BadDates {
		//System.out.println(">> FacadeImplementationWS: createOffer=> ruralHouse= "+ruralHouse+" firstDay= "+firstDay+" lastDay="+lastDay+" price="+price);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
        session.beginTransaction(); 
        RuralHouse a = new RuralHouse();
		RuralHouse rh = (RuralHouse) session.get(a.getClass(), ruralHouse.getHouseNumber());
		System.out.println("---------------------------------------------------------------"+rh.getCity()+"-----------------------------------------------------");
		Offer o=null;
		rh.overlapsWith(firstDay, lastDay);
		if (firstDay.compareTo(lastDay)>=0) { 
			session.close(); 
			throw new BadDates();}
		
		boolean b = existsOverlappingOffer(rh,firstDay,lastDay); 
		if (!b) {
			o = new Offer();
			o.setFirstDay(firstDay);
			o.setLastDay(lastDay);
			o.setPrice(price);
			o.setRuralHouse(rh);
			session.save(o);
			session.getTransaction().commit(); 
		}		

		session.close();
		//System.out.println("<< FacadeImplementationWS: createOffer=> O= "+o);
		return o;
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
	
	/**
	 * This method obtains the  offers of a ruralHouse in the provided dates 
	 * 
	 * @param ruralHouse, the ruralHouse to inspect 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there is no overlapping offer
	 */

	@WebMethod public HashSet<Offer> getOffers( RuralHouse rh, Date firstDay,  Date lastDay) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
        session.beginTransaction();
        List result = session.createQuery("from Offer").list();
        HashSet<Offer> result2 = new HashSet();
        for(int indice = 0;indice<result.size();indice++)
        {
            result2.add((Offer)result.get(indice));
        }
        
        session.close();
		return result2;
	}	
		
		


	 public void initializeBD(){
		
//		DataAccess dbManager=new DataAccess();
//		dbManager.initializeDB();
//		dbManager.close();

	}
	 
	 public boolean existsOverlappingOffer(RuralHouse rhn,Date firstDay, Date lastDay) throws  OverlappingOfferExists{
			try{
				if (rhn.overlapsWith(firstDay,lastDay)!=null) return true;
			} catch (Exception e){
					System.out.println("Error: "+e.toString());
					return true;
			}
		return false;
		}
}


