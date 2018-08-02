package yandexschool.dmpolyakov.money.di.features

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import yandexschool.dmpolyakov.money.repository.AccountRepository
import yandexschool.dmpolyakov.money.repository.AccountRepositoryImpl
import yandexschool.dmpolyakov.money.storage.AppDatabase
import yandexschool.dmpolyakov.money.ui.MainActivity
import javax.inject.Singleton


@Module
abstract class AppModule {

    @ContributesAndroidInjector(modules = [(MainModule::class)])
    abstract fun contributeMainActivity(): MainActivity

    @Module
    companion object {
        @JvmStatic
        @Singleton
        @Provides
        fun provideDatabase(context: Context) =
                Room.databaseBuilder(context, AppDatabase::class.java, "Money").build()

        @JvmStatic
        @Singleton
        @Provides
        fun provideContext(app: Application): Context = app

        @JvmStatic
        @Singleton
        @Provides
        fun provideResources(context: Context): Resources = context.resources

        @JvmStatic
        @Singleton
        @Provides
        fun provideAccountRepository(db: AppDatabase): AccountRepository = AccountRepositoryImpl(db)
    }

}