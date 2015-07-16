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
    req_header = {'User-Agent':'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6'}
    req_timeout = 20
    try:
        req = urllib2.Request(url,None,req_header)
        resp = urllib2.urlopen(req,None,req_timeout)
        html = resp.read()
    except urllib2.URLError as e:
        print e.message
    except socket.timeout as e:
        print e.message
    return html

def getImglistUrl(html):
    print 'getImglistUrl'
    reg = r'src="(.+?\.jpg|.+?\.png|.+?\.gif)"'
    image = re.compile(reg)
    imglist = set(re.findall(image,html))
    print imglist
    return imglist

def saveImage(imglist,path):
    print 'saveImage'
    x = 0
    for imgurl in imglist:
        str = imgurl[-3:]
        urllib.urlretrieve(imgurl,path+u'\图'+'%s.%s' % (x, str))
        x+=1

print 'begin'
path = getDocment()
html = getHtml("http://jandan.net/pic/page-6925#comments")
imglist = getImglistUrl(html)
saveImage(imglist,path)



