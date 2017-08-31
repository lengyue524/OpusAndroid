//
// Created by Administrator on 2017/8/28.
//
#include "include/com_lengyue524_opus_android_opus_OpusEncoder.h"
#include "include/HuyaEncoder.h"
#include "android/log.h"


JNIEXPORT jlong JNICALL Java_com_lengyue524_opus_1android_opus_OpusEncoder_init_1voip_1native
        (JNIEnv *env, jobject thiz, jint fs, jint channels) {
    HuyaEncoder *huyaEncoder = new HuyaEncoder();
    huyaEncoder->initVoip(fs, channels);
    return (jlong) huyaEncoder;
}

JNIEXPORT jlong JNICALL Java_com_lengyue524_opus_1android_opus_OpusEncoder_init_1audio_1native
        (JNIEnv *env, jobject thiz, jint fs, jint channels) {
    HuyaEncoder *huyaEncoder = new HuyaEncoder();
    huyaEncoder->initAudio(fs, channels);
    return (jlong) huyaEncoder;
}

JNIEXPORT jlong JNICALL Java_com_lengyue524_opus_1android_opus_OpusEncoder_init_1restricted_1low_1delay_1native
        (JNIEnv *env, jobject thiz, jint fs, jint channels) {
    HuyaEncoder *huyaEncoder = new HuyaEncoder();
    huyaEncoder->initRestrictedLowDelay(fs, channels);
    return (jlong) huyaEncoder;
}

JNIEXPORT jbyteArray JNICALL Java_com_lengyue524_opus_1android_opus_OpusEncoder_encode_1native
        (JNIEnv *env, jobject thiz, jlong encoder, jshortArray pcm, jint frame_size) {
    short * pcm_data = env->GetShortArrayElements(pcm, JNI_FALSE);
    int data_len = env->GetArrayLength(pcm);
    __android_log_print(ANDROID_LOG_ERROR, "HuyaEncoder", "pcm_len:%d", data_len);
    unsigned char * data = new unsigned char[data_len * 2];
    int out_len = ((HuyaEncoder *)encoder)->encode(pcm_data, frame_size, data);
    jbyteArray out = env->NewByteArray(out_len);

    env->ReleaseShortArrayElements(pcm, pcm_data, JNI_ABORT);
    env->SetByteArrayRegion(out, 0, out_len, (jbyte *)data);
    return out;
}

JNIEXPORT void JNICALL Java_com_lengyue524_opus_1android_opus_OpusEncoder_destory_1native
        (JNIEnv *env, jobject thiz, jlong encoder) {
    ((HuyaEncoder *)encoder)->destory();
    delete ((HuyaEncoder *)encoder);
}


