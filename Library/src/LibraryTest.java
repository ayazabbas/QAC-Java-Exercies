import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LibraryTest {

	private Library library;
	private Book b1;
	private Book b2;
	private Book harryPotter;
	private User u1;
	private User u2;
	private User ayaz;

	@Before
	public void setUp() {
		Library.setCurrentItemID(0);
		Library.setCurrentUserID(0);
		library = new Library();
		harryPotter = new Book("Harry Potter", "JK Rowling", new Date().toString(), false, false);
		ayaz = new User("Ayaz");
		b1 = new Book("", "", new Date().toString(), false, false);
		b2 = new Book("", "", new Date().toString(), false, false);
		u1 = new User("a");
		u2 = new User("b");
	}
	
	@Test
	public void testAddItem() {
		library.addItem(b1);
		library.addItem(b2);
		library.addItem(harryPotter);
		assertEquals(true, library.getListOfItems().contains(harryPotter) && harryPotter.getItemID().equals("B2"));
	}

	@Test
	public void testRemoveItem() {
		library.addItem(harryPotter);
		library.removeItem(harryPotter);
		assertEquals(false, library.getListOfItems().contains(harryPotter));
	}

	@Test
	public void testAddUser() {
		library.addUser(u1);
		library.addUser(u2);
		library.addUser(ayaz);
		assertEquals(true, library.getListOfUsers().contains(ayaz) && ayaz.getUserID().equals("U2"));
	}

	@Test
	public void testRemoveUser() {
		library.addUser(ayaz);
		library.removeUser(ayaz);
		assertEquals(false, library.getListOfUsers().contains(ayaz));
	}

	@Test
	public void testBorrowItem() {
		library.addItem(harryPotter);
		ayaz.borrowItem(harryPotter);
		assertEquals(true, harryPotter.isCheckedOut() && ayaz.getListOfBorrowedItems().contains(harryPotter));
	}

	@Test
	public void testReturnItem() {
		library.addItem(harryPotter);
		ayaz.borrowItem(harryPotter);
		ayaz.returnItem(harryPotter);
		assertEquals(false, harryPotter.isCheckedOut() && ayaz.getListOfBorrowedItems().contains(harryPotter));
	}
	
	@Test
	public void testFindObject() {
		library.addItem(harryPotter);
		assertEquals(true, harryPotter.equals(library.getInList(library.getListOfItems(), harryPotter)));
	}
	
	@Test
	public void testFindByAuthor() {
		library.addItem(harryPotter);
		assertEquals(true, library.getItemsByAuthor(library.getListOfItems(), "JK Rowling").contains(harryPotter));
	}

	@Test
	public void testFindBytitle() {
		library.addItem(harryPotter);
		assertEquals(true, library.getItemsByTitle(library.getListOfItems(), "Harry Potter").contains(harryPotter));
	}
	
	@Test
	public void testFindItemByID() {
		library.addItem(harryPotter);
		assertEquals(true, library.getItemByID(library.getListOfItems(), "B0").equals(harryPotter));
	}
	
	@Test
	public void testFindUsersByName() {
		User ayaz2 = new User("Ayaz");
		library.addUser(ayaz);
		library.addUser(u1);
		library.addUser(u2);
		library.addUser(ayaz2);
		assertEquals(true, library.getUsersByName("Ayaz").contains(ayaz) && library.getUsersByName("Ayaz").contains(ayaz2));
	}
	
	@Test
	public void testFindUserByID() {
		library.addUser(ayaz);
		assertEquals(true, library.getUserByID("U0").equals(ayaz));
	}
	
	@Test
	public void readFromFile() {
		Library test = new Library();
		test.readLibraryFromFile("test.txt");
		User amrit = test.getUserByID("U3");
		List<Item> amritBorrowed = amrit.getListOfBorrowedItems();
		Item nature = amrit.getItemByID(amritBorrowed, "J3");
		Item hp = amrit.getItemByID(amritBorrowed, "B0");
		assertEquals(true, nature.getTitle().equals("Nature") && hp.getDueDate().equals("30/08/2017"));
	}
	
	@Test
	public void testWriteToFile() {
		Library lib1 = new Library();
		Library lib2 = new Library();
		lib1.readLibraryFromFile("test.txt");
		lib1.writeLibraryToFile("test2.txt");
		lib2.readLibraryFromFile("test2.txt");
		List<Item> items1 = lib1.getListOfItems();
		List<Item> items2 = lib2.getListOfItems();
		List<User> users1 = lib1.getListOfUsers();
		List<User> users2 = lib2.getListOfUsers();
		assertEquals(true, items1.get(1).getItemID().equals(items2.get(1).getItemID()) && users1.get(1).getUserID().equals(users2.get(1).getUserID()));
	}
}
