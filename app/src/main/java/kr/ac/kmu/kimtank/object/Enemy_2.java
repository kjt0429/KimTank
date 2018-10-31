package kr.ac.kmu.kimtank.object;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

import kr.ac.kmu.kimtank.AppManager;
import kr.ac.kmu.kimtank.GameState;
import kr.ac.kmu.kimtank.R;

public class Enemy_2 extends Enemy {

    public static final int DIRECTiON_UP = 0;
    public static final int DIRECTiON_LEFT = 1;
    public static final int DIRECTiON_RIGHT = 2;
    public static final int DIRECTiON_BOTTOM = 3;

    public static final int NORMAL_STATE = 0;
    public static final int DIE_STATE = 1;


    public int m_direction;
    public int m_state;
    public Rect m_boundBox;
    public long lastTime;

    int ran;

    Map[][] map = new Map[GameState.MAP_HEIGHT_SIZE][GameState.MAP_WIDTH_SIZE];

    public Enemy_2() {
        super();

        m_bitmap = AppManager.getInstance().getBitmap(R.drawable.monster_2);


        m_direction = new Random().nextInt(4);
        m_state = NORMAL_STATE;
        m_boundBox = new Rect();
        lastTime = System.currentTimeMillis();

        //  GameState's 맵을 복사
        System.arraycopy(AppManager.getInstance().m_gamestate.m_map,
                0,
                map,
                0,
                AppManager.getInstance().m_gamestate.m_map.length);

    }

    public void Update() {
        int speed = 50;

        Log.d("Enemy_2_direction", String.valueOf(m_direction));

        if (System.currentTimeMillis() - lastTime > speed) {
            lastTime = System.currentTimeMillis();

            if (m_direction == DIRECTiON_UP && map[m_game_y - 1][m_game_x].m_type == Map.MAP_STATE_EMPTY) {
                Move();
            } else if (m_direction == DIRECTiON_BOTTOM && map[m_game_y + 1][m_game_x].m_type == Map.MAP_STATE_EMPTY) {
                Move();
            } else if (m_direction == DIRECTiON_LEFT && map[m_game_y][m_game_x - 1].m_type == Map.MAP_STATE_EMPTY) {
                Move();
            } else if (m_direction == DIRECTiON_RIGHT && map[m_game_y][m_game_x + 1].m_type == Map.MAP_STATE_EMPTY) {
                Move();
            } else {
                Log.d("Enemy_2", "충돌");
                ran = new Random().nextInt(4);
                m_direction = ran;
            }
        }
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

    @Override
    public void Draw(Canvas canvas) {
        if (m_state == NORMAL_STATE) {
            super.Draw(canvas);
        }
    }
}
