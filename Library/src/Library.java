import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Library implements ListManager {

	public static int currentItemID;
	public static int currentUserID;
	private List<Item> listOfItems = new ArrayList<Item>();
	private List<User> listOfUsers = new ArrayList<User>();

	public Library() {

	}

	public static int getCurrentItemID() {
		return currentItemID;
	}

	public static void setCurrentItemID(int currentItemID) {
		Library.currentItemID = currentItemID;
	}

	public static int getCurrentUserID() {
		return currentUserID;
	}

	public static void setCurrentUserID(int currentUserID) {
		Library.currentUserID = currentUserID;
	}

	public List<User> getUsersByName(String name) {
		List<User> result = new ArrayList<User>();
		Iterator<User> iter = listOfUsers.iterator();
		while (iter.hasNext()) {
			User u = iter.next();
			if (u.getName().equals(name)) {
				result.add(u);
			}
		}
		return result;
	}

	public User getUserByID(String ID) {
		Iterator<User> iter = listOfUsers.iterator();
		while (iter.hasNext()) {
			User u = iter.next();
			if (u.getUserID().equals(ID)) {
				return u;
			}
		}
		return null;
	}

	public void writeLibraryToFile(String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("items,," + currentItemID + System.lineSeparator());
			Iterator<Item> itemIter = listOfItems.iterator();
			while (itemIter.hasNext()) {
				Item i = itemIter.next();
				String uniqueStuff = "";
				String itemType = i.getItemID().charAt(0) + "";
				switch (itemType) {
				case "B":
					Book b = (Book) i;
					if (b.isHardBack()) {
						uniqueStuff += ",,true";
					} else
						uniqueStuff += ",,false";
					if (b.isLargePrint()) {
						uniqueStuff += ",,true";
					} else
						uniqueStuff += ",,false";
					break;
				case "J":
					Journal j = (Journal) i;
					uniqueStuff += ",," + j.getVolumeNumber();
					break;
				case "M":
					Magazine m = (Magazine) i;
					if (m.isInColour()) {
						uniqueStuff += ",,true";
					} else
						uniqueStuff += ",,false";
				}
				if (i.isCheckedOut()) {
					uniqueStuff += ",,true,," + i.getDueDate();
				} else
					uniqueStuff += ",,false";
				bw.write(i.getItemID() + ",," + i.getTitle() + ",," + i.getAuthor() + ",," + i.getDatePublished()
						+ uniqueStuff + System.lineSeparator());
			}
			bw.write("users,," + currentUserID + System.lineSeparator());
			Iterator<User> userIter = listOfUsers.iterator();
			while (userIter.hasNext()) {
				User u = userIter.next();
				String uniqueStuff = "";
				List<Item> borrowed = u.getListOfBorrowedItems();
				if (borrowed.size() > 0) {
					for (int i = 0; i < borrowed.size(); i++) {
						uniqueStuff += ",," + borrowed.get(i).getItemID();
					}
				}
				bw.write(u.getUserID() + ",," + u.getName() + uniqueStuff + System.lineSeparator());
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readLibraryFromFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			if (line.startsWith("items")) {
				currentItemID = Integer.parseInt("" + line.split(",,")[1]);
				while (!((line = br.readLine()).startsWith("users"))) {
					String fields[] = line.split(",,");
					String itemType = "" + fields[0].charAt(0);
					switch (itemType) {
					case "B":
						boolean isHardBack = false;
						boolean isLargePrint = false;
						if (fields[4].equals("true")) {
							isHardBack = true;
						}
						if (fields[5].equals("true")) {
							isLargePrint = true;
						}
						if (fields.length == 8) {
							this.addItem(new Book(fields[0], fields[1], fields[2], fields[3], isHardBack, isLargePrint,
									true, fields[7]));
						} else if (fields.length == 7) {
							this.addItem(new Book(fields[0], fields[1], fields[2], fields[3], isHardBack, isLargePrint,
									false, null));
						}
						break;
					case "J":
						if (fields.length == 7) {
							this.addItem(new Journal(fields[0], fields[1], fields[2], fields[3],
									Integer.parseInt(fields[4]), true, fields[6]));
						} else if (fields.length == 6) {
							this.addItem(new Journal(fields[0], fields[1], fields[2], fields[3],
									Integer.parseInt(fields[4]), false, null));

						}
						break;
					case "M":
						boolean isInColour = false;
						if (fields[4].equals("true")) {
							isInColour = true;
						}
						if (fields.length == 7) {
							this.addItem(new Magazine(fields[0], fields[1], fields[2], fields[3], isInColour, true,
									fields[6]));
						} else if (fields.length == 6) {
							this.addItem(
									new Magazine(fields[0], fields[1], fields[2], fields[3], isInColour, false, null));

						}
						break;
					}
				}
				if (line.startsWith("users")) {
					currentUserID = Integer.parseInt("" + line.split(",,")[1]);
					while ((line = br.readLine()) != null) {
						String fields[] = line.split(",,");
						User u = new User(fields[0], fields[1]);
						this.addUser(u);
						if (fields.length > 2) {
							for (int i = 2; i < fields.length; i++) {
								u.getListOfBorrowedItems().add(this.getItemByID(listOfItems, fields[i]));
							}
						}
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addUser(User u) {
		listOfUsers.add(u);
		if (u.getUserID() == null) {
			u.setUserID("" + u.getClass().getName().charAt(0) + currentUserID);
			currentUserID += 1;
		}

	}

	public void removeUser(User u) {
		listOfUsers.remove(u);
	}

	public void addItem(Item i) {
		listOfItems.add(i);
		if (i.getItemID() == null) {
			i.setItemID("" + i.getClass().getName().charAt(0) + currentItemID);
			currentItemID += 1;
		}
	}

	public void removeItem(Item i) {
		listOfItems.remove(i);
	}

	public List<Item> getListOfItems() {
		return listOfItems;
	}

	public void setListOfItems(List<Item> listOfItems) {
		this.listOfItems = listOfItems;
	}

	public List<User> getListOfUsers() {
		return listOfUsers;
	}

	public void setListOfUsers(List<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

	public String outputUsers() {
		if (listOfUsers.isEmpty()) {
			return "Library has no users.";
		}
		return "";
	}
}
