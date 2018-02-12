package edu.transport_task.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.transport_task.entity.ChargesPlane;
import edu.transport_task.entity.Consumer;
import edu.transport_task.entity.MinimalPath;
import edu.transport_task.entity.Supplier;
import edu.transport_task.entity.Task;
import edu.transport_task.entity.TransportMatrixValues;
import edu.transport_task.ui.MainController;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class ChargesPlaneServiceImpl implements ChargesPlaneService {
	
	private Logger logger = LoggerFactory.getLogger(MainController.class);

	private int necessityResult;
	private int availabilityResult;
	private boolean isOptimal;
	private Task task = new Task();

	@Override
	public void fullPlaneMatrix(String necessity, String availability, String cost)  {
		this.task.getConsumers().clear();
		this.task.getSuppliers().clear();
		
		parseNecessity(necessity);
		parseAvailability(availability);
		
		try {
			parseCost(this.task.getSuppliers(), this.task.getConsumers(), cost);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	
	@Override
	public ChargesPlane[][] getChargesPlane() {
		return task.getChargesPlane();
	}
	
	@Override
	public Map<Integer, String> getSuppliers() {
		Map<Integer, String> map = new HashMap();
		for (Map.Entry<Integer, Supplier> item : task.getSuppliers().entrySet()) {
			map.put(item.getKey(), item.getValue().getName().toString());
		}
		return map;
	}

	@Override
	public Map<Integer, String> getSuppliersAvailability() {
		Map<Integer, String> map = new HashMap();
		for (Map.Entry<Integer, Supplier> item : task.getSuppliers().entrySet()) {
			map.put(item.getKey(), "" + item.getValue().getAvailability());
		}
		return map;
	}

	@Override
	public Map<Integer, String> getSuppliersPotentail() {
		Map<Integer, String> map = new HashMap();
		for (Map.Entry<Integer, Supplier> item : task.getSuppliers().entrySet()) {
			map.put(item.getKey(), "" + item.getValue().getPotential());
		}
		return map;
	}

	@Override
	public Map<Integer, String> getConsumers() {
		Map<Integer, String> map = new HashMap();
		for (Map.Entry<Integer, Consumer> item : task.getConsumers().entrySet()) {
			map.put(item.getKey(), item.getValue().getName());
		}
		return map;
	}

	@Override
	public Map<Integer, String> getConsumersNecessity() {
		Map<Integer, String> map = new HashMap();
		for (Map.Entry<Integer, Consumer> item : task.getConsumers().entrySet()) {
			map.put(item.getKey(), "" + item.getValue().getNecessity());
		}
		return map;
	}

	@Override
	public Map<Integer, String> getConsumersPotentail() {
		Map<Integer, String> map = new HashMap();
		for (Map.Entry<Integer, Consumer> item : task.getConsumers().entrySet()) {
			map.put(item.getKey(), "" + item.getValue().getPotential());
		}
		return map;
	}

	@Override
	public boolean isOptimal() {
		return isOptimal;
	}


	@Override
	public void setOptimal(boolean isOptimal) {
		this.isOptimal = isOptimal;
	}
	

	@Override
	public int getResult() {
		return (necessityResult != availabilityResult ? 0 : availabilityResult );
	}
	
	@Override
	public void solveTask() {
		logger.info("The minimal path for this charges plane: " + System.lineSeparator() + task.getTransportTaxList());
		
		phaseOne();
		phaseTwo();
		phaseThree();
		phaseFour();
	
	}

	private void parseNecessity(String availability) {
		Optional.ofNullable(availability).map(value->value.split(",")).map(value->{
			Supplier supplier = null;
			for (int i=0;i<value.length;i++) {
				if (i % 3 == 0) {
					supplier = new Supplier();
					supplier.setId(Long.parseLong(value[i]));
				} else if (i % 3 == 1) {
					supplier.setName(value[i]);
				} else if (i % 3 == 2) {
					supplier.setAvailability(Integer.parseInt(value[i]));
					supplier.setCurrentAvailability(Integer.parseInt(value[i]));
					availabilityResult += Integer.parseInt(value[i]);
					task.getSuppliers().put(supplier.getId().intValue(), supplier);
				}
			}
			return task.getSuppliers();
		});

	}

	private void parseAvailability(String necessity) {
		Optional.ofNullable(necessity).map(value->value.split(",")).map(value->{
			Consumer consumer = null;
			for (int i=0;i<value.length;i++) {
				if (i % 3 == 0) {
					consumer = new Consumer();
					consumer.setId(Long.parseLong(value[i]));
				} else if (i % 3 == 1){
					consumer.setName(value[i]);
				} else if (i % 3 == 2){
					consumer.setNecessity(Integer.parseInt(value[i]));
					consumer.setCurrentNecessity(Integer.parseInt(value[i]));
					necessityResult += Integer.parseInt(value[i]);
					task.getConsumers().put(consumer.getId().intValue(), consumer);
				}
			}
			return task.getConsumers();
		});
		
	}

	private void parseCost(Map<Integer, Supplier> suppliers, Map<Integer, Consumer> consumers, String cost) {
		task.setChargesPlane(new ChargesPlane[suppliers.size()][consumers.size()]);
		
		Optional.ofNullable(cost).map(value->value.split(",")).map(value->{
			int supplier = 0;
			int consumer = 0;
			int transportTaxValue = 0;
			int min = 0;
			int max = 0;
			
			for (int i=0;i<value.length;i++) {
				if (i % 3 == 0) {
					supplier = Integer.parseInt(value[i]);
				} else if (i % 3 == 1){
					consumer = Integer.parseInt(value[i]);
				} else if (i % 3 == 2){
					transportTaxValue = Integer.parseInt(value[i]);
					TransportMatrixValues costMatrix = new TransportMatrixValues();
					costMatrix.setTransportTax(transportTaxValue);
					ChargesPlane chargePlane = new ChargesPlane();
					chargePlane.setTransportMatrix(costMatrix);
					
					chargePlane.setConsumers(task.getConsumers().get(consumer));
					chargePlane.setSuppliers(task.getSuppliers().get(supplier));
					
					task.getChargesPlane()[supplier][consumer] = chargePlane;
					MinimalPath mp = new MinimalPath();
					mp.setI(supplier);
					mp.setJ(consumer);
					mp.setTax(transportTaxValue);
					task.getTransportTaxList().add(mp);
				}
			}
			
			task.getTransportTaxList().sort((v1,v2) -> v1.compareTo(v2));
			return 0;
		});
	}

	private int getCurrentAvailability(int i, int j) {
		return task.getChargesPlane()[i][j].getSuppliers().getCurrentAvailability();
	}

	private int getCurrentNecessity(int i, int j) {
		return task.getChargesPlane()[i][j].getConsumers().getCurrentNecessity();
	}

	private void phaseOne() {
		
		logger.info("Begin calc phase one. ");

		List<MinimalPath> mp = new ArrayList<>();
		
		boolean flag = true;
		
		for ( MinimalPath item : task.getTransportTaxList()) {
			if ((task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().getTransportTax() == 0) && 
			   (flag == true )) {
				mp.add(item);
				continue;
			}

			flag = false;
			
			calcZeroResources(item);
		}
		
		for (MinimalPath item : mp) {
		
			calcZeroResources(item);
		}
		
		logger.info("End calc phase one. " + System.lineSeparator());

	}
	
	private void phaseTwo() {
		logger.info("Begin calc phase two. ");
		
		List<MinimalPath> mp = new ArrayList<>();
		
		for ( MinimalPath item : task.getTransportTaxList()) {
			mp.add(item);
		}
		
		Collections.reverse(mp);
		
		for ( MinimalPath item : mp) {
			if (task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().getCost() != 0) {
					
				int consumerPotential = task.getChargesPlane()[item.getI()][item.getJ()].getConsumers().getPotential();
				int supplierPotential = task.getChargesPlane()[item.getI()][item.getJ()].getSuppliers().getPotential();
				int transportTax = task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().getTransportTax();
				
				Consumer consumer = task.getChargesPlane()[item.getI()][item.getJ()].getConsumers();
				Supplier supplier = task.getChargesPlane()[item.getI()][item.getJ()].getSuppliers();
				
				if (consumerPotential == 0) {
					if (supplierPotential == 0) {
						consumer.setPotential(transportTax);
					} else {
						consumer.setPotential(transportTax - supplierPotential);
					}
			    } else {
			    	if (supplierPotential == 0) { 
			    		supplier.setPotential(transportTax - consumerPotential); 
			    	} 
				}
			
				logger.info("index: " + item.getTax());
			}
		
		}
		
		logger.info("End calc phase two. " + System.lineSeparator());
	}

	private void phaseThree() {
		logger.info("Begin calc phase three. ");
		
		for ( MinimalPath item : task.getTransportTaxList()) {
			int consumerPotential = task.getChargesPlane()[item.getI()][item.getJ()].getConsumers().getPotential();
			int supplierPotential = task.getChargesPlane()[item.getI()][item.getJ()].getSuppliers().getPotential();
			int transportTax = task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().getTransportTax();
			
			Consumer consumer = task.getChargesPlane()[item.getI()][item.getJ()].getConsumers();
			Supplier supplier = task.getChargesPlane()[item.getI()][item.getJ()].getSuppliers();
			
			if (task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().getCurrentTax() == 0) {
				task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().setCurrentTax(consumerPotential + supplierPotential);
			}
		}
		
		logger.info("End calc phase three. " + System.lineSeparator());
	}

	private void phaseFour() {
		logger.info("Begin calc phase four. ");

		boolean result = true;
		
		for ( MinimalPath item : task.getTransportTaxList()) {
			int consumerPotential = task.getChargesPlane()[item.getI()][item.getJ()].getConsumers().getPotential();
			int supplierPotential = task.getChargesPlane()[item.getI()][item.getJ()].getSuppliers().getPotential();
			int transportTax = task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().getTransportTax();
			
			Consumer consumer = task.getChargesPlane()[item.getI()][item.getJ()].getConsumers();
			Supplier supplier = task.getChargesPlane()[item.getI()][item.getJ()].getSuppliers();
			
			if (task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().getCurrentTax() > 
				task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().getTransportTax()
				) {
				result = false;
			}
		}

		if (result) {
			isOptimal = true;
			logger.info("ChargesPlane is optimal!");
		} else {
			isOptimal = false;
			logger.info("ChargesPlane is not optimal!");
		}
			
		logger.info("End calc phase four. " + System.lineSeparator());
	}

	private void calcZeroResources(MinimalPath item) {
		TransportMatrixValues tmv = new TransportMatrixValues();
		
		tmv.setCurrentTax(task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().getCurrentTax());
		tmv.setTransportTax(task.getChargesPlane()[item.getI()][item.getJ()].getTransportMatrix().getTransportTax());
		if (getCurrentAvailability(item.getI(), item.getJ()) >= getCurrentNecessity(item.getI(), item.getJ())) {
			tmv.setCost(getCurrentNecessity(item.getI(), item.getJ()));
			task.getChargesPlane()[item.getI()][item.getJ()].getSuppliers().setCurrentAvailability(
					getCurrentAvailability(item.getI(), item.getJ()) - tmv.getCost());	
			task.getChargesPlane()[item.getI()][item.getJ()].getConsumers().setCurrentNecessity(0);	
		} else {
			tmv.setCost(getCurrentAvailability(item.getI(), item.getJ()));
			task.getChargesPlane()[item.getI()][item.getJ()].getConsumers().setCurrentNecessity(
					getCurrentNecessity(item.getI(), item.getJ()) - tmv.getCost());	
			task.getChargesPlane()[item.getI()][item.getJ()].getSuppliers().setCurrentAvailability(0);	
		}

		task.getChargesPlane()[item.getI()][item.getJ()].setTransportMatrix(tmv);
	}

}
