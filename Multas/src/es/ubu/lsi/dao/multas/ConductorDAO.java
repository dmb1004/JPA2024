package es.ubu.lsi.dao.multas;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.multas.Conductor;
import es.ubu.lsi.model.multas.Incidencia;

public class ConductorDAO extends JpaDAO<Conductor, String> {

	private static final Logger logger = LoggerFactory.getLogger(ConductorDAO.class);

	public ConductorDAO(EntityManager em) {
		super(em);
	}

	// Método para obtener todos los conductores
	@Override
	public List<Conductor> findAll() {
		try {
			TypedQuery<Conductor> query = getEntityManager().createNamedQuery("Conductor.findAll", Conductor.class);
			return query.getResultList();
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}
	
	// Método para obtener todas las incidencias asociadas a un conductor específico
	public List<Incidencia> findByConductor(String nif) {
        try {
            TypedQuery<Incidencia> query = getEntityManager().createNamedQuery("Incidencia.findByConductor", Incidencia.class);
            query.setParameter("nif", nif);
            return query.getResultList();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}