package net.dwild.ets.log320.huffman;

import java.util.Arrays;

/**
 * Created by Dominic on 2015-01-08.
 */
public class TreeNodeValue implements ITreeNode, Comparable<ITreeNode> {
    private byte value;
    private int frequencie;
    private boolean[] key;

    public TreeNodeValue(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public int getFrequencie() {
        return frequencie;
    }

    public void setFrequencie(int frequencie) {
        this.frequencie = frequencie;
    }

    public void incrementFrequencie() {
        frequencie++;
    }

    public boolean[] getKey() {
        return key;
    }

    public void setKey(boolean[] key) {
        this.key = key;
    }

    @Override
    public int compareTo(ITreeNode o) {
        return Integer.compare(frequencie, o.getFrequencie());
    }

    @Override
    public String toString() {
        return "TreeNodeValue{" +
                "value=" + value +
                ", frequencie=" + frequencie +
                ", key=" + Arrays.toString(key) +
                '}';
    }
}
