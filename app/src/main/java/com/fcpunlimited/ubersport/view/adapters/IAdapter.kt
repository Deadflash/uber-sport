package com.fcpunlimited.ubersport.view.adapters

interface IAdapter<T> {
    fun add(newItem: T)
    fun add(newItems: ArrayList<T>?)
    fun addAtPosition(pos : Int, newItem : T)
    fun remove(position: Int)
    fun clearAll()
    fun getData(): ArrayList<T>
    fun setData(items: ArrayList<T>)
}