package app.github1552980358.wallet.business.base

import java.io.Serializable
import app.github1552980358.wallet.business.base.TransactionType.INCOME
import app.github1552980358.wallet.business.base.TransactionMethod.CASH

class Transaction: Serializable {
    
    // e.g. 20201101101000
    //    = 2020-11-01 10:10:00
    var datetime = ""
    
    var title = ""
    var amount = 0.0F
    var balance = 0.0F
    var type = INCOME
    var method = CASH
    var remark: String? = null
    
    fun doTransaction(transaction: Transaction) = apply {
        balance = transaction.balance + (if (type == INCOME) 1 else -1) * amount
    }
    
}