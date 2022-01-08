package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the prov_proveedor database table.
 * 
 */
@Entity
@Table(name="prov_proveedor")
@NamedQuery(name="ProvProveedor.findAll", query="SELECT p FROM ProvProveedor p")
public class ProvProveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="prov_prove_id", unique=true, nullable=false)
	private Integer provProveId;

	@Column(name="prov_prove_direccion", nullable=false, length=100)
	private String provProveDireccion;

	@Column(name="prov_prove_nombre", nullable=false, length=100)
	private String provProveNombre;

	@Column(name="prov_prove_nomcomercial", nullable=false, length=10)
	private String provProveNomcomercial;

	@Column(name="prov_prove_telefono", nullable=false, length=10)
	private String provProveTelefono;

	//bi-directional many-to-one association to ProvCiudad
	@ManyToOne
	@JoinColumn(name="prov_ciu_id", nullable=false)
	private ProvCiudad provCiudad;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_seg_usuario", nullable=false)
	private SegUsuario segUsuario;

	public ProvProveedor() {
	}

	public Integer getProvProveId() {
		return this.provProveId;
	}

	public void setProvProveId(Integer provProveId) {
		this.provProveId = provProveId;
	}

	public String getProvProveDireccion() {
		return this.provProveDireccion;
	}

	public void setProvProveDireccion(String provProveDireccion) {
		this.provProveDireccion = provProveDireccion;
	}

	public String getProvProveNombre() {
		return this.provProveNombre;
	}

	public void setProvProveNombre(String provProveNombre) {
		this.provProveNombre = provProveNombre;
	}

	public String getProvProveNomcomercial() {
		return this.provProveNomcomercial;
	}

	public void setProvProveNomcomercial(String provProveNomcomercial) {
		this.provProveNomcomercial = provProveNomcomercial;
	}

	public String getProvProveTelefono() {
		return this.provProveTelefono;
	}

	public void setProvProveTelefono(String provProveTelefono) {
		this.provProveTelefono = provProveTelefono;
	}

	public ProvCiudad getProvCiudad() {
		return this.provCiudad;
	}

	public void setProvCiudad(ProvCiudad provCiudad) {
		this.provCiudad = provCiudad;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

}