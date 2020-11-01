package app.github1552980358.wallet.container.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.github1552980358.wallet.business.Wallet
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivityViewModel: ViewModel() {

    private val _wallet = MutableLiveData<Wallet>()
    val wallet: LiveData<Wallet> = _wallet
    fun setWallet(wallet: Wallet?) { _wallet.value = wallet }
    
    var currentMonth = SimpleDateFormat("yyyyMM", Locale.getDefault()).format(System.currentTimeMillis())
    private val _selectedMonth = MutableLiveData(currentMonth)
    val selectedMonth: LiveData<String> = _selectedMonth
    fun setSelectedMonth(newMonth: String) { _selectedMonth.value = newMonth }
    
}