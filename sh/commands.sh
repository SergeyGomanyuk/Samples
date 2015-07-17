# OS version
lsb_release -a
cat /etc/*-release

# redirect output to files
foo > stdout.txt 2> stderr.txt
#or if you want in same file:
foo > allout.txt 2>&1

# show 10 biggest files & dirs
du -a /var | sort -n -r | head -n 10

# utilizes one CPU core with 100% load
cat /dev/zero > /dev/null

# Get disk space allocation 
df -h

# Observe tail of incrementally updated file
tail -f slee/log/system.log

# Observe tail of incrementally updated file and find particular substring in it
tail -f slee/log/system.log | grep SomBridge

# search in archives
zgrep "nfvo-backend.NFVOperationsImpl" *

# find all files with given text
grep --include=\*.{xml,properties} -rnw ./Host001/ -e "192.168.77.86"

# replace all substrings
grep --include=\*.{xml,properties} -rnl './Host001' -e "192.168.77.86" | xargs -i@ sed -i 's/192.168.77.86/10.247.137.45/g' @

# ssh example with key and without StrictHostKeyChecking
ssh -o "StrictHostKeyChecking no" -i ~/jnetx.pem 10.10.11.110
ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no peter@192.168.0.100

# Forwarding one IP to another
# http://superuser.com/questions/681705/using-iptables-to-redirect-ip-address
# Firstly: ip forwarding needs to enabled:
# Edit /etc/sysctl.conf and ensure that the line "net.ipv4.ip_forward = 1" is there and not commented out and check
# http://www.debuntu.org/how-to-redirecting-network-traffic-to-a-new-ip-using-iptables/
# echo "1" > /proc/sys/net/ipv4/ip_forward

# Add forwarding
iptables -t nat -A OUTPUT -d [ipaddress1] -j DNAT --to-destination [ipaddress2]

# Remove forwarding 
iptables -t nat -D OUTPUT 1

# Services 
# service command - list running services
service --status-all
# To print the status of apache (httpd) service:
service httpd status
# List all known services (configured via SysV)
chkconfig --list
# List service and their open ports
netstat -tulpn

#Search or find big files Linux (50MB) in current directory, enter:
find . -type f -size +50000k -exec ls -lh {} \; | awk '{ print $9 ": " $5 }'

# scp
#Скопировать файл "file.txt" с удаленной машины на локальную:
scp ваш_логин@имя_хоста.ru:file.txt /некоторая/директория 
#Скопировать файл "file.txt" с локальной машины на удаленную:
scp file.txt ваш_логин@имя_хоста.ru:/некоторая/директория
#Скопировать директорию "my_dir" с локальной машины на удаленную в директорию "your_dir":
scp -r my_dir ваш_логин@имя_хоста.ru:/некая/директория/your_dir 
#Скопировать файл "file.txt" с удаленной машины host1.domain.ru на другую удаленную машину host2.domain.ru:
scp ваш_логин@host1.domain.ru:/некая/директория/file.txt ваш_логин@host2.domain.ru:/некая/иная/директория/ 
#Скопировать файлы "file1.txt" и "file2.txt" с локальной машины на удаленную, в свою домашнюю директорию:
scp file1.txt file2.txt ваш_логин@имя_хоста.ru:~
#Скопировать несколько файлов с удаленной машины на локальную, в текущую директорию:
scp ваш_логин@имя_хоста.ru:/некая/директория/\{a,b,c\} . 
scp ваш_логин@имя_хоста.ru:~/\{file1.txt,file2.txt\} . 
#Замечание о скорости копирования
#По умолчанию scp использует при передаче данных алгоритм шифрования Triple-DES. Можно несколько увеличить скорость передачи, включив алгоритм Blowfish, для этого в командной строке следует добавить -c blowfish:
scp -c blowfish некий_файл ваш_логин@имя_хоста.ru:~

# dns server
cat /etc/resolv.conf 

# find package files
yum list | grep <package_name>
rpm -ql <package_name>

# find mac address of default gateway
ifconfig
ping
arp -a

# Create File of a Given Size (http://linuxcommando.blogspot.ru/2008/02/create-file-of-given-size.html)
dd if=/dev/zero of=output.dat  bs=1024  count=10240
dd if=/dev/zero of=output.dat  bs=1M  count=10

# Test TCP & UDP connection using netcut (https://www.digitalocean.com/community/tutorials/how-to-use-netcat-to-establish-and-test-tcp-and-udp-connections-on-a-vps)
netcat -v -l 4444
netcat -v domain.com 4444

netcat -v -l 4444 > received_file
netcat -v domain.com 4444 < original_file

# descrypt and store DVD (http://forum.doom9.org/showthread.php?t=149529)
filestat /dev/dvdrom
dd if=/dev/dvdrom of=dvd.iso bs=2048 conv=noerror,sync



