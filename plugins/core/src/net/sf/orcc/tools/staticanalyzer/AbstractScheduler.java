/*
 * Copyright (c) 2010, Ecole Polytechnique F�d�rale de Lausanne 
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
 *   * Neither the name of the Ecole Polytechnique F�d�rale de Lausanne nor the 
 *     names of its contributors may be used to endorse or promote products 
 *     derived from this software without specific prior written permission.
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

package net.sf.orcc.tools.staticanalyzer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;

/**
 * This interface defines a scheduler.
 * 
 * @author Ghislain Roquier
 * 
 */
public abstract class AbstractScheduler implements IScheduler {

	protected DirectedGraph<Vertex, Connection> graph;

	protected Schedule schedule;

	public AbstractScheduler(DirectedGraph<Vertex, Connection> graph)
			throws OrccException {
		this.graph = graph;
		schedule = schedule();
	}

	public Map<Connection, Integer> getBufferCapacities() {

		Map<Connection, Integer> bufferCapacities = new HashMap<Connection, Integer>();

		LinkedList<Iterand> stack = new LinkedList<Iterand>(
				schedule.getIterands());

		int rep = schedule.getIterationCount();

		while (!stack.isEmpty()) {
			Iterand iterand = stack.pop();

			if (iterand.isVertex()) {
				Vertex vertex = iterand.getVertex();

				for (Connection connection : graph.outgoingEdgesOf(vertex)) {
					int prd = connection.getSource().getNumTokensProduced();
					bufferCapacities.put(connection, rep * prd);
				}

				for (Connection connection : graph.incomingEdgesOf(vertex)) {
					if (!bufferCapacities.containsKey(connection)) {
						int cns = connection.getTarget().getNumTokensConsumed();
						bufferCapacities.put(connection, rep * cns);
					}
				}
			} else {
				Schedule sched = iterand.getSchedule();
				rep = sched.getIterationCount();
				for (Iterand subIterand : sched.getIterands()) {
					stack.push(subIterand);
				}
			}
		}
		return bufferCapacities;
	}

	public Schedule getSchedule() {
		return schedule;
	}

}
