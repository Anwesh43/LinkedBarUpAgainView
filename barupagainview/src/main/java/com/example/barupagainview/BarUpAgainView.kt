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

fun Canvas.drawBarUpAgain(scale : Float, w : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val lineH : Float = Math.min(w, h) / lineHFactor
    val barH : Float = Math.min(w, h) / barHFactor
    save()
    translate(w / 2, h)
    for (j in 0..1) {
        save()
        translate(0f, -barH / 2 + barH * j)
        drawLine(0f, 0f, 0f, -(lineH + barH) * sf1, paint)
        restore()
    }
    for (j in 0..1) {
        save()
        translate(-barH / 2, -(lineH + barH) + barH * j)
        drawLine(0f, 0f, barH * sf2, 0f, paint)
        restore()
    }
    save()
    translate(-barH / 2, -lineH)
    drawRect(RectF(0f, -barH * sf3, barH, 0f), paint)
    restore()
    restore()
}

fun Canvas.drawBUANode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawBarUpAgain(scale, w, h, paint)
}

class BarUpAgainView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }
}