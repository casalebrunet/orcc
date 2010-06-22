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
package net.sf.orcc.ir.instructions;

import net.sf.orcc.ir.Cast;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalTargetContainer;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.ValueContainer;
import net.sf.orcc.ir.util.CommonNodeOperations;

/**
 * This class defines an Assign instruction. The target is a local variable, and
 * the value an expression.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Assign extends AbstractInstruction implements
		LocalTargetContainer, ValueContainer {

	private LocalVariable target;

	private Expression value;

	public Assign(LocalVariable target, Expression value) {
		this(new Location(), target, value);
	}

	public Assign(Location location, LocalVariable target, Expression value) {
		super(location);
		setTarget(target);
		setValue(value);
	}

	@Override
	public Object accept(InstructionInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(InstructionVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	@Override
	public Cast getCast() {
		Type expr = value.getType();
		Type val = target.getType();

		if (expr == null) {
			return null;
		}

		if (value.isIntExpr() || value.isBooleanExpr()) {
			return null;
		}

		Cast cast = new Cast(expr, val);

		if (cast.isExtended() || cast.isTrunced()) {
			return cast;
		}

		return null;
	}

	@Override
	public LocalVariable getTarget() {
		return target;
	}

	@Override
	public Expression getValue() {
		return value;
	}

	@Override
	public void internalSetTarget(LocalVariable target) {
		this.target = target;
	}

	@Override
	public void internalSetValue(Expression value) {
		this.value = value;
	}

	@Override
	public boolean isAssign() {
		return true;
	}

	@Override
	public void setTarget(LocalVariable target) {
		CommonNodeOperations.setTarget(this, target);
	}

	@Override
	public void setValue(Expression value) {
		CommonNodeOperations.setValue(this, value);
	}

	@Override
	public String toString() {
		return target + " = " + getValue();
	}

}
