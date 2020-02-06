# FxRecyclerView
[中文文档](https://github.com/Stars-One/FxRecyclerView/blob/master/readme_zh.md)

[Github](https://github.com/Stars-One/FxRecyclerView)

a custom view for tornadofx to accomplish list view of many tasks,is based on scroll pane

I copy the idea of RecyclerView which is in Android,so call it FxRecyclerView
## Why need it?

I have used tornadofx some times.In the time of using tornadofx,I found that it can dymanically add a view,that is the reason why I give up the javafx.

Although tornadofx provides option that dymanically add a view,I think it is also fussy.

So I copy the idea and usage of RecyclerView(a view in android) to create this view.

I think the usage of this view is easily than native way in tornadofx.

## What can it do?
Here,some gif picture can show you what can it do.
### 1.add a group of data
![](https://img2018.cnblogs.com/blog/1210268/202002/1210268-20200206161808594-821208198.gif)
### 2.add a data
![](https://img2018.cnblogs.com/blog/1210268/202002/1210268-20200206161825000-1182546987.gif)
### 3.insert data in assigned index
![](https://img2018.cnblogs.com/blog/1210268/202002/1210268-20200206161858477-734686857.gif)
### 4.update assigned index data
![](https://img2018.cnblogs.com/blog/1210268/202002/1210268-20200206161837422-1892258410.gif)
### 5.onclick/right onclick event bind quickly
![](https://img2018.cnblogs.com/blog/1210268/202002/1210268-20200206161915734-777138313.gif)
### 6.remove assigned index data/remove all data
![](https://img2018.cnblogs.com/blog/1210268/202002/1210268-20200206161928105-166984812.gif)
 
[download demo(a jar file)](https://github.com/Stars-One/FxRecyclerView/blob/master/out/artifacts/FxRecyclerView_jar/FxRecyclerView.jar)

## How to use?
### 1.download the file
download the file name `FxRecyclerView` and put it in your tornadofx project.
[FxRecyclerView.kt](https://github.com/Stars-One/FxRecyclerView/blob/master/src/main/kotlin/com/starsone/fxrecyclerview/view/FxRecyclerView.kt)
### 2.create your data class
in the demo,I use a simple data class
```
data class Person(var name: String,var age:String)
```
### 3.create your view of item

the item in the demo is simple,just show the name and age of the Person

**Note:your view must extends the View which is belong to tornadofx**

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
        deleteBtn = button("delete")

    }
}
```
### 4.add the FxRecyclerView in your MainView or other view
**Note:you must use the generics when you new a instance of FxRecyclerView**
```
package com.example.demo.view

import tornadofx.*

class MainView : View("Hello TornadoFX") {
	//must use the generics
	//first is the data generics,second is the itemView generics
    val rv = FxRecyclerView<Person,ItemView>()
    override val root = vbox {
        //other...
		this+=rv
    }
}
```
### 5.create RvAdapter and implement method of it
RvAdapter is an abstract class,so need to be extended and implement its method.
```
 //1.init data source
val dataList = arrayListOf<Person>()
for (i in 0..10) {
	dataList.add(Person("person$i",(18+i).toString()))
}
//2.init adapter
val adapter = object :RVAdapter<Person,ItemView>(dataList){

	//3.override some necessary methods
	override fun onRightClick(itemView: ItemView, position: Int) {
		println("right onclick $position")
	}

	//onclick event
	override fun onClick(itemView: ItemView, position: Int) {
		println("onclick $position")
	}

	override fun onCreateView(): ItemView {
		return ItemView()
	}

	//bind data to change the itemview gui
	override fun onBindData(itemView: ItemView, bean: Person, position: Int) {
		itemView.nameTv.text = bean.name
		itemView.ageTv.text  = bean.age
		itemView.deleteBtn.setOnAction {
			rv.remove(position)
		}
	}
}
//4.set adapter
rv.adapter = adapter
```
## Other Complement

**Note：Following methods is in the FxRecyclerView object.**

|method name							|parma description																		|method description																|
|--								|--																						|--																				|
|setWidth(double)				|double类型的数值																		|设置宽度																		|
|setHegiht(double)				|double of data																			|设置高度																		|
|setIsShowHorizontalBar(String)	|way，has 3 choose,never(never show） always（always show） asneed（show when it need）	|set the horizontal bar show													|
|addList(arraylist)				|ArrayList of data																		|add a group of data,and refresh the item view									|
|addList(list)					|List of data																			|add a group of data,and refresh the item view									|
|add(beanT)						|																						|																				|
|add(bean,int)					|																						|																				|
|update(bean,int)				|																						|update the data of the assigned index and refresh item view					|
|update(bean,oldBean)			|																						|update the old data which is exist in the bean list and refresh the item view	|
|remove(bean)					|																						|																				|
|remove(index)					|																						|																				|
|removeAll()					|																						|																				|
