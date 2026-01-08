import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int M = Integer.parseInt(br.readLine());
        int S = 0; 

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String op = st.nextToken();
            int x;

            switch (op) {
                case "add":
                    x = Integer.parseInt(st.nextToken());
                    S |= (1 << (x - 1));
                    break;
                case "remove":
                    x = Integer.parseInt(st.nextToken());
                    S &= ~(1 << (x - 1));
                    break;
                case "check":
                    x = Integer.parseInt(st.nextToken());
                    sb.append((S & (1 << (x - 1))) != 0 ? 1 : 0).append("\n");
                    break;
                case "toggle":
                    x = Integer.parseInt(st.nextToken());
                    S ^= (1 << (x - 1));
                    break;
                case "all":
                    S = (1 << 20) - 1;
                    break;
                case "empty":
                    S = 0;
                    break;
            }
        }
        System.out.print(sb.toString());
    }
}