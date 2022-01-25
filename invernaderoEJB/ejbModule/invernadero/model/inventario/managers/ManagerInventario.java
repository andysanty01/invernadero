package invernadero.model.inventario.managers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import invernadero.model.auditoria.managers.ManagerAuditoria;
import invernadero.model.core.entities.Cliente;
import invernadero.model.core.entities.ComprasCab;
import invernadero.model.core.entities.FacturaCab;
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
public class ManagerInventario {

	// Nuevo codigo auditoria
	@EJB
	private ManagerAuditoria mAuditoria;

	@EJB
	private ManagerDAO mDAO;

	@EJB
	private ManagerSeguridades mSeg;

	public ManagerInventario() {
	}

/////////--------------------------CLIENTES---------------------------------------------------------

	// Listar clientes
	public List<Cliente> findAllClientes() {
		return mDAO.findAll(Cliente.class);
	}

/////////--------------------PROFORMAS CABECERA---------------------------------------------------------

	// Listar proformas cab
	public List<ProformasCab> findAllProformasCab() {
		return mDAO.findAll(ProformasCab.class);
	}

	// --------------------------------------PRODUCTOS-----------------------------------

	// Listar productos
	public List<Producto> findAllProductos() {
		return mDAO.findAll(Producto.class);
	}

	// -------------------------------------PROFORMAS-DETALLE---------------------------

	// Listar detalles segun proforma
	public List<ProformasDet> findDetalleByProforma(int proformaId) {
		return mDAO.findWhere(ProformasDet.class, "o.proformasCab.proCabId=" + proformaId, "o.proDetId");
	}

	/////// ------------------------------ORDENES-DE-TRABAJO----------------------

	// Listar Ordenes de trabajo
	public List<OrdenTrabajo> findAllOrdenesTrabajo() {
		return mDAO.findWhere(OrdenTrabajo.class, "o.ordenDespacho=false", "o.ordenId");
	}

	public List<OrdenTrabajo> findAllOrdenesTrabajoDespacho() {
		return mDAO.findWhere(OrdenTrabajo.class, "o.ordenDespacho=true", "o.ordenId");
	}

	// Lista de usuarios
	public List<SegUsuario> findAllUsuarios() {
		return mDAO.findAll(SegUsuario.class);
	}

	// Actualizar avance
	public void actualizarDespacho(LoginDTO loginDTO, int ordenId) throws Exception {
		OrdenTrabajo orden = (OrdenTrabajo) mDAO.findById(OrdenTrabajo.class, ordenId);
		orden.setOrdenAvance(1);
		orden.setOrdenEstado("EN CONSTRUCION");
		orden.setOrdenDespacho(true);
		mDAO.actualizar(orden);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarDespacho", "Se despacho orden: " + orden.getOrdenId());

	}

	// -------------------------------CUENTAS-ALEGRES-----------------------------

	public BigDecimal calcularVentas() {
		double sumaT = 0;

		List<FacturaCab> listaVentas = mDAO.findAll(FacturaCab.class);
		for (FacturaCab v : listaVentas) {
			sumaT += v.getFacCabTotal().doubleValue();
		}

		BigDecimal totalVentas = new BigDecimal(sumaT);
		return totalVentas.setScale(2, RoundingMode.HALF_UP);
	}

	public int nroVentas() {
		int nroVentas = 0;

		List<FacturaCab> listaVentas = mDAO.findAll(FacturaCab.class);
		for (FacturaCab v : listaVentas) {
			nroVentas += 1;
		}
		return nroVentas;
	}

	public BigDecimal calcularCompras() {
		double sumaT = 0;

		List<ComprasCab> listaCompras = mDAO.findAll(ComprasCab.class);
		for (ComprasCab v : listaCompras) {
			sumaT += v.getComCabTotal().doubleValue();
		}

		BigDecimal totalVentas = new BigDecimal(sumaT);
		return totalVentas.setScale(2, RoundingMode.HALF_UP);
	}
	
	public int nroCompras() {
		int nroCompras = 0;

		List<ComprasCab> listaCompras = mDAO.findAll(ComprasCab.class);
		for (ComprasCab v : listaCompras) {
			nroCompras += 1;
		}
		return nroCompras;
	}
	
	public BigDecimal calcularDiferencia() {
		double ventas = calcularVentas().doubleValue();
		double compras = calcularCompras().doubleValue();
		double diferencia = ventas - compras;

		BigDecimal totalDiferencia = new BigDecimal(diferencia);
		return totalDiferencia.setScale(2, RoundingMode.HALF_UP);
	}

}
