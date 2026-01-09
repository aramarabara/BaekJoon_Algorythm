import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        
        int[][] map = new int[n][m];
        int[][] dist = new int[n][m];
        int startX = -1, startY = -1;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                dist[i][j] = -1;
                
                if (map[i][j] == 2) {
                    startX = i;
                    startY = j;
                    dist[i][j] = 0;
                } else if (map[i][j] == 0) {
                    dist[i][j] = 0;
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {startX, startY});

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = curr[0] + dx[i];
                int ny = curr[1] + dy[i];
                
                if (nx >= 0 && nx < n && ny >= 0 && ny < m) {
                    if (dist[nx][ny] == -1 && map[nx][ny] == 1) {
                        dist[nx][ny] = dist[curr[0]][curr[1]] + 1;
                        queue.add(new int[] {nx, ny});
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(dist[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}