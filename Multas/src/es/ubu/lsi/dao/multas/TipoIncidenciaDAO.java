package es.ubu.lsi.dao.multas;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.multas.TipoIncidencia;

public class TipoIncidenciaDAO extends JpaDAO<TipoIncidencia, Long> {

	private static final Logger logger = LoggerFactory.getLogger(TipoIncidenciaDAO.class);

	public TipoIncidenciaDAO(EntityManager em) {
		super(em);
	}

	// Método para obtener todos los tipos de incidencias
	@Override
	public List<TipoIncidencia> findAll() {
		try {
			TypedQuery<TipoIncidencia> query = getEntityManager().createNamedQuery("TipoIncidencia.findAll", TipoIncidencia.class);
			return query.getResultList();

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}
}