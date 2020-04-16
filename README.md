# GlobalWeather
-----2020.04.13

1.把Firebase SDK 加入到 android 项目中
dependencies{
    implementation 'com.google.firebase:firebase-analytics:17.2.2'
}
项目中添加 google-services.json

2.下载UI KIT 参照
https://www.behance.net/collection/176176757/mobile-app-ui

-----2020.04.15

3.添加AdMob广告
注册AdMob账号
参考AdMob示例 https://github.com/xixiaohui/googleads-mobile-android-examples.git

dependencies{
    implementation 'com.google.android.gms:play-services-ads:19.0.0'
}


4.熟悉快捷键
ctrl + n    查找类文件
ctrl + shift + n    查找文件
ctrl + alt + left/right 操作记录
alt + shift + up/down   移动行
alt + F7    查找方法调用处
ctrl + alt + B  方法的跟进
ctrl + P    显示方法的参数
ctrl + D    快速复制行
ctrl + X    快速删除行
alt + shift + 鼠标点击  多行操作
ctrl + shift + enter    快速补全完成
ctrl + b    第一次: 跳转到变量的声明处
ctrl + shift + b    第二次: 跳转到变量类型的定义处
ctrl + 加号\减号    代码折叠
ctrl + shift + I    预览方法定义
ctrl + shift + v    粘贴板管理
ctrl + F12  查看大纲
F11书签
shift + F11 打开书签


ctrl + alt + shift + T  Extract

ctrl + J    代码模块
control + alt + H    方法调用栈


5.AdMod广告类型
Banner
https://developers.google.com/admob/android/quick-start?hl=en-US#banner

Interstitial
https://developers.google.com/admob/android/quick-start?hl=en-US#interstitial

Native
https://developers.google.com/admob/android/quick-start?hl=en-US#native

Rewarded
https://developers.google.com/admob/android/quick-start?hl=en-US#rewarded
