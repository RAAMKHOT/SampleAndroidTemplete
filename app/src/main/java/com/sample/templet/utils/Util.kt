package com.sample.templet.utils

import android.R
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.util.DisplayMetrics
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.sample.templet.utils.local.DataClass
import com.sample.templet.utils.local.ThemeHelper


class Util {
    companion object {
        const val EEE_dd_MMM_hh_mm_aaa = "EEE, dd MMM hh:mm aaa"
        const val YYYY_MM_DD_HHMMSS = "yyyy-dd-MM HH:mm:ss"
        const val E_DD_MM_YY = "E-DD,MM,YY"

        /**
         * Shoe Alert Dialog
         *
         * @param message message
         */
        fun mAlertDialog(title: String?, message: String?, cntxt: Context) {
            try {
                val builder = AlertDialog.Builder(cntxt)
                // Set the dialog title
                if (is10InchTablet(cntxt as Activity)) {
                    val titleText = TextView(cntxt)
                    titleText.textSize = 24f
                    titleText.setPadding(50, 15, 0, 0)
                    titleText.text = title
                    builder.setCustomTitle(titleText)
                } else {
                    builder.setTitle(title)
                }
                builder.setMessage(message)
                    .setCancelable(false) // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    // Set the action buttons
                    .setPositiveButton("OK") { dialog, id -> // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialogR.string.alert_delete
                        dialog.dismiss()
                    }
                /**
                 * If running device is tablet then we increase ui text size.
                 */
                if (is10InchTablet(cntxt)) {
                    val alert = builder.create()
                    alert.setCancelable(false)
                    alert.show()
                    val textView = alert.findViewById<View>(R.id.message) as TextView?
                    textView!!.textSize = 24f
                    val pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
                    pbutton.textSize = 24f
                } else {
                    builder.create().show()
                }
            } catch (e: Exception) {
            }
        }

        fun mAlertDialog(
            title: String?,
            message: String?,
            context: Context,
            listener: (DialogInterface, Int) -> Unit
        ) {

            try {
                val builder = AlertDialog.Builder(context)
                // Set the dialog title
                if (is10InchTablet(context as Activity)) {
                    val titleText = TextView(context)
                    titleText.textSize = 24f
                    titleText.setPadding(50, 15, 0, 0)
                    titleText.text = title
                    builder.setCustomTitle(titleText)
                } else {
                    builder.setTitle(title)
                }
                builder.setMessage(message)
                    .setCancelable(false) // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    // Set the action buttons
                    .setPositiveButton("OK", listener)
                /**
                 * If running device is tablet then we increase ui text size.
                 */
                if (is10InchTablet(context)) {
                    val alert = builder.create()
                    alert.setCancelable(false)
                    alert.show()
                    val textView = alert.findViewById<View>(R.id.message) as TextView?
                    textView!!.textSize = 24f
                    val pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
                    pbutton.textSize = 24f
                    pbutton.setTextColor(ThemeHelper.getInstance()!!.getCurrentTheme(context))
                } else {
                    builder.create().show()
                }
            } catch (e: Exception) {
            }
        }


        @RequiresApi(Build.VERSION_CODES.M)
        fun mAlertDialog(
            title: String?,
            message: String?,
            context: Context,
            positiveListener: (DialogInterface, Int) -> Unit,
            negativeListener: (DialogInterface, Int) -> Unit
        ) {

            try {
                val builder = AlertDialog.Builder(context)
                // Set the dialog title
                if (is10InchTablet(context as Activity)) {
                    val titleText = TextView(context)
                    titleText.textSize = 24f
                    titleText.setPadding(50, 15, 0, 0)
                    titleText.text = title
                    builder.setCustomTitle(titleText)
                } else {
                    builder.setTitle(title)
                }
                builder.setMessage(message)
                    .setCancelable(false) // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    // Set the action buttons
                    .setPositiveButton("OK", positiveListener)
                    .setNegativeButton("Cancel", negativeListener)
                /**
                 * If running device is tablet then we increase ui text size.
                 */
                if (is10InchTablet(context)) {
                    val alert = builder.create()
                    alert.setCancelable(false)
                    alert.show()
                    val textView = alert.findViewById<View>(R.id.message) as TextView?
                    textView!!.textSize = 24f
                    val pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
                    pbutton.textSize = 24f
                    pbutton.setTextColor(ThemeHelper.getInstance()!!.getCurrentTheme(context))
                } else {
                    builder.create().show()
                }
            } catch (e: Exception) {
            }
        }

        fun isTablet(context: Context): Boolean {
            val xlarge =
                context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == 4
            val large =
                context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
            return xlarge || large
        }


        fun is10InchTabletLenova(context: Activity): Boolean {
            return getScreenWidth(context) == 800f
        }

        fun is10InchTablet(context: Activity): Boolean {
            return getScreenWidth(context) >= 720
        }

        fun is8InchTablet(context: Activity): Boolean {
            return getScreenWidth(context) >= 720 && getScreenWidth(context) < 800
        }

        fun is7InchTablet(context: Activity): Boolean {
            return getScreenWidth(context) >= 600 && getScreenWidth(context) < 720
        }

        private fun getScreenWidth(context: Activity): Float {
            val metrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(metrics)
            return Math.min(metrics.widthPixels, metrics.heightPixels) / metrics.density
        }

        fun hideKeyboard(view: View, context: Context) {
            val inputMethodManager =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun getBitMapFromBase64(encodedImage: String?): Bitmap? {
            if (encodedImage?.isEmpty() == false && encodedImage != null) {
                val splitStringArray =
                    if (encodedImage.contains(",") == true) encodedImage.split(",")
                        .get(1) else encodedImage
                //val encodedImageString = encodedImage?.replace("data:image/jpg;base64,","")
                val decodedString: ByteArray = Base64.decode(splitStringArray, Base64.DEFAULT)
                return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            } else {
                return null
            }
        }

        fun clearAppData() {
            //DataClass.getInstance()?.setLoginUser(null)
            DataClass.getInstance()?.setSchemaName(null)
            DataClass.getInstance()?.setAuthToken(null)
            //DataClass.getInstance()?.setmEccKey(null)
            DataClass.getInstance()?.setUserId(null)
            AppPreferences.instance()?.clearSharedPreferences()
            val db = ApplicationController.getInstance().getDB()
            if(db!!.isOpen){
                db.clearAllTables()
                db.close()
            }
        }

        fun disableCopyPasteOperations(editText: EditText) {
            try {
                editText.customSelectionActionModeCallback = object : ActionMode.Callback {
                    override fun onCreateActionMode(actionMode: ActionMode, menu: Menu): Boolean {
                        return false
                    }

                    override fun onPrepareActionMode(actionMode: ActionMode, menu: Menu): Boolean {
                        return false
                    }

                    override fun onActionItemClicked(
                        actionMode: ActionMode,
                        item: MenuItem
                    ): Boolean {
                        return false
                    }

                    override fun onDestroyActionMode(actionMode: ActionMode) {}
                }
                editText.setOnLongClickListener { true }
                editText.isLongClickable = false
                editText.setTextIsSelectable(false)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        fun setDisplayName(lastname: String?, firstname: String?): String? {
            var displayName = ""
            try {
                displayName =
                    if (lastname != null && !lastname.isEmpty()) {
                        "$lastname, $firstname"
                    } else if (lastname != null && !lastname.isEmpty()) {
                        lastname
                    } else if (firstname != null && !firstname.isEmpty()) {
                        firstname
                    } else {
                        ""
                    }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return displayName
        }
        }
}