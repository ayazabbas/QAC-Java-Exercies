
public class Room {
	
	private int roomArea; //area to paint in sq m

	public Room(int area) {
		this.setRoomArea(area);
	}
	
	public int getRoomArea() {
		return roomArea;
	}

	public void setRoomArea(int area) {
		this.roomArea = area;
	}
	
	public PaintTin getBestPaint(PaintTin[] paints) {
		double bestCost = 0;
		int bestPaintIndex = 0;
		for(int i=0; i < paints.length; i++) {
			double paintPrice = paints[i].getPriceForRoom(this);
			if(bestCost == 0 || paintPrice < bestCost) {
				bestCost = paintPrice;
				bestPaintIndex = i;
			}
		}
		return paints[bestPaintIndex];
	}
	
}
