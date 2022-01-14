package invernadero.model.compras.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import invernadero.model.auditoria.managers.ManagerAuditoria;
import invernadero.model.core.entities.Ciudad;
import invernadero.model.core.entities.Proveedor;
import invernadero.model.core.entities.SegUsuario;
import invernadero.model.core.managers.ManagerDAO;
import invernadero.model.seguridades.dtos.LoginDTO;
import invernadero.model.seguridades.managers.ManagerSeguridades;

/**
 * Session Bean implementation class ManagerProyectos
 */
@Stateless
@LocalBean
public class ManagerCompras {

	//Nuevo codigo auditoria
	@EJB
	private ManagerAuditoria mAuditoria;
	
	@EJB
	private ManagerDAO mDAO;

	@EJB
	private ManagerSeguridades mSeg;
	
	
	

	public ManagerCompras() {
	}

	// Inicializadores
	// Ciudad
	public Ciudad inicializarCiudad() {
		Ciudad ciudad = new Ciudad();
		ciudad.setCiuNombre("");
		return ciudad;
	}

	// Proveedor
	public Proveedor inicializarProveedor() {
		Proveedor proveedor = new Proveedor();
		proveedor.setProveRuc("");
		proveedor.setProveNombre("");
		proveedor.setProveNomcomercial("");
		proveedor.setProveDireccion("");
		proveedor.setProveTelefono("");
		proveedor.setCiudad(null);
		return proveedor;
	}

	// Funciones del Administrador de proveedores
	// Listar ciudades
	public List<Ciudad> findAllCiudades() {
		return mDAO.findAll(Ciudad.class);
	}

	// Listar proveedores
	public List<Proveedor> findAllProveedores() {
		return mDAO.findAll(Proveedor.class);
	}

	// Insercion
	// Ciudades
	public void insertarCiudad(LoginDTO loginDTO, Ciudad nuevaCiudad) throws Exception {
		mDAO.insertar(nuevaCiudad); 
		//Nuevo codigo auditoria
		//Forma simple
		// mostrarLog(getClass(), "insertar Ciudad", "Ciudad: "+nuevaCiudad.getProvCiuNombre()+ " agregada con éxito"); Usar reflexion para ingreso automatico
		//Forma compuesta
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarCiudad", "Ciudad: "+nuevaCiudad.getCiuNombre()+ " agregada con éxito");
		
	}

	// Proveedores
	public void insertarProveedor(Proveedor nuevoProveedor, int CiuId) throws Exception {
		Ciudad ciudad = (Ciudad) mDAO.findById(Ciudad.class, CiuId);// buscamos la ciudad
		nuevoProveedor.setCiudad(ciudad);// asignamos al usuario a la tarea
		mDAO.insertar(nuevoProveedor);
	}

	
	
	//prueba git
	
	/*
	 * // TAREAS // Consulta de tareas por proyecto public List<PryTarea>
	 * findTareasByProyecto(int idPryProyecto) { return
	 * mDAO.findWhere(PryTarea.class, "o.pryProyecto.idPryProyecto=" +
	 * idPryProyecto, "o.fechaInicio");
	 * 
	 * }
	 * 
	 * // Inicializar tareas public PryTarea inicializarTarea(PryProyecto proyecto)
	 * { PryTarea tarea = new PryTarea(); tarea.setAvance(0);
	 * tarea.setFechaInicio(proyecto.getFechaInicio());
	 * tarea.setFechaFin(proyecto.getFechaFin()); tarea.setPryProyecto(proyecto); //
	 * relacion one to many(relacion) return tarea; }
	 * 
	 * // Inserccion de tarea public void insertarTarea(PryTarea nuevaTarea, int
	 * idSegUsuario) throws Exception { SegUsuario usuario = (SegUsuario)
	 * mDAO.findById(SegUsuario.class, idSegUsuario);// buscamos el usuario
	 * nuevaTarea.setSegUsuario(usuario);// asignamos al usuario a la tarea
	 * mDAO.insertar(nuevaTarea); }
	 * 
	 * // ROL: ANALISTA // TAREAS // Visualizar tareas asignadas a un determinado
	 * analista public List<PryTarea> findTareasByUsuario(int idSegUsuario) { return
	 * mDAO.findWhere(PryTarea.class, "o.segUsuario.idSegUsuario=" + idSegUsuario,
	 * "o.fechaInicio"); }
	 * 
	 * public void actualizarAvance(PryTarea tarea) throws Exception {
	 * mDAO.actualizar(tarea); // Actualizar avance de proyecto // edicion de
	 * archivo PryProyecto bidirectional
	 * 
	 * // suma de avances del proyecto List<PryTarea> listaTareas =
	 * findTareasByProyecto(tarea.getPryProyecto().getIdPryProyecto()); int suma =
	 * 0; for (PryTarea t : listaTareas) { suma += t.getAvance(); }
	 * 
	 * int promedio = suma / listaTareas.size(); // promedio de avances del proyecto
	 * PryProyecto proyecto = (PryProyecto) mDAO.findById(PryProyecto.class,
	 * tarea.getPryProyecto().getIdPryProyecto()); proyecto.setAvance(promedio); if
	 * (promedio > 0 && promedio < 100) { proyecto.setEstado("D"); } else { if
	 * (promedio == 100) { proyecto.setEstado("F"); } } // actualizar avance del
	 * proyecto mDAO.actualizar(proyecto); }
	 * 
	 */

}
