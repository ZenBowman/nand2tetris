package org.zenbowman.nand2tetris.assembler

import org.zenbowman.nand2tetris.assembler.AST._
import scala.collection.mutable.ListBuffer
import org.zenbowman.nand2tetris.assembler.AST.AInstruction
import org.zenbowman.nand2tetris.assembler.AST.LInstruction
import org.zenbowman.nand2tetris.assembler.AST.Program

class Linker {
  val symbolTable = new SymbolTable

  def link(program: Program): Program = pass2(pass1(program))

  /**
   * Pass1: Assign line numbers to labels.
   * @param program the original program as emitted from the parser.
   * @return the program with labels stripped, and label values assigned in the symbol table.
   */
  def pass1(program: Program): Program = {
    var lineNum = 0
    var programResult = new ListBuffer[Instruction]
    for (instruction <- program.instructions) {
      instruction match {
        case LInstruction(x) =>
          symbolTable.assignLabel(x, lineNum)
        case y =>
          lineNum += 1
          programResult += y
      }
    }
    Program(programResult.toList)
  }

  /**
   * Pass2: Assign addresses to variables.
   * @param program the stripped-label program, as emitted from pass1.
   * @return the program with all variables and labels assigned to literal addresses.
   */
  def pass2(program: Program): Program = {
    Program(program.instructions.map {
      instruction => instruction match {
        case AInstruction(SymbolicAddress(x)) =>
          AInstruction(LiteralAddress(symbolTable.getAddress(x)))
        case x =>
          x
      }
    })
  }

}
