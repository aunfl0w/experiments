#!/usr/bin/python3
import codecs
import array
import sys


key = int(sys.argv[1])
value = sys.argv[2]
print("encoding to base64 encoded with {} string {}".format(key, value))
bytevalue = bytearray(value, 'utf-8')
xored = [ key ^ a for a in bytevalue ]
xoredarr = array.array('B', xored)
print("xored hex string {}".format(codecs.encode(xoredarr,'hex')))
