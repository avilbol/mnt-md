package co.itau.montos;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import co.itau.montos.DAO.ClienteAnticipadoDAO;
import co.itau.montos.model.MontosMigrados;
import co.itau.montos.util.Constantes;
import tecnoevolucion.encapsulado.DatosLogUsuario;
import tecnoevolucion.web.WGeneral;
import tecnoevolucion.web.WInterfaceNew;
import tecnoevolucion.web.WUtil;

/**
 * Servlet implementation class PagMontos
 */
public class PagMontos extends BasePage {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(Constantes.LOGGER_NAME);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PagMontos() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		service(request,response);
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {
		mostrarMontos(request,response);
	}

	

	
	private void mostrarMontos(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		PrintWriter out = response.getWriter();
		String sesion = request.getParameter("SESION");
		char idioma = request.getParameter("IDIOMA").charAt(0);
		DatosLogUsuario dlg = WGeneral.consultaDatosSesion(sesion);
		
		if ( !ClienteAnticipadoDAO.tieneProductosMigrados(dlg.tipo_id_cli, dlg.numero_id_cli)){
			writeMessage(out,"La empresa " + dlg.nom_cliente +" no puede parametrizar montos por el portal", sesion, idioma);
			return;
		}
	
		List<MontosMigrados> listaMontos = ClienteAnticipadoDAO.getMontos(dlg.numero_id_cli);
		if (listaMontos.size() == 0){
			writeMessage(out,"La empresa " + dlg.nom_cliente +" no tiene montos configurados", sesion, idioma);
			return;
		}

		HashMap<String, String> montosConfigurados = ClienteAnticipadoDAO.isMontosConfig(dlg.numero_id_cli);
		Hashtable multilen = WGeneral.tomarMultilenguaje("00", "pagMontos", idioma);
		WInterfaceNew winterfacenew = new WInterfaceNew();


		out.println("<html>");
		out.println("<head>");
		out.println("<title>" + (String) multilen.get("Configuracion Montos") + "</title>");
		out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">");
		out.println("<meta http-equiv=Content-Type content=text/html; charset=iso-8859-1>");
		out.println("<link rel=stylesheet href=/estilo/estilo.css>");

		out.println("</head>");
		StringBuffer stringbuffer = winterfacenew.funcionesValidacionJS(idioma);
		out.println(stringbuffer);
		out.println("<body CLASS=cuerpo>");

		
		out.println("<form name=FORM1 action=/montos/actualizarMontos method=post onsubmit=\"return registrar()\">");
		out.println("<table width=600 border=0 class=tabla align=center>");
		out.println("<tr>");
		out.println("<td colspan=2 class=titulo align=center>" + (String) multilen.get("Configuracion Montos")
				+ "</td>");
		out.println("</tr>");
		out.println("<tr> ");
		out.println("<td colspan=2 class=subtitulo align=center>" + (String) multilen.get("Datos Basicos") + "</td>");
		out.println("</tr>");
		out.println("<tr> ");
		out.println("<td class=texFORMulario width=307>" + (String) multilen.get("Empresa") + "</td>");
		// inicio 3-ene-2017 solución incidencia 15682
		//out.println("<td class=texNormal>" + listaMontos.get(0).getPortafolio() + "</td>");
		out.println("<td class=texNormal>" + dlg.nom_cliente + "</td>");
		// fin 3-ene-2017 solución incidencia 15682
		out.println("</tr>");
		out.println("<tr> ");
		out.println("<td class=texFORMulario width=307>" + (String) multilen.get("NIT") + "</td>");
		out.println("<td class=texNormal>" + listaMontos.get(0).getNit() + "</td>");
		out.println("</tr>");
		out.println("<tr> ");
		out.println("<td class=texFORMulario width=307>" + (String) multilen.get("Usuario") + "</td>");
		// inicio 3-ene-2017 solución incidencia 15682
		//out.println("<td class=texNormal>" + dlg.nombre + "</td>");
		out.println("<td class=texNormal>" + dlg.identificacion + "</td>");
		// fin 3-ene-2017 solución incidencia 15682
		out.println("</tr>");

		out.println("<tr> ");
		out.println("<td class=texFORMulario width=307>" + (String) multilen.get("nombre") + "</td>");
		// inicio 3-ene-2017 solución incidencia 15682
		//out.println("<td class=texNormal>" + dlg.nom_cliente + "</td>");
		out.println("<td class=texNormal>" + dlg.nombre + "</td>");
		// fin 3-ene-2017 solución incidencia 15682
		out.println("</tr>");

		out.println("<tr> ");
		out.println("<td class=texFORMulario width=307>&nbsp;</td>");
		out.println("<td class=texFORMulario>&nbsp;</td>");
		out.println("</tr>");
		out.println("<tr> ");
		out.println("<td colspan=2 class=subtitulo align=CENTER>" + (String) multilen.get("Montos AzulNet") + "</td>");
		out.println("</tr>");
		out.println("<tr> ");
		out.println("<td width=124 class=texFormulario align=CENTER>" + (String) multilen.get("Transaccion") + "</td>");
		out.println("<td width=112 class=texFormulario align=CENTER>" + (String) multilen.get("Montos") + "</td>");
		out.println("</tr>");
		// ArrayList<E> vector = listaMontos;
		NumberFormat numberformat = NumberFormat.getInstance();
		int trxAz = 0;
		int jpatAz = 0;
		int pseAz = 0;

		for (int i = 0; i < listaMontos.size(); i++) {
			if (listaMontos.get(i).getCodigo().toString().equalsIgnoreCase("PAGO_COMPRAS_INTERNET_PSE")) {
				pseAz = pseAz + 1;
			} else if (listaMontos.get(i).getCodigo().toString().equalsIgnoreCase("OPERACION_ACH")
					|| listaMontos.get(i).getCodigo().toString().equalsIgnoreCase("PAGO_NOMINA")) {
				jpatAz = jpatAz + 1;
			} else {
				trxAz = trxAz + 1;
			}
			out.println("<tr> ");
			out.println("<td width=124 class=texNormal>" + listaMontos.get(i).getTransaccion() + "</td>");
			out.println("<td width=112 class=texNormal align=RIGHT>$"
					+ numberformat.format(listaMontos.get(i).getMonto()) + "</td>");
		}

		out.println("<tr> ");
		out.println("<td colspan=2 class=subtitulo align=CENTER>" + (String) multilen.get("Montos de Operacion Diaria")
				+ "</td>");
		out.println("</tr>");
		if (trxAz > 0) {
			out.println("<tr> ");
			out.println("<td class=texFORMulario width=307>" + (String) multilen.get("Monto Diario Portal") + "</td>");
			out.println("<TD CLASS=texFORMulario width=307>$ ");

			out.println(winterfacenew.campoNumeroBigDecimal("MPORT", "Monto Diario Transacciones Portal", 12, 12, 12, 0,
					new BigDecimal("0"), new BigDecimal("999999999999"),
					montosConfigurados.containsKey("montoTRX") ? montosConfigurados.get("montoTRX").toString() : "0", true)
					+ "</TD>");

			out.println("</tr>");
		}
		if (jpatAz > 0) {
			out.println("<tr> ");
			out.println("<td class=texFORMulario width=307>" + (String) multilen.get("Monto Diario Nomina y ACH")
					+ "</td>");
			out.println("<TD CLASS=texFORMulario >$ ");

			out.println(winterfacenew.campoNumeroBigDecimal("MJPAT", "Monto Diario Pagos ACH y Nómina", 20, 20, 20, 0,
					new BigDecimal("0"), new BigDecimal("99999999999999999999"),
					montosConfigurados.containsKey("montoJPAT") ? montosConfigurados.get("montoJPAT") : "0", true)
					+ "</TD>");

			out.println("</tr>");
		}
		if (pseAz > 0) {
			out.println("<tr> ");
			// inicio 3-ene-2017 solución incidencia 15682
			//out.println("<td class=texFORMulario width=307>" + (String) multilen.get("Monto Diario PSE") + "</td>");
			out.println("<td class=texFORMulario width=307>" + (String) multilen.get("Monto Diario PSE") + " PSE</td>");
			// fin 3-ene-2017 solución incidencia 15682
			out.println("<TD CLASS=texFORMulario width=307>$ ");

			out.println(winterfacenew.campoNumeroBigDecimal("MPSE", "Monto Diario Transacciones PSE", 12, 12, 12, 0,
					new BigDecimal("0"), new BigDecimal("999999999999"),
					montosConfigurados.containsKey("montoPSE") ? montosConfigurados.get("montoPSE") : "0", true)
					+ "</TD>");

			out.println("</tr>");
			out.println("<tr> ");
			// inicio 3-ene-2017 solución incidencia 15682
			//out.println("<td class=texFORMulario width=307>" + (String) multilen.get("Transacciones PSE") + "</td>");
			out.println("<td class=texFORMulario width=307>" + (String) multilen.get("Transacciones PSE") + " PSE</td>");
			// fin 3-ene-2017 solución incidencia 15682
			out.println("<TD CLASS=texFORMulario >$ ");

			out.println(winterfacenew.campoNumeroBigDecimal(
					"TPSE",
					"Transacciones PSE",
					2,
					2,
					2,
					0,
					new BigDecimal("0"),
					new BigDecimal("99"),
					montosConfigurados.containsKey("transacionesPSE") ? montosConfigurados.get("transacionesPSE") : "0",
					true)
					+ "</TD>");

			out.println("</tr>");
		}
		String url = "https://www.itau.co/empresas/";
		out.println("<tr>");
		out.println("<td style=\"color:#EC7404\" colspan=2> ");
		out.println((String) multilen.get("Nota"));
		out.println("</tr>");
		out.println("</td> ");
		out.println("<tr align=center> ");
		out.println("<td colspan=2 > ");

		out.println("<A HREF=/consultaproductos/servlet/CerrarSesion?SESION="
				+ sesion
				+ "&IDIOMA="
				+ idioma
				+ "&URLORI="
				+ url
				+ " onClick=\"return confirm('¿Está seguro que desea cancelar y cerrar sesión?')\" CLASS=boton-blanco>Cancelar</A>");
		out.println("<input CLASS=boton-naranja type=submit name=ACEPTO value=Registrar> ");
		out.println("<INPUT TYPE=HIDDEN NAME=SESION VALUE=" + sesion + ">");
		out.println("<INPUT TYPE=HIDDEN NAME=IDIOMA VALUE=" + idioma + ">");
		out.println("<INPUT TYPE=HIDDEN NAME=PAG VALUE=pagMontos>");
		out.println("<INPUT TYPE=HIDDEN NAME=MPORT VALUE=0>");
		out.println("<INPUT TYPE=HIDDEN NAME=MJPAT VALUE=0>");
		out.println("<INPUT TYPE=HIDDEN NAME=MPSE VALUE=0>");
		out.println("<INPUT TYPE=HIDDEN NAME=TPSE VALUE=0>");
		out.println("<INPUT TYPE=HIDDEN NAME=TAZUL VALUE=" + trxAz + ">");
		out.println("<INPUT TYPE=HIDDEN NAME=JAZUL VALUE=" + jpatAz + ">");
		out.println("<INPUT TYPE=HIDDEN NAME=PAZUL VALUE=" + pseAz + ">");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("<TABLE align=CENTER>");
		out.println("<TR ALIGN=center> ");
		out.println("</TR>");
		out.println("</TABLE>");
		out.println(winterfacenew.funcionObligatoriosJS("FORM1", idioma, "").toString());
		out.println("</body>");
		out.println("</html>");
	}

}
