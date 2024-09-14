# 8085-Microprocessor-Simulator
**Instructions implemented:**
* MOV
* MVI
* LXI H
* ADD
* ADI
* SUB
* SUI
* INR
* CMA

**Main.java**

main():
Decodes and executes instructions from the stack pointer (represented with a Deque being used as a stack) and prints the register and flag status both before and after the instruction is executed.

readInputFile():
Takes in a Deque that acts as a Stack, which serves as the stack pointer, and reads the input file until EOF is reached. If the first String in the line is 5 characters long, then it is a memory address that should be initialized with the hexadecimal number represented by the second String in the line. As such, it puts the hexadecimal number in the memory location specified using initializeMemory() from the InstructionSet class. Otherwise, the String is placed onto the stack pointer. After EOF is reached, the stack pointer is returned to be used for decoding and executing instructions.

decodeAndExecute():
Takes in a Deque that acts as a Stack (once again this is intended to be the stack pointer) and calls the correct function from InstructionSet based on the instruction. The arguments that get passed in are derived from parts of the line that are intended to be paired with that instruction. The register and flag status is printed before and after executing an instruction.

**NumberSystems.java**

There are 3 Lists used to help with number conversions: a List<Integer> of decimal numbers from 0-15, a List<Integer> of binary numbers from 0-1111 and a List<Character> of hexadecimal numbers from ‘0’-‘F’.

binaryToHex():

From left to right for every 4 binary bits, the value in hex at the index of the binary number (minus the extra 0s) is concatenated to a String; this String is returned. When using this method in InstructionSet, the result of this function is converted to a character array in order to update the registers.

hexToBinary():

From left to right, every hexadecimal character is converted to binary by accessing the value in the binary List at the index of the hex character. Before moving on to the next character, the int is converted to a String and padded with 0s until the String’s length becomes 4. A new int array created to store the binary result is created and traversed and the integer value of every character in the final String is assigned to the current index. Then, the int array is returned.

hexToDecimal():

Converts hex to binary and binary to a decimal int.

decimalToHex():

Converts decimal to binary and binary to a hexadecimal String. The returned value is the String converted to a char array.

getMemoryAddress():

Takes in a hexadecimal memory address and returns the respective indices to use when referencing memory values in the memory array of the InstructionSet class. The first 3 characters are converted to decimal by adding (value in decimal List at index of respective hex character) * 16^(the current character). This is the value of the row index. The value of the column index is the value in the decimal List at the index of the fourth hex character. These two indices are put into an int array and this array is returned.
