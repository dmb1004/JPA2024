package es.ubu.lsi.model.multas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;

// Anotación de entidad, implementación de consultas JPQL y de grafos
@Entity // Entidad
@NamedQueries({
		@NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v")
})
@NamedEntityGraphs({
		@NamedEntityGraph(name = "Vehiculo.graph", attributeNodes = {
				@NamedAttributeNode(value = "conductores", subgraph = "conductoresGraph")
		}, subgraphs = {
				@NamedSubgraph(name = "conductoresGraph", attributeNodes = {
						@NamedAttributeNode(value = "incidencias", subgraph = "incidenciasGraph")
				}),
				@NamedSubgraph(name = "incidenciasGraph", attributeNodes = {
						@NamedAttributeNode(value = "tipoIncidencia")
				})
		})
})

public class Vehiculo {

	@Id // Primary Key
	private String idauto;

	private String nombre;

	@Embedded // Objeto embebido
	private DireccionPostal direccionPostal;

	// Relación uno a muchos con la entidad conductor, el campo "vehiculo" de la
	// entidad Conductor es el propietario de la realción.
	// Aplicamos cascada para que todas las operaciones de persistencia realizadas
	// en Vehiculo se propagen a Conductor.
	@OneToMany(mappedBy = "vehiculo")
	private Set<Conductor> conductores;

	// Constructor público sin arugmentos, requerimiento para convertir POJO a
	// entidad
	public Vehiculo() {
	}

	// Getter y setter Idauto
	public String getIdauto() {
		return this.idauto;
	}

	public void setIdauto(String idauto) {
		this.idauto = idauto;
	}

	// Getter y setter Nombre
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// Getter y setter DireccionPostal
	public void setDireccionPostal(DireccionPostal direccionPostal) {
		this.direccionPostal = direccionPostal;
	}

	public DireccionPostal getDireccionPostal() {
		return this.direccionPostal;
	}

	// Getter y setter Conductores
	public Set<Conductor> getConductores() {
		return this.conductores;
	}

	public void setConductores(Set<Conductor> conductores) {
		this.conductores = conductores;
	}

	// Función para añadir un conducto al vehículo
	public void addConductor(Conductor conductor) {
		if (conductor != null && !getConductores().contains(conductor)) {
			getConductores().add(conductor);
			if (conductor.getVehiculo() != null)
				conductor.getVehiculo().getConductores().remove(conductor);
			conductor.setVehiculo(this);
		}
	}

	// Función para borrar a un conductor del vehículo
	public void removeConductor(Conductor conductor) {
		getConductores().remove(conductor);
		conductor.setVehiculo(null);
	}

	// Método toString de la entidad Vehiculo
	@Override
	public String toString() {
		return "Vehiculo [idauto=" + this.getIdauto() + ", nombre= " + this.getNombre() + ", direccionPostal= "
				+ this.getDireccionPostal() + "]";
	}
}