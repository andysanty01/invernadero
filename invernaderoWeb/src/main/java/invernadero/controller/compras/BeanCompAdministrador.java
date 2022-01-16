package invernadero.controller.compras;

import java.io.Serializable;
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
import invernadero.model.core.entities.Proveedor;
import invernadero.model.core.entities.SegUsuario;
import invernadero.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanCompAdministrador implements Serializable {

	@EJB
	private ManagerSeguridades mSeg;
	@EJB
	private ManagerCompras mCompras;
	private List<Ciudad> listaCiudades;
	private List<Proveedor> listaProveedores;
	private List<SegUsuario> listaUsuarios;

	// Variables
	private Ciudad nuevaCiudad;
	private Proveedor nuevoProveedor;
	private int idSegUsuarioSeleccionado;
	private int CiuIdSeleccionado;
	
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanCompAdministrador() {
	}

	@PostConstruct
	public void inicializar() {
		listaCiudades = mCompras.findAllCiudades();
		listaProveedores = mCompras.findAllProveedores();
		nuevaCiudad = mCompras.inicializarCiudad();
		nuevoProveedor = mCompras.inicializarProveedor();
	}

	//Inserccion
	//Ciudades
	public void actionListenerInsertarCiudad() {
		try {
			mCompras.insertarCiudad(beanSegLogin.getLoginDTO(), nuevaCiudad);;
			JSFUtil.crearMensajeINFO("Ciudad agregada con éxito");
			listaCiudades = mCompras.findAllCiudades();
			nuevaCiudad = mCompras.inicializarCiudad();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	//Proveedores
	public void actionListenerInsertarProveedor() {
		try {
			mCompras.insertarProveedor(nuevoProveedor, CiuIdSeleccionado);
			JSFUtil.crearMensajeINFO("Proveedor creado");
			listaProveedores = mCompras.findAllProveedores();
			nuevoProveedor = mCompras.inicializarProveedor();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Cargar pagina de Agregar Proveedores
	public String actionCargarAgregarProveedores() {
		nuevoProveedor = mCompras.inicializarProveedor();
		listaCiudades= mCompras.findAllCiudades();
		return "agregarProveedor";

		
	}

	
	// ACCESORES
	
	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
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

	public int getCiuIdSeleccionado() {
		return CiuIdSeleccionado;
	}

	public void setCiuIdSeleccionado(int ciuIdSeleccionado) {
		CiuIdSeleccionado = ciuIdSeleccionado;
	}
	
	
}
