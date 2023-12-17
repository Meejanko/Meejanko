import java.util.*;
public class IdSum {

    //System.out.println("hello");
    //ascii 32 = ' '(space)
    public IdSum() {
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int n_2=2;
        int n_3=3;
        int count=0;
        int sum=0;
        if(!isLegal(input,n_2)){
            System.out.print("You should only enter num and space in legal format.");
            return;
        }
        List<Integer> list0=getNum(input);
        int[] arr0=new int[list0.size()];
        for(int i=0;i<list0.size();i++){
            arr0[i]=list0.get(i);
        }
        int n=arr0[0];//total number of students: n
        int m=arr0[1];//total number of operations to be performed: m
        int[] student=new int[n];
        for(int i=0;i<n;i++){
            student[i]=i+1;//1st-nth student array
            System.out.print(student[i]+" ");//////test
        }
        System.out.print("\n");//////test
        while(count < m){
            int o=0;//oab o - select operation(1-3)
            int a=0;//oab a
            int b=0;//oab b
            int k1=0;//use for switch
            int k2=0;//use for switch
            int[] student1=new int[student.length];
            for(int i = 0; i < student.length; i++) {
                student1[i] = student[i];
            }
            String opr = scanner.nextLine();
            if(!isLegal1(opr,n_3)){
                System.out.print("You should only enter num and space in legal format.");
                return;
            }
            List<Integer> list1=getNum(opr);
            o=list1.get(0);
            a=list1.get(1);
            b=list1.get(2);
            if(a>n||b>n){
                System.out.print("Out of range.");
                return;
            }
            for(int j1=0;j1<n;j1++){
                if(a==student[j1]){
                    k1=j1;
                }
                if(b==student[j1]){
                    k2=j1;
                }
            }
            if(o==1){
                //k1 to the left of k2
                if(k1<k2 && !(k1==k2-1)){
                    //k2 donnot move,k1=k2-1
                    student[k2-1]=student1[k1];
                    for(int j2=k1+1;j2<k2;j2++){
                        student[j2-1]=student1[j2];
                    }
                }else if(k1>k2){
                    //k1=k2,k2=k2+1
                    student[k2]=student1[k1];
                    student[k2+1]=student1[k2];
                    if(k2<n-2) {
                        for (int j2 = k2 + 2; j2 <= k1; j2++) {
                            student[j2] = student1[j2 - 1];
                        }
                    }
                }
            }else if(o==2){
                //k1 to the right of k2
                if(k1>k2 && !(k1==k2+1)){
                    //k2 donnot move,k1=k2+1
                    student[k2+1]=student1[k1];
                    if(k2<n-2)
                    for(int j2=k2+2;j2<k1+1;j2++){
                        student[j2]=student1[j2-1];
                    }
                }else if(k1<k2){
                    //k1=k2,k2=k2-1
                    student[k2]=student1[k1];
                    student[k2-1]=student1[k2];
                    if(k1<n-2) {
                        for (int j2 = k1; j2 < k2-1; j2++) {
                            student[j2] = student1[j2 + 1];
                        }
                    }
                }
            }else if(o==3){
                //k1=k2,k2=k1
                student[k2]=student1[k1];
                student[k1]=student1[k2];
            }else{
                return;
            }
            count++;
            for(int i=0;i<n;i++){
                //System.out.print(student[i]+" ");//////test
            }
            //System.out.print("\n");//////test
        }
        if(n>1) {
            for (int i = 1; i < n; i = i + 2) {
                sum = sum + student[i];
            }
        }
        //output the result (the sum of the ID numbers of the students at even positions in the resulting sequence)
        System.out.print(sum);
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


    private static boolean isLegal1(String s,int n){
        int count=0;
        if(s.length()!=5){
            return false;
        }
        for(int i = 0; i < 5; i++){
            char ch=s.charAt(i);
            if(!((ch>='0' && ch<='9') || ch==32)){
                return false;
            }
            if(ch>='0' && ch<='9'){
                count++;
            }
            if(ch==32){
                if(i==0||i==2||i==4){
                    return false;
                }
            }
        }
        if(count!=3){
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


}