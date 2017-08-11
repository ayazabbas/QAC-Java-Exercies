
public abstract class Item {
	private String itemID;
	private String author;
	private String datePublished;
	private String dueDate;
	private String title;
	private boolean isCheckedOut;

	public Item(String title, String author, String datePublished) {
		super();
		this.itemID = null;
		this.author = author;
		this.datePublished = datePublished;
		this.title = title;
		this.isCheckedOut = false;
		this.dueDate = null;
	}
	
	public Item(String itemID, String title, String author, String datePublished, String dueDate, boolean isCheckedOut) {
		super();
		this.itemID = itemID;
		this.author = author;
		this.datePublished = datePublished;
		this.title = title;
		this.dueDate = dueDate;
		this.isCheckedOut = isCheckedOut;
	}

	public String toString() {
		String result = "ID: " + itemID + " | Title: " + title + " | Author: " + author + " | Published: " + datePublished;
		if (isCheckedOut)
			result += " | Due: " + dueDate;
		return result;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isCheckedOut() {
		return isCheckedOut;
	}

	public void setCheckedOut(boolean isCheckedOut) {
		this.isCheckedOut = isCheckedOut;
	}

	public String getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

}
