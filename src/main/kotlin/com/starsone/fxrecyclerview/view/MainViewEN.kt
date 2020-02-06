package com.starsone.fxrecyclerview.view

import tornadofx.*

/**
 * the main view of fxrecyclerview for english language
 */
class MainViewEN : View("Hello TornadoFX") {
    val rv = FxRecyclerView<Person,ItemView>()
    override val root = vbox {
        setPrefSize(1000.0,400.0)
        hbox{

            button("add a group of data") {
                action {
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

                        override fun onClick(itemView: ItemView, position: Int) {
                            println("onclick $position")
                        }

                        override fun onCreateView(): ItemView {
                            return ItemView()
                        }

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
                }
            }
            button("add a single data"){
                action {
                    rv.add(Person("new person",27.toString()))
                }
            }
            button("clear all data") {
                action{
                    rv.removeAll()
                }
            }
            button("insert a new data in index 3"){
                action {
                    rv.add(Person("index 3","42"),3)
                }
            }
            button("update the data of index 5"){
                action {
                    rv.update(Person("update index 5","34"),5)
                }
            }
        }
        this+=rv
        //if you want to add the data to the recyclerview when current view finishes init
        //you can write the code in following
        /*
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

            override fun onClick(itemView: ItemView, position: Int) {
                println("onclick $position")
            }

            override fun onCreateView(): ItemView {
                return ItemView()
            }

            override fun onBindData(itemView: ItemView, bean: Person, position: Int) {
                itemView.nameTv.text = bean.name
                itemView.ageTv.text  = bean.age
                itemView.deleteBtn.setOnAction {
                    rv.remove(position)
                }
            }
        }
        //4.set adapter
        rv.adapter = adapter*/
    }
}