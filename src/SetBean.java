import java.util.Date;
import java.util.HashSet;
import java.util.List;
import businessLogic.ApplicationFacadeInterfaceWS;

import modelo.dominio.Offer;
import modelo.dominio.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;

public class SetBean {
	private ApplicationFacadeInterfaceWS facadeBL;
	private RuralHouse casa;
	private HashSet<RuralHouse> casas;
	private Date fechaInicio;
	private Date fechaFin;
	private float precio;
	private String mensaje;
	
	
	public SetBean() {
		facadeBL = FacadeBean.getBusinessLogic();
	}
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	public RuralHouse getCasa() {
		return casa;
	}
	public void setCasa(RuralHouse casa) {
		this.casa = casa;
	}
	public HashSet<RuralHouse> getCasas() {
		casas = facadeBL.getAllRuralHouses();
		return casas;
	}
	public void setCasas(HashSet<RuralHouse> casas) {
		this.casas = casas;
	}
	
	public void crearOferta() {
		if (fechaInicio == null || fechaFin == null) {
			mensaje = "Tienes que seleccionar fecha de inicio y fecha de fin";
		}
		else if (precio == 0) {
			mensaje ="El precio tiene que ser mayor que 0";
		}
		else {
			try {
				Offer oferta = facadeBL.createOffer(casa, fechaInicio, fechaFin, precio);
				
				if (oferta == null) {
					mensaje="Ya hay una oferta que se solapa con esas fechas";
				}
				else {
					mensaje="La oferta se ha creado correctamente";
				}
				
			}
			catch (OverlappingOfferExists e) {
				mensaje="Ya hay una reserva en esas fechas";
			}
			catch(BadDates e) {
				mensaje="Las fechas no son correctas";
			}
		}
		
	}
	
	public String cancelar() {
		casa = null;
		casas = null;
		fechaInicio = null;
		fechaFin = null;
		precio = 0;
		mensaje = "";
		return "Cancelar";
	}
	
}
