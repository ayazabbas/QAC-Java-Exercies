
public class UniqueSum {
	
	public static void main(String[] args) {
		System.out.println(sumUniqueNumbers(1, 1, 1));
	}
	
	static int sumUniqueNumbers(int num1, int num2, int num3) {
		int result = 0;
		if(num1 == num2 && num1 != num3) {
			result = num3;
		} else if(num1 == num3 && num1 != num2) {
			result = num2;
		} else if(num2 == num3 && num2 != num1) {
			result = num1;
		} else if(num1 != num2 && num1 != num3 && num2 != num3) {
			result = num1 + num2 + num3;
		}
		return result;
	}
}
