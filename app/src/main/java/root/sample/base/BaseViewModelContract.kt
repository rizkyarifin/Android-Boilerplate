package root.sample.base

import kotlinx.coroutines.Job

interface BaseViewModelContract {

    fun getSupervisorJob(): Job

}
