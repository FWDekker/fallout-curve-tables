package com.fwdekker.falloutcurvetable

import com.beust.klaxon.Klaxon
import java.io.File


class Point(val x: Int, val y: Int)


class Curve(val curve: Array<Point>) {
    companion object {
        fun fromFile(file: File) = Klaxon().parse<Curve>(file)
            ?: throw IllegalArgumentException("Could not parse file.")
    }


    override fun toString() = curve.joinToString(separator = " ") { "${it.x},${it.y}" }
}
