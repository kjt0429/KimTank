package kr.ac.kmu.kimtank;

/**
 * Created by HP on 2018-04-10.
 */

import android.graphics.Rect;

/**
 * 2D 게임에 충돌 처리 방식에는 바운드박스, 충돌구, 충돌점이 있다.
 * 여기에서는 바운드 박스를 이용
 */
public class CollisionManager {

    public static boolean CheckBoxToBox(Rect _rt1, Rect _rt2){
        if(_rt1.right > _rt2.left && _rt1.left < _rt2.right && _rt1.top < _rt2.bottom && _rt1.bottom> _rt2.top) {
            return true;
        }
        return false;
    }

}
