# Linux

## 常用命令

```bash
rm -rf a.txt ##删除
cp b.txt a.txt ##复制文件
mv a.txt b.txt ##修改文件名
nohup ping todev.cn & ## 后台任务
jobs -l ## 查看后台任务, num,pid
kill %num ## 杀掉后台任务，根据编号
ps -aux | grep "****" ## 查看相关进程
kill pid ## 杀掉进程，根据pid
```

### 设置文件权限

```bash
chmod 777 test.txt # 设置 test.txt 文件为所有用户都有读、写、执行权限
-rw------- (600)  只有拥有者有读写权限。
-rw-r--r-- (644)  只有拥有者有读写权限；而属组用户和其他用户只有读权限。
-rwx------ (700)  只有拥有者有读、写、执行权限。
-rwxr-xr-x (755)  拥有者有读、写、执行权限；而属组用户和其他用户只有读、执行权限。
-rwx--x--x (711)  拥有者有读、写、执行权限；而属组用户和其他用户只有执行权限。
-rw-rw-rw- (666)  所有用户都有文件读、写权限。
-rwxrwxrwx (777)  所有用户都有读、写、执行权限。
```

### 环境配置

```bash
# 编辑
vi ~/.bash_profile
# 更新
source ~/.bash_profile
```
