package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the orden_trabajo database table.
 * 
 */
@Entity
@Table(name="orden_trabajo")
@NamedQuery(name="OrdenTrabajo.findAll", query="SELECT o FROM OrdenTrabajo o")
public class OrdenTrabajo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="orden_id", unique=true, nullable=false)
	private Integer ordenId;

	@Column(name="orden_avance", nullable=false)
	private Integer ordenAvance;

	@Column(name="orden_despacho")
	private Boolean ordenDespacho;

	@Column(name="orden_estado", length=20)
	private String ordenEstado;

	@Column(name="orden_estadopago", nullable=false, length=10)
	private String ordenEstadopago;

	@Temporal(TemporalType.DATE)
	@Column(name="orden_fechafin", nullable=false)
	private Date ordenFechafin;

	@Temporal(TemporalType.DATE)
	@Column(name="orden_fechaini", nullable=false)
	private Date ordenFechaini;

	@Column(name="orden_observaciones", nullable=false, length=100)
	private String ordenObservaciones;

	//bi-directional many-to-one association to Despacho
	@OneToMany(mappedBy="ordenTrabajo")
	private List<Despacho> despachos;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="cli_cedula")
	private Cliente cliente;

	//bi-directional many-to-one association to ProformasCab
	@ManyToOne
	@JoinColumn(name="pro_cab_id")
	private ProformasCab proformasCab;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_seg_usuario")
	private SegUsuario segUsuario;

	public OrdenTrabajo() {
	}

	public Integer getOrdenId() {
		return this.ordenId;
	}

	public void setOrdenId(Integer ordenId) {
		this.ordenId = ordenId;
	}

	public Integer getOrdenAvance() {
		return this.ordenAvance;
	}

	public void setOrdenAvance(Integer ordenAvance) {
		this.ordenAvance = ordenAvance;
	}

	public Boolean getOrdenDespacho() {
		return this.ordenDespacho;
	}

	public void setOrdenDespacho(Boolean ordenDespacho) {
		this.ordenDespacho = ordenDespacho;
	}

	public String getOrdenEstado() {
		return this.ordenEstado;
	}

	public void setOrdenEstado(String ordenEstado) {
		this.ordenEstado = ordenEstado;
	}

	public String getOrdenEstadopago() {
		return this.ordenEstadopago;
	}

	public void setOrdenEstadopago(String ordenEstadopago) {
		this.ordenEstadopago = ordenEstadopago;
	}

	public Date getOrdenFechafin() {
		return this.ordenFechafin;
	}

	public void setOrdenFechafin(Date ordenFechafin) {
		this.ordenFechafin = ordenFechafin;
	}

	public Date getOrdenFechaini() {
		return this.ordenFechaini;
	}

	public void setOrdenFechaini(Date ordenFechaini) {
		this.ordenFechaini = ordenFechaini;
	}

	public String getOrdenObservaciones() {
		return this.ordenObservaciones;
	}

	public void setOrdenObservaciones(String ordenObservaciones) {
		this.ordenObservaciones = ordenObservaciones;
	}

	public List<Despacho> getDespachos() {
		return this.despachos;
	}

	public void setDespachos(List<Despacho> despachos) {
		this.despachos = despachos;
	}

	public Despacho addDespacho(Despacho despacho) {
		getDespachos().add(despacho);
		despacho.setOrdenTrabajo(this);

		return despacho;
	}

	public Despacho removeDespacho(Despacho despacho) {
		getDespachos().remove(despacho);
		despacho.setOrdenTrabajo(null);

		return despacho;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ProformasCab getProformasCab() {
		return this.proformasCab;
	}

	public void setProformasCab(ProformasCab proformasCab) {
		this.proformasCab = proformasCab;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

}