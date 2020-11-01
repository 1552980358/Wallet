package app.github1552980358.wallet.business.base

import java.io.Serializable

class TransactionHolder: Serializable {

    var day = ""
    
    private val arraysList = ArrayList<Transaction>()
    
    fun getTransactionList() = arraysList.apply {
        sortBy { it.datetime }
    }
    
    fun addTransaction(newTransaction: Transaction) {
        arraysList.add(newTransaction)
    }
    
}