import requests
from contextlib import closing
import re
import os
import json
from multiprocessing.pool import ThreadPool

# 缓存文件
dbJsonFile = 'data.json'
dataJson = {}

# 读取数据


def readJson():
    global dataJson
    if os.path.exists(dbJsonFile) == True:
        with open(dbJsonFile, 'r') as f:
            dataJson = json.load(f)

# 用作目录和文件名称时不合法的字符
invalidchars = ['?', '\\', '/', ':', '*', '<', '>', '|', '\"']

def process_str(oristr):
    for invalidchar in invalidchars:
        oristr = oristr.replace(invalidchar, '_')
    return oristr

# 创建目录
def mkdir(path):
    if os.path.exists(path) == False:
        try:
            os.mkdir(path)
        except Exception:
            print(u'无法创建目录 %s' % path)

# 下载
def download(task):
    url = task['url']
    path = task['path']
    path = process_str(path)

    mkdir(path)

    filename = os.path.basename(url)
    filepath = path + '/' + filename

    if os.path.exists(filepath) == True:
        print('文件已经存在 - %s' %filepath)
        return ''

    try:
        with closing(requests.get(url, stream=True)) as response:
            chunk_size = 1024
            content_size = int(response.headers['content-length'])
            if response.status_code == 200:
                print('开始下载文件 %s 大小(%0.2f KB)' % (url, (content_size / chunk_size)))
                with open(filepath, "wb") as file:
                    for data in response.iter_content(chunk_size=chunk_size):
                        file.write(data)
            else:
                print('链接异常')
    except:
        print('文件 %s 下载失败' %filepath)
        return ''

    return filepath


if __name__ == "__main__":
    readJson()
    print(u'已读取缓存帖子 %d 条' % len(dataJson))

    tasks = []
    for article_key in dataJson:
        article = dataJson[article_key]
        article_title = article['title']
        print(u'帖子 - %s - %d 张' % (article_title, len(article['img_urls'])))

        for img_url in article['img_urls']:
            task = {
                'url': img_url,
                'path': article_title
            }
            tasks.append(task)

    results = ThreadPool(13).imap_unordered(download, tasks)
    for r in results:
        if r != '':
            print('文件 %s 下载成功' % r)
    
    pass

