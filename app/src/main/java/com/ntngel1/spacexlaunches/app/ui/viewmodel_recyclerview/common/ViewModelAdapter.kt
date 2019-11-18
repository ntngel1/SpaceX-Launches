package com.ntngel1.spacexlaunches.app.ui.viewmodel_recyclerview.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.ArrayMap
import androidx.recyclerview.widget.*

import java.util.ArrayList
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
open class ViewModelAdapter : RecyclerView.Adapter<ViewHolder>(),
    DifferProvider, ListUpdateCallback {

    protected val boundViewHolders: ArrayMap<String, ViewHolder> = ArrayMap()
    private var newViewModels: List<ViewModel> = emptyList()
    private val registeredViewTypes: ArrayList<KClass<ViewModel>> = ArrayList()
    private val viewBinders: ArrayList<ViewBinder<ViewModel, ViewModelDiffer<ViewModel, Payload>, Payload, ViewState>> =
        ArrayList()
    private var viewStateStore: ViewStateStore? = null
    private val differs: ArrayList<ViewModelDiffer<ViewModel, Payload>> = ArrayList()
    private val diffUtilCallback =
        ViewModelDiffUtilItemCallback(this)
    private val asyncListDiffer: AsyncListDiffer<ViewModel>

    init {
        asyncListDiffer = AsyncListDiffer(
            this as ListUpdateCallback,
            AsyncDifferConfig.Builder(diffUtilCallback)
                .build()
        )
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binder = getViewBinder(viewType)

        return LayoutInflater.from(parent.context)
            .inflate(binder.layoutId, parent, false)
            .let(::ViewHolder)
            .apply {
                this.viewType = viewType
                binder.init(itemView)
            }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewModel = getViewModel(position)
        val binder = getViewBinder(viewModel)
        val viewState = getViewState(viewModel)

        holder.viewModel = viewModel

        if (binder.viewStateProvider != null && viewState != null) {
            binder.bind(viewModel, holder.itemView, true)
        } else {
            binder.bind(viewModel, holder.itemView, false)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        val viewModel = getViewModel(position)
        val binder = getViewBinder(viewModel)

        if (payloads.isNotEmpty()) {
            payloads.forEach { payloads ->
                binder.update(payloads as List<Payload>, holder.itemView)
            }

            holder.viewModel = viewModel
        } else {
            //super.onBindViewHolder(holder, position, payloads)
            onBindViewHolder(holder, position)
        }

        boundViewHolders[viewModel.id] = holder
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        restoreViewStateIfNeeded(holder)
    }

    private fun restoreViewStateIfNeeded(holder: ViewHolder) {
        val viewModel = getViewModel(holder)
        val binder = getViewBinder(viewModel)
        val viewState = getViewState(viewModel)

        if (binder.viewStateProvider != null && viewState != null) {
            binder.viewStateProvider?.restoreState(viewState, holder.itemView)
        }
    }

    private fun getViewBinder(viewType: Int): ViewBinder<ViewModel, ViewModelDiffer<ViewModel, Payload>, Payload, ViewState> {
        return viewBinders.getOrNull(viewType)
            ?: throw Exception("Not registered viewType = $viewType")
    }

    protected fun getViewModel(holder: ViewHolder): ViewModel {
        return holder.viewModel
            ?: throw RuntimeException("ViewModel is not assigned to ViewHolder.")
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    private fun getViewModel(position: Int): ViewModel {
        return asyncListDiffer.currentList[position]
    }

    override fun getItemViewType(position: Int): Int {
        val viewModelType = getViewModel(position)::class

        val typeIndex = registeredViewTypes.indexOf(viewModelType)
        if (typeIndex != -1) {
            return typeIndex
        } else {
            throw Exception("There is no ViewBinder for this type = $viewModelType")
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

        val viewModel = getViewModel(holder)
        val binder = getViewBinder(viewModel)

        boundViewHolders.remove(viewModel.id)
        binder.onViewDetachedFromWindow(viewModel, holder.itemView)

        saveViewStateIfNeeded(holder)
    }

    fun registerViewBinder(binder: ViewBinder<*, *, *, *>) {
        val isAlreadyRegistered = isViewBinderRegistered(binder)

        if (!isAlreadyRegistered) {
            viewBinders.add(binder as ViewBinder<ViewModel, ViewModelDiffer<ViewModel, Payload>, Payload, ViewState>)
            registeredViewTypes.add(binder.viewModelClass)
            differs.add(binder.viewModelDiffer)
        } else {
            throw RuntimeException("ViewBinder already registed for this type = ${binder.viewModelClass}")
        }
    }

    private fun isViewBinderRegistered(viewBinder: ViewBinder<*, *, *, *>): Boolean {
        return registeredViewTypes.indexOf(viewBinder.viewModelClass) != -1
    }

    fun setViewModels(newViewModels: List<ViewModel>) {
        this.newViewModels = newViewModels
        asyncListDiffer.submitList(newViewModels)
    }

    private fun applyPayloadsIfNeeded(payload: Any?, count: Int, newViewModels: List<ViewModel>, position: Int) {
        if (payload != null && (payload as List<Payload>).isNotEmpty()) {
            if (count > 1) {
                throw RuntimeException("Count of changed viewModels greater than 1. Please, check that all of your ViewModels have stable and unique IDs.")
            }

            updateViewState(newViewModels[position], payload)
        }
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        this@ViewModelAdapter.notifyItemRangeChanged(position, count, payload)
        applyPayloadsIfNeeded(payload, count, newViewModels, position)
    }

    override fun onInserted(position: Int, count: Int) {
        this@ViewModelAdapter.notifyItemRangeInserted(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        this@ViewModelAdapter.notifyItemRangeRemoved(position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        this@ViewModelAdapter.notifyItemMoved(fromPosition, toPosition)
    }

    private fun updateViewState(viewModel: ViewModel, payload: Any?) {
        val payloads = payload as? List<Payload>
            ?: throw RuntimeException("Payloads is not a list of Payload. $payload")

        val viewState = getViewState(viewModel)

        if (payloads.isNotEmpty() && viewState != null) {
            val viewStateProvider = getViewStateProvider(viewModel)
                ?: return

            val newViewState = viewStateProvider.updateState(viewState, payloads)

            if (newViewState != null) {
                saveViewState(viewModel, newViewState)
            }
        }
    }

    protected fun saveViewState(viewModel: ViewModel, viewState: ViewState) {
        viewStateStore?.saveViewState(viewModel.id, viewState)
    }

    override fun provideDiffer(viewModel: ViewModel): ViewModelDiffer<ViewModel, *> {
        val typeIndex = registeredViewTypes.indexOf(viewModel::class)
        return differs[typeIndex]
    }

    protected fun getViewStateProvider(viewModel: ViewModel?): ViewStateProvider<ViewState, Payload>? {
        val viewBinder = getViewBinder(viewModel!!)
        return viewBinder.viewStateProvider
    }

    private fun getViewState(viewModel: ViewModel): ViewState? {
        return if (viewStateStore != null && viewStateStore!!.hasViewState(viewModel.id)) {
            viewStateStore!!.getViewState(viewModel.id)
        } else {
            null
        }
    }

    protected fun saveViewStateIfNeeded(holder: ViewHolder) {
        val viewModel = getViewModel(holder)
        val binder = getViewBinder(viewModel)

        if (viewStateStore != null && binder.viewStateProvider != null) {
            val viewState = binder.viewStateProvider!!.saveState(holder.itemView)
            viewStateStore?.saveViewState(viewModel.id, viewState)
        }
    }

    protected fun getViewBinder(viewModel: ViewModel): ViewBinder<ViewModel, ViewModelDiffer<ViewModel, Payload>, Payload, ViewState> {
        val typeIndex = registeredViewTypes.indexOf(viewModel::class)
        if (typeIndex != -1) {
            return viewBinders[typeIndex]
        } else {
            throw Exception("Not registered ViewBinder for viewModel: $viewModel")
        }
    }

    fun setViewStateStore(viewStateStore: ViewStateStore) {
        this.viewStateStore = viewStateStore
    }

    fun clearViewStates() {
        if (viewStateStore != null) {
            viewStateStore!!.clearViewStates()
        } else {
            throw Exception("You cannot clear viewStates without viewStateStore presented.")
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
    }
}
