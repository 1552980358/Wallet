package app.github1552980358.wallet.container.splash

import android.annotation.TargetApi
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal
import app.github1552980358.wallet.R
import app.github1552980358.wallet.container.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import lib.github1552980358.ktExtension.android.content.toast
import lib.github1552980358.ktExtension.android.util.logE

class SplashActivity: AppCompatActivity() {
    
    companion object {
        
        const val INTENT_WALLET = "WALLET"
        const val TAG = "SplashActivity"
        
    }
    
    @Suppress("DEPRECATION")
    private lateinit var fingerPrintManager: FingerprintManagerCompat
    private lateinit var cancellationSignal: CancellationSignal
    private var dialog: BottomSheetDialogFragment? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        logE(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        
    }
    
    @TargetApi(Build.VERSION_CODES.M)
    fun fingerPrintManager_v23() {
        @Suppress("DEPRECATION")
        fingerPrintManager = FingerprintManagerCompat.from(this)
        cancellationSignal = CancellationSignal()
        
        dialog = FingerprintDialogFragment().showNow(supportFragmentManager, cancellationSignal)
        
        fingerPrintManager.authenticate(
            null,
            0,
            cancellationSignal,
            object: @Suppress("DEPRECATION") FingerprintManagerCompat.AuthenticationCallback() {
                override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                    dialog?.dismiss()
                    toast(R.string.fingerprintDialogFragment_failed_toast)
                }
        
                override fun onAuthenticationFailed() {}
        
                override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                    (dialog as FingerprintDialogFragment).helpMsg(helpString)
                }
        
                override fun onAuthenticationSucceeded(result: @Suppress("DEPRECATION") FingerprintManagerCompat.AuthenticationResult?) {
                    dialog?.dismiss()
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
        
            },
            null
        )
        
    }
    
    @TargetApi(Build.VERSION_CODES.P)
    fun fingerPrintManager_v28() {
        BiometricPrompt.Builder(this)
            .setTitle(getString(R.string.fingerprintDialogFragment_title))
            .setNegativeButton(getString(R.string.str_cancel), mainExecutor, { _, _ -> })
            .build()
            .authenticate(
                android.os.CancellationSignal(),
                mainExecutor,
                object: BiometricPrompt.AuthenticationCallback() {
        
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                        super.onAuthenticationError(errorCode, errString)
                    }
        
                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }
        
                    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                        super.onAuthenticationHelp(helpCode, helpString)
                    }
        
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                        super.onAuthenticationSucceeded(result)
                    }
        
                })
    }
    
}