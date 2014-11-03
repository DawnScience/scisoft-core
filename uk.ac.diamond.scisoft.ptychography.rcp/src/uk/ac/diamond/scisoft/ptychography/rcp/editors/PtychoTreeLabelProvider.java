package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;

public class PtychoTreeLabelProvider extends ColumnLabelProvider implements IStyledLabelProvider{

	private int column;

	public PtychoTreeLabelProvider(int column) {
		this.column = column;
	}

	@Override
	public StyledString getStyledText(Object element) {
		if (element == null || !(element instanceof PtychoNode))
			return null;
		PtychoNode row = (PtychoNode)element;
		StyledString ret = new StyledString();
		switch (column) {
		case 0:
			ret.append(row.getData().getName(), StyledString.DECORATIONS_STYLER);
			break;
		case 1:
			ret.append(row.getData().getDefaultValue(), StyledString.DECORATIONS_STYLER);
			break;
		default:
			break;
		}
		return ret;
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
