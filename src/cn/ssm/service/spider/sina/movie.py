# coding=utf-8
"""
Target：爬取movie_list中的电影的热门评论第一页（用户＋博文ID＋评论）
Begin_time：2016.12.12

"""
from selenium import webdriver
import selenium.webdriver.support.ui as ui
import re
import time
import random
import os
import sys
reload(sys)
sys.setdefaultencoding( "utf-8" )

# # 先调用无界面浏览器FireFox
browser = webdriver.Firefox()
wait = ui.WebDriverWait(browser,10)

# 读取电影列表
def getFile(path,charset = 'utf-8'):
    with open(path) as f :
        lines = [line.strip().decode(charset) for line in f.readlines()]
    f.close()
    return lines

# 按电影的名称命名写入所需
def writeFile(path,lines,movie):
    dir = ['/home/iiip/PycharmProjects/Weibo/google/Result/'+movie]
    for i in dir:
        if not os.path.isdir(i):
            os.makedirs(i)
    with open(dir[0]+'/'+path,'w') as f:
        f.write(lines)
    # f.flush()# 刷新一下
    f.close()

# 自动登陆微博
def LoginWeibo(username,password):
    try:
        # 输入用户名／密码进行登陆　
        print u'准备登陆Weibo.cn网站...'
        browser.get("http://login.weibo.cn/login/")
        elem_user = browser.find_element_by_name("mobile")
        elem_user.send_keys(username)
        elem_pwd = browser.find_element_by_xpath("/html/body/div[2]/form/div/input[2]")
        elem_pwd.send_keys(password)
        # 暂停时间输入验证码　
        time.sleep(50)
    except Exception,e:
        print "Error:",e
    finally:
        print u'End LoginWeibo!\n\n'

#　获取电影的用户＋博文
def getBlog(key):
    try:
        url = "http://weibo.cn/search/mblog?hideSearchFrame=&keyword=" +"电影"+ key + "&page=%d"
        for a in xrange(2):
            comment_url = url % (a+1)
            # response = requests.get(comment_url)
            # content = response.text
            # comment = re.findall('class="ctt">:(.*?)</', content)
            # print comment_url
            for i in range(5):
                browser.get(comment_url)
                html = browser.page_source
                second = random.randint(4,9)
                time.sleep(second)
                if not '请尝试更换关键词，再次搜索' in html:
                    break
            # print type(html)
            html = str(html)
            b = re.findall('"ctt">:(.*?)</span></div>', html)
            c = re.findall('<div><a class="nk" href=.*?>(.*?)</a>', html)
            d = re.findall('class="c" id="(.*?)">', html)
            for i in range(len(b)):
                b[i] = re.sub('<.*?>', '', b[i])
                b[i] = re.sub('http://t.cn/RqSH1fu', '', b[i])
            for i in range(len(b)):
                print  c[i] + ' ' + d[i] + ' ' + b[i]
                with open('/home/iiip/PycharmProjects/Weibo/User/Result/'+d[i]+'.txt', 'a') as f:
                    f.write(c[i] + ' ' + d[i] + ' ' + b[i] + '\n')
    except Exception, e:
        print "Error:", e

if __name__ == '__main__':
    # 定义变量
    username = 'pqwsb72145@163.com'  # 输入用户名
    password = 'pachong'  # 输入密码
    LoginWeibo(username, password)
    get_path = '/home/iiip/PycharmProjects/Weibo/google/movie_list/movie_1.txt'
    movie = getFile(get_path)
    for i in range(len(movie)):
        key = movie[i]
        print key
        getBlog(key)
        write_path = '/home/totoro/codes/PycharmProjects/Weibo/google/Result/' + movie[i]
        writeFile(write_path,getBlog(key))