package kr.ac.kmu.kimtank;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by HP on 2018-03-29.
 */

public interface IState {

    public void Init();
    //  이 상태로 바뀌었을 때 실행할 것들

    public void Destroy();
    //  다른 상태로 바뀔 대 실행할 것들

    public void Update();
    //  지속적으로 수행할 것들

    public void Render(Canvas canvas);
    //  그려야 할 것들

    public boolean onKeyDown(int keyCode, KeyEvent event);
    //  키 입력 처리

    public boolean onTouchEvent(MotionEvent event);
    //  터치 입력 처리
}
