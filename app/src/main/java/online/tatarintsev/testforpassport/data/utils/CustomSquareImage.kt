package online.tatarintsev.testforpassport.data.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

class CustomSquareImage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredHeight = measuredHeight
        val desiredWidth = measuredWidth

        val width = min(desiredHeight, desiredWidth)

        setMeasuredDimension(width, width)
    }

}