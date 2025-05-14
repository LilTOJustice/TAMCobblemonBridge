package liltojustice.tamcobblemonbridge.client

import net.minecraft.text.OrderedText

fun OrderedText.getInternalString(): String {
    val result = StringBuilder()
    accept { _, _, codePoint ->
        result.append(codePoint.toChar().toString())
        true
    }

    return result.toString()
}