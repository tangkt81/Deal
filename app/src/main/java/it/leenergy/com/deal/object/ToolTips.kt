package it.leenergy.com.deal.`object`

import android.app.ActionBar
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import it.leenergy.com.deal.R
import kotlinx.android.synthetic.main.tooltips_layout.view.*
import java.util.logging.Handler
import java.util.logging.LogRecord

/**
 * Created by IT-LeEnergy on 04/01/2018.
 */
class ToolTips(context:Context) {
    private val context:Context
    private val popupWindow:PopupWindow
    private val contentView:View
    private val inflater:LayoutInflater
    internal val isTooltipShown:Boolean
        get() {
            if (popupWindow != null && popupWindow.isShowing())
                return true
            return false
        }
//    internal var handler:Handler = object:Handler() {
//        fun handleMessage(msg:android.os.Message) {
//            when (msg.what) {
//                MSG_DISMISS_TOOLTIP -> if (popupWindow != null && popupWindow.isShowing())
//                    popupWindow.dismiss()
//            }
//        }
//    }
    init{
        this.context = context
        popupWindow = PopupWindow(context)
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        contentView = inflater.inflate(R.layout.tooltips_layout, null)
    }
    internal fun showToolTip(anchor:View, msg:String) {
        popupWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT)
        popupWindow.setWidth(ActionBar.LayoutParams.WRAP_CONTENT)
        popupWindow.setOutsideTouchable(true)
        popupWindow.setTouchable(true)
        popupWindow.setFocusable(true)
        popupWindow.setBackgroundDrawable(BitmapDrawable())
        popupWindow.setContentView(contentView)
        val screen_pos = IntArray(2)
        // Get location of anchor view on screen
        anchor.getLocationOnScreen(screen_pos)
        // Get rect for anchor view
        val anchor_rect = Rect(screen_pos[0], screen_pos[1], (screen_pos[0] + anchor.getWidth()), screen_pos[1] + anchor.getHeight())
        // Call view measure to calculate how big your view should be.
        contentView.measure(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT)
        contentView.tooltip_text.text = msg
        val contentViewHeight = contentView.getMeasuredHeight()
        val contentViewWidth = contentView.getMeasuredWidth()
        // In this case , i dont need much calculation for x and y position of
        // tooltip
        // For cases if anchor is near screen border, you need to take care of
        // direction as well
        // to show left, right, above or below of anchor view
        val position_x = anchor_rect.centerX() - (contentViewWidth / 2)
        val position_y = anchor_rect.bottom - (anchor_rect.height() / 2)
        popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, position_x, position_y)
        // send message to handler to dismiss tipWindow after X milliseconds
        //handler.sendEmptyMessageDelayed(MSG_DISMISS_TOOLTIP, 4000)
    }
    internal fun dismissTooltip() {
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss()
    }
    companion object {
        private val MSG_DISMISS_TOOLTIP = 100
    }
}