
public class Blackjack {

	public static void main(String[] args) {
		System.out.println(winningValue(20, 21));
	}
	
	static int winningValue(int value1, int value2) {
		int winner = 0;
		if(value1 > 21 && value2 > 21) {
			winner = 0;
		} else if(value1 <= 21 && value2 <= 21) {
			winner = Math.max(value1, value2);
		} else if(value1 <= 21 && value2 > 21) {
			winner = value1;
		} else if(value1 > 21 && value2 <= 21) {
			winner = value2;
		}
		return winner;
	}

}
