package app.github1552980358.wallet.business.account

import app.github1552980358.wallet.business.bank.BankCard
import app.github1552980358.wallet.business.base.Remark
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.io.Serializable

class BankAccount: Serializable {
    
    companion object {
        
        const val NAME = "name"
        const val ID = "id"
        const val PASSWORD = "password"
        const val CARDS = "cards"
        const val REMARKS = "remarks"
    }
    
    var name = ""
    
    var id = ""
    
    var password = ""
    
    val cards = arrayListOf<BankCard>()
    
    val remarks = arrayListOf<Remark>()
    
    fun toJsonStr() = JsonObject().apply {
        addProperty(NAME, name)
        addProperty(ID, id)
        addProperty(PASSWORD, password)
        add(
            CARDS,
            JsonArray().also { jsonArray ->
                cards.forEach { cards ->
                    jsonArray.add(cards.getJSON())
                }
            }
        )
        add(
            REMARKS,
            JsonArray().also { jsonArray ->
                remarks.forEach { remark ->
                    jsonArray.add(remark.toJson())
                }
            }
        )
    }
    
    
}