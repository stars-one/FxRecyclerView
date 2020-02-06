# FxRecyclerView

基于Scroll Pane控件，仿造Android中的RecyclerView，实现的一款tornadofx的控件
[github](https://github.com/Stars-One/FxRecyclerView)
## 需求
由于我的之前做的几个项目都是那种类似下载列表的功能，[蓝奏云批量下载](https://www.cnblogs.com/stars-one/p/12215684.html)和[m3u8下载合并器](https://www.cnblogs.com/stars-one/p/12198518.html)

之所以抛弃了javafx的原因，是因为javafx中的动态添加控件比较麻烦，于是便是转到了tornadofx这个框架来。

tornadofx中动态添加控件的步骤虽然比javafx中要简单，但是，我还是觉得有些麻烦

于是我就参考了Android中的RecyclerView使用思路，打造出这个名为FxRecyclerView的控件，可以更加方便的动态进行控件的增删查改

## 功能介绍
- 动态添加ItemView
- 动态删除ItemView
- 动态更新itemView
- 快捷绑定单击/右击事件

## 功能演示
上波gif动态图就能很好说明了
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

[测试的jar包](https://github.com/Stars-One/FxRecyclerView/blob/master/out/artifacts/FxRecyclerView_jar/FxRecyclerView.jar) 
## 使用
### 1.复制FxRecyclerView源码
下载我下面给出的kt文件，复制到你的tornadofx项目中
[FxRecyclerView.kt](https://github.com/Stars-One/FxRecyclerView/blob/master/src/main/kotlin/com/starsone/fxrecyclerview/view/FxRecyclerView.kt)
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

|方法名							|参数说明	|方法说明				|
|--								|--			|--						|
|setWidth(double)				|double类型的数值			|设置宽度				|
|setHegiht(double)				|double类型的数值			|设置高度				|
|setIsShowHorizontalBar(String)	|显示方式，never(不显示） always（一直显示） asneed（自动根据需要显示）			|设置是否显示水平滚动条	|
|addList(arraylist)				|ArrayList类型的一组数据			|添加一组数据，同时更新视图			|
|addList(list)					|List类型的一组数据			|添加一组数据，同时更新视图			|
|add(beanT)						|			|添加一个数据，同时更新视图			|
|add(bean,int)					|			|在列表的指定位置插入指定bean数据对应的itemView。 将当前位于该位置的itemView（如果有）和任何后续的itemView（向其索引添加一个）移动。			|
|update(bean,int)				|			|更新指定位置的数据及itemView视图						|
|update(bean,oldBean)			|			|更新列表中存在的数据，替换为新的数据，同时更新视图						|
|remove(bean)					|			|移出某个数据，同时更新视图						|
|remove(index)					|			|移出列表中指定位置的数据，同时更新视图						|
|removeAll()					|			|移出列表所有数据，同时更新视图						|

## FxRecyclerView源码
由于kotlin文件可以写多个类，我的类都写在了一个文件里
```
package com.starsone.fxrecyclerview.view

import javafx.scene.control.ScrollPane
import javafx.scene.input.MouseButton
import javafx.scene.layout.VBox
import tornadofx.*

/**
 *
 * @author StarsOne
 * @date Create in  2020/1/20 0020 21:19
 * @description
 *
 */
class FxRecyclerView<beanT : Any, itemViewT : View> : View {
    var adapter: RVAdapter<beanT, itemViewT>? = null
        set(value) {
            field = value
            val adapter = value as RVAdapter<beanT, itemViewT>
            val beanList = adapter.beanList
            val itemViewList = adapter.itemViewList
            for (index in 0 until beanList.size) {
                val itemView = adapter.onCreateView()
                //绑定bean数据到itemView
                adapter.onBindData(itemView, beanList[index], index)
                //itemView添加到列表中
                itemViewList.add(itemView)
                //添加到RecyclerView的主容器中
                container.add(itemView)
                itemView.root.setOnMouseClicked {
                    if (it.button == MouseButton.PRIMARY) {
                        //单击事件回调
                        adapter.onClick(itemView, index)
                    }
                    if (it.button == MouseButton.SECONDARY) {
                        //右击事件回调
                        adapter.onRightClick(itemView, index)
                    }
                }
            }
        }

    var container = vbox { }

    constructor() {
        root.add(container)
    }

    constructor(vBox: VBox) {
        container = vBox
        root.add(container)
    }

    override val root = scrollpane {
        vbox { }
    }

    /**
     * 设置宽度
     */
    fun setWidth(width: Double) {
        root.prefWidth = width
    }

    /**
     * 设置[height]
     */
    fun setHeight(height: Double) {
        root.prefHeight = height
    }

    /**
     * 设置水平滚动条的显示方式
     * @param way 显示方式，never(不显示） always（一直显示） asneed（自动根据需要显示）
     */
    fun setIsShowVerticalBar(way: String) {
        when (way) {
            "never" -> root.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            "always" -> root.hbarPolicy = ScrollPane.ScrollBarPolicy.ALWAYS
            "asneed" -> root.hbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        }
    }

    /**
     * 添加一个列表的数据(arraylist)
     */
    fun addList(beanList: ArrayList<beanT>) {
        for (bean in beanList) {
            add(bean)
        }
    }

    /**
     * 添加一个列表的数据(list)
     */
    fun addList(beanList: List<beanT>) {
        for (bean in beanList) {
            add(bean)
        }
    }
    fun add(bean: beanT) {
        val beanList = adapter?.beanList
        val itemViewList = adapter?.itemViewList
        val index = beanList?.size as Int - 1
        beanList.add(bean)
        val itemView = adapter?.onCreateView() as itemViewT
        //invoke onBindData method to bind the bean data to te item view
        adapter?.onBindData(itemView, bean, index)
        //add the item view in the item view list
        itemViewList?.add(itemView)
        //add to the recyclerview container
        container.add(itemView)
        itemView.root.setOnMouseClicked {
            if (it.button == MouseButton.PRIMARY) {
                //单击
                adapter?.onClick(itemView, index)
            }
            if (it.button == MouseButton.SECONDARY) {
                //右击
                adapter?.onRightClick(itemView, index)
            }

        }
    }

    /**
     * 在列表的指定位置插入指定bean数据对应的itemView。 将当前位于该位置的itemView（如果有）和任何后续的itemView（向其索引添加一个）移动。
     * @param bean bean数据
     * @param index 要插入的下标
     */
    fun add(bean: beanT, index: Int) {
        val beanList = adapter?.beanList
        val itemViewList = adapter?.itemViewList
        beanList?.add(index, bean)
        val itemView = adapter?.onCreateView() as itemViewT
        //invoke onBindData method to bind the bean data to te item view
        adapter?.onBindData(itemView, bean, index)
        //add the item view in the item view list
        itemViewList?.add(index, itemView)
        //add to the recyclerview container
        container.addChildIfPossible(itemView.root, index)
        itemView.root.setOnMouseClicked {
            if (it.button == MouseButton.PRIMARY) {
                //单击
                adapter?.onClick(itemView, index)
            }
            if (it.button == MouseButton.SECONDARY) {
                //右击
                adapter?.onRightClick(itemView, index)
            }
        }
        //更新点击事件的回调
        for (i in index + 1 until itemViewList?.size as Int) {
            val itemView1 = itemViewList[i]
            adapter?.onBindData(itemView1, beanList!![i], i)
            itemView1.root.setOnMouseClicked {
                if (it.button == MouseButton.PRIMARY) {
                    //单击
                    adapter?.onClick(itemView1, i)
                }
                if (it.button == MouseButton.SECONDARY) {
                    //右击
                    adapter?.onRightClick(itemView1, i)
                }
            }
        }
    }

    /**
     * 更新指定位置的itemView
     */
    fun update(bean: beanT, index: Int) {
        remove(index)
        add(bean, index)
    }

    /**
     * 寻找列表中与oldBean相同的第一个元素，将其内容进行修改，同时更新界面的显示
     * @param bean 新的数据
     * @param oldBean 列表中已存在的数据
     */
    fun update(bean: beanT, oldBean: beanT) {
        val beanList = adapter?.beanList
        val index = beanList?.indexOf(oldBean) as Int
        if (index != -1) {
            update(bean, index)
        } else {
            println("列表中不存在该元素")
        }
    }

    fun remove(bean: beanT) {
        val beanList = adapter?.beanList
        val index = beanList?.indexOf(bean) as Int
        remove(index)
    }

    /**
     * 移出指定下标的itemview
     * @param index 下标
     */
    fun remove(index: Int) {
        val beanList = adapter?.beanList
        val itemViewList = adapter?.itemViewList
        beanList?.removeAt(index)
        val itemView = itemViewList!![index]
        itemView.removeFromParent()
        itemViewList.remove(itemView)
        for (i in index until beanList?.size as Int) {
            adapter?.onBindData(itemViewList[i], beanList[i], i)
            val itemView = itemViewList[i]
            itemView.root.setOnMouseClicked {
                if (it.button == MouseButton.PRIMARY) {
                    //单击
                    adapter?.onClick(itemView, i)
                }
                if (it.button == MouseButton.SECONDARY) {
                    //右击
                    adapter?.onRightClick(itemView, i)
                }
            }
        }
    }

    /**
     * 移出所有控件
     */
    fun removeAll() {
        val itemViewList = adapter?.itemViewList as ArrayList<itemViewT>
        val beanList = adapter?.beanList as ArrayList<beanT>
        for (itemView in itemViewList) {
            itemView.removeFromParent()
        }
        itemViewList.removeAll(itemViewList)
        beanList.removeAll(beanList)
    }
}

/**
 * 适配器
 * @author StarsOne
 * @date Create in  2020/1/20 0020 21:51
 * @description
 *
 */
abstract class RVAdapter<beanT : Any, itemViewT : View> {
    val beanList = arrayListOf<beanT>()
    val itemViewList = arrayListOf<itemViewT>()

    constructor(bean: beanT) {
        beanList.add(bean)
    }

    constructor(beanList: List<beanT>) {
        this.beanList.addAll(beanList)
    }

    constructor(beanList: ArrayList<beanT>) {
        this.beanList.addAll(beanList)
    }

    /**
     * 设置返回ItemView
     */
    abstract fun onCreateView(): itemViewT

    abstract fun onBindData(itemView: itemViewT, bean: beanT, position: Int)

    abstract fun onClick(itemView: itemViewT, position: Int)//单击

//    abstract fun onDoubleClick(itemView: itemViewT, position: Int)//双击

    abstract fun onRightClick(itemView: itemViewT, position: Int)//右击
}


```