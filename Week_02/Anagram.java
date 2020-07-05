import java.util.Scanner;

public class Anagram {
    public static void main(String[] args) {
        Anagram an = new Anagram();
        Scanner in = new Scanner(System.in);
        System.out.println("输入第一个字符串：");
        String s = in.nextLine();
        System.out.println("输入第二个字符串：");
        String t = in.nextLine();
        System.out.println("这两个字符串是否为字母异位词？");
        System.out.println(an.isAnagram(s, t));

    }

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) {
                return false;
            }
        }
        return true;
    }

}
