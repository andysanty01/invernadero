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
import invernadero.model.core.entities.Producto;
import invernadero.model.core.entities.ProformasCab;
import invernadero.model.core.entities.ProformasDet;
import invernadero.model.core.entities.Proveedor;
import invernadero.model.core.entities.SegUsuario;
import invernadero.model.seguridades.managers.ManagerSeguridades;
import invernadero.model.ventas.managers.ManagerVentas;

@Named
@SessionScoped
public class BeanVenVendedor implements Serializable {

	@EJB
	private ManagerSeguridades mSeg;
	@EJB
	private ManagerVentas mVentas;
	private List<Cliente> listaClientes;
	private List<ProformasCab> listaProformasCab;
	private List<ProformasDet> listaProformasDet;
	private List<Producto> listaProductos;

	// Variables Cliente
	private Cliente nuevaCliente;
	private Cliente edicionCliente;

	// Variables ProformasCab
	private String clienteSeleccionado;
	private ProformasCab nuevaProformaCab;
	private ProformasCab edicionProformaCab;

	// Variables ProformasDet
	private ProformasCab proformaCabSeleccionada;
	private int productoSeleccionado;
	
	private ProformasDet nuevaProformaDet;
	private ProformasDet edicionProformaDet;

	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanVenVendedor() {
	}

	@PostConstruct
	public void inicializar() {
		listaClientes = mVentas.findAllClientes();
		nuevaCliente = mVentas.inicializarCliente();

		listaProformasCab = mVentas.findAllProformasCab();
		nuevaProformaCab = mVentas.inicializarProformasCab();
	}

	// ------------------CLIENTES--------------------------------------------------------------------------------------

	// ----------------Inserccion
	// Agregar Cliente
	public void actionListenerInsertarCliente() {
		try {
			mVentas.insertarCliente(beanSegLogin.getLoginDTO(), nuevaCliente);
			;
			JSFUtil.crearMensajeINFO("Cliente agregada con éxito");
			listaClientes = mVentas.findAllClientes();
			nuevaCliente = mVentas.inicializarCliente();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Cargar pagina de Agregar Clientes
	public String actionCargarAgregarClientes() {
		nuevaCliente = mVentas.inicializarCliente();
		return "clientes_nuevo";

	}

	// -----------------Edicion
	// Actualizar Cliente
	public void actionListenerActualizarEdicionCliente() {
		try {
			mVentas.actualizarCliente(beanSegLogin.getLoginDTO(), edicionCliente);
			listaClientes = mVentas.findAllClientes();
			JSFUtil.crearMensajeINFO("Cliente actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Cargar pagina de Editar Cliente
	public String actionSeleccionarEdicionCliente(Cliente cliente) {
		edicionCliente = cliente;
		return "clientes_edicion";
	}

	// Activar/Desactivar Cliente
	public void actionListenerActivarDesactivarCliente(String cliCedula) {
		try {
			mVentas.activarDesactivarCliente(cliCedula);
			listaClientes = mVentas.findAllClientes();
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
			listaClientes = mVentas.findAllClientes();
			JSFUtil.crearMensajeINFO("Cliente eliminado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// ----------------------------PROFORMAS-CABECERA-----------------------------------------------------------
	// ----------------Inserccion
	// Agregar
	public void actionListenerInsertarProformaCab() {
		try {
			mVentas.insertarProformasCab(beanSegLogin.getLoginDTO(), nuevaProformaCab, clienteSeleccionado);
			JSFUtil.crearMensajeINFO("Proforma agregada con éxito");
			listaProformasCab = mVentas.findAllProformasCab();
			nuevaProformaCab = mVentas.inicializarProformasCab();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Cargar pagina
	public String actionCargarAgregarProformasCab() {
		nuevaProformaCab = mVentas.inicializarProformasCab();
		listaClientes = mVentas.findAllClientes();
		return "proformas_nuevo";

	}

	// -----------------Edicion
	// Actualizar
	public void actionListenerActualizarEdicionProformasCab() {
		try {
			mVentas.actualizarProformasCab(beanSegLogin.getLoginDTO(), edicionProformaCab);
			listaProformasCab = mVentas.findAllProformasCab();
			JSFUtil.crearMensajeINFO("Proforma actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Cargar pagina de Editar
	public String actionSeleccionarEdicionProformasCab(ProformasCab proformasCab) {
		edicionProformaCab = proformasCab;
		return "proformasCab_edicion";
	}

	// ---------------- Borracion

	public void actionListenerEliminarProformasCab(int proformasCabId) {
		try {
			mVentas.eliminarProformasCab(proformasCabId);
			listaProformasCab = mVentas.findAllProformasCab();
			JSFUtil.crearMensajeINFO("Proforma eliminada.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

//----------------------------- PROFORMAS-DETALLE---------------------------------------------------------------------
	// Cargar pagina de ingreso de Proformas Detalle
	
		public String actionCargarProformasDet(ProformasCab proformaCab) {
			listaProductos=mVentas.findAllProductos();
			proformaCabSeleccionada=proformaCab;
			listaProformasDet=mVentas.findDetalleByProforma(proformaCabSeleccionada.getProCabId());
			nuevaProformaDet=mVentas.inicializarProformasDet(proformaCabSeleccionada);
			return "detalles?faces-redirect=true";
		}
		
	// ----------------Inserccion
	// Agregar
	public void actionListenerInsertarProformaDet() {
		try {
			mVentas.insertarProformasDet(beanSegLogin.getLoginDTO(), nuevaProformaDet,productoSeleccionado);
			JSFUtil.crearMensajeINFO("Detalle agregado agregada con éxito");
			nuevaProformaDet=mVentas.inicializarProformasDet(proformaCabSeleccionada);
			listaProformasDet=mVentas.findDetalleByProforma(proformaCabSeleccionada.getProCabId());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	

	// ACCESORES

	// Clientes
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

	// ProformasCab

	public List<ProformasCab> getListaProformasCab() {
		return listaProformasCab;
	}

	public void setListaProformasCab(List<ProformasCab> listaProformasCab) {
		this.listaProformasCab = listaProformasCab;
	}

	public ProformasCab getNuevaProformaCab() {
		return nuevaProformaCab;
	}

	public void setNuevaProformaCab(ProformasCab nuevaProformaCab) {
		this.nuevaProformaCab = nuevaProformaCab;
	}

	public ProformasCab getEdicionProformaCab() {
		return edicionProformaCab;
	}

	public void setEdicionProformaCab(ProformasCab edicionProformaCab) {
		this.edicionProformaCab = edicionProformaCab;
	}

	public String getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public void setClienteSeleccionado(String clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}

	public List<ProformasDet> getListaProformasDet() {
		return listaProformasDet;
	}

	public void setListaProformasDet(List<ProformasDet> listaProformasDet) {
		this.listaProformasDet = listaProformasDet;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public ProformasCab getProformaCabSeleccionada() {
		return proformaCabSeleccionada;
	}

	public void setProformaCabSeleccionada(ProformasCab proformaCabSeleccionada) {
		this.proformaCabSeleccionada = proformaCabSeleccionada;
	}

	public int getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(int productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public ProformasDet getNuevaProformaDet() {
		return nuevaProformaDet;
	}

	public void setNuevaProformaDet(ProformasDet nuevaProformaDet) {
		this.nuevaProformaDet = nuevaProformaDet;
	}

	public ProformasDet getEdicionProformaDet() {
		return edicionProformaDet;
	}

	public void setEdicionProformaDet(ProformasDet edicionProformaDet) {
		this.edicionProformaDet = edicionProformaDet;
	}
	
	

}
