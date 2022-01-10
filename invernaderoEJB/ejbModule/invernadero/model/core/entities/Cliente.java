package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name="cliente")
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cli_cedula", unique=true, nullable=false, length=13)
	private String cliCedula;

	@Column(name="cli_apellido", nullable=false, length=50)
	private String cliApellido;

	@Column(name="cli_categoria", nullable=false, length=20)
	private String cliCategoria;

	@Column(name="cli_correo", nullable=false, length=50)
	private String cliCorreo;

	@Column(name="cli_direccion", nullable=false, length=100)
	private String cliDireccion;

	@Column(name="cli_estado", nullable=false)
	private Boolean cliEstado;

	@Column(name="cli_nombre", nullable=false, length=50)
	private String cliNombre;

	@Column(name="cli_telefono", nullable=false, length=10)
	private String cliTelefono;

	//bi-directional many-to-one association to ProformasCab
	@OneToMany(mappedBy="cliente")
	private List<ProformasCab> proformasCabs;

	public Cliente() {
	}

	public String getCliCedula() {
		return this.cliCedula;
	}

	public void setCliCedula(String cliCedula) {
		this.cliCedula = cliCedula;
	}

	public String getCliApellido() {
		return this.cliApellido;
	}

	public void setCliApellido(String cliApellido) {
		this.cliApellido = cliApellido;
	}

	public String getCliCategoria() {
		return this.cliCategoria;
	}

	public void setCliCategoria(String cliCategoria) {
		this.cliCategoria = cliCategoria;
	}

	public String getCliCorreo() {
		return this.cliCorreo;
	}

	public void setCliCorreo(String cliCorreo) {
		this.cliCorreo = cliCorreo;
	}

	public String getCliDireccion() {
		return this.cliDireccion;
	}

	public void setCliDireccion(String cliDireccion) {
		this.cliDireccion = cliDireccion;
	}

	public Boolean getCliEstado() {
		return this.cliEstado;
	}

	public void setCliEstado(Boolean cliEstado) {
		this.cliEstado = cliEstado;
	}

	public String getCliNombre() {
		return this.cliNombre;
	}

	public void setCliNombre(String cliNombre) {
		this.cliNombre = cliNombre;
	}

	public String getCliTelefono() {
		return this.cliTelefono;
	}

	public void setCliTelefono(String cliTelefono) {
		this.cliTelefono = cliTelefono;
	}

	public List<ProformasCab> getProformasCabs() {
		return this.proformasCabs;
	}

	public void setProformasCabs(List<ProformasCab> proformasCabs) {
		this.proformasCabs = proformasCabs;
	}

	public ProformasCab addProformasCab(ProformasCab proformasCab) {
		getProformasCabs().add(proformasCab);
		proformasCab.setCliente(this);

		return proformasCab;
	}

	public ProformasCab removeProformasCab(ProformasCab proformasCab) {
		getProformasCabs().remove(proformasCab);
		proformasCab.setCliente(null);

		return proformasCab;
	}

}