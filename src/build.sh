#! /bin/sh
# $Id: build.sh 51 2010-04-06 12:37:24Z marcusvnac $
#

# A skeleton script to call the real build script at
# ./argouml-build/build.sh

cd argouml-build
./build.sh $*
cd ..
