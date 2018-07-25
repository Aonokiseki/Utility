Set-Executionpolicy -Scope CurrentUser -ExecutionPolicy UnRestricted
$hashtable = @{}
$path = ".\mklink.ini"
$paylaod = Get-Content -Path $path |
Where-Object { $_ -like '*=*' } |
ForEach-Object {
    $infos = $_ -split '='
    $key = $infos[0].Trim()
    $value = $infos[1].Trim()
    $hashtable.$key = $value
}
#源文件路径空或空串,抛出异常
if(([String]::IsNullOrEmpty($hashtable.From))){
    echo "NullPointerException: [From] is null or empty!"
    pause
    return
}
#源文件路径不存在，抛出异常
if(((Test-Path -Path $hashtable.From) -eq $False)){
    echo "IOException: "$hashtable.From" not exist!"
    pause
    return
}
#源文件所在目录如果不是文件夹，抛出异常
if((Get-Item $hashtable.From) -is [IO.fileinfo]){
    echo "IOException: "$hashtable.From" is not directory!"
    pause
    return
}
$GameData = $hashtable.From+"\GameData\"
if((Test-Path $GameData) -eq $False){
    echo "IOException: [GameData] not exist!"
    pause
    return
}
if((Get-Item $GameData) -is [IO.fileinfo]){
    echo "IOException: [GameData] is not a directory!"
    pause
    return
}
#目标目录是否有值，没有值抛出异常
if([String]::IsNullOrEmpty($hashtable.To)){
    echo "NullPointerException: [To] is null or empty!"
    pause
    return
}
#目标目录路径不存在，自动创建
if((Test-Path -Path $hashtable.To) -eq $False){
    echo "IOException: "$hashtable.To" not exist!"
    pause
    return
}
#目标文件所在目录如果不是文件夹，抛出异常
if((Get-Item $hashtable.To) -is [IO.fileinfo]){
    echo "IOException: "$hashtable.To" is not directory!"
    pause
    return
}
if(($hashtable.From -eq $hashtable.To) -or ($hashtable.To+"\GameData" -eq $GameData)){
    echo "[From] == [To], program exit."
    pause
    return
}
#检查源文件夹下是否存在voice等文件
$ArcList = Get-ChildItem $GameData
$voiceList = @()
ForEach($_ in $ArcList){
    if($_.Name.StartsWith("voice")){
        $voiceList+=$_.FullName
        $target=[io.path]::combine($hashtable.To,$_.Name)
        mv $_.FullName $target
        New-Item -Path $_.FullName -ItemType SymbolicLink -Value $target
    }
}
echo "Setting symbolic links completed."
pause