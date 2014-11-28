#!/bin/sh

. ./create-digest.sh



for COMP in ${COMPONENTS}; do
	prepare_artifact_vars ${COMP}
	
	if [ "true" = "${!COMP_SKIP}" ]; then 
        echo -e "${red}skipping deployment of ${COMP}...${reset}"
		continue
	fi

    echo -e "${green}deploying ${COMP}...${reset}"
    echo -e "${blue}creating remote directory ${!COMP_REMOTE_DIR}"
	sshpass -p ${!COMP_HOST_PASSWORD} ssh ${!COMP_HOST_USER}@${!COMP_HOST} "mkdir -p ${!COMP_REMOTE_DIR}";
	echo -e "${blue}creating remote backup ${COMP_BACKUP_ZIP}...${reset}"
	if [ "true" = "${!COMP_ARTIFACT_UNPACK}" ]; then
		sshpass -p ${!COMP_HOST_PASSWORD} ssh ${!COMP_HOST_USER}@${!COMP_HOST} "cd ${!COMP_REMOTE_DIR}; zip ${COMP_BACKUP_ZIP} * -r -x *.zip *.log > /dev/null";
	else
		sshpass -p ${!COMP_HOST_PASSWORD} ssh ${!COMP_HOST_USER}@${!COMP_HOST} "cd ${!COMP_REMOTE_DIR}; zip ${COMP_BACKUP_ZIP} ${!COMP_ARTIFACT_FILE}";
	fi
	echo -e "${blue}copying ${!COMP_ARTIFACT_FILE} to ${!COMP_HOST_USER}@${!COMP_HOST}:${!COMP_REMOTE_DIR}...${reset}"
	sshpass -p ${!COMP_HOST_PASSWORD} scp ${BUILD_DIR}/${!COMP_ARTIFACT_DIR}/${!COMP_ARTIFACT_FILE} ${!COMP_HOST_USER}@${!COMP_HOST}:${!COMP_REMOTE_DIR} 
	if [ "true" = "${!COMP_ARTIFACT_UNPACK}" ]; then
		echo -e "${blue}unziping ${!COMP_ARTIFACT_FILE}...${reset}"
		sshpass -p ${!COMP_HOST_PASSWORD} ssh ${!COMP_HOST_USER}@${!COMP_HOST} "cd ${!COMP_REMOTE_DIR}; unzip -o ${!COMP_ARTIFACT_FILE} > /dev/null; chmod a+x *.sh";
	fi
done
