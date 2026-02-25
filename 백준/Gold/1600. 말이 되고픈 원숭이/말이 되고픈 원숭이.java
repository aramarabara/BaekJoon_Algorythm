import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int K;          // 말처럼 이동할 수 있는 최대 횟수
    static int W, H;      // 가로(W) x 세로(H)
    static int[][] map;   // 0 = 빈칸, 1 = 장애물
    static int[][][] dist; // dist[y][x][horseUsed]

    static final int INF = 1_000_000_000;

    // 상태 (x, y, horseUsed)
    static class State {
        int x, y, k;
        State(int x, int y, int k) {
            this.x = x;
            this.y = y;
            this.k = k;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // K 입력
        K = Integer.parseInt(br.readLine().trim());

        // W, H 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken()); // 가로
        H = Integer.parseInt(st.nextToken()); // 세로

        // map / dist 초기화
        map = new int[H][W];
        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < W; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dist = new int[H][W][K + 1];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                for (int k = 0; k <= K; k++) {
                    dist[i][j][k] = -1;
                }
            }
        }

        System.out.println(bfs());
    }

    // 메서드분리 & 가독성 확보
    private static int bfs() {
        // (0, 0)에서 시작, 말 점프 사용 횟수 = 0
        Queue<State> q = new ArrayDeque<>();
        q.offer(new State(0, 0, 0));
        dist[0][0][0] = 0;

        // 원숭이 이동
        int[] dxMonkey = {1, -1, 0, 0};
        int[] dyMonkey = {0, 0, 1, -1};

        // 말이동, 횟수 소진되면 못함
        int[] dxHorse = {1, 2, 2, 1, -1, -2, -2, -1};
        int[] dyHorse = {-2, -1, 1, 2, 2, 1, -1, -2};

        while (!q.isEmpty()) {
            State cur = q.poll();
            int x = cur.x;
            int y = cur.y;
            int used = cur.k;
            int curDist = dist[y][x][used];

            // 도착점에 왔다면, 그 순간의 거리가 최단거리, 도착지점을 먼저 검증해야 함
            if (x == W - 1 && y == H - 1) return curDist;

            // 1. 원숭이 이동 (상하좌우)
            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dxMonkey[dir];
                int ny = y + dyMonkey[dir];

                if ((nx < 0 || nx >= W || ny < 0 || ny >= H)
                    || map[ny][nx] == 1 
                    || dist[ny][nx][used] != -1) continue; // 맵 범위 + 장애물 + 방문체크

                dist[ny][nx][used] = curDist + 1;
                q.offer(new State(nx, ny, used));
            }

            // 2. 말처럼 이동 (나이트), 아직 기회가 남았을 때만
            if (used < K) {
                int nextUsed = used + 1;
                for (int dir = 0; dir < 8; dir++) {
                    int nx = x + dxHorse[dir];
                    int ny = y + dyHorse[dir];

                    if (nx < 0 || nx >= W || ny < 0 || ny >= H) continue;
                    if (map[ny][nx] == 1) continue;
                    if (dist[ny][nx][nextUsed] != -1) continue;

                    dist[ny][nx][nextUsed] = curDist + 1;
                    q.offer(new State(nx, ny, nextUsed));
                }
            }
        }

        // 도착 불가능
        return -1;
    }
}