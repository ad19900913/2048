package util;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	public int key;//����
	public double value;//����
	public List<Node> children;//�ӽڵ�
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
