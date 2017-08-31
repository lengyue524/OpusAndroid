cmake_minimum_required(VERSION 3.4.1)
option(FIXED_POINT "Use fixed-point code (for device with less powerful FPU" NO)
option(USE_ALLOCA "Use alloca for stack arrays (on non-C99 compilers)" NO)
set(OPUS_VERSION 1.2.1)
set(OPUS_ROOT opus/opus-${OPUS_VERSION})
set(CELT_SOURCES
    ${OPUS_ROOT}/celt/bands.c
    ${OPUS_ROOT}/celt/celt.c
    ${OPUS_ROOT}/celt/celt_encoder.c
    ${OPUS_ROOT}/celt/celt_decoder.c
    ${OPUS_ROOT}/celt/cwrs.c
    ${OPUS_ROOT}/celt/entcode.c
    ${OPUS_ROOT}/celt/entdec.c
    ${OPUS_ROOT}/celt/entenc.c
    ${OPUS_ROOT}/celt/kiss_fft.c
    ${OPUS_ROOT}/celt/laplace.c
    ${OPUS_ROOT}/celt/mathops.c
    ${OPUS_ROOT}/celt/mdct.c
    ${OPUS_ROOT}/celt/modes.c
    ${OPUS_ROOT}/celt/pitch.c
    ${OPUS_ROOT}/celt/celt_lpc.c
    ${OPUS_ROOT}/celt/quant_bands.c
    ${OPUS_ROOT}/celt/rate.c
    ${OPUS_ROOT}/celt/vq.c
)
set(SILK_SOURCES
    ${OPUS_ROOT}/silk/CNG.c
    ${OPUS_ROOT}/silk/code_signs.c
    ${OPUS_ROOT}/silk/init_decoder.c
    ${OPUS_ROOT}/silk/decode_core.c
    ${OPUS_ROOT}/silk/decode_frame.c
    ${OPUS_ROOT}/silk/decode_parameters.c
    ${OPUS_ROOT}/silk/decode_indices.c
    ${OPUS_ROOT}/silk/decode_pulses.c
    ${OPUS_ROOT}/silk/decoder_set_fs.c
    ${OPUS_ROOT}/silk/dec_API.c
    ${OPUS_ROOT}/silk/enc_API.c
    ${OPUS_ROOT}/silk/encode_indices.c
    ${OPUS_ROOT}/silk/encode_pulses.c
    ${OPUS_ROOT}/silk/gain_quant.c
    ${OPUS_ROOT}/silk/interpolate.c
    ${OPUS_ROOT}/silk/LP_variable_cutoff.c
    ${OPUS_ROOT}/silk/NLSF_decode.c
    ${OPUS_ROOT}/silk/NSQ.c
    ${OPUS_ROOT}/silk/NSQ_del_dec.c
    ${OPUS_ROOT}/silk/PLC.c
    ${OPUS_ROOT}/silk/shell_coder.c
    ${OPUS_ROOT}/silk/tables_gain.c
    ${OPUS_ROOT}/silk/tables_LTP.c
    ${OPUS_ROOT}/silk/tables_NLSF_CB_NB_MB.c
    ${OPUS_ROOT}/silk/tables_NLSF_CB_WB.c
    ${OPUS_ROOT}/silk/tables_other.c
    ${OPUS_ROOT}/silk/tables_pitch_lag.c
    ${OPUS_ROOT}/silk/tables_pulses_per_block.c
    ${OPUS_ROOT}/silk/VAD.c
    ${OPUS_ROOT}/silk/control_audio_bandwidth.c
    ${OPUS_ROOT}/silk/quant_LTP_gains.c
    ${OPUS_ROOT}/silk/VQ_WMat_EC.c
    ${OPUS_ROOT}/silk/HP_variable_cutoff.c
    ${OPUS_ROOT}/silk/NLSF_encode.c
    ${OPUS_ROOT}/silk/NLSF_VQ.c
    ${OPUS_ROOT}/silk/NLSF_unpack.c
    ${OPUS_ROOT}/silk/NLSF_del_dec_quant.c
    ${OPUS_ROOT}/silk/process_NLSFs.c
    ${OPUS_ROOT}/silk/stereo_LR_to_MS.c
    ${OPUS_ROOT}/silk/stereo_MS_to_LR.c
    ${OPUS_ROOT}/silk/check_control_input.c
    ${OPUS_ROOT}/silk/control_SNR.c
    ${OPUS_ROOT}/silk/init_encoder.c
    ${OPUS_ROOT}/silk/control_codec.c
    ${OPUS_ROOT}/silk/A2NLSF.c
    ${OPUS_ROOT}/silk/ana_filt_bank_1.c
    ${OPUS_ROOT}/silk/biquad_alt.c
    ${OPUS_ROOT}/silk/bwexpander_32.c
    ${OPUS_ROOT}/silk/bwexpander.c
    ${OPUS_ROOT}/silk/debug.c
    ${OPUS_ROOT}/silk/decode_pitch.c
    ${OPUS_ROOT}/silk/inner_prod_aligned.c
    ${OPUS_ROOT}/silk/lin2log.c
    ${OPUS_ROOT}/silk/log2lin.c
    ${OPUS_ROOT}/silk/LPC_analysis_filter.c
    ${OPUS_ROOT}/silk/LPC_inv_pred_gain.c
    ${OPUS_ROOT}/silk/table_LSF_cos.c
    ${OPUS_ROOT}/silk/NLSF2A.c
    ${OPUS_ROOT}/silk/NLSF_stabilize.c
    ${OPUS_ROOT}/silk/NLSF_VQ_weights_laroia.c
    ${OPUS_ROOT}/silk/pitch_est_tables.c
    ${OPUS_ROOT}/silk/resampler.c
    ${OPUS_ROOT}/silk/resampler_down2_3.c
    ${OPUS_ROOT}/silk/resampler_down2.c
    ${OPUS_ROOT}/silk/resampler_private_AR2.c
    ${OPUS_ROOT}/silk/resampler_private_down_FIR.c
    ${OPUS_ROOT}/silk/resampler_private_IIR_FIR.c
    ${OPUS_ROOT}/silk/resampler_private_up2_HQ.c
    ${OPUS_ROOT}/silk/resampler_rom.c
    ${OPUS_ROOT}/silk/sigm_Q15.c
    ${OPUS_ROOT}/silk/sort.c
    ${OPUS_ROOT}/silk/sum_sqr_shift.c
    ${OPUS_ROOT}/silk/stereo_decode_pred.c
    ${OPUS_ROOT}/silk/stereo_encode_pred.c
    ${OPUS_ROOT}/silk/stereo_find_predictor.c
    ${OPUS_ROOT}/silk/stereo_quant_pred.c
    ${OPUS_ROOT}/silk/LPC_fit.c
)
if (DEBUG)
    list(APPEND SILK_SOURCES ${OPUS_ROOT}/silk/debug.c)
endif (DEBUG)
if (FIXED_POINT)
    list(APPEND
         SILK_SOURCES
         ${OPUS_ROOT}/silk/fixed/LTP_analysis_filter_FIX.c
         ${OPUS_ROOT}/silk/fixed/LTP_scale_ctrl_FIX.c
         ${OPUS_ROOT}/silk/fixed/corrMatrix_FIX.c
         ${OPUS_ROOT}/silk/fixed/encode_frame_FIX.c
         ${OPUS_ROOT}/silk/fixed/find_LPC_FIX.c
         ${OPUS_ROOT}/silk/fixed/find_LTP_FIX.c
         ${OPUS_ROOT}/silk/fixed/find_pitch_lags_FIX.c
         ${OPUS_ROOT}/silk/fixed/find_pred_coefs_FIX.c
         ${OPUS_ROOT}/silk/fixed/noise_shape_analysis_FIX.c
         ${OPUS_ROOT}/silk/fixed/process_gains_FIX.c
         ${OPUS_ROOT}/silk/fixed/regularize_correlations_FIX.c
         ${OPUS_ROOT}/silk/fixed/residual_energy16_FIX.c
         ${OPUS_ROOT}/silk/fixed/residual_energy_FIX.c
         ${OPUS_ROOT}/silk/fixed/warped_autocorrelation_FIX.c
         ${OPUS_ROOT}/silk/fixed/apply_sine_window_FIX.c
         ${OPUS_ROOT}/silk/fixed/autocorr_FIX.c
         ${OPUS_ROOT}/silk/fixed/burg_modified_FIX.c
         ${OPUS_ROOT}/silk/fixed/k2a_FIX.c
         ${OPUS_ROOT}/silk/fixed/k2a_Q16_FIX.c
         ${OPUS_ROOT}/silk/fixed/pitch_analysis_core_FIX.c
         ${OPUS_ROOT}/silk/fixed/vector_ops_FIX.c
         ${OPUS_ROOT}/silk/fixed/schur64_FIX.c
         ${OPUS_ROOT}/silk/fixed/schur_FIX.c)
else (FIXED_POINT)
    list(APPEND
         SILK_SOURCES
         ${OPUS_ROOT}/silk/float/apply_sine_window_FLP.c
         ${OPUS_ROOT}/silk/float/corrMatrix_FLP.c
         ${OPUS_ROOT}/silk/float/encode_frame_FLP.c
         ${OPUS_ROOT}/silk/float/find_LPC_FLP.c
         ${OPUS_ROOT}/silk/float/find_LTP_FLP.c
         ${OPUS_ROOT}/silk/float/find_pitch_lags_FLP.c
         ${OPUS_ROOT}/silk/float/find_pred_coefs_FLP.c
         ${OPUS_ROOT}/silk/float/LPC_analysis_filter_FLP.c
         ${OPUS_ROOT}/silk/float/LTP_analysis_filter_FLP.c
         ${OPUS_ROOT}/silk/float/LTP_scale_ctrl_FLP.c
         ${OPUS_ROOT}/silk/float/noise_shape_analysis_FLP.c
         ${OPUS_ROOT}/silk/float/process_gains_FLP.c
         ${OPUS_ROOT}/silk/float/regularize_correlations_FLP.c
         ${OPUS_ROOT}/silk/float/residual_energy_FLP.c
         ${OPUS_ROOT}/silk/float/warped_autocorrelation_FLP.c
         ${OPUS_ROOT}/silk/float/wrappers_FLP.c
         ${OPUS_ROOT}/silk/float/autocorrelation_FLP.c
         ${OPUS_ROOT}/silk/float/burg_modified_FLP.c
         ${OPUS_ROOT}/silk/float/bwexpander_FLP.c
         ${OPUS_ROOT}/silk/float/energy_FLP.c
         ${OPUS_ROOT}/silk/float/inner_product_FLP.c
         ${OPUS_ROOT}/silk/float/k2a_FLP.c
         ${OPUS_ROOT}/silk/float/LPC_inv_pred_gain_FLP.c
         ${OPUS_ROOT}/silk/float/pitch_analysis_core_FLP.c
         ${OPUS_ROOT}/silk/float/scale_copy_vector_FLP.c
         ${OPUS_ROOT}/silk/float/scale_vector_FLP.c
         ${OPUS_ROOT}/silk/float/schur_FLP.c
         ${OPUS_ROOT}/silk/float/sort_FLP.c
)
endif (FIXED_POINT)
set(OPUS_SOURCES
     ${OPUS_ROOT}/src/opus.c
     ${OPUS_ROOT}/src/opus_decoder.c
     ${OPUS_ROOT}/src/opus_encoder.c
     ${OPUS_ROOT}/src/opus_multistream.c
     ${OPUS_ROOT}/src/opus_multistream_encoder.c
     ${OPUS_ROOT}/src/opus_multistream_decoder.c
     ${OPUS_ROOT}/src/repacketizer.c
     ${OPUS_ROOT}/src/mlp.c
     ${OPUS_ROOT}/src/mlp_data.c
     ${OPUS_ROOT}/src/analysis.c
)
add_definitions(-DOPUS_VERSION="\\"${OPUS_VERSION}\\"")
# It is strongly recommended to uncomment one of these
# VAR_ARRAYS: Use C99 variable-length arrays for stack allocation
# USE_ALLOCA: Use alloca() for stack allocation
# If none is defined, then the fallback is a non-threadsafe global array
if (USE_ALLOCA)
    add_definitions(-DUSE_ALLOCA)
else (USE_ALLOCA)
    add_definitions(-DVAR_ARRAYS)
endif (USE_ALLOCA)
# These options affect performance
# HAVE_LRINTF: Use C99 intrinsics to speed up float-to-int conversion
add_definitions(-DHAVE_LRINTF)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -W -Wstrict-prototypes -Wextra -Wcast-align")
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wnested-externs -Wshadow")
add_definitions(-DOPUS_BUILD)
if (FIXED_POINT)
    add_definitions(-DFIXED_POINT=1 -DDISABLE_FLOAT_API)
endif (FIXED_POINT)
include_directories(${OPUS_ROOT}/include
                    ${OPUS_ROOT}/silk
                    ${OPUS_ROOT}/silk/float
                    ${OPUS_ROOT}/silk/fixed
                    ${OPUS_ROOT}/celt
                    ${OPUS_ROOT}/src
                    ${OPUS_ROOT}/../include)
add_library(opus SHARED ${CELT_SOURCES} ${SILK_SOURCES} ${OPUS_SOURCES}
${OPUS_ROOT}/../com_lengyue524_opus_OpusDecoder.cpp
${OPUS_ROOT}/../HuyaDecoder.cpp
${OPUS_ROOT}/../HuyaEncoder.cpp
${OPUS_ROOT}/../com_lengyue524_opus_OpusEncoder.cpp)

find_library( # Defines the name of the path variable that stores the
              # location of the NDK library.
              log-lib

              # Specifies the name of the NDK library that
              # CMake needs to locate.
              log )

# Links your native library against one or more other native libraries.
target_link_libraries( # Specifies the target library.
                       opus

                       # Links the log library to the target library.
                       ${log-lib} )