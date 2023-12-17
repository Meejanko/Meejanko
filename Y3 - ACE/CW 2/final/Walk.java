import java.util.*;
public class Walk {

    public Walk() {
    }

    static ArrayList<ArrayList<Integer>> all = new ArrayList<>();//record all routes
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //[1]input integers: n and m
        String input = scanner.nextLine();
        int int_2 = 2;
        if (!isLegal(input, int_2)) {
            System.out.print("-1");//only enter positive integer and space in legal format
            return;
        }
        List<Integer> list0 = getNum(input);
        int[] arr0 = new int[list0.size()];
        for (int i = 0; i < list0.size(); i++) {
            arr0[i] = list0.get(i);
        }
        int n = arr0[0];//the number of columns of the grid: n
        int m = arr0[1];//the number of rows of the grid: m
        int[][] grid = new int[m][n];
        //[2]input two pairs of integers (s1, s2) and (e1, e2)
        String twoPairs = scanner.nextLine();
        if (!isLegalTwoPairs(twoPairs, n, m)) {
            System.out.print("-1");//should only enter two pairs of int in legal format
            return;
        }
        List<Integer> list1 = getTwoPairs(twoPairs);
        int[] arr1 = new int[list1.size()];
        for (int i = 0; i < list1.size(); i++) {
            arr1[i] = list1.get(i);
        }
        int s1 = arr1[0];
        int s2 = arr1[1];
        int e1 = arr1[2];
        int e2 = arr1[3];
        //[3]input a non-negative integer k: can ignore k block cells in one jump step
        String inputK = scanner.nextLine();
        int k = 0;
        if (!isNonNegaInt(inputK)) {
            System.out.print("-1");//should only enter a non-negative integer
            return;
        } else {
            k = Integer.parseInt(inputK);
        }
        //[4]input grid[][]
        //Assign values(only 0 or 1) to each one-dimensional array in a two-dimensional array
        String inputGrid = "";
        for (int i = 0; i < m; i++) {
            inputGrid = scanner.nextLine();
            if (!isLegalGrid(inputGrid, n)) {
                System.out.print("-1");//should only enter 0 or 1 and space in legal number and format
                return;
            }
            List<Integer> row = getNum(inputGrid);
            for (int j = 0; j < n; j++) {
                grid[i][j] = row.get(j);
                if (!(row.get(j) == 0 || row.get(j) == 1)) {
                    System.out.print("-1");//should only enter 0 or 1 as a cell
                    return;
                }
            }
        }
        if (grid[s2 - 1][s1 - 1] == 1 || grid[e2 - 1][e1 - 1] == 1) {
            System.out.print("-1");//yhe start cell must be a space cell
            return;
        }
        //[5]calculate the shortest step
        s1--;
        s2--;
        e1--;
        e2--;
        int type = 0; //record which direction to go
        int count = 0;//number of block cells in one step
        int minStep = -1;//initial minimal step
        //first: create ArrayList<Integer>step to record the steps
        ArrayList<Integer> step = new ArrayList<>();
        //use DFS search
        dfsFindWay(grid, s1, s2, e1, e2, k, step, type, count);
        //second: find minimum steps it takes
        int min = Integer.MAX_VALUE;//2147483647
        int idx = 0;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).size() < min) {
                min = all.get(i).size();
                idx = i;
            }
        }
        //third: output the minStep
        if(all.size()>0){
            step = all.get(idx);
            minStep = realStep(step, k);
        }
        System.out.print(minStep);//output minimal step
        all.clear();
        return;
    }

    private static boolean isLegal(String s, int n) {
        int count = 1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!((ch >= '0' && ch <= '9') || ch == 32)) {
                return false;
            }
            if (ch == 32) {
                if (i == 0 || i == s.length() - 1) {
                    return false;
                } else {
                    count++;
                }
            }
        }
        if (count != n) {
            return false;
        }
        return true;
    }

    private static List<Integer> getNum(String s) {
        int num = 0;
        int temp = 0;
        String numStr = "";
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                numStr = numStr + ch;
            } else if (ch == 32) {
                temp = Integer.parseInt(numStr);
                list.add(temp);
                numStr = "";
            }
        }
        temp = Integer.parseInt(numStr);
        list.add(temp);
        return list;
    }

    private static boolean isLegalTwoPairs(String s, int n, int m) {//two pairs of integers (s1, s2) and (f1, f2)
        int count1 = 0;//count brackets pair: how many '(x,y)'
        int count2 = 0;//count integers in the barckets: (x,y,z)->count2=3
        boolean isNum = false;
        boolean isSpace = false;
        String numStr = "";
        int temp = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!((ch >= '0' && ch <= '9') || ch == 32 || ch == '(' || ch == ',' || ch == ')')) {
                return false;
            }
            if (i == 0 && ch != '(') {
                return false;
            }
            if (i == s.length() - 1 && ch != ')') {
                return false;
            }
            if (ch == '(') {//when ch is '('
                count2 = 0;
                isNum = true;
                isSpace = false;
            }
            if (ch >= '0' && ch <= '9') {//when ch is an int
                if (!isNum) {
                    return false;
                } else if (isSpace) {
                    return false;
                }
                numStr = numStr + ch;
            }
            if (ch == ',' && isNum) {//when ch is a comma
                if (isSpace) {
                    return false;
                }
                count2++;
                isNum = false;//next ch should not be an int
                isSpace = true;//next ch should be a space
            }
            if (ch == 32) {//when ch is a space
                if (!isSpace) {
                    return false;
                }
                isNum = true;
                isSpace = false;
                if (numStr != "") {
                    temp = Integer.parseInt(numStr);
                    list.add(temp);
                    numStr = "";
                }
            }
            if (ch == ')') {//when ch is ')'
                if (isNum && count2 != 0) {
                    count2++;
                    if (count2 == 2) {
                        count1++;
                    } else {
                        return false;
                    }
                    temp = Integer.parseInt(numStr);
                    list.add(temp);
                    numStr = "";
                    if (list.get(0) < 1 || list.get(0) > n) {
                        return false;
                    }
                    if (list.get(1) < 1 || list.get(1) > m) {
                        return false;
                    }
                    list.remove(1);
                    list.remove(0);
                    isNum = false;
                    if (count1 > 0 && count1 < 2) {
                        isSpace = true;
                    } else if (count1 == 2) {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        if (isNum || isSpace) {
            return false;
        }
        return false;
    }

    private static List<Integer> getTwoPairs(String s) {//two pairs of integers (s1, s2) and (f1, f2)
        String numStr = "";
        int temp = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {//when ch is an int
                numStr = numStr + ch;
            }
            if (ch == 32 && numStr != "") {//when ch is a space
                temp = Integer.parseInt(numStr);
                list.add(temp);
                numStr = "";
            }
            if (ch == ')') {//when ch is ')'
                temp = Integer.parseInt(numStr);
                list.add(temp);
                numStr = "";
            }
        }
        return list;
    }

    private static boolean isLegalGrid(String s, int n) {
        int count = 1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!((ch == '0' || ch == '1') || ch == 32)) {
                return false;
            }
            if (ch == 32) {
                if (i == 0 || i == s.length() - 1) {
                    return false;
                } else if (!((s.charAt(i - 1) == '0' || s.charAt(i - 1) == '1') && (s.charAt(i + 1) == '0' || s.charAt(i + 1) == '1'))) {
                    return false;
                } else {
                    count++;
                }
            }
            if (ch == '0' || ch == '1') {
                if (!(i == 0 || i == s.length() - 1)) {
                    if (!(s.charAt(i - 1) == 32 && s.charAt(i + 1) == 32)) {
                        return false;
                    }
                }
            }
        }
        if (count != n) {
            return false;
        }
        return true;
    }

    private static boolean isNonNegaInt(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!(ch >= '0' && ch <= '9')) {
                return false;
            }
        }
        return true;
    }

    private static void dfsFindWay(int[][] grid, int s1, int s2, int e1, int e2, int k, ArrayList<Integer> step, int type, int count){
        int l1 = grid[0].length;
        int l2 = grid.length;
        if(s1<0 || s1>=l1 || s2<0 || s2>=l2 || grid[s2][s1] == 2){
            return;
        }
        if(s1==e1 && s2==e2){//find the end cell
            //add coordinate of the end cell
            step.add(s1+1);
            step.add(s2+1);
            all.add(new ArrayList<>(step));
            //backtrack
            step.remove(step.size() - 1);
            step.remove(step.size() - 1);
        }else{
            //dfs
            dfsType(grid, s1, s2, e1, e2, k, step, type, count);
        }
    }

    private static void dfsType(int[][] grid, int s1, int s2, int e1, int e2, int k, ArrayList<Integer> step, int type, int count){
        if(grid[s2][s1]==0){//this cell not been reached
            step.add(s1+1);
            step.add(s2+1);
            grid[s2][s1] = 2;
            if(type==0){
                dfsFindWay(grid, (s1 + 1), s2, e1, e2, k, step, type=1, count=0);//downward
                dfsFindWay(grid, s1, (s2 + 1), e1, e2, k, step, type=2, count=0);//right
                dfsFindWay(grid, (s1 - 1), s2, e1, e2, k, step, type=3, count=0);//upward
                dfsFindWay(grid, s1, (s2 - 1), e1, e2, k, step, type=4, count=0);//left
                dfsFindWay(grid, (s1 + 1), (s2 + 1), e1, e2, k, step, type=5, count=0);//lower right
                dfsFindWay(grid, (s1 - 1), (s2 + 1), e1, e2, k, step, type=6, count=0);//upper right
                dfsFindWay(grid, (s1 + 1), (s2 - 1), e1, e2, k, step, type=7, count=0);//lower left
                dfsFindWay(grid, (s1 - 1), (s2 - 1), e1, e2, k, step, type=8, count=0);//upper left
                grid[s2][s1] = 0;
                step.remove(step.size() - 1);
                step.remove(step.size() - 1);
            }else if(type==1){
                dfsFindWay(grid, (s1 + 1), s2, e1, e2, k, step, type=1, count);//downward
                dfsFindWay(grid, s1, (s2 + 1), e1, e2, k, step, type=2, count=0);//right
                dfsFindWay(grid, (s1 - 1), s2, e1, e2, k, step, type=3, count=0);//upward
                dfsFindWay(grid, s1, (s2 - 1), e1, e2, k, step, type=4, count=0);//left
                dfsFindWay(grid, (s1 + 1), (s2 + 1), e1, e2, k, step, type=5, count=0);//lower right
                dfsFindWay(grid, (s1 - 1), (s2 + 1), e1, e2, k, step, type=6, count=0);//upper right
                dfsFindWay(grid, (s1 + 1), (s2 - 1), e1, e2, k, step, type=7, count=0);//lower left
                dfsFindWay(grid, (s1 - 1), (s2 - 1), e1, e2, k, step, type=8, count=0);//upper left
                grid[s2][s1] = 0;
                step.remove(step.size() - 1);
                step.remove(step.size() - 1);
            }else if(type==2){
                dfsFindWay(grid, (s1 + 1), s2, e1, e2, k, step, type=1, count=0);//downward
                dfsFindWay(grid, s1, (s2 + 1), e1, e2, k, step, type=2, count);//right
                dfsFindWay(grid, (s1 - 1), s2, e1, e2, k, step, type=3, count=0);//upward
                dfsFindWay(grid, s1, (s2 - 1), e1, e2, k, step, type=4, count=0);//left
                dfsFindWay(grid, (s1 + 1), (s2 + 1), e1, e2, k, step, type=5, count=0);//lower right
                dfsFindWay(grid, (s1 - 1), (s2 + 1), e1, e2, k, step, type=6, count=0);//upper right
                dfsFindWay(grid, (s1 + 1), (s2 - 1), e1, e2, k, step, type=7, count=0);//lower left
                dfsFindWay(grid, (s1 - 1), (s2 - 1), e1, e2, k, step, type=8, count=0);//upper left
                grid[s2][s1] = 0;
                step.remove(step.size() - 1);
                step.remove(step.size() - 1);
            }else if(type==3){
                dfsFindWay(grid, (s1 + 1), s2, e1, e2, k, step, type=1, count=0);//downward
                dfsFindWay(grid, s1, (s2 + 1), e1, e2, k, step, type=2, count=0);//right
                dfsFindWay(grid, (s1 - 1), s2, e1, e2, k, step, type=3, count);//upward
                dfsFindWay(grid, s1, (s2 - 1), e1, e2, k, step, type=4, count=0);//left
                dfsFindWay(grid, (s1 + 1), (s2 + 1), e1, e2, k, step, type=5, count=0);//lower right
                dfsFindWay(grid, (s1 - 1), (s2 + 1), e1, e2, k, step, type=6, count=0);//upper right
                dfsFindWay(grid, (s1 + 1), (s2 - 1), e1, e2, k, step, type=7, count=0);//lower left
                dfsFindWay(grid, (s1 - 1), (s2 - 1), e1, e2, k, step, type=8, count=0);//upper left
                grid[s2][s1] = 0;
                step.remove(step.size() - 1);
                step.remove(step.size() - 1);
            }else if(type==4){
                dfsFindWay(grid, (s1 + 1), s2, e1, e2, k, step, type=1, count=0);//downward
                dfsFindWay(grid, s1, (s2 + 1), e1, e2, k, step, type=2, count=0);//right
                dfsFindWay(grid, (s1 - 1), s2, e1, e2, k, step, type=3, count=0);//upward
                dfsFindWay(grid, s1, (s2 - 1), e1, e2, k, step, type=4, count);//left
                dfsFindWay(grid, (s1 + 1), (s2 + 1), e1, e2, k, step, type=5, count=0);//lower right
                dfsFindWay(grid, (s1 - 1), (s2 + 1), e1, e2, k, step, type=6, count=0);//upper right
                dfsFindWay(grid, (s1 + 1), (s2 - 1), e1, e2, k, step, type=7, count=0);//lower left
                dfsFindWay(grid, (s1 - 1), (s2 - 1), e1, e2, k, step, type=8, count=0);//upper left
                grid[s2][s1] = 0;
                step.remove(step.size() - 1);
                step.remove(step.size() - 1);
            }else if(type==5){
                dfsFindWay(grid, (s1 + 1), s2, e1, e2, k, step, type=1, count=0);//downward
                dfsFindWay(grid, s1, (s2 + 1), e1, e2, k, step, type=2, count=0);//right
                dfsFindWay(grid, (s1 - 1), s2, e1, e2, k, step, type=3, count=0);//upward
                dfsFindWay(grid, s1, (s2 - 1), e1, e2, k, step, type=4, count=0);//left
                dfsFindWay(grid, (s1 + 1), (s2 + 1), e1, e2, k, step, type=5, count);//lower right
                dfsFindWay(grid, (s1 - 1), (s2 + 1), e1, e2, k, step, type=6, count=0);//upper right
                dfsFindWay(grid, (s1 + 1), (s2 - 1), e1, e2, k, step, type=7, count=0);//lower left
                dfsFindWay(grid, (s1 - 1), (s2 - 1), e1, e2, k, step, type=8, count=0);//upper left
                grid[s2][s1] = 0;
                step.remove(step.size() - 1);
                step.remove(step.size() - 1);
            }else if(type==6){
                dfsFindWay(grid, (s1 + 1), s2, e1, e2, k, step, type=1, count=0);//downward
                dfsFindWay(grid, s1, (s2 + 1), e1, e2, k, step, type=2, count=0);//right
                dfsFindWay(grid, (s1 - 1), s2, e1, e2, k, step, type=3, count=0);//upward
                dfsFindWay(grid, s1, (s2 - 1), e1, e2, k, step, type=4, count=0);//left
                dfsFindWay(grid, (s1 + 1), (s2 + 1), e1, e2, k, step, type=5, count=0);//lower right
                dfsFindWay(grid, (s1 - 1), (s2 + 1), e1, e2, k, step, type=6, count);//upper right
                dfsFindWay(grid, (s1 + 1), (s2 - 1), e1, e2, k, step, type=7, count=0);//lower left
                dfsFindWay(grid, (s1 - 1), (s2 - 1), e1, e2, k, step, type=8, count=0);//upper left
                grid[s2][s1] = 0;
                step.remove(step.size() - 1);
                step.remove(step.size() - 1);
            }else if(type==7){
                dfsFindWay(grid, (s1 + 1), s2, e1, e2, k, step, type=1, count=0);//downward
                dfsFindWay(grid, s1, (s2 + 1), e1, e2, k, step, type=2, count=0);//right
                dfsFindWay(grid, (s1 - 1), s2, e1, e2, k, step, type=3, count=0);//upward
                dfsFindWay(grid, s1, (s2 - 1), e1, e2, k, step, type=4, count=0);//left
                dfsFindWay(grid, (s1 + 1), (s2 + 1), e1, e2, k, step, type=5, count=0);//lower right
                dfsFindWay(grid, (s1 - 1), (s2 + 1), e1, e2, k, step, type=6, count=0);//upper right
                dfsFindWay(grid, (s1 + 1), (s2 - 1), e1, e2, k, step, type=7, count);//lower left
                dfsFindWay(grid, (s1 - 1), (s2 - 1), e1, e2, k, step, type=8, count=0);//upper left
                grid[s2][s1] = 0;
                step.remove(step.size() - 1);
                step.remove(step.size() - 1);
            }else if(type==8){
                dfsFindWay(grid, (s1 + 1), s2, e1, e2, k, step, type=1, count=0);//downward
                dfsFindWay(grid, s1, (s2 + 1), e1, e2, k, step, type=2, count=0);//right
                dfsFindWay(grid, (s1 - 1), s2, e1, e2, k, step, type=3, count=0);//upward
                dfsFindWay(grid, s1, (s2 - 1), e1, e2, k, step, type=4, count=0);//left
                dfsFindWay(grid, (s1 + 1), (s2 + 1), e1, e2, k, step, type=5, count=0);//lower right
                dfsFindWay(grid, (s1 - 1), (s2 + 1), e1, e2, k, step, type=6, count=0);//upper right
                dfsFindWay(grid, (s1 + 1), (s2 - 1), e1, e2, k, step, type=7, count=0);//lower left
                dfsFindWay(grid, (s1 - 1), (s2 - 1), e1, e2, k, step, type=8, count);//upper left
                grid[s2][s1] = 0;
                step.remove(step.size() - 1);
                step.remove(step.size() - 1);
            }
        }else if(grid[s2][s1]==1){//grid[s2][s1] equals to 1
            if(count+1 <= k){
                count++;
                step.add(s1+1);
                step.add(s2+1);
                grid[s2][s1] = 2;
                if(type==1){
                    dfsFindWay(grid, (s1+1), s2, e1, e2, k, step, type=1, count);//left
                }else if(type==2){
                    dfsFindWay(grid, s1, (s2 + 1), e1, e2, k, step, type=2, count);//right
                }else if(type==3){
                    dfsFindWay(grid, (s1 - 1), s2, e1, e2, k, step, type=3, count);//upward
                }else if(type==4){
                    dfsFindWay(grid, s1, (s2 - 1), e1, e2, k, step, type=4, count);//left
                }else if(type==5){
                    dfsFindWay(grid, (s1 + 1), (s2 + 1), e1, e2, k, step, type=5, count);//lower right
                }else if(type==6){
                    dfsFindWay(grid, (s1 - 1), (s2 + 1), e1, e2, k, step, type=6, count);//upper right
                }else if(type==7){
                    dfsFindWay(grid, (s1 + 1), (s2 - 1), e1, e2, k, step, type=7, count);//lower left
                }else if(type==8){
                    dfsFindWay(grid, (s1 - 1), (s2 - 1), e1, e2, k, step, type=8, count);//upper left
                }
                grid[s2][s1] = 1;
                step.remove(step.size() - 1);
                step.remove(step.size() - 1);
            }
        }
        return;
    }

    private static int realStep(ArrayList<Integer> step, int k){
        int minStep = -1;
        if(k>0){
            for (int i = 0; i < step.size(); i+=2) {
                minStep++;
                if(i>=2 && i<= step.size()-4){
                    if((step.get(i+3)-step.get(i+1) == step.get(i+1)-step.get(i-1)) && ((step.get(i+2)-step.get(i)) == (step.get(i)-step.get(i-2)))){
                        minStep--;
                    }
                }
            }
        }else if(k==0){
            for (int i = 0; i < step.size(); i+=2) {
                minStep++;
            }
        }
        return minStep;
    }

}