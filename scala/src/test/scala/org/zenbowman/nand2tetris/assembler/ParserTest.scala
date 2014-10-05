package org.zenbowman.nand2tetris.assembler

import junit.framework.{Assert, TestCase}
import AST._

class ParserTest extends TestCase {

  val parser = new Parser

  def testAInstructionParser() {
    assertParseResult(AInstruction(LiteralAddress(1234)), parser.aInstruction, "@1234")
    assertParseResult(AInstruction(SymbolicAddress("TEST")), parser.aInstruction, "@TEST")
  }

  def testCompParser() {
    assertParseResult(Comp.One, parser.comp, "1")
    assertParseResult(Comp.DMinusA, parser.comp, "D-A")
    assertParseResult(Comp.DOrA, parser.comp, "D|A")
    assertParseResult(Comp.M, parser.comp, "M")
    assertParseResult(Comp.MMinusD, parser.comp, "M-D")
  }

  def testDestParser() {
    assertParseResult(Dest.A, parser.dest, "A=")
  }

  def testCInstructionParser() {
    assertParseResult(CInstruction(Comp.Zero, Dest.A, Jump.Null), parser.cInstruction, "A=0")
    assertParseResult(CInstruction(Comp.DOrM, Dest.AMD, Jump.JEQ), parser.cInstruction, "AMD=D|M;JEQ")
  }

  def testProgramParser() {
    assertParseResult(Program(List(
      AInstruction(SymbolicAddress("TEST")),
      LInstruction("FOO"),
      CInstruction(Comp.DOrM, Dest.AMD, Jump.JEQ),
      AInstruction(LiteralAddress(1234)))),
      parser.program,
      """
        |@TEST
        |(FOO)
        |AMD=D|M;JEQ
        |@1234
      """.stripMargin)
  }

  def assertParseResult[T](x: Any, p: parser.Parser[T], s: String) {
    Assert.assertEquals(x, parser.parseAll(p, s).get)
  }
}
