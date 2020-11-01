package app.github1552980358.wallet.business.base

import com.google.gson.JsonObject

class Remark(var title: String, var content: String) {
    
    companion object {
        
        const val TITLE = "title"
        const val CONTENT = "content"
        
    }

    fun toJson() = JsonObject().apply {
            addProperty(TITLE, title)
            addProperty(CONTENT, content)
        }

}