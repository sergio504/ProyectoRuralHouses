package modelo.dominio;


import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;

import modelo.HibernateUtil;



public class RuralHouse implements Serializable {


	private Long houseNumber;
	private String description;
	private String city; 
	public List<Offer> offers;

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public RuralHouse() {
		super();
	}

	public RuralHouse(String description, String city) {
		
		this.description = description;
		this.city = city;
	}

	public Long getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Long houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}

	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city=city;
	}

	
		
	/**
	 * This method creates an offer with a house number, first day, last day and price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */
	public Offer createOffer(Date firstDay, Date lastDay, float price)  {
        System.out.println("LLAMADA RuralHouse createOffer, offerNumber="+" firstDay="+firstDay+" lastDay="+lastDay+" price="+price);
        Offer off=new Offer(firstDay,lastDay,price,this);
        offers.add(off);
        return off;
	}

	
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + houseNumber.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return this.houseNumber + ": " + this.city;
	}

	@Override
	public boolean equals(Object obj) {
		RuralHouse other = (RuralHouse) obj;
		if (this == obj)
		  return true;
		if (obj == null)
		  return false;
		if (getClass() != obj.getClass())
		  return false;
//		if (houseNumber != other.houseNumber) // NO COMPARAR ASÍ ya que houseNumber NO ES "int" sino objeto de "java.lang.Integer"
		if (!houseNumber.equals(other.houseNumber))
		  return false;
   	    return true;
	}
	
	/**
	 * This method obtains available offers for a concrete house in a certain period 
	 * 
	 * @param houseNumber, the house number where the offers must be obtained 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return a vector of offers(Offer class)  available  in this period
	 */
	public Vector<Offer> getOffers( Date firstDay,  Date lastDay) {
		System.out.println("Esta buscando en la casa, que tiene ");
	    System.out.println(offers.size());
		Vector<Offer> availableOffers=new Vector<Offer>();
		for(Offer o : offers){
			if ((o.getFirstDay().compareTo(firstDay)>=0) && (o.getLastDay().compareTo(lastDay)<=0) && o.getReservadaPor().equals("nadie")) {
				 availableOffers.add(o);
		}
		}

		return availableOffers;

		
	}
	
	
	/**
	 * This method obtains the first offer that overlaps with the provided dates
	 * 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there is no overlapping offer
	 */

	public Offer overlapsWith( Date firstDay,  Date lastDay) {
		for(int indice = 0;indice<offers.size();indice++){	
			if ( (offers.get(indice).getFirstDay().compareTo(lastDay)<0) && (offers.get(indice).getLastDay().compareTo(firstDay)>0))
				return offers.get(indice);
		}
		return null;
		
	}

}
