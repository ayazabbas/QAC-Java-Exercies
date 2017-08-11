
public class PaintTin {
	
	private double capacity;
	private double pricePerTin;
	private double efficiency; //area covered per litre (sq m)
	private String name;
	
	public PaintTin(String name, double capacity, double price, double efficiency) {
		this.setName(name);
		this.setCapacity(capacity);
		this.setPricePerTin(price);
		this.setEfficiency(efficiency);
	}
	
	public String toString() {
		return name + " £" + pricePerTin + " per tin. Covers " + efficiency + " sq m per litre.";
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public double getPricePerTin() {
		return pricePerTin;
	}

	public void setPricePerTin(double price) {
		this.pricePerTin = price;
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}
	
	public double getPriceForRoom(Room r) {
		return getNumberOfTinsNeededForRoom(r) * pricePerTin;
	}
	
	public double getWastedPaintForRoom(Room r) {
		int area = r.getRoomArea();
		return (getNumberOfTinsNeededForRoom(r) * capacity) - (area / efficiency);
	}
	
	public double getNumberOfTinsNeededForRoom(Room r) {
		int area = r.getRoomArea();
		double tinsNeeded = Math.ceil((area / efficiency) / capacity);
		if(tinsNeeded < 1) {
			return 1;
		}
		return tinsNeeded;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
