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
package net.sf.orcc.util;

import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;

/**
 * This class defines a parser of binary operation sequences. This parser
 * translates expressions such as "e(1) op(1) e(2) ... op(n-1) e(n)" to a binary
 * expression tree with respect to operator precedence.
 * 
 * <p>
 * This code is based on code written by Sam Harwell found on the site of ANTLR.
 * </p>
 * 
 * @author Matthieu Wipliez
 * 
 */
public class BinOpSeqParser {

	/**
	 * Creates the precedence tree from the given list of expressions,
	 * operators, and the start and stop indexes.
	 * 
	 * @param expressions
	 *            a list of expressions
	 * @param operators
	 *            a list of binary operators
	 * @param startIndex
	 *            start index
	 * @param stopIndex
	 *            stop index
	 * @return an expression
	 */
	private static Expression createPrecedenceTree(
			List<Expression> expressions, List<OpBinary> operators,
			int startIndex, int stopIndex) {
		if (stopIndex == startIndex) {
			return expressions.get(startIndex);
		}

		int pivot = findPivot(operators, startIndex, stopIndex - 1);
		OpBinary op = operators.get(pivot);
		Expression e1 = createPrecedenceTree(expressions, operators,
				startIndex, pivot);
		Expression e2 = createPrecedenceTree(expressions, operators, pivot + 1,
				stopIndex);

		return IrFactory.eINSTANCE.createExprBinary(e1, op, e2,
				IrFactory.eINSTANCE.createTypeVoid());
	}

	/**
	 * Returns the index of the pivot, which is the operator that has the
	 * highest precedence between start index and stop index. The pivot is
	 * therefore the operator that binds the least with its operands.
	 * 
	 * @param operators
	 *            a list of operators
	 * @param startIndex
	 *            start index
	 * @param stopIndex
	 *            stop index
	 * @return the index of the pivot operator
	 */
	private static int findPivot(List<OpBinary> operators, int startIndex,
			int stopIndex) {
		int pivot = startIndex;
		OpBinary bop = operators.get(pivot);
		int pivotRank = bop.getPrecedence();
		for (int i = startIndex + 1; i <= stopIndex; i++) {
			bop = operators.get(i);
			int current = bop.getPrecedence();
			boolean rtl = bop.isRightAssociative();
			if (pivotRank < current || (current == pivotRank && rtl)) {
				pivot = i;
				pivotRank = current;
			}
		}

		return pivot;
	}

	/**
	 * Parses a sequence of expressions and binary operators to a binary
	 * expression tree.
	 * 
	 * @param expressions
	 *            a list of expressions
	 * @param operators
	 *            a list of binary operators
	 * @return a binary expression tree
	 */
	public static Expression parse(List<Expression> expressions,
			List<OpBinary> operators) {
		return createPrecedenceTree(expressions, operators, 0,
				expressions.size() - 1);
	}

}
