package es.ubu.lsi.model.multas;

import java.io.Serializable;
import javax.persistence.*;

// Anotaciones de entidad y definición del conjunto de consultas JPQL
@Entity
@NamedQueries({ 
	@NamedQuery(name = "Incidencia.findAll", query = "SELECT i FROM Incidencia i"),
	@NamedQuery(name = "Incidencia.deleteAllWithNIF", query = "DELETE FROM Incidencia i WHERE i.id.nif = :p"),
	@NamedQuery(name = "Incidencia.findByConductor", query = "SELECT i FROM Incidencia i WHERE i.id.nif = :nif")
})
public class Incidencia {

	@EmbeddedId // Tipo embebido que actua como primary key compuesta
	private IncidenciaPK id;

	@Lob // Anotación para almacenar grandes cantidades de datos
	private String anotacion;

	// Relación varios a uno, se mapea a través del campo nif de la entidad Conductor. Forma parte de la clave primaria compuesta.
	// JoinColumn se usa para especificar la columna que actua como clave foranea
	@MapsId("nif") 
	@ManyToOne
	@JoinColumn(name = "NIF")
	private Conductor conductor;

	// Relación varios a uno, se usa IDTIPO como clave foranea
	@ManyToOne
	@JoinColumn(name = "IDTIPO")
	private TipoIncidencia tipoIncidencia;

	// Constrcutor público nor-args requisito POJO->Entidad
	public Incidencia() {
	}

	public Incidencia(IncidenciaPK id, String anotacion, Conductor conductor, TipoIncidencia tipoIncidencia) {
        this.id = id;
        this.anotacion = anotacion;
        this.conductor = conductor;
        this.tipoIncidencia = tipoIncidencia;
    }

	// Getter y setter para Id
	public IncidenciaPK getId() {
		return this.id;
	}

	
	public void setId(IncidenciaPK id) {
		this.id = id;
	}

	// Getter y setter para Anotacion
	public String getAnotacion() {
		return this.anotacion;
	}
	
	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}

	// Getter y setter para Conductor
	public Conductor getConductor() {
		return this.conductor;
	}

	public void setConductor(Conductor conductor) {
		
		// Eliminar esta Incidencia del conjunto de Incidencias del Conductor anterior
		if (getConductor() != null) 
			getConductor().getIncidencias().remove(this);
		

		this.conductor = conductor;

		// Agregar esta Incidencia al conjunto de Incidencias del nuevo Conductor si es necesario
		if (conductor != null && !conductor.getIncidencias().contains(this)) 
			conductor.getIncidencias().add(this);
		
	}

	// Getter y setter para tipoIncidencia
	public TipoIncidencia getTipoIncidencia() {
		return this.tipoIncidencia;
	}

	public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {

		// Eliminar esta Incidencia del conjunto de Incidencias del TipoIncidencia anterior
		if (getTipoIncidencia() != null) 
			getTipoIncidencia().getIncidencias().remove(this);
		

		this.tipoIncidencia = tipoIncidencia;

		// Agregar esta Incidencia al conjunto de Incidencias del nuevo TipoIncidencia si es necesario
		if (tipoIncidencia != null && !tipoIncidencia.getIncidencias().contains(this)) 
			tipoIncidencia.getIncidencias().add(this);
		

	}

	// Método toString de la entidad Incidencia
	@Override
	public String toString() {
		return "Incidencia [id= " + this.getId() + ", anotacion=" + this.getAnotacion() + ", conductor="
				+ this.getConductor() + ", tipoIncidencia=" + this.getTipoIncidencia() + "]";
	}

}