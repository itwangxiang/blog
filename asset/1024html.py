import os
import codecs
from multiprocessing.pool import ThreadPool

html1 = '''
<!DOCTYPE html>
<html>
<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<title>'''

html2 = '''</title>
</head>
<style>
img { height: auto; width: auto; width:100%; }
</style>
<body>'''

html3 = '''
</body>
</html>
'''

def findImgs(path):
    files = os.listdir(path)
    return files

def writeHtml(file, content):
    with codecs.open(file, 'w', "utf-8") as f:
        f.write(content)
        f.close

if __name__ == "__main__":
    curPath = os.getcwd()
    path = os.listdir(curPath)

    htmls = []

    for p in path:
        if os.path.isdir(p):
            html = html1 + p + html2

            imgs = findImgs(curPath + '\\' + p)
            imgHtml = ''
            for img in imgs:
                imgHtml = imgHtml + ' \n <img src="'+ img + '"/>  \n'

            html = html + imgHtml + html3

            createHtmlFile = curPath + '\\' + p + '\\index.html'
            # print(createHtmlFile)
            writeHtml(createHtmlFile, html)
