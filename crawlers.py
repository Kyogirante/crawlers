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
    content = ""
    try:
        req = urllib2.Request(url,None,req_header)
        resp = urllib2.urlopen(req,None,req_timeout)
        content = resp.read()
    except urllib2.URLError as e:
        print e.message
    except socket.timeout as e:
        print e.message
    return content

def getUrlNum(html):
    reg = r'<span class=\"current-comment-page\">\[(.+?)\]</span>'
    urlre = re.compile(reg)
    num = re.findall(urlre,html)
    return num[0]

def getImglistUrl(html):
    print 'getImglistUrl'
    reg = r'src="(.+?\.jpg|.+?\.png|.+?\.gif|.+?\.jpeg)"'
    image = re.compile(reg)
    imglist = set(re.findall(image,html)).remove('http://s.jandan.com/static/img/chart.png')
    return imglist

def saveImage(imglist,path,imgNum):
    print 'saveImage'
    for imgurl in imglist:
        str = imgurl[-3:]
        urllib.urlretrieve(imgurl,path+u'\图'+'%s.%s' % (imgNum, str))
        imgNum+=1

print 'begin'
imgNum = 0
pageNum = input('请输入下载页数:'.decode('utf-8').encode('gbk'))
path = getDocment()
baseHtml = getHtml('http://jandan.net/ooxx')
num = int(getUrlNum(baseHtml))#仅仅是获得第一页的页码
while pageNum > 0:
    url = 'http://jandan.net/ooxx/page-' + str(num) + '#comments'
    print url
    html = getHtml(url)
    imglist = getImglistUrl(html)
    saveImage(imglist,path,imgNum)
    pageNum-=1
    num-=1





