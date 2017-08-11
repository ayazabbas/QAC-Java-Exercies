
public class BeginnerExercises {

	static String output = "Hello World!";
	static int exercise = 11;
	static int[] array = new int[10];
	
	public static void main(String[] args) {
		switch(exercise) {
		case 1: // HELLO WORLD!
			System.out.println("Hello World!");
			break;
		case 2: // ASSIGNMENT
			System.out.println(output);
			break;
		case 3: // PARAMETERS
			outputString("Hello World!");
			break;
		case 4: // RETURN TYPES
			System.out.println(returnHelloWorld());
			break;
		case 5: // PARAMETERS/OPERATORS
			System.out.println(sumTwoNumbers(2, 3));
			break;
		case 6: // CONDITIONALS
			System.out.println(sumOrMultiplyTwoNumbers(false, 2, 3));
			break;
		case 7: // CONDITIONALS 2
			System.out.println(sumOrMultiplyTwoNumbers(false, 0, 4));
			break;
		case 8: // ITERATION
			for(int i=0; i<10; i++) {
				System.out.println(sumOrMultiplyTwoNumbers(false, 3, i));
			}
			break;
		case 9: // ARRAYS
			for(int i=0; i < 10; i++) {
				array[i] = i;
			}
			System.out.println(sumOrMultiplyTwoNumbers(false, 3, array[6]));
			break;
		case 10: // ITERATION/ARRAYS
			for(int i=0; i < 10; i++) {
				array[i] = i;
			}
			for(int i=0; i<array.length; i++) {
				System.out.println(array[i]);
			}
		case 11: // ITERATION/ARRAYS 2
			for(int i=0; i < 10; i++) {
				array[i] = i;
				System.out.println(array[i]);
			}
			for(int i=0; i<array.length; i++) {
				array[i] = array[i] * 10;
				System.out.println(array[i]);
			}
		}
			
	}

	static void outputString(String output) {
		System.out.println(output);
	}
	
	static String returnHelloWorld() {
		return "Hello World!";
	}
	
	static int sumTwoNumbers(int num1, int num2) {
		return num1 + num2;
	}
	
	static int sumOrMultiplyTwoNumbers(boolean addNumbers, int num1, int num2) {
		int result;
		if(num1 == 0) {
			return num2;
		}
		if(num2 == 0) {
			return num1;
		}
		if(addNumbers) {
			result = num1 + num2;
		} else {
			result = num1 * num2;
		}
		return result;
	}
	
}
