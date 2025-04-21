package com.example.manage.laundry.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.manage.laundry.R
import com.example.manage.laundry.service.dto.OrderNotification
import com.example.manage.laundry.service.dto.SystemNotification
import com.example.manage.laundry.ui.customer.CustomerActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject

@AndroidEntryPoint
class AppNotificationService : Service() {

    companion object {

        private const val FIREBASE_NOTIFICATION_KEY = "notifications"
        private const val FIREBASE_SYSTEM_NOTIFICATION_KEY = "system_notifications"

        private const val INVALID_CUSTOMER_ID = -1
        private var customerId: Int? = null
        const val EXTRA_CUSTOMER_ID: String = "customer_id"

        private const val CHANNEL_ID = "laundry_customer_channel"
        private const val NOTIFICATION_ID = 1001

        // Intent actions
        const val ACTION_START_SERVICE = "com.example.manage.laundry.START_NOTIFICATION_SERVICE"
        const val ACTION_STOP_SERVICE = "com.example.manage.laundry.STOP_NOTIFICATION_SERVICE"
        const val ACTION_SHOW_NOTIFICATION = "com.example.manage.laundry.SHOW_NOTIFICATION"

        // Extra keys
        const val EXTRA_NOTIFICATION_TITLE = "notification_title"
        const val EXTRA_NOTIFICATION_MESSAGE = "notification_message"
    }

    private val serviceScope = CoroutineScope(Dispatchers.Default + Job())
    private var isServiceRunning = false
    private var orderNotificationListener: ValueEventListener? = null
    private var systemNotificationListener: ValueEventListener? = null
    private var database: FirebaseDatabase? = null

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        // Initialize Firebase properly
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }
        database = FirebaseDatabase.getInstance()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val newCustomerId =
            intent?.getIntExtra(EXTRA_CUSTOMER_ID, INVALID_CUSTOMER_ID) ?: INVALID_CUSTOMER_ID
        when (intent?.action) {
            ACTION_START_SERVICE -> startNotificationService(newCustomerId)
            ACTION_STOP_SERVICE -> stopNotificationService()
            ACTION_SHOW_NOTIFICATION -> {
                val title = intent.getStringExtra(EXTRA_NOTIFICATION_TITLE) ?: "Laundry App"
                val message =
                    intent.getStringExtra(EXTRA_NOTIFICATION_MESSAGE) ?: "New notification"
                showNotification(title, message)
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        removeListeners()
        serviceScope.cancel()
        isServiceRunning = false
        super.onDestroy()
    }

    private fun startNotificationService(newCustomerId: Int) {
        if (newCustomerId == INVALID_CUSTOMER_ID) {
            stopSelf()
            return
        }

        customerId = newCustomerId

        if (!isServiceRunning) {
            isServiceRunning = true

            // Start as foreground service
            val notification = createNotification(
                "Laundry App Running",
                "Monitoring for updates for customer #$customerId"
            )
            startForeground(NOTIFICATION_ID, notification)

            // Start listening for notifications
            setupOrderNotificationsListener(newCustomerId)
            setupSystemNotificationsListener()
        }
    }

    private fun setupSystemNotificationsListener() {
        removeListeners()
        if (database == null) {
            database = FirebaseDatabase.getInstance()
        }
        val systemNotificationsRef = database?.reference
            ?.child(FIREBASE_SYSTEM_NOTIFICATION_KEY)
            ?: return
        systemNotificationListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                processSystemNotification(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }

        }
        systemNotificationsRef.addValueEventListener(systemNotificationListener!!)
    }

    private fun setupOrderNotificationsListener(customerId: Int) {
        // Remove existing listener if any
        removeListeners()

        // Check if database is initialized
        if (database == null) {
            database = FirebaseDatabase.getInstance()
        }

        // Setup new listener for OrderNotification
        val notificationsRef = database?.reference
            ?.child(FIREBASE_NOTIFICATION_KEY)
            ?.child(customerId.toString())
            ?: return

        orderNotificationListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                processOrderNotification(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        }

        notificationsRef.addValueEventListener(orderNotificationListener!!)
    }

    private fun processSystemNotification(snapshot: DataSnapshot) {
        try {
            val systemNotification = snapshot.getValue(SystemNotification::class.java)
            if (systemNotification != null) {
                val title = "System Notification"
                showNotification(title, systemNotification.message ?: "")

                snapshot.ref.setValue(null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun processOrderNotification(snapshot: DataSnapshot) {
        try {
            val orderNotification = snapshot.getValue(OrderNotification::class.java)
            if (orderNotification != null) {
                val title = "Order #${orderNotification.orderId}"
                showNotification(title, orderNotification.message ?: "")

                snapshot.ref.setValue(null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun removeListeners() {
        orderNotificationListener?.let { listener ->
            customerId?.let { id ->
                database?.reference
                    ?.child(FIREBASE_NOTIFICATION_KEY)
                    ?.child(id.toString())
                    ?.removeEventListener(listener)
            }
            orderNotificationListener = null
        }
        systemNotificationListener?.let { listener ->
            database?.reference
                ?.child(FIREBASE_SYSTEM_NOTIFICATION_KEY)
                ?.removeEventListener(listener)
            systemNotificationListener = null
        }
    }

    private fun stopNotificationService() {
        removeListeners()
        isServiceRunning = false
        stopForeground(true)
        stopSelf()
    }

    private fun showNotification(title: String, message: String) {
        val notification = createNotification(title, message)
        notificationManager.notify(
            NOTIFICATION_ID,
            notification
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Laundry Customer Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for laundry service updates"
                enableVibration(true)
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(title: String, message: String) =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.app_icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(createContentIntent())
            .build()

    private fun createContentIntent(): PendingIntent {
        val intent = Intent(this, CustomerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        return PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}