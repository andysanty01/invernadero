package invernadero.controller.inventario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import invernadero.controller.JSFUtil;
import invernadero.controller.seguridades.BeanSegLogin;
import invernadero.model.core.entities.Cliente;
import invernadero.model.core.entities.FacturaCab;
import invernadero.model.core.entities.FacturaDet;
import invernadero.model.core.entities.OrdenTrabajo;
import invernadero.model.core.entities.Producto;
import invernadero.model.core.entities.ProformasCab;
import invernadero.model.core.entities.ProformasDet;
import invernadero.model.core.entities.Proveedor;
import invernadero.model.core.entities.SegUsuario;
import invernadero.model.inventario.managers.ManagerInventario;
import invernadero.model.seguridades.managers.ManagerSeguridades;
import invernadero.model.ventas.managers.ManagerVentas;

@Named
@SessionScoped
public class BeanInvAdministrador implements Serializable {

	@EJB
	private ManagerSeguridades mSeg;
	@EJB
	private ManagerInventario mInventario;
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

	//Variables de cuentas
	private BigDecimal totalVentas;
	private BigDecimal totalCompras;
	private BigDecimal totalDiferencia;
	
	private int nroVentas;
	private int nroCompras;
	
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanInvAdministrador() {
	}

	@PostConstruct
	public void inicializar() {
		listaClientes = mInventario.findAllClientes();

		listaProformasCab = mInventario.findAllProformasCab();

		listaOrdenes = mInventario.findAllOrdenesTrabajo();
		
		totalVentas=mInventario.calcularVentas();
		nroVentas=mInventario.nroVentas();
		
		totalCompras=mInventario.calcularCompras();
		nroCompras=mInventario.nroCompras();
		
		totalDiferencia= mInventario.calcularDiferencia();

	}

	// ------------------CLIENTES--------------------------------------------------------------------------------------

	// ----------------------------PROFORMAS-CABECERA-----------------------------------------------------------

//----------------------------- PROFORMAS-DETALLE---------------------------------------------------------------------
	// Cargar pagina de ingreso de Proformas Detalle

	public String actionCargarProformasDet(ProformasCab proformaCab) {
		listaProductos = mInventario.findAllProductos();
		proformaCabSeleccionada = proformaCab;
		listaProformasDet = mInventario.findDetalleByProforma(proformaCabSeleccionada.getProCabId());
		return "detallesProforma?faces-redirect=true";
	}

	/// --------------------------ORDENES DE TRABAJO

	// Actualizar orden de trabajo
	public void actionListenerActualizarDespacho(int ordenId) {
		try {
			mInventario.actualizarDespacho(beanSegLogin.getLoginDTO(), ordenId);
			listaOrdenes = mInventario.findAllOrdenesTrabajo();
			JSFUtil.crearMensajeINFO("Orden actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

//------------------------CUENTAS ALEGRES----------------
	public void actionListenerCalcularDiferencia() {
		try {
			totalVentas=mInventario.calcularVentas();
			
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public int getNroVentas() {
		return nroVentas;
	}

	public void setNroVentas(int nroVentas) {
		this.nroVentas = nroVentas;
	}

	public int getNroCompras() {
		return nroCompras;
	}

	public void setNroCompras(int nroCompras) {
		this.nroCompras = nroCompras;
	}

	public List<FacturaCab> getListaFacturasCab() {
		return listaFacturasCab;
	}
	

	public BigDecimal getTotalVentas() {
		return totalVentas;
	}

	public void setTotalVentas(BigDecimal totalVentas) {
		this.totalVentas = totalVentas;
	}

	public BigDecimal getTotalCompras() {
		return totalCompras;
	}

	public void setTotalCompras(BigDecimal totalCompras) {
		this.totalCompras = totalCompras;
	}

	public BigDecimal getTotalDiferencia() {
		return totalDiferencia;
	}

	public void setTotalDiferencia(BigDecimal totalDiferencia) {
		this.totalDiferencia = totalDiferencia;
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
