package com.lengyue524.opus_android;

/**
 * Created by lihang1@yy.com on 2017/8/28.
 */

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.lengyue524.opus_android.opus.OpusConstants;

public class AudioCapturer {

    private static final String TAG = "AudioCapturer";

    private static final int DEFAULT_SOURCE = MediaRecorder.AudioSource.MIC;

    private AudioRecord mAudioRecord;
    private int mMinBufferSize = 0;

    private Thread mCaptureThread;
    private boolean mIsCaptureStarted = false;
    private volatile boolean mIsLoopExit = false;

    private OnAudioFrameCapturedListener mAudioFrameCapturedListener;

    public interface OnAudioFrameCapturedListener {
        public void onAudioFrameCaptured(short[] audioData);
    }

    public boolean isCaptureStarted() {
        return mIsCaptureStarted;
    }

    public void setOnAudioFrameCapturedListener(OnAudioFrameCapturedListener listener) {
        mAudioFrameCapturedListener = listener;
    }

    public boolean startCapture() {
        return startCapture(DEFAULT_SOURCE, OpusConstants.DEFAULT_SAMPLE_RATE, OpusConstants.DEFAULT_CHANNEL_CONFIG,
                OpusConstants.DEFAULT_AUDIO_FORMAT);
    }

    public boolean startCapture(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {

        if (mIsCaptureStarted) {
            Log.e(TAG, "Capture already started !");
            return false;
        }

        mMinBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        Log.d(TAG, "mMinBufferSize:" + mMinBufferSize);
        if (mMinBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            Log.e(TAG, "Invalid parameter !");
            return false;
        }
        Log.d(TAG, "getMinBufferSize = " + mMinBufferSize + " bytes !");

        mAudioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, mMinBufferSize);
        if (mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
            Log.e(TAG, "AudioRecord initialize fail !");
            return false;
        }

        mAudioRecord.startRecording();

        mIsLoopExit = false;
        mCaptureThread = new Thread(new AudioCaptureRunnable());
        mCaptureThread.start();

        mIsCaptureStarted = true;

        Log.d(TAG, "Start audio capture success !");

        return true;
    }

    public void stopCapture() {

        if (!mIsCaptureStarted) {
            return;
        }

        mIsLoopExit = true;
        try {
            mCaptureThread.interrupt();
            mCaptureThread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            mAudioRecord.stop();
        }

        mAudioRecord.release();

        mIsCaptureStarted = false;
        mAudioFrameCapturedListener = null;

        Log.d(TAG, "Stop audio capture success !");
    }

    private class AudioCaptureRunnable implements Runnable {

        @Override
        public void run() {

            while (!mIsLoopExit) {

                short[] buffer = new short[mMinBufferSize];

                int ret = mAudioRecord.read(buffer, 0, mMinBufferSize);
                if (ret == AudioRecord.ERROR_INVALID_OPERATION) {
                    Log.e(TAG, "Error ERROR_INVALID_OPERATION");
                } else if (ret == AudioRecord.ERROR_BAD_VALUE) {
                    Log.e(TAG, "Error ERROR_BAD_VALUE");
                } else {
                    if (mAudioFrameCapturedListener != null) {
                        mAudioFrameCapturedListener.onAudioFrameCaptured(buffer);
                    }
                    Log.d(TAG, "OK, Captured " + ret + " bytes !");
                }
            }
        }
    }
}
