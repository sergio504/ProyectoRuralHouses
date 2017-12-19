import java.util.Date;

import org.hibernate.Session;

import modelo.dominio.Offer;
import modelo.dominio.RuralHouse;

public class CrearCasas {
	private void crearCasa1() {
		System.out.println("Llega al metodo1");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
        session.beginTransaction(); 
        RuralHouse casa = new RuralHouse();
        casa.setCity("Portugalete");
        casa.setDescription("Casa grande");
        session.save(casa);
        session.getTransaction().commit();
	}
	private void crearCasa2() {
		System.out.println("Llega al metodo2");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
        session.beginTransaction(); 
        RuralHouse casa2 = new RuralHouse();
        casa2.setCity("Ermua");
        casa2.setDescription("Casa pequeña");
        session.save(casa2);
        session.getTransaction().commit();
	}
	private void crearCasa3() {
		System.out.println("Llega al metodo3");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
        session.beginTransaction(); 
        RuralHouse casa3 = new RuralHouse();
        casa3.setCity("Mungia");
        casa3.setDescription("Casa bonita");
        session.save(casa3);
        session.getTransaction().commit();
	}
		private void crearCasa4() {
		System.out.println("Llega al metodo4");
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
	    session.beginTransaction(); 
        RuralHouse casa4 = new RuralHouse();
        casa4.setCity("Leioa");
        casa4.setDescription("casa fea");
        session.save(casa4);
        session.getTransaction().commit();
	}
		
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CrearCasas c = new CrearCasas();
		System.out.println("Empieza");
		c.crearCasa1();
		c.crearCasa2();
		c.crearCasa3();
		c.crearCasa4();
	}

}
