package yandexschool.dmpolyakov.money.Base

import org.mockito.MockitoAnnotations

abstract class BaseUnitTest {
    open fun onInit() {
        MockitoAnnotations.initMocks(this)
        onMockInit()
    }

    abstract fun onMockInit()
}