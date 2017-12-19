package businessLogic;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import dataAccess.DataAccess;

//import domain.Booking;
import modelo.dominio.Offer;
import modelo.dominio.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;

//Service Implementation
@WebService(endpointInterface = "businessLogic.ApplicationFacadeInterfaceWS")
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
		System.out.println(">> FacadeImplementationWS: createOffer=> ruralHouse= "+ruralHouse+" firstDay= "+firstDay+" lastDay="+lastDay+" price="+price);
		
		DataAccess dbManager=new DataAccess();
		Offer o=null;
		if (firstDay.compareTo(lastDay)>=0) { dbManager.close(); throw new BadDates();}
		
		boolean b = dbManager.existsOverlappingOffer(ruralHouse,firstDay,lastDay); 
		if (!b) o=dbManager.createOffer(ruralHouse,firstDay,lastDay,price);		

		dbManager.close();
		System.out.println("<< FacadeImplementationWS: createOffer=> O= "+o);
		return o;
	}
 

	
		
	public HashSet<RuralHouse> getAllRuralHouses()  {
		System.out.println(">> FacadeImplementationWS: getAllRuralHouses");

		DataAccess dbManager=new DataAccess();

		HashSet<RuralHouse>  ruralHouses=dbManager.getAllRuralHouses();
		//dbManager.close();
		System.out.println("<< FacadeImplementationWS:: getAllRuralHouses");

		return ruralHouses;

	}
	
	/**
	 * This method obtains the  offers of a ruralHouse in the provided dates 
	 * 
	 * @param ruralHouse, the ruralHouse to inspect 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there is no overlapping offer
	 */

	@WebMethod public Vector<Offer> getOffers( RuralHouse rh, Date firstDay,  Date lastDay) {
		System.out.println("Llega al facade");
		DataAccess dbManager=new DataAccess();
		HashSet<Offer>  offers=new HashSet<Offer>();
		offers=dbManager.getOffers(rh,firstDay,lastDay);
		Vector<Offer> a = new Vector<Offer>();
		for (Offer s : offers) {
		   a.addElement(s);
		}
		return a;
	}	
		
		
	
	public void close() {
		DataAccess dbManager=new DataAccess();

		dbManager.close();

	}
	
	public Offer returnOffer(String x) {
		Offer tmp = null;
		DataAccess dbManager=new DataAccess();
		tmp = dbManager.returnOffer(x);
		return tmp;
	}


	@Override
	public void initializeBD() {
		// TODO Auto-generated method stub
		
	}
	
	public void reservarOferta(Offer o, String nombre) {
		DataAccess dbManager=new DataAccess();
		dbManager.reservarOferta(o, nombre);
		}



	}

