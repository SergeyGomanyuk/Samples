#!/bin/bash
LOG_FILE="./rotate-log.log"
LOGROTATE_CONF="./rotate-log.conf"
LOGROTATE_STATE="./rotate-log.state"

# see man logrotate
rotate_log() {
    echo -e "\"$LOG_FILE\" {\n\tcreate\n\trotate 2\n\tsize 1024\n}" > $LOGROTATE_CONF
    /usr/sbin/logrotate -s $LOGROTATE_STATE $LOGROTATE_CONF #> /dev/null 2>&1
    rm -f "$LOGROTATE_CONF"
    rm -f "$LOGROTATE_STATE"
}

rm ${LOG_FILE}*

while [ ! -f "${LOG_FILE}.2" ]
do

 { echo "some text...."; } >> "$LOG_FILE" 2>&1;
 rotate_log;

done
