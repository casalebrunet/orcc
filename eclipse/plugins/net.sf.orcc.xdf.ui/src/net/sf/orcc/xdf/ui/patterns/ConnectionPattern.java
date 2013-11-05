/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.xdf.ui.patterns;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.xdf.ui.styles.StyleUtil;

import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.AbstractConnectionPattern;
import org.eclipse.graphiti.pattern.IFeatureProviderWithPatterns;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.ILinkService;
import org.eclipse.graphiti.services.IPeCreateService;

/**
 * Implements a visible connection between 2 ports.
 * 
 * Each connection link an input and an output port. A port can be represented
 * by an Input or an Output port directly contained in the network, or a port in
 * an Instance. In the second case, the real port is a port contained in an
 * Actor or in a Network, depending on how the instance has been refined.
 * 
 * @author Antoine Lorence
 * 
 */
public class ConnectionPattern extends AbstractConnectionPattern {

	private enum PortDirection {
		INPUT, OUTPUT
	}
	private enum PortType {
		NETWORK_PORT, INSTANCE_PORT
	}

	public class PortInformation {
		private final Vertex vertex;
		private final Port port;
		private final PortDirection direction;
		private final PortType type;

		public PortInformation(Vertex vertex, Port port, PortDirection direction, PortType type) {
			this.vertex = vertex;
			this.port = port;
			this.direction = direction;
			this.type = type;
		}

		public Vertex getVertex() {
			return vertex;
		}

		public Port getPort() {
			return port;
		}

		public PortDirection getDirection() {
			return direction;
		}

		public PortType getType() {
			return type;
		}
	}

	@Override
	public String getCreateName() {
		return "Connection";
	}

	@Override
	public String getCreateDescription() {
		return "Add a connection between 2 ports";
	}

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		final PortInformation src = getPortInformations(context.getSourceAnchor());
		if (src == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		// The current diagram has a Network attached
		if (!(getBusinessObjectForPictogramElement(getDiagram()) instanceof Network)) {
			return false;
		}

		final PortInformation src = getPortInformations(context.getSourceAnchor());
		final PortInformation trgt = getPortInformations(context.getTargetAnchor());
		if (src == null || trgt == null) {
			return false;
		}

		// Disallow connection between 2 network ports
		if (src.getType() == PortType.NETWORK_PORT && trgt.getType() == PortType.NETWORK_PORT) {
			return false;
		}

		// Connection between instance ports are checked specifically
		if (src.getType() == PortType.INSTANCE_PORT && trgt.getType() == PortType.INSTANCE_PORT) {

			// User tries to connect 2 ports from the same instance
			if (context.getSourcePictogramElement().eContainer() == context.getTargetPictogramElement().eContainer()) {
				return false;
			}

			// User tries to connect incompatible ports directions
			if (src.getDirection() == trgt.getDirection()) {
				return false;
			}
		}

		// net IN -> inst IN = OK
		// net IN -> inst OUT = NOK
		// net OUT -> inst OUT = OK
		// net OUT -> inst IN = NOK
		// etc.
		if (src.getType() != trgt.getType()) {
			if (src.getDirection() != trgt.getDirection()) {
				return false;
			}
		}

		// TODO: Check in an input port has not already a connection connected
		// to it.
		return true;
	}

	@Override
	public Connection create(ICreateConnectionContext context) {
		// In some case, connection source and target need to be exchanged, to
		// avoid arrow on the wrong side of the connection shape
		PortInformation src = getPortInformations(context.getSourceAnchor());
		boolean needInverseSourceTarget = false;
		if (src.getType() == PortType.NETWORK_PORT && src.getDirection() == PortDirection.OUTPUT) {
			needInverseSourceTarget = true;
		} else if (src.getType() == PortType.INSTANCE_PORT && src.getDirection() == PortDirection.INPUT) {
			needInverseSourceTarget = true;
		}

		// Create connection context
		final AddConnectionContext addContext;
		if (needInverseSourceTarget) {
			addContext = new AddConnectionContext(context.getTargetAnchor(), context.getSourceAnchor());
		} else {
			addContext = new AddConnectionContext(context.getSourceAnchor(), context.getTargetAnchor());
		}

		src = getPortInformations(addContext.getSourceAnchor());
		final PortInformation tgt = getPortInformations(addContext.getTargetAnchor());
		// Create new business object
		final net.sf.orcc.df.Connection dfConnection = DfFactory.eINSTANCE.createConnection(src.getVertex(),
				src.getPort(), tgt.getVertex(), tgt.getPort());
		final Network network = (Network) getBusinessObjectForPictogramElement(getDiagram());
		network.getConnections().add(dfConnection);

		addContext.setNewObject(dfConnection);
		Connection newConnection = (Connection) getFeatureProvider().addIfPossible(addContext);

		return newConnection;
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (context instanceof IAddConnectionContext) {
			if (context.getNewObject() instanceof net.sf.orcc.df.Connection) {
				return true;
			}
		}
		return super.canAdd(context);
	}

	@Override
	public PictogramElement add(IAddContext context) {
		final IAddConnectionContext addConContext = (IAddConnectionContext) context;
		final net.sf.orcc.df.Connection addedConnection = (net.sf.orcc.df.Connection) context.getNewObject();
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();

		// CONNECTION WITH POLYLINE
		final FreeFormConnection connection = peCreateService.createFreeFormConnection(getDiagram());
		connection.setStart(addConContext.getSourceAnchor());
		connection.setEnd(addConContext.getTargetAnchor());

		ConnectionDecorator cd;
		cd = peCreateService.createConnectionDecorator(connection, false, 1.0, true);
		createArrow(cd);

		/* Polyline polyline = */gaService.createPolyline(connection);
		StyleUtil.getStyleForConnection(getDiagram());

		// create link and wire it
		link(connection, addedConnection);
		return super.add(context);
	}

	/**
	 * Retrieve all information related to a port in a network/diagram. Build
	 * corresponding PortInformation structure.
	 * 
	 * @param anchor
	 *            The anchor corresponding to a port in a Network or an Instance
	 * @return
	 */
	public PortInformation getPortInformations(Anchor anchor) {

		ILinkService linkService = Graphiti.getLinkService();

		if (anchor == null) {
			return null;
		}
		// Instance port are linked directly to the anchor
		Object obj = getBusinessObjectForPictogramElement(anchor);
		if (obj instanceof Port) {
			FixPointAnchor fpAnchor = (FixPointAnchor) anchor;
			// FIXME: a better detection of in/out port ?
			PortDirection dir = fpAnchor.getLocation().getX() == 0 ? PortDirection.INPUT : PortDirection.OUTPUT;
			Instance parentInstance = (Instance) linkService
					.getBusinessObjectForLinkedPictogramElement(anchor
					.getParent());
			PortInformation info = new PortInformation(parentInstance, (Port) obj, dir, PortType.INSTANCE_PORT);
			return info;
		}

		// Network ports are linked to the top level shape (anchor container)
		obj = getBusinessObjectForPictogramElement(anchor.getParent());
		if (obj instanceof Port) {
			// Retrieve the pattern corresponding to the current port
			IPattern ipattern = ((IFeatureProviderWithPatterns) getFeatureProvider())
					.getPatternForPictogramElement(anchor.getParent());

			// Check the type of this pattern to know the direction of the port
			PortDirection dir = ipattern instanceof InputNetworkPortPattern ? PortDirection.INPUT
					: PortDirection.OUTPUT;
			PortInformation info = new PortInformation((Port) obj, null, dir, PortType.NETWORK_PORT);
			return info;
		}

		// Anchor without port ? Maybe an error here...
		return null;
	}

	private Polyline createArrow(GraphicsAlgorithmContainer gaContainer) {
		IGaService gaService = Graphiti.getGaService();
		Polyline polyline = gaService.createPolyline(gaContainer, new int[] { -15, 10, 0, 0, -15, -10 });
		polyline.setStyle(StyleUtil.getStyleForConnection(getDiagram()));
		return polyline;
	}
}
