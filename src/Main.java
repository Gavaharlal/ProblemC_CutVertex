import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class Main {

    private static final int MAXN = 20_100;
    private static ArrayList<HashSet<Integer>> graph = new ArrayList<>();
    private static boolean[] used = new boolean[MAXN];
    private static int[] tin = new int[MAXN];
    private static int[] up = new int[MAXN];
    private static TreeSet<Integer> cutVx = new TreeSet<>();
    private static int timeCounter = 0;

    private static void getVertices(int n) {
        for (int i = 1; i <= n; i++) {
            if (!used[i]) {
                dfs(i, -1);
            }
        }
    }

    static void dfs(int v, int parent) {
        used[v] = true;
        tin[v] = up[v] = timeCounter++;
        int childes = 0;
        for (int to : graph.get(v)) {
            if (to == parent) continue;
            if (used[to]) {
                up[v] = Math.min(up[v], tin[to]);
            } else {
                dfs(to, v);
                up[v] = Math.min(up[v], up[to]);
                childes++;
                if (up[to] >= tin[v] && parent != -1) {
                    cutVx.add(v);
                }
            }
        }
        if (parent == -1 && childes > 1) {
            cutVx.add(v);
        }
    }

    public static void main(String[] args) throws IOException {
        int n, m;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        ) {
            String[] argus = in.readLine().split(" ");
            n = Integer.parseInt(argus[0]);
            m = Integer.parseInt(argus[1]);
            for (int i = 0; i <= n; i++) {
                graph.add(new HashSet<>());
            }
            for (int i = 0; i < m; i++) {
                argus = in.readLine().split(" ");
                int u = Integer.parseInt(argus[0]);
                int v = Integer.parseInt(argus[1]);
                graph.get(u).add(v);
                graph.get(v).add(u);
            }

            getVertices(n);
            out.write(cutVx.size() + "\n");
            for (int v : cutVx) {
                out.write(v + " ");
            }
        }
    }
}
