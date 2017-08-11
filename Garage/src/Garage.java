import java.util.ArrayList;
import java.util.Iterator;

public class Garage {
	private int size; //area in sq m
	private int currentId = 0;
	private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

	public Garage(int size){
		this.size = size;
	}

	public void listVehicles() {
		if(vehicles.size() == 0) {
			System.out.println("Garage is empty.");
		} else {
			for(Vehicle v : vehicles) {
				System.out.println(v.getId() + ": " + v.toString());
			}
		}
	}

	public void addVehicle(Vehicle v) {
		vehicles.add(v);
		v.setId(currentId);
		currentId += 1;
	}
	
	public void addVehicle(Vehicle v, int id) {
		vehicles.add(v);
		v.setId(id);
		currentId += 1;
	}

	public void removeVehicle(int id) {
		for(Vehicle v : vehicles){
			if(v.getId() == id) {
				vehicles.remove(v);
				break;
			}
		}
	}

	public void removeAllVehiclesOfType(String type) {
		Iterator<Vehicle> iter = vehicles.iterator();
		while(iter.hasNext()) {
			Vehicle v = iter.next();
			switch (type) {
			case "car":
				if(v instanceof Car) {
					iter.remove();
				}
				break;
			case "motorcycle":
				if(v instanceof Motorcycle) {
					iter.remove();
				}
				break;
			case "truck":
				if(v instanceof Truck) {
					iter.remove();
				}
				break;
			}
		}
		
	}

	public int calculateBill() {
		int bill = 100 + (size / 10);
		for(Vehicle v : vehicles) {
			bill += v.calculateCost();
		}
		return bill;
	}

	public void emptyGarage() {
		vehicles.clear();
	}

	public int getNumberOfVehicles() {
		return vehicles.size();
	}
	
	public void printGarageDetails() {
		this.listVehicles();
		System.out.println("There are " + this.getNumberOfVehicles() + " vehicles in this garage.");
		System.out.println("Bill: £" + this.calculateBill() + " per month");
		System.out.println();
	}

}