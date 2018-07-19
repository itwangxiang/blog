if [ ${PWD##*/} != "docs" ];
  then
  echo "please run at docs."
  exit 2
fi
echo "update git code start..."
git pull origin master
echo "update git code done."
rm -rf ../../nginx/webroot/asset
cp -R asset/* ../../nginx/webroot/asset
