package org.zenbowman.nand2tetris.assembler.parser

import scala.util.parsing.combinator.RegexParsers

/**
 * AST types that represent instructions in the HACK assembly language
 */
object AST {

  case class Program(instructions: List[Instruction])

  sealed trait Instruction

  case class AInstruction(address: Address) extends Instruction

  trait Address

  case class LiteralAddress(address: Int) extends Address

  case class SymbolicAddress(address: String) extends Address

  case class CInstruction(comp: Comp.Comp, dest: Dest.Dest, jump: Jump.Jump) extends Instruction

  object Comp extends Enumeration {
    type Comp = Value
    val Zero, One, MinusOne, D, A, NotD, NotA, MinusD, MinusA, DPlusOne, APlusOne, DMinusOne, AMinusOne, DPlusA,
    DMinusA, AMinusD, DAndA, DOrA, M, NotM, MinusM, MPlusOne, MMinusOne, DPlusM, DMinusM, MMinusD, DAndM, DOrM = Value
  }

  object Dest extends Enumeration {
    type Dest = Value
    val Null, M, D, MD, A, AM, AD, AMD = Value
  }

  object Jump extends Enumeration {
    type Jump = Value
    val Null, JGT, JEQ, JGE, JLT, JNE, JLE, JMP = Value
  }

}

/**
 * Parser class for the HACK assembler
 */
class Parser extends RegexParsers {

  import AST._

  def program: Parser[Program] = {
    rep(instruction) ^^ {
      x => Program(x)
    }
  }

  def instruction: Parser[Instruction] =
    aInstruction | cInstruction

  def aInstruction: Parser[AInstruction] = {
    "@" ~> address ^^ {
      x => AInstruction(x)
    }
  }

  def address: Parser[Address] = symbolicAddress | literalAddress

  def symbolicAddress: Parser[SymbolicAddress] = {
    "([a-zA-Z]+)".r ^^ {
      x => SymbolicAddress(x)
    }
  }

  def literalAddress: Parser[LiteralAddress] = {
    "([0-9]+)".r ^^ {
      x => LiteralAddress(x.toInt)
    }
  }

  def cInstruction: Parser[CInstruction] = {
    opt(dest) ~ comp ~ opt(jump) ^^ {
      case Some(d) ~ c ~ Some(j) => CInstruction(c, d, j)
      case Some(d) ~ c ~ None => CInstruction(c, d, Jump.Null)
    }
  }

  def dest: Parser[Dest.Dest] = {
    "([A-Z]+)".r <~ "=" ^^ {
      case "M" => Dest.M
      case "D" => Dest.D
      case "MD" => Dest.MD
      case "A" => Dest.A
      case "AM" => Dest.AM
      case "AD" => Dest.AD
      case "AMD" => Dest.AMD
    }
  }

  def comp: Parser[Comp.Comp] = {
    import Comp._
    def c(k: Comp.Comp)(x: String) = k

    "0" ^^ c(Zero) | "1" ^^ c(One) | "-1" ^^ c(MinusOne) | "!D" ^^ c(NotD) |
      "!A" ^^ c(NotA) | "-D" ^^ c(MinusD) | "-A" ^^ c(MinusA) | "D+1" ^^ c(DPlusOne) | "A+1" ^^ c(APlusOne) |
      "D-1" ^^ c(DMinusOne) | "A-1" ^^ c(AMinusOne) | "D+A" ^^ c(DPlusA) | "D-A" ^^ c(DMinusA) | "A-D" ^^ c(AMinusD) |
      "D&A" ^^ c(DAndA) | "D|A" ^^ c(DOrA) | "!M" ^^ c(NotM) | "-M" ^^ c(MinusM) | "M+1" ^^ c(MPlusOne) |
      "D+M" ^^ c(DPlusM) | "D-M" ^^ c(DMinusM) | "M-D" ^^ c(MMinusD) | "D&M" ^^ c(DAndM) | "D|M" ^^ c(DOrM) |
      "D" ^^ c(Comp.D) | "A" ^^ c(A) | "M" ^^ c(M)
  }

  def jump: Parser[Jump.Jump] = {
    ";" ~> jumpTypes
  }

  def jumpTypes: Parser[Jump.Jump] = {
    "JGT" ^^ (_ => Jump.JGT) | "JEQ" ^^ (_ => Jump.JEQ) | "JGE" ^^ (_ => Jump.JGE)
  }
}
