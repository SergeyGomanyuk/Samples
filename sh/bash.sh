#!/bin/bash

# in case of directory use '/', i.e. '*/dir/'
FILE_NAME_PATTERN=*.sh

# http://stackoverflow.com/questions/6363441/check-if-a-file-exists-with-wildcard-in-shell-script
echo "check if file/directory exists using name pattern $FILE_NAME_PATTERN"
if ls -d1 $FILE_NAME_PATTERN > /dev/null 2>&1; then
	echo "files with mask: $FILE_NAME_PATTERN exist"
fi

echo "iterate over files using file name pattern $FILE_NAME_PATTERN"
for FILE_NAME in $FILE_NAME_PATTERN; do
	echo "file: $FILE_NAME"
done