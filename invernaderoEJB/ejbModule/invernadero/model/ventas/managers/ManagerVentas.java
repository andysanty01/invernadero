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
		if (cliente.getProformasCabs().size() > 0)
			throw new Exception("No se puede elimininar el cliente porque tiene proformas registradas.");
		mDAO.eliminar(Cliente.class, cliente.getCliCedula());
		// TODO agregar uso de LoginDTO para auditar metodo.
	}

/////////--------------------PROFORMAS CABECERA---------------------------------------------------------

	// Inicializar proformas cab

	public ProformasCab inicializarProformasCab() {
		ProformasCab proformasCab = new ProformasCab();
		proformasCab.setCliente(new Cliente()); // prueba
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
	public void insertarProformasCab(LoginDTO loginDTO, ProformasCab nuevaProformasCab, String clienteSeleccionado)
			throws Exception {

		Cliente cliente = (Cliente) mDAO.findById(Cliente.class, clienteSeleccionado); // Encontrar el cliente
		nuevaProformasCab.setCliente(cliente);

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

		proformasCab.setCliente(edicionProformasCab.getCliente());
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
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarProformasDet",
				"Detalle: " + nuevaProformasDet.getProDetId() + " agregada con éxito");
	}

	public BigDecimal calculoPrecioTotal(BigDecimal precioU, int cant) {
		double precio_unitario = precioU.doubleValue();
		double precioTotal = precio_unitario * cant;

		BigDecimal precioT = new BigDecimal(precioTotal);
		return precioT;
	}
	
	

}
