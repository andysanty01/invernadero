package invernadero.controller.ventas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import invernadero.controller.JSFUtil;
import invernadero.controller.seguridades.BeanSegLogin;
import invernadero.model.core.entities.Cliente;
import invernadero.model.core.entities.ComprasCab;
import invernadero.model.core.entities.ComprasDet;
import invernadero.model.core.entities.FacturaCab;
import invernadero.model.core.entities.FacturaDet;
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
	private List<FacturaCab> listaFacturasCab;
	private List<FacturaDet> listaFacturasDet;

	// Variables Cliente
	private Cliente nuevaCliente;
	private Cliente edicionCliente;

	// Variables ProformasCab
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
	
	// Variables FacturasCab
		private String clienteSeleccionado;
		private FacturaCab nuevaFacturaCab;
		private FacturaCab edicionFacturaCab;

		// Variables FacturasDet
		private FacturaCab facturaCabSeleccionada;
		private int productoSeleccionadoF;

		private FacturaDet nuevaFacturaDet;
		private FacturaDet edicionFacturaDet;
		
		// Proformas - Detalle
		// ComprasDetalle
		private ComprasDet nuevoDetalle;
		private List<ProformasDet> listaDetalle;
		private int productoIngreso;
		private int cantidadIngreso;
		private double precioIngreso;
		private double totalDetalle;
		// ComprasCabecera
		private ComprasCab nuevaCompra;
		private double extensionIngreso;

	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanVenVendedor() {
	}

	@PostConstruct
	public void inicializar() {
		listaClientes = mVentas.findAllClientes();
		nuevaCliente = mVentas.inicializarCliente();

		listaProformasCab = mVentas.findAllProformasCab();
		
		listaOrdenes = mVentas.findAllOrdenesTrabajo();
		nuevaOrden = mVentas.inicializarOrdenTrabajo();
		
		listaFacturasCab = mVentas.findAllFacturasCab();
		nuevaFacturaCab = mVentas.inicializarFacturasCab();
		
		listaUsuarios = mVentas.findAllUsuarios();
		
		listaProductos = mVentas.findAllProductos();
	}

	// ------------------CLIENTES--------------------------------------------------------------------------------------

	// ----------------Inserccion
	// Agregar Cliente
	public void actionListenerInsertarCliente() {
		try {
			mVentas.insertarCliente(beanSegLogin.getLoginDTO(), nuevaCliente);
			;
			JSFUtil.crearMensajeINFO("Cliente agregada con ?xito");
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
			JSFUtil.crearMensajeINFO("Proforma agregada con ?xito");
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
			JSFUtil.crearMensajeINFO("Detalle agregado agregada con ?xito");
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
		listaClientes = mVentas.findAllClientes();
		nuevaOrden = mVentas.inicializarOrdenTrabajo();
		
		return "ordenes_nuevo";
	}
	
	//Insertar orden
	public void actionListenerInsertarOrdenes() {
		try {
			System.out.print(usuarioSeleccionado);
			System.out.print(proformaSeleccionada);
			mVentas.insertarOrdenTrabajo(beanSegLogin.getLoginDTO(), nuevaOrden, proformaSeleccionada, usuarioSeleccionado, clienteSeleccionado);
			JSFUtil.crearMensajeINFO("Orden agregada con ?xito");
			listaOrdenes= mVentas.findAllOrdenesTrabajo();
			nuevaOrden = mVentas.inicializarOrdenTrabajo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//Actualizar orden de trabajo
		public void actionListenerActualizarEdicionOrden() {
			try {
				mVentas.actualizarOrden(beanSegLogin.getLoginDTO(), edicionOrden, proformaSeleccionada,usuarioSeleccionado, clienteSeleccionado );
				listaOrdenes = mVentas.findAllOrdenesTrabajo();
				JSFUtil.crearMensajeINFO("Orden actualizado.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
		}

	// Cargar pagina de Editar Cliente
	public String actionSeleccionarEdicionOrden(OrdenTrabajo orden) {
		edicionOrden = orden;
		proformaSeleccionada=0;
		usuarioSeleccionado=0;
		clienteSeleccionado="";
		listaProformasCab=mVentas.findAllProformasCab();
		listaUsuarios=mVentas.findAllUsuarios();
		listaClientes=mVentas.findAllClientes();
		
		return "ordenes_edicion";
	}
	
	
	public String actionListenerEliminarOrden(int ordenId) {
		String pagina="";
		try {
			mVentas.eliminarOrden(ordenId);
			pagina=cargarPaginaOrdenes();
			JSFUtil.crearMensajeINFO("Orden eliminada.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Mensaje prueba");
			e.printStackTrace();
		}
		return pagina;
	}
	
	
	
	public String cargarPaginaOrdenes() {
		listaOrdenes = mVentas.findAllOrdenesTrabajo();
		return "menu";
	}

	// ----------------------------FACTURAS-CABECERA-----------------------------------------------------------
		// ----------------Inserccion
		// 
	
	
		public void actionListenerGenerarFactura(OrdenTrabajo orden) {
			try {
				
				mVentas.insertarFacturasCab(beanSegLogin.getLoginDTO(), orden.getProformasCab().getProCabId(), orden.getCliente().getCliCedula());
				JSFUtil.crearMensajeINFO("Factura agregada con ?xito");
				listaFacturasCab = mVentas.findAllFacturasCab();
				nuevaFacturaCab = mVentas.inicializarFacturasCab();
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
		}

		// Cargar pagina
		public String actionCargarAgregarFacturasCab() {
			nuevaFacturaCab = mVentas.inicializarFacturasCab();
			listaClientes = mVentas.findAllClientes();
			return "facturas_nuevo";

		}

		// -----------------Edicion
		// Actualizar
		public void actionListenerActualizarEdicionFacturasCab() {
			try {
				mVentas.actualizarFacturasCab(beanSegLogin.getLoginDTO(), edicionFacturaCab);
				listaFacturasCab = mVentas.findAllFacturasCab();
				JSFUtil.crearMensajeINFO("Factura actualizada.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
		}

		// Cargar pagina de Editar
		public String actionSeleccionarEdicionFacturasCab(FacturaCab facturaCab) {
			edicionFacturaCab = facturaCab;
			return "facturas_edicion";
		}

		// ---------------- Borracion

		public void actionListenerEliminarFacturasCab(int facturaCabId) {
			try {
				mVentas.eliminarFacturasCab(facturaCabId);
				listaFacturasCab = mVentas.findAllFacturasCab();
				JSFUtil.crearMensajeINFO("Factura eliminada.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
		}

	//----------------------------- FACTURAS-DETALLE---------------------------------------------------------------------
		// Cargar pagina de ingreso de Facturas Detalle

		public String actionCargarFacturasDet(FacturaCab facturaCab) {
			listaProductos = mVentas.findAllProductos();
			facturaCabSeleccionada = facturaCab;
			listaFacturasDet = mVentas.findDetalleByFactura(facturaCabSeleccionada.getFacCabId());
			nuevaFacturaDet = mVentas.inicializarFacturasDet(facturaCabSeleccionada);
			return "detallesFactura?faces-redirect=true";
		}
		

		// Actualizar lista del boton Regresar
		public String cargarPaginaFacturas() {
			listaFacturasCab = mVentas.findAllFacturasCab();
			return "proformas";
		}

		// ----------------Inserccion
		// Agregar

		public void actionListenerInsertarFacturaDet() {
			try {
				mVentas.insertarFacturasDet(beanSegLogin.getLoginDTO(), nuevaFacturaDet, productoSeleccionadoF);
				JSFUtil.crearMensajeINFO("Detalle agregada con ?xito");
				nuevaFacturaDet = mVentas.inicializarFacturasDet(facturaCabSeleccionada);
				listaFacturasDet = mVentas.findDetalleByFactura(facturaCabSeleccionada.getFacCabId());
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
		}
	
	// --------------------------- PROFORMAS  MULITPLE
		// Capturar detalles de compra
		public void actionListenerAgregarDetalleProforma() throws Exception {

			double stockDisponible = mVentas.calcularStockInicial(productoIngreso);
			double resta = stockDisponible - cantidadIngreso;

			if (resta >= 0) {
				nuevaProformaCab = mVentas.crearDetalleProforma(nuevaProformaCab, productoIngreso, cantidadIngreso);
				totalDetalle = mVentas.ProCabSubtotal(nuevaProformaCab);
				listaDetalle = nuevaProformaCab.getProformasDets();
				JSFUtil.crearMensajeINFO("Se agreg? un producto");
			} else {
				JSFUtil.crearMensajeERROR("No existe suficientes productos");
			}
			
		}

		// Guardar compra
		public void actionListenerGuardarProforma(){
			try {
				mVentas.registrarProforma(nuevaProformaCab, extensionIngreso, totalDetalle);
				JSFUtil.crearMensajeINFO("Se guard? la compra exitosamente");
				nuevaCompra = new ComprasCab();
				listaProductos = mVentas.findAllProductos();
				listaProformasCab = mVentas.findAllProformasCab();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			listaDetalle = new ArrayList<ProformasDet>();
			totalDetalle = 0;
			
		}
		
		public String actionListarDetalles(ProformasCab proforma) {
			proformaCabSeleccionada=proforma;
			listaProformasDet=mVentas.findDetalleByProforma(proformaCabSeleccionada.getProCabId());
			return "detallesProforma?faces-redirect=true";
		}
	
		
	
	
		
		
	public List<ProformasDet> getListaDetalle() {
			return listaDetalle;
		}

		public void setListaDetalle(List<ProformasDet> listaDetalle) {
			this.listaDetalle = listaDetalle;
		}

		public int getProductoIngreso() {
			return productoIngreso;
		}

		public void setProductoIngreso(int productoIngreso) {
			this.productoIngreso = productoIngreso;
		}

		public int getCantidadIngreso() {
			return cantidadIngreso;
		}

		public void setCantidadIngreso(int cantidadIngreso) {
			this.cantidadIngreso = cantidadIngreso;
		}

		public double getPrecioIngreso() {
			return precioIngreso;
		}

		public void setPrecioIngreso(double precioIngreso) {
			this.precioIngreso = precioIngreso;
		}

		public double getTotalDetalle() {
			return totalDetalle;
		}

		public void setTotalDetalle(double totalDetalle) {
			this.totalDetalle = totalDetalle;
		}

		public double getExtensionIngreso() {
			return extensionIngreso;
		}

		public void setExtensionIngreso(double extensionIngreso) {
			this.extensionIngreso = extensionIngreso;
		}

	public List<FacturaCab> getListaFacturasCab() {
			return listaFacturasCab;
		}

		public void setListaFacturasCab(List<FacturaCab> listaFacturasCab) {
			this.listaFacturasCab = listaFacturasCab;
		}

		public List<FacturaDet> getListaFacturasDet() {
			return listaFacturasDet;
		}

		public void setListaFacturasDet(List<FacturaDet> listaFacturasDet) {
			this.listaFacturasDet = listaFacturasDet;
		}

		public FacturaCab getNuevaFacturaCab() {
			return nuevaFacturaCab;
		}

		public void setNuevaFacturaCab(FacturaCab nuevaFacturaCab) {
			this.nuevaFacturaCab = nuevaFacturaCab;
		}

		public FacturaCab getEdicionFacturaCab() {
			return edicionFacturaCab;
		}

		public void setEdicionFacturaCab(FacturaCab edicionFacturaCab) {
			this.edicionFacturaCab = edicionFacturaCab;
		}

		public FacturaCab getFacturaCabSeleccionada() {
			return facturaCabSeleccionada;
		}

		public void setFacturaCabSeleccionada(FacturaCab facturaCabSeleccionada) {
			this.facturaCabSeleccionada = facturaCabSeleccionada;
		}

		public int getProductoSeleccionadoF() {
			return productoSeleccionadoF;
		}

		public void setProductoSeleccionadoF(int productoSeleccionadoF) {
			this.productoSeleccionadoF = productoSeleccionadoF;
		}

		public FacturaDet getNuevaFacturaDet() {
			return nuevaFacturaDet;
		}

		public void setNuevaFacturaDet(FacturaDet nuevaFacturaDet) {
			this.nuevaFacturaDet = nuevaFacturaDet;
		}

		public FacturaDet getEdicionFacturaDet() {
			return edicionFacturaDet;
		}

		public void setEdicionFacturaDet(FacturaDet edicionFacturaDet) {
			this.edicionFacturaDet = edicionFacturaDet;
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
