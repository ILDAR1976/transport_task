package edu.transport_task.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Task {
	
	Map<Integer, Consumer> consumers = new HashMap<Integer, Consumer>();
	Map<Integer, Supplier> suppliers = new HashMap<Integer, Supplier>();
 	List<MinimalPath> transportTaxList = new ArrayList<>();
 	ChargesPlane[][] chargesPlane;
 	
 	public Task() {}

	public Map<Integer, Consumer> getConsumers() {
		return consumers;
	}

	public void setConsumers(Map<Integer, Consumer> consumers) {
		this.consumers = consumers;
	}

	public Map<Integer, Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Map<Integer, Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public ChargesPlane[][] getChargesPlane() {
		return chargesPlane;
	}

	public void setChargesPlane(ChargesPlane[][] chargesPlane) {
		this.chargesPlane = chargesPlane;
	}

	public List<MinimalPath> getTransportTaxList() {
		return transportTaxList;
	}

	public void setTransportTaxList(List<MinimalPath> transportTaxList) {
		this.transportTaxList = transportTaxList;
	}

	
}
