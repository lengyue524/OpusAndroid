package com.lengyue524.opus_android;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.lengyue524.opus_android.opus.OpusConstants;
import com.lengyue524.opus_android.opus.OpusDecoder;
import com.lengyue524.opus_android.opus.OpusEncoder;
import com.lengyue524.opus_android.opus.OpusPacket;
import com.lengyue524.opus_android.opus.OpusUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("opus");
    }

    public static int CHANNELS = 2;

    private Button sampleText;
    private Button record;
    private AudioCapturer mAudioCapturer;
    private OpusEncoder mOpusEncoder;
    FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sampleText = (Button) findViewById(R.id.sample_text);
        record = (Button) findViewById(R.id.record);

        sampleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusic();
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAudioCapturer.isCaptureStarted()) {
                    mAudioCapturer.stopCapture();
                    record.setText("开始录音");
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    File recordFile = getRecordFile();
                    if (recordFile.exists()) {
                        recordFile.delete();
                    }
                    mAudioCapturer.startCapture();
                    record.setText("结束录音");
                    try {
                        fos = new FileOutputStream(recordFile, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        Log.d("MainActivity", "init OpusEncoder");
        mOpusEncoder = new OpusEncoder();
        mOpusEncoder.initAudio(OpusConstants.DEFAULT_SAMPLE_RATE, CHANNELS);

        mAudioCapturer = new AudioCapturer();
        mAudioCapturer.setOnAudioFrameCapturedListener(new AudioCapturer.OnAudioFrameCapturedListener() {
            @Override
            public void onAudioFrameCaptured(short[] audioData) {
                int frameSize = OpusUtils.getFrameSize(audioData.length, AudioFormat.CHANNEL_OUT_STEREO);
                byte[] encode = mOpusEncoder.encode(audioData, frameSize);
//                byte[] encode = new byte[audioData.length * 2];
//                int encode_len = mOpuCodec.encode(encoder, audioData, 0, encode);
                Log.d(this.getClass().getSimpleName(), "encode_len:" + encode.length);
                try {
                    if (fos != null) {
                        byte[] bytes = OpusUtils.packetEncodeData(encode, encode.length, frameSize);
                        fos.write(bytes, 0, bytes.length);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                try {
//                    ByteBuffer b = ByteBuffer.allocate(audioData.length * 2);
//                    b.asShortBuffer().put(audioData);
//                    if (fos != null)
//                        fos.write(b.array());
////                        fos.write(shortToByte(audioData));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    @NonNull
    private File getRecordFile() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        return new File(externalStorageDirectory, "example.opus");
    }

    private void startMusic() {
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                OpusConstants.DEFAULT_SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT,
                AudioTrack.getMinBufferSize(OpusConstants.DEFAULT_SAMPLE_RATE, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT),
                AudioTrack.MODE_STREAM);
        audioTrack.play();
//    // Example of a call to a native method
//    TextView tv = (TextView) findViewById(R.id.sample_text);
//    tv.setText(stringFromJNI());
        OpusDecoder opusDecoder = new OpusDecoder();
        opusDecoder.init(OpusConstants.DEFAULT_SAMPLE_RATE, CHANNELS);
//        decoder = mOpuCodec.createDecoder();
        try {
            FileInputStream fis = new FileInputStream(getRecordFile());

//            byte[] bytes;
//            while ((bytes = OpusUtils.readPacket(fis)) != null) {
//                short[] decoded = new short[bytes.length * 10];
//                int decode_len = mOpuCodec.decode(decoder, bytes, bytes.length, decoded);
//                Log.d(this.getClass().getSimpleName(), "decode_len:" + decode_len);
//                audioTrack.write(decoded, 0, decode_len);
//            }
            OpusPacket opusPacket;
            while ((opusPacket = OpusUtils.readPacket(fis)) != null) {
                short[] decode = opusDecoder.decode(opusPacket.packet, opusPacket.frameSize);
                Log.d(this.getClass().getSimpleName(), "decode_len:" + decode.length);
                audioTrack.write(decode, 0, decode.length);
            }

//            byte[] datas = new byte[OpusConstants.getFrameSize(OpusConstants.DEFAULT_SAMPLE_RATE,
//                    OpusConstants.DEFAULT_CHANNEL_CONFIG, OpusConstants.DEFAULT_AUDIO_FORMAT)];
//            int len;
//            while ((len = fis.read(datas)) > 0) {
////                short[] decoded = opusDecoder.decode(datas);
//                short[] decoded = new short[datas.length * 10];
//                int decode_len = mOpuCodec.decode(decoder, datas, datas.length, decoded);
//                Log.d(this.getClass().getSimpleName(), "decode_len:" + decode_len);
//                audioTrack.write(decoded, 0, decoded.length);
////                ByteBuffer sb = ByteBuffer.wrap(datas);
////                ShortBuffer shortBuffer = sb.asShortBuffer();
////                short[] array = new short[len >> 1];
////                shortBuffer.get(array);
////
//////                short[] array = byteToShort(datas);
////                audioTrack.write(array, 0, array.length);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        audioTrack.stop();
//        audioTrack.release();
    }

    private byte[] shortToByte(short[] data) {
        byte[] bytes = new byte[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            int pos = i << 1;
            bytes[pos] = (byte) (data[i] >> 8);
            bytes[pos + 1] = (byte) (data[i] & 0x00ff);
        }
        return bytes;
    }

    private short[] byteToShort(byte[] data) {
        short[] shorts = new short[data.length >> 1];
        for (int i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                short temp1 = data[i];
                short temp2 = data[i + 1];
                shorts[i >> 1] = (short) (temp1 << 8 & temp2);
            }
        }
        return shorts;
    }

//    /**
//     * A native method that is implemented by the 'native-lib' native library,
//     * which is packaged with this application.
//     */
//    public native String stringFromJNI();
//
//    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }
}
