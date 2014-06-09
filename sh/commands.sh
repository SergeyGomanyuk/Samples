# OS version
lsb_release -a
cat /etc/*-release

# Get disk space allocation 
df -h

# Observe tail of incrementally updated file
tail -f slee/log/system.log

# Observe tail of incrementally updated file and find particular substring in it
tail -f slee/log/system.log | grep SomBridge

# search in archives
zgrep "nfvo-backend.NFVOperationsImpl" *

# ssh example with key and without StrictHostKeyChecking
ssh -o "StrictHostKeyChecking no" -i ~/jnetx.pem 10.10.11.110

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

