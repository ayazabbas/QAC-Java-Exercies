
public class Book extends Item {

	private boolean isHardBack;
	private boolean isLargePrint;

	public Book(String title, String author, String datePublished, boolean isHardBack, boolean isLargePrint) {
		super(title, author, datePublished);
		this.isHardBack = isHardBack;
		this.isLargePrint = isLargePrint;
	}

	public Book(String itemID, String title, String author, String datePublished, boolean isHardBack,
			boolean isLargePrint, boolean isCheckedOut, String dueDate) {
		super(itemID, title, author, datePublished, dueDate, isCheckedOut);
		this.isHardBack = isHardBack;
		this.isLargePrint = isLargePrint;
	}

	public boolean isHardBack() {
		return isHardBack;
	}

	public void setHardBack(boolean isHardBack) {
		this.isHardBack = isHardBack;
	}

	public boolean isLargePrint() {
		return isLargePrint;
	}

	public void setLargePrint(boolean isLargePrint) {
		this.isLargePrint = isLargePrint;
	}

}
