package com.hicoo.android_zxing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.hicoo.android_zxing.zxing.camera.CameraManager;
import com.hicoo.android_zxing.zxing.view.BaseViewfinderView;

/**
 * Created by ZhouZi on 2018/9/26.
 * time:16:34
 * ----------Dragon be here!----------/
 * 　　　┏┓　　 ┏┓
 * 　　┏┛┻━━━┛┻┓━━━
 * 　　┃　　　　　 ┃
 * 　　┃　　　━　  ┃
 * 　　┃　┳┛　┗┳
 * 　　┃　　　　　 ┃
 * 　　┃　　　┻　  ┃
 * 　　┃　　　　   ┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛━━━━━
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━━━━━━神兽出没━━━━━━━━━━━━━━
 */
public class ViewfinderView extends BaseViewfinderView {

    private Paint finderMaskPaint;
    private int measureedWidth;
    private int measureedHeight;
    private Paint linePaint;

    public ViewfinderView(Context context) {
        this(context, null);
    }

    public ViewfinderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewfinderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    int lineHeight = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(leftRect, finderMaskPaint);
        canvas.drawRect(topRect, finderMaskPaint);
        canvas.drawRect(rightRect, finderMaskPaint);
        canvas.drawRect(bottomRect, finderMaskPaint);

        //画框
        roi_box.setBounds(middleRect);
        roi_box.draw(canvas);
        if (lineHeight == 0)
            lineHeight = middleRect.top;

        canvas.drawLine(middleRect.left, lineHeight, middleRect.right, lineHeight, linePaint);

        lineHeight += 3;

        if (lineHeight > middleRect.bottom)
            lineHeight = 0;

        postInvalidateDelayed(40, middleRect.left, middleRect.top, middleRect.right, middleRect.bottom);

    }

    private Rect topRect = new Rect();
    private Rect bottomRect = new Rect();
    private Rect rightRect = new Rect();
    private Rect leftRect = new Rect();
    private Rect middleRect = new Rect();
    private Drawable roi_box;

    private void init(Context context) {
        int finder_mask = Color.parseColor("#60000000");
        finderMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        finderMaskPaint.setColor(finder_mask);
        roi_box = context.getResources().getDrawable(R.mipmap.scavenging_paymentqrcodelri_saomiao);

        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.parseColor("#3CBD23"));
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(5);

    }

    //////////////新增该方法//////////////////////

    /**
     * 根据图片size求出矩形框在图片所在位置，tip：相机旋转90度以后，拍摄的图片是横着的，所有传递参数时，做了交换
     *
     * @param w
     * @param h
     * @return
     */
    public Rect getScanImageRect(int w, int h) {
        //先求出实际矩形
        Rect rect = new Rect();
        float tempw = w / (float) measureedWidth;
        float temph = h / (float) measureedHeight;
        rect.left = (int) (middleRect.left * tempw);
        rect.right = (int) (middleRect.right * tempw);
        rect.top = (int) (middleRect.top * temph);
        rect.bottom = (int) (middleRect.bottom * temph);
        return rect;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureedWidth = MeasureSpec.getSize(widthMeasureSpec);
        measureedHeight = MeasureSpec.getSize(heightMeasureSpec);
        int borderWidth = (int) getResources().getDimension(R.dimen.x400);
        int top = (int) getResources().getDimension(R.dimen.y333);
        middleRect.set((measureedWidth - borderWidth) / 2, top,
                (measureedWidth - borderWidth) / 2 + borderWidth, top + borderWidth);
        leftRect.set(0, middleRect.top, middleRect.left, middleRect.bottom);
        topRect.set(0, 0, measureedWidth, middleRect.top);
        rightRect.set(middleRect.right, middleRect.top, measureedWidth, middleRect.bottom);
        bottomRect.set(0, middleRect.bottom, measureedWidth, measureedHeight);
        CameraManager.FRAME_WIDTH = borderWidth;
        CameraManager.FRAME_HEIGHT = borderWidth;
        CameraManager.FRAME_MARGINTOP = top;
    }

}
