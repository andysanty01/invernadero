package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the compras_cab database table.
 * 
 */
@Entity
@Table(name="compras_cab")
@NamedQuery(name="ComprasCab.findAll", query="SELECT c FROM ComprasCab c")
public class ComprasCab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="com_cab_id", unique=true, nullable=false)
	private Integer comCabId;

	@Temporal(TemporalType.DATE)
	@Column(name="com_cab_fecha", nullable=false)
	private Date comCabFecha;

	@Column(name="com_cab_iva")
	private Boolean comCabIva;

	@Column(name="com_cab_subtotal", nullable=false, precision=7, scale=2)
	private BigDecimal comCabSubtotal;

	@Column(name="com_cab_total", nullable=false, precision=7, scale=2)
	private BigDecimal comCabTotal;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="prove_id")
	private Proveedor proveedor;

	//bi-directional many-to-one association to ComprasDet
	@OneToMany(mappedBy="comprasCab",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<ComprasDet> comprasDets;

	public ComprasCab() {
	}

	public Integer getComCabId() {
		return this.comCabId;
	}

	public void setComCabId(Integer comCabId) {
		this.comCabId = comCabId;
	}

	public Date getComCabFecha() {
		return this.comCabFecha;
	}

	public void setComCabFecha(Date comCabFecha) {
		this.comCabFecha = comCabFecha;
	}

	public Boolean getComCabIva() {
		return this.comCabIva;
	}

	public void setComCabIva(Boolean comCabIva) {
		this.comCabIva = comCabIva;
	}

	public BigDecimal getComCabSubtotal() {
		return this.comCabSubtotal;
	}

	public void setComCabSubtotal(BigDecimal comCabSubtotal) {
		this.comCabSubtotal = comCabSubtotal;
	}

	public BigDecimal getComCabTotal() {
		return this.comCabTotal;
	}

	public void setComCabTotal(BigDecimal comCabTotal) {
		this.comCabTotal = comCabTotal;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<ComprasDet> getComprasDets() {
		return this.comprasDets;
	}

	public void setComprasDets(List<ComprasDet> comprasDets) {
		this.comprasDets = comprasDets;
	}

	public ComprasDet addComprasDet(ComprasDet comprasDet) {
		getComprasDets().add(comprasDet);
		comprasDet.setComprasCab(this);

		return comprasDet;
	}

	public ComprasDet removeComprasDet(ComprasDet comprasDet) {
		getComprasDets().remove(comprasDet);
		comprasDet.setComprasCab(null);

		return comprasDet;
	}

}