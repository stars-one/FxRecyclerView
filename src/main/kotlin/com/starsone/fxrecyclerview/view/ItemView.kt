package com.starsone.fxrecyclerview.view

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
