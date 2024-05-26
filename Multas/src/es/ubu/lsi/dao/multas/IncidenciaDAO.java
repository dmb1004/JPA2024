package es.ubu.lsi.dao.multas;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.multas.Incidencia;
import es.ubu.lsi.model.multas.IncidenciaPK;

public class IncidenciaDAO extends JpaDAO<Incidencia, IncidenciaPK> {

	private static final Logger logger = LoggerFactory.getLogger(TipoIncidenciaDAO.class);

	public IncidenciaDAO(EntityManager em) {
		super(em);
	}

	// Método para obtener todas las incidencias
	@Override
	public List<Incidencia> findAll() {
		try {
			TypedQuery<Incidencia> query = getEntityManager().createNamedQuery("Incidencia.findAll", Incidencia.class);
			return query.getResultList();

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

	// Método para eliminar todas las incidencias de un conductor específico
	public void deleteAllWithNIF(String nif) {
		try {
			getEntityManager().createNamedQuery("Incidencia.deleteAllWithNIF").setParameter("p", nif).executeUpdate();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}
}