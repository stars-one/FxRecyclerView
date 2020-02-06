package com.starsone.fxrecyclerview.view

import tornadofx.*

/**
 * 中文演示的主界面
 */
class MainViewZH : View("Hello TornadoFX") {
    val rv = FxRecyclerView<Person,ItemView>()
    override val root = vbox {
        setPrefSize(800.0,400.0)
        hbox{

            button("初始化并添加一组数据") {
                action {

                }
            }
            button("添加一组数据") {
                action{
                    val dataList = arrayListOf<Person>()
                    for (i in 0..10) {
                        dataList.add(Person("张三$i",(18+i).toString()))
                    }
                    rv.addList(dataList)
                }
            }
            button("添加单个数据"){
                action {
                    rv.add(Person("这是张三",27.toString()))
                }
            }
            button("清除全部") {
                action{
                    rv.removeAll()
                }
            }
            button("下标3插入新数据"){
                action {
                    rv.add(Person("下标3","42"),3)
                }
            }
            button("更新下标5"){
                action {
                    rv.update(Person("更新后","34"),5)
                }
            }
            button("更新名字为张三9的子项"){
                action {
                    rv.update(Person("更新后10","34"),Person("张三9",27.toString()))
                }
            }
        }
        this+=rv

        val adapter = object :RVAdapter<Person,ItemView>(Array(10) { i ->Person("张三$i",(18+i).toString())}.toList()){

            override fun onRightClick(itemView: ItemView, position: Int) {
                println("右击$position")
            }

            override fun onClick(itemView: ItemView, position: Int) {
                println("单击$position")
            }

            override fun onCreateView(): ItemView {
                return ItemView()
            }

            override fun onBindData(itemView: ItemView, bean: Person, position: Int) {
                itemView.nameTv.text = bean.name
                itemView.ageTv.text  = bean.age
                itemView.deleteBtn.setOnAction {
                    println(position)
                    rv.remove(position)
                }
            }

        }
        rv.adapter = adapter
    }
}