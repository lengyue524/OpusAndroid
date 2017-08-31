//
// Created by Administrator on 2017/8/25.
//

#include "include/HuyaDecoder.h"
#include "android/log.h"

void HuyaDecoder::init(opus_int32 Fs, int channels) {
    __android_log_print(ANDROID_LOG_ERROR, "HuyaDecoder", "start");
    int error;
    m_channels = channels;
    opusDecoder = opus_decoder_create(Fs, channels, &error);

    if (error != OPUS_OK) {
        __android_log_print(ANDROID_LOG_ERROR, "HuyaDecoder", "init_error:%d", error);
    }
}

int HuyaDecoder::decode(const unsigned char *in, opus_int32 in_len, opus_int16 *out, int frame_size) {
    __android_log_print(ANDROID_LOG_ERROR, "HuyaDecoder", "decode_frame_size:%d", frame_size);
    int size = opus_decode(opusDecoder, in, in_len, out, frame_size, 0);
    __android_log_print(ANDROID_LOG_ERROR, "HuyaDecoder", "decode_size:%d", size);
    if (size < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "HuyaDecoder", "decode_error:%s", opus_strerror(size));
    }
    return size * m_channels; // 返回采样size ，需要 * 声道数
}

void HuyaDecoder::destory() {
    opus_decoder_destroy(opusDecoder);
}