package com.example.category.presentation.adapters.dishes

import com.example.category.presentation.adapters.DelegateAdapterItem
import com.example.category.presentation.view.lists.IDishesList
import com.example.domain.entity.Menus

class DishesDelegate(
    val dishes: Menus,
    val dishesList: IDishesList?
): DelegateAdapterItem {

    override fun id(): Any = Menus::class.java

    override fun content(): Any = DishesContent(dishes, dishesList)

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is DishesDelegate){
            if (dishes != other.dishes){
                return ChangePayload.CategoryNameChanged(other.dishes)
            }
        }
        return DelegateAdapterItem.Payloadable.None
    }

    inner class DishesContent(val dishes: Menus,
                              val dishesList: IDishesList?){
        override fun equals(other: Any?): Boolean {
            if (other is DishesContent){
                return dishes == other.dishes && dishesList == other.dishesList
            }
            return false
        }

        override fun hashCode(): Int {
            var result = dishes.hashCode()
            result = 31 * result + (dishesList?.hashCode() ?: 0)
            return result
        }
    }
    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class CategoryNameChanged (val dishesName: Menus): ChangePayload()
    }

}