package com.mobile.vision.app

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.support.multidex.MultiDex
import com.crashlytics.android.Crashlytics
import com.google.firebase.auth.FirebaseAuth
import com.mobile.vision.di.components.AppComponent
import com.mobile.vision.di.components.DaggerAppComponent
import com.mobile.vision.di.modules.AppModule
import com.mobile.vision.di.modules.FirebaseModule
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class MobileVisionApp : Application(){

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .firebaseModule(FirebaseModule())
                .build()
    }

    @Inject
    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate() {
        super.onCreate()

        appComponent.injectApp(this)

        Fabric.with(this, Crashlytics())

        registerConnectivityManager()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /**
     * Registers Connectivity Manager for devcies on API level 21 and up*/
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun registerConnectivityManager(){
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkBuilder = NetworkRequest.Builder()

        // we need the network to be able to reach the internet
        networkBuilder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        connectivityManager.registerNetworkCallback(networkBuilder.build(),
                object : ConnectivityManager.NetworkCallback(){
                    override fun onAvailable(network: Network?) {
                        appComponent.networkSubject().onNext(true)
                        // sendBroadcast(createIntent(false))
                    }

                    override fun onLost(network: Network?) {
                        appComponent.networkSubject().onNext(false)

                        // send broadcast that the network is not reachable
                        // sendBroadcast(createIntent(true))
                    }

                    override fun onUnavailable() {
                        appComponent.networkSubject().onNext(false)
                    }
                })

    }

    private fun createIntent(noConnection : Boolean) : Intent {
        val intent = Intent()
        intent.action = "android.net.conn.CONNECTIVITY_CHANGE"
        intent.putExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, noConnection)
        return intent
    }

}