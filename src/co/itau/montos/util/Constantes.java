package co.itau.montos.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import properties.AcquiringCodesProperties;

public class Constantes {
	
	public static String URL_BD_SEGURIDAD = "seguridad.db.jndiname";
	public static String URL_BD_PORTAL = "portal.db.jndiname";
	public static String URL_BD_NEGOCIO = "negocio.db.jndiname";
	public static String URL_LOG_CONFIG= "log.file.path";
	public static String RESP_ACTUALIZACION_EXITO = "EXITO";
	public static String RESP_ACTUALIZACION_ERROR = "ERROR";
	public static String EVENTO_ACTUALIZACION_MONTOS = "portal.montos.actualizacio.montos.tx";
	
	private static transient Properties mainProperties;
	

	
	public static String LOGGER_NAME = "montosLogger";
	static{
		if (mainProperties == null)
		mainProperties = loadProperties();
		PropertyConfigurator.configure(getProperty(URL_LOG_CONFIG));
	}
	
	public static String getProperty(String propertyName){
		return mainProperties.getProperty(propertyName);
	}
	
	private static Properties loadProperties(){
		String archivoExterno = AcquiringCodesProperties.appProperties.getProperty("montos.props");
		System.out.println("Cargando las propiedades: " + archivoExterno);
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(archivoExterno));
			return properties;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}	
}
