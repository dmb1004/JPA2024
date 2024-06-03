package es.ubu.lsi.model.multas;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

// Entidad y implementación de consulta JPQL
@Entity
@NamedQueries({
		@NamedQuery(name = "TipoIncidencia.findAll", query = "SELECT t FROM TipoIncidencia t")
})
public class TipoIncidencia {

	@Id // Primary Key
	private long id;

	private String descripcion;

	private BigDecimal valor;

	// Relación uno a muchos con la entidad Incidencia, el campo "tipoIncidencia" es
	// el propietario de la realción.
	@OneToMany(mappedBy = "tipoIncidencia")
	private Set<Incidencia> incidencias;

	// Constructor público no-args necesario en entidades
	public TipoIncidencia() {
	}

	// Getter y setter ID
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// Getter y setter Descripcion
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	// Getter y setter Valor
	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	// Getter y setter Incidencias
	public Set<Incidencia> getIncidencias() {
		return this.incidencias;
	}

	public void setIncidencias(Set<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	// Función para añadir una incidencia al tipo
	public void addIncidencia(Incidencia incidencia) {
		if (incidencia != null && !getIncidencias().contains(incidencia)) {
			// Asegura que la incidencia no está ya asociada a este tipo de incidencia

			if (!getIncidencias().contains(incidencia)) {
				getIncidencias().add(incidencia);
			}
			// Asegura que la incidencia está apuntando a este tipo de incidencia

			if (incidencia.getTipoIncidencia() != null) {
				incidencia.getTipoIncidencia().getIncidencias().remove(incidencia);
			}
			if (incidencia.getTipoIncidencia() != this) {
				incidencia.setTipoIncidencia(this);
			}
		}
	}

	// Función para borrar una incidencia al tipo
	public void removeIncidencia(Incidencia incidencia) {
		if (incidencia != null && getIncidencias().contains(incidencia)) {
			getIncidencias().remove(incidencia);
			if (incidencia.getTipoIncidencia() == this) {
				incidencia.setTipoIncidencia(null);
			}
		}
	}

	// Método toString de la entidad TipoIncidencia
	@Override
	public String toString() {
		return "TipoIncidencia [id=" + this.getId() + ", descripcion=" + this.getDescripcion() + ", valor="
				+ this.getValor() + "]";
	}

}