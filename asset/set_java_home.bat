@ECHO OFF

:START
set /p home=请输入JDK安装路径：
IF EXIST "%home%\bin\java.exe" GOTO INSTALL

:WARNING
rem 输入目录错误，提示重新输入
echo 您所输入的路径不是JDK安装路径
echo 请重新输入正确的JDK安装路径
pause
goto START

:INSTALL
rem 如输入正确的 Java2SDK 安装目录，开始设置环境变量
echo 输入的路径是:%home%
@setx JAVA_HOME "%home%"
@setx Path "%%JAVA_HOME%%\bin;%Path%"
IF "%CLASSPATH%"=="" goto NOCLASSPATH
@setx CLASSPATH "%CLASSPATH%;.;%%JAVA_HOME%%\lib\tools.jar;%%JAVA_HOME%%\lib\dt.jar;%%JAVA_HOME%%\jre\lib\rt.jar"
GOTO END

:NOCLASSPATH
@setx CLASSPATH ".;%%JAVA_HOME%%\lib\tools.jar;%%JAVA_HOME%%\lib\dt.jar;%%JAVA_HOME%%\jre\lib\rt.jar"
GOTO END

:END
echo Java环境设置完毕
pause