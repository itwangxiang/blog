cd ../
if [ ! -d "gitcode" ];
  then
  mkdir gitcode
fi
cd gitcode

if [ ! -d "todev.web" ];
  then
  echo "checkout git code start..."
  mkdir todev.web
  cd todev.web
  git init
  git remote add origin git@github.com:itwangxiang/todev.web.git
  git pull origin master
  echo "checkout git code done."
else
  echo "update git code start..."
  cd todev.web
  git pull origin master
  echo "update git code done."
fi

if [ ! -d "/mnt/www.todev.cn/wwwroot" ];
  then
  mkdir -p /mnt/www.todev.cn/wwwroot
fi

rm -rf src/Config.js docs
cp -R * /mnt/www.todev.cn/wwwroot
echo "deploy code done"

cd /mnt/www.todev.cn/wwwroot
npm install
echo "npm install done"
/mnt/www.todev.cn/wwwroot/restart.sh
echo "restart todev.web done"