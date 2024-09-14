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
