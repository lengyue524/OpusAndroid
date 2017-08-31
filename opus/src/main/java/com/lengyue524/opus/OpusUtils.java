package com.lengyue524.opus;

import android.media.AudioFormat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by lihang1@yy.com on 2017/8/30.
 */

public class OpusUtils {
    public static byte[] packetEncodeData(byte[] encoded, int len, int frameSize) {
        byte[] lenField = intTobyte(len);
        byte[] frameSizeField = intTobyte(frameSize);
        byte[] packet = new byte[len + 4 + 4];
        packet[0] = lenField[0];
        packet[1] = lenField[1];
        packet[2] = lenField[2];
        packet[3] = lenField[3];
        packet[4] = frameSizeField[0];
        packet[5] = frameSizeField[1];
        packet[6] = frameSizeField[2];
        packet[7] = frameSizeField[3];
        for (int i = 0; i < len; i++) {
            packet[i + 8] = encoded[i];
        }
        return packet;
    }

    public static OpusPacket readPacket(InputStream is) {
        byte[] lenField = new byte[4];
        byte[] frameSizeField = new byte[4];
        try {
            int readLen = is.read(lenField);
            if (readLen == -1) {
                return null;
            }
            if (readLen != 4) {
                throw new RuntimeException("readPacket error: read packet lenth error:" + readLen);
            }
            int len = byteToint(lenField);

            int readFrameSize = is.read(frameSizeField);
            if (readFrameSize == -1) {
                return null;
            }
            if (readFrameSize != 4) {
                throw new RuntimeException("readPacket error: read frame size error:" + readFrameSize);
            }
            int frameSize = byteToint(frameSizeField);

            byte[] packet = new byte[len];
            int readPacket = is.read(packet);
            if (readPacket != len) {
                throw new RuntimeException("readPacket error: read packet  error:" + readPacket);
            }
            return new OpusPacket(len, frameSize, packet);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] intTobyte(int i) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
//        byte[] bytes = new byte[4];
//        bytes[0] = (byte) (i>>24);
//        bytes[1] = (byte) ((i>>16)&0xFF);
//        bytes[2] = (byte) ((i>>8)&0xFF);
//        bytes[3] = (byte) (i&0xFF);
//        return bytes;
    }

    public static int byteToint(byte[] b) {
        if (b.length != 4) {
            throw new RuntimeException("byteToint error: bytes length must be 4");
        }
        ByteBuffer bb = ByteBuffer.wrap(b);
        return bb.asIntBuffer().get();
//        return (((int)b[0])<<24) | (((int)b[1])<<16)
//                | (((int)b[2])<< 8) |  ((int)b[3]);
    }

    public static int getFrameSize(int pcmLength, int channelConfig) {
        int channels;
        if (channelConfig == AudioFormat.CHANNEL_OUT_MONO) {
            channels = 1;
        } else if (channelConfig == AudioFormat.CHANNEL_OUT_STEREO) {
            channels = 2;
        } else {
            throw new RuntimeException("wrong Channel Config");
        }
        return pcmLength / channels;
    }
}
