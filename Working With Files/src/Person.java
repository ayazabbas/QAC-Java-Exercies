
public class Person {
	
	private String name;
	private String occupation;
	private String age;
	
	public Person(String name, String occupation, String age) {
		this.setName(name);
		this.setOccupation(occupation);
		this.setAge(age);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOccupation() {
		return occupation;
	}
	
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public String getAge() {
		return age;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
	
	public String toString() {
		return "Name: " + name + ". Occupation: " + occupation + ". Age: " + age + ".";
	}
	
}
