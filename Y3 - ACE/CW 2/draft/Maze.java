import java.util.*;
public class Maze {

    public Maze(){
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        //[1]input integers: n and m
        String input = scanner.nextLine();
        int int_2 = 2;
        if(!isLegal(input,int_2)){
            System.out.print("You should only enter positive integer and space in legal format.");
            return;
        }
        List<Integer> list0 = getNum(input);
        int[] arr0 = new int[list0.size()];
        for(int i = 0;i < list0.size(); i++){
            arr0[i] = list0.get(i);
        }
        int n = arr0[0];//the number of columns of the grid: n
        int m = arr0[1];//the number of rows of the grid: m
        //*1*test, pls delete it later
        //System.out.println("n: "+n+"; m: "+m);
        int[][] maze = new int[m][n];
        //[2]input two pairs of integers (s1, s2) and (f1, f2)
        //indicating the positions of the start and the finish
        //where s1,f1 ∈ [1, n], s2,f2 ∈ [1, m]
        //and the positions are on the boundary of the grid
        String twoPairs = scanner.nextLine();
        if(!isLegalTwoPairs(twoPairs,n,m)){
            System.out.println("Please enter two pairs of int in legal format.");
            return;
        }
        List<Integer> list1 = getTwoPairs(twoPairs);
        int[] arr1 = new int[list1.size()];
        for(int i = 0;i < list1.size(); i++){
            arr1[i] = list1.get(i);
        }
        int s1 = arr1[0];
        int s2 = arr1[1];
        int f1 = arr1[2];
        int f2 = arr1[3];
        //*2*test, pls delete it later
        //System.out.println("(s1, s2): ("+s1+", "+s2+"); (f1, f2): ("+f1+", "+f2+")");
        //[3]input assignment of maze[][]
        //Assign values to each one-dimensional array in a two-dimensional array
        //给二维数组中的每一个一维数组赋值
        String inputMaze = "";
        for(int i = 0; i < m; i++){
            inputMaze = scanner.nextLine();
            if(!isLegal(inputMaze,n)){
                System.out.print("Please enter corresponding number of positive integer and space in legal format.");
                return;
            }
            List<Integer> row = getNum(inputMaze);
            for(int j = 0; j < n; j++){
                maze[i][j] = row.get(j);
                if(row.get(j)==0){
                    System.out.print("Please enter positive integer.");
                    return;
                }
            }
        }
        //*3*test, pls delete it later
        /*for (int i = 0; i < m; i++) {
            System.out.println(Arrays.toString(maze[i]));
        }*/
        //*4*test, pls delete it later
        //System.out.println("test: ("+s1+", "+s2+"): "+maze[s2-1][s1-1]+"; ("+f1+", "+f2+"): "+maze[f2-1][f1-1]);
        //[4]walking time calculation
        int time = 0;//total time
        //Dijkstra algorithm
        time = dijkstra(maze, s1, s2, f1, f2);
        System.out.println("total time is: "+time);
        return;
    }



    private static boolean isLegal(String s,int n){
        int count=1;
        for(int i = 0; i < s.length(); i++){
            char ch=s.charAt(i);
            if(!((ch>='0' && ch<='9') || ch==32)){
                return false;
            }
            if(ch==32){
                if(i==0 || i==s.length()-1){
                    return false;
                }else{
                    count++;
                }
            }
        }
        if(count!=n){
            return false;
        }
        return true;
    }


    private static List<Integer> getNum(String s){
        int num=0;
        int temp=0;
        String numStr="";
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);
            if(ch>='0' && ch<='9'){
                numStr=numStr+ch;
            }else if(ch==32){
                temp = Integer.parseInt(numStr);
                list.add(temp);
                numStr = "";
            }
        }
        temp=Integer.parseInt(numStr);
        list.add(temp);
        return list;
    }


    private static boolean isLegalTwoPairs(String s, int n, int m){//two pairs of integers (s1, s2) and (f1, f2)
        int count1 = 0;//count brackets pair: how many '(x,y)'
        int count2 = 0;//count integers in the barckets: (x,y,z)->count2=3
        boolean isNum = false;
        boolean isSpace = false;
        String numStr="";
        int temp = 0;
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < s.length(); i++){
            char ch=s.charAt(i);
            if(!((ch>='0' && ch<='9') || ch==32 || ch=='(' || ch==',' || ch==')')){
                return false;
            }
            if(i==0 && ch!='('){
                return false;
            }
            if(i==s.length()-1 && ch!=')'){
                return false;
            }
            if(ch=='('){//when ch is '('
                count2 = 0;
                isNum = true;
                isSpace = false;
            }
            if(ch>='0' && ch<='9'){//when ch is an int
                if(!isNum){
                    return false;
                }else if(isSpace){
                    return false;
                }
                numStr = numStr + ch;
            }
            if(ch==',' && isNum){//when ch is a comma
                if(isSpace){
                    return false;
                }
                count2++;
                isNum = false;//next ch should not be an int
                isSpace = true;//next ch should be a space
            }
            if(ch==32){//when ch is a space
                if(!isSpace) {
                    return false;
                }
                isNum = true;
                isSpace = false;
                if(numStr!=""){
                    temp = Integer.parseInt(numStr);
                    list.add(temp);
                    numStr = "";
                }
            }
            if(ch==')'){//when ch is ')'
                if(isNum && count2!=0){
                    count2++;
                    if(count2==2){
                        count1++;
                    }else{
                        return false;
                    }
                    temp = Integer.parseInt(numStr);
                    list.add(temp);
                    numStr = "";
                    if(list.get(0)<1||list.get(0)>n){
                        return false;
                    }
                    if(list.get(1)<1||list.get(1)>m){
                        return false;
                    }
                    if(!(list.get(0)==1 || list.get(0)==n || list.get(1)==1 || list.get(1)==m)){
                        return false;
                    }
                    list.remove(1);
                    list.remove(0);
                    isNum = false;
                    if(count1>0 && count1<2){
                        isSpace = true;
                    }else if(count1==2){
                        return true;
                    }
                }else{
                    return false;
                }
            }
        }
        if(isNum || isSpace){
            return false;
        }
        return false;
    }


    private static List<Integer> getTwoPairs(String s){//two pairs of integers (s1, s2) and (f1, f2)
        String numStr="";
        int temp = 0;
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < s.length(); i++){
            char ch=s.charAt(i);
            if(ch>='0' && ch<='9'){//when ch is an int
                numStr = numStr + ch;
            }
            if(ch==32 && numStr!=""){//when ch is a space
                temp = Integer.parseInt(numStr);
                list.add(temp);
                numStr = "";
            }
            if(ch==')'){//when ch is ')'
                temp = Integer.parseInt(numStr);
                list.add(temp);
                numStr = "";
            }
        }
        return list;
    }


    private static int dijkstra(int[][] maze, int s1, int s2, int f1, int f2){//input maze[][] and s1, s2, f1, f2
        int time = 0;//total time
        s1--;
        s2--;
        f1--;
        f2--;
        int x1 = 0;//for example, both of s1 and f1 are x1
        int x2 = 0;//for example, both of s2 and f2 are x2
        int l1 = maze[0].length;
        int l2 = maze.length;
        int size = l2*l1;//size is the total number of elements in the maze[][]
        List<Integer> allCell1 = new ArrayList<>();//include all the cells except the vertex cells
        List<Integer> allCell2 = new ArrayList<>();
        for(int i=0; i<l1; i++){
            for(int j=0; j<l2; j++){
                if(i==s1 && j==s2){
                    continue;
                }
                allCell1.add(i);
                allCell2.add(j);
            }
        }
        List<Integer> vertex1 = new ArrayList<>();//list of vertexes
        List<Integer> vertex2 = new ArrayList<>();
        vertex1.add(s1);//initial point
        vertex2.add(s2);
        int[][] dist = new int[l2][l1];//shortest time distance from initial cell to each cell
        int[] path2 = new int[l2];//the vertex cell that is closest
        int[] path1 = new int[l1];
        int[] one = new int[2];
        one[0] = -1;
        one[1] = 1;
        //first step: initialization
        for(int i=0; i<l2; i++){
            for(int j=0; j<l1; j++){
                dist[i][j] = 2147483647;//largest integer in java is: 2147483647
            }
        }
        for(int i=0; i<l2; i++){
            path2[i] = -1;
        }
        for(int i=0; i<l1; i++){
            path1[i] = -1;
        }
        for(int i=0; i<2; i++){
            if(s1+one[i]>=0 && s1+one[i]<l1){
                dist[s2][s1+one[i]] = edge(maze[s2][s1],maze[s2][s1+one[i]]);
                //System.out.println("dist["+s2+"]["+(s1+one[i])+"]: "+dist[s2][s1+one[i]]);//test
            }
        }
        for(int i=0; i<2; i++){
            if(s2+one[i]>=0 && s2+one[i]<l2){
                dist[s2+one[i]][s1] = edge(maze[s2][s1],maze[s2+one[i]][s1]);
                //System.out.println("dist["+(s2+one[i])+"]["+s1+"]: "+dist[s2+one[i]][s1]);//test
            }
        }
        //second step: loop
        int min = 2147483647;//record the shortest dist[][]
        int r1 = 0;
        int r2 = 0;
        int t1 = 0;
        int t2 = 0;
        int temp = 0;
        int count = 0;
        while(allCell1.size()!=0){
            //test
            count++;
            //System.out.println("while-loop" + count + " start");
            //start loop
            for(int i=0; i<allCell1.size(); i++){//find the shortest dist[][]
                if(dist[allCell2.get(i)][allCell1.get(i)] <= min){
                    r1 = allCell1.get(i);
                    r2 = allCell2.get(i);
                    min = dist[r2][r1];
                }
            }
            vertex1.add(r1);//add new cell which has the shortest dist[][] to the vertex list
            vertex2.add(r2);
            for(int i=0; i<allCell1.size(); i++){
                if(allCell1.get(i)==r1 && allCell2.get(i)==r2){//exclude traversed vertexes from allCell list
                    allCell1.remove(i);
                    allCell2.remove(i);
                }
            }
            r1 = 0;
            r2 = 0;
            min = 2147483647;
            for(int j=0; j<vertex1.size(); j++){//update dist[][]
                t1 = vertex1.get(j);
                t2 = vertex2.get(j);
                for(int k=0; k<2; k++){
                    if(t1+one[k]>=0 && t1+one[k]<l1){
                        for(int i=0; i<allCell1.size(); i++){
                            if(t1+one[k] == allCell1.get(i) && t2 == allCell2.get(i)){
                                temp = dist[t2][t1] + edge(maze[t2][t1],maze[t2][t1+one[k]]);
                                if(temp < dist[t2][t1+one[k]] && temp > 0){
                                    dist[t2][t1+one[k]] = temp;
                                }
                            }
                        }
                    }
                    if(t2+one[k]>=0 && t2+one[k]<l2){
                        for(int i=0; i<allCell1.size(); i++){
                            if(t1 == allCell1.get(i) && t2+one[k] == allCell2.get(i)){
                                temp = dist[t2][t1] + edge(maze[t2][t1],maze[t2+one[k]][t1]);
                                if(temp < dist[t2+one[k]][t1] && temp > 0){
                                    dist[t2+one[k]][t1] = temp;
                                }
                            }
                        }
                    }
                }
            }
            t1 = 0;
            t2 = 0;
            //next loop
            //test
            /*System.out.println("AllCell1.size(): " + allCell1.size());
            System.out.println("dist[f2][f1]: " + dist[f2][f1]);
            System.out.println("while-loop end");*/
        }
        /////////output/////////
        time = dist[f2][f1];
        return time;
    }


    private static int edge(int a, int b){//1 plus the cardinality of the difference between the integers
        int c = 0;
        if(a>b){
            c = 1+a-b;
        }else if(b>a){
            c = 1+b-a;
        }else if(a==b){
            c = 1;
        }
        return c;
    }

}