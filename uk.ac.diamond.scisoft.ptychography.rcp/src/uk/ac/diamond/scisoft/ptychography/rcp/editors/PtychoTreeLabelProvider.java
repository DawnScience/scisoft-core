package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;

public class PtychoTreeLabelProvider extends ColumnLabelProvider {

	private int column;

	public PtychoTreeLabelProvider(int column) {
		this.column = column;
	}

	@Override
	public String getText(Object element) {
		if (element == null || !(element instanceof PtychoNode))
			return null;
		PtychoNode row = (PtychoNode)element;
		StringBuilder ret = new StringBuilder();
		switch (column) {
		case 0:
			ret.append(row.getData().getName());
			break;
		case 1:
			ret.append(row.getData().getDefaultValue());
			break;
		default:
			break;
		}
		return ret.toString();
	}

	@Override
	public String getToolTipText(Object element) {
		if (!(element instanceof PtychoNode))
			return super.getToolTipText(element);

		PtychoNode in = (PtychoNode) element;
		StringBuilder buf = new StringBuilder();
		// buf.append("'");
		// buf.append(ln.getPath());
		// buf.append("'\n");
		buf.append(in.getData().getShortDoc());
		return buf.toString();
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

}
