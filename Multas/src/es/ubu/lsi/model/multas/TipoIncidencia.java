package es.ubu.lsi.model.multas;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity //Entidad
@NamedQueries({
    @NamedQuery(name = "TipoIncidencia.findAll", query = "SELECT t FROM TipoIncidencia t")
})
public class TipoIncidencia {
	
	@Id // Primary Key
	private long id;
	
	private String descripcion;
	
	private BigDecimal valor;
	
	
	//relación uno a muchos con la entidad Incidencia, el campo "tipoIncidencia" es el dueño de la realción.
	@OneToMany(mappedBy = "tipoIncidencia")
	private Set<Incidencia> incidencias;
	
	//constructor público no-args necesario en entidades
	public TipoIncidencia() {
	}
	
	//getter y setter ID
	public long getId() {
	return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	//getter y setter Descripcion
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	// getter y setter Valor
	public BigDecimal getValor() {
		return this.valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	// getter y setter Incidencias
	public Set<Incidencia> getIncidencias() {
		return this.incidencias;
	}
	
	public void setIncidencias(Set<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}
	
	/*
	public Incidencia addIncidencia(Incidencia incidencia) {
	    if (incidencia != null) {
	        // Asegura que la incidencia no está ya asociada a este tipo de incidencia
	        if (!getIncidencias().contains(incidencia)) {
	            getIncidencias().add(incidencia);
	        }
	        // Asegura que la incidencia está apuntando a este tipo de incidencia
	        if (incidencia.getTipoIncidencia() != this) {
	            incidencia.setTipoIncidencia(this);
	        }
	    }
	    return incidencia;
	}
		
	public Incidencia removeIncidencia(Incidencia incidencia) {
	    if (incidencia != null && getIncidencias().contains(incidencia)) {
	        getIncidencias().remove(incidencia);
	        if (incidencia.getTipoIncidencia() == this) {
	            incidencia.setTipoIncidencia(null);
	        }
	    }
	    return incidencia;
	}*/
	
	@Override
	public String toString() {
		return "TipoIncidencia [id=" + this.getId() + ", descripcion=" + this.getDescripcion() + ", valor="
		+ this.getValor() + "]";
	}

}