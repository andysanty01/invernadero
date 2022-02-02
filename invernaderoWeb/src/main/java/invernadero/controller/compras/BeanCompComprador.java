package invernadero.controller.compras;

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
import invernadero.model.compras.managers.ManagerCompras;
import invernadero.model.core.entities.Ciudad;
import invernadero.model.core.entities.Cliente;
import invernadero.model.core.entities.ComprasCab;
import invernadero.model.core.entities.ComprasDet;
import invernadero.model.core.entities.Producto;
import invernadero.model.core.entities.ProformasCab;
import invernadero.model.core.entities.Proveedor;
import invernadero.model.core.entities.SegUsuario;
import invernadero.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanCompComprador implements Serializable {

	@EJB
	private ManagerSeguridades mSeg;
	@EJB
	private ManagerCompras mCompras;
	private List<Ciudad> listaCiudades;
	private List<Proveedor> listaProveedores;
	private List<Producto> listaProductos;
	private List<ComprasCab> listaComprasCab;
	private List<ComprasDet> listaComprasDet;
	private List<SegUsuario> listaUsuarios;

	// Variables Ciudad
	private Ciudad nuevaCiudad;
	private Ciudad edicionCiudad;

	// Variables Proveedor
	private Proveedor nuevoProveedor;
	private Proveedor edicionProveedor;
	private int ciudadSeleccionado;

	// Variables Producto
	private Producto nuevoProducto;
	private Producto edicionProducto;

	// Variables ComprasCab

	private ComprasCab edicionComprasCab;

	// Variables ComprasDet
	private ComprasCab compraCabSeleccionada;
	private int productoSeleccionado;

	// ComprasDetalle
	private ComprasDet nuevoDetalle;
	private List<ComprasDet> listaDetalle;
	private int productoIngreso;
	private int cantidadIngreso;
	private double precioIngreso;
	private double totalDetalle;
	// ComprasCabecera
	private ComprasCab nuevaCompra;
	private int proveedorIngreso;
	private Date fechaMinima;
	private boolean ivaIngreso;
	private Date fechaIngreso;

	private int idSegUsuarioSeleccionado;
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanCompComprador() {
	}

	@PostConstruct
	public void inicializar() {
		listaCiudades = mCompras.findAllCiudades();
		listaProveedores = mCompras.findAllProveedores();
		listaProductos = mCompras.findAllProductos();
		listaComprasCab = mCompras.findAllComprasCab();

		nuevaCiudad = mCompras.inicializarCiudad();
		nuevoProveedor = mCompras.inicializarProveedor();
		nuevoProducto = mCompras.inicializarProducto();
	}

	// -------------------------------------CIUDADES--------------------------------------
	// Insertar
	public void actionListenerInsertarCiudad() {
		try {
			mCompras.insertarCiudad(beanSegLogin.getLoginDTO(), nuevaCiudad);
			JSFUtil.crearMensajeINFO("Ciudad agregada con éxito");
			listaCiudades = mCompras.findAllCiudades();
			nuevaCiudad = mCompras.inicializarCiudad();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// -----------------------------------PROVEEDORES-------------------------------------------------
	// Insertar
	public void actionListenerInsertarProveedor() {
		try {
			mCompras.insertarProveedor(beanSegLogin.getLoginDTO(), nuevoProveedor, ciudadSeleccionado);
			JSFUtil.crearMensajeINFO("Proveedor creado");
			listaProveedores = mCompras.findAllProveedores();
			nuevoProveedor = mCompras.inicializarProveedor();
			ciudadSeleccionado = 0;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Cargar pagina de Actualizar
	public String actionSeleccionarEdicionCliente(Proveedor proveedor) {
		edicionProveedor = proveedor;
		return "proveedor_edicion";
	}

	// Actualizar
	public void actionListenerActualizarProveedor() {
		try {
			mCompras.actualizarProveedor(beanSegLogin.getLoginDTO(), edicionProveedor);
			listaProveedores = mCompras.findAllProveedores();
			JSFUtil.crearMensajeINFO("Proveedor actualizado.");
			ciudadSeleccionado = 0;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Eliminar
	public void actionListenerEliminarProveedor(int idProveedor) {
		try {
			mCompras.eliminarProveedor(beanSegLogin.getLoginDTO(), idProveedor);
			listaProveedores = mCompras.findAllProveedores();
			JSFUtil.crearMensajeINFO("Proveedor eliminado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// -----------------------------------PRODUCTOS-------------------------------------------------
	// Insertar
	public void actionListenerInsertarProducto() {
		try {
			mCompras.insertarProducto(beanSegLogin.getLoginDTO(), nuevoProducto);
			JSFUtil.crearMensajeINFO("Producto creado");
			listaProductos = mCompras.findAllProductos();
			nuevoProducto = mCompras.inicializarProducto();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Cargar pagina de Actualizar
	public String actionSeleccionarEdicionProducto(Producto producto) {
		edicionProducto = producto;
		return "producto_edicion";
	}

	// Actualizar
	public void actionListenerActualizarProducto() {
		try {
			mCompras.actualizarProducto(beanSegLogin.getLoginDTO(), edicionProducto);
			listaProductos = mCompras.findAllProductos();
			JSFUtil.crearMensajeINFO("Producto actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Eliminar
	public void actionListenerEliminarProducto(int idProducto) {
		try {
			mCompras.eliminarProducto(beanSegLogin.getLoginDTO(), idProducto);
			listaProductos = mCompras.findAllProductos();
			JSFUtil.crearMensajeINFO("Producto eliminado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// ---------------------------COMPRAS MULTIPLE----------------------------

	// Capturar detalles de compra
	public void actionListenerAgregarDetalleCompra() throws Exception {

		double stockDisponible = mCompras.calcularStockInicial(productoIngreso);
		double resta = stockDisponible - cantidadIngreso;

		if (resta > 0) {
			nuevaCompra = mCompras.crearDetalleCompra(nuevaCompra, productoIngreso, cantidadIngreso, precioIngreso);
			totalDetalle = mCompras.ComCabSubtotal(nuevaCompra);
			listaDetalle = nuevaCompra.getComprasDets();
		} else {
			JSFUtil.crearMensajeERROR("No existe suficientes productos");
		}
	}
	
	//Quitar detalle de compra
	public void actionListenerQuitarDetalleCompra(ComprasDet quitarDetalle) throws Exception {
			nuevaCompra = mCompras.quitarDetalleCompra(nuevaCompra, quitarDetalle);
			System.out.println("LISTA: "+nuevaCompra.getComprasDets());
			totalDetalle = mCompras.ComCabSubtotal(nuevaCompra);
			listaDetalle = nuevaCompra.getComprasDets();
	}

	// Guardar compra
	public void actionListenerGuardarCompra(){
		try {
			mCompras.registrarCompra(nuevaCompra, proveedorIngreso, fechaIngreso, totalDetalle, ivaIngreso);
			JSFUtil.crearMensajeINFO("Se guardó la compra exitosamente");
			nuevaCompra = new ComprasCab();
			listaProductos = mCompras.findAllProductos();
			listaComprasCab = mCompras.findAllComprasCab();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		listaDetalle = new ArrayList<ComprasDet>();
		totalDetalle = 0;
		proveedorIngreso=0;
		fechaIngreso = new Date();
		totalDetalle=0;
		
	}
	
	public String actionListarDetalles(ComprasCab compras) {
		compraCabSeleccionada=compras;
		listaComprasDet=mCompras.findDetallesByCompras(compraCabSeleccionada.getComCabId());
		return "detalleCompras?faces-redirect=true";
	}
	
	
	

	// ACCESORES
	
	
	
	

	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}

	public ComprasDet getNuevoDetalle() {
		return nuevoDetalle;
	}

	public void setNuevoDetalle(ComprasDet nuevoDetalle) {
		this.nuevoDetalle = nuevoDetalle;
	}

	public List<ComprasDet> getListaDetalle() {
		return listaDetalle;
	}

	public void setListaDetalle(List<ComprasDet> listaDetalle) {
		this.listaDetalle = listaDetalle;
	}

	public int getProductoIngreso() {
		return productoIngreso;
	}

	public void setProductoIngreso(int productoIngreso) {
		this.productoIngreso = productoIngreso;
	}

	public ComprasCab getNuevaCompra() {
		return nuevaCompra;
	}

	public void setNuevaCompra(ComprasCab nuevaCompra) {
		this.nuevaCompra = nuevaCompra;
	}

	public int getProveedorIngreso() {
		return proveedorIngreso;
	}

	public void setProveedorIngreso(int proveedorIngreso) {
		this.proveedorIngreso = proveedorIngreso;
	}

	public boolean isIvaIngreso() {
		return ivaIngreso;
	}

	public void setIvaIngreso(boolean ivaIngreso) {
		this.ivaIngreso = ivaIngreso;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
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

	public Ciudad getEdicionCiudad() {
		return edicionCiudad;
	}

	public void setEdicionCiudad(Ciudad edicionCiudad) {
		this.edicionCiudad = edicionCiudad;
	}

	public Proveedor getEdicionProveedor() {
		return edicionProveedor;
	}

	public void setEdicionProveedor(Proveedor edicionProveedor) {
		this.edicionProveedor = edicionProveedor;
	}

	public int getCiudadSeleccionado() {
		return ciudadSeleccionado;
	}

	public void setCiudadSeleccionado(int ciudadSeleccionado) {
		this.ciudadSeleccionado = ciudadSeleccionado;
	}

	public Producto getNuevoProducto() {
		return nuevoProducto;
	}

	public void setNuevoProducto(Producto nuevoProducto) {
		this.nuevoProducto = nuevoProducto;
	}

	public Producto getEdicionProducto() {
		return edicionProducto;
	}

	public void setEdicionProducto(Producto edicionProducto) {
		this.edicionProducto = edicionProducto;
	}

	public List<ComprasDet> getListaComprasDet() {
		return listaComprasDet;
	}

	public void setListaComprasDet(List<ComprasDet> listaComprasDet) {
		this.listaComprasDet = listaComprasDet;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public ComprasCab getEdicionComprasCab() {
		return edicionComprasCab;
	}

	public void setEdicionComprasCab(ComprasCab edicionComprasCab) {
		this.edicionComprasCab = edicionComprasCab;
	}

	public Date getFechaMinima() {
		return fechaMinima;
	}

	public void setFechaMinima(Date fechaMinima) {
		this.fechaMinima = fechaMinima;
	}

	public ComprasCab getCompraCabSeleccionada() {
		return compraCabSeleccionada;
	}

	public void setCompraCabSeleccionada(ComprasCab compraCabSeleccionada) {
		this.compraCabSeleccionada = compraCabSeleccionada;
	}

	public int getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(int productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public List<ComprasCab> getListaComprasCab() {
		return listaComprasCab;
	}

	public void setListaComprasCab(List<ComprasCab> listaComprasCab) {
		this.listaComprasCab = listaComprasCab;
	}

	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public List<Proveedor> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<Proveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}

	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Ciudad getNuevaCiudad() {
		return nuevaCiudad;
	}

	public void setNuevaCiudad(Ciudad nuevaCiudad) {
		this.nuevaCiudad = nuevaCiudad;
	}

	public Proveedor getNuevoProveedor() {
		return nuevoProveedor;
	}

	public void setNuevoProveedor(Proveedor nuevoProveedor) {
		this.nuevoProveedor = nuevoProveedor;
	}

	public int getIdSegUsuarioSeleccionado() {
		return idSegUsuarioSeleccionado;
	}

	public void setIdSegUsuarioSeleccionado(int idSegUsuarioSeleccionado) {
		this.idSegUsuarioSeleccionado = idSegUsuarioSeleccionado;
	}

}
