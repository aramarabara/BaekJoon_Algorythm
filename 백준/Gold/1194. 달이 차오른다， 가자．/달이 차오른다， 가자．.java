import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static int rw, cl;
    static char[][] maps;
    static int[][][] dist;
    static int result;
    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        
        rw = Integer.parseInt(st.nextToken());
        cl = Integer.parseInt(st.nextToken());


        maps = new char[rw][cl];
        dist = new int[rw][cl][64];

        int startX = 0;
        int startY = 0;

        // 1. BFS용 두개의 맵을 작성, 이때, i,j는 맵이고 k가 열쇠상태의 개수, 즉 3차원 우주의 분기(맵을 갱신한다고 생각)
        for (int i = 0; i < rw; i++) {
            String line = br.readLine();
            for (int j = 0; j < cl; j++) {
                
                char location = line.charAt(j);

                if(location == '0') {
                    startX = i;
                    startY = j;
                    maps[i][j] = '.';
                    for (int k = 0; k < 64; k++) {
                        dist[startX][startY][k] = -1;    
                    }
                    dist[startX][startY][0] = 0;
                } else {
                    maps[i][j] = location;
                    for (int k = 0; k < 64; k++) {
                        dist[i][j][k] = -1;        
                    }
                }
            }
        }


        // 2. 탐색 for문 
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {startX, startY, 0});

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        result = 99999999;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = curr[0] + dx[i];
                int ny = curr[1] + dy[i];
                
                // 탐색 시 맵의 범위를 벗어나는 경우는 제외
                if (nx >= 0 && nx < rw && ny >= 0 && ny < cl) {

                    // 벽일 경우 탐색종료
                    if(maps[nx][ny] == '#') continue;

                    // 일반 탐색
                    if (maps[nx][ny] == '.' && dist[nx][ny][curr[2]] == -1) {
                        dist[nx][ny][curr[2]] = dist[curr[0]][curr[1]][curr[2]] + 1;
                        queue.add(new int[] {nx, ny, curr[2]});;
                    // 키를 얻을 경우 
                    } else if(maps[nx][ny] >= 'a' && maps[nx][ny] <= 'f'){
                        int nextKey = curr[2] | (1 << (maps[nx][ny] - 'a'));
                        if(dist[nx][ny][nextKey] == -1) {
                            dist[nx][ny][nextKey] = dist[curr[0]][curr[1]][curr[2]] + 1;
                            queue.add(new int[] {nx, ny, nextKey});
                        }
                    // 문을 통과할 경우 
                    } else if((maps[nx][ny] >= 'A' && maps[nx][ny] <= 'F') && dist[nx][ny][curr[2]] == -1) {
                        int doorBit = 1 << (maps[nx][ny] - 'A');
                        if((curr[2] & doorBit) != 0) {
                            dist[nx][ny][curr[2]] = dist[curr[0]][curr[1]][curr[2]] + 1;
                            queue.add(new int[] {nx, ny, curr[2]});;
                        }
                    } else if(maps[nx][ny] == '1') {
                        if(result > dist[curr[0]][curr[1]][curr[2]] + 1) {
                            result = dist[curr[0]][curr[1]][curr[2]] + 1;
                        };
                    }
                }   
            }
        }


        // 3. 최단거리 출력
        if(result == 99999999) {
            System.out.println(-1);
        } else {
            System.out.println(result);
        }

        
        
    }
}
