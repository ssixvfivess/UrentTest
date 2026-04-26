package com.vyatkina.urenttest.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CoroutineDispatchersImpl @Inject constructor() : CoroutineDispatchers {

    override fun main(): CoroutineDispatcher = Dispatchers.Main

    override fun mainImmediate(): CoroutineDispatcher = Dispatchers.Main.immediate

    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun default(): CoroutineDispatcher = Dispatchers.Default

    override fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}