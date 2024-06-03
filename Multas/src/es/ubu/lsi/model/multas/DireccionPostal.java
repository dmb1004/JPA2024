package es.ubu.lsi.model.multas;

import javax.persistence.*;

@Embeddable // Clase embebida
public class DireccionPostal {

	private String ciudad;

	private String cp;

	private String direccion;

	// Getter y setter Ciudad
	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	// Getter y setter cp
	public String getCp() {
		return this.cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	// Getter y setter direccion
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	// Método toString de la clase embebida DireccionPostal
	@Override
	public String toString() {
		return "DireccionPostal [direccion=" + this.getDireccion() + ", codigoPostal=" + this.getCp() + ", ciudad="
				+ this.getCiudad() + "]";
	}

}