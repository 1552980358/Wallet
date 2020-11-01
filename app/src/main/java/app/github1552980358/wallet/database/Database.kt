package app.github1552980358.wallet.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import app.github1552980358.wallet.business.Wallet.Companion.COLUMN_AMOUNT
import app.github1552980358.wallet.business.Wallet.Companion.COLUMN_BALANCE
import app.github1552980358.wallet.business.Wallet.Companion.COLUMN_DATETIME
import app.github1552980358.wallet.business.Wallet.Companion.COLUMN_METHOD
import app.github1552980358.wallet.business.Wallet.Companion.COLUMN_REMARK
import app.github1552980358.wallet.business.Wallet.Companion.COLUMN_TITLE
import app.github1552980358.wallet.business.Wallet.Companion.COLUMN_TYPE
import app.github1552980358.wallet.business.Wallet.Companion.TABLE
import lib.github1552980358.ktExtension.jvm.kotlin.isNull

class Database(context: Context, name: String = DATABASE, cursorFactory: SQLiteDatabase.CursorFactory? = null, version: Int = 1):
    SQLiteOpenHelper(context, name, cursorFactory, version) {
    
    companion object {
        
        const val DATABASE = "database.db"
        
        @JvmStatic
        @Volatile
        private var database: Database? = null
    
        @JvmStatic
        @Synchronized
        fun instance(context: Context): Database {
            if (database.isNull()) {
                database = Database(context)
            }
            return database!!
        }
        
        @JvmStatic
        @Synchronized
        fun recycle() {
            if (database.isNull()) {
                return
            }
            database?.close()
            database = null
        }
    
    }
    
    override fun onCreate(db: SQLiteDatabase?) {
    
        db?.execSQL(
            "create table $TABLE( $COLUMN_DATETIME text not null, $COLUMN_TITLE text not null, $COLUMN_AMOUNT single not null, $COLUMN_BALANCE single not null, $COLUMN_TYPE text not null, $COLUMN_METHOD byte not null, $COLUMN_REMARK longtext )"
        )
        
    }
    
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    
    }
    
}