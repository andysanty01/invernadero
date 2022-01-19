package invernadero.model.ventas.managers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import invernadero.model.auditoria.managers.ManagerAuditoria;
import invernadero.model.core.entities.Cliente;
import invernadero.model.core.entities.FacturaCab;
import invernadero.model.core.entities.FacturaDet;
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

/////////--------------------------CLIENTES---------------------------------------------------------
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
		Cliente cliente = (Cliente) mDAO.findById(Cliente.class, edicionCliente.getCliCedula()); // Buscar el cliente a
																									// editar // editar
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

	// Activar/Desactivar
	public void activarDesactivarCliente(String cliCedula) throws Exception {
		Cliente cliente = (Cliente) mDAO.findById(Cliente.class, cliCedula);
		cliente.setCliEstado(!cliente.getCliEstado());
		System.out.println("activar/desactivar " + cliente.getCliEstado());
		mDAO.actualizar(cliente);
	}

	// Borracion de Clientes
	public void eliminarCliente(String cliCedula) throws Exception {
		Cliente cliente = (Cliente) mDAO.findById(Cliente.class, cliCedula); // Encontrar el cliente a eliminar
		if (cliente.getFacturaCabs().size() > 0)
			throw new Exception("No se puede elimininar el cliente porque tiene proformas registradas.");
		mDAO.eliminar(Cliente.class, cliente.getCliCedula());
		// TODO agregar uso de LoginDTO para auditar metodo.
	}

/////////--------------------PROFORMAS CABECERA---------------------------------------------------------

	// Inicializar proformas cab

	public ProformasCab inicializarProformasCab() {
		ProformasCab proformasCab = new ProformasCab();
		proformasCab.setProCabFecha(new Timestamp(System.currentTimeMillis()));
		proformasCab.setProCabExtension(new BigDecimal(0));
		proformasCab.setProCabSubtotal(new BigDecimal(0));
		proformasCab.setProCabIva(new BigDecimal(0));
		proformasCab.setProCabTotal(new BigDecimal(0));
		return proformasCab;
	}

	// Listar proformas cab
	public List<ProformasCab> findAllProformasCab() {
		return mDAO.findAll(ProformasCab.class);
	}

	// Insercion de Proformas Cab
	public void insertarProformasCab(LoginDTO loginDTO, ProformasCab nuevaProformasCab)
			throws Exception {

		mDAO.insertar(nuevaProformasCab);
		// Nuevo codigo auditoria
		// Forma simple
		// mostrarLog(getClass(), "insertar Cliente", "Cliente:
		// "+nuevaCliente.getProvCiuNombre()+ " agregada con éxito"); Usar reflexion
		// para ingreso automatico
		// Forma compuesta
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarProformasCab",
				"ProformasCab: " + nuevaProformasCab.getProCabId() + " agregada con éxito");
	}

	// Actualizacion de ProformasCab
	public void actualizarProformasCab(LoginDTO loginDTO, ProformasCab edicionProformasCab) throws Exception {
		ProformasCab proformasCab = (ProformasCab) mDAO.findById(ProformasCab.class, edicionProformasCab.getProCabId()); // Buscar el cliente

		proformasCab.setProCabExtension(edicionProformasCab.getProCabExtension());
		mDAO.actualizar(proformasCab);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarProformasCab",
				"se actualizó a ProformasCab " + edicionProformasCab.getProCabId());
	}

	// Borracion de ProformasCab
	public void eliminarProformasCab(int ProformasCabId) throws Exception {
		ProformasCab proformasCab = (ProformasCab) mDAO.findById(ProformasCab.class, ProformasCabId); // Encontrar el
																										// cliente a
																										// eliminar
		if (proformasCab.getProformasDets().size() > 0)
			throw new Exception("No se puede elimininar la proforma porque tiene productos registrados.");
		mDAO.eliminar(ProformasCab.class, proformasCab.getProCabId());
		// TODO agregar uso de LoginDTO para auditar metodo.
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
	
	// Inicializar
	public ProformasDet inicializarProformasDet(ProformasCab proformasCab) {
		ProformasDet proformasDet = new ProformasDet();

		proformasDet.setProformasCab(proformasCab); // Inicializamos con el ID de la proforma Cab
		proformasDet.setProducto(new Producto());
		proformasDet.setProDetCantidad(0);
		proformasDet.setProDetPrecio(new BigDecimal(0));
		proformasDet.setProDetPreciototal(new BigDecimal(0));
		return proformasDet;
	}

	// Insercion de Proformas Cab
	public void insertarProformasDet(LoginDTO loginDTO, ProformasDet nuevaProformasDet,int productoSeleccionado) throws Exception {

		Producto producto = (Producto) mDAO.findById(Producto.class, productoSeleccionado); // Encontrar la proforma
		nuevaProformasDet.setProducto(producto);
		nuevaProformasDet.setProDetPrecio(producto.getProducPreciou());
		nuevaProformasDet.setProDetPreciototal(
				calculoPrecioTotal(producto.getProducPreciou(), nuevaProformasDet.getProDetCantidad()));

		mDAO.insertar(nuevaProformasDet);
		// Forma compuesta
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarProformasDet",	"Detalle: " + nuevaProformasDet.getProDetId() + " agregada con éxito");
		//Actualizar total de proforma cabecera
		calcularTotalProforma(nuevaProformasDet.getProformasCab().getProCabId());
	}
	
	
	//Metodo para actualizar Total de ProformasCabecera
	public void calcularTotalProforma(int proformaId) throws Exception {
		
		//Buscar proforma
    	ProformasCab proformaCab=(ProformasCab) mDAO.findById(ProformasCab.class, proformaId);
    	//Agregar a una lista los detalles de dicha proforma
    	List<ProformasDet> detalles=findDetalleByProforma(proformaId);
    	double suma=0;
    	
    	for(ProformasDet d:detalles) {
    		suma+=d.getProDetPreciototal().doubleValue();
    	}
    	
    	double iva = suma*0.12;
    	double TotalFinal = suma + iva;
    	
    	BigDecimal sumaT = new BigDecimal(suma);
    	BigDecimal ivaT = new BigDecimal(iva);
    	BigDecimal TotalFinalT = new BigDecimal(TotalFinal);
    	System.out.println("suma total:"+sumaT);
    	proformaCab.setProCabSubtotal(sumaT);
    	proformaCab.setProCabIva(ivaT);
    	proformaCab.setProCabTotal(TotalFinalT);
    	
    	mDAO.actualizar(proformaCab);
    }
	
	public BigDecimal calculoPrecioTotal(BigDecimal precioU, int cant) {
		double precio_unitario = precioU.doubleValue();
		double precioTotal = precio_unitario * cant;

		BigDecimal precioT = new BigDecimal(precioTotal);
		return precioT;
	}
	
	
	///////------------------------------ORDENES DE TRABAJO-----------------------------------------------
	
	//Listar Ordenes de trabajo
		public List<OrdenTrabajo> findAllOrdenesTrabajo(){
			return mDAO.findAll(OrdenTrabajo.class);
		}
		
		//Lista de usuarios
		public List<SegUsuario> findAllUsuarios(){
			return mDAO.findAll(SegUsuario.class);
		}
		
	//Inicializar
		public OrdenTrabajo inicializarOrdenTrabajo() {
			OrdenTrabajo ordenTrabajo = new OrdenTrabajo();

			ordenTrabajo.setCliente(new Cliente());
			ordenTrabajo.setProformasCab(new ProformasCab());; // Inicializamos con el ID de la proforma Cab
			ordenTrabajo.setOrdenObservaciones("");
			ordenTrabajo.setOrdenEstadopago("");
			ordenTrabajo.setOrdenFechaini(new Timestamp(System.currentTimeMillis()));
			ordenTrabajo.setOrdenFechafin(new Date());
			ordenTrabajo.setOrdenEstado("SOLICITADO");
			ordenTrabajo.setOrdenAvance(0);
			ordenTrabajo.setOrdenDespacho(false);
			ordenTrabajo.setSegUsuario(new SegUsuario());
			return ordenTrabajo;
		}
		
		
		//Agregar orden de trabajo

		public void insertarOrdenTrabajo(LoginDTO loginDTO, OrdenTrabajo nuevaOrden,int proformaSeleccionada, int usuarioSeleccionado, String clienteSeleccionado) throws Exception {

			SegUsuario usuario = (SegUsuario) mDAO.findById(SegUsuario.class, usuarioSeleccionado);
			ProformasCab proforma = (ProformasCab) mDAO.findById(ProformasCab.class, proformaSeleccionada); // Encontrar la proforma
			Cliente cliente = (Cliente) mDAO.findById(Cliente.class, clienteSeleccionado); // Encontrar la proforma
			
			nuevaOrden.setProformasCab(proforma);
			nuevaOrden.setSegUsuario(usuario);
			nuevaOrden.setCliente(cliente);
			mDAO.insertar(nuevaOrden);
			// Forma compuesta
			mAuditoria.mostrarLog(loginDTO, getClass(), "insertarOrden",	"Orden: " + nuevaOrden.getOrdenId() + " agregada con éxito");
		}
		
		// Actualizacion de Ordenes
		public void actualizarOrden(LoginDTO loginDTO, OrdenTrabajo edicionOrden) throws Exception {
			OrdenTrabajo orden = (OrdenTrabajo) mDAO.findById(OrdenTrabajo.class, edicionOrden.getOrdenId()); // Buscar el cliente
			
			orden.setProformasCab(edicionOrden.getProformasCab());
			orden.setSegUsuario(edicionOrden.getSegUsuario());
			orden.setOrdenObservaciones(edicionOrden.getOrdenObservaciones());
			orden.setCliente(edicionOrden.getCliente());
			orden.setOrdenEstadopago(edicionOrden.getOrdenEstadopago());
			
			
			mDAO.actualizar(orden);
			mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarOrden",
					"se actualizó la orden " + edicionOrden.getOrdenId());
		}
		
		//Eliminar orden
		public void eliminarOrden(int ordenId) throws Exception {
			OrdenTrabajo orden = (OrdenTrabajo) mDAO.findById(OrdenTrabajo.class, ordenId); // Encontrar el cliente a																				// eliminar
			if (orden.getOrdenAvance()>0)
				throw new Exception("No se puede elimininar la orden porque ya empezó su construcción.");
			mDAO.eliminar(OrdenTrabajo.class, orden.getOrdenId());
			// TODO agregar uso de LoginDTO para auditar metodo.
		}
		
		
		
		/////////////////// ------------- FACTURAS-CABECERA----------------------------------------------------------------------
		
		//Inicializar
		public FacturaCab inicializarFacturasCab() {
			FacturaCab facturaCab = new FacturaCab();
			facturaCab.setCliente(new Cliente()); // prueba
			facturaCab.setFacCabFecha(new Timestamp(System.currentTimeMillis()));
			facturaCab.setFacCabSubtotal(new BigDecimal(0));
			facturaCab.setFacCabIva(new BigDecimal(0));
			facturaCab.setFacCabTotal(new BigDecimal(0));
			return facturaCab;
		}

		// Listar FacturaCab
		public List<FacturaCab> findAllFacturasCab() {
			return mDAO.findAll(FacturaCab.class);
		}

		// Insercion de FacturaCab
		public void insertarFacturasCab(LoginDTO loginDTO, FacturaCab nuevaFacturaCab, String clienteSeleccionado)
				throws Exception {

			Cliente cliente = (Cliente) mDAO.findById(Cliente.class, clienteSeleccionado); // Encontrar el cliente
			nuevaFacturaCab.setCliente(cliente);

			mDAO.insertar(nuevaFacturaCab);
			// Nuevo codigo auditoria
			// Forma simple
			// mostrarLog(getClass(), "insertar Cliente", "Cliente:
			// "+nuevaCliente.getProvCiuNombre()+ " agregada con éxito"); Usar reflexion
			// para ingreso automatico
			// Forma compuesta
			mAuditoria.mostrarLog(loginDTO, getClass(), "insertarFacturasCab",
					"FacturaCab: " + nuevaFacturaCab.getFacCabId() + " agregada con éxito");
		}

		// Actualizacion de ProformasCab
		public void actualizarFacturasCab(LoginDTO loginDTO, FacturaCab edicionFacturaCab) throws Exception {
			FacturaCab facturaCab = (FacturaCab) mDAO.findById(FacturaCab.class, edicionFacturaCab.getFacCabId()); // Buscar el cliente

			facturaCab.setCliente(edicionFacturaCab.getCliente());
			mDAO.actualizar(facturaCab);
			mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarFacturasCab",
					"se actualizó a FacturasCab " + edicionFacturaCab.getFacCabId());
		}

		// Borracion de ProformasCab
		public void eliminarFacturasCab(int FacturasCabId) throws Exception {
			FacturaCab facturaCab = (FacturaCab) mDAO.findById(FacturaCab.class, FacturasCabId); // Encontrar el
																											// cliente a
																											// eliminar
			if (facturaCab.getFacturaDets().size() > 0)
				throw new Exception("No se puede elimininar la factura porque tiene productos registrados.");
			mDAO.eliminar(FacturaCab.class, facturaCab.getFacCabId());
			// TODO agregar uso de LoginDTO para auditar metodo.
		}
		
		
		//------------------------------ FACTURAS DETALLE ---------------------------------------------------------------

		//Listar detalles segun proforma
		public List<FacturaDet> findDetalleByFactura(int facturaId){
	    	return mDAO.findWhere(FacturaDet.class, "o.facturaCab.facCabId="+facturaId, "o.facDetId");
	    }
		
		// Inicializar
		public FacturaDet inicializarFacturasDet(FacturaCab facturaCab) {
			FacturaDet facturasDet = new FacturaDet();

			facturasDet.setFacturaCab(facturaCab); // Inicializamos con el ID de la proforma Cab
			facturasDet.setProducto (new Producto());
			facturasDet.setFacDetCantidad(0);
			facturasDet.setFacDetPrecio(new BigDecimal(0));
			facturasDet.setFacDetPreciototal(new BigDecimal(0));
			return facturasDet;
		}

		// Insercion de Facturas Det
		public void insertarFacturasDet(LoginDTO loginDTO, FacturaDet nuevaFacturaDet,int productoSeleccionado) throws Exception {

			Producto producto = (Producto) mDAO.findById(Producto.class, productoSeleccionado); // Encontrar la proforma
			nuevaFacturaDet.setProducto(producto);
			nuevaFacturaDet.setFacDetPrecio(producto.getProducPreciou());
			nuevaFacturaDet.setFacDetPreciototal(
					calculoPrecioTotal(producto.getProducPreciou(), nuevaFacturaDet.getFacDetCantidad()));

			mDAO.insertar(nuevaFacturaDet);
			// Forma compuesta
			mAuditoria.mostrarLog(loginDTO, getClass(), "insertarFacturaDet",	"Detalle: " + nuevaFacturaDet.getFacDetId() + " agregada con éxito");
			//Actualizar total de proforma cabecera
			calcularTotalProforma(nuevaFacturaDet.getFacturaCab().getFacCabId());
		}
		
		
		//Metodo para actualizar Total de ProformasCabecera
		public void calcularTotalFactura(int facturaId) throws Exception {
			
			//Buscar proforma
	    	FacturaCab facturaCab=(FacturaCab) mDAO.findById(FacturaCab.class, facturaId);
	    	//Agregar a una lista los detalles de dicha proforma
	    	List<FacturaDet> detalles=findDetalleByFactura(facturaId);
	    	double suma=0;
	    	for(FacturaDet d:detalles) {
	    		suma+=d.getFacDetPreciototal().doubleValue();
	    	}
	    	double iva = suma*0.12;
	    	double TotalFinal = suma + iva;
	    	
	    	BigDecimal sumaT = new BigDecimal(suma);
	    	BigDecimal ivaT = new BigDecimal(iva);
	    	BigDecimal TotalFinalT = new BigDecimal(TotalFinal);
	    	System.out.println("suma total:"+sumaT);
	    	facturaCab.setFacCabSubtotal(sumaT);
	    	facturaCab.setFacCabIva(ivaT);
	    	facturaCab.setFacCabTotal(TotalFinalT);
	    	
	    	mDAO.actualizar(facturaCab);
	    }
		
		
}
