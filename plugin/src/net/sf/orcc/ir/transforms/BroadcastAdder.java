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
package net.sf.orcc.ir.transforms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.network.Broadcast;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.transforms.INetworkTransformation;

import org.jgrapht.DirectedGraph;

/**
 * Adds broadcast actors when needed.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class BroadcastAdder implements INetworkTransformation {

	private DirectedGraph<Vertex, Connection> graph;

	private Set<Connection> toBeRemoved;

	private void goForIt(Vertex vertex) {
		// make a copy of connections
		Set<Connection> connections = new HashSet<Connection>(graph
				.outgoingEdgesOf(vertex));

		// create a (port => list of connections) map
		Map<Port, List<Connection>> outMap = new HashMap<Port, List<Connection>>();
		for (Connection connection : connections) {
			Port src = connection.getSource();
			List<Connection> outList = outMap.get(src);
			if (outList == null) {
				outList = new ArrayList<Connection>();
				outMap.put(src, outList);
			}
			outList.add(connection);
		}

		Instance instance = vertex.getInstance();
		Actor srcActor = instance.getActor();
		for (Connection connection : connections) {
			Port src = connection.getSource();
			if (src != null) {
				List<Connection> outList = outMap.get(src);
				int numOutput = outList.size();
				if (numOutput > 1) {
					Broadcast bcast = new Broadcast(srcActor.getName(), src
							.getName(), numOutput, src.getType());
					Vertex vertexBCast = new Vertex(bcast);
					graph.addVertex(vertexBCast);

					// from source to broadcast
					Port tgt = new Port(connection.getTarget());
					tgt.setName("input");

					IAttribute size = connection
							.getAttribute(Connection.BUFFER_SIZE);
					Connection c1 = new Connection(src, tgt,
							Connection.BUFFER_SIZE, size);
					graph.addEdge(vertex, vertexBCast, c1);

					// from broadcast to targets
					int i = 0;
					for (Connection conn : outList) {
						// new connection
						Vertex target = graph.getEdgeTarget(conn);
						src = new Port(conn.getSource());
						src.setName("output_" + i);
						i++;

						size = conn.getAttribute(Connection.BUFFER_SIZE);
						Connection c2 = new Connection(src, conn.getTarget(),
								Connection.BUFFER_SIZE, size);
						graph.addEdge(vertexBCast, target, c2);

						// source == null => we don't check it again
						toBeRemoved.add(conn);
						conn.setSource(null);
					}
				}
			}
		}
	}

	@Override
	public void transform(Network network) {
		graph = network.getGraph();
		toBeRemoved = new HashSet<Connection>();

		Set<Vertex> vertices = new HashSet<Vertex>(graph.vertexSet());
		for (Vertex vertex : vertices) {
			if (vertex.isInstance()) {
				goForIt(vertex);
			}
		}

		// removes old connections
		graph.removeAllEdges(toBeRemoved);
	}
}
