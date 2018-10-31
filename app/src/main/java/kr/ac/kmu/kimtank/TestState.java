package kr.ac.kmu.kimtank;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by HP on 2018-03-29.
 */

public class TestState implements IState {

 //   private GraphicObject m_Image;

    private SpriteAnimation m_spr;

    @Override
    public void Init() {
       // m_Image = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.android));

        m_spr = new SpriteAnimation(BitmapFactory.decodeResource(AppManager.getInstance().getResources(), R.drawable.walk));

        m_spr.InitSpriteData(m_spr.getWidth()/4,m_spr.getHeight(),5,4);

    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();
        m_spr.Update(GameTime);
    }

    @Override
    public void Render(Canvas canvas) {
      //  m_Image.Draw(canvas);

        m_spr.Draw(canvas);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
