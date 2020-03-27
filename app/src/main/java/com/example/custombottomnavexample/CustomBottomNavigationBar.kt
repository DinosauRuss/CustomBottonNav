package com.example.custombottomnavexample

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomBottomNavigationBar: BottomNavigationView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setBackgroundColor(Color.TRANSPARENT)
    }

    private val mPath = Path()
    private val mPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = ContextCompat.getColor(context, R.color.colorOrange)
    }

    // Points to define custom curve
    private val mFirstCurveStartPoint = Point()
    private val mFirstCurveEndPoint = Point()
    private val mFirstCurveControlPoint1 = Point()
    private val mFirstCurveControlPoint2 = Point()
    private val mSecondCurveStartPoint = Point()
    private val mSecondCurveEndPoint = Point()
    private val mSecondCurveControlPoint1 = Point()
    private val mSecondCurveControlPoint2 = Point()

    private val FAB_RADIUS = 128/2   // 1/2 dimension of fab
    private var mBottomNavWidth = 0
    private var mBottonNavHeight = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mBottomNavWidth = w
        mBottonNavHeight = h

        // First curve
        // Start and end points
        mFirstCurveStartPoint.set((mBottomNavWidth / 2) - (FAB_RADIUS * 2) - (FAB_RADIUS / 3), 0 )
        mFirstCurveEndPoint.set((mBottomNavWidth/2), FAB_RADIUS + (FAB_RADIUS / 4))
        // Control points
        mFirstCurveControlPoint1.set(mFirstCurveStartPoint.x + FAB_RADIUS + (FAB_RADIUS / 4), mFirstCurveStartPoint.y)
        mFirstCurveControlPoint2.set(mFirstCurveEndPoint.x - (FAB_RADIUS * 2) + FAB_RADIUS, mFirstCurveEndPoint.y)

        // Second curve
        // Start and end points
        mSecondCurveStartPoint.set(mFirstCurveEndPoint.x, mFirstCurveEndPoint.y)
        mSecondCurveEndPoint.set((mBottomNavWidth / 2) + (FAB_RADIUS * 2) + (FAB_RADIUS / 3), 0)
        // Control points
        mSecondCurveControlPoint1.set(mSecondCurveStartPoint.x + (FAB_RADIUS * 2) - FAB_RADIUS, mSecondCurveStartPoint.y);
        mSecondCurveControlPoint2.set(mSecondCurveEndPoint.x - (FAB_RADIUS + (FAB_RADIUS / 4)), mSecondCurveEndPoint.y)

        // Draw the lines
        mPath.reset()
        mPath.moveTo(0f, 0f)
        mPath.lineTo(mFirstCurveStartPoint.x.toFloat(), mFirstCurveStartPoint.y.toFloat())
        mPath.cubicTo(
            mFirstCurveControlPoint1.x.toFloat(),
            mFirstCurveControlPoint1.y.toFloat(),
            mFirstCurveControlPoint2.x.toFloat(),
            mFirstCurveControlPoint2.y.toFloat(),
            mFirstCurveEndPoint.x.toFloat(),
            mFirstCurveEndPoint.y.toFloat()
        )
        mPath.cubicTo(
            mSecondCurveControlPoint1.x.toFloat(),
            mSecondCurveControlPoint1.y.toFloat(),
            mSecondCurveControlPoint2.x.toFloat(),
            mSecondCurveControlPoint2.y.toFloat(),
            mSecondCurveEndPoint.x.toFloat(),
            mSecondCurveEndPoint.y.toFloat()
        )
        mPath.lineTo(mBottomNavWidth.toFloat(), 0f)
        mPath.lineTo(mBottomNavWidth.toFloat(), mBottonNavHeight.toFloat())
        mPath.lineTo(0f, mBottonNavHeight.toFloat())
        mPath.close()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(mPath, mPaint)
    }
}
