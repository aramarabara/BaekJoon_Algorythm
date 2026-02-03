import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        if (line == null) return;
        int N = Integer.parseInt(line.trim());

        // colMask[j] : j열의 상태를 나타내는 행 비트마스크
        // i번째 비트가 1이면 i행의 해당 열이 'T'
        int[] colMask = new int[N];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            if (s == null) s = "";
            s = s.trim();
            for (int j = 0; j < N; j++) {
                if (s.charAt(j) == 'T') {
                    colMask[j] |= (1 << i);
                }
            }
        }

        int limit = 1 << N; // 2^N
        int best = N * N;   // 상한값

        // 각 flip(행 뒤집기 조합)에 대해 tails를 XOR 방식으로 계산
        for (int flip = 0; flip < limit; flip++) {
            int sum = 0;
            for (int j = 0; j < N; j++) {
                int tails = Integer.bitCount(colMask[j] ^ flip);
                sum += Math.min(tails, N - tails);
            }
            if (sum < best) best = sum;
        }

        System.out.println(best);
    }
}
