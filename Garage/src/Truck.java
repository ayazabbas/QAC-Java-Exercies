
public class Truck extends Vehicle{

	private int length;
	
	public Truck(int length, int numberOfWheels, int topSpeed) {
		super(numberOfWheels, topSpeed);
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public String toString() {
		return length + "m truck.";
	}

	public int calculateCost() {
		return 100 + (this.length * 5);
	}
}
