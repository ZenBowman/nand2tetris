package org.zenbowman.nand2tetris.assembler.parser

import junit.framework.{Assert, TestCase}
import org.zenbowman.nand2tetris.assembler.parser.AST._

class ParserTest extends TestCase {

  def testAInstructionParser() {
    val parser = new Parser

    Assert.assertEquals(AInstruction(RealAddress(1234)),
      parser.parseAll(parser.ainstruction, "@1234").get)

    Assert.assertEquals(AInstruction(SymbolicAddress("TEST")),
      parser.parseAll(parser.ainstruction, "@TEST").get)
  }

}
