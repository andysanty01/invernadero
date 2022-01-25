package invernadero.model.compras.managers;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import invernadero.model.auditoria.managers.ManagerAuditoria;
import invernadero.model.core.entities.Ciudad;
import invernadero.model.core.entities.Cliente;
import invernadero.model.core.entities.ComprasCab;
import invernadero.model.core.entities.ComprasDet;
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
public class ManagerCompras {

	@EJB
	private ManagerAuditoria mAuditoria;

	@EJB
	private ManagerDAO mDAO;

	@EJB
	private ManagerSeguridades mSeg;

	public ManagerCompras() {
	}

	// ---------------------------------------------CIUDADES------------------------------------------------
	// Inicializar
	public Ciudad inicializarCiudad() {
		Ciudad ciudad = new Ciudad();
		ciudad.setCiuNombre("");
		return ciudad;
	}

	// Listar
	public List<Ciudad> findAllCiudades() {
		return mDAO.findAll(Ciudad.class);
	}

	// Insertar
	public void insertarCiudad(LoginDTO loginDTO, Ciudad nuevaCiudad) throws Exception {
		mDAO.insertar(nuevaCiudad);
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarCiudad",
				"Ciudad: " + nuevaCiudad.getCiuNombre() + " agregada con éxito");
	}

	// Actualizar
	public void actualizarCiudad(LoginDTO loginDTO, Ciudad edicionCiudad) throws Exception {
		Ciudad ciudad = (Ciudad) mDAO.findById(Ciudad.class, edicionCiudad.getCiuId());

		ciudad.setCiuNombre(edicionCiudad.getCiuNombre());
		mDAO.actualizar(ciudad);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarCiudad",
				"se actualizó la ciudad " + edicionCiudad.getCiuNombre());
	}

	// Eliminar
	public void eliminarCiudad(LoginDTO loginDTO, int idCiudad) throws Exception {
		Ciudad ciudad = (Ciudad) mDAO.findById(Ciudad.class, idCiudad);
		if (ciudad.getProveedors().size() > 0)
			throw new Exception("No se puede elimininar la ciudad porque tiene proveedores registrados.");
		mDAO.eliminar(Ciudad.class, ciudad.getCiuId());
		mAuditoria.mostrarLog(loginDTO, getClass(), "eliminarCiudad", "se eliminó la ciudad " + ciudad.getCiuId());
	}

	// ---------------------------------------------PROVEEDORES------------------------------------------------
	// Inicializar
	public Proveedor inicializarProveedor() {
		Proveedor proveedor = new Proveedor();
		proveedor.setProveRuc("");
		proveedor.setProveNombre("");
		proveedor.setProveNomcomercial("");
		proveedor.setProveDireccion("");
		proveedor.setProveTelefono("");
		proveedor.setCiudad(new Ciudad());
		return proveedor;
	}

	// Listar
	public List<Proveedor> findAllProveedores() {
		return mDAO.findAll(Proveedor.class);
	}

	// Insertar
	public void insertarProveedor(LoginDTO loginDTO, Proveedor nuevoProveedor, int CiuId) throws Exception {
		Ciudad ciudad = (Ciudad) mDAO.findById(Ciudad.class, CiuId);
		nuevoProveedor.setCiudad(ciudad);
		mDAO.insertar(nuevoProveedor);
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarProveedor",
				"Proveedor: " + nuevoProveedor.getProveId() + " agregado con éxito");
	}

	// Actualizar
	public void actualizarProveedor(LoginDTO loginDTO, Proveedor edicionProveedor) throws Exception {
		Proveedor proveedor = (Proveedor) mDAO.findById(Proveedor.class, edicionProveedor.getProveId());
		Ciudad ciudad = (Ciudad) mDAO.findById(Ciudad.class, edicionProveedor.getCiudad().getCiuId());
		proveedor.setProveRuc(edicionProveedor.getProveRuc());
		proveedor.setProveNombre(edicionProveedor.getProveNombre());
		proveedor.setProveNomcomercial(edicionProveedor.getProveNomcomercial());
		proveedor.setProveDireccion(edicionProveedor.getProveDireccion());
		proveedor.setProveTelefono(edicionProveedor.getProveTelefono());
		proveedor.setCiudad(ciudad);
		mDAO.actualizar(ciudad);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarProveedor",
				"se actualizó al proveedor " + edicionProveedor.getProveId());
	}

	// Eliminar
	public void eliminarProveedor(LoginDTO loginDTO, int idProveedor) throws Exception {
		Proveedor proveedor = (Proveedor) mDAO.findById(Proveedor.class, idProveedor);
		if (proveedor.getComprasCabs().size() > 0)
			throw new Exception("No se puede elimininar al proveedor porque tiene compras registradas.");
		mDAO.eliminar(Proveedor.class, proveedor.getProveId());
		mAuditoria.mostrarLog(loginDTO, getClass(), "eliminarProveedor",
				"se eliminó al proveedor " + proveedor.getProveId());
	}

	// -----------------------------------------PRODUCTOS-------------------------------------------
	// Inicializar
	public Producto inicializarProducto() {
		Producto producto = new Producto();
		producto.setProducNombre("");
		producto.setProducDescripcion("");
		producto.setProducFichatecnica("");
		producto.setProducStock(0);
		producto.setProducPreciou(new BigDecimal(0));
		producto.setProducEstado(true);
		producto.setProducDescuento(true);
		return producto;
	}

	// Listar
	public List<Producto> findAllProductos() {
		return mDAO.findAll(Producto.class);
	}

	// Insertar
	public void insertarProducto(LoginDTO loginDTO, Producto nuevoProducto) throws Exception {
		mDAO.insertar(nuevoProducto);
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarProducto",
				"Producto: " + nuevoProducto.getProducId() + " agregado con éxito");
	}

	// Actualizar
	public void actualizarProducto(LoginDTO loginDTO, Producto edicionProducto) throws Exception {
		Producto producto = (Producto) mDAO.findById(Producto.class, edicionProducto.getProducId());

		producto.setProducNombre(edicionProducto.getProducNombre());
		producto.setProducDescripcion(edicionProducto.getProducDescripcion());
		producto.setProducFichatecnica(edicionProducto.getProducFichatecnica());
		producto.setProducStock(edicionProducto.getProducStock());
		producto.setProducPreciou(edicionProducto.getProducPreciou());
		producto.setProducEstado(edicionProducto.getProducEstado());
		producto.setProducDescuento(edicionProducto.getProducDescuento());
		mDAO.actualizar(producto);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarProducto",
				"se actualizó la ciudad " + edicionProducto.getProducId());
	}

	// Eliminar
	public void eliminarProducto(LoginDTO loginDTO, int idProducto) throws Exception {
		Producto producto = (Producto) mDAO.findById(Producto.class, idProducto);
		if (producto.getFacturaDets().size() > 0)
			throw new Exception("No se puede elimininar la ciudad porque tiene facturas registrados.");
		mDAO.eliminar(Producto.class, producto.getProducId());
		mAuditoria.mostrarLog(loginDTO, getClass(), "eliminarProducto",
				"se eliminó la ciudad " + producto.getProducNombre());
	}

	// ----------------------------------COMPRAS-CABECERA------------------------------------
	// Inicializar
	public ComprasCab inicializarComprasCab() {
		ComprasCab comprasCab = new ComprasCab();
		comprasCab.setComCabFecha(new Date(System.currentTimeMillis()));
		comprasCab.setComCabSubtotal(new BigDecimal(0));
		comprasCab.setComCabIva(true);
		comprasCab.setComCabTotal(new BigDecimal(0));
		comprasCab.setProveedor(new Proveedor());
		return comprasCab;
	}

	// Listar
	public List<ComprasCab> findAllComprasCab() {
		return mDAO.findAll(ComprasCab.class);
	}

	// Insertar
	public void insertarComprasCab(LoginDTO loginDTO, ComprasCab nuevoComprasCab, int idProveedor) throws Exception {

		Proveedor proveedor = (Proveedor) mDAO.findById(Proveedor.class, idProveedor);
		nuevoComprasCab.setProveedor(proveedor);

		mDAO.insertar(nuevoComprasCab);
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarComprasCab",
				"Compra: " + nuevoComprasCab.getComCabId() + " agregada con éxito");
	}

	// Actualizar
	public void actualizarComprasCab(LoginDTO loginDTO, ComprasCab edicionComprasCab) throws Exception {
		ComprasCab comprasCab = (ComprasCab) mDAO.findById(ComprasCab.class, edicionComprasCab.getComCabId());
		Proveedor proveedor = (Proveedor) mDAO.findById(Proveedor.class, edicionComprasCab.getProveedor().getProveId());

		comprasCab.setProveedor(proveedor);
		comprasCab.setComCabFecha(edicionComprasCab.getComCabFecha());
		comprasCab.setComCabSubtotal(edicionComprasCab.getComCabSubtotal());
		comprasCab.setComCabIva(edicionComprasCab.getComCabIva());
		comprasCab.setComCabTotal(edicionComprasCab.getComCabTotal());
		mDAO.actualizar(comprasCab);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarComprasCab",
				"se actualizó la compra " + comprasCab.getComCabId());
	}

	// Eliminar
	public void eliminarComprasCab(LoginDTO loginDTO, int idComprasCab) throws Exception {
		ComprasCab comprasCab = (ComprasCab) mDAO.findById(ComprasCab.class, idComprasCab);
		if (comprasCab.getComprasDets().size() > 0)
			throw new Exception("No se puede elimininar la compra porque tiene detalles registrados.");
		mDAO.eliminar(ComprasCab.class, comprasCab.getComCabId());
		mAuditoria.mostrarLog(loginDTO, getClass(), "eliminarComprasCab",
				"se eliminó la compra " + comprasCab.getComCabId());
	}

	// -------------------------------------CABECERA-DETALLE---------------------------
	// Inicializar
		public ComprasDet inicializarComprasDet(ComprasCab comprasCab) {
			ComprasDet comprasDet = new ComprasDet();

			comprasDet.setComprasCab(comprasCab); // Inicializamos con el ID de la proforma Cab
			comprasDet.setProducto(new Producto());
			comprasDet.setComDetCantidad(0);
			comprasDet.setComDetPrecio(new BigDecimal(0));
			comprasDet.setComDetPreciototal(new BigDecimal(0));
			return comprasDet;
		}
	
	// Listar detalles segun proforma
	public List<ComprasDet> findDetalleByCompra(int compraId) {
		return mDAO.findWhere(ComprasDet.class, "o.comprasCab.comCabId=" + compraId, "o.comDetId");
	}
	

	// Insercion de Compras Cab
	public void insertarComprasDet(LoginDTO loginDTO, ComprasDet nuevaComprasDet, int productoSeleccionado)
			throws Exception {

		Producto producto = (Producto) mDAO.findById(Producto.class, productoSeleccionado); // Encontrar la proforma
		nuevaComprasDet.setProducto(producto);
		//nuevaComprasDet.setComDetPrecio(producto.getProducPreciou());
		nuevaComprasDet.setComDetPreciototal(calculoPrecioTotal(nuevaComprasDet.getComDetPrecio(), nuevaComprasDet.getComDetCantidad()));
		mDAO.insertar(nuevaComprasDet);
		// Forma compuesta
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarComprasDet",
				"Detalle: " + nuevaComprasDet.getComDetId() + " agregada con éxito");
		// Actualizar total de compra cabecera
		calcularTotalCompra(nuevaComprasDet.getComprasCab().getComCabId());
		actualizarProducto(producto, nuevaComprasDet.getComDetCantidad(), nuevaComprasDet.getComDetPrecio());
	}

	// Metodo para actualizar Total de ComprasCabecera
	public void calcularTotalCompra(int compraId) throws Exception {
		// Buscar compra
		ComprasCab compraCab = (ComprasCab) mDAO.findById(ComprasCab.class, compraId);
		// Agregar a una lista los detalles de dicha proforma
		List<ComprasDet> detalles = findDetalleByCompra(compraId);
		double suma = 0;

		for (ComprasDet d : detalles) {
			suma += d.getComDetPreciototal().doubleValue();
		}
		double iva=0;
		if(compraCab.getComCabIva()==true) {
			iva = suma * 0.12;
		}
		double TotalFinal = suma + iva;
		

		BigDecimal sumaT = new BigDecimal(suma);
		BigDecimal TotalFinalT = new BigDecimal(TotalFinal);
		System.out.println("suma total:" + sumaT);
		
		compraCab.setComCabSubtotal(sumaT);
		compraCab.setComCabTotal(TotalFinalT);
		mDAO.actualizar(compraCab);
	}

	public BigDecimal calculoPrecioTotal(BigDecimal precioU, int cant) {
		double precio_unitario = precioU.doubleValue();
		double precioTotal = precio_unitario * cant;

		BigDecimal precioT = new BigDecimal(precioTotal);
		return precioT;
	}
	
	public void actualizarProducto(Producto producto, int cantidad, BigDecimal precio) throws Exception {
		int stock=producto.getProducStock();
		stock=stock+cantidad;
		
		producto.setProducStock(stock);
		producto.setProducPreciou(precio);
		mDAO.actualizar(producto);
	}
}
