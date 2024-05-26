package es.ubu.lsi.service.multas;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.lsi.dao.multas.ConductorDAO;
import es.ubu.lsi.dao.multas.IncidenciaDAO;
import es.ubu.lsi.dao.multas.TipoIncidenciaDAO;
import es.ubu.lsi.dao.multas.VehiculoDAO;
import es.ubu.lsi.model.multas.Conductor;
import es.ubu.lsi.model.multas.Incidencia;
import es.ubu.lsi.model.multas.IncidenciaPK;
import es.ubu.lsi.model.multas.TipoIncidencia;
import es.ubu.lsi.model.multas.Vehiculo;
import es.ubu.lsi.service.PersistenceException;
import es.ubu.lsi.service.PersistenceService;

public class ServiceImpl extends PersistenceService implements Service {

	private static final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

	@Override
	public void insertarIncidencia(Date fecha, String nif, long tipo) throws PersistenceException {

		//crear una nueva sesión
		EntityManager em = this.createSession();

		try {
			//iniciar transacción
			beginTransaction(em);
			
			ConductorDAO conductorDAO = new ConductorDAO(em);
			TipoIncidenciaDAO tipoIncidenciaDAO = new TipoIncidenciaDAO(em);

			// Encontramos conductor por su NIF
			Conductor conductor = conductorDAO.findById(nif);
			if (conductor == null)
				throw new IncidentException(IncidentError.NOT_EXIST_DRIVER);

			// Encontramos el tipo de incidencia por su ID
			TipoIncidencia tipoIncidencia = tipoIncidenciaDAO.findById(tipo);
			if (tipoIncidencia == null)
				throw new IncidentException(IncidentError.NOT_EXIST_INCIDENT_TYPE);

			// Crear una incidencia y actualizar los puntos de conductor
			Incidencia incidencia = new Incidencia(new IncidenciaPK(nif, fecha), "", conductor, tipoIncidencia);

			// verificar si el conductor tiene suficientes puntos
			if (conductor.getPuntos().compareTo(tipoIncidencia.getValor()) < 0)
				throw new IncidentException(IncidentError.NOT_AVAILABLE_POINTS);

			//Descontar los puntos al conductor y añadir incidencia
			conductor.setPuntos(conductor.getPuntos().subtract(tipoIncidencia.getValor()));
			conductor.addIncidencia(incidencia);

			// Persistir la incidencia en la base de datos (el coonductor ya era persistente)
			em.persist(incidencia);
			commitTransaction(em);

		} catch (IncidentException e) {
            rollbackTransaction(em); //revertir en caso de error
            logger.error(e.getLocalizedMessage(), e);
            throw e;
        } catch (Exception e) {
            rollbackTransaction(em); //revertir en caso de error
            logger.error("excepción no esperada:", e);
            throw new PersistenceException("Error general:", e);
        } finally {
            close(em);
            logger.debug("Sesión cerrada.");
        }
	}

	
	
	@Override
	public void indultar(String nif) throws PersistenceException {

		//crear una nueva sesión
		EntityManager em = this.createSession();

		try {

			//iniciar la transacción
			beginTransaction(em);
			
			ConductorDAO conductorDAO = new ConductorDAO(em);
			IncidenciaDAO incidenciaDAO = new IncidenciaDAO(em);

			// encontrar al conductor mediante su NIF
			Conductor conductor = conductorDAO.findById(nif);
			if (conductor == null)
				throw new IncidentException(IncidentError.NOT_EXIST_DRIVER);

			// restaurar los puntos del conductor al máximo
			conductor.setPuntos(BigDecimal.valueOf(MAXIMO_PUNTOS));

			// Eliminar todas las incidencias del conductor en la base de datos
			incidenciaDAO.deleteAllWithNIF(nif);

			// Eliminar todas las incidencias del conductor en el modeelo de objetos
			Set<Incidencia> incidenciasParaBorrar = new HashSet<Incidencia>(conductor.getIncidencias());
			for (Incidencia i : incidenciasParaBorrar) {
				conductor.removeIncidencia(i);
			}

			// confirmar transacción
			commitTransaction(em);

        } catch (IncidentException e) {
            rollbackTransaction(em);
            logger.error(e.getLocalizedMessage(), e);
            throw e;
        } catch (Exception e) {
            rollbackTransaction(em);
            logger.error("Excepción inesperada:", e);
            throw new PersistenceException("Error general:", e);
        } finally {
            close(em);
            logger.debug("Sesión cerrada.");
        }
    }

	@Override
	public List<Vehiculo> consultarVehiculos() throws PersistenceException {

		//Crear una nueva sesión
		EntityManager em = this.createSession();

		try {
			
			//iniciar transacción
			beginTransaction(em);
			VehiculoDAO vehiculoDAO = new VehiculoDAO(em);

			//consultar todos los vehiculos usando un grafo
			List<Vehiculo> vehiculos = vehiculoDAO.findAllWithGraph();
			commitTransaction(em); // confirmamos la tranascción (opcional)

			return vehiculos;

        } catch (Exception e) {
            rollbackTransaction(em); //revertir en caso de error
            logger.error("excepción no esperada:", e);
            throw new PersistenceException("Error general:", e);
        } finally {
            close(em);
            logger.debug("Sesión cerrada.");
        }
	}

}