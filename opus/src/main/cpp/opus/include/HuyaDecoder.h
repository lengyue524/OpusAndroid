//
// Created by Administrator on 2017/8/25.
//

#ifndef OPUS_ANDROID_HUYADECODER_H
#define OPUS_ANDROID_HUYADECODER_H

#include "opus.h"
#include "Constants.h"

class HuyaDecoder {
public:
    HuyaDecoder() = default;
    void init(opus_int32 Fs, int channels);
    int decode(const unsigned char * in, opus_int32 in_len, opus_int16 *out, int frame_size);
    void destory();

private:
    OpusDecoder *opusDecoder;
    int m_channels;
};


#endif //OPUS_ANDROID_HUYADECODER_H
