
public class Magazine extends Item {

	private boolean isInColour;

	public Magazine(String author, String title, String datePublished, boolean isInColour) {
		super(title, author, datePublished);
		this.isInColour = isInColour;
	}
	
	public Magazine(String itemID, String title, String author, String datePublished, boolean isInColour, boolean isCheckedOut, String dueDate) {
		super(itemID, title, author, datePublished, dueDate, isCheckedOut);
		this.isInColour = isInColour;
	}

	public boolean isInColour() {
		return isInColour;
	}

	public void setInColour(boolean isInColour) {
		this.isInColour = isInColour;
	}

}
