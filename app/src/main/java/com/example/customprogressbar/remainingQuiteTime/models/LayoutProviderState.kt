package com.example.customprogressbar.remainingQuiteTime.models

enum class LayoutProviderState {
    EMPTY, SINGLE, MULTIPLE;

    companion object {
        fun fromInt(value: Int): LayoutProviderState {
            return when(value) {
                0 -> EMPTY
                //1 -> SINGLE
                else -> MULTIPLE
            }
        }
    }
}