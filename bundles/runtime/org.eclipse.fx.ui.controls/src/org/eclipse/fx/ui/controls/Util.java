/*******************************************************************************
 * Copyright (c) 2014 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package org.eclipse.fx.ui.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fx.ui.controls.styledtext.StyledString;
import org.eclipse.fx.ui.controls.styledtext.StyledStringSegment;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Utility methods
 *
 * @since 1.2
 */
public class Util {
	/**
	 * Dump the scene graph to a formatted string
	 *
	 * @param n
	 *            the node to start with
	 * @return the dump as a formatted XML
	 */
	public static String dumpSceneGraph(Node n) {
		return new SceneGraphDumper().dump(n).toString();
	}

	static class SceneGraphDumper {
		private StringBuilder sb = new StringBuilder();
		private int ident = 0;

		public StringBuilder dump(Node n) {
			for (int i = 0; i < this.ident; i++) {
				this.sb.append("    "); //$NON-NLS-1$
			}
			this.ident++;

			this.sb.append("<" + n.getClass().getName() + " styleClass=\"" //$NON-NLS-1$ //$NON-NLS-2$
					+ n.getStyleClass() + "\">\n"); //$NON-NLS-1$
			if (n instanceof Parent) {
				for (Node subNode : ((Parent) n).getChildrenUnmodifiable()) {
					dump(subNode);
				}
			}

			this.ident--;
			for (int i = 0; i < this.ident; i++) {
				this.sb.append("    "); //$NON-NLS-1$
			}
			this.sb.append("</" + n.getClass().getName() + ">\n"); //$NON-NLS-1$ //$NON-NLS-2$

			return this.sb;
		}
	}

	/**
	 * Create a scenegraph node from the styled string
	 *
	 * @param s
	 *            the string
	 * @return a scenegraph node
	 */
	public static Node toNode(StyledString s) {
		List<Text> segList = new ArrayList<>();
		for (StyledStringSegment seg : s.getSegmentList()) {
			Text t = new Text(seg.getText());
			t.getStyleClass().addAll(seg.getStyleClass());
			segList.add(t);
		}

		return new TextFlow(segList.toArray(new Node[0]));
	}
}