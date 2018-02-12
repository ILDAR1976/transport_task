package edu.transport_task.entity;

public class MinimalPath {
	private int i;
	private int j;
	private int tax;
	
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public int getTax() {
		return tax;
	}
	public void setTax(int tax) {
		this.tax = tax;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		result = prime * result + tax;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MinimalPath other = (MinimalPath) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		if (tax != other.tax)
			return false;
		return true;
	}
	public int compareTo(MinimalPath mp) {
		if (this.tax == mp.tax) {
			return 0;
		} else if (this.tax > mp.tax) {
			return 1;
		} else {
			return -1;
		} 
	}
	@Override
	public String toString() {
		String out = "";
		out += " index ixj: " + this.i + " x " + this.j;
		out += " value tax: " + this.tax + System.lineSeparator();
		return out;
	}
}
