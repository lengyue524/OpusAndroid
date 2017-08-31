package com.lengyue524.opus;

/**
 * Created by lihang1@yy.com on 2017/8/22.
 */

public class OpusDecoder {
    private long decoder;

    private native long init_native(int fs, int channels);

    private native short[] decode_native(long decoder, byte[] in, int frame_size);

    private native void destory_native(long decoder);

    public void init(int fs, int channels) {
        decoder = init_native(fs, channels);
    }

    public short[] decode(byte[] in, int frameSize) {
        return decode_native(decoder, in, frameSize);
    }

    public void descory() {
        destory_native(decoder);
    }
}
