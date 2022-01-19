package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the factura_cab database table.
 * 
 */
@Entity
@Table(name="factura_cab")
@NamedQuery(name="FacturaCab.findAll", query="SELECT f FROM FacturaCab f")
public class FacturaCab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fac_cab_id", unique=true, nullable=false)
	private Integer facCabId;

	@Temporal(TemporalType.DATE)
	@Column(name="fac_cab_fecha")
	private Date facCabFecha;

	@Column(name="fac_cab_iva", precision=131089)
	private BigDecimal facCabIva;

	@Column(name="fac_cab_subtotal", precision=131089)
	private BigDecimal facCabSubtotal;

	@Column(name="fac_cab_total", precision=131089)
	private BigDecimal facCabTotal;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="cli_cedula")
	private Cliente cliente;

	//bi-directional many-to-one association to FacturaDet
	@OneToMany(mappedBy="facturaCab")
	private List<FacturaDet> facturaDets;

	public FacturaCab() {
	}

	public Integer getFacCabId() {
		return this.facCabId;
	}

	public void setFacCabId(Integer facCabId) {
		this.facCabId = facCabId;
	}

	public Date getFacCabFecha() {
		return this.facCabFecha;
	}

	public void setFacCabFecha(Date facCabFecha) {
		this.facCabFecha = facCabFecha;
	}

	public BigDecimal getFacCabIva() {
		return this.facCabIva;
	}

	public void setFacCabIva(BigDecimal facCabIva) {
		this.facCabIva = facCabIva;
	}

	public BigDecimal getFacCabSubtotal() {
		return this.facCabSubtotal;
	}

	public void setFacCabSubtotal(BigDecimal facCabSubtotal) {
		this.facCabSubtotal = facCabSubtotal;
	}

	public BigDecimal getFacCabTotal() {
		return this.facCabTotal;
	}

	public void setFacCabTotal(BigDecimal facCabTotal) {
		this.facCabTotal = facCabTotal;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<FacturaDet> getFacturaDets() {
		return this.facturaDets;
	}

	public void setFacturaDets(List<FacturaDet> facturaDets) {
		this.facturaDets = facturaDets;
	}

	public FacturaDet addFacturaDet(FacturaDet facturaDet) {
		getFacturaDets().add(facturaDet);
		facturaDet.setFacturaCab(this);

		return facturaDet;
	}

	public FacturaDet removeFacturaDet(FacturaDet facturaDet) {
		getFacturaDets().remove(facturaDet);
		facturaDet.setFacturaCab(null);

		return facturaDet;
	}

}