package org.zenbowman.nand2tetris.assembler.parser

import junit.framework.{Assert, TestCase}
import org.zenbowman.nand2tetris.assembler.parser.AST._

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
  }

  def assertParseResult[T](x: Any, p: parser.Parser[T], s: String) {
    Assert.assertEquals(x, parser.parseAll(p, s).get)
  }
}
