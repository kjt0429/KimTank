package kr.ac.kmu.kimtank.object;

import android.graphics.Rect;
import android.support.annotation.NonNull;

import kr.ac.kmu.kimtank.AppManager;
import kr.ac.kmu.kimtank.GraphicObject;
import kr.ac.kmu.kimtank.R;

/**
 * Created by HP on 2018-05-08.
 */

/*  Map 내에 있는 그리드 공간 한개 한개를 Map Object로 생성
*
*   타입, 충돌박스, 비트맵 설정
* */
public class Map extends GraphicObject implements Comparable<Map>{

    //  벽 상태 상수 : 외곽(0),내부(1),비어있음(2)
    public static final int MAP_STATE_OUT = 0;
    public static final int MAP_STATE_IN = 1;
    public static final int MAP_STATE_EMPTY = 2;

    public int m_type;
    //  게임 내 좌표
    public int m_game_x;
    public int m_game_y;

    public Rect m_boundBox = new Rect();;

    public int F, G, H;
    public Map parent;

    public void CalF(){
        this.F = G + H;
    }

    public void CalG(){
        int val = Math.abs(parent.m_game_x - this.m_game_x) + Math.abs(parent.m_game_y - this.m_game_y);

        //  대각선인 경우
        if(val == 2){
            this.G = parent.G + 13;
        }
        //  상하좌우 경우
        else{
            this.G = parent.G + 10;
        }
    }
    //  휴리스틱 : 일단 멘하탄 (장애물 무시 상하 좌우이동 값으로)
    public void CalH(Map destination){
        this.H = (Math.abs(destination.m_game_x - this.m_game_x) + Math.abs(destination.m_game_y - this.m_game_y)) * 10;


    }


    @Override
    public int compareTo(@NonNull Map target) {
        if (this.F > target.F) {
            return 1;
        } else if (this.F < target.F) {
            return -1;
        }
        return 0;
    }

    public Map(int _type) {
        super();

        //  맵 타입 변수
        m_type = _type;

        //

        //  맵 타입에 따라 m_bitmap 설정
        int res = 0;
        switch(m_type){
            case MAP_STATE_OUT:
                res = R.drawable.img_wall_type0;
                break;
            case MAP_STATE_IN:
                res = R.drawable.img_wall_type1;
                break;
            case MAP_STATE_EMPTY:
                res = R.drawable.img_wall_type2;
                break;
        }
        m_bitmap = AppManager.getInstance().getBitmap(res);

        parent = null;
        F = 999;

    }


    //  나중에 상태값 달라지면 리소스 수정을 여기서 시키기
    public void Update() {
        //  맵 타입에 따라 m_bitmap 설정
        int res = 0;
        switch(m_type){
            case MAP_STATE_OUT:
                res = R.drawable.img_wall_type0;
                break;
            case MAP_STATE_IN:
                res = R.drawable.img_wall_type1;
                break;
            case MAP_STATE_EMPTY:
                res = R.drawable.img_wall_type2;
                break;
        }
        m_bitmap = AppManager.getInstance().getBitmap(res);

        m_boundBox.set(m_x,m_y,m_x+80,m_y+80);
    }

    public void setType(int _type){
        m_type = _type;
    }
    public int getType() {return m_type;}

}
