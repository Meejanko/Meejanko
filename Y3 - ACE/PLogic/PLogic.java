import java.util.*;
public class PLogic {


    public PLogic(){
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean result = true;
        String formula0 = scanner.next(); // a formula in propositional logic
        if (!isLegal(formula0)){
            System.out.println("Error, you should enter legal propositional logic formula.");
            return;
        }
        Character[] arr = findLetter(formula0);
        for(int i = 0 ;i < arr.length; i++){
            int j=i+1;
            //System.out.println(j + "th proprsitional variable is: " + arr[i]);//仅供验证，可删
        }
        String numStr = scanner.next(); // (String)total number of propositional variables
        if (!isNumeric(numStr)){
            System.out.println("Error, you should enter an integer as the total number of propositional variables.");
            return;
        }
        int num = Integer.parseInt(numStr); // (Integer)total number of propositional variables
        if(num<0||num>200){
            System.out.println("The total number of propositional variables should be 0~200.");
            return;
        }else if(num>0&&num<=200){
            Boolean[] truthValue = new Boolean[num]; // truth value of propositional variable
            if(num!=arr.length){
                System.out.println("Please ensure the number you've entered equals to the number of propositional variables.");
                return;
            }
            for (int i = 0; i < num; i++) {
                String truthValueStr = scanner.next();
                if(!isBoolean(truthValueStr)){
                    System.out.println("Error, you should enter boolean.");
                    return;
                }else{
                    truthValue[i] = Boolean.valueOf(truthValueStr); //supply the value to the propositional variable
                    //System.out.println("truth value of " + arr[i] +": " + truthValue[i]); //test
                }
            }
            //calculate1
            result=calculate(formula0,arr,truthValue);
        }else if(num==0){
            //calculate2
            result=calculate(formula0);
        }
        //output result
        System.out.println("truth value: " + result);
        return;

    }





    private static boolean isNumeric(String s) { //judge if the content of string is int
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!(ch >= '0' && ch <= '9')) {
                return false;
            }
        }
            return true;
    }


    private static boolean isLegal(String s) { //propositional logic formula 命题公式 in legal form
        for (int i = 0; i < s.length(); i++) { 
            char ch = s.charAt(i);
            if (!(ch == 'T'||ch == 'F'||(ch >= 'a' && ch <= 'z')||ch == '~'||ch == '&'||ch == '|'||ch == '-'||ch == '>'||ch == '<'||ch == '('||ch == ')')) {//judge if the content of string is a-z(Lower case English letter)or ~&|-><()
                return false;
            }
            if(!(((i == 0)&&((ch == '~')||(ch == '(')||(ch == 'T')||(ch == 'F')||(ch >= 'a' && ch <= 'z')))||!(i == 0))){ //1st letter should not be anything excepts '~' or'(' or 'a'-'z'
                return false;
            }
            if(!(((i == s.length()-1)&&((ch == ')')||(ch == 'T')||(ch == 'F')||(ch >= 'a' && ch <= 'z')))||!(i == s.length()-1))){ //last letter should not be anything excepts ')' or 'a'-'z'
                return false;
            }
            if((i > 0)&&(i < (s.length()))){ //if i>0,i<length, (a-z,T,F) near to (a-z,T,F),return false(存在字母相邻，返回报错)
                if(((ch >= 'a' && ch <= 'z')||(ch == 'T')||(ch == 'F'))&&((s.charAt(i-1) >= 'a' && s.charAt(i-1) <= 'z')||s.charAt(i-1) == 'T'||s.charAt(i-1) == 'F')){
                    return false;
                }
            }
            if(i > 0 && i < (s.length()-1)){ //if i>0,i<length-1, char near to char, return false(存在符号相邻，返回报错)
                if((ch <= '&'||ch == '|')&&(s.charAt(i+1) == ')'||s.charAt(i-1) == '('||s.charAt(i-1) == '~' || s.charAt(i-1) == '&'|| s.charAt(i-1) == '|' || s.charAt(i-1) == '<' || s.charAt(i-1) == '>' || s.charAt(i-1) == '-')){
                    return false;
                }
            } 
            if(ch == '~' && i != 0 && i < s.length()-1){ //if the use of '~' is legal
                if((s.charAt(i-1) >= 'a' && s.charAt(i-1) <= 'z')||s.charAt(i-1) == 'T'||s.charAt(i-1) == 'F' || (s.charAt(i-1) == ')')){
                    return false;
                }
                if(!((s.charAt(i+1) >= 'a' && s.charAt(i+1) <= 'z') ||s.charAt(i+1) == 'T'||s.charAt(i+1) == 'F'|| s.charAt(i+1) == '(' || s.charAt(i+1) == '~')){
                    return false;
                }
            }
            
            if(ch == '<'){ //'<->'must be complete and legal
                if(i > (s.length()-4)){
                    return false;
                }else if(!((s.charAt(i+1) == '-')&&(s.charAt(i+2) == '>'))){
                    return false;
                }else if(!((s.charAt(i-1) >= 'a' && s.charAt(i-1) <= 'z' )||s.charAt(i-1) == 'T'||s.charAt(i-1) == 'F'|| s.charAt(i-1) == ')')){
                    return false;
                }else if(!((s.charAt(i+3) >= 'a' && s.charAt(i+3) <= 'z' )||s.charAt(i+3) == 'T'||s.charAt(i+3) == 'F'|| s.charAt(i+3) == '('|| s.charAt(i+3) == '~')){
                    return false;
                }
            }
            if((ch == '-') && !(s.charAt(i-1) == '<')){ //'->'must be complete and legal
                if(i > (s.length()-3)){
                    return false;
                }else if(!(s.charAt(i+1) == '>')){
                    return false;
                }else if(!((s.charAt(i-1) >= 'a' && s.charAt(i-1) <= 'z' )||s.charAt(i-1) == 'T'||s.charAt(i-1) == 'F'|| s.charAt(i-1) == ')')){
                    return false;
                }else if(!((s.charAt(i+2) >= 'a' && s.charAt(i+2) <= 'z')||s.charAt(i+2) == 'T'||s.charAt(i+2) == 'F'|| s.charAt(i+2) == '('|| s.charAt(i+2) == '~')){
                    return false;
                }
            }
        }
        if(!isLegalBrackets(s)){
            return false;
        }
        return true;
    }


    private static boolean isBoolean(String s) {
        return "true".equalsIgnoreCase(s) || "false".equalsIgnoreCase(s);
    }


    private static Character[] findLetter(String s){ //count the number of total propositional variables (no-repeat)
        List<Character> letter = new ArrayList<>();
        for (int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            if(ch >= 'a' && ch <= 'z'){
                if(letter.size() == 0){
                    letter.add(ch);
                }else if(!letter.contains(ch)){
                    letter.add(ch);
                }
            }  
        }
        Character[] arr = new Character[letter.size()];
        for(int i = 0; i < letter.size(); i++){
            arr[i] = letter.get(i);
        }
        return arr;
    }


    private static boolean isLegalBrackets(String s){ //if the use of '(' and ')' is correct
        int j=0;
        int k=0;
        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);
            if(ch=='('){
                if(i>0){
                    if((s.charAt(i-1)>='a' && s.charAt(i-1)<='z') || s.charAt(i-1)=='T' || s.charAt(i-1)=='F'){
                        return false;
                    }
                }
                j++;
            }
            if(ch==')'){
                k++;
                if(i>0){
                    char chAtFront=s.charAt(i-1);
                    if(chAtFront=='('){
                        return false;
                    }
                }
                if(i<s.length()-1){
                    if((s.charAt(i+1)>='a' && s.charAt(i+1)<='z') || s.charAt(i+1)=='T' || s.charAt(i+1)=='F'){
                        return false;
                    }
                }
            }
            if(j<k){
                return false;
            }
        }
        if(j!=k){
            return false;
        }
        return true;
    }


    private static int countChar(String s, Character c){ //count the number of char c in the string
        int count=0;
        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);
            if(ch==c){
                count++;
            }
        }
        return count;
    }


    private static boolean calculate(String s,Character[] arr1,Boolean[] arr2) { //prepare for calculate[have a-z]
        int j = 0;
        int k = 0;
        String s1 = s;
        String front = "";
        String back = "";
        char char1 = ')';
        while (!(s1.equals("T")||s1.equals("F"))) {
            int totalBracket=countChar(s1, char1); //count the total number of ')'
            if(totalBracket!=0) {
                for (int i = 0; i < s1.length(); i++) {
                    char ch = s1.charAt(i);
                    if (ch == '(') {
                        j = i;
                    }
                    if (ch == ')') {
                        k = i;
                    }
                    if (k != 0) {
                        if(j>0){
                            front = s1.substring(0,j);
                        }
                        if(k<s1.length()-2){
                            back = s1.substring(k+1,s1.length());
                        }
                        String s2 = s1.substring(j+1,k);
                        //calculate_1
                        String s3 = calculating_1(s2,arr1,arr2);
                        s1 = front+s3+back;
                        k = 0;
                    }
                }
            }else if (totalBracket==0){
                s1=calculating_1(s1,arr1,arr2);
            }
        }
        if(s1.equals("F")){
            return false;
        }
        return true;
    }


    private static boolean calculate(String s) { //prepare for calculate[no a-z]
        int j = 0;
        int k = 0;
        String s1 = s;
        String front = "";
        String back = "";
        char char1 = ')';
        while (!(s1.equals("T")||s1.equals("F"))) {
            int totalBracket=countChar(s1, char1); //count the total number of ')'
            if(totalBracket!=0) {
                for (int i = 0; i < s1.length(); i++) {
                    char ch = s1.charAt(i);
                    if (ch == '(') {
                        j = i;
                    }
                    if (ch == ')') {
                        k = i;
                    }
                    if (k != 0) {
                        if(j>0){
                            front = s1.substring(0,j);
                        }
                        if(k<s1.length()-2){
                            back = s1.substring(k+1,s1.length());
                        }
                        String s2 = s1.substring(j+1,k);
                        //calculate_2
                        String s3 = calculating_2(s2);
                        s1 = front+s3+back;
                        k = 0;
                    }
                }
            }else if (totalBracket==0){
                s1=calculating_2(s1);
            }
        }
        if(s1.equals("F")){
            return false;
        }
        return true;
    }


    private static String calculating_1(String s,Character[] arr1,Boolean[] arr2){//calculate within the brackets.
        while(!(s.equals("T")||s.equals("F"))) {
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);// ~ & | -> <-> a-z T F
                String front = "";
                String back = "";
                if (ch == '~') {
                    if(i>0) {
                        front = s.substring(0, i);
                    }
                    if(i<s.length()-2) {
                        back = s.substring(i + 2, s.length());
                    }
                    if(s.charAt(i+1)!='T' && s.charAt(i+1)!='F') {
                        for (int j = 0; j < arr1.length; j++) {
                            if (s.charAt(i + 1) == arr1[j]) {
                                boolean b = !arr2[j];
                                if (b) {
                                    s = front + "T" + back;
                                } else {
                                    s = front + "F" + back;
                                }
                            }
                        }
                    }else if(s.charAt(i+1)=='T'){
                        s = front + "F" + back;
                    }else if(s.charAt(i+1)=='F'){
                        s = front + "T" + back;
                    }
                    break;
                }
                if (ch == '&' && s.indexOf('~')==-1){
                    boolean b1 = true;
                    boolean b2 = true;
                    if(i>1) {
                        front = s.substring(0, i-1);
                    }
                    if(i<s.length()-3) {
                        back = s.substring(i + 2, s.length());
                    }
                    if(s.charAt(i-1)!='T' && s.charAt(i-1)!='F') {
                        for (int j = 0; j < arr1.length; j++) {
                            if (s.charAt(i - 1) == arr1[j]) {
                                b1 = arr2[j];
                            }
                        }
                    }else if(s.charAt(i-1)=='T'){
                        b1 = true;
                    }else if(s.charAt(i-1)=='F'){
                        b1 = false;
                    }
                    if(s.charAt(i+1)!='T' && s.charAt(i+1)!='F') {
                        for (int k = 0; k < arr1.length; k++) {
                            if (s.charAt(i + 1) == arr1[k]) {
                                b2 = arr2[k];
                            }
                        }
                    }else if(s.charAt(i+1)=='T'){
                        b2 = true;
                    }else if(s.charAt(i+1)=='F'){
                        b2 = false;
                    }
                    boolean b = b1 && b2;
                    if (b) {
                        s = front + "T" + back;
                    } else {
                        s = front + "F" + back;
                    }
                    break;
                }
                if (ch == '|' && s.indexOf('~')==-1 && s.indexOf('&')==-1){
                    boolean b1 = true;
                    boolean b2 = true;
                    if(i>1) {
                        front = s.substring(0, i-1);
                    }
                    if(i<s.length()-3) {
                        back = s.substring(i + 2, s.length());
                    }
                    if(s.charAt(i-1)!='T' && s.charAt(i-1)!='F') {
                        for (int j = 0; j < arr1.length; j++) {
                            if (s.charAt(i - 1) == arr1[j]) {
                                b1 = arr2[j];
                            }
                        }
                    }else if(s.charAt(i-1)=='T'){
                        b1 = true;
                    }else if(s.charAt(i-1)=='F'){
                        b1 = false;
                    }
                    if(s.charAt(i+1)!='T' && s.charAt(i+1)!='F') {
                        for (int k = 0; k < arr1.length; k++) {
                            if (s.charAt(i + 1) == arr1[k]) {
                                b2 = arr2[k];
                            }
                        }
                    }else if(s.charAt(i+1)=='T'){
                        b2 = true;
                    }else if(s.charAt(i+1)=='F'){
                        b2 = false;
                    }
                    boolean b = b1 || b2;
                    if (b) {
                        s = front + "T" + back;
                    } else {
                        s = front + "F" + back;
                    }
                    break;
                }
                if (ch == '-' && s.charAt(i-1)!='<' && s.charAt(i+1)=='>' && s.indexOf('~')==-1 && s.indexOf('&')==-1 && s.indexOf('|')==-1){
                    //p->q equals to (~p)|q
                    boolean b1 = true;
                    boolean b2 = true;
                    if(i>1) {
                        front = s.substring(0, i-1);
                    }
                    if(i<s.length()-5) {
                        back = s.substring(i + 3, s.length());
                    }
                    if(s.charAt(i-1)!='T' && s.charAt(i-1)!='F') {
                        for (int j = 0; j < arr1.length; j++) {
                            if (s.charAt(i - 1) == arr1[j]) {
                                b1 = arr2[j];
                            }
                        }
                    }else if(s.charAt(i-1)=='T'){
                        b1 = true;
                    }else if(s.charAt(i-1)=='F'){
                        b1 = false;
                    }
                    if(s.charAt(i+2)!='T' && s.charAt(i+2)!='F') {
                        for (int k = 0; k < arr1.length; k++) {
                            if (s.charAt(i + 2) == arr1[k]) {
                                b2 = arr2[k];
                            }
                        }
                    }else if(s.charAt(i+2)=='T'){
                        b2 = true;
                    }else if(s.charAt(i+2)=='F'){
                        b2 = false;
                    }
                    boolean b = (!b1 || b2);
                    if (b) {
                        s = front + "T" + back;
                    } else {
                        s = front + "F" + back;
                    }
                    break;
                }
                if (ch == '<' && s.charAt(i+1)=='-' && s.charAt(i+2)=='>' && s.indexOf('~')==-1 && s.indexOf('&')==-1 && s.indexOf('|')==-1){
                    //p<->q equals to ((~p)|q)&&((~q)|p)
                    boolean b1 = true;
                    boolean b2 = true;
                    if(i>1) {
                        front = s.substring(0, i-1);
                    }
                    if(i<s.length()-6) {
                        back = s.substring(i + 4, s.length());
                    }
                    if(s.charAt(i-1)!='T' && s.charAt(i-1)!='F') {
                        for (int j = 0; j < arr1.length; j++) {
                            if (s.charAt(i - 1) == arr1[j]) {
                                b1 = arr2[j];
                            }
                        }
                    }else if(s.charAt(i-1)=='T'){
                        b1 = true;
                    }else if(s.charAt(i-1)=='F'){
                        b1 = false;
                    }
                    if(s.charAt(i+3)!='T' && s.charAt(i+3)!='F') {
                        for (int k = 0; k < arr1.length; k++) {
                            if (s.charAt(i + 3) == arr1[k]) {
                                b2 = arr2[k];
                            }
                        }
                    }else if(s.charAt(i+3)=='T'){
                        b2 = true;
                    }else if(s.charAt(i+3)=='F'){
                        b2 = false;
                    }
                    boolean b = (!b1||b2)&&(!b2||b1);
                    if (b) {
                        s = front + "T" + back;
                    } else {
                        s = front + "F" + back;
                    }
                    break;
                }
                if (ch>='a' && ch<='z' && s.indexOf('~')==-1 && s.indexOf('&')==-1 && s.indexOf('|')==-1 && s.indexOf('<')==-1 && s.indexOf('-')==-1 && s.indexOf('>')==-1){
                    for (int j = 0; j < arr1.length; j++) {
                        if (ch == arr1[j]) {
                            if(arr2[j]==true){
                                s = "T";
                            }else if(arr2[j]==false){
                                s = "F";
                            }
                            return s;
                        }
                    }
                }
            }

        }
        return s;
    }


    private static String calculating_2(String s){//calculate within the brackets.
        while(!(s.equals("T")||s.equals("F"))) {
            //test: System.out.println("Hi_1");
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);// ~ & | -> <-> T F
                String front = "";
                String back = "";
                if (ch == '~') {
                    if(i>0) {
                        front = s.substring(0, i);
                    }
                    if(i<s.length()-2) {
                        back = s.substring(i + 2, s.length());
                    }
                    if(s.charAt(i+1)=='T'){
                        s = front + "F" + back;
                    }else if(s.charAt(i+1)=='F'){
                        s = front + "T" + back;
                    }
                    break;
                }
                if (ch == '&' && s.indexOf('~')==-1){
                    boolean b1 = true;
                    boolean b2 = true;
                    if(i>1) {
                        front = s.substring(0, i-1);
                    }
                    if(i<s.length()-3) {
                        back = s.substring(i + 2, s.length());
                    }
                    if(s.charAt(i-1)=='T'){
                        b1 = true;
                    }else if(s.charAt(i-1)=='F'){
                        b1 = false;
                    }
                    if(s.charAt(i+1)=='T'){
                        b2 = true;
                    }else if(s.charAt(i+1)=='F'){
                        b2 = false;
                    }
                    boolean b = b1 && b2;
                    if (b) {
                        s = front + "T" + back;
                    } else {
                        s = front + "F" + back;
                    }
                    break;
                }
                if (ch == '|' && s.indexOf('~')==-1 && s.indexOf('&')==-1){
                    boolean b1 = true;
                    boolean b2 = true;
                    if(i>1) {
                        front = s.substring(0, i-1);
                    }
                    if(i<s.length()-3) {
                        back = s.substring(i + 2, s.length());
                    }
                    if(s.charAt(i-1)=='T'){
                        b1 = true;
                    }else if(s.charAt(i-1)=='F'){
                        b1 = false;
                    }
                    if(s.charAt(i+1)=='T'){
                        b2 = true;
                    }else if(s.charAt(i+1)=='F'){
                        b2 = false;
                    }
                    boolean b = b1 || b2;
                    if (b) {
                        s = front + "T" + back;
                    } else {
                        s = front + "F" + back;
                    }
                    break;
                }
                if (ch == '-' && s.charAt(i-1)!='<' && s.charAt(i+1)=='>' && s.indexOf('~')==-1 && s.indexOf('&')==-1 && s.indexOf('|')==-1){
                    //p->q equals to (~p)|q
                    boolean b1 = true;
                    boolean b2 = true;
                    if(i>1) {
                        front = s.substring(0, i-1);
                    }
                    if(i<s.length()-5) {
                        back = s.substring(i + 3, s.length());
                    }
                    if(s.charAt(i-1)=='T'){
                        b1 = true;
                    }else if(s.charAt(i-1)=='F'){
                        b1 = false;
                    }
                    if(s.charAt(i+2)=='T'){
                        b2 = true;
                    }else if(s.charAt(i+2)=='F'){
                        b2 = false;
                    }
                    boolean b = (!b1 || b2);
                    if (b) {
                        s = front + "T" + back;
                    } else {
                        s = front + "F" + back;
                    }
                    break;
                }
                if (ch == '<' && s.charAt(i+1)=='-' && s.charAt(i+2)=='>' && s.indexOf('~')==-1 && s.indexOf('&')==-1 && s.indexOf('|')==-1){
                    //p<->q equals to ((~p)|q)&&((~q)|p)
                    boolean b1 = true;
                    boolean b2 = true;
                    if(i>1) {
                        front = s.substring(0, i-1);
                    }
                    if(i<s.length()-6) {
                        back = s.substring(i + 4, s.length());
                    }
                    if(s.charAt(i-1)=='T'){
                        b1 = true;
                    }else if(s.charAt(i-1)=='F'){
                        b1 = false;
                    }
                    if(s.charAt(i+3)=='T'){
                        b2 = true;
                    }else if(s.charAt(i+3)=='F'){
                        b2 = false;
                    }
                    boolean b = (!b1||b2)&&(!b2||b1);
                    if (b) {
                        s = front + "T" + back;
                    } else {
                        s = front + "F" + back;
                    }
                    break;
                }
            }
        }
        return s;
    }




}