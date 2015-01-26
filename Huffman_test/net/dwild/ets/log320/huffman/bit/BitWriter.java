package net.dwild.ets.log320.huffman.bit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BitWriter {

    private OutputStream outputStream;

    private int currentByte;

    private int bitPosition = 7;

    public BitWriter(OutputStream fileOutputStream) {
        this.outputStream = fileOutputStream;
    }

    public void writeBit(boolean bit) throws IOException {
        if(bitPosition < 0) {
            flush();
        }

        if(bit) {
            currentByte |= (1 << bitPosition);
        }
        else {
            currentByte &= ~(1 << bitPosition);
        }

        bitPosition--;
    }

    public void writeBits(boolean[] bits) throws IOException {
        for(boolean bit:bits) {
            writeBit(bit);
        }
    }
    
    public void writeInt(int size) throws IOException{
    	 outputStream.write((size >> 24) & 255);
    	 outputStream.write((size >> 16) & 255);
    	 outputStream.write((size >> 8) & 255);
    	 outputStream.write((size) & 255);
    }

    public void flush() throws IOException {
        if(bitPosition < 7) {
            outputStream.write(currentByte);

            currentByte = 0;
            bitPosition = 7;
        }
    }

    public void writeByte(int b) throws IOException {
        flush();
        outputStream.write(b);
    }

    public void close() throws IOException {
        flush();
        outputStream.close();
    }
}
