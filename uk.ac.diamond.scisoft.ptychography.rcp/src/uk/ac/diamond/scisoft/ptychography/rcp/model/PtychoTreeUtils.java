package uk.ac.diamond.scisoft.ptychography.rcp.model;

import java.util.ArrayList;
import java.util.List;

public class PtychoTreeUtils {

	/**
	 * Populates the Ptycho tree given a list of PtychoInput read from
	 * a CSV file
	 * 
	 * @param input
	 * @return
	 */
	public static List<PtychoNode> populate(List<PtychoInput> input) {
		List<PtychoNode> nodes = new ArrayList<PtychoNode>();
		int i = 0;
		while (i < input.size()) {
			if (i < input.size() - 1) {
				PtychoNode node = new PtychoNode(input.get(i));
				int level = input.get(i).getLevel();
				int nextLevel = input.get(i + 1).getLevel();
				if (level == 3 && nextLevel == 2) {
					int j = 0;
					while (nextLevel == 2) {
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
						if (nextLevel == 1) {
							int k = 0;
							i += j;
							while (nextLevel == 1) {
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
}
