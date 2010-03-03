/*
 * generated by Xtext
 */
package net.sf.orcc.labeling;

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.cal.Action;
import net.sf.orcc.cal.Tag;

import org.eclipse.xtext.ui.core.DefaultLabelProvider;

/**
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class CalLabelProvider extends DefaultLabelProvider {

	public String text(Action action) {
		Tag tag = action.getTag();
		if (tag == null) {
			return "(untagged)";
		} else {
			StringBuilder builder = new StringBuilder();
			List<String> identifiers = action.getTag().getIdentifiers();
			Iterator<String> it = identifiers.iterator();
			builder.append(it.next());

			while (it.hasNext()) {
				builder.append('.');
				builder.append(it.next());
			}

			return builder.toString();
		}
	}

}
