package kr.ac.kmu.kimtank;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by HP on 2018-03-27.
 */

public class GameViewThread extends Thread {
    private SurfaceHolder m_surfaceHolder;
    private GameView m_gameView;

    private boolean m_run = false;

    public GameViewThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        m_surfaceHolder = surfaceHolder;
        m_gameView = gameView;
    }

    public void setRunning(boolean run) {
        m_run = run;
    }

    @Override
    public void run() {
        super.run();

        Canvas _canvas;
        while (m_run) {
            _canvas = null;
            try {
                m_gameView.Update();
                //  SurfaceHolder를 통해 Surface에 접근해서 가져옴.
                _canvas = m_surfaceHolder.lockCanvas(null);
                synchronized (m_surfaceHolder) {
                    if(m_gameView != null) {
                        m_gameView.onDraw(_canvas);
                    }
                }
            } finally {
                if (_canvas != null)
                    //  Surface를 화면에 표시함
                    m_surfaceHolder.unlockCanvasAndPost(_canvas);
            }
        }
    }
}

