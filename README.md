# Takeaway_android

## 简介

该项目是我的Android课设，主要用于学习实践Android开发的流程，界面仿美团外卖。
**部分界面和功能因为时间精力关系而被简化甚至去除**

为了缩短开发周期，直接基于Android 5.x开发，不对5.0以下版本进行适配。

图片资源从[美团外卖APP](http://waimai.meituan.com/mobile/download/default)中解压，用于学习测试，如造成侵权请联系我删除.

![预览](https://github.com/0xLLLLH/Takeaway_android/blob/50bacf7cb571c044136396c11ef6067ced2d6521/preview.gif?raw=true)

## 还需要填的坑

* Activity未统一管理
* 代码风格需要调整，多划分函数使结构清晰
* 目前直接使用JSONObject而不是封装对应的类，不便于开发
* UI需调整美化
* 需要将Utils.getValueFromJSONObject()删去，直接使用JSONObject的opt系列方法（可选）

## 项目中使用的开源项目

* [LoadMoreRecyclerView](https://github.com/alicx/LoadMoreRecyclerView)
* [StickyListHeaders](https://github.com/emilsjolander/StickyListHeaders)