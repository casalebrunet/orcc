/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.cal.type;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstInputPattern;
import net.sf.orcc.cal.cal.AstPort;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeBool;
import net.sf.orcc.cal.cal.AstTypeFloat;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeList;
import net.sf.orcc.cal.cal.AstTypeString;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.expression.AstExpressionEvaluator;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines an AST type to IR type transformer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeTransformer extends CalSwitch<Type> {

	/**
	 * Creates a new AST type to IR type transformation.
	 */
	public TypeTransformer() {
	}

	@Override
	public Type caseAstExpression(AstExpression expression) {
		TypeChecker checker = new TypeChecker(null);
		return checker.getType(expression);
	}

	@Override
	public Type caseAstFunction(AstFunction function) {
		return doSwitch(function.getType());
	}

	@Override
	public Type caseAstPort(AstPort port) {
		return doSwitch(port.getType());
	}

	@Override
	public Type caseAstTypeBool(AstTypeBool type) {
		return IrFactory.eINSTANCE.createTypeBool();
	}

	@Override
	public Type caseAstTypeFloat(AstTypeFloat type) {
		return IrFactory.eINSTANCE.createTypeFloat();
	}

	@Override
	public Type caseAstTypeInt(AstTypeInt type) {
		AstExpression astSize = type.getSize();
		int size;
		if (astSize == null) {
			size = 32;
		} else {
			size = new AstExpressionEvaluator(null).evaluateAsInteger(astSize);
		}
		return IrFactory.eINSTANCE.createTypeInt(size);
	}

	@Override
	public Type caseAstTypeList(AstTypeList listType) {
		Type type = doSwitch(listType.getType());
		AstExpression expression = listType.getSize();
		Expression size = new AstExpressionEvaluator(null).evaluate(expression);
		return IrFactory.eINSTANCE.createTypeList(size, type);
	}

	@Override
	public Type caseAstTypeString(AstTypeString type) {
		return IrFactory.eINSTANCE.createTypeString();
	}

	@Override
	public Type caseAstTypeUint(AstTypeUint type) {
		AstExpression astSize = type.getSize();
		int size;
		if (astSize == null) {
			size = 32;
		} else {
			size = new AstExpressionEvaluator(null).evaluateAsInteger(astSize);
		}

		return IrFactory.eINSTANCE.createTypeUint(size);
	}

	@Override
	public Type caseAstVariable(AstVariable variable) {
		AstType astType;
		List<AstExpression> dimensions;

		if (variable.eContainer() instanceof AstInputPattern) {
			AstInputPattern pattern = (AstInputPattern) variable.eContainer();
			astType = EcoreUtil.copy(pattern.getPort().getType());
			dimensions = new ArrayList<AstExpression>();
			AstExpression repeat = pattern.getRepeat();
			if (repeat != null) {
				dimensions.add(repeat);
			}
		} else {
			astType = EcoreUtil.copy(variable.getType());
			dimensions = variable.getDimensions();
		}

		// convert the type of the variable
		ListIterator<AstExpression> it = dimensions.listIterator(dimensions
				.size());
		while (it.hasPrevious()) {
			AstExpression expression = it.previous();

			AstTypeList newAstType = CalFactory.eINSTANCE.createAstTypeList();
			AstExpression size = EcoreUtil.copy(expression);
			newAstType.setSize(size);
			newAstType.setType(astType);

			astType = newAstType;
		}

		return doSwitch(astType);
	}

}
