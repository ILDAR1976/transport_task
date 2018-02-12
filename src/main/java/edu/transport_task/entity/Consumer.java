package edu.transport_task.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "consumer")
public class Consumer implements Serializable {
   
	@Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
	private Long id;

	@Column
	private String name;
	
	@Column
	private int necessity;
	
	@Column
	private int potential;

	private int currentNecessity;
	
	public Consumer() {}
	
	public Consumer(Long id, String name, int necessity) {
		this.id = id;
		this.name = name;
		this.necessity = necessity;
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

	public int getNecessity() {
		return necessity;
	}

	public void setNecessity(int necessity) {
		this.necessity = necessity;
	}

	public int getPotential() {
		return potential;
	}

	public void setPotential(int potential) {
		this.potential = potential;
	}

	public int getCurrentNecessity() {
		return currentNecessity;
	}

	public void setCurrentNecessity(int currentNecessity) {
		this.currentNecessity = currentNecessity;
	}
}
