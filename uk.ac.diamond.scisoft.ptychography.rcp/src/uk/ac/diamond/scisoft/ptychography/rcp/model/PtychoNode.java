package uk.ac.diamond.scisoft.ptychography.rcp.model;

import java.util.ArrayList;
import java.util.List;

public class PtychoNode {
	private PtychoInput data;
	private PtychoNode parent;
	private List<PtychoNode> children;

	public PtychoNode(PtychoInput data, int nbChildren) {
		this.setData(data);
		this.children = new ArrayList<PtychoNode>(nbChildren);
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

	public PtychoInput getData() {
		return data;
	}

	public void setData(PtychoInput data) {
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
