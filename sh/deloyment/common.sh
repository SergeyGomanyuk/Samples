. ./deployment-descriptor.sh

debug() {
    [ "$_DEBUG" == "debug" ] &&  $@
 }
 
 usage() {
	echo "Usage: ${0} <build_dir> [debug]"
	exit 1
 }
 
 prepare_artifact_vars() {
	debug echo "prepare vars for '${1}'..."
	COMP_HOST=${1}_HOST
	COMP_HOST_USER=${1}_HOST_USER
	COMP_HOST_PASSWORD=${1}_HOST_PASSWORD
	COMP_REMOTE_DIR=${1}_REMOTE_DIR
	COMP_ARTIFACT_DIR=${1}_ARTIFACT_DIR
	COMP_ARTIFACT_FILE=${1}_ARTIFACT_FILE
	COMP_ARTIFACT_UNPACK=${1}_ARTIFACT_UNPACK
	COMP_SKIP=${1}_SKIP
	
	COMP_DIGEST_FILE=digest-${COMP}.md5
	COMP_PREPARE_DIGEST_DIR=digest-${COMP}
	COMP_BACKUP_ZIP=${COMP}-${TIMESTAMP}.zip
	
	debug echo "${COMP_SKIP}=${!COMP_SKIP}"
	debug echo "${COMP_HOST}=${!COMP_HOST}"
	debug echo "${COMP_HOST_USER}=${!COMP_HOST_USER}"
	debug echo "${COMP_HOST_PASSWORD}=${!COMP_HOST_PASSWORD}"
	debug echo "${COMP_REMOTE_DIR}=${!COMP_REMOTE_DIR}"
	debug echo "${COMP_ARTIFACT_DIR}=${!COMP_ARTIFACT_DIR}"
	debug echo "${COMP_ARTIFACT_FILE}=${!COMP_ARTIFACT_FILE}"
	debug echo "${COMP_ARTIFACT_UNPACK}=${!COMP_ARTIFACT_UNPACK}"
}

WORKING_DIR=`pwd`

BUILD_DIR=$1
if [ ! -d "${BUILD_DIR}" ]; then
	usage
fi

_DEBUG=$2
TIMESTAMP=`date +"%y-%m-%d-%H-%M-%S"`

#Black        0;30     Dark Gray     1;30
#Blue         0;34     Light Blue    1;34
#Green        0;32     Light Green   1;32
#Cyan         0;36     Light Cyan    1;36
#Red          0;31     Light Red     1;31
#Purple       0;35     Light Purple  1;35
#Brown/Orange 0;33     Yellow        1;33
#Light Gray   0;37     White         1;37

red='\e[0;31m'; export green;
green='\e[0;32m'; export green;
blue='\e[0;34m'; export blue;
reset='\e[0m';export reset;

debug echo "WORKING_DIR=${WORKING_DIR}"
debug echo "BUILD_DIR=${BUILD_DIR}"
debug echo "_DEBUG=${_DEBUG}"
