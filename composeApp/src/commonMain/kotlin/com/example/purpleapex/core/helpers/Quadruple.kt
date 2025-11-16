package com.example.purpleapex.core.helpers

data class Quadruple<out A, out B, out C, out D>(
    public val first: A,
    public val second: B,
    public val third: C,
    public val fourth: D,
) {

    /**
     * Returns string representation of the [Quadruple] including its [first], [second], [third], and [fourth] values.
     */
    public override fun toString(): String = "($first, $second, $third, $fourth)"
}

/**
 * Converts this quadruple into a list.
 */
public fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)
