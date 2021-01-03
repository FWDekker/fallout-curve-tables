package com.fwdekker.falloutcurvetables

import com.beust.klaxon.Klaxon
import java.io.File
import java.io.IOException


/**
 * A 2-dimensional point in a curve.
 *
 * @property x the x coordinate of the point
 * @property y the y coordinate of the point
 */
data class Point(val x: Int, val y: Int)


/**
 * A curve, i.e. a sequence of points.
 *
 * @property curve the points that the curve consists of
 */
data class Curve(val curve: List<Point>) {
    companion object {
        /**
         * Reads a curve from the given file.
         *
         * @param file the file to read the curve from
         * @throws IOException if the file could not be read or parsed
         */
        fun fromFile(file: File) =
            Klaxon().parse<Curve>(file)
                ?: throw IOException("Could not parse file.")
    }


    /**
     * Returns the string representation of the curve.
     *
     * @return the string representation of the curve
     */
    override fun toString() = curve.joinToString(separator = " ") { "${it.x},${it.y}" }
}
