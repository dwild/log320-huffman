package net.dwild.ets.log320.huffman;

import java.util.ArrayList;

public class TreeParser {
	private ArrayList<Boolean> bufferStructure = new ArrayList<Boolean>();
	private ArrayList<Byte> bufferContent = new ArrayList<Byte>();
	
	public TreeParser() { }
	
	/**
	 * Permet de parcourir une seule fois l'arbre à la place de deux fois avec l'ancienne méthode
	 * @param node
	 */
	public void writeTree(TreeNode node) {
        ITreeNode left = node.getLeft();
        if(left instanceof TreeNodeValue) {
        	this.bufferStructure.add(true);
        	this.bufferContent.add(((TreeNodeValue) left).getValue());
        }
        else if(left instanceof TreeNode) {
        	this.bufferStructure.add(false);
        	this.writeTree((TreeNode) left);
        }

        ITreeNode right = node.getRight();
        if(right instanceof TreeNodeValue) {
        	this.bufferStructure.add(true);
        	this.bufferContent.add(((TreeNodeValue) right).getValue());
        }
        else if(right instanceof TreeNode) {
        	this.bufferStructure.add(false);
        	this.writeTree((TreeNode) right);
        }
	}
	
	public ArrayList<Boolean> getBufferStructure() {
		return bufferStructure;
	}
	
	public ArrayList<Byte> getBufferContent() {
		return bufferContent;
	}
}
