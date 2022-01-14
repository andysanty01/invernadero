package invernadero.model.ventas.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import invernadero.model.auditoria.managers.ManagerAuditoria;
import invernadero.model.core.entities.Cliente;
import invernadero.model.core.entities.ProformasCab;
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
public class ManagerVentas {

	// Nuevo codigo auditoria
	@EJB
	private ManagerAuditoria mAuditoria;

	@EJB
	private ManagerDAO mDAO;

	@EJB
	private ManagerSeguridades mSeg;

	public ManagerVentas() {
	}

	// Inicializadores
	// Cliente
	public Cliente inicializarCliente() {
		Cliente cliente = new Cliente();
		cliente.setCliCedula("");
		cliente.setCliApellido("");
		cliente.setCliNombre("");
		cliente.setCliCorreo("");
		cliente.setCliDireccion("");
		cliente.setCliTelefono("");
		cliente.setCliCategoria("");
		cliente.setCliEstado(true);
		return cliente;
	}

	// Funciones del Administrador de proveedores
	// Listar clientes
	public List<Cliente> findAllClientes() {
		return mDAO.findAll(Cliente.class);
	}

	// Insercion de Clientes
	public void insertarCliente(LoginDTO loginDTO, Cliente nuevaCliente) throws Exception {
		mDAO.insertar(nuevaCliente);
		// Nuevo codigo auditoria
		// Forma simple
		// mostrarLog(getClass(), "insertar Cliente", "Cliente:
		// "+nuevaCliente.getProvCiuNombre()+ " agregada con éxito"); Usar reflexion
		// para ingreso automatico
		// Forma compuesta
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarCliente", "Cliente: " + nuevaCliente.getCliApellido() + " "
				+ nuevaCliente.getCliNombre() + " agregada con éxito");
	}

	// Actualizacion de Clientes
	public void actualizarCliente(LoginDTO loginDTO, Cliente edicionCliente) throws Exception {
		Cliente cliente = (Cliente) mDAO.findById(Cliente.class, edicionCliente.getCliCedula()); // Buscar el cliente a	editar																					// editar
		cliente.setCliApellido(edicionCliente.getCliApellido());
		cliente.setCliNombre(edicionCliente.getCliNombre());
		cliente.setCliCorreo(edicionCliente.getCliCorreo());
		cliente.setCliTelefono(edicionCliente.getCliTelefono());
		cliente.setCliDireccion(edicionCliente.getCliDireccion());
		cliente.setCliCategoria(edicionCliente.getCliCategoria());
		mDAO.actualizar(cliente);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarCliente",
				"se actualizó al cliente " + edicionCliente.getCliApellido() + " " + edicionCliente.getCliNombre());
	}
	//Activar/Desactivar
    public void activarDesactivarCliente(String cliCedula) throws Exception {
    	Cliente cliente=(Cliente) mDAO.findById(Cliente.class, cliCedula);
    	cliente.setCliEstado(!cliente.getCliEstado());
    	System.out.println("activar/desactivar "+cliente.getCliEstado());
    	mDAO.actualizar(cliente);
    }
    

	// Borracion de Clientes
	public void eliminarCliente(String cliCedula) throws Exception {
		Cliente cliente = (Cliente) mDAO.findById(Cliente.class, cliCedula); //Encontrar el cliente a eliminar
		if (cliente.getProformasCabs().size()>0)
			throw new Exception("No se puede elimininar el cliente porque tiene proformas registradas.");
		mDAO.eliminar(Cliente.class, cliente.getCliCedula());
		// TODO agregar uso de LoginDTO para auditar metodo.
	}


//// Proforma Cabecera
	
	public ProformasCab inicializarProformasCab() {
		ProformasCab proformascab = new ProformasCab();
		proformascab.setProCabExtension(null);
		proformascab.setProCabIva(null);;
		proformascab.setProCabSubtotal(null);
		proformascab.setProCabTotal(null);
		
		return proformascab;
	}
	
	
	
}
