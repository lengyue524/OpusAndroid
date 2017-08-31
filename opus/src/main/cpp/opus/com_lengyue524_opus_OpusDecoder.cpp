//
// Created by Administrator on 2017/8/25.
//

#include <malloc.h>
#include "include/com_lengyue524_opus_OpusDecoder.h"
#include "include/HuyaDecoder.h"

JNIEXPORT jlong JNICALL Java_com_lengyue524_opus_OpusDecoder_init_1native
        (JNIEnv *env, jobject thiz, jint fs, jint channels) {
    HuyaDecoder *huyaDecoder = new HuyaDecoder();
    huyaDecoder->init(fs, channels);
    return (jlong) huyaDecoder;
}

JNIEXPORT jshortArray JNICALL Java_com_lengyue524_opus_OpusDecoder_decode_1native
        (JNIEnv *env, jobject thiz, jlong decoder, jbyteArray in, jint frame_size) {
    jbyte *in_data = env->GetByteArrayElements(in, JNI_FALSE);
    int data_size = env->GetArrayLength(in);

    opus_int16 *out_data = (short *) malloc(frame_size * 2 * sizeof(short));
    int out_len = ((HuyaDecoder *) decoder)->decode((unsigned char *) in_data, data_size, out_data,
                                                    frame_size);
    jshortArray out = env->NewShortArray(out_len);

    env->ReleaseByteArrayElements(in, in_data, JNI_ABORT);
    env->SetShortArrayRegion(out, 0, out_len, out_data);
    free(out_data);
    return out;
}

JNIEXPORT void JNICALL Java_com_lengyue524_opus_OpusDecoder_destory_1native
        (JNIEnv *env, jobject thiz, jlong decoder) {
    ((HuyaDecoder *) decoder)->destory();
    delete ((HuyaDecoder *) decoder);
}
