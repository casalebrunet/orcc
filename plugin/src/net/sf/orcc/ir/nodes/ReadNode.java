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

import net.sf.orcc.common.Location;
import net.sf.orcc.common.LocalVariable;

/**
 * @author Matthieu Wipliez
 * 
 */
public class ReadNode extends AbstractNode {

	private String fifoName;

	private int numTokens;

	private LocalVariable varDef;

	public ReadNode(int id, Location location, String fifoName, int numTokens,
			LocalVariable varDef) {
		super(id, location);
		this.fifoName = fifoName;
		this.numTokens = numTokens;
		this.varDef = varDef;
	}

	@Override
	public void accept(NodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public String getFifoName() {
		return fifoName;
	}

	public int getNumTokens() {
		return numTokens;
	}

	public LocalVariable getVarDef() {
		return varDef;
	}

	public void setFifoName(String fifoName) {
		this.fifoName = fifoName;
	}

	public void setNumTokens(int numTokens) {
		this.numTokens = numTokens;
	}

	public void setVar(LocalVariable varDef) {
		this.varDef = varDef;
	}

	@Override
	public String toString() {
		return varDef + " = read(" + fifoName + ", " + numTokens + ")";
	}

}
