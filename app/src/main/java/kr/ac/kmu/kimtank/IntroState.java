package kr.ac.kmu.kimtank;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by HP on 2018-03-29.
 */

public class IntroState implements IState {

    private int m_x;
    private int m_y;

    @Override
    public void Init() {

    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        //  꽉차게 배경을 그림
        Rect dst = new Rect(0, 0, w, h);
        Bitmap bitmap = BitmapFactory.decodeResource(AppManager.getInstance().getResources(),R.drawable.img_intro);
        canvas.drawBitmap(bitmap, null, dst, null);


        Bitmap startBtn_bitmap = BitmapFactory.decodeResource(AppManager.getInstance().getResources(),R.drawable.ic_start);
        startBtn_bitmap = resizeBitmap(startBtn_bitmap);
        canvas.drawBitmap(startBtn_bitmap,1400,800,null);

        Bitmap exit_bitmap = BitmapFactory.decodeResource(AppManager.getInstance().getResources(),R.drawable.ic_exit);
        exit_bitmap = resizeBitmap(exit_bitmap);
        canvas.drawBitmap(exit_bitmap,1400,920,null);
    }

    public Bitmap resizeBitmap(Bitmap original) {

        int resizeWidth = 300;

        double aspectRatio = (double) original.getHeight() / (double) original.getWidth();
        int targetHeight = (int) (resizeWidth * aspectRatio);
        Bitmap result = Bitmap.createScaledBitmap(original, resizeWidth, targetHeight, false);
        if (result != original) {
            original.recycle();
        }
        return result;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        m_x = (int)event.getX();
        m_y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN){

            //  touch startBtn
            if(m_x > 1400 && m_x<1700 && m_y>800 && m_y < 900){
                AppManager.getInstance().getGameview().ChangeGameState(new GameState());
            }

            //  touch exitBtn
            else if(m_x > 1400 && m_x<1700 && m_y>920 && m_y < 1020){
                System.exit(0);
            }
        }

        return true;
    }
}
