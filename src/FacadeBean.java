import businessLogic.FacadeImplementationWS;
import businessLogic.ApplicationFacadeInterfaceWS;



public class FacadeBean{

    private static ApplicationFacadeInterfaceWS facadeInterface;

	public static ApplicationFacadeInterfaceWS getBusinessLogic(){
		if (facadeInterface == null) {
			new FacadeBean();
		}
		return facadeInterface;
	
	}
	
	private FacadeBean() {
			 try{

	               facadeInterface = new FacadeImplementationWS();

	          } catch (Exception e){

	               System.out.println("FacadeBean: error creando la logica del nogocio" + e.getMessage());

	          }	

	}
	

}