package uk.ac.diamond.scisoft.ptychography.rcp.model;

import java.util.ArrayList;
import java.util.List;

public class PtychoNode {
	private PtychoData data;
	private PtychoNode parent;
	private List<PtychoNode> children = new ArrayList<PtychoNode>();

	public PtychoNode(PtychoData data) {
		this.setData(data);
	}

	public void addChild(PtychoNode child) {
		children.add(child);
	}

	public PtychoNode getChildAt(int i) {
		return children.get(i);
	}

	public List<PtychoNode> getChildren() {
		return children;
	}

	public PtychoData getData() {
		return data;
	}

	public void setData(PtychoData data) {
		this.data = data;
	}

	public PtychoNode getParent() {
		return parent;
	}

	public void setParent(PtychoNode parent) {
		this.parent = parent;
	}

	public int nbChildren() {
		return children.size();
	}
}
