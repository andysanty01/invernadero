package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the despacho database table.
 * 
 */
@Entity
@Table(name="despacho")
@NamedQuery(name="Despacho.findAll", query="SELECT d FROM Despacho d")
public class Despacho implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="desp_id", unique=true, nullable=false)
	private Integer despId;

	@Temporal(TemporalType.DATE)
	@Column(name="desp_fecha", nullable=false)
	private Date despFecha;

	//bi-directional many-to-one association to OrdenTrabajo
	@ManyToOne
	@JoinColumn(name="orden_id")
	private OrdenTrabajo ordenTrabajo;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_seg_usuario", nullable=false)
	private SegUsuario segUsuario;

	public Despacho() {
	}

	public Integer getDespId() {
		return this.despId;
	}

	public void setDespId(Integer despId) {
		this.despId = despId;
	}

	public Date getDespFecha() {
		return this.despFecha;
	}

	public void setDespFecha(Date despFecha) {
		this.despFecha = despFecha;
	}

	public OrdenTrabajo getOrdenTrabajo() {
		return this.ordenTrabajo;
	}

	public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

}