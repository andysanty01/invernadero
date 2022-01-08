package invernadero.controller.proveedores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import invernadero.controller.JSFUtil;
import invernadero.controller.seguridades.BeanSegLogin;
import invernadero.model.core.entities.ProvCiudad;
import invernadero.model.core.entities.ProvProveedor;
import invernadero.model.core.entities.SegUsuario;
import invernadero.model.proveedores.managers.ManagerProveedores;
import invernadero.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanProvAdministrador implements Serializable {

	@EJB
	private ManagerSeguridades mSeg;
	@EJB
	private ManagerProveedores mProveedores;
	private List<ProvCiudad> listaCiudades;
	private List<ProvProveedor> listaProveedores;
	private List<SegUsuario> listaUsuarios;

	// Variables
	private ProvCiudad nuevaCiudad;
	private ProvProveedor nuevoProveedor;
	private int idSegUsuarioSeleccionado;
	private int provCiuIdSeleccionado;
	
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanProvAdministrador() {
	}

	@PostConstruct
	public void inicializar() {
		listaCiudades = mProveedores.findAllCiudades();
		listaProveedores = mProveedores.findAllProveedores();
		nuevaCiudad = mProveedores.inicializarCiudad();
		nuevoProveedor = mProveedores.inicializarProveedor();
	}

	//Inserccion
	//Ciudades
	public void actionListenerInsertarCiudad() {
		try {
			mProveedores.insertarCiudad(beanSegLogin.getLoginDTO(), nuevaCiudad);;
			JSFUtil.crearMensajeINFO("Ciudad agregada con éxito");
			listaCiudades = mProveedores.findAllCiudades();
			nuevaCiudad = mProveedores.inicializarCiudad();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	//Proveedores
	public void actionListenerInsertarProveedor() {
		try {
			mProveedores.insertarProveedor(nuevoProveedor, idSegUsuarioSeleccionado, provCiuIdSeleccionado);
			JSFUtil.crearMensajeINFO("Proveedor creado");
			listaProveedores = mProveedores.findAllProveedores();
			nuevoProveedor = mProveedores.inicializarProveedor();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Cargar pagina de Agregar Proveedores
	public String actionCargarAgregarProveedores() {
		nuevoProveedor = mProveedores.inicializarProveedor();
		listaCiudades= mProveedores.findAllCiudades();
		listaUsuarios = mSeg.findAllUsuarios();
		return "agregarProveedor";

	}
	
	// ACCESORES
	public List<ProvCiudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<ProvCiudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public BeanSegLogin getBeanSegLogin() {
		return beanSegLogin;
	}

	public void setBeanSegLogin(BeanSegLogin beanSegLogin) {
		this.beanSegLogin = beanSegLogin;
	}

	public List<ProvProveedor> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<ProvProveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}

	public ProvCiudad getNuevaCiudad() {
		return nuevaCiudad;
	}

	public void setNuevaCiudad(ProvCiudad nuevaCiudad) {
		this.nuevaCiudad = nuevaCiudad;
	}

	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ProvProveedor getNuevoProveedor() {
		return nuevoProveedor;
	}

	public void setNuevoProveedor(ProvProveedor nuevoProveedor) {
		this.nuevoProveedor = nuevoProveedor;
	}

	public int getIdSegUsuarioSeleccionado() {
		return idSegUsuarioSeleccionado;
	}

	public void setIdSegUsuarioSeleccionado(int idSegUsuarioSeleccionado) {
		this.idSegUsuarioSeleccionado = idSegUsuarioSeleccionado;
	}

	public int getProvCiuIdSeleccionado() {
		return provCiuIdSeleccionado;
	}

	public void setProvCiuIdSeleccionado(int provCiuIdSeleccionado) {
		this.provCiuIdSeleccionado = provCiuIdSeleccionado;
	}
	
	

}
