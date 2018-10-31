package kr.ac.kmu.kimtank;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by HP on 2018-03-29.
 */

/*
*  맴버 변수 : bitmap, x,y 좌표
*  맴버 함수 : bitmap draw
*
* */
public class GraphicObject {
    public Bitmap m_bitmap;
    public int m_x;
    public int m_y;

    public GraphicObject(){
        super();
        m_x = 0;
        m_y = 0;
    }

    public GraphicObject(Bitmap _bitmap) {
        super();
        m_bitmap = _bitmap;
        m_x = 0;
        m_y = 0;
    }

    public void SetPosition(int x, int y) {
        m_x = x;
        m_y = y;
    }

    public int getWidth() { return m_bitmap.getWidth();}
    public int getHeight() { return m_bitmap.getHeight();}


    public int GetX() {
        return m_x;
    }

    public int GetY() {
        return m_y;
    }

    public void Draw(Canvas canvas) {
        canvas.drawBitmap(m_bitmap, m_x, m_y, null);
    }





}
