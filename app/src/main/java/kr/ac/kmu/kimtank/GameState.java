package kr.ac.kmu.kimtank;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kmu.kimtank.object.Castle;
import kr.ac.kmu.kimtank.object.Enemy_1;
import kr.ac.kmu.kimtank.object.Enemy_2;
import kr.ac.kmu.kimtank.object.Map;
import kr.ac.kmu.kimtank.object.Player;
import kr.ac.kmu.kimtank.object.PlayerMissile;


/**
 * Created by HP on 2018-05-08.
 */

public class GameState implements IState {

    public static final int MAP_WIDTH_SIZE = 22;
    public static final int MAP_HEIGHT_SIZE = 9;


    public Map m_map[][];          //  map
    public Player m_player;        //  player
    public Enemy_1 m_enemy;        //  enemy
    public Enemy_2 m_enemy2;
    public Castle m_castle;


    ArrayList<PlayerMissile> m_pmslist = new ArrayList<PlayerMissile>();


    public GameState() {
        AppManager.getInstance().m_gamestate = this;
    }

    @Override
    public void Init() {
        //  맵 생성 & 초기화
        CreateMap();

        //  플레이어 생성 & 초기화
        m_player = new Player();
        m_player.m_game_x = 1;
        m_player.m_game_y = 7;
        m_player.SetPosition(m_player.m_game_x * 80 + 80, m_player.m_game_y * 80 + 80);

        //  적 생성 & 초기화
        m_enemy = new Enemy_1();
        m_enemy.m_game_x = 8;
        m_enemy.m_game_y = 6;
        m_enemy.SetPosition(m_enemy.m_game_x * 80 + 80, m_enemy.m_game_y * 80 + 80);

        //  적 2생성 & 초기화
        m_enemy2 = new Enemy_2();
        m_enemy2.m_game_x = 15;
        m_enemy2.m_game_y = 5;
        m_enemy2.SetPosition(m_enemy2.m_game_x * 80 + 80, m_enemy2.m_game_y * 80 + 80);


        //  깃발 생성
        m_castle = new Castle();
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        //  맵 업데이트
        for (int j = 0; j < MAP_HEIGHT_SIZE; j++) {
            for (int i = 0; i < MAP_WIDTH_SIZE; i++) {
                m_map[j][i].Update();
            }
        }

        //  플레이어 미사일 업데이트
        for (int i = m_pmslist.size() - 1; i >= 0; i--) {
            m_pmslist.get(i).Update();
        }

        //  적 업데이트(플레이어의 x,y좌표를 통해 경로 탐색 후, 이동)
        m_enemy.Update(m_player.m_game_x, m_player.m_game_y);

        m_enemy2.Update();

        checkCollision();
/*
       //  player의 이전 좌표를 비교해서 움직였을때, 적의 경로 ( aStar 경로 계산 )
        if(player_prevXpos != m_player.m_game_x || player_prevYpos != m_player.m_game_y) {

            player_prevXpos = m_player.m_game_x;
            player_prevYpos = m_player.m_game_y;

            m_enemy.aStar(m_player.m_game_x, m_player.m_game_y);
        }
*/

    }

    @Override
    public void Render(Canvas canvas) {

        //  player rotation Btn
        Bitmap directionBitmap = BitmapFactory.decodeResource(AppManager.getInstance().getResources(), R.drawable.img_rotation);
        directionBitmap = Bitmap.createScaledBitmap(directionBitmap, 200, 200, false);
        canvas.drawBitmap(directionBitmap, 80, 850, null);


        //  player move Btn
        Bitmap moveBitmap = BitmapFactory.decodeResource(AppManager.getInstance().getResources(), R.drawable.img_move);
        moveBitmap = Bitmap.createScaledBitmap(moveBitmap, 200, 200, false);
        canvas.drawBitmap(moveBitmap, 1200, 850, null);

        //  player wall Btn
        Bitmap wallBitmap = BitmapFactory.decodeResource(AppManager.getInstance().getResources(), R.drawable.img_wall);
        wallBitmap = Bitmap.createScaledBitmap(wallBitmap, 200, 200, false);
        canvas.drawBitmap(wallBitmap, 1420, 850, null);

        //  player missile Btn
        Bitmap missileBitmap = BitmapFactory.decodeResource(AppManager.getInstance().getResources(), R.drawable.img_missile_btn);
        missileBitmap = Bitmap.createScaledBitmap(missileBitmap, 200, 200, false);
        canvas.drawBitmap(missileBitmap, 1640, 850, null);


        Paint p = new Paint();
        p.setTextSize(25);
        p.setColor(Color.WHITE);
        canvas.drawText("missile count : " + String.valueOf(m_pmslist.size()), 0, 30, p);


        //  맵 마다 F = G + H 표시
        Paint redPaint = new Paint();
        redPaint.setTextSize(25);
        redPaint.setColor(Color.RED);

        Paint bluePaint = new Paint();
        bluePaint.setTextSize(25);
        bluePaint.setColor(Color.BLUE);

        Paint greenPaint = new Paint();
        greenPaint.setTextSize(25);
        greenPaint.setColor(Color.GREEN);

        Paint blackPaint = new Paint();
        blackPaint.setTextSize(25);
        blackPaint.setColor(Color.BLACK);

        for (int j = 0; j < MAP_HEIGHT_SIZE; j++) {
            for (int i = 0; i < MAP_WIDTH_SIZE; i++) {
                m_map[j][i].Draw(canvas);

                //  비용 출력
                canvas.drawText(String.valueOf(m_map[j][i].F), m_map[j][i].m_x, m_map[j][i].m_y + 25, redPaint);
                canvas.drawText(String.valueOf(m_map[j][i].G), m_map[j][i].m_x, m_map[j][i].m_y + 80, bluePaint);
                canvas.drawText(String.valueOf(m_map[j][i].H), m_map[j][i].m_x + 35, m_map[j][i].m_y + 80, greenPaint);

                //  좌표 출력
                canvas.drawText(String.valueOf(m_map[j][i].m_game_x) +
                                "," +
                                String.valueOf(m_map[j][i].m_game_y)
                        , m_map[j][i].m_x, m_map[j][i].m_y + 50, blackPaint);


            }
        }

        m_player.Draw(canvas);

        m_enemy.Draw(canvas);

        m_enemy2.Draw(canvas);

        for (PlayerMissile pms : m_pmslist) {
            pms.Draw(canvas);
        }

        m_castle.Draw(canvas);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int m_x = (int) event.getX();
        int m_y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //  Rotation Btn : direction, bitmap_rotation
            if (m_x > 80 && m_x < 280 && m_y > 930 && m_y < 1130) {

                switch (m_player.m_direction) {
                    case Player.DIRECTiON_UP:
                        m_player.m_direction = Player.DIRECTiON_RIGHT;
                        break;
                    case Player.DIRECTiON_RIGHT:
                        m_player.m_direction = Player.DIRECTiON_BOTTOM;
                        break;
                    case Player.DIRECTiON_BOTTOM:
                        m_player.m_direction = Player.DIRECTiON_LEFT;
                        break;
                    case Player.DIRECTiON_LEFT:
                        m_player.m_direction = Player.DIRECTiON_UP;
                        break;
                }
                m_player.BitmapRotation();
                AppManager.getInstance().getGameview().invalidate();
            }

            //  Move Btn
            if (m_x > 1200 && m_x < 1400 && m_y > 930 && m_y < 1130) {

                //  게임 내 그리드 좌표를 구한다
                m_player.m_game_x = (m_player.m_x - 80) / 80;
                m_player.m_game_y = (m_player.m_y - 80) / 80;

                //  direction에 따라 이동할 맵이 비어있을때, 이동
                if (m_player.m_direction == Player.DIRECTiON_UP &&
                        m_map[m_player.m_game_y - 1][m_player.m_game_x].getType() == Map.MAP_STATE_EMPTY) {
                    m_player.Move();
                }
                if (m_player.m_direction == Player.DIRECTiON_BOTTOM &&
                        m_map[m_player.m_game_y + 1][m_player.m_game_x].getType() == Map.MAP_STATE_EMPTY) {
                    m_player.Move();
                }
                if (m_player.m_direction == Player.DIRECTiON_RIGHT &&
                        m_map[m_player.m_game_y][m_player.m_game_x + 1].getType() == Map.MAP_STATE_EMPTY) {
                    m_player.Move();
                }
                if (m_player.m_direction == Player.DIRECTiON_LEFT &&
                        m_map[m_player.m_game_y][m_player.m_game_x - 1].getType() == Map.MAP_STATE_EMPTY) {
                    m_player.Move();
                }

                AppManager.getInstance().getGameview().invalidate();
            }
            //  Wall Btn
            if (m_x > 1420 && m_x < 1620 && m_y > 930 && m_y < 1130) {
                //  게임 내 좌표를 구한다
                m_player.m_game_x = (m_player.m_x - 80) / 80;
                m_player.m_game_y = (m_player.m_y - 80) / 80;
                m_map[m_player.m_game_y][m_player.m_game_x].setType(Map.MAP_STATE_IN);
                AppManager.getInstance().getGameview().invalidate();

            }

            //  Missile Btn
            if (m_x > 1640 && m_x < 1820 && m_y > 930 && m_y < 1130) {
                PlayerMissile pm = new PlayerMissile(AppManager.getInstance().getBitmap(R.drawable.img_missile));
                pm.SetPosition(m_player.m_x, m_player.m_y);
                pm.SetDirection(m_player.m_direction);
                m_pmslist.add(pm);
            }


        }

        return true;
    }

    public void CreateMap() {

        //  맵 생성 및 초기화
        m_map = new Map[MAP_HEIGHT_SIZE][MAP_WIDTH_SIZE];
        for (int j = 0; j < MAP_HEIGHT_SIZE; j++) {
            for (int i = 0; i < MAP_WIDTH_SIZE; i++) {

                //  외곽벽 (MAP_STATE_OUT) 생성
                if (i == 0 || i == MAP_WIDTH_SIZE - 1
                        || j == 0 || j == MAP_HEIGHT_SIZE - 1) {
                    m_map[j][i] = new Map(Map.MAP_STATE_OUT);

                }
                //  내부 공간(MAP_STATE_EMPTY) 생성
                else {
                    m_map[j][i] = new Map(Map.MAP_STATE_EMPTY);
                }

                m_map[j][i].SetPosition(i * 80 + 80, j * 80 + 80);
                m_map[j][i].m_game_x = i;
                m_map[j][i].m_game_y = j;

            }
        }

        //  내부벽 (MAP_STATE_IN) 생성 - 나중에 파일 리드 형태로 읽기 가능.
        m_map[1][3].setType(Map.MAP_STATE_IN);
        m_map[2][3].setType(Map.MAP_STATE_IN);
        m_map[3][3].setType(Map.MAP_STATE_IN);
        m_map[4][2].setType(Map.MAP_STATE_IN);
        m_map[4][3].setType(Map.MAP_STATE_IN);

        m_map[2][1].setType(Map.MAP_STATE_IN);

        for(int i =1; i<4; i++){
            m_map[6][i].setType(Map.MAP_STATE_IN);

        }

        for (int i = 5; i < 12; i++) {
            m_map[2][i].setType(Map.MAP_STATE_IN);
        }
        m_map[3][11].setType(Map.MAP_STATE_IN);

        for (int i = 3; i < 8; i++) {
            m_map[i][5].setType(Map.MAP_STATE_IN);
        }

        for (int i = 8; i < 15; i++) {
            m_map[6][i].setType(Map.MAP_STATE_IN);
        }
        for (int i = 1; i < 7; i++) {
            m_map[i][14].setType(Map.MAP_STATE_IN);
        }

    }

    public void checkCollision() {

        for (int i = m_pmslist.size() - 1; i >= 0; i--) {
            for (int y = 0; y < MAP_HEIGHT_SIZE; y++) {
                for (int x = 0; x < MAP_WIDTH_SIZE; x++) {


                    if (CollisionManager.CheckBoxToBox(
                            m_pmslist.get(i).m_boundBox,
                            m_map[y][x].m_boundBox)) {
                        if (m_map[y][x].m_type == Map.MAP_STATE_OUT) {
                            m_pmslist.remove(i);
                        } else if (m_map[y][x].m_type == Map.MAP_STATE_IN) {
                            m_pmslist.remove(i);
                            m_map[y][x].m_type = Map.MAP_STATE_EMPTY;
                        }
                        return;
                    }


                }
            }
        }

        if (CollisionManager.CheckBoxToBox(m_player.m_boundBox, m_castle.m_boundBox)) {
            AppManager.getInstance().getGameview().ChangeGameState(new IntroState());
        }

        if (CollisionManager.CheckBoxToBox(m_player.m_boundBox, m_enemy.m_boundBox)) {
            AppManager.getInstance().getGameview().ChangeGameState(new IntroState());
        }

        if (CollisionManager.CheckBoxToBox(m_player.m_boundBox, m_enemy2.m_boundBox)) {
           // m_enemy2.m_state = Enemy_2.DIE_STATE;

            AppManager.getInstance().getGameview().ChangeGameState(new IntroState());
        }


    }


}
