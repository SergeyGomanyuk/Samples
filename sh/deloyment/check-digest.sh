#!/bin/sh

. ./common.sh

for COMP in ${COMPONENTS}; do
	prepare_artifact_vars ${COMP}
	
	if [ "true" = "${!COMP_SKIP}" ]; then 
        echo -e "${red}skipping ${COMP_DIGEST_FILE}...${reset}"
		continue
	fi

    echo -e "${green}checking ${COMP_DIGEST_FILE}...${reset}"

	sshpass -p ${!COMP_HOST_PASSWORD} scp ${COMP_DIGEST_FILE} ${!COMP_HOST_USER}@${!COMP_HOST}:${!COMP_REMOTE_DIR} 
	sshpass -p ${!COMP_HOST_PASSWORD} ssh ${!COMP_HOST_USER}@${!COMP_HOST} "cd ${!COMP_REMOTE_DIR}; md5sum --quiet -c ${COMP_DIGEST_FILE}; rm -f ${COMP_DIGEST_FILE}"; 
	
done