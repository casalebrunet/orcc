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
package net.sf.orcc.backends.c.quasistatic.scheduler.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.backends.c.quasistatic.scheduler.unrollers.FSMUnroller;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Port;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class ActorGraph {

	private Actor actor;

	private List<Graph<Action, DefaultEdge>> graphs;


	public ActorGraph(Actor actor) {
		this.actor = actor;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public String getName() {
		return actor.getName();
	}

	public List<Graph<Action, DefaultEdge>> getGraphs() {
		return graphs;
	}

	public void unrollFSM() throws QuasiStaticSchedulerException {
		System.out.println("********* Unrolling actor " + getName()
							+ " *********");

		graphs = new FSMUnroller(actor).unroll();
	}

	public String toString() {
		return getName();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ActorGraph)) {
			return false;
		}

		ActorGraph otherActor = (ActorGraph) other;
		return actor.getName().equals(otherActor.getActor().getName());
	}

	public List<Action> getActionsWithPort(Port port) {
		List<Action> actions = new ArrayList<Action>();
		for (Action action : actor.getActions()) {
			if (action.getInputPattern().containsKey(port)
					|| action.getOutputPattern().containsKey(port)) {
				actions.add(action);
			}
		}
		return actions;
	}

	public boolean contains(Actor actor) {
		return this.actor.getName().equals(actor.getName());
	}

}
