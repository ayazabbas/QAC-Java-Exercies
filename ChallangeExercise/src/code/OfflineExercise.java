package code;

public class OfflineExercise {

	// Given a string, return a string where
	// for every char in the original,
	// there are two chars.

	// doubleChar("The") → "TThhee"
	// doubleChar("AAbb") → "AAAAbbbb"
	// doubleChar("Hi-There") → "HHii--TThheerree"

	public static void main(String[] args) {
		System.out.println(evenlySpaced(4, 6, 2));
	}
	
	public String doubleChar(String input) {
		String result = "";
		for (int i = 0; i < input.length(); i++) {
			result = result + input.charAt(i) + input.charAt(i);
		}
		return result;
	}
	//
	// A sandwich is two pieces of bread with something in between. Return the
	// string that is between the first and last appearance of "bread" in the
	// given string, or return the empty string "" if there are not two pieces
	// of bread.

	// getSandwich("breadjambread") → "jam"
	// getSandwich("xxbreadjambreadyy") → "jam"
	// getSandwich("xxbreadyy") → ""

	public String getSandwich(String input) {
		String result = "";
		String firstBread = "";
		int firstIndex = input.indexOf("bread");
		if (firstIndex != -1) {
			for (int i = firstIndex + 5; i < input.length(); i++) {
				firstBread = firstBread + input.charAt(i);
			}
		} else
			return "";
		int secondIndex = firstBread.indexOf("bread");
		if (secondIndex != -1) {
			for (int i = 0; i < secondIndex; i++) {
				result = result + firstBread.charAt(i);
			}
		}
		return result;
	}

	// Given three ints, a b c, one of them is small, one is medium and one is
	// large. Return true if the three values are evenly spaced, so the
	// difference between small and medium is the same as the difference between
	// medium and large.

	// evenlySpaced(2, 4, 6) → true
	// evenlySpaced(4, 6, 2) → true
	// evenlySpaced(4, 6, 3) → false

	public static boolean evenlySpaced(int a, int b, int c) {
		if ((b - a == c - b || b - a == -(c - b))) {
			return true;
		}
		return false;
	}

	// Given a string and an int n, return a string made of the first and last n
	// chars from the string. The string length will be at least n.

	// nTwice("Hello", 2) → "Helo"
	// nTwice("Chocolate", 3) → "Choate"
	// nTwice("Chocolate", 1) → "Ce"

	public String nTwice(String input, int a) {
		String result = "";
		for (int i = 0; i < a; i++) {
			result = result + input.charAt(i);
		}
		for (int i = input.length() - a; i < input.length(); i++) {
			result = result + input.charAt(i);
		}
		return result;
	}

	// Given a string, return true if it ends in "ly".

	// endsLy("oddly") → true
	// endsLy("y") → false
	// endsLy("oddy") → false

	public boolean endsly(String input) {
		if (input.endsWith("ly"))
			return true;
		return false;
	}

	// Given a string, return recursively a "cleaned" string where adjacent
	// chars that are the same have been reduced to a single char. So "yyzzza"
	// yields "yza".

	// stringClean("yyzzza") → "yza"
	// stringClean("abbbcdd") → "abcd"
	// stringClean("Hello") → "Helo"
	public static String stringClean(String input) {
		String result = "" + input.charAt(0);
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != result.charAt(result.length() - 1)) {
				result = result + input.charAt(i);
			}
		}
		return result;
	}

	// The fibonacci sequence is a famous bit of mathematics, and it happens to
	// have a recursive definition. The first two values in the sequence are 0
	// and 1 (essentially 2 base cases). Each subsequent value is the sum of the
	// previous two values, so the whole sequence is: 0, 1, 1, 2, 3, 5, 8, 13,
	// 21 and so on. Define a recursive fibonacci(n) method that returns the nth
	// fibonacci number, with n=0 representing the start of the sequence.

	// fibonacci(0) → 0
	// fibonacci(1) → 1
	// fibonacci(2) → 1

	public static int fibonacci(int input) {
		int first = 0;
		int second = 1;
		int result = 0;
		if (input == 0) {
			return 0;
		} else if (input == 1) {
			return 1;
		}
		for (int i = 1; i < input; i++) {
			result = first + second;
			first = second;
			second = result;
		}
		return result;
	}

	// We have a number of bunnies and each bunny has two big floppy ears. We
	// want to compute the total number of ears across all the bunnies
	// recursively (without loops or multiplication).
	//
	// bunnyEars(0) → 0
	// bunnyEars(1) → 2
	// bunnyEars(2) → 4

	public int bunnyEars(int input) {
		return 2*input;
	}

}
