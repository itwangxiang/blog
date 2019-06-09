# ubuntu

## apt

### 常用命令

```bash
apt-cache search package # 搜索包

apt-cache show package # 获取包的相关信息，如说明、大小、版本等

sudo apt-get install package # 安装包

sudo apt-get install package - - reinstall # 重新安装包

sudo apt-get -f install # 修复安装"-f = ——fix-missing"

sudo apt-get remove package # 删除包

sudo apt-get remove package - - purge # 删除包，包括删除配置文件等

sudo apt-get update # 更新源

sudo apt-get upgrade # 更新已安装的包

sudo apt-get dist-upgrade # 升级系统

sudo apt-get dselect-upgrade # 使用 dselect 升级

apt-cache depends package # 了解使用依赖

apt-cache rdepends package # 是查看该包被哪些包依赖

sudo apt-get build-dep package # 安装相关的编译环境

apt-get source package # 下载该包的源代码

sudo apt-get clean && sudo apt-get autoclean # 清理无用的包

sudo apt-get check # 检查是否有损坏的依赖
```

### 常见问题

#### 修改密码

```bash
sudo passwd root
```

#### 中文乱码

```bash
locale # 查看是否中文
locale-gen zh_CN.GBK # 如果不是，添加中文字符集
vim /etc/environment # 配置环境
LANGUAGE=”zh_CN:zh:en_US:en”
LANG=zh_CN.GBK
source /etc/environment # 生效
```
