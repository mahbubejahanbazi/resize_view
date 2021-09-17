package ir.mjahanbazi.resizeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class CustomView extends RelativeLayout {

    private final Bitmap imageCorner = BitmapFactory.decodeResource(getResources(), R.drawable.ic_resize);
    private final Bitmap imageLeft = BitmapFactory.decodeResource(getResources(), R.drawable.ic_left);
    private final Bitmap imageRight = BitmapFactory.decodeResource(getResources(), R.drawable.ic_right);
    private final Bitmap imageTop = BitmapFactory.decodeResource(getResources(), R.drawable.ic_top);
    private final Bitmap imageBottom = BitmapFactory.decodeResource(getResources(), R.drawable.ic_bottom);
    private Rect srcCorner = new Rect();
    private Rect srcLeft = new Rect();
    private Rect srcRight = new Rect();
    private Rect srcTop = new Rect();
    private Rect srcBottom = new Rect();
    private Rect rectTop= new Rect();
    private Rect rectBottom= new Rect();
    private Rect rectLeft= new Rect();
    private Rect rectRight= new Rect();
    private Rect rectCorner= new Rect();
    private final int rectSizeCorner = 150;
    private final int rectSizeWidth = 120;
    private final int rectSizeHeight = 80;

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOnTouchListener(new OnTouchListener() {
            int x = 0;
            int y = 0;
            LayoutParams params;
            boolean resize = false;
            boolean flagCorner = false;
            boolean flagLeft = false;
            boolean flagRight = false;
            boolean flagTop = false;
            boolean flagBottom = false;
            boolean flagDrag = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!resize) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    if (rectCorner.contains(x, y)) {
                        flagCorner = true;
                    } else if (rectLeft.contains(x, y)) {
                        flagLeft = true;
                    } else if (rectRight.contains(x, y)) {
                        flagRight = true;
                    } else if (rectTop.contains(x, y)) {
                        flagTop = true;
                    } else if (rectBottom.contains(x, y)) {
                        flagBottom = true;
                    } else {
                        flagDrag = true;
                    }
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        resize = true;
                        x = (int) event.getRawX();
                        y = (int) event.getRawY();
                        params = (LayoutParams) CustomView.this.getLayoutParams();
                        break;
                    case MotionEvent.ACTION_UP:
                        resize = false;
                        flagLeft = false;
                        flagRight = false;
                        flagTop = false;
                        flagBottom = false;
                        flagCorner = false;
                        flagDrag = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!resize) {
                            break;
                        }
                        int newx = (int) event.getRawX();
                        int newy = (int) event.getRawY();
                        if (flagCorner) {
                            resizeCorner(x, y, newx, newy, params);
                        } else if (flagTop) {
                            resizeTop(x, y, newx, newy, params);
                        } else if (flagBottom) {
                            resizeBottom(x, y, newx, newy, params);
                        } else if (flagRight) {
                            resizeRight(x, y, newx, newy, params);
                        } else if (flagLeft) {
                            resizeLeft(x, y, newx, newy, params);
                        } else if (flagDrag) {
                            resizeDrag(x, y, newx, newy, params);
                        }
                        break;
                }
                return true;
            }

        });
    }

    private void resizeDrag(int x, int y, int newx, int newy, LayoutParams params) {
        CustomView.this.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT) {
                    {
                        setMargins(params.leftMargin + (newx - x),
                                params.topMargin + (newy - y),
                                params.rightMargin - (newx - x),
                                params.bottomMargin - (newy - y));
                    }
                });
    }

    private void resizeCorner(int x, int y, int newx, int newy, RelativeLayout.LayoutParams params) {
        CustomView.this.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT) {
                    {
                        setMargins(params.leftMargin,
                                params.topMargin + (newy - y),
                                params.rightMargin - (newx - x),
                                params.bottomMargin);
                    }
                });
    }

    private void resizeTop(int x, int y, int newx, int newy, RelativeLayout.LayoutParams params) {
        CustomView.this.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT) {
                    {
                        setMargins(params.leftMargin,
                                params.topMargin + (newy - y),
                                params.rightMargin,
                                params.bottomMargin);
                    }
                });
    }

    private void resizeLeft(int x, int y, int newx, int newy, RelativeLayout.LayoutParams params) {
        CustomView.this.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT) {
                    {
                        setMargins(params.leftMargin + (newx - x),
                                params.topMargin,
                                params.rightMargin,
                                params.bottomMargin);
                    }
                });
    }

    private void resizeRight(int x, int y, int newx, int newy, RelativeLayout.LayoutParams params) {
        CustomView.this.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT) {
                    {
                        setMargins(params.leftMargin,
                                params.topMargin,
                                params.rightMargin - (newx - x),
                                params.bottomMargin);
                    }
                });
    }

    private void resizeBottom(int x, int y, int newx, int newy, RelativeLayout.LayoutParams params) {
        CustomView.this.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT) {
                    {
                        setMargins(params.leftMargin,
                                params.topMargin,
                                params.rightMargin,
                                params.bottomMargin - (newy - y));
                    }
                });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        rectTop.set(w / 2 - rectSizeWidth / 2, 0, w / 2 + rectSizeWidth / 2, rectSizeHeight);
        rectBottom.set(w / 2 - rectSizeWidth / 2, h - rectSizeHeight, w / 2 + rectSizeWidth / 2, h);
        rectLeft.set(0, h / 2 - rectSizeWidth / 2, rectSizeHeight, h / 2 + rectSizeWidth / 2);
        rectRight.set(w - rectSizeHeight, h / 2 - rectSizeWidth / 2, w, h / 2 + rectSizeWidth / 2);
        rectCorner.set(w - rectSizeCorner, 0, w, rectSizeCorner);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        srcCorner.set(0, 0, imageCorner.getWidth(), imageCorner.getHeight());
        canvas.drawBitmap(imageCorner, srcCorner, rectCorner, null);
        srcLeft.set(0, 0, imageLeft.getWidth(), imageLeft.getHeight());
        canvas.drawBitmap(imageLeft, srcLeft, rectLeft, null);
        srcRight.set(0, 0, imageRight.getWidth(), imageRight.getHeight());
        canvas.drawBitmap(imageRight, srcRight, rectRight, null);
        srcTop.set(0, 0, imageTop.getWidth(), imageTop.getHeight());
        canvas.drawBitmap(imageTop, srcTop, rectTop, null);
        srcBottom.set(0, 0, imageBottom.getWidth(), imageBottom.getHeight());
        canvas.drawBitmap(imageBottom, srcBottom, rectBottom, null);

    }


}
