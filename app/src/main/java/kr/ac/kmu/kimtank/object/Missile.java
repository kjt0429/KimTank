package kr.ac.kmu.kimtank.object;

import android.graphics.Bitmap;
import android.graphics.Rect;

import kr.ac.kmu.kimtank.GraphicObject;

public class Missile extends GraphicObject {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_OUT = 1;

    public static final int DIRECTiON_UP = 0;
    public static final int DIRECTiON_LEFT = 1;
    public static final int DIRECTiON_RIGHT = 2;
    public static final int DIRECTiON_BOTTOM = 3;

    public int m_state;
    public int m_direction;

    public Rect m_boundBox;

    public Missile(Bitmap _bitmap) {
        super(_bitmap);

        m_state = STATE_NORMAL;
        m_boundBox = new Rect();
    }

    public void SetDirection(int _direction) {
        m_direction = _direction;
    }

    public void Update() {
        switch (m_direction) {
            case DIRECTiON_UP:
                m_y -= 80;
                break;
            case DIRECTiON_BOTTOM:
                m_y += 80;
                break;
            case DIRECTiON_LEFT:
                m_x -= 80;
                break;
            case DIRECTiON_RIGHT:
                m_x += 80;
                break;
        }

        m_boundBox.set(m_x,m_y,m_x+80,m_y+80);
    }
}
