#!/bin/bash

chk_v_mraa="$1"
chk_v_upm="$2"
echo "Expected mraa version : $chk_v_mraa"
echo "Expected upm version : $chk_v_upm"

export http_proxy="http://proxy-src.research.ge.com:8080"
export https_proxy="http://proxy-src.research.ge.com:8080"
export HTTPS_PROXY="http://proxy-src.research.ge.com:8080"
export HTTP_PROXY="http://proxy-src.research.ge.com:8080"
export no_proxy="127.0.0.1,localhost,localhost.localdomain"

mraa_version=$(opkg list mraa |head -n 1 | awk -F"-" '{print $2}' | sed 's/ //g')
upm_version=$(opkg list upm |head -n 1 | awk -F"-" '{print $2}' | sed 's/ //g')
opkg update
if [[ "$chk_v_mraa" != "$mraa_version" ]]; then
   echo "mraa version on edison $mraa_version does not match with exptected version $chk_v_mraa"
   echo "updating mraa to latest packages"
   opkg install mraa
   mraa_version=$(opkg list mraa |head -n 1 | awk -F"-" '{print $2}' | sed 's/ //g')
   echo "mraa version now $mraa_version"
else
   echo "mraa version on edison $mraa_version matches with expected version $chk_v_mraa"
fi

if [[ "$chk_v_upm" != "$upm_version" ]]; then
   echo "upm version on edison $upm_version does not match with exptected version $chk_v_upm"
   echo "updating upm to latest packages"
   opkg install upm
   upm_version=$(opkg list upm |head -n 1 | awk -F"-" '{print $2}' | sed 's/ //g')
   echo "upm version now $upm_version"
else
   echo "upm version on edison $upm_version matches with expected version $chk_v_upm"
fi

if type keytool >/dev/null; then
	echo "keytool found"
else
	echo "keytool not found add now..."
	ln -sf $(dirname `readlink -f $(which java)`)/keytool /usr/bin/keytool
	echo "keytool added successfully"
fi