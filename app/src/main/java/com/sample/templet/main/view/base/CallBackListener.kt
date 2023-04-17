package com.sample.templet.main.view.base

interface CallBackListener {
    fun moveNext(fragmentCount: Int)
    fun movePrev(fragmentCount: Int)
    fun completeAppointment(isFragmentFrom: String)
}