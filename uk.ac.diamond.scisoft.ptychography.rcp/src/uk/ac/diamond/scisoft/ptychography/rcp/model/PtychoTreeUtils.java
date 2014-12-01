package uk.ac.diamond.scisoft.ptychography.rcp.model;

import java.util.ArrayList;
import java.util.List;

public class PtychoTreeUtils {

	/**
	 * Populates the Ptycho tree given a list of PtychoInput read from
	 * a CSV file
	 * TODO put in a recursive method to support more than depth 3 tree
	 * @param input
	 * @return
	 */
	public static List<PtychoNode> populate(List<PtychoData> input) {
		List<PtychoNode> nodes = new ArrayList<PtychoNode>();
		int i = 0;
		while (i < input.size()) {
			if (i < input.size() - 1) {
				PtychoNode node = new PtychoNode(input.get(i));
				int level = input.get(i).getLevel();
				int nextLevel = input.get(i + 1).getLevel();
				if (level == 0 && nextLevel == 1) {
					int j = 0;
					while (nextLevel == 1) {
						int max = input.size() - (j + 1);
						if (i == max)
							break;
						PtychoNode child = new PtychoNode(input.get(i + 1 + j));
						j++;
						if ((i + 1 + j) >= input.size()) {
							child.setParent(node);
							node.addChild(child);
							break;
						}
						nextLevel = input.get(i + 1 + j).getLevel();
						if (nextLevel == 2) {
							int k = 0;
							i += j;
							while (nextLevel == 2) {
								PtychoNode subchild = new PtychoNode(
										input.get(i + 1 + k));
								k++;
								if (i < input.size() - k)
									nextLevel = input.get(i + 1 + k).getLevel();
								subchild.setParent(child);
								child.addChild(subchild);
							}
							i += k;
							j = 0;
						}
						child.setParent(node);
						node.addChild(child);
					}
					i += j;
				}
				nodes.add(node);
			}
			i++;
		}
		return nodes;
	}

	/**
	 * expands the depth 3 tree and puts it into a list
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
							json.append("\"" + leaf.getData().getDefaultValue() +"\"");
							if (k < leafs.size() - 1)
								json.append(",");
						}
						json.append("}");
						if (j < children.size() - 1)
							json.append(",");
					} else {
						json.append("\"" + child.getData().getDefaultValue() +"\"");
						if (j < children.size() - 1)
							json.append(",");
					}
				}
				json.append("}");
			} else {
				json.append("\"" + node.getData().getDefaultValue() +"\"");
			}
			
			if (i < tree.size() - 1)
				json.append(",");
		}
		json.append("}");
		return json.toString();
	}
}
