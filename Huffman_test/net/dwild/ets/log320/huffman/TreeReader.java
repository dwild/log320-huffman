package net.dwild.ets.log320.huffman;

import java.io.IOException;
import java.util.ArrayList;

import net.dwild.ets.log320.huffman.bit.BitReader;

public class TreeReader {
	private ArrayList<TreeNodeValue> treeNodeValues = new ArrayList<TreeNodeValue>();
	private TreeNode tree;
	
	private int i;
	
	public TreeReader() {}
	
	public TreeNode readTree(BitReader bitReader) throws IOException {
        treeNodeValues = new ArrayList<TreeNodeValue>();

        int treeNodeValueSize = bitReader.readByte();
        for(int i = 0; i < treeNodeValueSize; i++) {
        	treeNodeValues.add(new TreeNodeValue((byte) bitReader.readByte()));
        }
        
        i = 0;
        
        tree = (TreeNode) readTreeNode(new TreeNode(), bitReader);
		return tree;
	}
	
	private ITreeNode readTreeNode(TreeNode treeNode, BitReader bitReader) throws IOException {
		boolean leftBit = bitReader.readBit();
		
		if (leftBit) {
			treeNode.setLeft(treeNodeValues.get(i));
			i++;
		}
		else {
			TreeNode leftNode = new TreeNode();
			treeNode.setLeft(leftNode);
			readTreeNode(leftNode, bitReader);
		}
		
		boolean rightBit = bitReader.readBit();
		
		if (rightBit) {
			treeNode.setRight(treeNodeValues.get(i));
			i++;
		}
		else {
			TreeNode rightNode = new TreeNode();
			treeNode.setRight(rightNode);
			readTreeNode(rightNode, bitReader);
		}
		
		return treeNode;
	}

	public ArrayList<TreeNodeValue> getTreeNodeValues() {
		return treeNodeValues;
	}

	public TreeNode getTree() {
		return tree;
	} 
}
