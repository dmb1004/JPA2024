package es.ubu.lsi.dao.multas;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.multas.Vehiculo;

public class VehiculoDAO extends JpaDAO<Vehiculo, String> {

	private static final Logger logger = LoggerFactory.getLogger(ConductorDAO.class);

	public VehiculoDAO(EntityManager em) {
		super(em);
	}

	// Método para obtener todos los vehiculos
	@Override
	public List<Vehiculo> findAll() {
		try {
			TypedQuery<Vehiculo> query = getEntityManager().createNamedQuery("Vehiculo.findAll", Vehiculo.class);
			return query.getResultList();

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

	// Método para obtener todos los vehiculos con grafo
	public List<Vehiculo> findAllWithGraph() {
        try {
            TypedQuery<Vehiculo> query = getEntityManager().createNamedQuery("Vehiculo.findAll", Vehiculo.class)
                    .setHint("javax.persistence.loadgraph", getEntityManager()
                            .getEntityGraph("Vehiculo.graph"));

            return query.getResultList();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
