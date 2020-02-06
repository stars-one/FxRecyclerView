package com.starsone.fxrecyclerview.view

import tornadofx.*

class MainView : View("Hello TornadoFX") {
    override val root = vbox {
        button("fxrecyclerview(English)"){
            action{
                find(MainViewEN::class).openModal()
            }
        }
        button("fxrecyclerview(中文)"){
            action{
                find(MainViewZH::class).openModal()
            }
        }
    }
}