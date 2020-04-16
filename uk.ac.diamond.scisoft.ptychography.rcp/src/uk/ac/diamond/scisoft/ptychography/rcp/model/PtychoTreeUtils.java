package uk.ac.diamond.scisoft.ptychography.rcp.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

public class PtychoTreeUtils {
	
	/*
	 * Recursive method to populate the Ptycho Input Editor tree.
	 * Supports 'infinite' tree depth, and will continue until all rows are added.
	 */
	public static List<PtychoNode> populate(int count, int level, List<PtychoData> input){
		List<PtychoNode> nodes = new ArrayList<PtychoNode>();
		
		PtychoNode node = new PtychoNode(input.get(count));
		nodes.add(node);
		while(++count < input.size()){
			PtychoNode nextNode = new PtychoNode(input.get(count));
			int nextLevel = nextNode.getData().getLevel();
			
			if(nextLevel > level){
				List<PtychoNode> temp = populate(count, nextLevel, input);
				for(PtychoNode n : temp){
					n.setParent(node);
					node.addChild(n);
					count++;
				}
				count--;
			} else if(nextLevel == level) {
				nodes.add(nextNode);
			} else if (nextLevel < level){
				return nodes;
			}
			node = nextNode;
		}
		return nodes;
	}

	public static List<PtychoNode> findNodeWithName(PtychoNode node, String name) {
		List<PtychoNode> result = new ArrayList<PtychoNode>();
		if (node.getData().getName().equals(name)) {
			result.add(node);
		}
		if (node != null && node.nbChildren() == 0)
			return result;
		List<PtychoNode> children = node.getChildren();
		List<PtychoNode> temp = new ArrayList<PtychoNode>();
		if (children.size() > 0)
			for (int i = 0; i < children.size(); i++) {
				temp = findNodeWithName(children.get(i), name);
				if (temp != null && !temp.isEmpty())
					return temp;
			}
		return null;
	}

	public static String getTreePath(PtychoNode tree, PtychoNode node) {
		String path = "";
		List<PtychoNode> children = tree.getChildren();
		for (PtychoNode child : children) {
			if (child.equals(node)) {
				path = tree.getData().getName() + "/" + child.getData().getName();
				return path;
			}
			if (!child.getChildren().isEmpty())
				path = getTreePath(child, node);
		}
		return path;
	}

	/**
	 * expands the depth 4 tree and puts it into a list
	 * TODO put in a recursive method
	 * @param tree
	 * @return
	 */
	public static List<PtychoData> extract(List<PtychoNode> tree) {
		List<PtychoData> result = new ArrayList<PtychoData>();
		for (PtychoNode node : tree) {
			result.add(node.getData());
			if (!node.getChildren().isEmpty()) {
				List<PtychoNode> children = node.getChildren();
				for (PtychoNode child : children) {
					result.add(child.getData());
					if (!child.getChildren().isEmpty()) {
						List<PtychoNode> leafs = child.getChildren();
						for (PtychoNode leaf : leafs) {
							result.add(leaf.getData());
							if (!leaf.getChildren().isEmpty()) {
								List<PtychoNode> leafChildren = leaf.getChildren();
								for (PtychoNode leafChild : leafChildren) {
									result.add(leafChild.getData());
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * creates a Json string out of a PtychoNode tree of depth 3
	 * TODO put in a recursive method
	 * @param tree
	 * @return
	 */
	public static String jsonMarshal(List<PtychoNode> tree) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		for (int i = 0; i < tree.size(); i ++) {
			PtychoNode node = tree.get(i);
			json.append("\"" + node.getData().getName() +"\"");
			json.append(":");
			if (!node.getChildren().isEmpty()) {
				List<PtychoNode> children = node.getChildren();
				json.append("{");
				for (int j = 0; j < children.size(); j ++) {
					PtychoNode child = children.get(j);
					json.append("\"" + child.getData().getName() +"\"");
					json.append(":");
					if (!child.getChildren().isEmpty()) {
						List<PtychoNode> leafs = child.getChildren();
						json.append("{");
						for (int k = 0; k < leafs.size(); k ++) {
							PtychoNode leaf = leafs.get(k);
							json.append("\"" + leaf.getData().getName() +"\"");
							json.append(":");
							if (!leaf.getChildren().isEmpty()) {
								List<PtychoNode> leafChildren = leaf.getChildren();
								json.append("{");
								for (int l = 0; l < leafChildren.size(); l ++) {
									PtychoNode leafChild = leafChildren.get(l);
									json.append("\"" + leafChild.getData().getName() +"\"");
									json.append(":");
									json.append(getValue(leafChild.getData().getDefaultValue(), leafChild.getData().getType()));
									if (l < leafChildren.size() - 1)
										json.append(",");
								}
								json.append("}");
								if (k < leafs.size() - 1)
									json.append(",");
							} else {
								json.append(getValue(leaf.getData().getDefaultValue(), leaf.getData().getType()));
								if (k < leafs.size() - 1)
									json.append(",");
							}
						}
						json.append("}");
						if (j < children.size() - 1)
							json.append(",");
					} else {
						json.append(getValue(child.getData().getDefaultValue(), child.getData().getType()));
						if (j < children.size() - 1)
							json.append(",");
					}
				}
				json.append("}");
			} else {
				json.append(getValue(node.getData().getDefaultValue(), node.getData().getType()));
			}
			
			if (i < tree.size() - 1)
				json.append(",");
		}
		json.append("}");
		return json.toString();
	}
	
	/*
	 * One case with existing CSV template has the value 00 as a string
	 * Once converted to JSON, NumberUtils.isNumber correctly identifies this as numeric
	 * This may not be desirable, however the value can be surrounded with "" in the tree editor to force string type
	 */
	private static String getValue(String value, String type) {
		if (((value.startsWith("[") && value.endsWith("]")) ||
				(value.startsWith("\"") && value.endsWith("\"")))
				|| ((type != null) && (type.equals("int")||(type.equals("float"))))
				|| (NumberUtils.isNumber(value))) {
			if(value.equals("") || value.equals("None")) return "\"\"";
			return value;
		}
		return "\"" + value + "\"";
	}
}
