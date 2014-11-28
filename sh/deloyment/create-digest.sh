#!/bin/sh

. ./common.sh

for COMP in ${COMPONENTS}; do
	prepare_artifact_vars ${COMP}
	
	if [ "true" = "${!COMP_SKIP}" ]; then 
        echo -e "${red}skipping ${COMP_DIGEST_FILE}...${reset}"
		continue
	fi
	
	rm -rf ${COMP_DIGEST_FILE} ${COMP_PREPARE_DIGEST_DIR}
	
    echo -e "${green}preparing ${COMP_DIGEST_FILE}...${reset}"
	
	if [ "true" = "${!COMP_ARTIFACT_UNPACK}" ]; then
		mkdir -p ${COMP_PREPARE_DIGEST_DIR}
		unzip ${BUILD_DIR}/${!COMP_ARTIFACT_DIR}/${!COMP_ARTIFACT_FILE} -d ${COMP_PREPARE_DIGEST_DIR} > /dev/null
		cd ${COMP_PREPARE_DIGEST_DIR}
		find . -type f -exec md5sum {} >> ../${COMP_DIGEST_FILE} \;
		cd ..
		rm -rf ${COMP_PREPARE_DIGEST_DIR}
	else
	    cd ${BUILD_DIR}/${!COMP_ARTIFACT_DIR}
		md5sum ${!COMP_ARTIFACT_FILE} >> ${WORKING_DIR}/${COMP_DIGEST_FILE}
	    cd ${WORKING_DIR}
	fi
	
done
