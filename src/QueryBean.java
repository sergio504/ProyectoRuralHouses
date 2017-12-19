import java.util.Calendar;
import businessLogic.ApplicationFacadeInterfaceWS;
import businessLogic.FacadeImplementationWS;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import modelo.dominio.Offer;
import modelo.dominio.RuralHouse;

public class QueryBean {
	private ApplicationFacadeInterfaceWS facadeBL;
	private HashSet<RuralHouse> casas;
	private RuralHouse casa;
	private Date fechaInicio;
	private int numNoches;
	private Vector<Offer> listaOfertas;
	private String mensaje = "";
	private String nombre;
	private String ofertaDeseada;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOfertaDeseada() {
		return ofertaDeseada;
	}

	public void setOfertaDeseada(String ofertaDeseada) {
		this.ofertaDeseada = ofertaDeseada;
	}

	
	
	public QueryBean() {
		listaOfertas= new Vector<Offer>();
		facadeBL = FacadeBean.getBusinessLogic();
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public int getNumNoches() {
		return numNoches;
	}

	public void setNumNoches(int numNoches) {
		this.numNoches = numNoches;
	}

	public RuralHouse getCasa() {
		return casa;
	}
	public HashSet<RuralHouse> getCasas(){
		casas=facadeBL.getAllRuralHouses();
		return casas;
	}
	public void setCasa(RuralHouse casa) {
		this.casa = casa;
	}


	public void setCasas(HashSet<RuralHouse> casas) {
		this.casas = casas;
	}
	
	public void obtenerOfertas() {
		System.out.println("AQUI LLEGA");
		if (fechaInicio == null) {
			mensaje = "Tienes que elegir una fecha de inicio";
		}
		else if (numNoches == 0) {
			mensaje = "El numero de noches tiene que ser mayor que 0";
		}
		else {
			Calendar calendar = Calendar.getInstance();
	    	calendar.setTime(fechaInicio);
	        calendar.add(Calendar.DAY_OF_YEAR, numNoches); 
	    	Date fechaFin= calendar.getTime();
			listaOfertas = facadeBL.getOffers(casa, fechaInicio, fechaFin);
		}
		
	}


	public Vector<Offer> getListaOfertas() {
		return listaOfertas;
	}

	public void setListaOfertas(Vector<Offer> listaOfertas) {
		this.listaOfertas = listaOfertas;
	}
	public String cancelar() {
		casa = null;
		casas = null;
		fechaInicio = null;
		listaOfertas = null;
		numNoches = 0;
		mensaje = "";
		return "Cancelar";
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public void alquilar() {
		if (nombre!=null && nombre!="nadie") {
		Offer tmp = comprobarSiCoincide(ofertaDeseada); 
		if (tmp == null) {
			this.mensaje = "La oferta no existe o no es seleccionable";
		} else {
			if (tmp.getReservadaPor().equals("nadie")) {
				facadeBL.reservarOferta(tmp, nombre);
				mensaje = "Oferta alquilada!";
				borrarOferta(tmp);
			}else {
				mensaje = "Esta oferta esta reservada recientemente";
			}
		}
		}
	}
	
	public Offer comprobarSiCoincide(String x) {
		Offer tmp = facadeBL.returnOffer(x);
		if (tmp == null) {
			return null;
		} else {
			for (Offer o: listaOfertas) {
				if (o.getOfferNumber() == tmp.getOfferNumber()) {
					return tmp;
				}
			}
		}
		return null;
	}
	
	private void borrarOferta(Offer x) {
			int ind = 0;
			boolean encontrada = false;
			while(ind < listaOfertas.size() && !encontrada) {
				if  (listaOfertas.get(ind).getOfferNumber()== x.getOfferNumber()){
					System.out.println("Ha encontrado la casa");
					encontrada = true;
				} else {
					ind++;
				}
			}
			if (encontrada) listaOfertas.remove(ind);
	}
}