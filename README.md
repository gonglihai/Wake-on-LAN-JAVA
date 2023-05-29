# Wake-on-LAN-JAVA 网络唤醒JAVA实现

- JAVA 版本 1.8

## 配置
MAC_ADDRESS       唤醒机器的mac地址  
BROADCAST_ADDRESS 内网广播地址

## 编译
```shell
javac Wol.java
```

## 运行
```shell
java Wol
```

## crontab 定时任务
```
crontab -e 每周1至周5 上午9:00, 网络唤醒办公电脑
0 9 * * 1,2,3,4,5 /workspase/wol/start.sh
```
