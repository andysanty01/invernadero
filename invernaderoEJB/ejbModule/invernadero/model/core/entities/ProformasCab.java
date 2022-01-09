package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the proformas_cab database table.
 * 
 */
@Entity
@Table(name="proformas_cab")
@NamedQuery(name="ProformasCab.findAll", query="SELECT p FROM ProformasCab p")
public class ProformasCab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pro_cab_id", unique=true, nullable=false)
	private Integer proCabId;

	@Column(name="pro_cab_fecha")
	private Timestamp proCabFecha;

	@Column(name="pro_cab_iva", nullable=false, precision=7, scale=2)
	private BigDecimal proCabIva;

	@Column(name="pro_cab_subtotal", nullable=false, precision=7, scale=2)
	private BigDecimal proCabSubtotal;

	@Column(name="pro_cab_total", nullable=false, precision=7, scale=2)
	private BigDecimal proCabTotal;

	//bi-directional many-to-one association to OrdenTrabajo
	@OneToMany(mappedBy="proformasCab")
	private List<OrdenTrabajo> ordenTrabajos;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="cli_cedula", nullable=false)
	private Cliente cliente;

	//bi-directional many-to-one association to ProformasDet
	@OneToMany(mappedBy="proformasCab")
	private List<ProformasDet> proformasDets;

	public ProformasCab() {
	}

	public Integer getProCabId() {
		return this.proCabId;
	}

	public void setProCabId(Integer proCabId) {
		this.proCabId = proCabId;
	}

	public Timestamp getProCabFecha() {
		return this.proCabFecha;
	}

	public void setProCabFecha(Timestamp proCabFecha) {
		this.proCabFecha = proCabFecha;
	}

	public BigDecimal getProCabIva() {
		return this.proCabIva;
	}

	public void setProCabIva(BigDecimal proCabIva) {
		this.proCabIva = proCabIva;
	}

	public BigDecimal getProCabSubtotal() {
		return this.proCabSubtotal;
	}

	public void setProCabSubtotal(BigDecimal proCabSubtotal) {
		this.proCabSubtotal = proCabSubtotal;
	}

	public BigDecimal getProCabTotal() {
		return this.proCabTotal;
	}

	public void setProCabTotal(BigDecimal proCabTotal) {
		this.proCabTotal = proCabTotal;
	}

	public List<OrdenTrabajo> getOrdenTrabajos() {
		return this.ordenTrabajos;
	}

	public void setOrdenTrabajos(List<OrdenTrabajo> ordenTrabajos) {
		this.ordenTrabajos = ordenTrabajos;
	}

	public OrdenTrabajo addOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
		getOrdenTrabajos().add(ordenTrabajo);
		ordenTrabajo.setProformasCab(this);

		return ordenTrabajo;
	}

	public OrdenTrabajo removeOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
		getOrdenTrabajos().remove(ordenTrabajo);
		ordenTrabajo.setProformasCab(null);

		return ordenTrabajo;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ProformasDet> getProformasDets() {
		return this.proformasDets;
	}

	public void setProformasDets(List<ProformasDet> proformasDets) {
		this.proformasDets = proformasDets;
	}

	public ProformasDet addProformasDet(ProformasDet proformasDet) {
		getProformasDets().add(proformasDet);
		proformasDet.setProformasCab(this);

		return proformasDet;
	}

	public ProformasDet removeProformasDet(ProformasDet proformasDet) {
		getProformasDets().remove(proformasDet);
		proformasDet.setProformasCab(null);

		return proformasDet;
	}

}