
public class Journal extends Item {
	
	private int volumeNumber;

	public Journal(String title, String author, String datePublished, int volumeNumber) {
		super(title, author, datePublished);
		this.volumeNumber = volumeNumber;
	}
	
	public Journal(String itemID, String title, String author, String datePublished, int volumeNumber, boolean isCheckedOut, String dueDate) {
		super(itemID, title, author, datePublished, dueDate, isCheckedOut);
		this.volumeNumber = volumeNumber;
	}

	public int getVolumeNumber() {
		return volumeNumber;
	}

	public void setVolumeNumber(int volumeNumber) {
		this.volumeNumber = volumeNumber;
	}

}
