package kr.ac.kmu.kimtank.object;

import android.graphics.Rect;

import kr.ac.kmu.kimtank.AppManager;
import kr.ac.kmu.kimtank.GraphicObject;
import kr.ac.kmu.kimtank.R;

public class Castle extends GraphicObject {

    public int m_game_x;
    public int m_game_y;

    public Rect m_boundBox = new Rect();

    public Castle() {
        super();
        m_bitmap = AppManager.getInstance().getBitmap(R.drawable.img_castle);

        m_game_x = 20;
        m_game_y = 1;

        SetPosition(m_game_x * 80 + 80, m_game_y * 80 + 80);
        m_boundBox.set(m_x, m_y, m_x + 80, m_y + 80);
    }
}
