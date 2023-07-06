package com.example.mycard.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.domain.repository.IMyCardProducts
import com.example.mycard.di.scopes.MyCardScope
import com.example.mycard.presentation.fragments.MyCardFragment
import dagger.Component
import kotlin.properties.Delegates

@MyCardScope
@Component(
    dependencies = [MyCardComponent.MyCardDeps::class]
)
interface MyCardComponent {

    interface MyCardDeps {
        val myCard: IMyCardProducts
    }

    fun inject(myCardFragment: MyCardFragment)
}

interface MyCardDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val myCard: MyCardComponent.MyCardDeps

    companion object : MyCardDepsProvider by ArticlesMyCard
}

object ArticlesMyCard : MyCardDepsProvider {

    override var myCard: MyCardComponent.MyCardDeps by Delegates.notNull()
}

internal class ArticlesComponentMyCardViewModel : ViewModel() {

    val newDetailsMyCardComponent =
        DaggerMyCardComponent.builder().myCardDeps(MyCardDepsProvider.myCard).build()

}


