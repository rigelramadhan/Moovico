package one.reevdev.moovico.core.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
){

    companion object {
        private const val THREAD_COUNT = 3
    }

    @Inject
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    )

    fun diskIO(): Executor = diskIO

    fun networkIO(): Executor = networkIO

    fun mainThread(): Executor = mainThread

    private class MainThreadExecutor : Executor {
        private val mainThreadHandleInfo = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandleInfo.post(command)
        }
    }
}