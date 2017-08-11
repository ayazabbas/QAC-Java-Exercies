import java.text.DecimalFormat;

public class PaintWizard {
	
	public static void main(String[] args) {
		PaintTin cheapoMax = new PaintTin("CheapoMax", 20, 19.99, 10);
		PaintTin averageJoes = new PaintTin("AverageJoes", 15, 17.99, 11);
		PaintTin deluxePaints = new PaintTin("DuluxourousPaints", 10, 25, 20);
		Room room = new Room(100);
		PaintTin[] paints = {cheapoMax, averageJoes, deluxePaints};
		PaintTin bestPaint = room.getBestPaint(paints);
		
		DecimalFormat twoDP = new DecimalFormat("##.00");
		
		System.out.println("The best paint to use is " + bestPaint.toString());
		System.out.println("You need to buy " + (int) bestPaint.getNumberOfTinsNeededForRoom(room) + " tins which will cost £" + twoDP.format(bestPaint.getPricePerTin() * bestPaint.getNumberOfTinsNeededForRoom(room)));
		System.out.println("You will waste " + twoDP.format(bestPaint.getWastedPaintForRoom(room)) + " litres of paint.");
	}
	
}
