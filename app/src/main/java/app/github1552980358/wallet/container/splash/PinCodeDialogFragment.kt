package app.github1552980358.wallet.container.splash

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import app.github1552980358.wallet.R
import app.github1552980358.wallet.container.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialogfragment_pin.imageButton_backspace
import kotlinx.android.synthetic.main.dialogfragment_pin.imageButton_close
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_0
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_1
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_2
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_3
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_4
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_5
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_6
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_7
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_8
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_9
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_pw_0
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_pw_1
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_pw_2
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_pw_3
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_pw_4
import kotlinx.android.synthetic.main.dialogfragment_pin.textView_pw_5
import lib.github1552980358.ktExtension.jvm.kotlin.isNull
import java.io.File
import java.security.MessageDigest
import kotlin.experimental.and

class PinCodeDialogFragment: BottomSheetDialogFragment() {
    
    companion object {
        
        private const val FULL_CIRCLE = "●"
        private const val CIRCLE = "○"
        
        private const val TAG = "PinCodeDialogFragment"
        
        private const val SECURITY_FILE = "security"
        private const val PIN_MD5 = "pin_md5"
    }
    
    private lateinit var textViews: ArrayList<TextView>
    private var stringBuilder = StringBuilder()
    
    private var counter = 0
    private var pinMD5: String? = null
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setContentView(R.layout.dialogfragment_pin)
            
            textViews =
                arrayListOf(textView_pw_0, textView_pw_1, textView_pw_2, textView_pw_3, textView_pw_4, textView_pw_5)
    
            @Suppress("DuplicatedCode")
            textView_0.setOnClickListener {
                appendPin('0')
            }
            textView_1.setOnClickListener {
                appendPin('1')
            }
            textView_2.setOnClickListener {
                appendPin('2')
            }
            textView_3.setOnClickListener {
                appendPin('3')
            }
            textView_4.setOnClickListener {
                appendPin('4')
            }
            textView_5.setOnClickListener {
                appendPin('5')
            }
            textView_6.setOnClickListener {
                appendPin('6')
            }
            textView_7.setOnClickListener {
                appendPin('7')
            }
            textView_8.setOnClickListener {
                appendPin('8')
            }
            textView_9.setOnClickListener {
                appendPin('9')
            }
            imageButton_backspace.setOnClickListener {
                if (stringBuilder.isEmpty()) {
                    return@setOnClickListener
                }
                //stringBuilder = StringBuilder(stringBuilder.substring(0, stringBuilder.lastIndex))
                stringBuilder.deleteAt(stringBuilder.lastIndex)
                showCircles()
            }
            imageButton_close.setOnClickListener {
                dismiss()
            }
        }
    }
    
    @Synchronized
    private fun appendPin(char: Char) {
        stringBuilder.append(char)
        showCircles()
        if (stringBuilder.length != 6) {
            return
        }
        if (validatePin()) {
            dismiss()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
            return
        }
        stringBuilder.clear()
        showCircles()
    }
    
    private fun showCircles() {
        for (i in stringBuilder.indices) {
            textViews[i].text = FULL_CIRCLE
        }
        for (i in stringBuilder.length .. 5) {
            textViews[i].text = CIRCLE
        }
    }
    
    private fun validatePin(): Boolean {
        if (counter == 0) {
            pinMD5 = requireContext().getSharedPreferences(SECURITY_FILE, MODE_PRIVATE)?.getString(PIN_MD5, "")
        }
        if (pinMD5.isNull()) {
            return false
        }
        if (pinMD5!!.isEmpty()) {
            return false
        }
        return calMD5() == pinMD5
    }
    
    private fun calMD5(): String {
        val sb = StringBuilder()
        var tmp: String
        MessageDigest.getInstance("MD5").digest(stringBuilder.toString().encodeToByteArray()).forEach { byte ->
            tmp = Integer.toHexString(byte.toInt() and 0xff)
            if (tmp.length == 1) {
                sb.append('0')
            }
            sb.append(tmp)
        }
        return sb.toString()
    }
    
    fun showNow(manager: FragmentManager) {
        super.showNow(manager, TAG)
    }
    
}