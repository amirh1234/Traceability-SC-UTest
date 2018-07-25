import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    private static int BraCountReverse=0;

    private static int BraCount=-1;
    private static ArrayList<String> Scope=new ArrayList<String>();
    private static String Line=null;
    private static int count=0;
    private static Stack<String> stack=new Stack<String>();
    private static int found=0;
    private static File f ;

    private static BufferedReader in ;
    private static int Bracket=0;
    private static Object obj=new Object();
    public static void main(String[] args) throws IOException {


        //URL url = Main.class.getClassLoader().getResource("AntClassLoaderDelegationTest.txt");


        String pattern="assertEquals";
        int i=0;
        int index;

        String ObjName;


        f = new File("./AntClassLoaderDelegationTest.txt");



        in = new BufferedReader (new InputStreamReader
                (new ReverseLineInputStream(f)));



        while(in.readLine()!=null){
            count++;
        }
        in = new BufferedReader (new InputStreamReader
                (new ReverseLineInputStream(f)));

        index=IndexFinder(count,pattern,0);
        if(index!=-1){
            Line=Line.substring(index);
        }


        ObjName=obj.getDefinedName();
        System.out.printf("\nObjName:%s\n",ObjName);

        if(ObjName!=null) {


            obj.setDefinedName(ObjName);
            obj.setLine(count);
            index=IndexFinder(count,ObjName,1);
            if(index!=-1){


                obj.setClassNameL(Line.substring(0,index));
                List<String> ListTemp=new ArrayList<String>(Arrays.asList(Line.split(" ")));
                for(int j=0;j<ListTemp.size();j++){
                    if(ListTemp.get(i).equals("new")){
                        index=ListTemp.get(i+1).indexOf("(");
                        break;
                    }
                }



                obj.setClassNameR(Line.substring(0,index));

            }
            else{
                obj.setClassNameR(ObjName);
                obj.setClassNameL(ObjName);
            }


            System.out.printf("Object:%s\tClass:%s\tnew:%s",obj.getDefinedName(),obj.getClassNameL(),obj.getClassNameR());

        }
        else{
            System.out.printf("No object found");
        }

















    }

    /* finding an index of a pattern in file given

     */

    public static int IndexFinder(int length, String pattern, int object){
        String TLine;
        String temp;

        int index;

        int index0;
        int found=0;
        try {
            TLine=in.readLine();
            //System.out.printf("Line:%s",TLine);
           // if(TLine.compareTo("}")!=-1){
           //     TLine=in.readLine();
           //     Bracket=1;
           // }
        }
        catch (IOException g) {
            return -1;
        }
        while(length!=0){

            //if(Bracket!=1){
                try {
                    TLine=in.readLine();

                }
                catch (IOException e) {
                    return -1;
                }
          //  }
          //  Bracket=0;
            if(object==1){

//                if(ScopeChecker(TLine)==0){
//                    return -1;
//                    }


            }
            //push the line in stack
            temp=TLine;
            BraCount+=temp.length()-temp.replace("}","").length();

            stack.push(TLine);

            index=TLine.indexOf('.');

            if(index!=-1){
                index0=TLine.indexOf(',');
                if(index0==-1){
                    index0=TLine.indexOf('(');
                }
                if(index0>index){
                    List<String> list=new ArrayList<String>(Arrays.asList(TLine.split(" ")));
                    if(list.get(0).equals("return")){
                        index=list.get(1).indexOf('.');
                        index0++;
                        obj.setDefinedName(list.get(1).substring(0,index));
                      //  System.out.printf("\nLine:%s\n",TLine);
                    }

                }
                else if(index0==-1){
                    List<String> list=new ArrayList<String>(Arrays.asList(TLine.split(" ")));
                    if(list.get(0).equals("return")){
                        index=list.get(1).indexOf('.');
                        index0++;
                        obj.setDefinedName(list.get(1).substring(0,index));
                       // System.out.printf("\nLine:%s\n",TLine);
                    }

                }
                else{
                    index0++;
                    obj.setDefinedName(TLine.substring(index0,index));
                }

            }
            index=TLine.indexOf(pattern);
            if (index != -1) {
                found=1;
                Line=TLine;
               // System.out.printf("Line:%d\tindex:%s\n",length,index);
               // System.out.printf("\nLine:%s\n",Line);
                return index;
            }
            //check if it's end of scope
            if(TLine.indexOf('{')!=-1){
                temp=TLine;
                BraCountReverse+= temp.length()-temp.replace("{","").length();

                    //pop until reaching end of scope
                    temp=stack.pop();
                BraCount-=BraCountReverse;
                   // while(true){
                        if(BraCount<=0 && found==1){
                            return -1;
                        }

//                        if(temp.indexOf('}')!=-1){
//                           // BraCount--;
//                            //temp=stack.pop();
//                            if(BraCount<=0){
//                                return -1;
//                            }
//                            break;
//                        }
//                        else if(temp==null){
//                            if(found==0){
//                                break;
//                            }
//                            return -1;
//                        }
//                        temp=stack.pop();
                   // }
            }

            length--;
        }
        return -1;
    }

//    public static String ObjFinder(String Line){
//        int index0;
//        int index;
//        index=Line.indexOf('.');
//            if(index!=-1){
//                index0=Line.indexOf(',');
//                return Line.substring(index0,index);
//            }
//        if(obj.getDefinedName()!=null){
//
//        }
////        if(object==null) {
////
////
////        }
////        else {
////                if(in!=null){
////                    try {
////                        Line=in.readLine();
////                    } catch (IOException e) {
////                        return null;
////                    }
////                }
////
////            String temp=IndexFinder()(Line,object,null);
//
//
//      //  }
//
//        return null;
//    }

//    public static int ScopeChecker(String Line){
//        int index;
//        int a=0;
//        index=Line.indexOf('{');
//        if(index!=-1) {
//            index = Line.indexOf("if");
//            if (index != -1) {
//                a = 1;
//            }
//            index = Line.indexOf("else");
//            if (index != -1) {
//                a = 1;
//            }
//            index = Line.indexOf("for");
//            if (index != -1) {
//                a = 1;
//            }
//            index = Line.indexOf("while");
//            if (index != -1) {
//                a = 1;
//            }
//        }
//        if(a==0){
//            return 0;
//        }
//        return 1;
//    }
//    public void ScopeSaver(){
//
//    }



}
