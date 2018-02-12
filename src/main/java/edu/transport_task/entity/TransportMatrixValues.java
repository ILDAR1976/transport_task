package edu.transport_task.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("unused")
@Entity
@Table(name = "transport_matrix")
public class TransportMatrixValues implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
 	private Long id;
	@Column
	private int cost;
	@Column
	private int transportTax;
	@Column
	private int currentTax;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getTransportTax() {
		return transportTax;
	}
	public void setTransportTax(int transportTax) {
		this.transportTax = transportTax;
	}
	public int getCurrentTax() {
		return currentTax;
	}
	public void setCurrentTax(int currentTax) {
		this.currentTax = currentTax;
	}
}
