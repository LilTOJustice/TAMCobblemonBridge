package liltojustice.tamcobblemonbridge.client

import java.io.BufferedReader
import java.io.InputStreamReader

object Constants {
    val legendaries = BufferedReader(
        InputStreamReader(
            this::class.java.getClassLoader().getResourceAsStream("assets/legendaries")!!, "UTF-8")
    ).use { reader -> reader.readLines().toSet() }
}