import java.util.*;
import java.lang.*;
import java.io.*;

public class SP
{
    static int count;
    static StringBuilder current;
    static ArrayList<StringBuilder> list;
    static ArrayList<StringBuilder> list2;
    static HashMap<Character, Character> map = new HashMap<>();
    public static void main (String[] sp) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());
        for(int i=0; i<26; ++i) {
            map.put((char)(i+97), (char)(i+65));
            map.put((char)(i+65), (char)(i+97));
        }
        //System.out.println(map);
        while( t-- > 0 ) {
            char [] arr = reader.readLine().toCharArray();
            Arrays.sort(arr);
            count = 0;
            current = new StringBuilder();
            list = new ArrayList<>();
            list2 = new ArrayList<>();
            subSeq(arr, 0);
            list.remove(0);
            System.out.println(list);
            for(StringBuilder s : list) {
//                System.out.println(s.toString() + list.size());
                find(new StringBuilder(s), 0);
            }
            Comparator<StringBuilder> comp = new Comparator<StringBuilder>() {
                @Override
                public int compare(StringBuilder o1, StringBuilder o2) {
                    return (o1.toString()).compareTo(o2.toString());
                }
            };
            Collections.sort(list2, comp);
            System.out.println(list2.size());
            for(int i=0; i<list2.size(); ++i)
                System.out.print(list2.get(i) + " ");
            System.out.println();
        }
    }

    public static void find(StringBuilder sp, int index) {
        if(index == sp.length()) {
            list2.add(new StringBuilder(sp));
            //System.out.println(sp);
            return;
        }
        find(sp, index+1);
        sp.setCharAt(index, map.get(sp.charAt(index)));
        find(sp, index+1);
        sp.setCharAt(index, map.get(sp.charAt(index)));

    }

    public static void subSeq(char [] arr, int index) {
        list.add(new StringBuilder(current));
        for(int i=index; i<arr.length; ++i) {
            current.append(arr[i]);
            subSeq(arr, i+1);
            current.deleteCharAt(current.length()-1);
        }

    }

}
