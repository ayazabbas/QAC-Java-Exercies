
public class Motorcycle extends Vehicle {
	
	private int engineSize;

	public Motorcycle(int engineSize, int topSpeed) {
		super(2, topSpeed);
		this.engineSize = engineSize;
	}

	public int getEngineSize() {
		return engineSize;
	}

	public void setEngineSize(int engineSize) {
		this.engineSize = engineSize;
	}
	
	public String toString() {
		return engineSize + " cc motorcycle.";
	}
	
	public int calculateCost() {
		return this.engineSize / 20;
	}

}
