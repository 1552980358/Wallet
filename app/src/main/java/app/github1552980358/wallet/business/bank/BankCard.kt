package app.github1552980358.wallet.business.bank

import app.github1552980358.wallet.business.bank.Organization.UNKNOWN
import app.github1552980358.wallet.business.base.Remark
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.io.Serializable

class BankCard: Serializable {
    
    companion object {
        
        const val NAME = "name"
        const val ID = "id"
        const val CODE = "code"
        const val CODE2 = "code2"
        @Suppress("SpellCheckingInspection")
        const val VALIDM = "validM"
        @Suppress("SpellCheckingInspection")
        const val VALIDY = "validY"
        const val ORGANIZATION = "organization"
        const val REMARKS = "remarks"
        
    }
    
    var name = ""
    
    var id = ""
    
    var code = ""
    
    var code2: String? = null
    
    var validM = ""
    var validY = ""
    
    var organization = UNKNOWN
    
    var remarks = arrayListOf<Remark>()
    
    fun getJSON() = JsonObject().apply {
        addProperty(NAME, name)
        addProperty(ID, id)
        addProperty(CODE, code)
        addProperty(CODE2, code2)
        addProperty(VALIDM, validM)
        addProperty(VALIDY, validY)
        addProperty(ORGANIZATION, organization.name)
        add(
            REMARKS,
            JsonArray().apply {
                remarks.forEach { remark ->
                    add(remark.toJson())
                }
            }
        )
    }
    
}