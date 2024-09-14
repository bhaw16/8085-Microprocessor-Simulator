import java.util.*;


public class NumberSystems {
	private static List<Integer> decimal = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
	private static List<Integer> binary = List.of(0, 1, 10, 11, 100, 101, 110, 111, 1000, 1001, 1010, 1011, 1100, 1101, 1110, 1111);
	private static List<Character> hex = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F');
	//binary.get(hex.indexOf('2'));
	
	public static String binaryToHex(int[] binaryNo) {
		StringBuilder hexNum = new StringBuilder();
		for (int i = 0; i < binaryNo.length; i += 4) {
			int binaryNum = 0;
			int place = 3;
			for (int j = 0; j < 4; j++) {
				binaryNum += binaryNo[i + j] * Math.pow(10, place);
				place--;
			}
			hexNum.append(hex.get(binary.indexOf(binaryNum)));
		}
		return hexNum.toString();
	}
	
	public static int[] hexToBinary(char[] hexNum) {
		List<Integer> binaryN = new ArrayList<>();
		for (int i = 0; i < hexNum.length; i++) {
			String b = new String();
			if (hex.contains(hexNum[i])) {
				b = String.valueOf(binary.get(hex.indexOf(hexNum[i])));
			}
			while (b.length() < 4) {
				b = new StringBuilder("0").append(b).toString();
			}
			for (int j = 0; j < b.length(); j++) {
				binaryN.add(Integer.parseInt(b.substring(j, j + 1)));
			}
		}
		int[] binaryNum = new int[binaryN.size()];
		for (int i = 0; i < binaryNum.length; i++) {
			binaryNum[i] = binaryN.get(i);
		}
		return binaryNum;
	}
	
	public static int binaryToDecimal(int[] binary) {
		int place = 0;
		int decimal = 0;
		for (int i = binary.length - 1; i > -1; i--) {
			int temp = (int)(binary[i] * Math.pow(2, place));
			decimal += temp;
			place++;
		}
		return decimal;
	}
	
	public static int[] decimalToBinary(int decimal) {
		List<Integer> binaryNum = new ArrayList<>();
		while (decimal > 2) {
			binaryNum.add(0, decimal % 2);
			decimal /= 2;
		}
		binaryNum.add(0, 1);
		int[] binaryNo = new int[binaryNum.size()];
		for (int i = 0; i < binaryNum.size(); i++) {
			binaryNo[i] = binaryNum.get(i);
		}
		return binaryNo;
	}
	
	public static char[] decimalToHex(int decimal) {
		return binaryToHex(decimalToBinary(decimal)).toCharArray();
	}
	
	public static int hexToDecimal(char[] hexadecimal) {
		return binaryToDecimal(hexToBinary(hexadecimal));
	}
	
	public static int[] getMemoryAddress(String hexadecimal) {
		int sum = 0;
		int place = hexadecimal.length() - 2;
		for (int i = 0; i < hexadecimal.length() - 2; i++) {
			sum += Math.pow(16, place) * decimal.get(hex.indexOf(hexadecimal.charAt(i)));
			place--;
		}
		int[] indices = {sum, decimal.get(hex.indexOf(hexadecimal.charAt(hexadecimal.length() - 1)))};
		return indices;
	}
}
