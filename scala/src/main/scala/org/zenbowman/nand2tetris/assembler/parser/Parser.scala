package org.zenbowman.nand2tetris.assembler.parser

/**
 * AST types that represent instructions in the HACK assembly language
 */
object AST {

  case class Program(instructions: List[Instruction])

  sealed trait Instruction

  case class AInstruction(address: Address) extends Instruction

  trait Address

  case class RealAddress(address: Int) extends Address

  case class SymbolicAddress(address: String) extends Address

  case class CInstruction(aBit: Boolean, comp: Comp.Comp, dest: Dest.Dest, jump: Jump.Jump)

  object Comp extends Enumeration {
    type Comp = Value
    val Zero, One, MinusOne, D, A, NotD, NotA, MinusD, MinusA, DPlusOne, APlusOne, DMinusOne, AMinusOne, DPlusA,
    DMinusA, AMinusD, DAndA, DOrA, M, NotM, MinusM, MPlusOne, MMinusOne, DPlusM, DMinusM, MMinusD, DAndM, DOrM = Value
  }

  object Dest extends Enumeration {
    type Dest = Value
    val Null, M, D, MD, A, AM, AD, AMB = Value
  }

  object Jump extends Enumeration {
    type Jump = Value
    val Null, JGT, JEQ, JGE, JLT, JNE, JLE, JMP = Value
  }

}

/**
 * Parser class for the HACK assembler
 */
object Parser {


}
