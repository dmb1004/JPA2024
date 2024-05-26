package es.ubu.lsi.model.multas;

import javax.persistence.*;
import java.util.Set;

@Entity //Entidad
@NamedQueries({
    @NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v")
})
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "Vehiculo.graph",
        attributeNodes = {
            @NamedAttributeNode(value = "conductores", subgraph = "conductoresGraph")
        },
        subgraphs = {
            @NamedSubgraph(
                name = "conductoresGraph",
                attributeNodes = {
                    @NamedAttributeNode(value = "incidencias", subgraph = "incidenciasGraph")
                }
            ),
            @NamedSubgraph(
                name = "incidenciasGraph",
                attributeNodes = {
                    @NamedAttributeNode(value = "tipoIncidencia")
                }
            )
        }
    )
})

public class Vehiculo {
	
	@Id //Primary Key
	private String idauto;
	
	private String nombre;
	
	@Embedded //objeto embebido
	private DireccionPostal direccionPostal;
	
	//relación uno a muchos con la entidad conductor, el campo "vehiculo" de la entidad Conductor es el dueño de la realción.
	//Aplicamos cascada para que todas las operaciones de persistencia realizadas en Vehiculo se propagen a Conductor.
	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
	private Set<Conductor> conductores;
	
	//constructor sin público sin arugmentos, requerimiento para convertir POJO a entidad
	public Vehiculo() {
	}
	
	//getter y setter Idauto
	public String getIdauto() {
		return this.idauto;
	}
	
	public void setIdauto(String idauto) {
		this.idauto = idauto;
	}
	
	//getter y setter Nombre
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	//getter y setter DireccionPostal
	public void setDireccionPostal(DireccionPostal direccionPostal) {
		this.direccionPostal = direccionPostal;
	}
	
	public DireccionPostal getDireccionPostal() {
		return this.direccionPostal;
	}
	
	//getter y setter Conductores
	public Set<Conductor> getConductores() {
		return this.conductores;
	}
	
	public void setConductores(Set<Conductor> conductores) {
		this.conductores = conductores;
	}
	
	/*
	public Conductor addConductor(Conductor conductor) {
		if (conductor != null && !getConductores().contains(conductor)) {
			getConductores().add(conductor);
			if (conductor.getVehiculo() != null) 
				conductor.getVehiculo().getConductores().remove(conductor);
			conductor.setVehiculo(this);
		}
		return conductor;
	}
	
	public Conductor removeConductor(Conductor conductor) {
		getConductores().remove(conductor);
		conductor.setVehiculo(null);
		return conductor;
	}*/
	
	// Método toString entidad Vehiculo
	@Override
	public String toString() {
		return "Vehiculo [idauto=" + idauto + ", nombre=" + nombre + ", direccionPostal=" + direccionPostal
				+ ", conductores=" + conductores + "]";
	}
}