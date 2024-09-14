# 8085-Microprocessor-Simulator

**Main.java**

main():
Decodes and executes instructions from the stack pointer (represented with a Deque being used as a stack) and prints the register and flag status both before and after the instruction is executed.

readInputFile():
Takes in a Deque that acts as a Stack, which serves as the stack pointer, and reads the input file until EOF is reached. If the first String in the line is 5 characters long, then it is a memory address that should be initialized with the hexadecimal number represented by the second String in the line. As such, it puts the hexadecimal number in the memory location specified using initializeMemory() from the InstructionSet class. Otherwise, the String is placed onto the stack pointer. After EOF is reached, the stack pointer is returned to be used for decoding and executing instructions.

decodeAndExecute():
Takes in a Deque that acts as a Stack (once again this is intended to be the stack pointer) and calls the correct function from InstructionSet based on the instruction. The arguments that get passed in are derived from parts of the line that are intended to be paired with that instruction. The register and flag status is printed before and after executing an instruction.

**NumberSystems.java**

There are 3 Lists used to help with number conversions: a List<Integer> of decimal numbers from 0-15, a List<Integer> of binary numbers from 0-1111 and a List<Character> of hexadecimal numbers from ‘0’-‘F’.

binaryToHex(): From left to right for every 4 binary bits, the value in hex at the index of the binary number (minus the extra 0s) is concatenated to a String; this String is returned. When using this method in InstructionSet, the result of this function is converted to a character array in order to update the registers.

hexToBinary(): From left to right, every hexadecimal character is converted to binary by accessing the value in the binary List at the index of the hex character. Before moving on to the next character, the int is converted to a String and padded with 0s until the String’s length becomes 4. A new int array created to store the binary result is created and traversed and the integer value of every character in the final String is assigned to the current index. Then, the int array is returned.

hexToDecimal(): Converts hex to binary and binary to a decimal int.

decimalToHex(): Converts decimal to binary and binary to a hexadecimal String. The returned value is the String converted to a char array.

getMemoryAddress(): Takes in a hexadecimal memory address and returns the respective indices to use when referencing memory values in the memory array of the InstructionSet class. The first 3 characters are converted to decimal by adding (value in decimal List at index of respective hex character) * 16^(the current character). This is the value of the row index. The value of the column index is the value in the decimal List at the index of the fourth hex character. These two indices are put into an int array and this array is returned.

**InstructionSet.java**

The flags are implemented as booleans initialized as false.

The accumulator is represented by a char array. The register pairs (BC, DE and HL) are 2d char arrays of length 2. The first char array represents the first register in the pair (B in BC, D in DE and H in HL) and the second one represents the other register in the pair (C, E and L respectively).

The memory array is a 2d String array; a String is converted to a char array when accessed from the memory array so that it can be passed to methods the same way that the other registers are.

initializeMemory(): takes in a memory address String and hexadecimal number String and assigns the hexadecimal String to the memory address specified using getMemoryAddress() from the NumberSystems class.

binaryAdd(): Accepts two int arrays each representing an 8-bit binary number. A new int array is created to store the sum and carry; its size is initialized as the length of the arrays being added and is then traversed from the last element to the second element (i. e. the rightmost bit of the sum to the leftmost bit of the sum excluding the carry bit). Then, the current index of the sum’s array and the carry are calculated based on the sum of the carry, the first binary digit and the second binary digit. If the sum == 0 (default case), that means that the carry and binary digits being added are all 0. As such, the current digit of the sum will be 0 and the carry will also be 0. If the sum == 1, that means that either the carry, first binary digit or second binary digit is 1, but not all of them (i. e. XOR); the current bit of the sum will be 1 and the carry will be 0. If the sum == 2, that means that either both of the addends’ bits are one or that the carry bit is 1 and the bit of one if the addend's is 1; in this case, the sum bit is 0 and the carry is 1. Finally, if the sum == 3, that means all 3 bits are 1; here, the sum bit is 1 and the carry bit is also 1. For the latter two cases, the carry flag is set and the auxiliary carry flag is also set if the index of the next bit is divisible by 4 (i. e. the next bit is in the upper nibble). Then, the carry bit is discarded by converting only the result portion to an int and by putting the result (without carry) in another array. If the int == 0, the zero flag is set to true, the sign flag is set to false and the parity flag is also set to false. Otherwise, the zero flag is set to false and the sign flag is set to true if the first bit is 1 and false otherwise. For the parity flag, the array is converted to an IntStream in order to check the number of 1s (filter() and toArray() are used to create an array out of the 1s in the binary number). If the created array’s length is even, the parity flag is set to true; otherwise it is set to false. Finally, the array of the sum without the carry bit is returned.

**Instructions implemented:**

* MOV: copies the char array from one register to another.
* MVI: replaces the char array of the register to the char array representing the hexadecimal number being copied to the register.
* LXI H: takes in a hexadecimal memory address and changes the char arrays in the HL register pairs to match the hexadecimal memory address.
* ADD: The flags are reset at the start of this operation. Then, the accumulator content and the register content are converted to binary numbers (both represented by an int array) and added together with the binaryAdd() method. The result is then converted to a hexadecimal number and stored in the accumulator.
* ADI: The flags are reset at the start of this operation. Then, the accumulator content and the hexadecimal number being added (represented by a char array) are converted to binary numbers (both represented by an int array) and added together with the binaryAdd() method. The result is then converted to a hexadecimal number and stored in the accumulator.
* SUB: The flags are reset at the start of this operation. Then, 2s complement is performed on the register content and the flags are reset again since binaryAdd() affects the auxiliary carry and carry flags. Then, the accumulator content and the 2s complement of the register content are converted to binary numbers (both represented by an int array) and added together with the binaryAdd() method. The result is then converted to a hexadecimal number and stored in the accumulator.
* SUI: The flags are reset at the start of this operation. Then, 2s complement is performed on the hexadecimal number being added (char array) and the flags are reset again since binaryAdd() affects the auxiliary carry and carry flags. Then, the accumulator content and the 2s complement of the register content are converted to binary numbers (both represented by an int array) and added together with the binaryAdd() method. The result is then converted to a hexadecimal number and stored in the accumulator.
* INR: The flags are reset at the start of this operation. Then, the register content is converted to a binary number (represented by an int array) and added with the binary number 00000001 ([0, 0, 0, 0, 0, 0, 0, 1]) with the binaryAdd() method. The result is then converted to a hexadecimal number and stored in the register being incremented.
* CMA: The accumulator content is converted to binary and then 1s complement is performed on the bits. Then, the result is converted back to hex and stored in the accumulator.


**Additional notes:**

I did this project at the end of the Spring 2024 semester for honors credit in my Computer Architecture class which is why some of these notes mention time constraints.

I preferred to use StringBuilder’s append() method rather than using the concatenation operator or concat() method for padding/concatenating Strings because it is better for the runtime of the program and as such is my personal preference.

I implemented the LXI instruction for only the HL register pair because I wanted to be able to use M as a register for the MOV, ADD and SUB instructions at the minimum in spite of time constraints.

I did not implement the INX instruction or any other extended instructions because I preferred to focus on 8-bit instructions rather than 16-bit instructions.

I did not implement LDA, STA, custom Exceptions or Exception handling (aside from throwing IOExceptions to the compiler) due to time constraints.

I did not make the stack pointer a global variable because some Java programmers consider the use of global variables in the Main class to be bad practice, so I had it returned and passed in between methods in order to access and use it in main().

For the instructions that use commas, I made it so that a character has to come right before and right after the comma. If I had more time, I would try to allow spaces as well by using trim().
