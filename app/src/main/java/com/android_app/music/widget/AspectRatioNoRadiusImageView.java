package com.android_app.music.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.android_app.music.R;

public class AspectRatioNoRadiusImageView extends AppCompatImageView {

    // Các hằng số để xác định cách đo kích thước
    public static final int MEASUREMENT_WIDTH = 0;
    public static final int MEASUREMENT_HEIGHT = 1;

    // Giá trị mặc định cho tỷ lệ khung hình và cách tính kích thước
    private static final float DEFAULT_ASPECT_RATIO = 1f;
    private static final boolean DEFAULT_ASPECT_RATIO_ENABLED = false;
    private static final int DEFAULT_DOMINANT_MEASUREMENT = MEASUREMENT_WIDTH;

    // Các biến để lưu trữ tỷ lệ khung hình và các thuộc tính liên quan
    private float aspectRatio;
    private boolean aspectRatioEnabled;
    private int dominantMeasurement;

    // Constructors
    public AspectRatioNoRadiusImageView(Context context) {
        this(context, null);
    }

    public AspectRatioNoRadiusImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioNoRadiusImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadStateFromAttrs(attrs); // Load các thuộc tính từ AttributeSet
    }

    // Phương thức để load các thuộc tính từ AttributeSet
    @SuppressLint("CustomViewStyleable")
    private void loadStateFromAttrs(AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray a = null;
        try {
            // Load các thuộc tính từ tệp XML layout
            a = getContext().obtainStyledAttributes(attributeSet, R.styleable.AspectRatioView);
            aspectRatio = a.getFloat(R.styleable.AspectRatioView_aspectRatio, DEFAULT_ASPECT_RATIO);
            aspectRatioEnabled = a.getBoolean(R.styleable.AspectRatioView_aspectRatioEnabled,
                    DEFAULT_ASPECT_RATIO_ENABLED);
            dominantMeasurement = a.getInt(R.styleable.AspectRatioView_dominantMeasurement,
                    DEFAULT_DOMINANT_MEASUREMENT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    // Ghi đè phương thức onMeasure để tính toán lại kích thước của ImageView
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!aspectRatioEnabled) return;

        int newWidth;
        int newHeight;
        switch (dominantMeasurement) {
            case MEASUREMENT_WIDTH:
                newWidth = getMeasuredWidth();
                newHeight = (int) (newWidth * aspectRatio);
                break;

            case MEASUREMENT_HEIGHT:
                newHeight = getMeasuredHeight();
                newWidth = (int) (newHeight * aspectRatio);
                break;

            default:
                throw new IllegalStateException("Unknown measurement with ID " + dominantMeasurement);
        }

        setMeasuredDimension(newWidth, newHeight); // Set kích thước mới cho ImageView
    }
}
