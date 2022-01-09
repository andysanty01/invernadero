package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the proformas_det database table.
 * 
 */
@Entity
@Table(name="proformas_det")
@NamedQuery(name="ProformasDet.findAll", query="SELECT p FROM ProformasDet p")
public class ProformasDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pro_det_id", unique=true, nullable=false)
	private Integer proDetId;

	@Column(name="pro_det_cantidad")
	private Integer proDetCantidad;

	@Column(name="pro_det_precio", nullable=false, precision=7, scale=2)
	private BigDecimal proDetPrecio;

	@Column(name="pro_det_preciototal", nullable=false, precision=7, scale=2)
	private BigDecimal proDetPreciototal;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="produc_id")
	private Producto producto;

	//bi-directional many-to-one association to ProformasCab
	@ManyToOne
	@JoinColumn(name="pro_cab_id")
	private ProformasCab proformasCab;

	public ProformasDet() {
	}

	public Integer getProDetId() {
		return this.proDetId;
	}

	public void setProDetId(Integer proDetId) {
		this.proDetId = proDetId;
	}

	public Integer getProDetCantidad() {
		return this.proDetCantidad;
	}

	public void setProDetCantidad(Integer proDetCantidad) {
		this.proDetCantidad = proDetCantidad;
	}

	public BigDecimal getProDetPrecio() {
		return this.proDetPrecio;
	}

	public void setProDetPrecio(BigDecimal proDetPrecio) {
		this.proDetPrecio = proDetPrecio;
	}

	public BigDecimal getProDetPreciototal() {
		return this.proDetPreciototal;
	}

	public void setProDetPreciototal(BigDecimal proDetPreciototal) {
		this.proDetPreciototal = proDetPreciototal;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public ProformasCab getProformasCab() {
		return this.proformasCab;
	}

	public void setProformasCab(ProformasCab proformasCab) {
		this.proformasCab = proformasCab;
	}

}