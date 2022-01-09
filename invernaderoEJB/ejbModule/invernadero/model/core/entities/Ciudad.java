package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ciudad database table.
 * 
 */
@Entity
@Table(name="ciudad")
@NamedQuery(name="Ciudad.findAll", query="SELECT c FROM Ciudad c")
public class Ciudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ciu_id", unique=true, nullable=false)
	private Integer ciuId;

	@Column(name="ciu_nombre", nullable=false, length=100)
	private String ciuNombre;

	//bi-directional many-to-one association to Proveedor
	@OneToMany(mappedBy="ciudad")
	private List<Proveedor> proveedors;

	public Ciudad() {
	}

	public Integer getCiuId() {
		return this.ciuId;
	}

	public void setCiuId(Integer ciuId) {
		this.ciuId = ciuId;
	}

	public String getCiuNombre() {
		return this.ciuNombre;
	}

	public void setCiuNombre(String ciuNombre) {
		this.ciuNombre = ciuNombre;
	}

	public List<Proveedor> getProveedors() {
		return this.proveedors;
	}

	public void setProveedors(List<Proveedor> proveedors) {
		this.proveedors = proveedors;
	}

	public Proveedor addProveedor(Proveedor proveedor) {
		getProveedors().add(proveedor);
		proveedor.setCiudad(this);

		return proveedor;
	}

	public Proveedor removeProveedor(Proveedor proveedor) {
		getProveedors().remove(proveedor);
		proveedor.setCiudad(null);

		return proveedor;
	}

}