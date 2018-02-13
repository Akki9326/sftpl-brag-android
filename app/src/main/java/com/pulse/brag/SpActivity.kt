package com.pulse.brag

import android.app.FragmentManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import com.google.firebase.iid.FirebaseInstanceId
import com.pulse.brag.ui.activities.MainActivity
import com.pulse.brag.utils.PreferencesManager
import com.pulse.brag.utils.Utility
import com.pulse.brag.interfaces.BaseInterface
import kotlinx.android.synthetic.main.activity_splash.*


class SpActivity : AppCompatActivity(), BaseInterface {

    internal lateinit var fragManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_sp)
        Utility.applyTypeFace(applicationContext, findViewById<View>(R.id.base_layout) as RelativeLayout)

        setDeviceToken()

        Handler().postDelayed({
            if (PreferencesManager.getInstance().isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                overridePendingTransition(R.anim.right_in, R.anim.left_out)
            } else {
                animatedViewAndLogin()
            }
        }, 2000)

    }


    override fun setToolbar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initializeData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        fragManager = getFragmentManager()
    }

    override fun setListeners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun setDeviceToken() {
        if (PreferencesManager.getInstance().deviceToken.isEmpty()) {
            PreferencesManager.getInstance().setDeviceTypeAndOsVer(Utility.getDeviceName(), android.os.Build.VERSION.RELEASE)
        }
        Log.i(javaClass.simpleName, "setDeviceNameAndOS: device token " + FirebaseInstanceId.getInstance().token!!)
        PreferencesManager.getInstance().deviceToken = FirebaseInstanceId.getInstance().token
    }

    fun animatedViewAndLogin() {
        val slide = AnimationUtils.loadAnimation(applicationContext,
                R.anim.slide_up_view)
        imageview_logo.startAnimation(slide)

        slide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                fragment_container_login.setVisibility(View.VISIBLE)
//                pushFragments(LogInFragment, true, false, "")
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
    }

   /* fun pushFragments(fragment: Fragment, isAdd: Boolean, isAnimation: Boolean, tag: String) {

        val ft = fragManager.beginTransaction();
        if (isAnimation) {
//            if (fragment is LogInFragment) {
//                ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//            } else {
//                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
//                        R.anim.enter_from_left, R.anim.exit_to_right)
//            }
        }
        if (isAdd) {
            ft.addToBackStack(tag)
        }
//        ft.replace(fragment_container_login, fragment, tag)
        ft.commit()
    }*/
}
