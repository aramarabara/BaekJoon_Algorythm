import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstLine = br.readLine();
        while (firstLine != null && firstLine.trim().isEmpty()) {
            firstLine = br.readLine();
        }
        if (firstLine == null) return;
        StringTokenizer st = new StringTokenizer(firstLine);
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] grid = new int[N][M];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            if (line == null) line = "";
            for (int j = 0; j < M; j++) {
                char c = (j < line.length()) ? line.charAt(j) : '0';
                grid[i][j] = c - '0';
            }
        }

        int total = N * M;
        int limit = 1 << total; // 2^(N*M)
        int maxSum = 0;
        for (int mask = 0; mask < limit; mask++) {
            int sum = computeSumForMask(mask, grid, N, M);
            if (sum > maxSum) maxSum = sum;
        }

        System.out.println(maxSum);
    }

    private static int computeSumForMask(int mask, int[][] grid, int N, int M) {

        int horizontalSum = 0;
        int verticalSum = 0;

        // 가로 조각 (mask 비트가 0인 칸)
        for (int i = 0; i < N; i++) {
            int number = 0;
            for (int j = 0; j < M; j++) {
                int k = i * M + j;
                if ((mask & (1 << k)) == 0) {
                    number = number * 10 + grid[i][j];
                } else {
                    horizontalSum += number;
                    number = 0;
                }
            }
            horizontalSum += number;
        }

        // 세로 조각 (mask 비트가 1인 칸)
        for (int j = 0; j < M; j++) {
            int number = 0;
            for (int i = 0; i < N; i++) {
                int k = i * M + j;
                if ((mask & (1 << k)) != 0) {
                    number = number * 10 + grid[i][j];
                } else {
                    verticalSum += number;
                    number = 0;
                }
            }
            verticalSum += number;
        }

        return horizontalSum + verticalSum;
    }
}
