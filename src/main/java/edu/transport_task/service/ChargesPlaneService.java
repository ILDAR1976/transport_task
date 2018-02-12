package edu.transport_task.service;

import java.util.Map;

import edu.transport_task.entity.ChargesPlane;
import edu.transport_task.entity.Consumer;
import edu.transport_task.entity.Supplier;
import edu.transport_task.entity.Task;
import edu.transport_task.entity.TransportMatrixValues;

@SuppressWarnings("unused")
public interface ChargesPlaneService {

	public void fullPlaneMatrix(String necessity, String availability, String cost);
		
	public ChargesPlane[][] getChargesPlane();
	
	public Map<Integer, String> getSuppliers();
	
	public Map<Integer, String> getSuppliersAvailability();
	
	public Map<Integer, String> getSuppliersPotentail();
	
	public Map<Integer, String> getConsumers();
	
	public Map<Integer, String> getConsumersNecessity();
	
	public Map<Integer, String> getConsumersPotentail();
	 
	public boolean isOptimal();
	
	public void setOptimal(boolean isOptimal);
	
	public int getResult();
	
	public void solveTask();
	
}
