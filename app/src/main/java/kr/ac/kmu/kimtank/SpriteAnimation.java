package kr.ac.kmu.kimtank;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by HP on 2018-04-03.
 */

public class SpriteAnimation extends GraphicObject {

    private Rect m_Rect;        //  그려줄 사각영역
    private int m_fps;          //  초당 프레임
    private int m_iFrames;      //  프레임 개수

    //  현재 프레임을 담는 멤버 변수
    private int m_CurrentFrame; //  최근 프레임
    private int m_SpriteWidth;
    private int m_SpriteHeight;

    private long m_FrameTimer;

    public SpriteAnimation(Bitmap _bitmap) {
        super(_bitmap);

        //  멤버변수 초기화
        m_Rect = new Rect(0,0,0,0);
        m_CurrentFrame = 0;
        m_FrameTimer = 0;
    }

    public void InitSpriteData(int _width, int _height, int _fps, int iFrames){
        m_SpriteWidth = _width;
        m_SpriteHeight = _height;
        m_Rect.top = 0;
        m_Rect.bottom = m_SpriteHeight;
        m_Rect.left = 0;
        m_Rect.right = m_SpriteWidth;
        m_fps = 1000/ _fps;             //  30프레임으로 그려주기 위한 계산식
        m_iFrames = iFrames;
    }


    @Override
    public void Draw(Canvas canvas) {
        //super.Draw(canvas);

            Rect dest = new Rect(m_x,m_y,m_x+m_SpriteWidth,m_y + m_SpriteHeight);
        canvas.drawBitmap(m_bitmap,m_Rect,dest,null);
    }

    public void Update(long GameTime){
        if (GameTime > m_FrameTimer + m_fps){
            m_FrameTimer = GameTime;
            m_CurrentFrame += 1;

           //   마지막 프레임을 그리고 다시 첫번째 프레임을 그림
            if(m_CurrentFrame >= m_iFrames){
                m_CurrentFrame = 0;
            }


        }
        m_Rect.left = m_CurrentFrame * m_SpriteWidth;
        m_Rect.right = m_Rect.left + m_SpriteWidth;
    }
}
