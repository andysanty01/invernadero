package invernadero.controller.compras;

import java.io.Serializable;
import java.sql.Date;
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
	private ComprasCab nuevaComprasCab;
	private ComprasCab edicionComprasCab;
	private int proveedorSeleccionado;
	private Date fechaMinima;

	// Variables ComprasDet
	private ComprasCab compraCabSeleccionada;
	private ComprasDet nuevaComprasDet;
	private int productoSeleccionado;

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
		nuevaComprasCab = mCompras.inicializarComprasCab();

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
	
	
	// --------------------------------COMPRAS-CABECERA----------------------------------------------------
	// Insertar
	public void actionListenerInsertarComprasCab() {
		try {
			mCompras.insertarComprasCab(beanSegLogin.getLoginDTO(), nuevaComprasCab, proveedorSeleccionado);
			JSFUtil.crearMensajeINFO("Compra agregada con éxito");
			listaComprasCab = mCompras.findAllComprasCab();
			nuevaComprasCab = mCompras.inicializarComprasCab();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Cargar pagina de Actualizar
	public String actionSeleccionarEdicionComprasCab(ComprasCab comprasCab) {
		edicionComprasCab = comprasCab;
		return "comprascab_edicion";
	}

	// Actualizar
	public void actionListenerActualizarComprasCab() {
		try {
			mCompras.actualizarComprasCab(beanSegLogin.getLoginDTO(), edicionComprasCab);
			listaComprasCab = mCompras.findAllComprasCab();
			JSFUtil.crearMensajeINFO("Compra actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Eliminar
	public void actionListenerEliminarComprasCab(int idComprasCab) {
		try {
			mCompras.eliminarComprasCab(beanSegLogin.getLoginDTO(), idComprasCab);
			listaComprasCab = mCompras.findAllComprasCab();
			JSFUtil.crearMensajeINFO("Compra eliminado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// ------------------------------COMPRAS-DETALLE----------------------------------------------

	// Cargar pagina detalles
	public String actionCargarComprasDet(ComprasCab comprasCab) {
		listaProductos = mCompras.findAllProductos();
		compraCabSeleccionada = comprasCab;
		listaComprasDet = mCompras.findDetalleByCompra(compraCabSeleccionada.getComCabId());
		nuevaComprasDet = mCompras.inicializarComprasDet(compraCabSeleccionada);
		return "detalleCompras?faces-redirect=true";
	}

	// Actualizar lista del boton Regresar
	public String cargarPaginaCompras() {
		listaComprasCab = mCompras.findAllComprasCab();
		return "menu";
	}

	// Insertar
	public void actionListenerInsertarComprasDet() {
		try {
			mCompras.insertarComprasDet(beanSegLogin.getLoginDTO(), nuevaComprasDet, productoSeleccionado);
			JSFUtil.crearMensajeINFO("Detalle agregado con éxito");
			nuevaComprasDet = mCompras.inicializarComprasDet(compraCabSeleccionada);
			listaComprasDet = mCompras.findDetalleByCompra(compraCabSeleccionada.getComCabId());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// ACCESORES

	
	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
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

	public ComprasDet getNuevaComprasDet() {
		return nuevaComprasDet;
	}

	public void setNuevaComprasDet(ComprasDet nuevaComprasDet) {
		this.nuevaComprasDet = nuevaComprasDet;
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

	public ComprasCab getNuevaComprasCab() {
		return nuevaComprasCab;
	}

	public void setNuevaComprasCab(ComprasCab nuevaComprasCab) {
		this.nuevaComprasCab = nuevaComprasCab;
	}

	public int getProveedorSeleccionado() {
		return proveedorSeleccionado;
	}

	public void setProveedorSeleccionado(int proveedorSeleccionado) {
		this.proveedorSeleccionado = proveedorSeleccionado;
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
