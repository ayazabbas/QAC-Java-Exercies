
public class GarageController {
	
	public static void main(String[] args) {
		Garage garage1 = new Garage(500);
		Car car1 = new Car(4, 130);
		Car car2 = new Car(5, 110);
		Motorcycle mc1 = new Motorcycle(125, 40);
		Motorcycle mc2 = new Motorcycle(600, 90);
		Truck truck1 = new Truck(15, 12, 110);
		Truck truck2 = new Truck(20, 16, 100);
		
		garage1.addVehicle(car1);
		garage1.addVehicle(car2);
		garage1.addVehicle(mc1, 15);
		garage1.addVehicle(mc2);
		garage1.addVehicle(truck1);
		garage1.addVehicle(truck2);
		
		garage1.printGarageDetails();
		
		garage1.removeVehicle(4);
		garage1.printGarageDetails();
		
		garage1.removeAllVehiclesOfType("car");
		garage1.printGarageDetails();
		
		garage1.emptyGarage();
		garage1.printGarageDetails();
	}

}
