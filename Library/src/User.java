import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class User implements ListManager {
	private String userID;
	private String name;
	private List<Item> listOfBorrowedItems = new ArrayList<Item>();

	public User(String name) {
		super();
		this.name = name;
		userID = null;
	}
	
	public User(String userID, String name) {
		super();
		this.userID = userID;
		this.name = name;
	}

	public String toString() {
		return "ID: " + userID + " | " + "Name: " + name + " | " + "Number Of Borrowed Items: "
				+ listOfBorrowedItems.size();
	}

	public void borrowItem(Item i) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 21);
		listOfBorrowedItems.add(i);
		i.setCheckedOut(true);
		i.setDueDate(new Date(cal.getTimeInMillis()).toString());
	}

	public void returnItem(Item i) {
		getListOfBorrowedItems().remove(i);
		i.setCheckedOut(false);
		i.setDueDate(null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public List<Item> getListOfBorrowedItems() {
		return listOfBorrowedItems;
	}

	public void setListOfBorrowedItems(List<Item> listOfBorrowedItems) {
		this.listOfBorrowedItems = listOfBorrowedItems;
	}

}
