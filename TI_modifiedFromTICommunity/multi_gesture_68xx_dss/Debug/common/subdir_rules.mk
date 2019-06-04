################################################################################
# Automatically-generated file. Do not edit!
################################################################################

SHELL = cmd.exe

# Each subdirectory must supply rules for building sources it contributes
common/mmwDemo_monitor.oe674: ../common/mmwDemo_monitor.c $(GEN_OPTS) | $(GEN_HDRS)
	@echo 'Building file: "$<"'
	@echo 'Invoking: C6000 Compiler'
	"C:/ti/ti-cgt-c6000_8.1.3/bin/cl6x" -mv6740 --abi=eabi -O3 -ms0 --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI_modifiedFromTICommunity/multi_gesture_68xx_dss" --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI_modifiedFromTICommunity/multi_gesture_68xx_dss/dss" --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI_modifiedFromTICommunity/multi_gesture_68xx_dss/common" --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI_modifiedFromTICommunity/multi_gesture_68xx_dss/common/ANN" --include_path="C:/ti/mmwave_sdk_03_01_01_02/packages" --include_path="C:/ti/mmwave_sdk_03_01_01_02/packages/ti/demo/utils" --include_path="C:/ti/mathlib_c674x_3_1_2_1/packages" --include_path="C:/ti/dsplib_c64Px_3_4_0_0/packages/" --include_path="C:/ti/dsplib_c64Px_3_4_0_0/packages/ti/dsplib/src/DSP_fft16x16/c64P" --include_path="C:/ti/dsplib_c64Px_3_4_0_0/packages/ti/dsplib/src/DSP_fft32x32/c64P" --include_path="C:/ti/ti-cgt-c6000_8.1.3/include" -g --gcc --define=SOC_XWR68XX --define=SUBSYS_DSS --define=MMWAVE_L3RAM_NUM_BANK=6 --define=MMWAVE_SHMEM_TCMA_NUM_BANK=0 --define=MMWAVE_SHMEM_TCMB_NUM_BANK=0 --define=MMWAVE_SHMEM_BANK_SIZE=0x20000 --define=DOWNLOAD_FROM_CCS --define=DebugP_ASSERT_ENABLED --define=_LITTLE_ENDIAN --define=MMW_6843_ODS --diag_error=10015 --display_error_number --diag_wrap=off --diag_warning=225 --gen_func_subsections=on --obj_extension=.oe674 --preproc_with_compile --preproc_dependency="common/mmwDemo_monitor.d_raw" --obj_directory="common" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: "$<"'
	@echo ' '


