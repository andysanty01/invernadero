package invernadero.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the producto database table.
 * 
 */
@Entity
@Table(name="producto")
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="produc_id", unique=true, nullable=false)
	private Integer producId;

	@Column(name="produc_descripcion", nullable=false, length=100)
	private String producDescripcion;

	@Column(name="produc_descuento", nullable=false)
	private Boolean producDescuento;

	@Column(name="produc_estado", nullable=false)
	private Boolean producEstado;

	@Column(name="produc_fichatecnica", nullable=false, length=200)
	private String producFichatecnica;

	@Column(name="produc_nombre", nullable=false, length=100)
	private String producNombre;

	@Column(name="produc_precioc", precision=7, scale=2)
	private BigDecimal producPrecioc;

	@Column(name="produc_preciou", nullable=false, precision=7, scale=2)
	private BigDecimal producPreciou;

	@Column(name="produc_stock", nullable=false)
	private Integer producStock;

	//bi-directional many-to-one association to ComprasDet
	@OneToMany(mappedBy="producto")
	private List<ComprasDet> comprasDets;

	//bi-directional many-to-one association to FacturaDet
	@OneToMany(mappedBy="producto")
	private List<FacturaDet> facturaDets;

	//bi-directional many-to-one association to ProformasDet
	@OneToMany(mappedBy="producto")
	private List<ProformasDet> proformasDets;

	public Producto() {
	}

	public Integer getProducId() {
		return this.producId;
	}

	public void setProducId(Integer producId) {
		this.producId = producId;
	}

	public String getProducDescripcion() {
		return this.producDescripcion;
	}

	public void setProducDescripcion(String producDescripcion) {
		this.producDescripcion = producDescripcion;
	}

	public Boolean getProducDescuento() {
		return this.producDescuento;
	}

	public void setProducDescuento(Boolean producDescuento) {
		this.producDescuento = producDescuento;
	}

	public Boolean getProducEstado() {
		return this.producEstado;
	}

	public void setProducEstado(Boolean producEstado) {
		this.producEstado = producEstado;
	}

	public String getProducFichatecnica() {
		return this.producFichatecnica;
	}

	public void setProducFichatecnica(String producFichatecnica) {
		this.producFichatecnica = producFichatecnica;
	}

	public String getProducNombre() {
		return this.producNombre;
	}

	public void setProducNombre(String producNombre) {
		this.producNombre = producNombre;
	}

	public BigDecimal getProducPrecioc() {
		return this.producPrecioc;
	}

	public void setProducPrecioc(BigDecimal producPrecioc) {
		this.producPrecioc = producPrecioc;
	}

	public BigDecimal getProducPreciou() {
		return this.producPreciou;
	}

	public void setProducPreciou(BigDecimal producPreciou) {
		this.producPreciou = producPreciou;
	}

	public Integer getProducStock() {
		return this.producStock;
	}

	public void setProducStock(Integer producStock) {
		this.producStock = producStock;
	}

	public List<ComprasDet> getComprasDets() {
		return this.comprasDets;
	}

	public void setComprasDets(List<ComprasDet> comprasDets) {
		this.comprasDets = comprasDets;
	}

	public ComprasDet addComprasDet(ComprasDet comprasDet) {
		getComprasDets().add(comprasDet);
		comprasDet.setProducto(this);

		return comprasDet;
	}

	public ComprasDet removeComprasDet(ComprasDet comprasDet) {
		getComprasDets().remove(comprasDet);
		comprasDet.setProducto(null);

		return comprasDet;
	}

	public List<FacturaDet> getFacturaDets() {
		return this.facturaDets;
	}

	public void setFacturaDets(List<FacturaDet> facturaDets) {
		this.facturaDets = facturaDets;
	}

	public FacturaDet addFacturaDet(FacturaDet facturaDet) {
		getFacturaDets().add(facturaDet);
		facturaDet.setProducto(this);

		return facturaDet;
	}

	public FacturaDet removeFacturaDet(FacturaDet facturaDet) {
		getFacturaDets().remove(facturaDet);
		facturaDet.setProducto(null);

		return facturaDet;
	}

	public List<ProformasDet> getProformasDets() {
		return this.proformasDets;
	}

	public void setProformasDets(List<ProformasDet> proformasDets) {
		this.proformasDets = proformasDets;
	}

	public ProformasDet addProformasDet(ProformasDet proformasDet) {
		getProformasDets().add(proformasDet);
		proformasDet.setProducto(this);

		return proformasDet;
	}

	public ProformasDet removeProformasDet(ProformasDet proformasDet) {
		getProformasDets().remove(proformasDet);
		proformasDet.setProducto(null);

		return proformasDet;
	}

}