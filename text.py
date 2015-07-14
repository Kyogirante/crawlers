
# -*- coding=utf-8 -*-
import urllib2
import urllib
import socket
import os
import re

def Docment():
    print u'把文件存在E:\Python\图（请输入数字或字母）'
    h=raw_input()
    path=u'E:\Python\图'+str(h)
    if not os.path.exists(path):
        os.makedirs(path)
    return path
def getallurl(html):
    reg=r"a href='(.*?\.htm)'"
    allurl= re.compile(reg)
    allList = re.findall(allurl,html)
    return allList
def getHTML(url):
    url=url
    req_header = {'User-Agent':'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6'}
    req_timeout = 20
    html='cuowu'
    try:
        req = urllib2.Request(url,None,req_header)
        resp = urllib2.urlopen(req,None,req_timeout)
        html = resp.read()
    except urllib2.URLError as e:
        print e.message
    except socket.timeout as e:
        getHTML(url,fu)
    return html
def getImg(html,path):
    reg = r'img class=IMG_show border=0 src=(.*?\.jpg)'
    imgre= re.compile(reg)
    imgList = re.findall(imgre, html)
    if imgList:
        print 'ghasghg',path
        for imgurl in imgList:
            print imgurl
            content2=urllib2.urlopen(imgurl).read()
            with open(path+'/'+imgurl[-7:],'wb') as code:
                code.write(content2)
    else:
        return 0
def getallurl(html):
    reg=r"a href='(.*?\.htm)'"
    allurl= re.compile(reg)
    allList = re.findall(allurl,html)
    return allList

j=1
i=0
print u'请输入网址:'
ul=raw_input()
print u'开始下载'
print u'第'+str(j)+u'页'
html=getHTML(ul)
allList=getallurl(html)
path=Docment()
getImg(html,path)
while i<len(allList):
    for lis in allList:
        l=lis[i]
        url=r'http://www.umei.cc/p/gaoqing/rihan/'+lis
        i=i+1
        j=j+1
        html=getHTML(url)
        getImg(html,path)
        print u'第'+str(j)+u'页'
    else:
        print u'下载完毕'