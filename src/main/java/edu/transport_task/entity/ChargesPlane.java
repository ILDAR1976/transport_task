package edu.transport_task.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable; 


@SuppressWarnings({"unused","rawtypes","unchecked"})
@Entity
@Table(name="charges_plane")
public class ChargesPlane implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
 	private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "consumer_id", nullable = false)
    private Consumer consumer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "transport_matrix_id", nullable = false)
    private TransportMatrixValues transportMatrix;

	public ChargesPlane() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Supplier getSuppliers() {
		return supplier;
	}
	
	public void setSuppliers(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public Consumer getConsumers() {
		return consumer;
	}
	
	public void setConsumers(Consumer consumer) {
		this.consumer = consumer;
	}
	
	public TransportMatrixValues getTransportMatrix() {
		return transportMatrix;
	}
	
	public void setTransportMatrix(TransportMatrixValues transportMatrix) {
		this.transportMatrix = transportMatrix;
	}

}
