#coding=utf-8
import urllib
import urllib2
import os
import re

def getDocment():
    print 'getDocment'
    path=u'E:\Python'
    if not os.path.exists(path):
        os.makedirs(path)
    return path

def getHtml(url):
    print 'getHtml'
    page = urllib.urlopen(url)
    html = page.read()
    return html

def getImglistUrl(html):
    print 'getImglistUrl'
    reg = r'src="(.+?\.jpg)" pic_ext'
    image = re.compile(reg)
    imglist = re.findall(image,html)
    print 'imglist ='
    print imglist
    return imglist

def saveImage(imglist,path):
    print 'saveImage'
    x = 0
    for imgurl in imglist:
        print 'imgurl'
        urllib.urlretrieve(imgurl,path+u'\图'+'%s.jpg' % x)
        x+=1

print 'begin'
path = getDocment()
html = getHtml("http://tieba.baidu.com/p/3892706715?fr=frs")
imglist = getImglistUrl(html)
saveImage(imglist,path)



