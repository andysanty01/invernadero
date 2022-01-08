package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the prov_ciudad database table.
 * 
 */
@Entity
@Table(name="prov_ciudad")
@NamedQuery(name="ProvCiudad.findAll", query="SELECT p FROM ProvCiudad p")
public class ProvCiudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="prov_ciu_id", unique=true, nullable=false)
	private Integer provCiuId;

	@Column(name="prov_ciu_nombre", nullable=false, length=100)
	private String provCiuNombre;

	//bi-directional many-to-one association to ProvProveedor
	@OneToMany(mappedBy="provCiudad")
	private List<ProvProveedor> provProveedors;

	public ProvCiudad() {
	}

	public Integer getProvCiuId() {
		return this.provCiuId;
	}

	public void setProvCiuId(Integer provCiuId) {
		this.provCiuId = provCiuId;
	}

	public String getProvCiuNombre() {
		return this.provCiuNombre;
	}

	public void setProvCiuNombre(String provCiuNombre) {
		this.provCiuNombre = provCiuNombre;
	}

	public List<ProvProveedor> getProvProveedors() {
		return this.provProveedors;
	}

	public void setProvProveedors(List<ProvProveedor> provProveedors) {
		this.provProveedors = provProveedors;
	}

	public ProvProveedor addProvProveedor(ProvProveedor provProveedor) {
		getProvProveedors().add(provProveedor);
		provProveedor.setProvCiudad(this);

		return provProveedor;
	}

	public ProvProveedor removeProvProveedor(ProvProveedor provProveedor) {
		getProvProveedors().remove(provProveedor);
		provProveedor.setProvCiudad(null);

		return provProveedor;
	}

}