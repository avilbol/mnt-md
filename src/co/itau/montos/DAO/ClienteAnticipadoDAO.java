package co.itau.montos.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import co.itau.montos.model.MontosMigrados;
import co.itau.montos.util.Constantes;
import tecnoevolucion.encapsulado.DatosLogUsuario;

public class ClienteAnticipadoDAO extends BaseDAO {

	private static Logger log = Logger.getLogger(Constantes.LOGGER_NAME);

	public static List<MontosMigrados> getMontos(String identificacion) {

		String query = "SELECT TRANSACCION, MONTO, PORTAFOLIO, NIT, CODIGO FROM dbo.montos_migrados where ACTIVO = 'S' and NIT = ?";

		List<MontosMigrados> listaMontos = new LinkedList<MontosMigrados>();
		Connection connection = null;

		try {

			connection = getConexionSeguridad();
			PreparedStatement stmt = null;
			stmt = connection.prepareStatement(query);
			stmt.setString(1, identificacion);
			ResultSet rs = null;
			rs = stmt.executeQuery();

			while (rs.next()) {
				MontosMigrados montoTransaccion = new MontosMigrados();
				montoTransaccion.setTransaccion(rs.getString("TRANSACCION"));
				montoTransaccion.setMonto(rs.getDouble("MONTO"));
				montoTransaccion.setNit(rs.getString("NIT"));
				montoTransaccion.setPortafolio(rs.getString("PORTAFOLIO"));
				montoTransaccion.setCodigo(rs.getString("CODIGO"));
				listaMontos.add(montoTransaccion);

			}
			rs.close();
			stmt.close();
			connection.close();

		} catch (Exception exception) {
			log.error("Error en la sentencia getMontos ", exception);
			exception.printStackTrace();
			try {
				if (!connection.isClosed())
					connection.close();
			} catch (Exception exception1) {
				log.error("Error Cerrando la bd 1");
				exception1.printStackTrace();
			}
		} finally {
			try {
				if (!connection.isClosed())
					connection.close();
			} catch (Exception exception3) {
				log.error("Error Cerrando la bd");
				exception3.printStackTrace();
			}
		}

		return listaMontos;

	}

	public static final void actualizaMontosSeguridad(DatosLogUsuario dlu, String montoPortal, String dirIp)
			throws SQLException {
		String codStatus = "000";
		String updatePE = "UPDATE parametros_empresa set monto_credito = ?, monto_debito = ?,"
				+ "disponible_credito = ?, disponible_debito= ? Where id_cliente = ?";

		Connection conSeg = null;
		PreparedStatement pss = null;
		try {
			conSeg = getConexionSeguridad();
			pss = conSeg.prepareStatement(updatePE);
			pss.setDouble(1, Double.valueOf(montoPortal));
			pss.setDouble(2, Double.valueOf(montoPortal));
			pss.setDouble(3, Double.valueOf(montoPortal));
			pss.setDouble(4, Double.valueOf(montoPortal));
			pss.setString(5, dlu.numero_id_cli);
			pss.executeUpdate();
			pss.close();
			conSeg.close();

		} finally {
			try {
				if (pss != null)
					pss.close();
				if (!conSeg.isClosed())
					conSeg.close();
			} catch (Exception exception3) {
				log.error("Error Cerrando la bd");
				exception3.printStackTrace();
			}
		}

	}

	public static final void actualizaMontosNegocio(DatosLogUsuario dlu, String montoJPAT, String dirIp)
			throws SQLException {
		String codStatus = "000";
		String updateBPC = "UPDATE BP_CUSTOMER SET CUSMAXCREDITAMOUNT = ? , CUSMAXDEBITAMOUNT = ? "
				+ "WHERE CUSIDNUMBER = ? ";

		Connection conNeg = null;
		PreparedStatement psn = null;
		try {
			conNeg = getConexionNegocio();
			psn = conNeg.prepareStatement(updateBPC);
			psn.setDouble(1, Double.valueOf(montoJPAT));
			psn.setDouble(2, Double.valueOf(montoJPAT));
			psn.setString(3, dlu.numero_id_cli);
			psn.executeUpdate();
			psn.close();
			conNeg.close();

		} finally {
			try {

				if (psn != null)
					psn.close();
				if (!conNeg.isClosed())
					conNeg.close();
			} catch (Exception exception3) {
				log.error("Error Cerrando la bd");
				exception3.printStackTrace();
			}
		}

	}

	public static final void actualizaMontosPortal(DatosLogUsuario dlu, String montoPSE, int trxPSE, String dirIp)
			throws SQLException {
		String codStatus = "000";
		String updatePSE = "UPDATE BC_WEB_LIMIT_PAYMENTS set daily_max_payments = ?, max_amount = ? "
				+ "where client_id = ?";

		Connection conPor = null;
		PreparedStatement psp = null;
		try {
			conPor = getConexionPortal();
			psp = conPor.prepareStatement(updatePSE);
			psp.setInt(1, trxPSE);
			psp.setDouble(2, Double.valueOf(montoPSE));
			psp.setString(3, dlu.numero_id_cli);
			psp.executeUpdate();
			psp.close();
			conPor.close();

		} finally {
			try {
				if (psp != null)
					psp.close();
				if (!conPor.isClosed())
					conPor.close();
			} catch (Exception exception3) {
				log.error("Error Cerrando la bd");
				exception3.printStackTrace();
			}
		}

	}

	public static HashMap<String, String> isMontosConfig(String identificacion) {

		boolean hasTrx = false;
		boolean hasJPAT = false;
		boolean hasPSE = false;
		int pseAz = 0;
		int jpatAz = 0;
		int trxAz = 0;
		HashMap<String, String> montosConfigurados = new HashMap<String, String>();
		log.debug("Buscando informacion de montos para el nit " + identificacion);
		List<MontosMigrados> listaMontos = getMontos(identificacion);

		for (int i = 0; i < listaMontos.size(); i++) {
			log.debug("Codigo monto " + listaMontos.get(i).getCodigo().toString());
			if (listaMontos.get(i).getCodigo().toString().equalsIgnoreCase("PAGO_COMPRAS_INTERNET_PSE")) {
				pseAz = pseAz + 1;
				hasPSE = true;

			} else if (listaMontos.get(i).getCodigo().toString().equalsIgnoreCase("OPERACION_ACH")
					|| listaMontos.get(i).getCodigo().toString().equalsIgnoreCase("PAGO_NOMINA")) {
				jpatAz = jpatAz + 1;
				hasJPAT = true;
			} else {
				trxAz = trxAz + 1;
				hasTrx = true;
			}
		}
		log.debug("hasTrx " + hasTrx + " hasJPAT " + hasJPAT + " hasPSE " + hasPSE);
		boolean isConfig = false;

		String queryP = "select max_amount as monto, daily_max_payments as transacciones from BC_WEB_LIMIT_PAYMENTS where client_id = ?";
		String queryS = "select monto_debito as monto from parametros_empresa where id_cliente = ?";
		String queryN = "select CUSMAXDEBITAMOUNT as monto from BP_CUSTOMER where CUSIDNUMBER = ?";

		Connection conS = null;
		Connection conP = null;
		Connection conN = null;

		PreparedStatement stmts = null;
		PreparedStatement stmtp = null;
		PreparedStatement stmtn = null;

		ResultSet rss = null;
		ResultSet rsp = null;
		ResultSet rsn = null;

		try {
			if (hasTrx) {
				conS = getConexionSeguridad();
				stmts = conS.prepareStatement(queryS);
				stmts.setString(1, identificacion);
				rss = stmts.executeQuery();
				if (rss.next()) { // montoTRX
					String monto = rss.getBigDecimal(1).toString();
					montosConfigurados.put("montoTRX", monto);
					log.debug("Valor de montoTRX " + monto);
				}
				rss.close();
				stmts.close();
				conS.close();
			}
			if (hasPSE) {
				conP = getConexionPortal();
				stmtp = conP.prepareStatement(queryP);
				stmtp.setString(1, identificacion);
				rsp = stmtp.executeQuery();
				if (rsp.next()) { // montoPSE //Transacciones PSE
					String monto = rsp.getBigDecimal(1).toString();
					montosConfigurados.put("montoPSE", monto);
					String num = rsp.getBigDecimal(2).toString();
					montosConfigurados.put("transacionesPSE", num);
					log.debug("Monto PSE" + monto + " transacciones " + num);
				}
				rsp.close();
				stmtp.close();
				conP.close();
			}
			if (hasJPAT) {
				conN = getConexionNegocio();
				stmtn = conN.prepareStatement(queryN);
				stmtn.setString(1, identificacion);
				rsn = stmtn.executeQuery();
				if (rsn.next()) { // montoJPAT
					String monto = rsn.getBigDecimal(1).toString();
					montosConfigurados.put("montoJPAT", monto);
					log.debug("Monto jpat " + monto);
				}
				rsn.close();
				stmtn.close();
				conN.close();
			}

		} catch (Exception exception) {

			log.error("Error en la sentencia montos config ", exception);
			exception.printStackTrace();
		} finally {
			try {
				if (rss != null)
					rss.close();
				if (rsn != null)
					rsn.close();
				if (rsp != null)
					rsp.close();
				if (stmts != null)
					stmts.close();
				if (stmtn != null)
					stmtn.close();
				if (stmtp != null)
					stmtp.close();
				if (conS != null && !conS.isClosed())
					conS.close();
				if (conP != null && !conP.isClosed())
					conP.close();
				if (conN != null && !conN.isClosed())
					conN.close();
			} catch (Exception exception3) {
				log.error("Error Cerrando la bd");
				exception3.printStackTrace();
			}
		}
		return montosConfigurados;

	}

	public static final void updateMontosMigrados(String identificacion) {

		String query = "update dbo.montos_migrados set ACTIVO = 'N' where NIT = ?";

		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = getConexionSeguridad();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, identificacion);
			stmt.executeUpdate();
			stmt.close();
			connection.close();

		} catch (Exception exception) {
			log.error("Error en la sentencia update montos migrados", exception);
			exception.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null && !connection.isClosed())
					connection.close();

			} catch (Exception exception3) {
				log.error("Error Cerrando la bd");
				exception3.printStackTrace();
			}
		}
	}

	public static boolean tieneProductosMigrados(String tipoId, String id){
		String query = "select * from cliente_producto where tipo_id_cliente = ? and id_cliente = ? and estado = 'M'";

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean prodMigrados = false;
		try {
			connection = getConexionSeguridad();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, tipoId);
			stmt.setString(2, id);
			rs = stmt.executeQuery();
			prodMigrados = rs.next();

		} catch (Exception exception) {
			log.error("Error en la sentencia update montos migrados", exception);
			exception.printStackTrace();
		} finally {
			try {
				if (rs != null){
					rs.close();
				}
				if (stmt != null){
					stmt.close();
				}
				if (connection != null && !connection.isClosed()){
					connection.close();
				}

			} catch (Exception exception3) {
				log.error("Error Cerrando la bd");
				exception3.printStackTrace();
			}
		}
		return prodMigrados;
	}
}
