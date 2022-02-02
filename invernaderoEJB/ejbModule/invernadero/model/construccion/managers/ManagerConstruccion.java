package invernadero.model.construccion.managers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import invernadero.model.auditoria.managers.ManagerAuditoria;
import invernadero.model.core.entities.Cliente;
import invernadero.model.core.entities.OrdenTrabajo;
import invernadero.model.core.entities.Producto;
import invernadero.model.core.entities.ProformasCab;
import invernadero.model.core.entities.ProformasDet;
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
public class ManagerConstruccion {

	// Nuevo codigo auditoria
	@EJB
	private ManagerAuditoria mAuditoria;

	@EJB
	private ManagerDAO mDAO;

	@EJB
	private ManagerSeguridades mSeg;

	public ManagerConstruccion() {
	}

/////////--------------------------CLIENTES---------------------------------------------------------

	// Funciones del Administrador de proveedores
	// Listar clientes
	public List<Cliente> findAllClientes() {
		return mDAO.findAll(Cliente.class);
	}


/////////--------------------PROFORMAS CABECERA---------------------------------------------------------

	// Inicializar proformas cab

	// Listar proformas cab
	public List<ProformasCab> findAllProformasCab() {
		return mDAO.findAll(ProformasCab.class);
	}

	

	//--------------------------------------PRODUCTOS-----------------------------------
	
	//Listar productos
	public List<Producto> findAllProductos(){
		return mDAO.findAll(Producto.class);
	}
	
	// -------------------------------------PROFORMAS-DETALLE---------------------------


	//Listar detalles segun proforma
	public List<ProformasDet> findDetalleByProforma(int proformaId){
    	return mDAO.findWhere(ProformasDet.class, "o.proformasCab.proCabId="+proformaId, "o.proDetId");
    }
	
	
	///////------------------------------ORDENES DE TRABAJO-----------------------------------------------
	
	//Listar Ordenes de trabajo
		public List<OrdenTrabajo> findAllOrdenesTrabajo(){
			return mDAO.findAll(OrdenTrabajo.class);
		}
		
		 public List<OrdenTrabajo> findOrdenesByUsuario(int idSegUsuario){
		    	return mDAO.findWhere(OrdenTrabajo.class, "o.ordenEstado<>'TERMINADO' AND o.segUsuario.idSegUsuario="+idSegUsuario, "o.ordenAvance");
		    }
		 
		 public List<OrdenTrabajo> findOrdenesTerminadasByUsuario(int idSegUsuario){
		    	return mDAO.findWhere(OrdenTrabajo.class, "o.ordenEstado='TERMINADO' AND o.segUsuario.idSegUsuario="+idSegUsuario, "o.ordenAvance");
		    }
		
		
		//Actualizar avance
		public void actualizarAvance(OrdenTrabajo edicionOrden) throws Exception {
			
			OrdenTrabajo orden = (OrdenTrabajo) mDAO.findById(OrdenTrabajo.class, edicionOrden.getOrdenId()); // Buscar el cliente
			
	    	if(edicionOrden.getOrdenAvance()<0 || orden.getOrdenAvance()>100)
	    		throw new Exception("Avance incorrecto.");
	    	
	    	if(edicionOrden.getOrdenAvance() ==100) {
	    		orden.setOrdenAvance(edicionOrden.getOrdenAvance());
	    		orden.setOrdenEstado("TERMINADO");
	    		mDAO.actualizar(orden);
	    	}else {
	    		if(edicionOrden.getOrdenAvance()<100 && edicionOrden.getOrdenAvance()>0) {
	    			orden.setOrdenAvance(edicionOrden.getOrdenAvance());
	    			orden.setOrdenEstado("EN CONSTRUCCION");
		    		mDAO.actualizar(orden);
	    		}
	    	}
	    	
	    }
		
}
