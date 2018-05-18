import socket
import urllib.request
import sys
import os
import subprocess
import ctypes

url = sys.argv[1]
directory = os.path.dirname(os.path.abspath(__file__))
file = os.path.join(directory, sys.argv[2])

def Mbox(title, text, style):
    ctypes.windll.user32.MessageBoxW(0, text, title, style)


def startNewJar():
  os.startfile(sys.argv[2])

def downloadJar(downloadURL, fileName):
  print('Downloading Updated Jarfile')

  Mbox('Info', 'A mandatory update is required for you to use this application. It will download and start itself automatically.', 0)

  try:
    urllib.request.urlretrieve(downloadURL, fileName)
    print('File successfully downloaded')
    return
  except IOError:
    print('Something went wrong with the file download. Maybe check the link?')

if not os.path.exists(file):
  downloadJar(url, file)
  startNewJar()
  
else: 
  os.remove(file)
  downloadJar(url, file)
  startNewJar()
  