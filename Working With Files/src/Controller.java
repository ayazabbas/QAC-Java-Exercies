import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

	public static void main(String[] args) {
		String fileName = "people.txt";

		ArrayList<Person> peopleToWrite = new ArrayList<Person>();
		ArrayList<Person> readPeople = new ArrayList<Person>();

		Person gandalf = new Person("Gandalf", "Wizard", "2019");
		Person frodo = new Person("Frodo", "Ring Bearer", "33");
		Person sam = new Person("Sam", "Bodyguard", "50");
		Person aragorn = new Person("Aragorn", "Ranger", "87");
		Person legolas = new Person("Legolas", "Cool Elf", "2931");

		peopleToWrite.add(gandalf);
		peopleToWrite.add(frodo);
		peopleToWrite.add(sam);
		peopleToWrite.add(aragorn);
		peopleToWrite.add(legolas);

		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Person p : peopleToWrite) {
				bw.write(p.getName() + "," + p.getOccupation() + "," + p.getAge() + System.lineSeparator());
			}
			bw.close();
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(",");
				if (fields.length == 3) {
					readPeople.add(new Person(fields[0], fields[1], fields[2]));
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Person p : readPeople) {
			System.out.println(p.toString());
		}
	}

}
