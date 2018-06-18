#!/bin/bash

# https://unix.stackexchange.com/questions/236084/how-do-i-create-a-service-for-a-shell-script-so-i-can-start-and-stop-it-like-a-d

#WD="/project/autotst/jatf/deploy/manual_run/test-app-xl-sc/mgmt-rest"
#CMD="/usr/java/jdk1.8.0_74/bin/java -Dcom.jnetx.asp.home='/project/autotst/jatf/deploy/manual_run/test-app-xl-sc' -jar lib/asp-xl-mgmt-rest-*.jar"
#PID_FILE="/project/autotst/jatf/deploy/manual_run/test-app-xl-sc/mgmt-rest/bin/xl-mgmt-rest.pid"
#OUTPUT_FILE="/project/autotst/jatf/deploy/manual_run/test-app-xl-sc/mgmt-rest/bin/xl-mgmt-rest.out"

WD=./
CMD=$0
PID_FILE=/var/run/`basename $0`.pid
OUTPUT_FILE=/var/run/`basename $0`.out

case "$1" in
start)
   if ! $0 status; then
       CUR_WD=`pwd`
       cd ${WD}
       ${CMD} > ${OUTPUT_FILE} 2>&1 &
       echo $! > ${PID_FILE}
       cd ${CUR_WD}
       echo `basename $0` has been started, pid=`cat ${PID_FILE}`
   else
      exit 1
   fi
   ;;
stop)
   if $0 status; then
       kill `cat ${PID_FILE}`
       rm ${PID_FILE}
       echo `basename $0` has been stopped
   else
      exit 1
   fi
   ;;
restart)
   $0 stop
   $0 start
   ;;
status)
   if [ -e ${PID_FILE} ]; then
      PID=`cat ${PID_FILE}`
      PS=`ps ${PID}`
      if [[ ${PS} = *${CMD:0:255}* ]]; then
         echo `basename $0` is running, pid=${PID}
      else
         rm ${PID_FILE}
         echo `basename $0` is NOT running
         exit 1
      fi
   else
      echo `basename $0` is NOT running
      exit 1
   fi
   ;;
*)
   echo "Usage: `basename $0` {start|stop|status|restart}"
esac

exit 0
