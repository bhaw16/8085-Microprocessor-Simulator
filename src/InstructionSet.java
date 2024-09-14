import java.io.*;
import java.util.*;

public class InstructionSet {
	private static boolean CY = false;
	private static boolean AC = false;
	private static boolean S = false;
	private static boolean P = false;
	private static boolean Z = false;
	
	private static char[] accumulator = {'0', '0'};
	private static char[][] BCRegister = {{'0', '0'}, {'0', '0'}};
	private static char[][] DERegister = {{'0', '0'}, {'0', '0'}};
	private static char[][] HLRegister = {{'0', '0'}, {'0', '0'}};
	private static String[][] memory = new String[4096][16];
	
	public static void initializeMemory(String address, String hex) {
		memory[NumberSystems.getMemoryAddress(address)[0]][NumberSystems.getMemoryAddress(address)[1]] = hex;
	}
	
	public static void LXIH(char[][] address) {
		HLRegister[0] = address[0];
		HLRegister[1] = address[1];
	}
	
	public static void MOVe(char r1, char r2) {
		char[] register2 = new char[2];
		switch(r2) {
			case 'A':
				register2 = accumulator;
				break;
			case 'B':
				register2 = BCRegister[0];
				break;
			case 'C':
				register2 = BCRegister[1];
				break;
			case 'D':
				register2 = DERegister[0];
				break;
			case 'E':
				register2 = DERegister[1];
				break;
			case 'H':
				register2 = HLRegister[0];
				break;
			case 'L':
				register2 = HLRegister[1];
				break;
			default:	//r2 == 'M'
				int[] memoryIndices = NumberSystems.getMemoryAddress(new StringBuilder(new String(HLRegister[0])).append(new StringBuilder(new String(HLRegister[1]))).toString());
				int zero = memoryIndices[0];
				int one = memoryIndices[1];
				register2 = memory[zero][one].toCharArray();
		}
		switch(r1) {
		case 'A':
			accumulator = register2;
			break;
		case 'B':
			BCRegister[0] = register2;
			break;
		case 'C':
			BCRegister[1] = register2;
			break;
		case 'D':
			DERegister[0] = register2;
			break;
		case 'E':
			DERegister[1] = register2;
			break;
		case 'H':
			HLRegister[0] = register2;
			break;
		default:	//r1 == 'L'
			HLRegister[1] = register2;
		}
	}
	
	public static void MoVeImmediate(char register, char[] data) {
		switch(register) {
		case 'A':
			accumulator = data;
			break;
		case 'B':
			BCRegister[0] = data;
			break;
		case 'C':
			BCRegister[1] = data;
			break;
		case 'D':
			DERegister[0] = data;
			break;
		case 'E':
			DERegister[1] = data;
			break;
		case 'H':
			HLRegister[0] = data;
			break;
		default:	//r1 == 'L'
			HLRegister[1] = data;
		}
	}
	
	public static void CoMplementAccumulator() {
		accumulator = NumberSystems.binaryToHex(OnesComplement(NumberSystems.hexToBinary(accumulator))).toCharArray();
	}
	
	public static void ADD_register_content_to_accumulator(char register) {
		resetFlags();
		switch (register) {
			case 'A':
				accumulator = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(accumulator), NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'B':
				accumulator = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(BCRegister[0]), NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'C':
				accumulator = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(BCRegister[1]), NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'D':
				accumulator = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(DERegister[0]), NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'E':
				accumulator = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(DERegister[1]), NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'H':
				accumulator = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(HLRegister[0]), NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'L':
				accumulator = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(HLRegister[1]), NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			default:	//argument == 'M'
				int[] memoryIndices = NumberSystems.getMemoryAddress(new StringBuilder(new String(HLRegister[0])).append(new StringBuilder(new String(HLRegister[1]))).toString());
				int zero = memoryIndices[0];
				int one = memoryIndices[1];
				int[] binaryNum = NumberSystems.hexToBinary(new String(memory[zero][one]).toCharArray());
				accumulator = NumberSystems.binaryToHex(binaryAdd(binaryNum, NumberSystems.hexToBinary(accumulator))).toCharArray();
		}
	}
	
	public static void ADdImmediate(char[] hex) {
		resetFlags();
		accumulator = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(accumulator), NumberSystems.hexToBinary(hex))).toCharArray();
	}
	
	public static void SUbtractImmediate(char[] hex) {
		resetFlags();
		int[] n1 = TwosComplement(NumberSystems.hexToBinary(hex));
		resetFlags();
		accumulator = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(accumulator), n1)).toCharArray();
	}
	
	public static void SUBtract_register_content_from_accumulator(char register) {
		resetFlags();
		switch (register) {
			case 'A':
				int[] n1 = TwosComplement(NumberSystems.hexToBinary(accumulator));
				resetFlags();
				accumulator = NumberSystems.binaryToHex(binaryAdd(n1, NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'B':
				int[] n2 = TwosComplement(NumberSystems.hexToBinary(BCRegister[0]));
				resetFlags();
				accumulator = NumberSystems.binaryToHex(binaryAdd(n2, NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'C':
				int[] n3 = TwosComplement(NumberSystems.hexToBinary(BCRegister[1]));
				resetFlags();
				accumulator = NumberSystems.binaryToHex(binaryAdd(n3, NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'D':
				int[] n4 = TwosComplement(NumberSystems.hexToBinary(DERegister[0]));
				resetFlags();
				accumulator = NumberSystems.binaryToHex(binaryAdd(n4, NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'E':
				int[] n5 = TwosComplement(NumberSystems.hexToBinary(DERegister[1]));
				resetFlags();
				accumulator = NumberSystems.binaryToHex(binaryAdd(n5, NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'H':
				int[] n6 = TwosComplement(NumberSystems.hexToBinary(HLRegister[0]));
				resetFlags();
				accumulator = NumberSystems.binaryToHex(binaryAdd(n6, NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			case 'L':
				int[] n7 = TwosComplement(NumberSystems.hexToBinary(HLRegister[1]));
				resetFlags();
				accumulator = NumberSystems.binaryToHex(binaryAdd(n7, NumberSystems.hexToBinary(accumulator))).toCharArray();
				break;
			default:	//argument == 'M'
				int[] memoryIndices = NumberSystems.getMemoryAddress(new StringBuilder(new String(HLRegister[0])).append(new StringBuilder(new String(HLRegister[1]))).toString());
				int zero = memoryIndices[0];
				int one = memoryIndices[1];
				int[] binaryNum = TwosComplement(NumberSystems.hexToBinary(new String(memory[zero][one]).toCharArray()));
				resetFlags();
				accumulator = NumberSystems.binaryToHex(binaryAdd(binaryNum, NumberSystems.hexToBinary(accumulator))).toCharArray();
		}
	}
	
	public static void INcRementRegister(char register) {
		resetFlags();
		int[] num = {0, 0, 0, 0, 0, 0, 0, 1};
		switch (register) {
			case 'A':
				accumulator = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(accumulator), num)).toCharArray();
				break;
			case 'B':
				BCRegister[0] = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(BCRegister[0]), num)).toCharArray();
				break;
			case 'C':
				BCRegister[1] = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(BCRegister[1]), num)).toCharArray();
				break;
			case 'D':
				DERegister[0] = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(DERegister[0]), num)).toCharArray();
				break;
			case 'E':
				DERegister[1] = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(DERegister[1]), num)).toCharArray();
				break;
			case 'H':
				HLRegister[0] = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(HLRegister[0]), num)).toCharArray();
				break;
			case 'L':
				HLRegister[1] = NumberSystems.binaryToHex(binaryAdd(NumberSystems.hexToBinary(HLRegister[1]), num)).toCharArray();
				break;
			default:	//argument == 'M'
				int[] memoryIndices = NumberSystems.getMemoryAddress(new StringBuilder(new String(HLRegister[0])).append(new StringBuilder(new String(HLRegister[1]))).toString());
				int zero = memoryIndices[0];
				int one = memoryIndices[1];
				int[] binaryNum = NumberSystems.hexToBinary(new String(memory[zero][one]).toCharArray());
				accumulator = NumberSystems.binaryToHex(binaryAdd(binaryNum, num)).toCharArray();
		}
	}
	
	public static int[] binaryAdd(int[] x, int[] y) {
		int carry = 0;
		int[] result = new int[x.length + 1];
		for (int i = result.length - 1; i > 0; i--) {
			switch (x[i - 1] + y[i - 1] + carry) {
				case 1:
					result[i] = 1;
					carry = 0;
					break;
				case 2:
					result[i] = 0;
					carry = 1;
					if (!CY) {
						CY = true;
					}
					if (!AC && (i - 1) % 4 == 0) {
						AC = true;
					}
					break;
				case 3:
					result[i] = 1;
					carry = 1;
					if (!CY) {
						CY = true;
					}
					if (!AC && (i - 1) % 4 == 0) {
						AC = true;
					}
					break;
				default:
					result[i] = 0;
					carry = 0;
			}
		}
		result[0] = carry;
		int sum = 0;
		int place = 0;
		for (int j = result.length - 1; j > 0; j--) {
			sum += result[j] * Math.pow(10, place);
			place++;
		}
		int[] resultNC = new int[result.length - 1];
		for (int i = 1; i < result.length; i++) {
			resultNC[i - 1] = result[i];
		}
		if (sum == 0) {
			Z = true;
			P = false;
			S = false;
		}
		else {
			Z = false;
			if ((Arrays.stream(resultNC).filter(num -> num == 1).toArray().length) % 2 == 0) {
				P = true;
			}
			else {
				P = false;
			}
			if (resultNC[0] == 1) {
				S = true;
			}
			else {
				S = false;
			}
		}
		return resultNC;
	}
	
	public static int integerBinaryAdd(int[] x, int[] y) {
		int carry = 0;
		int[] result = new int[x.length + 1];
		for (int i = result.length - 1; i > 0; i--) {
			switch (x[i - 1] + y[i - 1] + carry) {
				case 1:
					result[i] = 1;
					carry = 0;
					break;
				case 2:
					result[i] = 0;
					carry = 1;
					if (!CY) {
						CY = true;
					}
					if (!AC && (i - 1) % 4 == 0) {
						AC = true;
					}
					break;
				case 3:
					result[i] = 1;
					carry = 1;
					if (!CY) {
						CY = true;
					}
					if (!AC && (i - 1) % 4 == 0) {
						AC = true;
					}
					break;
				default:
					result[i] = 0;
					carry = 0;
			}
		}
		result[0] = carry;
		int sum = 0;
		int place = 0;
		for (int j = result.length - 1; j > 0; j--) {
			sum += result[j] * Math.pow(10, place);
			place++;
		}
		if (sum == 0) {
			Z = true;
			P = false;
			S = false;
		}
		else {
			int[] resultNC = new int[result.length - 1];
			for (int i = 1; i < result.length; i++) {
				resultNC[i - 1] = result[i];
			}
			Z = false;
			if ((Arrays.stream(resultNC).filter(num -> num == 1).toArray().length) % 2 == 0) {
				P = true;
			}
			else {
				P = false;
			}
			if (resultNC[0] == 1) {
				S = true;
			}
			else {
				S = false;
			}
		}
		return sum;
	}
	
	public static void printFlagStatus(PrintWriter outFile) throws IOException {
		outFile.println(new StringBuilder("AC: ").append(AC).append(" CY: ").append(CY)
				.append(" Z: ").append(Z).append(" S: ").append(S).append(" P: ").append(P));
		outFile.flush();
	}
	
	public static void printRegisters(PrintWriter outFile) throws IOException {
		outFile.println();
		outFile.println(new StringBuilder("Accumulator: ").append(new String(accumulator)));
		outFile.println(new StringBuilder("BC : ").append(new String(registerString(BCRegister))));
		outFile.println(new StringBuilder("DE : ").append(new String(registerString(DERegister))));
		outFile.println(new StringBuilder("HL : ").append(new String(registerString(HLRegister))));
		outFile.println();
		outFile.flush();
	}
	
	private static String registerString(char[][] register) {
		return new StringBuilder("[").append(new String(register[0])).append(", ").append(new String(register[1])).append("]").toString();
	}
	
	private static void resetFlags() {
		if (AC) {
			AC = false;
		}
		if (CY) {
			CY = false;
		}
		if (P) {
			P = false;
		}
		if (Z) {
			Z = false;
		}
		if (S) {
			S = false;
		}
	}
	
	public static boolean getSignFlag() {
		return S;
	}
	
	private static int[] OnesComplement(int[] num) {
		int[] newNum = new int[num.length];
		for (int i = 0; i < newNum.length; i++) {
			switch(num[i]) {
				case 0:
					newNum[i] = 1;
					break;
				default:
					newNum[i] = 0;
			}
		}
		return newNum;
	}
	
	private static int TwosComplementInteger(int[] num) {
		int[] one = {0, 0, 0, 0, 0, 0, 0, 1};
		return integerBinaryAdd(OnesComplement(num), one);
	}
	
	public static int[] TwosComplement(int[] num) {
		int[] one = {0, 0, 0, 0, 0, 0, 0, 1};
		return binaryAdd(OnesComplement(num), one);
	}
	
	private static int OnesComplementInteger(int[] num) {
		int[] number = OnesComplement(num);
		int place = number.length - 1;
		int integerNumber = 0;
		for (int i = 0; i < number.length; i++) {
			integerNumber += number[i] * Math.pow(10, place);
			place--;
		}
		return integerNumber;
	}
	
}
