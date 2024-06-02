package es.ubu.lsi.model.multas;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * The persistent class for the CONDUCTOR database table.
 * 
 */
@Entity // Entidad
@NamedQueries({
	@NamedQuery(name = "Conductor.findAll", query = "SELECT c FROM Conductor c")
})
public class Conductor {
	private static final long serialVersionUID = 1L;

	@Id // Primary key
	private String nif;

	private String apellido;

	private String nombre;

	private BigDecimal puntos;
	
	// relación varios a uno, se usa IDAUTO como clave foranea

	@ManyToOne
	@JoinColumn(name = "IDAUTO")
	private Vehiculo vehiculo;

	// /relación uno a muchos con la entidad Incidencia, el campo "conductor" de la entidad Incidencia es el dueño de la realción.
	@OneToMany(mappedBy = "conductor")
	private Set<Incidencia> incidencias;

	// Tipo embebido 
	@Embedded
	private DireccionPostal direccionPostal;

	//constructor público no-args
	public Conductor() {
	}

	//getter y setter NIF
	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	//getter y setter apellido
	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	//getter y setter nombre
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	//getter y setter puntos
	public BigDecimal getPuntos() {
		return this.puntos;
	}

	public void setPuntos(BigDecimal puntos) {
		this.puntos = puntos;
	}

	//getter y setter Vehiculo
	public Vehiculo getVehiculo() {
		return this.vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {

        if (this.vehiculo != null && this.vehiculo != vehiculo) 
        	getVehiculo().getConductores().remove(this);

		this.vehiculo = vehiculo;

		if (vehiculo != null && !vehiculo.getConductores().contains(this)) {
			vehiculo.getConductores().add(this);
		}
	}

	public Set<Incidencia> getIncidencias() {
		return this.incidencias;
	}

	public void setIncidencias(Set<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public Incidencia addIncidencia(Incidencia incidencia) {
		if (incidencia != null && !getIncidencias().contains(incidencia)) {
			
            getIncidencias().add(incidencia);

			if (incidencia.getConductor() != null) {
				incidencia.getConductor().getIncidencias().remove(incidencia);
			}
			incidencia.setConductor(this);
		}
		return incidencia;
	}

	public Incidencia removeIncidencia(Incidencia incidencia) {
		if (incidencias.remove(incidencia))
            incidencia.setConductor(null);

		return incidencia;
	}

	public void setDireccionPostal(DireccionPostal direccionPostal) {
		this.direccionPostal = direccionPostal;
	}

	public DireccionPostal getDireccionPostal() {
		return this.direccionPostal;
	}

	@Override
	public String toString() {

		return "Conductor [nif=" + this.getNif() + ", nombre=" + this.getNombre() + ", apellido=" + this.getApellido()
				+ ", direccionPostal=" + this.getDireccionPostal() + ", puntos=" + this.getPuntos() + "]";
	}

}