import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface ListManager {

	public default Object getInList(List list, Object o) {
		Iterator<Object> iter = list.iterator();
		while (iter.hasNext()) {
			Object inList = iter.next();
			if (inList.equals(o)) {
				return inList;
			}
		}
		return null;
	}
	
	public default Item getItemByID(List<Item> list, String ID) {
		Iterator<Item> iter = list.iterator();
		while (iter.hasNext()) {
			Item i = iter.next();
			if (i.getItemID().equals(ID)) {
				return i;
			}
		}
		return null;
	}

	public default List<Item> getItemsByAuthor(List<Item> list, String author) {
		List<Item> result = new ArrayList<Item>();
		Iterator<Item> iter = list.iterator();
		while (iter.hasNext()) {
			Item i = iter.next();
			if (i.getAuthor().equals(author)) {
				result.add(i);
			}
		}
		return result;
	}

	public default List<Item> getItemsByTitle(List<Item> list, String title) {
		List<Item> result = new ArrayList<Item>();
		Iterator<Item> iter = list.iterator();
		while (iter.hasNext()) {
			Item i = iter.next();
			if (i.getTitle().equals(title)) {
				result.add(i);
			}
		}
		return result;
	}

	public default String outputItems(List<Item> list) {
		String result = "";
		if (list.isEmpty()) {
			result = "No items.";
		} else {
			for (Item i : list) {
				result += i.toString() + "\n";
			}
		}
		return result;
	}
}
