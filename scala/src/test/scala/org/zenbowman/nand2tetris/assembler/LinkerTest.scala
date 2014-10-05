package org.zenbowman.nand2tetris.assembler

import junit.framework.{Assert, TestCase}

class LinkerTest extends TestCase {
  import org.zenbowman.nand2tetris.assembler.AST._

  /**
   * Test that labels are stripped out in pass1.
   */
  def testLabelLinking() {
    val parser = new Parser
    val program = parser.parseAll(parser.program,
      """
        |@i
        |D=M
        |@100
        |D=D-A
        |@END
        |(END)
        |@END
        |0;JMP
      """.stripMargin)
    val linker = new Linker
    Assert.assertEquals(
      Program(List(
        AInstruction(SymbolicAddress("i")),
        CInstruction(Comp.M, Dest.D, Jump.Null),
        AInstruction(LiteralAddress(100)),
        CInstruction(Comp.DMinusA, Dest.D, Jump.Null),
        AInstruction(SymbolicAddress("END")),
        AInstruction(SymbolicAddress("END")),
        CInstruction(Comp.Zero, Dest.Null, Jump.JMP)
      )),
      linker.pass1(program.get))
  }

  /**
   * Test that addresses are assigned in pass2.
   */
  def testAddressLinking() {
    val parser = new Parser
    val program = parser.parseAll(parser.program,
      """
        |@i
        |D=M
        |@100
        |D=D-A
        |@END
        |(END)
        |@END
        |0;JMP
      """.stripMargin)
    val linker = new Linker
    Assert.assertEquals(
      Program(List(
        AInstruction(LiteralAddress(16)),
        CInstruction(Comp.M, Dest.D, Jump.Null),
        AInstruction(LiteralAddress(100)),
        CInstruction(Comp.DMinusA, Dest.D, Jump.Null),
        AInstruction(LiteralAddress(5)),
        AInstruction(LiteralAddress(5)),
        CInstruction(Comp.Zero, Dest.Null, Jump.JMP)
      )),
      linker.link(program.get))
  }
}
