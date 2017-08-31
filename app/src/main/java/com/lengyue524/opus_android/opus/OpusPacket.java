package com.lengyue524.opus_android.opus;

/**
 * Created by lihang1@yy.com on 2017/8/30.
 */

public class OpusPacket {
    public OpusPacket(int len, int frameSize, byte[] packet) {
        this.len = len;
        this.frameSize = frameSize;
        this.packet = packet;
    }

    public int len;
    public int frameSize;
    public byte[] packet;
}
