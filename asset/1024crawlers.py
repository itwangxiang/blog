import requests
import re
import os
import json
from lxml import etree
from multiprocessing.pool import ThreadPool

# 草榴 URL
base_url = 'https://t66y.com/'
# 达盖尔版块的前缀 URL
dge_prefix_url = 'http://t66y.com/thread0806.php?fid=16&page='

# 缓存文件
# data.json
# {
#     'htm_data/2001/16/3742892.html': {
#         'title': '',
#         'imgs': []
#     }
# }
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

# 读取数据


def readJson():
    global dataJson
    if os.path.exists(dbJsonFile) == True:
        with open(dbJsonFile, 'r') as f:
            dataJson = json.load(f)
    print('已读取 %d 条缓存数据：' %len(dataJson))

def writeJson():
    with open(dbJsonFile, 'w') as f:
        json.dump(dataJson, f)
    print('%d 条数据缓存成功' %len(dataJson))

# 用作目录和文件名称时不合法的字符
invalidchars = ['?', '\\', '/', ':', '*', '<', '>', '|', '\"']
def process_str(oristr):
    for invalidchar in invalidchars:
        oristr = oristr.replace(invalidchar, '_')
    return oristr

# 爬取页面的 Content
def crawlerContent(url):
    page_response = requests.get(url=url, headers=headers)
    page_response.encoding = 'gbk'
    page_content = page_response.content
    return page_content

# 爬页面中所有帖子的链接
def crawlerArticleUrls(page):
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

# 爬帖子中的所有图片的链接
def crawlerImgUrls(article):
    article_url = article['url']

    if article_url in dataJson:
        jsonArticle = dataJson[article_url]
        article['title'] = jsonArticle['title']
        article['imgs'] = jsonArticle['imgs']
        article['status'] = 'exists'
        return article

    #文章-Content
    article_content = crawlerContent(base_url + article['url'])
    #文章-标题
    article_title = re.findall(r'<h4>(.*)</h4>', str(article_content, 'gbk', errors='ignore'))
    if len(article_title) > 0:
        article_title = article_title[0]
    else:
        article_title = 'default'
    #文章-图片
    tree = etree.HTML(str(article_content, 'gbk', errors='ignore'))
    article_imgs = tree.xpath('//img/@data-src')

    article['title'] = article_title
    article['imgs'] = article_imgs
    article['status'] = 'add'
    return article


# 爬草榴达盖尔版块的第 number 个页面
def crawlerPages(page):
    print('Page%d - 开始抓取帖子' %(page))

    article_urls = crawlerArticleUrls(page)
    article_len = len(article_urls)
    print('Page%d - 一共找到了 %d 个帖子' % (page, article_len))

    articles = []
    idx = 0
    for article_url in article_urls:
        idx = idx + 1
        articles.append({
            'page': page,
            'url': article_url,
            'position': str(idx) + '/' + str(article_len)
        })
    
    return articles

if __name__ == "__main__":
    print('*' * 100)
    print('欢迎使用 1024 达盖尔图片下载小助手')
    print('*' * 100)
    number  = input('请输入需要抓取的页数:\n')
    number = int(number)

    readJson()

    try:
        articles = []

        page_results = ThreadPool(7).imap_unordered(crawlerPages, range(1, number))
        for page_articles in page_results:
            articles = articles + page_articles
        
        print('/n')

        results = ThreadPool(7).imap_unordered(crawlerImgUrls, articles)
        for article in results:
            status = article['status']
            page = article['page']
            position = article['position']
            article_url = article['url']
            article_title = article['title']
            article_imgs = article['imgs']

            if status == 'add':
                print('Page(%d/%d) - 帖子(%s) - %s 抓取 %d 张图片成功' %(page, number, position, article_title, len(article_imgs)))
            elif status == 'exists':
                print('Page(%d/%d) - 帖子(%s) - %s 已缓存 %d 张图片' %(page, number, position, article_title, len(article_imgs)))
            else:
                print('未知错误')
            
            # 保持缓存数据中
            dataJson[article_url] = article

    except BaseException as e:
        print(u'抓取失败 - %s' %e)

    writeJson()

    input('抓取完成:\n')
