package tech.cherri.tokenpushexample

import java.lang.StringBuilder

object JSONUtils {
    fun pretty(unformattedString: String): String {
        val stringBuilder = StringBuilder()
        var indentString = ""
        for (i in 0 until unformattedString.length) {
            val letter = unformattedString[i]
            when (letter) {
                '{', '[' -> {
                    stringBuilder.append(
                        """
    
    $indentString$letter
    
    """.trimIndent()
                    )
                    indentString = indentString + "\t"
                    stringBuilder.append(indentString)
                }
                '}', ']' -> {
                    indentString = indentString.replaceFirst("\t".toRegex(), "")
                    stringBuilder.append(
                        """
    
    $indentString$letter
    """.trimIndent()
                    )
                }
                ',' -> stringBuilder.append(
                    """
    $letter
    $indentString
    """.trimIndent()
                )
                else -> stringBuilder.append(letter)
            }
        }
        return stringBuilder.toString()
    }
}