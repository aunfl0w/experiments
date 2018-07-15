#!/usr/bin/python3
import codecs
import array
import sys

value = sys.argv[1]
print("guess decoded string of hex encoded string {}".format(value))
hexval = codecs.decode(value, 'hex')

for xor in range(0,256):
    xored = [ a ^ xor for a in hexval]
    strval = ''.join(map(chr,xored))
    
    if str(strval).isprintable():
        print("try key{}".format(xor))
        print(''.join(map(chr,xored)))

print('\n\ndone')