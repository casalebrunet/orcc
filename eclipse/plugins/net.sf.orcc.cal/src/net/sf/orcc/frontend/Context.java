/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.frontend;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

/**
 * This class defines a context used by the AST transformer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Context {

	/**
	 * A map from global variables to local variables
	 */
	private Map<Var, Var> mapGlobals;

	/**
	 * Contains the current procedures where local variables or nodes should be
	 * added by the expression transformer or statement transformer.
	 */
	private Procedure procedure;

	private Set<Var> setGlobalsToLoad;

	private Set<Var> setGlobalsToStore;

	/**
	 * Creates a new context with the given parent context and the given
	 * procedure.
	 * 
	 * @param context
	 *            a context, or <code>null</code> to create a new empty context
	 * @param procedure
	 *            a procedure
	 */
	public Context(Context context, Procedure procedure) {
		this.procedure = procedure;

		mapGlobals = new HashMap<Var, Var>();

		setGlobalsToLoad = new LinkedHashSet<Var>();
		setGlobalsToStore = new LinkedHashSet<Var>();
	}

	/**
	 * Clears this context.
	 */
	public void clear() {
		mapGlobals.clear();
		procedure = null;
		setGlobalsToLoad.clear();
		setGlobalsToStore.clear();
	}

	/**
	 * Returns the mapGlobals field.
	 * 
	 * @return the mapGlobals field
	 */
	public Map<Var, Var> getMapGlobals() {
		return mapGlobals;
	}

	/**
	 * Returns the procedure field.
	 * 
	 * @return the procedure field
	 */
	public Procedure getProcedure() {
		return procedure;
	}

	/**
	 * Returns the setGlobalsToLoad field.
	 * 
	 * @return the setGlobalsToLoad field
	 */
	public Set<Var> getSetGlobalsToLoad() {
		return setGlobalsToLoad;
	}

	/**
	 * Returns the setGlobalsToStore field.
	 * 
	 * @return the setGlobalsToStore field
	 */
	public Set<Var> getSetGlobalsToStore() {
		return setGlobalsToStore;
	}

}
