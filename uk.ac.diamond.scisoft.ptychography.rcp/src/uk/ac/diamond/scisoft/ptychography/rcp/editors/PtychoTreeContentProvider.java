package uk.ac.diamond.scisoft.ptychography.rcp.editors;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import uk.ac.diamond.scisoft.ptychography.rcp.model.PtychoNode;

public class PtychoTreeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		return ((ArrayList<?>) inputElement).toArray();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return ((PtychoNode) parentElement).getChildren().toArray();
	}

	@Override
	public Object getParent(Object element) {
		if (element == null) {
			return null;
		}
		return ((PtychoNode) element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		return !((PtychoNode) element).getChildren().isEmpty();
	}
}
