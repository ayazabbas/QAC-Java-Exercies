
public class Person {
	private String name;
	private String jobTitle;
	private int age;
	
	public Person(String name, int age, String jobTitle) {
		this.name = name;
		this.age = age;
		this.jobTitle = jobTitle;
	}
	
	public String toString() {
		return "Name: " + name + "\n" + "Age: " + age + "\n" + "Job Title: " + jobTitle;
	}
}
