import java.util.*;
public class migongMinStep {

    //from CSDN: https://blog.csdn.net/m0_56651882/article/details/118638328?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522170161774716800186599030%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=170161774716800186599030&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-4-118638328-null-null.142^v96^pc_search_result_base7&utm_term=java%E8%B5%B0%E8%BF%B7%E5%AE%AB%E6%9C%80%E7%9F%AD%E8%B7%AF%E5%BE%84&spm=1018.2226.3001.4187

    public migongMinStep(){
    }

    static ArrayList<ArrayList<Integer>> res = new ArrayList<>();//找出最小值的关键：记录全部可能
    public static void main(String[] args) {
        //利用 Scanner 读入数据
        Scanner sc = new Scanner(System.in);
        //拥有多组数据(完成一个迷宫后可以继续输入下一个迷宫)
        while(sc.hasNextInt()){
            //读入列和行
            int n = sc.nextInt();
            int m = sc.nextInt();
            //读入数组
            int[][] maze = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    maze[i][j] = sc.nextInt();
                }
            }

            // 第一步
            //创建一个 ArrayList<Integer> step 记录步数
            ArrayList<Integer> step = new ArrayList<>();
            //调用 dfs 函数
            dfs(maze,0,0,n,m,step);

            // 第三步
            //得到结果 筛选最短路径
            int size = Integer.MAX_VALUE;//size等于Integer型能取到的最大值
            int index = 0;
            for (int i = 0; i < res.size(); i++) {
                if (res.get(i).size() < size) {
                    size = res.get(i).size();
                    index = i;
                }
            }

            //第三步
            //将最短路径保存并输出
            step = res.get(index);
            for (int i = 0; i < step.size(); i+=2) {
                System.out.println("(" + step.get(i) + "," + step.get(i+1) + ")");
            }

            //清空 res 继续下一个迷宫
            //这一步其实不仅仅是循环输入的时候用到
            //个人觉得清除后可以节省空间。
            res.clear();
        }
    }

    public static void dfs(int[][] maze,int i,int j,int n,int m,ArrayList<Integer> step) {
        // 越界 、 有墙 、 已经走过
        if (i < 0 || i >= n || j < 0 || j >= m || maze[i][j] == 1 || maze[i][j] == 2) {
            return;
        }

        //如果到达终点
        if (i == n - 1 && j == m - 1) {
            //添加终点坐标
            step.add(i);
            step.add(j);
            res.add(new ArrayList<>(step));
            //回溯
            step.remove(step.size() - 1);
            step.remove(step.size() - 1);
        }
        //没有到达终点
        else {
            //添加当前坐标
            step.add(i);
            step.add(j);
            //标记为已经走过
            maze[i][j] = 2;
            // 递归
            dfs(maze, i + 1, j, n, m, step);
            dfs(maze, i, j + 1, n, m, step);
            dfs(maze, i - 1, j, n, m, step);
            dfs(maze, i, j - 1, n, m, step);
            // 回溯
            maze[i][j] = 0;
            step.remove(step.size() - 1);
            step.remove(step.size() - 1);
        }
    }

}