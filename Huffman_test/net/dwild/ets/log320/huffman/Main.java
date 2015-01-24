package net.dwild.ets.log320.huffman;

import net.dwild.ets.log320.huffman.bit.BitReader;
import net.dwild.ets.log320.huffman.bit.BitWriter;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String usage = "Usage: " +
                "\n\tPour compresser: java -jar huffman.jar -c|--compress FICHIER" +
                "\n\tPour décompresser: java -jar huffman.jar -d|--decompress FICHIER";
        if (args.length != 2 ) {
            System.out.println(usage);
        }
        else {
            if (args[0].equals("-c") || args[0].equals("--compress")){
                compress(args[1]);
            }
            else if (args[0].equals("-d") || args[0].equals("--decompress")){
                //decompress(args[1]);
            }
            else {
                System.out.println(usage);
            }
        }
    }

    public static void compress(String file_to_compress) throws IOException {
        BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file_to_compress));

        ArrayList<TreeNodeValue> values = new ArrayList<TreeNodeValue>();

        TreeNodeValue[] valuesArray = new TreeNodeValue[256];

        int c;
        while ((c = fileInputStream.read()) != -1) {
            TreeNodeValue value = valuesArray[c];

            if(value == null) {
                value = new TreeNodeValue((byte) c);
                values.add(value);

                valuesArray[c] = value;
            }

            value.incrementFrequencie();
        }

        Collections.sort(values);

        PriorityQueue<ITreeNode> queue = new PriorityQueue<ITreeNode>(values);
        while(queue.size() > 1) {
            TreeNode node = new TreeNode();
            node.setRight(queue.poll());
            node.setLeft(queue.poll());
            node.setFrequencie(node.getRight().getFrequencie() + node.getLeft().getFrequencie());
            queue.add(node);
        }

        TreeNode tree = (TreeNode) queue.poll();

        generateKey(new boolean[0], tree);

        BitWriter bitWriter = new BitWriter(new BufferedOutputStream(new FileOutputStream(file_to_compress + ".huf")));

        ArrayList<Boolean> treeStructure = writeTreeStructure(tree);

        ArrayList<Byte> treeContent = writeTreeContent(tree);

        bitWriter.writeByte(treeContent.size());

        for(Byte b:treeContent) {
            bitWriter.writeByte(b);
        }

        for(Boolean bit:treeStructure) {
            bitWriter.writeBit(bit);
        }

        bitWriter.flush();

        fileInputStream = new BufferedInputStream(new FileInputStream(file_to_compress));

        while ((c = fileInputStream.read()) != -1) {
            TreeNodeValue value = valuesArray[c];
            if(value != null) {
                bitWriter.writeBits(value.getKey());
            }
        }

        fileInputStream.close();
        bitWriter.close();
    }

    /*
    public static void decompress() throws IOException {
        BitReader bitReader = new BitReader(new FileInputStream("test_compress.huf"));

        ArrayList<TreeNodeValue> values = new ArrayList<TreeNodeValue>();

        int treeNodeValueSize = bitReader.readByte();
        for(int i = 0; i < treeNodeValueSize; i++) {
            values.add(new TreeNodeValue((byte) bitReader.readByte()));
        }

        TreeNode currentNode = new TreeNode();

        buffer = new ArrayDeque<Boolean>();

        while ((c = fileInputStream.read()) != -1) {
            boolean[] value = getBoolean((byte) c);

            for(boolean b:value) {
                buffer.add(b);
            }
        }

        fileOutputStream = new FileOutputStream("test_compress.huf.bmp");
        ITreeNode currentNode = tree;
        while(!buffer.isEmpty()) {
            if(currentNode instanceof TreeNodeValue) {
                fileOutputStream.write(((TreeNodeValue) currentNode).getValue());
                currentNode = tree;
            }
            else if(currentNode instanceof TreeNode) {
                boolean isRight = buffer.poll();

                if(isRight) {
                    currentNode = ((TreeNode) currentNode).getRight();
                }
                else {
                    currentNode = ((TreeNode) currentNode).getLeft();
                }
            }
        }
    }
    */

    public static boolean readTreeStructure(BitReader bitReader) {


        return false;
    }

    public static ArrayList<Boolean> writeTreeStructure(TreeNode node) {
        ArrayList<Boolean> buffer = new ArrayList<Boolean>();

        ITreeNode left = node.getLeft();
        if(left instanceof TreeNodeValue) {
            buffer.add(true);
        }
        else if(left instanceof TreeNode) {
            buffer.add(false);
            buffer.addAll(writeTreeStructure((TreeNode) left));
        }

        ITreeNode right = node.getRight();
        if(right instanceof TreeNodeValue) {
            buffer.add(true);
        }
        else if(right instanceof TreeNode) {
            buffer.add(false);
            buffer.addAll(writeTreeStructure((TreeNode) right));
        }

        return buffer;
    }

    public static ArrayList<Byte> writeTreeContent(TreeNode node) {
        ArrayList<Byte> buffer = new ArrayList<Byte>();

        ITreeNode left = node.getLeft();
        if(left instanceof TreeNodeValue) {
            buffer.add(((TreeNodeValue) left).getValue());
        }
        else if(left instanceof TreeNode) {
            buffer.addAll(writeTreeContent((TreeNode) left));
        }

        ITreeNode right = node.getRight();
        if(right instanceof TreeNodeValue) {
            buffer.add(((TreeNodeValue) right).getValue());
        }
        else if(right instanceof TreeNode) {
            buffer.addAll(writeTreeContent((TreeNode) right));
        }

        return buffer;
    }

    public static void generateKey(boolean[] lastKey, TreeNode treeNode) {
        ITreeNode left = treeNode.getLeft();

        if(left != null) {
            boolean[] leftLastKey = Arrays.copyOf(lastKey, lastKey.length + 1);
            leftLastKey[lastKey.length] = false;
            if(left instanceof TreeNodeValue) {
                TreeNodeValue value = (TreeNodeValue) left;
                value.setKey(leftLastKey);
            }
            else if(left instanceof TreeNode) {
                generateKey(leftLastKey, (TreeNode) left);
            }
        }

        ITreeNode right = treeNode.getRight();
        if(right != null) {
            boolean[] rightLastKey = Arrays.copyOf(lastKey, lastKey.length + 1);
            rightLastKey[lastKey.length] = true;
            if(right instanceof TreeNodeValue) {
                TreeNodeValue value = (TreeNodeValue) right;
                value.setKey(rightLastKey);
            }
            else if(right instanceof TreeNode) {
                generateKey(rightLastKey, (TreeNode) right);
            }
        }

    }
}
