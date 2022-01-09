package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the compras_det database table.
 * 
 */
@Entity
@Table(name="compras_det")
@NamedQuery(name="ComprasDet.findAll", query="SELECT c FROM ComprasDet c")
public class ComprasDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="com_det_id", unique=true, nullable=false)
	private Integer comDetId;

	@Column(name="com_det_cantidad", nullable=false)
	private Integer comDetCantidad;

	@Column(name="com_det_precio", nullable=false, precision=7, scale=2)
	private BigDecimal comDetPrecio;

	@Column(name="com_det_preciototal", precision=7, scale=2)
	private BigDecimal comDetPreciototal;

	//bi-directional many-to-one association to ComprasCab
	@ManyToOne
	@JoinColumn(name="com_cab_id", nullable=false)
	private ComprasCab comprasCab;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="produc_id", nullable=false)
	private Producto producto;

	public ComprasDet() {
	}

	public Integer getComDetId() {
		return this.comDetId;
	}

	public void setComDetId(Integer comDetId) {
		this.comDetId = comDetId;
	}

	public Integer getComDetCantidad() {
		return this.comDetCantidad;
	}

	public void setComDetCantidad(Integer comDetCantidad) {
		this.comDetCantidad = comDetCantidad;
	}

	public BigDecimal getComDetPrecio() {
		return this.comDetPrecio;
	}

	public void setComDetPrecio(BigDecimal comDetPrecio) {
		this.comDetPrecio = comDetPrecio;
	}

	public BigDecimal getComDetPreciototal() {
		return this.comDetPreciototal;
	}

	public void setComDetPreciototal(BigDecimal comDetPreciototal) {
		this.comDetPreciototal = comDetPreciototal;
	}

	public ComprasCab getComprasCab() {
		return this.comprasCab;
	}

	public void setComprasCab(ComprasCab comprasCab) {
		this.comprasCab = comprasCab;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}