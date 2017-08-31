//
// Created by Administrator on 2017/8/29.
//

#ifndef OPUS_ANDROID_CONSTANTS_H
#define OPUS_ANDROID_CONSTANTS_H

#include <opus_multistream.h>
#include "opus.h"

#if 0 /* This is a hack that replaces the normal encoder/decoder with the multistream version */
#define OpusEncoder OpusMSEncoder
#define OpusDecoder OpusMSDecoder
#define opus_encode opus_multistream_encode
#define opus_decode opus_multistream_decode
#define opus_encoder_ctl opus_multistream_encoder_ctl
#define opus_decoder_ctl opus_multistream_decoder_ctl
#define opus_encoder_create ms_opus_encoder_create
#define opus_decoder_create ms_opus_decoder_create
#define opus_encoder_destroy opus_multistream_encoder_destroy
#define opus_decoder_destroy opus_multistream_decoder_destroy

static OpusEncoder *ms_opus_encoder_create(opus_int32 Fs, int channels, int application, int *error)
{
   int streams, coupled_streams;
   unsigned char mapping[256];
   return (OpusEncoder *)opus_multistream_surround_encoder_create(Fs, channels, 1, &streams, &coupled_streams, mapping, application, error);
}
static OpusDecoder *ms_opus_decoder_create(opus_int32 Fs, int channels, int *error)
{
   int streams;
   int coupled_streams;
   unsigned char mapping[256]={0,1};
   streams = 1;
   coupled_streams = channels==2;
   return (OpusDecoder *)opus_multistream_decoder_create(Fs, channels, streams, coupled_streams, mapping, error);
}
#endif

#endif //OPUS_ANDROID_CONSTANTS_H
