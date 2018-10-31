package kr.ac.kmu.kimtank.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

import kr.ac.kmu.kimtank.AppManager;
import kr.ac.kmu.kimtank.GameState;
import kr.ac.kmu.kimtank.R;

public class Enemy_1 extends Enemy {

    public Rect m_boundBox;

    Map[][] mMap = new Map[GameState.MAP_HEIGHT_SIZE][GameState.MAP_WIDTH_SIZE];

    Map pMap;

    PriorityQueue<Map> priorityQueue = new PriorityQueue<Map>();
    ArrayList<Map> closeList = new ArrayList<Map>();
    Stack<Map> routeStack = new Stack<>();

    long lastTime = System.currentTimeMillis();



    public Enemy_1() {
        super();
        m_boundBox = new Rect();

        m_bitmap = AppManager.getInstance().getBitmap(R.drawable.monster_1);


        //  GameState's 맵을 복사
        System.arraycopy(AppManager.getInstance().m_gamestate.m_map,
                0,
                mMap,
                0,
                AppManager.getInstance().m_gamestate.m_map.length);

    }



    //  xPos: 목표지점 x좌표  yPos: 목표지점 y좌표
    public void aStar(int xPos, int yPos) {
        int gValue;     //  뒤에 g값 비교 계산 때 들어감.

        for (int y = 0; y < GameState.MAP_HEIGHT_SIZE; y++) {
            for (int x = 0; x < GameState.MAP_WIDTH_SIZE; x++) {
                mMap[y][x].F = 999;
                mMap[y][x].G = 0;
                mMap[y][x].H = 0;

            }
        }
        priorityQueue.clear();
        closeList.clear();

        //  처음 적 위치를 openQueue에 넣어줌
        priorityQueue.offer(mMap[m_game_y][m_game_x]);

        while (true) {

            //  openQueue에서 F(전체 비용)이 가장 낮은 값을 빼서(기준이 됨), closeList에 저장
            pMap = priorityQueue.poll();
            closeList.add(pMap);

            //  pMap(기준)으로 8방향 체크
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    if (y == 0 && x == 0) continue; //  자기 자신(pMap)은 넘김

                    //  만약에 갈 수 있는 맵이고, closeList에 포함되지 않으면
                    if (mMap[pMap.m_game_y + y][pMap.m_game_x + x].m_type == Map.MAP_STATE_EMPTY
                            && !closeList.contains(mMap[pMap.m_game_y + y][pMap.m_game_x + x])) {

                        //  openQueue에 없는 경우 : 부모를 기준으로 삼고 G,H,F 각 비용을 계산후 openQueue에 담는다.
                        if (!priorityQueue.contains(mMap[pMap.m_game_y + y][pMap.m_game_x + x])) {
                            mMap[pMap.m_game_y + y][pMap.m_game_x + x].parent = pMap;
                            mMap[pMap.m_game_y + y][pMap.m_game_x + x].CalG();
                            mMap[pMap.m_game_y + y][pMap.m_game_x + x].CalH(mMap[yPos][xPos]);
                            mMap[pMap.m_game_y + y][pMap.m_game_x + x].CalF();
                            priorityQueue.offer(mMap[pMap.m_game_y + y][pMap.m_game_x + x]);
                        }

                        //  openQueue에 있는 경우 : G값을 비교후, 더 가까운 G값이 있으면 부모를 바꾸고, G,F 비용을 다시 계산
                        else if (priorityQueue.contains(mMap[pMap.m_game_y + y][pMap.m_game_x + x])) {

                            if (Math.abs(x) + Math.abs(y) == 2) gValue = 13;   //  대각선인 경우 비용 13 추가
                            else gValue = 10;                               //  상하좌우인 경우 비용 10 추가

                            //  g 값 비교
                            if (mMap[pMap.m_game_y + y][pMap.m_game_x + x].G >= pMap.G + gValue) {
                                mMap[pMap.m_game_y + y][pMap.m_game_x + x].parent = pMap;
                                mMap[pMap.m_game_y + y][pMap.m_game_x + x].CalG();
                                mMap[pMap.m_game_y + y][pMap.m_game_x + x].CalF();
                            }
                        }

                    }
                }   //  for : x
            }       //  for : y

            //  openQueue가 비어있거나(탐색실패), openQueue에 목표 지점이 들어온다면(탐색성공) 알고리즘 종료
            if (priorityQueue.size() == 0) {
                break;
            }
            if (priorityQueue.contains(mMap[yPos][xPos])) {

                /*Map map = mMap[yPos][xPos];

                while (map.parent != null) {
                    routeStack.push(map);
                    map = map.parent;
                }*/


                //  계산된 맵 정보에 따라 체크하며 한칸이동.
                Map map = mMap[yPos][xPos];
                while (map.parent.parent !=null){
                    map = map.parent;
                }
                Log.d("nextpos",String.valueOf(m_game_x)+","+String.valueOf(m_game_y));

                m_game_x = map.m_game_x;
                m_game_y = map.m_game_y;
                m_x = map.m_x;
                m_y = map.m_y;
                map.parent = null;

                m_boundBox.set(m_x,m_y,m_x+80,m_y+80);

                break;
            }

        }// while


    }


    @Override
    public void Draw(Canvas canvas) {
        super.Draw(canvas);

        Paint p = new Paint();
        p.setTextSize(25);
        p.setColor(Color.WHITE);

        Iterator iterator = priorityQueue.iterator();

        int z = 0;

        while (iterator.hasNext()) {
            Map map = (Map) iterator.next();
            canvas.drawText(String.valueOf(map.m_game_x) + "," + String.valueOf(map.m_game_y), z, 60, p);
            z += 50;
        }

    }

    public void Update(int x, int y) {
        int speed = 500;

        if (System.currentTimeMillis() - lastTime > speed) {
            lastTime = System.currentTimeMillis();

            aStar(x,y);
            /*if (!routeStack.empty()) {
                Map map = routeStack.pop();
                m_game_x = map.m_game_x;
                m_game_y = map.m_game_y;
                m_x = map.m_x;
                m_y = map.m_y;
            }*/

        }
    }
}
