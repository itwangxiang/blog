# 设置Java SDK 环境变量 > powershell -ExecutionPolicy Unrestricted -File

$softwares = Get-ItemProperty HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall\* 
$jdk = $softwares  | Where-Object DisplayName -Match 'Java SE Development Kit'

if ($jdk.Count -gt 1) {
    Write-Host "find more java:"
    $id = 1
    foreach ($e in $jdk) {
        Write-Host "${id}." $e.DisplayName
        $id++
    }
    $choice = [int](Read-Host -Prompt "do use who ?")
    $jdk = $jdk[$choice]
}

$install_location = $jdk.InstallLocation.trim('\')
# 去掉路径最后的斜杠
Write-Host "find install path: ${install_location}"

# 配置环境变量
$java_home = $install_location
$classpath = ".;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar"
$path = $java_home + "\bin;" + $java_home + "\jre\bin;" + $env:Path

[environment]::SetEnvironmentvariable("JAVA_HOME", $java_home, "Machine")
[environment]::SetEnvironmentVariable("CLASSPATH", $classpath, "Machine")
[environment]::SetEnvironmentVariable("PATH", $path , "Machine")

Write-Host "set JDK env variables success..."