
public class TooHot {
	
	public static void main(String[] args) {
		System.out.println(isTheTemperatureRight(95, false));
	}
	
	static boolean isTheTemperatureRight(int temperature, boolean isSummer) {
		boolean result = false;
		int lowerBound = 60;
		int upperBound = 90;
		if(isSummer) {
			upperBound = 100;
		}
		if(temperature >= lowerBound && temperature <= upperBound) {
			result = true;
		}
		return result;
	}
}
