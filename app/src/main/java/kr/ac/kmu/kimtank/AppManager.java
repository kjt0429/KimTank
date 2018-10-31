package kr.ac.kmu.kimtank;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by HP on 2018-03-29.
 */

public class AppManager {

    //  싱글톤
    private static AppManager s_instance;

    //  게임에서 사용할 서브시스템, 앱 관련 정보
    private GameView m_gameview;
    private Resources m_resources;

    public GameState m_gamestate;

    public GameView getGameview() {
        return m_gameview;
    }
    public void setGameview(GameView _gameview) {
        this.m_gameview = _gameview;
    }

    public Resources getResources() {
        return m_resources;
    }
    public void setResources(Resources _resources) {
        this.m_resources = _resources;
    }


    public static AppManager getInstance(){
        if(s_instance == null){
            s_instance = new AppManager();
        }
        return s_instance;
    }

    //  Resized Bitmap
    public Bitmap getBitmap(int r){
        return resizeBitmap(BitmapFactory.decodeResource(m_resources,r));
    }


    //  비트맵 리사이즈
    public Bitmap resizeBitmap(Bitmap original) {

        int resizeWidth = 80;

        double aspectRatio = (double) original.getHeight() / (double) original.getWidth();
        int targetHeight = (int) (resizeWidth * aspectRatio);
        Bitmap result = Bitmap.createScaledBitmap(original, resizeWidth, targetHeight, false);
        if (result != original) {
            original.recycle();
        }
        return result;
    }

}
