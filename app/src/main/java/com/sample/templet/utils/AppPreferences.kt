package com.sample.templet.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.security.GeneralSecurityException
import java.util.*

object AppPreferences {

    //---------------- SharedPreferences --------------
    private var applicationPref: SharedPreferences? = null
    private var mPreferences: SharedPreferences? = null
    private var mPreferencesGroup: SharedPreferences? = null
    private var mPreferencesRoom: SharedPreferences? = null
    private var mPreferencesChatProfile: SharedPreferences? = null
    private var mPreferencesChatHistry: SharedPreferences? = null
    private var mPreferencesChatList: SharedPreferences? = null
    private var mReconnectChats: SharedPreferences? = null

    //---------------- SharedPreferences Editor --------------
    private var mApplicationPref: SharedPreferences.Editor? = null
    private var mEditor: SharedPreferences.Editor? = null
    private var mEditorRoom: SharedPreferences.Editor? = null
    private var mEditorChatProfile: SharedPreferences.Editor? = null
    private var mEditorGroup: SharedPreferences.Editor? = null
    private var mEditorChatHistry: SharedPreferences.Editor? = null
    private var mEditorChatList: SharedPreferences.Editor? = null
    private var mEditorReconnectChats: SharedPreferences.Editor? = null

    private var instance: AppPreferences? = null


    //Captcha login APIs params
    const val CAPTCHA_ID = "captchaId"
    const val CAPTCHA_IMAGE = "captchaImage"
    const val CAPTCHA_STRING = "captchString"
    //App Theme
    const val APP_THEME_NAME = "themeName"
    const val APP_THEME_ID = "themeId"

    const val PIN = "pin"
    const val IS_PASSCODE = "ispasscode"
    const val IS_TIME_OUT = "istimeout"

    //Constant Values
    const val SHOWLOCK = "showlock"
    const val RMLOGEDIN = "RMLogedIn"
    const val REMEMBERUSERID = "RMUserZyter"
    const val REMEMBERCHECKED = "RMChecked"
    const val TERMSCHECKED = "TMChecked"
    const val REMEMBERWORD = "RMPassword"
    const val USERDETAIL = "RMUserDetial"
    const val LOGIN_RESPONSE = "LoginResponse"
    const val ECCKEYID = "eccKeyId"
    const val ECCKEY = "eccKey"
    const val USER_OFFLINE_STATUS = "userofflineStstus"

    const val USER_KEY = "UserKey"
    const val CONTACT_SETTING = "contactSettings"

    //PushNotification
    const val PUSHFLAG = "pushFlag"
    const val PUSH_P_COUNT = "pushPCount"
    const val PUSH_EMG_COUNT = "pushEmgCount"
    const val CHART_TYPE = "ChatType"
    const val ACTION = "Action"
    const val SERVER_CALL_TIME = "serverCallTime"
    const val IS_BACK_CALL = "isBackCall"

    const val BK_USER_ID = "bk_userIds"
    const val BK_USER_NAME = "bk_userName"
    const val BK_ACTION = "bk_action"
    const val BK_VIDEO = "bk_Video"
    const val BK_CALL_SENDER = "bk_CallSender"
    const val BK_NAME = "bk_name"
    const val DEFFERENT = "different"
    const val SER_GER_Time = "server_greater_time"
    const val IS_EVENT_TYPE = "isEventType"
    const val EVENT_TYPE = "EventType"
    const val IS_BACKGROUND_LOGOUT = "isBackgroundLogout"
    const val USER_STATUS = "userStstus"
    const val LOCAL_TOTAL_COUNT = "localtotalCount"
    const val PUSH_TOKEN = "pushToken"
    const val IS_CALL_PUSH_NOTIFY = "isCallPushNotify"

    const val SHOW_FINGERPRINT = "showfingerprint"
    const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    const val SELCET_REGION = "selectedRegion"
    const val SELCET_REGION_ID = "selectedRegionId"
    const val SELCET_LOCATION = "selectedLocation"
    const val ACCESS_TOKEN = "ACCESS_TOKEN"
    const val ACCOUNT_NAME = "accountName"
    const val CURRENT_THEME = "theme_current"
    const val EMR_PATIENT_ID = "emr_patient_id"
    const val EMR_HTML_DATA = "emr_html_data"
    const val EMR_TYPE_DATA = "emr_type_data"


    fun init(context: Context) {
        applicationPref = getEncryptedSharedPreferences("ApplicationPref", context.applicationContext)
        mPreferences = getEncryptedSharedPreferences("Message", context.applicationContext)
        mPreferencesGroup = getEncryptedSharedPreferences("GroupMessage", context.applicationContext)
        mPreferencesRoom = getEncryptedSharedPreferences("Room", context.applicationContext)
        mPreferencesChatProfile = getEncryptedSharedPreferences("ChatProfile", context.applicationContext)
        mPreferencesChatHistry = getEncryptedSharedPreferences("ChatHistry", context.applicationContext)
        mPreferencesChatList = getEncryptedSharedPreferences("ChatList", context.applicationContext)
        mReconnectChats = getEncryptedSharedPreferences("ReSent", context.applicationContext)

        mApplicationPref = applicationPref!!.edit()
        mEditor = mPreferences!!.edit()
        mEditorGroup = mPreferencesGroup!!.edit()
        mEditorRoom = mPreferencesRoom!!.edit()
        mEditorChatProfile = mPreferencesChatProfile!!.edit()
        mEditorChatHistry = mPreferencesChatHistry!!.edit()
        mEditorChatList = mPreferencesChatList!!.edit()
        mEditorReconnectChats = mReconnectChats!!.edit()
    }

    fun clearSharedPreferences(){
        mApplicationPref?.clear()?.apply()
        mEditor?.clear()?.apply()
        mEditorGroup?.clear()?.apply()
        mEditorRoom?.clear()?.apply()
        mEditorChatProfile?.clear()?.apply()
        mEditorChatHistry?.clear()?.apply()
        mEditorChatList?.clear()?.apply()
        mEditorReconnectChats?.clear()?.apply()
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    //GET SharedPreference
    fun getString(key: String?): String? {
        return applicationPref?.getString(key, "")
    }

    fun getString(key: String?, `val`: String?): String? {
        return applicationPref?.getString(key, `val`)
    }

    fun getInt(key: String?): Int {
        return applicationPref!!.getInt(key, 0)
    }

    fun getInt(key: String?, `val`: Int): Int {
        return applicationPref!!.getInt(key, `val`)
    }

    fun getFloat(key: String?): Float {
        return applicationPref!!.getFloat(key, 0f)
    }

    fun getFloat(key: String?, `val`: Float): Float {
        return applicationPref!!.getFloat(key, `val`)
    }

    fun getLong(key: String?): Long {
        return applicationPref!!.getLong(key, 0)
    }

    fun getLong(key: String?, `val`: Long): Long {
        return applicationPref!!.getLong(key, `val`)
    }

    open fun getBoolean(key: String?): Boolean {
        return applicationPref!!.getBoolean(key, false)
    }

    fun getBoolean(key: String?, flage: Boolean): Boolean {
        return applicationPref!!.getBoolean(key, flage)
    }

    fun getMsgAll(): Map<String?, *>? {
        return mPreferences?.all
    }

    fun getMsgString(key: String?): String? {
        return mPreferences?.getString(key, null)
    }

    class SecurePreferencesException(e: Throwable?) : RuntimeException(e)



    fun putString(key: String?, values: String?) {
        mApplicationPref!!.putString(key, values)
        commit(mApplicationPref!!)
    }

    fun putInt(key: String?, values: Int?) {
        if (values != null) {
            mApplicationPref!!.putInt(key, values)
        }
        commit(mApplicationPref!!)
    }

    fun putLong(key: String?, values: Long?) {
        if (values != null) {
            mApplicationPref!!.putLong(key, values)
        }
        commit(mApplicationPref!!)
    }

    fun putLong(key: String?, values: Float?) {
        if (values != null) {
            mApplicationPref!!.putFloat(key, values)
        }
        commit(mApplicationPref!!)
    }

    fun putBoolean(key: String?, values: Boolean?) {
        if (values != null) {
            mApplicationPref!!.putBoolean(key, values)
        }
        commit(mApplicationPref!!)
    }

    fun remove(key: String?) {
        mApplicationPref!!.remove(key)
    }

    fun clearAll() {
        mApplicationPref!!.clear()
    }

    fun commit(editor: SharedPreferences.Editor) {
        editor.commit()
    }

    fun commit() {
        putCommit(mEditor!!)
    }

    fun apply(editor: SharedPreferences.Editor) {
        editor.apply()
    }

    @Throws(SecurePreferencesException::class)
    fun putCommit(edit: SharedPreferences.Editor) {
        edit.commit()
    }

    /**
     * @param values Set Message
     */
    fun setMSG(key: String?, values: String?) {
        mEditor!!.putString(key, values)
        mEditor!!.commit()
    }

    /**
     * Delete message from key
     *
     * @param key
     */
    fun DeleteKey(key: String?) {
        mEditor!!.remove(key).commit()
    }

    //Group
    fun getMsgGroupAll(): Map<String?, *>? {
        return mPreferencesGroup?.all
    }

    fun getMsgGroupString(key: String?): String? {
        return mPreferencesGroup?.getString(key, null)
    }

    /**
     * @param values Set Message
     */
    fun setMSGGroup(key: String?, values: String?) {
        mEditorGroup!!.putString(key, values)
        mEditorGroup!!.commit()
    }

    /**
     * Delete message from key
     *
     * @param key
     */
    fun DeleteKeyGroup(key: String?) {
        mEditorGroup!!.remove(key).commit()
    }


    /**
     * save Roommsg from key
     *
     * @param key
     * @param values
     */
    fun setRoomMSG(key: String?, values: String?) {
        mEditorRoom!!.putString(key, values)
        mEditorRoom!!.commit()
    }

    /**
     * get All Room message
     *
     * @return
     */
    fun getMsgRoomAll(): Map<String?, *>? {
        return mPreferencesRoom?.all
    }

    /**
     * Get room message from key
     *
     * @param key
     * @return
     */
    fun getMsgRoomString(key: String?): String? {
        return mPreferencesRoom?.getString(key, null)
    }

    /**
     * Delete room message from key
     *
     * @param key
     */
    fun DeleteRoomKey(key: String?) {
        mEditorRoom!!.remove(key).commit()
    }

    /**
     * Delete Roommsg from key
     *
     * @param key
     * @param values
     */
    fun setMSGProfile(key: String?, values: String?) {
        mEditorChatProfile!!.putString(key, values)
        mEditorChatProfile!!.commit()
    }

    /**
     * get All Room message
     *
     * @return
     */
    fun getProfileAll(): Map<String?, *>? {
        return mPreferencesChatProfile?.all
    }

    /**
     * Get room message from key
     *
     * @param key
     * @return
     */
    fun getProfileString(key: String?): String? {
        return mPreferencesChatProfile?.getString(key, null)
    }

    /**
     * Delete Roommsg from key
     *
     * @param key
     * @param values
     */
    fun setHistry(key: String?, values: Int) {
        mEditorChatHistry!!.putInt(key, values)
        mEditorChatHistry!!.commit()
    }

    /**
     * get All Room message
     *
     * @return
     */
    fun getHistry(key: String?): Int {
        return mPreferencesChatHistry!!.getInt(key, 0)
    }



    /**
     * Delete room message from key
     *
     * @param key
     */
    fun DeleteProfileKey(key: String?) {
        mEditorChatProfile!!.remove(key).commit()
    }

    /**
     * Delete room message from key
     *
     * @param key
     */
    fun DeleteChateListKey(key: String?) {
        mEditorChatList!!.remove(key).commit()
    }


    /**
     * save Reconnect chats from key
     *
     * @param key
     * @param values
     */
    fun setReconnectChatsMSG(key: String?, values: JSONObject) {
        val gson = Gson()
        // myObject - instance of MyObject
        mEditorReconnectChats!!.putString(key, values.toString())
        mEditorReconnectChats!!.commit()
    }

    fun getReconnectAll(): MutableMap<String, *>? {
        return mReconnectChats?.all
    }

    /**
     * Get room message from key
     *
     * @param key
     * @return
     */
    fun getReconnectString(key: String?): JSONObject? {
        val gson = Gson()
        val json: String? = mReconnectChats?.getString(key, "")
        var obj: JSONObject? = null
        try {
            obj = JSONObject(json)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return obj
    }

    /**
     * Delete room message from key
     *
     * @param key
     */
    fun DeleteReconnectKey(key: String?) {
        mEditorReconnectChats!!.remove(key).commit()
    }

    /**
     * Clear all preferences
     */
    fun deletePreference() {
        try {
            mEditor!!.clear().commit()
            mEditorRoom!!.clear().commit()
            mEditorChatProfile!!.clear().commit()
            mEditorChatHistry!!.clear().commit()
            mEditorGroup!!.clear().commit()
            mEditorChatList!!.clear().commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @Synchronized
    fun instance(): AppPreferences? {
        if (instance == null) {
            instance = AppPreferences
        }
        return instance
    }

    private fun getEncryptedSharedPreferences(fileName: String, context: Context): SharedPreferences? {
        var sharedPreferences: SharedPreferences? = null
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val spec = KeyGenParameterSpec.Builder(
                    MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                    .build()

                val masterKey: MasterKey =
                    MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                        .setKeyGenParameterSpec(spec)
                        .build()

                /*sharedPreferences = EncryptedSharedPreferences.create(
                    fileName,
                    java.lang.String.valueOf(masterKey),
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )*/

                sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    fileName,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            }

        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sharedPreferences
    }
}


/*var firstRun: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = applicationPref!!.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)

        // custom setter to save a preference back to preferences file
        set(value) = applicationPref!!.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }*/