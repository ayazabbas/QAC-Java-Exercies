
public class Strings {

	public static void main(String[] args) {
		System.out.println(findLongestCommonSubsequence("yyyyyyabcdefghijkyyyy", "xxxxabcdexxdefxxxx"));
	}

	static String findLongestCommonSubsequence(String string1, String string2) {
		String result = "";
		String subsequence = "";
		char[] a = string1.toCharArray();
		char[] b = string2.toCharArray();

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b.length; j++) {
				if (a[i] == b[j]) {
					for (int n = 0; n < a.length - i && n < b.length - j; n++) {
						if (a[i + n] == b[j + n]) {
							subsequence = subsequence + a[i + n];
						} else {
							break;
						}
					}
				}
			}
			if (subsequence.length() > result.length()) {
				result = subsequence;
			}
			subsequence = "";
		}
		return result;
	}

}
