package invernadero.controller.construccion;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import invernadero.controller.JSFUtil;
import invernadero.controller.seguridades.BeanSegLogin;
import invernadero.model.construccion.managers.ManagerConstruccion;
import invernadero.model.core.entities.Cliente;
import invernadero.model.core.entities.OrdenTrabajo;
import invernadero.model.core.entities.Producto;
import invernadero.model.core.entities.ProformasCab;
import invernadero.model.core.entities.ProformasDet;
import invernadero.model.core.entities.Proveedor;
import invernadero.model.core.entities.SegUsuario;
import invernadero.model.seguridades.managers.ManagerSeguridades;
import invernadero.model.ventas.managers.ManagerVentas;

@Named
@SessionScoped
public class BeanConConstructor implements Serializable {

	@EJB
	private ManagerSeguridades mSeg;
	@EJB
	private ManagerConstruccion mConstruccion;
	
	private List<Cliente> listaClientes;
	private List<ProformasCab> listaProformasCab;
	private List<ProformasDet> listaProformasDet;
	private List<Producto> listaProductos;
	private List<OrdenTrabajo> listaOrdenes;
	private List<OrdenTrabajo> listaOrdenesTerminadas;
	private List<SegUsuario> listaUsuarios;

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

	// Variables Ordenes Trabajo
	private int proformaSeleccionada;
	private OrdenTrabajo nuevaOrden;
	private OrdenTrabajo edicionOrden;
	private int usuarioSeleccionado;
	
	private int ordenProformaSeleccionada;

	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanConConstructor() {
	}

	@PostConstruct
	public void inicializar() {
		listaClientes = mConstruccion.findAllClientes();

		listaProformasCab = mConstruccion.findAllProformasCab();
		
		listaOrdenes = mConstruccion.findOrdenesByUsuario(beanSegLogin.getIdSegUsuario());
		listaOrdenesTerminadas = mConstruccion.findOrdenesTerminadasByUsuario(beanSegLogin.getIdSegUsuario());
	}

	// ------------------CLIENTES--------------------------------------------------------------------------------------
	
	

	// ----------------------------PROFORMAS-CABECERA-----------------------------------------------------------
//----------------------------- PROFORMAS-DETALLE---------------------------------------------------------------------
	// Cargar pagina de ingreso de Proformas Detalle

	public String actionCargarProformasDet(int proformaCab) {
		
		ordenProformaSeleccionada=proformaCab;
		listaProformasDet = mConstruccion.findDetalleByProforma(ordenProformaSeleccionada);
		return "detalles?faces-redirect=true";
	}

	// Actualizar lista del boton Regresar
	public String cargarPaginaProformas() {
		listaProformasCab = mConstruccion.findAllProformasCab();
		return "proformas";
	}

	// ----------------Inserccion
	// Agregar

	
	//Actualizar Avance
	public void actionListenerActualizarAvance(OrdenTrabajo edicionOrden) {
		try {
			boolean despacho=edicionOrden.getOrdenDespacho();
			if(despacho==false) {
				mConstruccion.actualizarAvance(edicionOrden);
				JSFUtil.crearMensajeINFO("Avance actualizado.");
			}
			else {
				JSFUtil.crearMensajeINFO("La orden aún no ha sido despachada.");
			}
			
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	

	// Clientes
	
	
	
	
	
	public Cliente getNuevaCliente() {
		return nuevaCliente;
	}

	public List<OrdenTrabajo> getListaOrdenesTerminadas() {
		return listaOrdenesTerminadas;
	}

	public void setListaOrdenesTerminadas(List<OrdenTrabajo> listaOrdenesTerminadas) {
		this.listaOrdenesTerminadas = listaOrdenesTerminadas;
	}

	public int getOrdenProformaSeleccionada() {
		return ordenProformaSeleccionada;
	}

	public void setOrdenProformaSeleccionada(int ordenProformaSeleccionada) {
		this.ordenProformaSeleccionada = ordenProformaSeleccionada;
	}

	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public int getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(int usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
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

	public List<OrdenTrabajo> getListaOrdenes() {
		return listaOrdenes;
	}

	public void setListaOrdenes(List<OrdenTrabajo> listaOrdenes) {
		this.listaOrdenes = listaOrdenes;
	}

	public int getProformaSeleccionada() {
		return proformaSeleccionada;
	}

	public void setProformaSeleccionada(int proformaSeleccionada) {
		this.proformaSeleccionada = proformaSeleccionada;
	}

	public OrdenTrabajo getNuevaOrden() {
		return nuevaOrden;
	}

	public void setNuevaOrden(OrdenTrabajo nuevaOrden) {
		this.nuevaOrden = nuevaOrden;
	}

	public OrdenTrabajo getEdicionOrden() {
		return edicionOrden;
	}

	public void setEdicionOrden(OrdenTrabajo edicionOrden) {
		this.edicionOrden = edicionOrden;
	}
	
	
	

}
