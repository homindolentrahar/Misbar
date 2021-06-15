package com.homindolentrahar.misbar.utils.mappers

object StringMapper {
    fun capitalize(input: String) =
        input.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}