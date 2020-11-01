package app.github1552980358.wallet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.github1552980358.wallet.R
import app.github1552980358.wallet.container.main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_balance.textView_month
import java.text.SimpleDateFormat

class HomeFragment: Fragment() {
    
    private var viewModel: MainActivityViewModel? = null
        get() {
            if (field == null) {
                field = requireActivity().viewModels<MainActivityViewModel>().value
            }
            return field
        }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_home, container, false)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        
        
    }
    
}