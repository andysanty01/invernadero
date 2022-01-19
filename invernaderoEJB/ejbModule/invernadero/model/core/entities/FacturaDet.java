package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the factura_det database table.
 * 
 */
@Entity
@Table(name="factura_det")
@NamedQuery(name="FacturaDet.findAll", query="SELECT f FROM FacturaDet f")
public class FacturaDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fac_det_id", unique=true, nullable=false)
	private Integer facDetId;

	@Column(name="fac_det_cantidad")
	private Integer facDetCantidad;

	@Column(name="fac_det_precio", precision=131089)
	private BigDecimal facDetPrecio;

	@Column(name="fac_det_preciototal", precision=131089)
	private BigDecimal facDetPreciototal;

	//bi-directional many-to-one association to FacturaCab
	@ManyToOne
	@JoinColumn(name="fac_cab_id")
	private FacturaCab facturaCab;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="produc_id")
	private Producto producto;

	public FacturaDet() {
	}

	public Integer getFacDetId() {
		return this.facDetId;
	}

	public void setFacDetId(Integer facDetId) {
		this.facDetId = facDetId;
	}

	public Integer getFacDetCantidad() {
		return this.facDetCantidad;
	}

	public void setFacDetCantidad(Integer facDetCantidad) {
		this.facDetCantidad = facDetCantidad;
	}

	public BigDecimal getFacDetPrecio() {
		return this.facDetPrecio;
	}

	public void setFacDetPrecio(BigDecimal facDetPrecio) {
		this.facDetPrecio = facDetPrecio;
	}

	public BigDecimal getFacDetPreciototal() {
		return this.facDetPreciototal;
	}

	public void setFacDetPreciototal(BigDecimal facDetPreciototal) {
		this.facDetPreciototal = facDetPreciototal;
	}

	public FacturaCab getFacturaCab() {
		return this.facturaCab;
	}

	public void setFacturaCab(FacturaCab facturaCab) {
		this.facturaCab = facturaCab;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}