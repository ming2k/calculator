# 计算机

## Basic

- kotlin: 写逻辑
- xml: 写样式

about xml:
这个项目中你需要了解layout布局
```xml
<brahbrahLayout xmlns:android="http://schemas.android.com/apk/res/android">
</brahbrahLayout>
```
还有shape
```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
</shape>
```
以及vector
```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android">
</vector>
```

## Feature

- 多项式四则运算
- 撤销

## Usage

请按照`数字 运算符 数字 运算符 数字 运算符 ... 数字`的结构键入，在键入`等于`

当然你也可以不按照这样输入，在键入`等于`之后提示错误

## 算法实现思想

我通过`mutableListOf`创建`list`来存放所有`数字`和`运算符`，同时负责显示到`TextView`，每一次点击按钮都将添加相应的一个元素到`list`中，撤销一个最近的一个操作即删除`list`最后一个元素。
这些逻辑都不难，比较难的逻辑是等于操作，首先在执行等于之前我要判断等于号是否符合`数字 运算符 数字 运算符 数字 运算符 ... 数字`格式，通过求余和类型比较来判断，不符合的执行相应操作后使用`return`结束，符合的将继续执行。四则运算有优先级，所以我将二级运算转换为一级运算，即将所有的乘除先算完，再算余下部分。如何先计算乘除？首先要定位到奇数位置的乘除乘操作符，然后使前后运算，这样三个元素就会得到一个结果，将这个结果放置最前，然后删除余下两个数，这时候后面的元素下标也会减小2。当都这样处理之后，就只剩下加法运算，按照从前到后顺序运算即可。

上述逻辑还是比较复杂的，希望有耐心尽量看完，看代码具体实现。

同时，我遇到这个bug，当遍历list过程中更新list会出现`ConcurrentModificationException`错误。
```kotlin
for (str in list) {
    if (someCondition) {
        myArrayList.removeAt(str);
    }
}
```

