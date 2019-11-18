package com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.epoxy.ActivityRecyclerPool
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.epoxy.UnboundedViewPool
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.SceneController
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewModel
import com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common.ViewModelAdapter

class ViewModelRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var viewModels = emptyList<ViewModel>()
        private set

    private var sceneController: SceneController? = null
    private var onViewModelsDispatch: ((viewModels: List<ViewModel>) -> Unit)? = null

    init {
        setHasFixedSize(true)
        initViewPool()
    }


    fun attachSceneController(controller: SceneController) {
        sceneController = controller
        (adapter as ViewModelAdapter).setViewStateStore(controller)
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        check(adapter is ViewModelAdapter) { "RecyclerView.Adapter should be ViewModelAdapter!" }
        super.setAdapter(adapter)
    }

    fun buildScene() {
        val controller = checkNotNull(sceneController)
        val viewModelAdapter = adapter as ViewModelAdapter

        controller.buildViewModels()
            .also { viewModels ->
                this.viewModels = viewModels
                onViewModelsDispatch?.invoke(viewModels)
            }
            .let(viewModelAdapter::setViewModels)
    }

    fun onViewModelsDispatch(listener: ((viewModels: List<ViewModel>) -> Unit)?) {
        onViewModelsDispatch = listener
    }

    private fun initViewPool() {
        setRecycledViewPool(
            ACTIVITY_RECYCLER_POOL.getPool(
                context
            ) { createViewPool() }.viewPool
        )
    }

    private fun createViewPool(): RecycledViewPool {
        return UnboundedViewPool()
    }


    companion object {
        private val ACTIVITY_RECYCLER_POOL = ActivityRecyclerPool()
    }
}