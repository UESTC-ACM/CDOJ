if [ $# -ne 1 ]; then
  curl https://api.github.com/repos/UESTC-ACM/CDOJ/issues\?state\=closed > change_log.log
else
  version=$1
  curl https://api.github.com/repos/UESTC-ACM/CDOJ/issues\?milestone\=${version}\&state\=closed > change_log.log
fi
./format_change_log.py > formatted.log
rm -f change_log.log

