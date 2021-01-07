package com.example.barupagainview

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.Color

val parts : Int = 4
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val lineHFactor : Float = 2f
val barHFactor : Float = 8.9f
val delay : Long = 20
val colors : Array<Int> = arrayOf(
    "#F44336",
    "#3F51B5",
    "#795548",
    "#009688",
    "#2196F3"
).map {
    Color.parseColor(it)
}.toTypedArray()

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()
