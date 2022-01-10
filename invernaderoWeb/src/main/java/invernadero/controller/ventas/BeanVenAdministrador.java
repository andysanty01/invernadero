package invernadero.controller.ventas;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import invernadero.controller.JSFUtil;
import invernadero.controller.seguridades.BeanSegLogin;
import invernadero.model.core.entities.Cliente;
import invernadero.model.core.entities.Proveedor;
import invernadero.model.core.entities.SegUsuario;
import invernadero.model.seguridades.managers.ManagerSeguridades;
import invernadero.model.ventas.managers.ManagerVentas;

@Named
@SessionScoped
public class BeanVenAdministrador implements Serializable {

	@EJB
	private ManagerSeguridades mSeg;
	@EJB
	private ManagerVentas mVentas;
	private List<Cliente> listaClientes;

	// Variables
	private Cliente nuevaCliente;
	private Cliente edicionCliente;
	
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanVenAdministrador() {
	}

	@PostConstruct
	public void inicializar() {
		listaClientes = mVentas.findAllClientes();
		nuevaCliente = mVentas.inicializarCliente();
	}

	// ----------------Inserccion
	//Agregar Cliente
	public void actionListenerInsertarCliente() {
		try {
			mVentas.insertarCliente(beanSegLogin.getLoginDTO(), nuevaCliente);;
			JSFUtil.crearMensajeINFO("Cliente agregada con éxito");
			listaClientes = mVentas.findAllClientes();
			nuevaCliente = mVentas.inicializarCliente();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	//Cargar pagina de Agregar Clientes
	public String actionCargarAgregarClientes() {
		nuevaCliente = mVentas.inicializarCliente();
		return "clientes_nuevo";

	}
	
	//-----------------Edicion
	//Actualizar Cliente
	public void actionListenerActualizarEdicionCliente() {
		try {
			mVentas.actualizarCliente(beanSegLogin.getLoginDTO(),edicionCliente);
			listaClientes=mVentas.findAllClientes();
			JSFUtil.crearMensajeINFO("Cliente actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	// Cargar pagina de Editar Cliente
	public String actionSeleccionarEdicionCliente(Cliente cliente) {
		edicionCliente=cliente;
		return "clientes_edicion";
	}
	//Activar/Desactivar Cliente
	public void actionListenerActivarDesactivarCliente(String cliCedula) {
		try {
			mVentas.activarDesactivarCliente(cliCedula);
			listaClientes=mVentas.findAllClientes();
			JSFUtil.crearMensajeINFO("Cliente activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	// ---------------- Borracion
	
	public void actionListenerEliminarCliente(String cliCedula) {
		try {
			mVentas.eliminarCliente(cliCedula);
			listaClientes=mVentas.findAllClientes();
			JSFUtil.crearMensajeINFO("Cliente eliminado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	
	// ACCESORES
	
	public Cliente getNuevaCliente() {
		return nuevaCliente;
	}

	public void setNuevaCliente(Cliente nuevaCliente) {
		this.nuevaCliente = nuevaCliente;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public Cliente getEdicionCliente() {
		return edicionCliente;
	}

	public void setEdicionCliente(Cliente edicionCliente) {
		this.edicionCliente = edicionCliente;
	}
	
	
	
}
