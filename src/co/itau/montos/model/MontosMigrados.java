package co.itau.montos.model;

import java.sql.Date;


public class MontosMigrados {
	
	private int idMontos;
	private String codigo;
	private String transaccion;
	private String portafolio;
	private String nit;
	private double monto;
	private Date fechaPortafolio;
	
	public MontosMigrados(){
		idMontos = 0;
		codigo = null;
		transaccion = null;
		portafolio = null;
		nit = null;
		monto = 0;
		fechaPortafolio = null;
	}

	public int getIdMontos() {
		return idMontos;
	}

	public void setIdMontos(int idMontos) {
		this.idMontos = idMontos;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(String transaccion) {
		this.transaccion = transaccion;
	}

	public String getPortafolio() {
		return portafolio;
	}

	public void setPortafolio(String portafolio) {
		this.portafolio = portafolio;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Date getFechaPortafolio() {
		return fechaPortafolio;
	}

	public void setFechaPortafolio(Date fechaPortafolio) {
		this.fechaPortafolio = fechaPortafolio;
	}
	
	
}
