
public class Car extends Vehicle {

	private int numberOfDoors;
	
	public Car(int numberOfDoors, int topSpeed) {
		super(4, topSpeed);
		this.numberOfDoors = numberOfDoors;
	}

	public int getNumberOfDoors() {
		return numberOfDoors;
	}

	public void setNumberOfDoors(int numberOfDoors) {
		this.numberOfDoors = numberOfDoors;
	}
	
	public String toString() {
		return numberOfDoors + " door car.";
	}
	
	public int calculateCost() {
		return this.numberOfDoors * 10;
	}

}
