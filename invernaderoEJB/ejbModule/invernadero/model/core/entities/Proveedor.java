package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the proveedor database table.
 * 
 */
@Entity
@Table(name="proveedor")
@NamedQuery(name="Proveedor.findAll", query="SELECT p FROM Proveedor p")
public class Proveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="prove_id", unique=true, nullable=false)
	private Integer proveId;

	@Column(name="prove_direccion", nullable=false, length=100)
	private String proveDireccion;

	@Column(name="prove_nombre", nullable=false, length=100)
	private String proveNombre;

	@Column(name="prove_nomcomercial", nullable=false, length=100)
	private String proveNomcomercial;

	@Column(name="prove_ruc", nullable=false, length=13)
	private String proveRuc;

	@Column(name="prove_telefono", nullable=false, length=10)
	private String proveTelefono;

	//bi-directional many-to-one association to ComprasCab
	@OneToMany(mappedBy="proveedor")
	private List<ComprasCab> comprasCabs;

	//bi-directional many-to-one association to Ciudad
	@ManyToOne
	@JoinColumn(name="ciu_id")
	private Ciudad ciudad;

	public Proveedor() {
	}

	public Integer getProveId() {
		return this.proveId;
	}

	public void setProveId(Integer proveId) {
		this.proveId = proveId;
	}

	public String getProveDireccion() {
		return this.proveDireccion;
	}

	public void setProveDireccion(String proveDireccion) {
		this.proveDireccion = proveDireccion;
	}

	public String getProveNombre() {
		return this.proveNombre;
	}

	public void setProveNombre(String proveNombre) {
		this.proveNombre = proveNombre;
	}

	public String getProveNomcomercial() {
		return this.proveNomcomercial;
	}

	public void setProveNomcomercial(String proveNomcomercial) {
		this.proveNomcomercial = proveNomcomercial;
	}

	public String getProveRuc() {
		return this.proveRuc;
	}

	public void setProveRuc(String proveRuc) {
		this.proveRuc = proveRuc;
	}

	public String getProveTelefono() {
		return this.proveTelefono;
	}

	public void setProveTelefono(String proveTelefono) {
		this.proveTelefono = proveTelefono;
	}

	public List<ComprasCab> getComprasCabs() {
		return this.comprasCabs;
	}

	public void setComprasCabs(List<ComprasCab> comprasCabs) {
		this.comprasCabs = comprasCabs;
	}

	public ComprasCab addComprasCab(ComprasCab comprasCab) {
		getComprasCabs().add(comprasCab);
		comprasCab.setProveedor(this);

		return comprasCab;
	}

	public ComprasCab removeComprasCab(ComprasCab comprasCab) {
		getComprasCabs().remove(comprasCab);
		comprasCab.setProveedor(null);

		return comprasCab;
	}

	public Ciudad getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

}