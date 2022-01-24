package root.sample.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), BaseViewModelContract, CoroutineScope {

    private val supervisorJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = supervisorJob

    override fun getSupervisorJob() = supervisorJob

    override fun onCleared() {
        try {
            supervisorJob.cancel()
        } catch (e: Throwable) {
        }
        super.onCleared()
    }

}
