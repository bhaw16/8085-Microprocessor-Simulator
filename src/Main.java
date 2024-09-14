import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//test prints
		/*
		int[] num1 = {1, 0, 0, 1, 1, 1, 1, 1};
		int[] num2 = {0, 0, 0, 0, 0, 0, 0, 1};
		System.out.println(InstructionSet.integerBinaryAdd(num1, num2));
		int[] num3 = {0, 1, 0, 0, 0, 1, 0, 0};
		System.out.println(NumberSystems.binaryToHex(num3));
		char[] hex = {'B', '4'};
		System.out.println(Arrays.toString(NumberSystems.hexToBinary(hex)));
		*/
		decodeAndExecute(readInputFile(new File("/Users/briannahawkins/eclipse-workspace/8085_Microprocessor_Simulator/src/init.txt")),
				new PrintWriter(new File("/Users/briannahawkins/eclipse-workspace/8085_Microprocessor_Simulator/src/output.txt")));
	}
	
	public static Deque<String> readInputFile(File init) throws IOException {
		Scanner sc = new Scanner(init);
		Deque<String> auxiliaryStack = new ArrayDeque<>();
		while (sc.hasNext()) {
			String line = sc.nextLine();
			String[] initMemory = line.split(" ");
			if (initMemory[0].length() == 5) {
				InstructionSet.initializeMemory(initMemory[0].substring(0, 4), initMemory[1].substring(0, 2));
			}
			else {
				auxiliaryStack.push(line);
			}
		}
		Deque<String> stackPointer = new ArrayDeque<>();
		while (!auxiliaryStack.isEmpty()) {
			stackPointer.push(auxiliaryStack.pop());
		}
		sc.close();
		return stackPointer;
	}
	
	public static void decodeAndExecute(Deque<String> stackPointer, PrintWriter outFile) throws IOException {
		while (!stackPointer.isEmpty()) {
			String instructionString = stackPointer.pop();
			String[] instruction = instructionString.split(" ");
			outFile.print(new StringBuilder("\nFlag and register status before ").append(instructionString));
			outFile.println(":");
			InstructionSet.printRegisters(outFile);
			InstructionSet.printFlagStatus(outFile);
			switch(instruction[0]) {
				case "MOV":
					InstructionSet.MOVe(instruction[1].charAt(0), instruction[1].charAt(2));
					break;
				case "MVI":
					InstructionSet.MoVeImmediate(instruction[1].charAt(0), instruction[1].substring(2, instruction[1].length() - 1).toCharArray());
					break;
				case "LXI":
					char[][] address = {instruction[1].substring(2, 4).toCharArray(), instruction[1].substring(4, 6).toCharArray()};
					InstructionSet.LXIH(address);
					break;
				case "SUB":
					InstructionSet.SUBtract_register_content_from_accumulator(instruction[1].charAt(0));
					break;
				case "SUI":
					InstructionSet.SUbtractImmediate(instruction[1].substring(0, 2).toCharArray());
					break;
				case "ADD":
					InstructionSet.ADD_register_content_to_accumulator(instruction[1].charAt(0));
					break;
				case "ADI":
					InstructionSet.ADdImmediate(instruction[1].substring(0, 2).toCharArray());
					break;
				case "INR":
					InstructionSet.INcRementRegister(instruction[1].charAt(0));
					break;
				default:
					InstructionSet.CoMplementAccumulator();
			}
			outFile.print(new StringBuilder("\nFlag and register status after ").append(instructionString));
			outFile.println(":");
			InstructionSet.printRegisters(outFile);
			InstructionSet.printFlagStatus(outFile);
		}
		outFile.close();
	}
}


