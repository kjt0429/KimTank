package kr.ac.kmu.kimtank;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by HP on 2018-03-27.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameViewThread m_thread;
    private IState m_state;


    //  기본 생성자
    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        m_thread = new GameViewThread(getHolder(), this);

        AppManager.getInstance().setGameview(this);
        AppManager.getInstance().setResources(getResources());

        ChangeGameState(new IntroState());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        m_state.onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        m_state.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void ChangeGameState(IState _state) {
        if (m_state != null)
            m_state.Destroy();
        _state.Init();
        m_state = _state;
    }

    public void Update() {
        m_state.Update();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        m_state.Render(canvas);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(m_thread.getState() == Thread.State.TERMINATED){
            m_thread = new GameViewThread(getHolder(),this);
        }

        m_thread.setRunning(true);
        m_thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        m_thread.setRunning(false);
        while (retry) {
            try {
                m_thread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }
}
