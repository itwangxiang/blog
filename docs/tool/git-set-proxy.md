# 设置 Git 代理

## 设置

```bash
git config --global http.proxy http://127.0.0.1:1087
git config --global https.proxy https://127.0.0.1:1087

# 只对 github.com
git config --global http.https://github.com.proxy http://127.0.0.1:1087
git config --global https.https://github.com.proxy https://127.0.0.1:1087
```

## 取消

```bash
git config --global --unset http.proxy
git config --global --unset https.proxy

# github.com
git config --global --unset http.https://github.com.proxy
git config --global --unset https.https://github.com.proxy
```
