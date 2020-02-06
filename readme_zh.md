# FxRecyclerView

基于Scroll Pane控件，仿造Android中的RecyclerView，实现的一款tornadofx的控件
[github](https://github.com/Stars-One/FxRecyclerView)
## 需求
由于我的之前做的几个项目都是那种类似下载列表的功能，[蓝奏云批量下载](https://www.cnblogs.com/stars-one/p/12215684.html)和[m3u8下载合并器](https://www.cnblogs.com/stars-one/p/12198518.html)

之所以抛弃了javafx的原因，是因为javafx中的动态添加控件比较麻烦，于是便是转到了tornadofx这个框架来。

tornadofx中动态添加控件的步骤虽然比javafx中要简单，但是，我还是觉得有些麻烦

于是我就参考了Android中的RecyclerView使用思路，打造出这个名为FxRecyclerView的控件，可以更加方便的动态进行控件的增删查改

[测试的jar包](https://github.com/Stars-One/FxRecyclerView/blob/master/out/artifacts/FxRecyclerView_jar/FxRecyclerView.jar)
## 功能介绍
- 动态添加ItemView
- 动态删除ItemView
- 动态更新itemView
- 快捷绑定单击/右击事件

## 功能演示

## 使用
### 1.复制FxRecyclerView源码
[FxRecyclerView源码下载](https://github.com/Stars-One/FxRecyclerView/blob/master/src/main/kotlin/com/starsone/fxrecyclerview/view/FxRecyclerView.kt)

### 2.创建bean类
这个没啥好说的，就是一个存数据的bean类，如一个`Person`类，根据自己的情况与需求创建
```
data class Person(var name: String,var age:String)
```
### 3.创建ItemView
这个就是列表中的每一项View，**需要继承tornadofx中的View**，我这里就是显示Person的name和age属性，比较简单演示一下

**为了简单起见，我的ItemView起名为ItemView，各位使用的过程中可以自由更改名字**
```
import javafx.scene.control.Button
import javafx.scene.text.Text
import tornadofx.*

/**
 *
 * @author StarsOne
 * @date Create in  2020/1/21 0021 18:36
 * @description
 *
 */
class ItemView : View("My View") {
    var nameTv by singleAssign<Text>()
    var ageTv by singleAssign<Text>()
    var deleteBtn by singleAssign<Button>()
    override val root = hbox {
        spacing = 20.0
        nameTv = text()
        ageTv = text()
        deleteBtn = button("删除")

    }
}
data class Person(var name: String,var age:String)

```
### 4.界面添加FxRecyclerView
```
package com.example.demo.view

import tornadofx.*

class MainView : View("Hello TornadoFX") {
	//创建FxRecyclerView需要使用到泛型，第一个是bean类，第二个是ItemView
    val rv = FxRecyclerView<Person,ItemView>()
    override val root = vbox {
        //省略...
		this+=rv
    }
}
```
### 4.创建RvAdapter
RvAdapter是抽象类，所以得通过继承并实现其中的几个方法
```
//创建数据
val dataList = arrayListOf<Person>()
for (i in 0..10) {
	dataList.add(Person("张三$i",(18+i).toString()))
}
//重写RVAdapter的方法
val adapter = object :RVAdapter<Person,ItemView>(dataList){

	override fun onRightClick(itemView: ItemView, position: Int) {
		//右击事件
		println("右击$position")
	}

	override fun onClick(itemView: ItemView, position: Int) {
		//单击事件
		println("单击$position")
	}

	override fun onCreateView(): ItemView {
		//必须实现
		//返回ItemVIew的实例
		return ItemView()
	}

	override fun onBindData(itemView: ItemView, bean: Person, position: Int) {
		//必须实现
		//将bean类中的数据绑定到itemView中
		itemView.nameTv.text = bean.name
		itemView.ageTv.text  = bean.age
		itemView.deleteBtn.setOnAction {
			rv.remove(position)
		}
	}
}
//设置FxRecyclerView的adapter
rv.adapter = adapter
```
## 使用补充
PS：以下的方法都是rv调用（FxRecyclerView对象）

|方法名							|参数说明	|方法说明	|
|--								|--			|--			|
|setWidth(double)				|			|			|
|setHegiht(double)				|			|			|
|setisShowVerticalbar(String)	|			|			|
|addList(arraylist)				|			|			|
|addList(list)					|			|			|
|add(beanT)						|			|			|
|add(bean,position)				|			|			|
|update(bean,int)				|			|			|
|update(bean,oldBean)			|			|			|
|remove(bean)					|			|			|
|remove(index)					|			|			|
|removeAll()					|			|			|

### 添加新数据
rv.add(bean)
rv.add(bean,)
### 删除数据
rv.remove(position) 移出指定position的itemView
rv.removeAll() 移出所有itemView
### 更新数据
 
## FxRecyclerView源码
由于kotlin文件可以写多个类，我的类都写在了一个文件里
```

```