package com.example.category.presentation.adapters.menus

import com.example.category.presentation.adapters.DelegateAdapterItem
import com.example.category.presentation.view.lists.IListMenu
import com.example.core.domain.entity.MenuCategory
import com.example.core.domain.entity.Menus

data class CategoriesDelegate(
    val menus: MenuCategory,
    val menusListList: IListMenu
): DelegateAdapterItem {

    override fun id(): Any = MenuCategory::class.java

    override fun content(): Any = CategoryContent(menus, menusListList)

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is CategoriesDelegate){
            if (menus != other.menus){
                return ChangePayload.CategoryNameChanged(other.menus)
            }

        }
        return DelegateAdapterItem.Payloadable.None
    }

    inner class CategoryContent(val categories: MenuCategory,
                                val categoryList: IListMenu
    ){
        override fun equals(other: Any?): Boolean {
            if (other is CategoryContent){
                return categories == other.categories && categoryList == other.categoryList
            }
            return false
        }

        override fun hashCode(): Int {
            var result = categories.hashCode()
            result = 31 * result + categoryList.hashCode()
            return result
        }
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class CategoryNameChanged (val categoryName: MenuCategory): ChangePayload()
    }
}