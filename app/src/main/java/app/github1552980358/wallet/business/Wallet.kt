package app.github1552980358.wallet.business

import android.content.ContentValues
import android.content.Context
import android.os.Trace
import app.github1552980358.wallet.business.account.BankAccount
import app.github1552980358.wallet.business.base.Transaction
import app.github1552980358.wallet.business.base.TransactionMethod
import app.github1552980358.wallet.business.base.TransactionType
import app.github1552980358.wallet.database.Database
import lib.github1552980358.ktExtension.jvm.keyword.tryRun
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

class Wallet(context: Context): Serializable {
    
    companion object {
        
        const val TABLE = "Wallet"
        
        
        const val COLUMN_DATETIME = "datetime"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_BALANCE = "balance"
        const val COLUMN_TYPE = "type"
        const val COLUMN_METHOD = "method"
        const val COLUMN_REMARK = "remark"
        
        private const val RAW_QUERY_FRONT = "select * from $TABLE where $COLUMN_DATETIME like '"
        private const val RAW_QUERY_BACK = "%' order by $COLUMN_DATETIME asc"
    }
    
    val bankAccounts = arrayListOf<BankAccount>()
    
    val transactions = arrayListOf<Transaction>()
    
    init {
        
        Database(context, cursorFactory = null).readableDatabase.also { database ->
            
            database.rawQuery(
                "$RAW_QUERY_FRONT${
                    SimpleDateFormat(
                        "yyyyMM",
                        Locale.getDefault()
                    ).format(System.currentTimeMillis())
                }$RAW_QUERY_BACK",
                null
            ).apply {
                
                this ?: return@apply
                
                while (moveToNext()) {
                    transactions.add(
                        Transaction().apply {
                            datetime = getString(getColumnIndex(COLUMN_DATETIME))
                            title = getString(getColumnIndex(COLUMN_TITLE))
                            amount = getFloat(getColumnIndex(COLUMN_AMOUNT))
                            balance = getFloat(getColumnIndex(COLUMN_BALANCE))
                            type = TransactionType.valueOf(getString(getColumnIndex(COLUMN_TYPE)))
                            method = TransactionMethod.valueOf(getString(getColumnIndex(COLUMN_METHOD)))
                            remark = tryRun { getString(getColumnIndexOrThrow(COLUMN_METHOD)) }
                        }
                    )
                }
                
            }.close()
            
            database.close()
        }
    }
    
    @Synchronized
    fun addTransaction(context: Context, newTransaction: Transaction): Boolean {
        newTransaction.doTransaction(transactions.last())
        if (Database.instance(context).writableDatabase.insert(
                TABLE,
                null,
                ContentValues().apply {
                    put(COLUMN_DATETIME, newTransaction.datetime)
                    put(COLUMN_TITLE, newTransaction.title)
                    put(COLUMN_AMOUNT, newTransaction.amount)
                    put(COLUMN_BALANCE, newTransaction.balance)
                    put(COLUMN_TYPE, newTransaction.type.name)
                    put(COLUMN_METHOD, newTransaction.method.name)
                    put(COLUMN_REMARK, newTransaction.remark)
                }
            ) == -1L
        ) {
            return false
        }
        Database.recycle()
        transactions.add(newTransaction)
        return true
    }
    
    fun addBankAccount(bankAccount: BankAccount) = apply { bankAccounts.add(bankAccount) }
    
    fun getTransactionsSpecified(context: Context, month: String) = ArrayList<Transaction>().apply {
        Database.instance(context).readableDatabase.rawQuery("$RAW_QUERY_FRONT$month$RAW_QUERY_BACK", null).apply {
            while (moveToNext()) {
                add(Transaction().apply {
                    datetime = getString(getColumnIndex(COLUMN_DATETIME))
                    title = getString(getColumnIndex(COLUMN_TITLE))
                    amount = getFloat(getColumnIndex(COLUMN_AMOUNT))
                    balance = getFloat(getColumnIndex(COLUMN_BALANCE))
                    type = TransactionType.valueOf(getString(getColumnIndex(COLUMN_TYPE)))
                    method = TransactionMethod.valueOf(getString(getColumnIndex(COLUMN_METHOD)))
                    remark = tryRun { getString(getColumnIndexOrThrow(COLUMN_METHOD)) }
                })
            }
        }.close()
        Database.recycle()
    }
    
}