package net.dwild.ets.log320.huffman.bit;

import java.io.IOException;
import java.io.InputStream;

public class BitReader {

    private InputStream inputStream;

    private int currentByte;

    private int bitPosition = -1;

    public BitReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public boolean readBit() throws IOException {
        if(bitPosition < 0) {
            currentByte = inputStream.read();
            bitPosition = 7;
        }

        boolean bit = ((currentByte >> bitPosition) & 0x1) == 1;

        bitPosition--;

        return bit;
    }

    public int readByte() throws IOException {
        bitPosition = -1;

        return inputStream.read();
    }

    public void close() throws IOException {
        inputStream.close();
    }
}
