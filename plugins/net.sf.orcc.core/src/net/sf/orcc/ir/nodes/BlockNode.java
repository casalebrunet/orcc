/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.ir.nodes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;

/**
 * This class defines a Block node. A block node is a node that contains
 * instructions.
 * 
 * @author Matthieu Wipliez
 * 
 */
public final class BlockNode extends AbstractNode implements
		Iterable<Instruction> {

	/**
	 * Returns the first block in the list of nodes of the given procedure. A
	 * new block is created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @return a block
	 */
	public static BlockNode getFirst(Procedure procedure) {
		return getFirst(procedure, procedure.getNodes());
	}

	/**
	 * Returns the first block in the given list of nodes. A new block is
	 * created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param nodes
	 *            a list of nodes of the given procedure
	 * @return a block
	 */
	public static BlockNode getFirst(Procedure procedure, List<CFGNode> nodes) {
		BlockNode block;
		if (nodes.isEmpty()) {
			block = new BlockNode(procedure);
			nodes.add(block);
		} else {
			CFGNode node = nodes.get(0);
			if (node instanceof BlockNode) {
				block = (BlockNode) node;
			} else {
				block = new BlockNode(procedure);
				nodes.add(0, block);
			}
		}

		return block;
	}

	/**
	 * Returns the last block in the list of nodes of the given procedure. A new
	 * block is created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @return a block
	 */
	public static BlockNode getLast(Procedure procedure) {
		return getLast(procedure, procedure.getNodes());
	}

	/**
	 * Returns the last block in the given list of nodes. A new block is created
	 * if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param nodes
	 *            a list of nodes of the given procedure
	 * @return a block
	 */
	public static BlockNode getLast(Procedure procedure, List<CFGNode> nodes) {
		BlockNode block;
		if (nodes.isEmpty()) {
			block = new BlockNode(procedure);
			nodes.add(block);
		} else {
			CFGNode node = nodes.get(nodes.size() - 1);
			if (node instanceof BlockNode) {
				block = (BlockNode) node;
			} else {
				block = new BlockNode(procedure);
				nodes.add(block);
			}
		}

		return block;
	}

	/**
	 * the list of instructions of this block node.
	 */
	private List<Instruction> instructions;

	/**
	 * Creates a new empty block node with the given location.
	 */
	public BlockNode(Location location, Procedure procedure) {
		super(location, procedure);
		instructions = new ArrayList<Instruction>();
	}

	/**
	 * Creates a new empty block node.
	 */
	public BlockNode(Procedure procedure) {
		this(new Location(), procedure);
	}

	@Override
	public Object accept(NodeInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(NodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	/**
	 * Appends the instructions of the specified block at the end of this block.
	 * 
	 * @param block
	 *            a block
	 */
	public void add(BlockNode block) {
		for (Instruction instruction : block) {
			add(instruction);
		}
	}

	/**
	 * Appends the specified instruction to the end of this block.
	 * 
	 * @param instruction
	 *            an instruction
	 */
	public void add(Instruction instruction) {
		instruction.setBlock(this);
		instructions.add(instruction);
	}

	/**
	 * Appends the specified instruction to this block at the specified index.
	 * 
	 * @param index
	 *            the index
	 * @param instruction
	 *            an instruction
	 */
	public void add(int index, Instruction instruction) {
		instruction.setBlock(this);
		instructions.add(index, instruction);
	}

	/**
	 * Returns the instructions of this block node.
	 * 
	 * @return the instructions of this block node
	 */
	public List<Instruction> getInstructions() {
		return instructions;
	}

	@Override
	public boolean isBlockNode() {
		return true;
	}

	@Override
	public Iterator<Instruction> iterator() {
		return instructions.iterator();
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper
	 * sequence) that is positioned after the last instruction.
	 * 
	 * @return a list iterator over the elements in this list (in proper
	 *         sequence)
	 */
	public ListIterator<Instruction> lastListIterator() {
		return instructions.listIterator(instructions.size());
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper
	 * sequence).
	 * 
	 * @return a list iterator over the elements in this list (in proper
	 *         sequence)
	 */
	public ListIterator<Instruction> listIterator() {
		return instructions.listIterator();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Instruction instruction : this) {
			sb.append(instruction.toString());
			sb.append("\\n");
		}

		return sb.toString();
	}

}
