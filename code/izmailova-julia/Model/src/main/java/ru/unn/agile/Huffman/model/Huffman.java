package ru.unn.agile.Huffman.model;
import java.util.Arrays;
public final class Huffman {

        private final char ch;
        private final int freq;
        private final int left, right;


        Huffman(final char ch, final int freq, final int left, final int right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public int getfreq(final Huffman r) {
            return r.freq;
        }

       public static Huffman[] makeNode(final String str) {
           if (str == "" || str == null) {
               return null;
           }

           char[] input = str.toCharArray();
           int ch = input[0];
           for (int i = 1; i < input.length; i++) {
               if (ch < input[i]) {
                   ch = input[i];
               }
           }
           int[] freq = new int[ch + 1];
           for (int i = 0; i < input.length; i++) {
               freq[input[i]]++;
           }

           int r = freq[input[0]];
           int t = 0;

               for (char j = 0; j < freq.length; j++) {
                   if (freq[j] != 0) {
                       t++;
                       if (freq[j] < r) {
                           r = freq[j];
                       }
                   }

               }

           Huffman[] r1 = new Huffman[t];
           int j = 0;
           while (j < t) {
               for (char i = 0; i < freq.length; i++) {
                   if (freq[i] == r) {
                       r1[j] = new Huffman(i, freq[i], -1, -1);
                       j++;
                   }
               }
               r += 1;
           }
           return r1;
       }

    public  static Huffman[] buildTree(final String str) {
        Huffman[] n1 = makeNode(str);
        if (n1 == null) {
            return null;
        }
        Huffman[] n2 = new Huffman[n1.length * 2];
        Huffman[] root = new Huffman[n1.length * 2];
        Huffman current, next;
        int j = 0, c = 0, f = 0, r = 0, l = 0, frq = 0;
        current = n1[j];
        root[0] = current;
        j++;
        if (j < n1.length) {

            next = n1[j];
            root[1] = next;
            n2[c] = new Huffman('\0', current.freq + next.freq, 0, 1);
            c++;
            r = 1;
        }
        j++;
        while (j < n1.length) {
            current = n1[j];
            if (n2[f].freq <= current.freq) {
                n2[c] = addHollowNode(root, r, n2[f], current);
                r += 2;
                f++;
                c++;
                l = 0;
            } else {
                frq = n2[f].freq;
                if (frq > current.freq) {
                    j++;
                    if (j < n1.length - 1) {
                        next = n1[j];
                        if (frq <= next.freq) {
                            n2[c] = addHollowNode(root, r, n2[f], next);
                            r += 2;
                            f++;
                            c++;
                        } else {
                            n2[c] = addHollowNode(root, r, next, current);
                            r += 2;
                            c++;
                        }

                    }
                    l = 1;
                }
            }
            j++;
        }

        if (l == 1) {
            n2[c] = addHollowNode(root, r, current, n2[f]);
            r += 2;
            f++;
            c++;
        }
        if (j == n1.length) {
           current = n1[n1.length - 1];
           n2[c] = addHollowNode(root, r, n2[f], current);
           r += 2;
           f++;
           c++;
           l = 0;
          /*  && (root[r - 1] != n1[n1.length - 1])*/
        }
        while (c - f > 2) {
            current = n2[f];
            f++;
            next = n2[f];
            f++;
            n2[c] = addHollowNode(root, r, current, next);
            r += 2;
            c++;
        }
        if (f < c) {
            current = n2[f];
            f++;
            next = n2[f];
            n2[c] = addHollowNode(root, r, current, next);
            r += 2;
            r++;
            root[r] = n2[c];
        }
        return root;
    }
    private static Huffman addHollowNode(final Huffman[] root, final int r,
                                      final Huffman left, final Huffman right) {
        int c = r;
        c++;
        root[c] = left;
        int lft = c;
        c++;
        int rgh = c;
        root[c] = right;
        return new Huffman('\0', left.freq + right.freq, lft, rgh);
    }

    public static boolean checkNode(final Huffman[] x, final int r) {
        if (x[0] == null) {
            return false;
        }
        int i = 0;
        while (x[i].freq != r) {
            i++;
        }
        Huffman root = x[i];
        if (root == null) {
            return false;
        }
        return true;
    }
    public static boolean buildCode(final String[] st, final Huffman[] x,
                                 final String s, final int frq, final int r) {
        boolean res = true;
        if (x == null) {
            return false;
        }
        int i = 0;
        if (r == -1) {
            while (x[i].freq != frq) {
                i++;
            }
        } else {
            i = r;
        }
        if (x[i].left == -1 && x[i].right == -1) {
            st[x[i].ch] = s;
        }
        if (x[i].right != -1 || x[i].left != -1) {
            int lft = x[i].left;
            int rgh = x[i].right;
            res = buildCode(st, x, s + '0', 0, lft);
            res = buildCode(st, x, s + '1', 0, rgh);
        }
        return res;
    }
    public static String writeTree(final Huffman[] huff, final int frq) {
        String s = "";
        String[][] st = new String[frq * 2][frq];
        for (int r = 0; r < frq; r++) {
            Arrays.fill(st[r], "");
        }
        int i = 0;
        while (huff[i].freq != frq) {
            i++;
        }
        if (frq == 1) {
            s = s + huff[0].ch;
            return  s;
        }
        st[0][frq / 2] = "root";
        sfm(huff, i, st, 0, frq / 2);

        for (int n = 0; n < frq; n++) {
            for (int j = 0; j < frq; j++) {
                s += "  " + st[n][j];
            }
            s += "\n";
        }
        return s;
    }
    private static void sfm(final Huffman[] huff, final int i,
                             final String[][]st, final int count, final int pt) {
        int r = count;
        r++;
        if (huff[i].left == -1 && huff[i].right == -1) {
            st[r][pt] += huff[i].ch;
        }
        if (huff[i].left != -1) {
            int lft = huff[i].left;
            st[r][pt - 1] += "0";
            sfm(huff, lft, st, r, pt - 1);
        }
        if (huff[i].right != -1) {
            int rgh = huff[i].right;
            st[r][pt + 1] += "1";
            sfm(huff, rgh, st, r, pt + 1);

        }

    }




   public static String encode(final String[] st, final char[] input) {

       String s = "";
       for (int i = 0; i < input.length; i++) {
       String code = st[input[i]];
       for (int j = 0; j < code.length(); j++) {
           if (code.charAt(j) == '0') {
               s += "0";
           } else
                if (code.charAt(j) == '1') {
                s += "1";
           }
       }
           s += " ";
       }
       return s;
   }


 }
