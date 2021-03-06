// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

	@16384
	D=A
	@R0
	M=D
	@R1
	M=D
	@24576
	D=A
	@R2
	M=D

	(CLOOP)
	@24576
	D=M
	@BLOOP
	D;JNE
	@R0
	D=M
	@R1
	D=D-M
	@CLOOP
	D;JEQ
	@R0
	AD=M
	M=0
	D=D-1
	@R0
	M=D
	@CLOOP
	0;JMP

	(BLOOP)
	@R0
	D=M
	@R2
	D=D-M
	@CLOOP
	D;JEQ
	@R0
	AD=M
	M=-1
	D=D+1
	@R0
	M=D
	@CLOOP
	0;JMP
