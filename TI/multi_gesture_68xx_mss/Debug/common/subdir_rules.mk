################################################################################
# Automatically-generated file. Do not edit!
################################################################################

SHELL = cmd.exe

# Each subdirectory must supply rules for building sources it contributes
common/cli.oer4f: ../common/cli.c $(GEN_OPTS) | $(GEN_HDRS)
	@echo 'Building file: "$<"'
	@echo 'Invoking: ARM Compiler'
	"C:/ti/ti-cgt-arm_16.9.6.LTS/bin/armcl" -mv7R4 --code_state=16 --float_support=VFPv3D16 -me -O3 --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI/multi_gesture_68xx_mss" --include_path="C:/ti/mmwave_sdk_03_01_01_02/packages" --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI/multi_gesture_68xx_mss/mss" --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI/multi_gesture_68xx_mss/common" --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI/multi_gesture_68xx_mss/common/ANN" --include_path="C:/ti/ti-cgt-arm_16.9.6.LTS/include" --define=SOC_XWR68XX --define=SUBSYS_MSS --define=DOWNLOAD_FROM_CCS --define=MMWAVE_L3RAM_NUM_BANK=6 --define=MMWAVE_SHMEM_TCMA_NUM_BANK=0 --define=MMWAVE_SHMEM_TCMB_NUM_BANK=0 --define=MMWAVE_SHMEM_BANK_SIZE=0x20000 --define=DebugP_ASSERT_ENABLED --define=_LITTLE_ENDIAN --define=APP_RESOURCE_FILE='<'ti/demo/xwr68xx/mmw/mmw_res.h'>' -g --diag_warning=225 --diag_wrap=off --display_error_number --gen_func_subsections=on --enum_type=int --abi=eabi --obj_extension=.oer4f --preproc_with_compile --preproc_dependency="common/cli.d_raw" --obj_directory="common" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: "$<"'
	@echo ' '

common/cli_mmwave.oer4f: ../common/cli_mmwave.c $(GEN_OPTS) | $(GEN_HDRS)
	@echo 'Building file: "$<"'
	@echo 'Invoking: ARM Compiler'
	"C:/ti/ti-cgt-arm_16.9.6.LTS/bin/armcl" -mv7R4 --code_state=16 --float_support=VFPv3D16 -me -O3 --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI/multi_gesture_68xx_mss" --include_path="C:/ti/mmwave_sdk_03_01_01_02/packages" --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI/multi_gesture_68xx_mss/mss" --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI/multi_gesture_68xx_mss/common" --include_path="C:/Users/sonif/Documents/StudiumMaster/Semester3/Masterthesis/TI/multi_gesture_68xx_mss/common/ANN" --include_path="C:/ti/ti-cgt-arm_16.9.6.LTS/include" --define=SOC_XWR68XX --define=SUBSYS_MSS --define=DOWNLOAD_FROM_CCS --define=MMWAVE_L3RAM_NUM_BANK=6 --define=MMWAVE_SHMEM_TCMA_NUM_BANK=0 --define=MMWAVE_SHMEM_TCMB_NUM_BANK=0 --define=MMWAVE_SHMEM_BANK_SIZE=0x20000 --define=DebugP_ASSERT_ENABLED --define=_LITTLE_ENDIAN --define=APP_RESOURCE_FILE='<'ti/demo/xwr68xx/mmw/mmw_res.h'>' -g --diag_warning=225 --diag_wrap=off --display_error_number --gen_func_subsections=on --enum_type=int --abi=eabi --obj_extension=.oer4f --preproc_with_compile --preproc_dependency="common/cli_mmwave.d_raw" --obj_directory="common" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: "$<"'
	@echo ' '


