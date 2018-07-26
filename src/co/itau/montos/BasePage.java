package co.itau.montos;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;

import tecnoevolucion.web.WManipularFecha;

public class BasePage extends HttpServlet{
	
	public void writeMessage(PrintWriter out, String mensaje, String sesion, char idioma){
		String fecha = (String)WManipularFecha.fecDMY12( "/",true );
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Untitled Document</title>");
		out.println("<meta http-equiv=Content-Type content=text/html; charset=iso-8859-1>");
		out.println("<link rel=stylesheet href=/estilo/estilo.css>");
		out.println("</head>");
		out.println("<body CLASS=cuerpo>");
		out.println("<TABLE ALIGN=CENTER WIDTH=100% >");

		out.println("<TR>");
		out.println("	<TD VALIGN=TOP CLASS=tituloNombres WIDTH=35% ></TD>");
		out.println("	<TD VALIGN=TOP CLASS=tituloNombres WIDTH=35% ></TD>");

		out.println("	<TD VALIGN=TOP CLASS=tituloNombres WIDTH=30% >Fecha y hora " + fecha + "</TD>");
		out.println("</TR>");
		out.println("</TABLE>");

		out.println("<TABLE width=600 border=0 class=tabla ALIGN=CENTER>");
		out.println("<TR align=CENTER>");
		out.println("<TD class=titulo>Mensaje</TD>");
		out.println("</TR>");
		out.println("<TR align=CENTER>");
		out.println("<TD class=subtitulo align=center colspan=2>Resultado</TD>");
		out.println("</TR>");
		out.println("<TR>");
		out.println("<TD class=texFormulario align=CENTER><br>");
		out.println(mensaje);
		out.println("	<br>");
		out.println("</TD>");
		out.println("</TR>");
		out.println("<TR>");
		out.println("<TD class=celdaBlanca align=CENTER>");
		out.println("<div align='center'>");
		out.println("<a href=/montos/pagMontos?SESION=" + sesion + "&IDIOMA=" + idioma + ">Regresar</a>");
		out.println("</div>");
		out.println("</TD></TR>");
		out.println("</TABLE>");
		out.println("</body>");
		out.println("</html>");
	}

}
