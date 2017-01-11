package util;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	public int key;//方向
	public double value;//评分
	public List<Node> children;//子节点
	public double alpha;
	public double beta;
	public Node parent;
	public Node(int key, double value, Node parent) {
		super();
		this.key = key;
		this.value = value;
		this.parent = parent;
		children = new ArrayList<Node>();
	}
	@Override
	public String toString() {
		return key + ":" + value;
	}
	
}
