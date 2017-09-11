import requests

# 连接程序
r = requests.get("https://www.95516.com/static/union/pages/index/index.html")

## 查看python请求发给服务器的UA信息
## 输出内容如下：{'User-Agent': 'python-requests/2.18.4', 'Accept-Encoding': 'gzip, deflate', 'Accept': '*/*', 'Connection': 'keep-alive'}
print(r.request.headers)

## 重新定义UA的信息
kv = {'User-Agent': 'Mozilla/5.0'}
## 将UA添加到访问信息中
url = 'https://item.jd.com/3857525.html'
r = requests.get(url, headers=kv)
## 再次输出UA信息，结果为：{'User-Agent': 'Mozilla/5.0', 'Accept-Encoding': 'gzip, deflate', 'Accept': '*/*', 'Connection': 'keep-alive'}
print(r.request.headers)

## 输出获取内容
# print(r.text)

print(r.text)