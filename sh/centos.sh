# install CentOS as Basic Server
# prevent inheriting locale setting in ssh sessions
# see 'Accept locale-related environment variables' section
vim /etc/ssh/sshd_config

# enable EPEL repository using following command
rpm -Uvh http://download.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm

# enable elrepo repository using following command
rpm --import http://elrepo.org/RPM-GPG-KEY-elrepo.org
rpm -Uvh http://elrepo.org/elrepo-release-6-5.el6.elrepo.noarch.rpm

# install ntfs hfsplus support
yum -y install ntfs-3g
yum -y install kmod-hfsplus

# mount/unmount usb
umount /mnt/USB
mount /dev/sdb2 /mnt/USB

# mount and format new disk
ls /dev/sd*
#/dev/sda /dev/sda1 /dev/sda2 /dev/sdb
#/dev/sdb - new disk

# format new disk use m for menu
#fdisk has partition limit size
yum install gdisk
gdisk /dev/sdb
# format
/sbin/mkfs.ext4 -L /timemachine /dev/sdb1
/sbin/mkfs.ext4 -L /data /dev/sdb2
# add automount entries to /etc/fstab
#LABEL=/timemachine /timemachine ext4 defaults 1 2
#LABEL=/data /data ext4 defaults 1 2
vim /etc/fstab

# rip dvd to iso
dd if=/dev/scd0 of=image_name.iso

# disable selinux
# SELINUX=disabled
vim /etc/selinux/config

# create user and include it in sudoers list
groupadd sudoers
useradd -G sudoers sergg
passwd sergg
chmod u+w /etc/sudoers
vim /etc/sudoers # %sudoers        ALL=(ALL)       NOPASSWD: ALL
chmod u-w /etc/sudoers

# install dropbox (https://gist.github.com/briangonzalez/6903025)

# install trasmission
cd /etc/yum.repos.d/
wget http://geekery.altervista.org/geekery-el6-x86_64.repo
yum install trasmission transmission-daemon
# create trasmission download dir
mkdir /home/transmission
chown transmission:transmission transmission
# set download dir
# "download-dir": "/home/transmission/Downloads",
# "download-queue-enabled": true,
# "download-queue-size": 1,
# set whitelist
# "rpc-whitelist": "127.0.0.1,192.168.1.104,192.168.1.107",
# or
# "rpc-whitelist-enabled": false
# "start-added-torrents": true,
# "trash-original-torrent-files": true,
# "umask": 2,
# "watch-dir": "/home/transmission/Dropbox/Torrents",
# "watch-dir-enabled": true

vim /var/lib/transmission/settings.json
chkconfig transmission-daemon on  
service transmission-daemon start

# open ports 
# http://www.binarytides.com/open-http-port-iptables-centos/
# edit iptables: open 9091 - transmisssion web 51413 - torrent
# -A INPUT -p tcp -m tcp --dport 9091 -m state --state NEW,ESTABLISHED -j ACCEPT
# -A INPUT -p tcp -m tcp --dport 51413 -m state --state NEW,ESTABLISHED -j ACCEPT
vim /etc/sysconfig/iptables 
service iptables restart

# open trasmission http://192.168.1.101:9091/transmission/web/

# install plex media server (see last version at plex.tv)
wget http://downloads.plexapp.com/plex-media-server/0.9.9.12.504-3e7f93c/plexmediaserver-0.9.9.12.504-3e7f93c.x86_64.rpm
yum install plexmediaserver-0.9.9.12.504-3e7f93c.x86_64.rpm
# install kinopoisk plugin (download the last from http://sourceforge.net/projects/russianplex/)
cd /var/lib/plexmediaserver/Library/Application\ Support/Plex\ Media\ Server/Plug-ins/
wget <kinopoisk_plex_plugin_link>
tar xvfz <downloaded_kinopoisk_plex_plugin>
rm -rf <downloaded_kinopoisk_plex_plugin>
chkconfig plexmediaserver on  
service plexmediaserver start 

# open plex ports
#-A INPUT -p tcp -m tcp --dport 32400 -m state --state NEW,ESTABLISHED -j ACCEPT
#-A INPUT -p tcp -m tcp --dport 32443 -m state --state NEW,ESTABLISHED -j ACCEPT
#-A INPUT -p udp -m udp --dport 1900 -m state --state NEW,ESTABLISHED -j ACCEPT
#-A INPUT -p udp -m udp --dport 5353 -m state --state NEW,ESTABLISHED -j ACCEPT
#-A INPUT -p udp -m udp --dport 32410 -m state --state NEW,ESTABLISHED -j ACCEPT
#-A INPUT -p udp -m udp --dport 32412 -m state --state NEW,ESTABLISHED -j ACCEPT
#-A INPUT -p udp -m udp --dport 32413 -m state --state NEW,ESTABLISHED -j ACCEPT
#-A INPUT -p udp -m udp --dport 32414 -m state --state NEW,ESTABLISHED -j ACCEPT
#-A INPUT -p udp -m udp --dport 32469 -m state --state NEW,ESTABLISHED -j ACCEPT
vim /etc/sysconfig/iptables 
service iptables restart

# open plex http://192.168.1.101:32400/web/index.html
# Settings>Server>Agents Kinopoisk (enable LocalMediaAssets(Movies), etc...)
# Settingg>Server>Transcoder advanced - Enable Dolby Digital on Apple TV
# Add movies library Home>+... set transmission download dir and kinpoisk agent 

# PlexConnect - Plex on ATV
# open ports
# plex connect (http, https, dns)
#-A INPUT -p tcp -m tcp --dport 80 -m state --state NEW,ESTABLISHED -j ACCEPT
#-A INPUT -p tcp -m tcp --dport 443 -m state --state NEW,ESTABLISHED -j ACCEPT
#-A INPUT -p udp -m udp --dport 53 -j ACCEPT
#-A OUTPUT -p udp -m udp --sport 53 -j ACCEPT
vim /etc/sysconfig/iptables 
service iptables restart

# install python 2.7.x (requred for plex connect keeping 2.6.x for centos needs)
# as described here https://github.com/0xdata/h2o/wiki/Installing-python-2.7-on-centos-6.3.-Follow-this-sequence-exactly-for-centos-machine-only
# install plex connect - https://github.com/iBaa/PlexConnect/wiki/Install-Guide
# to run PlexConnect as a service
cp /usr/local/lib/PlexConnect/PlexConnect_daemon.bash /usr/local/lib/PlexConnect/PlexConnect_daemon.bash.bak
# add to PlexConnect_daemon.bash
#chkconfig: 345 20 80
#description: plexconnect
# and change PYTHON
#PYTHON="/usr/local/bin/python"
vi /usr/local/lib/PlexConnect/PlexConnect_daemon.bash
ln -s /usr/local/lib/PlexConnect/PlexConnect_daemon.bash  /etc/init.d/plexconnect
chkconfig plexconnect on
service plexconnect start

# configure timemachine 
yum -y install netatalk avahi
useradd timemachine -M
passwd timemachine
chown -R timemachine:timemachine /timemachine/
#You need to add the following line to the bottom of the file afpd.conf:-
#- -tcp -noddp -uamlist uams_randnum.so,uams_dhx.so,uams_dhx2.so -nosavepassword
nano /etc/netatalk/afpd.conf
#Add to bottom of AppleVolumes.default
#/timemachine TimeMachine allow:<username> cnidscheme:dbd options:usedots,upriv,tm
nano /etc/netatalk/AppleVolumes.default
# create/etc/avahi/services/afpd.service
# <?xml version="1.0" standalone='no'?><!--*-nxml-*-->
# <!DOCTYPE service-group SYSTEM "avahi-service.dtd">
# <service-group>
#    <name replace-wildcards="yes">%h</name>
#    <service>
#        <type>_afpovertcp._tcp</type>
#        <port>548</port>
#    </service>
#    <service>
#        <type>_device-info._tcp</type>
#        <port>0</port>
#        <txt-record>model=MacPro</txt-record>
#    </service>
#</service-group>
nano /etc/avahi/services/afpd.service
chkconfig netatalk on
chkconfig avahi-daemon on
service netatalk start
service  avahi-daemon start
# add to iptables and resart service
#-A INPUT -p tcp -m state --state NEW -m tcp --dport 548 -j ACCEPT
#-A INPUT -p tcp -m state --state NEW -m tcp --dport 5353 -j ACCEPT
#-A INPUT -p tcp -m state --state NEW -m tcp --dport 5354 -j ACCEPT
#-A INPUT -p udp -m udp --dport 548 -j ACCEPT
#-A INPUT -p udp -m udp --dport 5353 -j ACCEPT
#-A INPUT -p udp -m udp --dport 5354 -j ACCEPT
vim /etc/sysconfig/iptables 
service iptables restart

# install samba (http://lintut.com/easy-samba-server-installation-on-centos-6-5/) and setup share (see http://www.ubuntugeek.com/howto-setup-samba-server-with-tdbsam-backend.html)
