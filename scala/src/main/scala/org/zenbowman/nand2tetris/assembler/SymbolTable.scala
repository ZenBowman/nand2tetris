package org.zenbowman.nand2tetris.assembler

import scala.collection.mutable

/**
 * Symbol table implementation for the HACK assembler.
 */
class SymbolTable {
  val entries = new mutable.HashMap[String, Int]
  var nextVariableAddress = 16

  def contains(label: String) = entries.contains(label)

  def assignLabel(label: String, line: Int) {
    entries.put(label, line)
  }

  def assignVariableIfNotExists(variable: String) {
    if (!entries.contains(variable)) {
      entries.put(variable, nextVariableAddress)
      nextVariableAddress += 1
    }
  }

  def getAddress(variable: String): Int = {
    assignVariableIfNotExists(variable)
    entries.get(variable).get
  }
}
