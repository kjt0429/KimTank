package kr.ac.kmu.kimtank.object;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;

import kr.ac.kmu.kimtank.AppManager;
import kr.ac.kmu.kimtank.GraphicObject;
import kr.ac.kmu.kimtank.R;

public class Player extends GraphicObject {

    public static final int DIRECTiON_UP = 0;
    public static final int DIRECTiON_LEFT = 1;
    public static final int DIRECTiON_RIGHT = 2;
    public static final int DIRECTiON_BOTTOM = 3;

    public int m_life;
    public int m_direction;
    public Rect m_boundBox;

    //  게임 내 좌표
    public int m_game_x;
    public int m_game_y;


    public Player() {
        super();
        m_life = 3;
        m_direction = DIRECTiON_UP;
        m_boundBox = new Rect();


        m_bitmap = AppManager.getInstance().getBitmap(R.drawable.img_player);
    }

    //  나중에 체크해서 방향이 바뀌면 방향 돌리는 함수 가동시키기

    public void BitmapRotation() {
        Matrix rotateMatrix = new Matrix();
        rotateMatrix.postRotate(90); //-360~360
        m_bitmap = Bitmap.createBitmap(m_bitmap, 0, 0,
                m_bitmap.getWidth(), m_bitmap.getHeight(), rotateMatrix, false);
    }

    public void Move() {


        switch (m_direction) {
            case DIRECTiON_UP:
                m_y -= 80;
                m_game_y--;
                break;
            case DIRECTiON_BOTTOM:
                m_y += 80;
                m_game_y++;
                break;

            case DIRECTiON_LEFT:
                m_x -= 80;
                m_game_x--;
                break;

            case DIRECTiON_RIGHT:
                m_x += 80;
                m_game_x++;
                break;
        }

        m_boundBox.set(m_x, m_y, m_x + 80, m_y + 80);

    }


}
