package com.sfag.automata.core.machine

/**
 * Dynamická páska pre Turingov stroj.
 * Je „nekonečná“ na obe strany pomocou MutableMap<Int, Char>.
 */
class TuringTape(
    initial: String = "",
    private val blank: Char = '_'
) {
    private val map: MutableMap<Int, Char> = mutableMapOf()
    var head = 0
        private set

    init {
        initial.forEachIndexed { i, c ->
            map[i] = c
        }
    }

    /** Prečíta aktuálny symbol pod hlavou */
    fun read(): Char = map.getOrDefault(head, blank)

    /** Zapíše symbol na aktuálnu pozíciu */
    fun write(c: Char) {
        map[head] = c
    }

    /** Posun doľava */
    fun moveLeft() {
        head--
        map.putIfAbsent(head, blank)
    }

    /** Posun doprava */
    fun moveRight() {
        head++
        map.putIfAbsent(head, blank)
    }

    /** Vráti časť pásky okolo hlavy (napr. pre UI) */
    fun view(range: Int = 20): List<Pair<Int, Char>> {
        val from = head - range
        val to = head + range
        return (from..to).map { it to map.getOrDefault(it, blank) }
    }

    override fun toString(): String {
        val min = map.keys.minOrNull() ?: 0
        val max = map.keys.maxOrNull() ?: 0
        return (min..max).joinToString("") { (map[it] ?: blank).toString() }
    }
}
