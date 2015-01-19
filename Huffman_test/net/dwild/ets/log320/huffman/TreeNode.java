package net.dwild.ets.log320.huffman;

/**
 * Created by Dominic on 2015-01-08.
 */
public class TreeNode implements ITreeNode {
    private ITreeNode left;
    private ITreeNode right;

    public TreeNode() {

    }

    public int getFrequencie() {
        int leftFrequencie = 0;
        int rightFrequencie = 0;

        if(left != null) {
            leftFrequencie = left.getFrequencie();
        }

        if(right != null) {
            rightFrequencie = right.getFrequencie();
        }

        return leftFrequencie + rightFrequencie;
    }

    public ITreeNode getLeft() {
        return left;
    }

    public void setLeft(ITreeNode left) {
        this.left = left;
    }

    public ITreeNode getRight() {
        return right;
    }

    public void setRight(ITreeNode right) {
        this.right = right;
    }

    @Override
    public int compareTo(ITreeNode o) {
        return Integer.compare(getFrequencie(), o.getFrequencie());
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "frequencie=" + getFrequencie() +
                ",left=" + left +
                ", right=" + right +
                '}';
    }
}
