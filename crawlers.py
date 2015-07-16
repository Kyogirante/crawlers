#coding:utf-8
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
        baseHtml = resp.read()
    except urllib2.URLError as e:
        print e.message
    except socket.timeout as e:
        print e.message
    return baseHtml

def getUrlNum(html):
    reg = r'<span class=\"current-comment-page\">\[(.+?)\]</span>'
    urlre = re.compile(reg)
    num = re.findall(urlre,html)
    return num[0]

def getImglistUrl(html):
    print 'getImglistUrl'
    reg = r'src="(.+?\.jpg|.+?\.png|.+?\.gif)"'
    image = re.compile(reg)
    imglist = set(re.findall(image,html))
    return imglist

def saveImage(imglist,path,imgNum):
    print 'saveImage'
    for imgurl in imglist:
        str = imgurl[-3:]
        urllib.urlretrieve(imgurl,path+u'\图'+'%s.%s' % (imgNum, str))
        imgNum+=1

print 'begin'
imgNum = 0
pageNum = input("请输入下载页数:".decode('utf-8').encode('gbk'))
path = getDocment()
baseHtml = getHtml("http://jandan.net/pic")
num = int(getUrlNum(baseHtml))
while pageNum > 0:
    url = 'http://jandan.net/pic/page-' + str(num) + '#comments'
    print url
    html = getHtml(url)
    imglist = getImglistUrl(html)
    saveImage(imglist,path,imgNum)
    pageNum-=1
    num-=1





