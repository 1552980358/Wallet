package app.github1552980358.wallet.container.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import app.github1552980358.wallet.R
import app.github1552980358.wallet.business.Wallet
import app.github1552980358.wallet.container.splash.SplashActivity
import app.github1552980358.wallet.ui.accounts.AccountsFragment
import app.github1552980358.wallet.ui.balance.BalanceFragment
import app.github1552980358.wallet.ui.cards.CardsFragment
import app.github1552980358.wallet.ui.home.HomeFragment
import app.github1552980358.wallet.ui.me.MeFragment
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
//import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.viewPager
import lib.github1552980358.ktExtension.androidx.fragment.app.SimpleFragmentPagerAdapter
import lib.github1552980358.ktExtension.jvm.kotlin.isNull

class MainActivity: AppCompatActivity() {
    
    companion object {
        
        const val INTENT_WALLET = SplashActivity.INTENT_WALLET
        
    }
    
    private lateinit var viewModel: MainActivityViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // setSupportActionBar(toolbar)
        
        viewModel = viewModels<MainActivityViewModel>().value
        // 首次启动
        if (viewModel.wallet.value.isNull()) {
            viewModel.setWallet(intent.getSerializableExtra(INTENT_WALLET) as Wallet?)
        }
        viewModel.wallet.observe(this, { wallet ->
            // 为空时无视
            wallet?:return@observe
            
        })
        
        val navTitles = arrayOf(R.id.menu_cards, R.id.menu_accounts, R.id.menu_home,  R.id.menu_balance,R.id.menu_me)
        viewPager.adapter = SimpleFragmentPagerAdapter(supportFragmentManager, arrayOf(CardsFragment(), AccountsFragment(), HomeFragment(), BalanceFragment(), MeFragment()))
        viewPager.currentItem = 2
        bottomNavigationView.selectedItemId = navTitles[2]
        bottomNavigationView.setOnNavigationItemSelectedListener {
            viewPager.currentItem = navTitles.indexOf(it.itemId)
            true
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            
            }
            
            override fun onPageSelected(position: Int) {
                bottomNavigationView.selectedItemId = navTitles[position]
            }
    
            override fun onPageScrollStateChanged(state: Int) {}
            
        })
        
    }
}