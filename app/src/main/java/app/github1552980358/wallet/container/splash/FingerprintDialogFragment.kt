package app.github1552980358.wallet.container.splash

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.os.CancellationSignal
import androidx.fragment.app.FragmentManager
import app.github1552980358.wallet.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialogfragment_fingerprint.linearLayout
import kotlinx.android.synthetic.main.dialogfragment_fingerprint.textView_status
import lib.github1552980358.ktExtension.android.util.logE

class FingerprintDialogFragment: BottomSheetDialogFragment() {
    companion object {
        
        const val TAG = "FingerprintDialogFragment"
        
    }
    
    private var cancellationSignal: CancellationSignal? = null
    private var textViewStatus: TextView? = null
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        logE(TAG, "onCreateDialog")
        return super.onCreateDialog(savedInstanceState).apply {
            setContentView(R.layout.dialogfragment_fingerprint)
            
            linearLayout.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, resources.displayMetrics.heightPixels * 2 / 5).apply {
                resources.getDimensionPixelSize(R.dimen.dialogfragment_margins).apply {
                    setMargins(this, this, this, this)
                }
            }
            textViewStatus = textView_status
            window?.findViewById<View>(R.id.design_bottom_sheet)?.apply {
                setBackgroundResource(android.R.color.transparent)
                BottomSheetBehavior.from(this).isHideable = false
            }
        }
    }
    
    fun showNow(manager: FragmentManager, cancellationSignal: CancellationSignal) = apply {
        this.cancellationSignal = cancellationSignal
        super.showNow(manager, TAG)
    }
    
    override fun showNow(manager: FragmentManager, tag: String?) {
        logE(TAG, "showNow")
        super.showNow(manager, tag)
    }
    
    override fun onDismiss(dialog: DialogInterface) {
        logE(TAG, "onDismiss")
        cancellationSignal?.cancel()
        cancellationSignal = null
        super.onDismiss(dialog)
    }
    
    fun helpMsg(msg: CharSequence?) {
        textViewStatus?.text = msg
    }

}