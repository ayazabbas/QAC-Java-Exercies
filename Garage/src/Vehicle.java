
public abstract class Vehicle {
	private int numberOfWheels;
	private int topSpeed;
	private int id;
	
	public Vehicle(int numberOfWheels, int topSpeed) {
		this.numberOfWheels = numberOfWheels;
		this.topSpeed = topSpeed;
	}

	public int getNumberOfWheels() {
		return numberOfWheels;
	}

	public void setNumberOfWheels(int numberOfWheels) {
		this.numberOfWheels = numberOfWheels;
	}

	public int getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(int topSpeed) {
		this.topSpeed = topSpeed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public abstract int calculateCost();
	
	public abstract String toString();
	

}
