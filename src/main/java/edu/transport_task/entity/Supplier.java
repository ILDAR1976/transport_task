package edu.transport_task.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "supplier")
public class Supplier implements Serializable {

	@Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private int availability;
	
	@Column
	private int potential;

	private int currentAvailability;
	
	public Supplier() {}
	
	public Supplier(Long id, String name, int availability) {
		this.id = id;
		this.name = name;
		this.availability = availability;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public int getPotential() {
		return potential;
	}

	public void setPotential(int potential) {
		this.potential = potential;
	}

	public int getCurrentAvailability() {
		return currentAvailability;
	}

	public void setCurrentAvailability(int currentAvailability) {
		this.currentAvailability = currentAvailability;
	}
	
}
