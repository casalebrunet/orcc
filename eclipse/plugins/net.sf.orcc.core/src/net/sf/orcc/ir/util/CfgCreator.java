/*
 * Copyright (c) 2012, Synflow
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
package net.sf.orcc.ir.util;

import java.util.List;
import java.util.ListIterator;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.GraphFactory;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.Cfg;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;

/**
 * This class defines a CFG creator from an FSM.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CfgCreator extends DfSwitch<Void> {

	private Cfg cfg;

	private FSM fsm;

	private Node last;

	public CfgCreator() {
		cfg = IrFactory.eINSTANCE.createCfg();
		Node start = IrFactory.eINSTANCE.createNodeBlock();
		cfg.getVertices().add(start);
		cfg.setEntry(start);

		last = start;
	}

	/**
	 * Adds the CFG nodes and edges corresponding to the given list of actions
	 * sorted by descending priority.
	 * 
	 * @param actions
	 *            a list of actions
	 */
	private void addActions(List<Action> actions) {
		// initial step: start from the end
		Node nodeLastElse = addNode(IrFactory.eINSTANCE.createNodeBlock());
		Node nodeLastJoin = nodeLastElse;

		// reverse iterate
		ListIterator<Action> it = actions.listIterator(actions.size());
		while (it.hasPrevious()) {
			Action action = it.previous();

			Node nodeIf = addNode(IrFactory.eINSTANCE.createNodeIf());
			nodeIf.setAttribute("action", action);

			Node nodeThen = addNode(IrFactory.eINSTANCE.createNodeBlock());
			nodeThen.setAttribute("action", action);

			addEdge(nodeIf, nodeThen).setAttribute("flag", true);
			addEdge(nodeIf, nodeLastElse);

			Node nodeJoin = addNode(IrFactory.eINSTANCE.createNodeBlock());
			addEdge(nodeThen, nodeJoin);
			addEdge(nodeLastJoin, nodeJoin);

			// update last else and join
			nodeLastElse = nodeIf;
			nodeLastJoin = nodeJoin;
		}

		// final step: link to the incoming node, and update last
		addEdge(last, nodeLastElse);
		last = nodeLastJoin;
	}

	/**
	 * Creates an edge and adds it to this CFG.
	 * 
	 * @return a newly-created edge
	 */
	private Edge addEdge(Node from, Node to) {
		Edge edge = GraphFactory.eINSTANCE.createEdge();
		cfg.getEdges().add(edge);
		edge.setSource(from);
		edge.setTarget(to);
		return edge;
	}

	@Override
	public Void caseActor(Actor actor) {
		fsm = actor.getFsm();
		if (fsm == null) {
			addActions(actor.getActionsOutsideFsm());
		} else {

		}
		return null;
	}

	/**
	 * Creates a node and adds it to this CFG.
	 * 
	 * @return a newly-created node
	 */
	private Node addNode(Node node) {
		cfg.getVertices().add(node);
		return node;
	}

	public Cfg getCfg() {
		return cfg;
	}

}