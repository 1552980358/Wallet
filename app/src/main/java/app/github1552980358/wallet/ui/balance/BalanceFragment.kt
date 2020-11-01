package app.github1552980358.wallet.ui.balance

import android.app.Service
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.github1552980358.wallet.R
import app.github1552980358.wallet.business.base.Transaction
import app.github1552980358.wallet.business.base.TransactionHolder
import app.github1552980358.wallet.business.base.TransactionMethod.ALIPAY
import app.github1552980358.wallet.business.base.TransactionMethod.BANK
import app.github1552980358.wallet.business.base.TransactionMethod.CASH
import app.github1552980358.wallet.business.base.TransactionMethod.WECHAT
import app.github1552980358.wallet.business.base.TransactionType
import app.github1552980358.wallet.container.main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_balance.textView_month

class BalanceFragment: Fragment() {
    
    companion object {
        
        private const val MONTH_SEPARATOR = '.'
        private const val COLON = ':'
        private const val AMOUNT_FORMAT = "%.2f"
        
    }
    
    private var viewModel: MainActivityViewModel? = null
        get() {
            if (field == null) {
                field = requireActivity().viewModels<MainActivityViewModel>().value
            }
            return field
        }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_balance, container, false)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        textView_month.text =
            viewModel?.currentMonth?.substring(0, 4) + MONTH_SEPARATOR + viewModel?.currentMonth?.substring(4)
        viewModel?.selectedMonth?.observe(viewLifecycleOwner) { selectedMonth ->
            textView_month.text = selectedMonth.substring(0, 4) + MONTH_SEPARATOR + selectedMonth.substring(4)
        }
        
    }
    
    override fun onDestroy() {
        viewModel = null
        super.onDestroy()
    }
    
    private class RecyclerViewAdapter(private var transactions: ArrayList<TransactionHolder>):
        RecyclerView.Adapter<ViewHolder>() {
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            (parent.context.getSystemService(Service.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.view_day_transactions, parent, false)
        )
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.recyclerView.layoutManager = LinearLayoutManager(holder.recyclerView.context)
            holder.recyclerView.adapter = ChildRecyclerViewAdapter(transactions[position].getTransactionList())
            holder.textView_month.text = transactions[position].day
        }
        
        override fun getItemCount(): Int = transactions.size
        
        fun updateTransactions(newTransaction: ArrayList<TransactionHolder>) {
            transactions = newTransaction.apply {
                sortBy { it.day }
                reverse()
            }
            notifyDataSetChanged()
        }
        
    }
    
    private class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView_month: TextView = view.findViewById(R.id.textView_month)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    }
    
    private class ChildRecyclerViewAdapter(private var transactions: ArrayList<Transaction>):
        RecyclerView.Adapter<ChildViewHolder>() {
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
            return ChildViewHolder(
                (parent.context.getSystemService(Service.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.view_transaction,
                    parent,
                    false
                )
            )
        }
        
        override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
            transactions[position].datetime.apply {
                holder.textView_time.text =
                    this.substring(8, 10) + COLON + this.substring(10, 12) + COLON + this.substring(12)
            }
            holder.textView_amount.text = String.format(AMOUNT_FORMAT, transactions[position].amount)
            holder.textView_title.text = transactions[position].title
            holder.imageView.setImageResource(
                when (transactions[position].method) {
                    CASH -> R.drawable.ic_cash
                    BANK -> R.drawable.ic_bank
                    ALIPAY -> R.drawable.ic_alipay
                    WECHAT -> R.drawable.ic_wechat
                }
            )
            if (transactions[position].type == TransactionType.EXPENDITURE) {
                holder.textView_amount.setBackgroundResource(R.drawable.bg_textview_expenditure)
            }
        }
        
        override fun getItemCount(): Int = transactions.size
        
    }
    
    private class ChildViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView_amount: TextView = view.findViewById(R.id.textView_amount)
        val textView_title: TextView = view.findViewById(R.id.textView_title)
        val textView_time: TextView = view.findViewById(R.id.textView_time)
    }
    
}