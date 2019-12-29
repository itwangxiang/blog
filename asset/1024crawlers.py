import requests
import re
import os
import json
from lxml import etree

# 草榴 URL
base_url = 'https://t66y.com/'
# 达盖尔版块的前缀 URL
dge_prefix_url = 'http://t66y.com/thread0806.php?fid=16&page='

# 缓存文件
dbJsonFile = 'data.json'
dataJson = {}

# 模拟请求 Headers
headers = {
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'Accept-Encoding': 'gzip, deflate',
    'Accept-Language': 'zh-CN,zh;q=0.9',
    'Proxy-Connection': 'keep-alive',
    'Host': 't66y.com',
    'Referer': 'http://t66y.com/thread0806.php?fid=16',
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36',
    'Cookie': '__cfduid=dccaf83180801300d8243479f98b542e91577519364; PHPSESSID=jlka2tcth2b95kq9fhr51gbd54; serverInfo=t66y.com%7C149.129.81.172; 227c9_lastvisit=0%091577526232%09%2Fnotice.php%3F'
}

# 用作目录和文件名称时不合法的字符
invalidchars = ['?', '\\', '/', ':', '*', '<', '>', '|', '\"']
def process_str(oristr):
    for invalidchar in invalidchars:
        oristr = oristr.replace(invalidchar, '_')
    return oristr

# 爬取页面中图片列表
def crawlerImgUrls(html):
    tree = etree.HTML(str(html, 'gbk', errors='ignore'))
    img_urls = tree.xpath('//img/@data-src')
    return img_urls

# 爬取页面的 Content
def crawlerContent(url):
    page_response = requests.get(url=url, headers=headers)
    page_response.encoding = 'gbk'
    page_content = page_response.content
    return page_content

# 爬页面中文章链接列表
# def crawlerArticeUrls(html):
#     article_urls = re.findall(r'<h3><a href="(.*)" target="_blank" id="">(?!<.*>).*</a></h3>', html)
#     return article_urls
def crawlerArticeUrls(page):
    ##列表链接+页码
    article_url = dge_prefix_url + str(page)
    ##转为字符串
    content = str(crawlerContent(article_url))
    ##创建正则模式对象,匹配全文链接
    pattern = re.compile(r'<a href="htm_data.{0,30}html" target="_blank" id="">.*?</a>')
    ##取出所有匹配内容
    com_list = pattern.findall(content)
    ##如果是第一页，把公告栏链接切片
    if page == 1:
        com_list = com_list[7:]
    ##链接正则
    pattern_url = re.compile(r'a href="(.*?)"')
    ##取出所有链接后缀
    url_list = pattern_url.findall(str(com_list))
    return url_list

# 爬草榴达盖尔版块的 number 个页面
def crawlers(number):
    print(u'开始抓取数据')

    for page in range(1, number + 1):
        print(u'Page(%d/%d): 开始抓取' %(page, number))
        
        page_article_urls = crawlerArticeUrls(page)
        artice_len = len(page_article_urls)
        
        print(u'Page(%d/%d): 一共找到了 %d 个帖子' % (page, number, artice_len))
        
        idx = 0
        for article_url in page_article_urls:
            idx = idx + 1

            if article_url in dataJson and len(dataJson[article_url]['img_urls']) > 0:
                print(u'Page(%d/%d): 第 %d/%d 个帖子，已经抓取(%d 张图片)' % (page, number, idx, artice_len, len(dataJson[article_url]['img_urls'])))
                continue
            else:
                print(u'Page(%d/%d): 第 %d/%d 个帖子，开始抓取 - %s' % (page, number, idx, artice_len, article_url))
            
            #文章-Content
            article_content = crawlerContent(base_url + article_url)
            #文章-标题
            article_title = re.findall(r'<h4>(.*)</h4>', str(article_content, 'gbk', errors='ignore'))
            if len(article_title) > 0:
                article_title = article_title[0]
            else:
                article_title = 'default'
            #文章-图片列表
            article_img_urls = crawlerImgUrls(article_content)

            dataJson[article_url] = {
                "title": article_title,
                "url": article_url,
                "img_urls": article_img_urls
            }

            print(u'Page(%d/%d): 第 %d/%d 个帖子，抓取成功（%d 张图片）' % (page, number, idx, artice_len, len(article_img_urls)))
        
        print(u'Page(%d/%d): 抓取成功' %(page, number))
    
    print(u'数据抓取成功')

if __name__ == "__main__":
    print('*' * 100)
    print('欢迎使用 1024 达盖尔图片下载小助手')
    print('*' * 100)
    number  = input('请输入需要抓取的页数:\n')

    if os.path.exists(dbJsonFile) == True:
        with open(dbJsonFile, 'r') as f:
            dataJson = json.load(f)

    print(u'已读取 %d 条缓存数据：' %len(dataJson))

    try:
        crawlers(int(number))
    except BaseException as e:
        print(u'抓取失败 - %s' %e)

    with open(dbJsonFile, 'w') as f:
        json.dump(dataJson, f)
    
    print(u'%d 条数据缓存成功' %len(dataJson))
