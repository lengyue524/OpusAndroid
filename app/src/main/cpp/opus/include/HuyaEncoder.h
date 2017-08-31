//
// Created by Administrator on 2017/8/28.
//

#ifndef OPUS_ANDROID_HUYAENCODER_H
#define OPUS_ANDROID_HUYAENCODER_H

#include "opus.h"
#include "Constants.h"

class HuyaEncoder {
public:
    HuyaEncoder() = default;
    void initVoip(opus_int32 Fs, int channels);
    void initAudio(opus_int32 Fs, int channels);
    void initRestrictedLowDelay(opus_int32 Fs, int channels);
    int encode(const opus_int16 *pcm , int frame_size, unsigned char *data);
    void destory();
private:
    OpusEncoder *opusEncoder;
    int m_channels;
    void init(opus_int32 Fs, int channels, int application);
};


#endif //OPUS_ANDROID_HUYAENCODER_H
