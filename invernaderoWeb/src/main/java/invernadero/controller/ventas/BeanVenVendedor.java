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
public class BeanVenVendedor implements Serializable {

	@EJB
	private ManagerSeguridades mSeg;
	@EJB
	private ManagerVentas mVentas;
	private List<Cliente> listaClientes;
	private List<ProformasCab> listaProformasCab;
	private List<ProformasDet> listaProformasDet;
	private List<Producto> listaProductos;
	private List<OrdenTrabajo> listaOrdenes;
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
		
		listaOrdenes = mVentas.findAllOrdenesTrabajo();
		nuevaOrden = mVentas.inicializarOrdenTrabajo();
	}

	// ------------------CLIENTES--------------------------------------------------------------------------------------

	// ----------------Inserccion
	// Agregar Cliente
	public void actionListenerInsertarCliente() {
		try {
			mVentas.insertarCliente(beanSegLogin.getLoginDTO(), nuevaCliente);
			;
			JSFUtil.crearMensajeINFO("Cliente agregada con �xito");
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
			mVentas.insertarProformasCab(beanSegLogin.getLoginDTO(), nuevaProformaCab);
			JSFUtil.crearMensajeINFO("Proforma agregada con �xito");
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
		listaProductos = mVentas.findAllProductos();
		proformaCabSeleccionada = proformaCab;
		listaProformasDet = mVentas.findDetalleByProforma(proformaCabSeleccionada.getProCabId());
		nuevaProformaDet = mVentas.inicializarProformasDet(proformaCabSeleccionada);
		return "detallesProforma?faces-redirect=true";
	}

	// Actualizar lista del boton Regresar
	public String cargarPaginaProformas() {
		listaProformasCab = mVentas.findAllProformasCab();
		return "proformas";
	}

	// ----------------Inserccion
	// Agregar

	public void actionListenerInsertarProformaDet() {
		try {
			mVentas.insertarProformasDet(beanSegLogin.getLoginDTO(), nuevaProformaDet, productoSeleccionado);
			JSFUtil.crearMensajeINFO("Detalle agregado agregada con �xito");
			nuevaProformaDet = mVentas.inicializarProformasDet(proformaCabSeleccionada);
			listaProformasDet = mVentas.findDetalleByProforma(proformaCabSeleccionada.getProCabId());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/// --------------------------ORDENES DE TRABAJO

	//Cargamos pagina de nueva orden
	public String actionCargarOrdenes() {
		listaProformasCab = mVentas.findAllProformasCab();
		listaUsuarios = mVentas.findAllUsuarios();
		nuevaOrden = mVentas.inicializarOrdenTrabajo();
		
		return "ordenes_nuevo";
	}
	
	//Insertar orden
	public void actionListenerInsertarOrdenes() {
		try {
			System.out.print(usuarioSeleccionado);
			System.out.print(proformaSeleccionada);
			mVentas.insertarOrdenTrabajo(beanSegLogin.getLoginDTO(), nuevaOrden, proformaSeleccionada, usuarioSeleccionado);
			JSFUtil.crearMensajeINFO("Orden agregada con �xito");
			listaOrdenes= mVentas.findAllOrdenesTrabajo();
			nuevaOrden = mVentas.inicializarOrdenTrabajo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void actionListenerEliminarOrden(int ordenId) {
		try {
			mVentas.eliminarOrden(ordenId);
			listaOrdenes = mVentas.findAllOrdenesTrabajo();
			JSFUtil.crearMensajeINFO("Proforma eliminada.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	

	// ----------------------------FACTURAS-CABECERA-----------------------------------------------------------
		// ----------------Inserccion
		// Agregar
		public void actionListenerInsertarInsertarCab() {
			try {
				mVentas.insertarFacturasCab(beanSegLogin.getLoginDTO(), nuevaProformaCab, clienteSeleccionado);
				JSFUtil.crearMensajeINFO("Proforma agregada con �xito");
				listaProformasCab = mVentas.findAllProformasCab();
				nuevaProformaCab = mVentas.inicializarProformasCab();
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
		}

		// Cargar pagina
		public String actionCargarAgregarFacturasCab() {
			nuevaProformaCab = mVentas.inicializarFacturasCab();
			listaClientes = mVentas.findAllClientes();
			return "facturas_nuevo";

		}

		// -----------------Edicion
		// Actualizar
		public void actionListenerActualizarEdicionFacturasCab() {
			try {
				mVentas.actualizarFacturasCab(beanSegLogin.getLoginDTO(), edicionProformaCab);
				listaProformasCab = mVentas.findAllProformasCab();
				JSFUtil.crearMensajeINFO("Proforma actualizado.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
		}

		// Cargar pagina de Editar
		public String actionSeleccionarEdicionFacturasCab(ProformasCab proformasCab) {
			edicionProformaCab = proformasCab;
			return "facturas_edicion";
		}

		// ---------------- Borracion

		public void actionListenerEliminarFacturasCab(int proformasCabId) {
			try {
				mVentas.eliminarFacturasCab(proformasCabId);
				listaProformasCab = mVentas.findAllProformasCab();
				JSFUtil.crearMensajeINFO("Proforma eliminada.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
		}

	//----------------------------- FACTURAS-DETALLE---------------------------------------------------------------------
		// Cargar pagina de ingreso de Proformas Detalle

		public String actionCargarFacturasDet(ProformasCab proformaCab) {
			listaProductos = mVentas.findAllProductos();
			proformaCabSeleccionada = proformaCab;
			listaProformasDet = mVentas.findDetalleByProforma(proformaCabSeleccionada.getProCabId());
			nuevaProformaDet = mVentas.inicializarProformasDet(proformaCabSeleccionada);
			return "detallesFactura?faces-redirect=true";
		}

		// Actualizar lista del boton Regresar
		public String cargarPaginaFacturas() {
			listaProformasCab = mVentas.findAllProformasCab();
			return "proformas";
		}

		// ----------------Inserccion
		// Agregar

		public void actionListenerInsertarFacturaDet() {
			try {
				mVentas.insertarProformasDet(beanSegLogin.getLoginDTO(), nuevaProformaDet, productoSeleccionado);
				JSFUtil.crearMensajeINFO("Detalle agregado agregada con �xito");
				nuevaProformaDet = mVentas.inicializarProformasDet(proformaCabSeleccionada);
				listaProformasDet = mVentas.findDetalleByProforma(proformaCabSeleccionada.getProCabId());
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
		}
	
	
	
	
	public Cliente getNuevaCliente() {
		return nuevaCliente;
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
