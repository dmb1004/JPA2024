package es.ubu.lsi.model.multas;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable // Tipo embedido, ejerciendo de clave primaria compuesta, por lo que debe
			// implementar serializable
public class IncidenciaPK implements Serializable {

	// Se mapea a una columna timeStamp de la base de datos
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fecha;

	// No se insertará y actualizará directamente en la bd.
	@Column(insertable = false, updatable = false)
	private String nif;

	// Constructor público no-args
	public IncidenciaPK() {
	}

	public IncidenciaPK(String nif, java.util.Date fecha) {
		this.nif = nif;
		this.fecha = fecha;
	}

	// Getter y setter Fecha
	public java.util.Date getFecha() {
		return this.fecha;
	}

	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}

	// Getter y setter NIF
	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	// Método toString del tipo embedido incidenciaPK
	@Override
	public String toString() {
		return "IncidenciaPK [fecha=" + this.getFecha() + ", nif=" + this.getNif() + "]";
	}

	// Método equals sobreescribido, requerimiento de clases que ejercen de clave
	// compuesta
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IncidenciaPK)) {
			return false;
		}
		IncidenciaPK castOther = (IncidenciaPK) other;
		return this.fecha.equals(castOther.fecha) && this.nif.equals(castOther.nif);
	}

	// Método HashCode sobreescribido, requerimiento de clases que ejercen de clave
	// compuesta
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fecha.hashCode();
		hash = hash * prime + this.nif.hashCode();

		return hash;
	}
}