#!/bin/sh
# A rather bad example of a shell script triggered by java rtime.exec
# to fire off an perl script that generates excel worksheets.
# archive2excel.pl uses cfg includes that can't be sourced in
# when running directly from rtime.exec and you cannot change the
# working directory of a JVM process AFAIK
# will be replaced when archive2excel.pl is replaced by java version.
# philw@ebi.ac.uk

# cwd to pl location
cd /data/web/internal/scripts/perl/

# Remove existing statistics.xls files
files=`ls /data/web/htdocs/statistics/statistics.xls`
if [ "$files" != "" ]; then
    echo "removing files:\n$files"
    rm /data/web/htdocs/statistics/statistics.xls
fi

# run pl file
./archive2excel.pl -v -o statistics.xls -u phil.1 -p wilkinson -d emmastr > /data/web/TESTsubmissions/jdata.out
 