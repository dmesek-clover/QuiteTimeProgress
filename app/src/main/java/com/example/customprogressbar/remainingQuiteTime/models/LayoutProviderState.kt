package com.example.customprogressbar.remainingQuiteTime.models

enum class LayoutProviderState(var itemCount: Int) {
    EMPTY(0), SINGLE(1), MULTIPLE(1);

    fun setItemcount(number: Int) {
        itemCount = number
    }
    fun decrementItemCount(): LayoutProviderState {
        itemCount--
        return determineNextState()
    }
    fun incrementItemCountBy(increment: Int): LayoutProviderState {
        itemCount+=increment
        return determineNextState()
    }
    private fun determineNextState(): LayoutProviderState {
        return when (itemCount) {
            0 -> EMPTY
            //1 -> SINGLE
            else -> {
                val multiple = MULTIPLE
                multiple.setItemcount(itemCount)
                return multiple
            }
        }
    }
}