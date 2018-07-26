package co.itau.montos.DAO;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import co.itau.montos.util.Constantes;



public class BaseDAO {
	
	private static Logger log = Logger.getLogger(Constantes.LOGGER_NAME);
	
	public static Connection getConexionSeguridad() {
		return getConexion(Constantes.getProperty(Constantes.URL_BD_SEGURIDAD));
	}
	
	public static Connection getConexionPortal(){
		return getConexion(Constantes.getProperty(Constantes.URL_BD_PORTAL));
	}
	
	public static Connection getConexionNegocio(){
		return getConexion(Constantes.getProperty(Constantes.URL_BD_NEGOCIO));
	}
	
	
	private static Connection getConexion(String jndiName) {
		Connection conn = null;
		log.debug("===== Inicia getConexionSeguridad =====");
		try {
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup(jndiName);
			conn = ds.getConnection();
		} catch (Exception e) {
			log.error("Error abriendo conexion");
		}
		log.debug("Retorno (Connection) getConexionSeguridad: "
				+ conn.toString());
		log.debug("===== Termina getConexionSeguridad =====");
		return conn;
	}
}
