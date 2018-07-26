package co.itau.montos;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import tecnoevolucion.encapsulado.DatosLogUsuario;
import tecnoevolucion.web.WGeneral;
import co.com.bcr.auditlogtx.business.services.AuditLogUtility;
import co.com.bcr.auditlogtx.common.domain.vo.AuditLogTransAdditionalVO;
import co.com.bcr.auditlogtx.common.domain.vo.AuditLogTransactionVO;
import co.com.bcr.auditlogtx.common.domain.vo.AuditLogVO;
import co.com.bcr.auditlogtx.util.exceptions.InternalErrorException;
import co.itau.montos.DAO.ClienteAnticipadoDAO;
import co.itau.montos.util.Constantes;

/**
 * Servlet implementation class ActualizarMontos
 */
public class ActualizarMontos extends BasePage {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Constantes.LOGGER_NAME);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActualizarMontos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {
		
		actualizarMontos(request,response );
	}	
	
	private void actualizarMontos(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String montoPortal = request.getParameter("MPORT");
		String montoJpat = request.getParameter("MJPAT");
		String montoPse = request.getParameter("MPSE");
		String trxPse = request.getParameter("TPSE");
		char idioma = request.getParameter("IDIOMA").charAt(0);
		int numTx = Integer.parseInt(trxPse);
		
		String sesion = request.getParameter("SESION");
		DatosLogUsuario dlg = WGeneral.consultaDatosSesion(sesion);

		PrintWriter out = response.getWriter();
		String remoteAddress = getDireccionRemota(request);
		BigDecimal montoTxPSE = new BigDecimal(montoPse);
		BigDecimal montoTxPortal = new BigDecimal(montoPortal);
		if (montoTxPSE.compareTo(montoTxPortal) > 0){
			writeMessage(out, "El monto PSE debe ser inferior o igual al monto global de canales", sesion, idioma);
			return;
		}
				
		try {
			ClienteAnticipadoDAO.actualizaMontosSeguridad(dlg, montoPortal, remoteAddress);
			grabarAuditorBasico(dlg, Constantes.RESP_ACTUALIZACION_EXITO, "por" + montoPortal, remoteAddress);
			ClienteAnticipadoDAO.actualizaMontosPortal(dlg, montoPse, numTx, remoteAddress);
			grabarAuditorBasico(dlg, Constantes.RESP_ACTUALIZACION_EXITO, "PSE " + montoPse, remoteAddress);
			ClienteAnticipadoDAO.actualizaMontosNegocio(dlg, montoJpat, remoteAddress);
			grabarAuditorBasico(dlg, Constantes.RESP_ACTUALIZACION_EXITO, "PAT" + montoJpat, remoteAddress);
		} catch (SQLException e) {
			log.error("Error actualizando los montos ", e);
			writeMessage(out,"Se presento un error registrando la informacion de montos", sesion, idioma);
			grabarAuditorBasico(dlg, Constantes.RESP_ACTUALIZACION_ERROR, e.getMessage(), remoteAddress);
			return;
		}
		writeMessage(out,"Montos actualizados correctamente", sesion, idioma);
		return;

	}
	
	private String getDireccionRemota(HttpServletRequest request) {
		if (request.getHeader("Proxy-ip") == null)
			return request.getRemoteAddr();
		return request.getHeader("Proxy-ip");
	}
	
	/**
	 * Guarda la auditoria 
	 * @param dlg
	 */
	public void grabarAuditorBasico(DatosLogUsuario dlg, String resp, String valores, String ip){
		AuditLogUtility audit = new AuditLogUtility();
		AuditLogVO auditVO = new AuditLogVO();
		auditVO.setAudUserId(dlg.identificacion);
		auditVO.setAudUserIdType(dlg.tipo_id);
		auditVO.setAudCustomerIdType(dlg.tipo_id_cli);
		auditVO.setAudCustomerId(dlg.numero_id_cli);
		auditVO.setAudSessionId(dlg.sesion_web);
		auditVO.setAudTxCanal(dlg.canal);
		auditVO.setAudTxRespCode(resp);
		Date date = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		auditVO.setAudTxStartDate(gc);
		auditVO.setAudTxFinishDate(gc);
		auditVO.setAudTerminalId(ip);
		String eventCode = audit.findEventCode(Constantes.EVENTO_ACTUALIZACION_MONTOS);
		auditVO.setAudTxCodEvent(eventCode);
		auditVO.setAudTxSeverity(audit.findSeverityCode(eventCode, resp));
		auditVO.setAudTxRespCode(valores);
		
		try {
			audit.store(auditVO);
		} catch (InternalErrorException e) {
			e.printStackTrace();
			log.error("Error guadando la auditoria del evento de actualizacion de montos ", e);
		}
	}

}
