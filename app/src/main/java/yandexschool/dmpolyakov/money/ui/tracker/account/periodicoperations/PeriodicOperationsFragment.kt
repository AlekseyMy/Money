package yandexschool.dmpolyakov.money.ui.tracker.account.periodicoperations

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.example.delegateadapter.delegate.diff.IComparableItem
import kotlinx.android.synthetic.main.fragment_periodic_operations.*
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.models.Account
import yandexschool.dmpolyakov.money.models.FinanceOperation
import yandexschool.dmpolyakov.money.navigation.MainRouter
import yandexschool.dmpolyakov.money.ui.base.mvp.BaseMvpFragment
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.EmptyStateDelegateAdapter
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.PeriodicOperationsDelegateAdapter
import yandexschool.dmpolyakov.money.ui.base.rv_delegates.view_models.EmptyStateViewModel
import java.util.ArrayList
import javax.inject.Inject

class PeriodicOperationsFragment: BaseMvpFragment<PeriodicOperationsPresenter>(),
        PeriodicOperationsView {

    @Inject
    lateinit var router: MainRouter

    @Inject
    @InjectPresenter
    lateinit var presenter: PeriodicOperationsPresenter

    @ProvidePresenter
    override fun providePresenter(): PeriodicOperationsPresenter = presenter

    private val periodicOperationsAdapter = DiffUtilCompositeAdapter.Builder()
            .add(PeriodicOperationsDelegateAdapter {
                presenter.onDeleteClick(it)
            })
            .add(EmptyStateDelegateAdapter())
            .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_periodic_operations, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPeriodic.layoutManager = LinearLayoutManager(context)
        rvPeriodic.adapter = periodicOperationsAdapter
    }

    override fun loadAccount() {
        val account = arguments?.getParcelable<Account>("account")!!
        presenter.loadAccount(account)
    }

    override fun showOperations(operations: List<FinanceOperation>) {
        val data = ArrayList<IComparableItem>()
        data.addAll(operations.reversed())
        data.add(EmptyStateViewModel(getString(R.string.empty_periodic_operations_list)))
        periodicOperationsAdapter.swapData(data)
        rvPeriodic.scrollToPosition(0)
    }

    override fun getLogName(): String = "PeriodicOperationsFragment"

}