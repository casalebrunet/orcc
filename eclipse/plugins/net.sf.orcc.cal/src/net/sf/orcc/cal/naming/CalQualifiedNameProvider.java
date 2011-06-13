/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.naming;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * This class defines a qualified name provider for RVC-CAL.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalQualifiedNameProvider extends
		DefaultDeclarativeQualifiedNameProvider {

	public QualifiedName qualifiedName(AstAction action) {
		AstTag tag = action.getTag();
		if (tag == null) {
			return getConverter().toQualifiedName(action.toString());
		}

		return getConverter().toQualifiedName(
				OrccUtil.toString(tag.getIdentifiers(), "."));
	}

	public QualifiedName qualifiedName(AstActor actor) {
		return getConverter().toQualifiedName(
				Util.getQualifiedName((AstEntity) actor.eContainer()));
	}

	public QualifiedName qualifiedName(AstStatementForeach foreach) {
		return getConverter().toQualifiedName(foreach.toString());
	}

	public QualifiedName qualifiedName(AstEntity entity) {
		return getConverter().toQualifiedName(Util.getQualifiedName(entity));
	}

	public QualifiedName qualifiedName(AstExpression expr) {
		return getConverter().toQualifiedName(expr.toString());
	}

	public QualifiedName qualifiedName(AstTag tag) {
		return null;
	}

	public QualifiedName qualifiedName(AstUnit unit) {
		return getConverter().toQualifiedName(
				Util.getQualifiedName((AstEntity) unit.eContainer()));
	}

}
