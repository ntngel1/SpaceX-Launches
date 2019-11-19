package com.ntngel1.spacexlaunches.app.common.viewmodel_recyclerview.common

import android.view.View
import kotlin.reflect.KClass

abstract class ViewBinder<VM : ViewModel, out VD : ViewModelDiffer<VM, P>, P : Payload, VS : ViewState> {

    abstract val layoutId: Int
    abstract val viewModelClass: KClass<VM>
    abstract val viewModelDiffer: VD

    /**
     * По дефолту сохранение состояние отключено
     * Чтобы включить, нужно переопределить этот метод и вернуть [ViewStateProvider]
     */
    open val viewStateProvider: ViewStateProvider<VS, P>? = null


    /**
     * Вызывается в момент создания view
     *
     * @see [ViewModelAdapter.onCreateViewHolder]
     */
    open fun init(itemView: View) {}

    /**
     * Вызывается в момент биндинга без payloads.
     *
     * @param hasViewState Обозначает, есть ли сохраненный [ViewState] для данной [ViewModel].
     *                     К примеру, если имеются какие-либо анимации (раскрывающийся список),
     *                     если hasViewState = true, мы забиндим список как раскрытый без
     *                     применения анимаций. В противном случае, анимацию мы применим.
     *
     * @see [ViewModelAdapter.onBindViewHolder]
     */
    open fun bind(viewModel: VM, itemView: View, hasViewState: Boolean) {}

    /**
     * Вызывается в том случае, если применены какие-либо payloads.
     * Не будет вызван никогда, если вы не переопределили метод [ViewModelDiffer.getChangePayload]
     * у [viewModelDiffer]. Нужен для частичного бинда элемента.
     * Пример: раскрытие списка. При клике на шапку элемента нужно полностью его раскрыть, тогда мы
     * в [ViewModelDiffer.getChangePayload] определяем payload ExpansionChanged.
     */
    open fun update(payloads: List<P>, itemView: View) {}

    /**
     * Вызывается в момент, когда view не находится в области видимости экрана
     *
     * @see [ViewModelAdapter.onViewDetachedFromWindow]
     */
    open fun onViewDetachedFromWindow(viewModel: VM, itemView: View) {}

}