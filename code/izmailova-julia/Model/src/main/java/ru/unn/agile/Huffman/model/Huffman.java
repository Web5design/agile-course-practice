package ru.unn.agile.Huffman.model;
import java.util.Arrays;

public final class Huffman {
    private final char ch;
    private final int freq;
    private final int left, right;
    private Huffman(final char ch, final int freq, final int left, final int right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
    public  static Huffman[] buildTree(final String str) {
        if (str == "") {
            return null;
        }
        Huffman[] mes = makeNode(str);
        Huffman[] hol = new Huffman[mes.length];
        Huffman[] tree = new Huffman[mes.length * 2];
        Huffman[] gmes, ghol;
        gmes = null;
        Huffman root;
        int m = 0, h = 0, treeCnt = -1 , hc = 0;
        if (mes == null) {
            return null;
        }
        if (mes.length == 1) {
            return mes;
        }
        root = addHollowNode(tree, treeCnt, mes[m], mes[m + 1]);
        m += 2;
        treeCnt += 2;
        hol[hc] = root;
        hc++;
        ghol = getNext(hol, hc, 0);
        while (root.freq != str.length()) {
            if (m < mes.length) {
                gmes = getNext(mes, mes.length, m);
                m++;
                if (gmes[1] != null) {
                    m++;
                }
            }
            if (gmes[1] == null) {
                if (gmes[0] == null) {
                    ghol = getNext(hol, hc, h);
                    root = addHollowNode(tree, treeCnt, ghol[0], ghol[1]);
                    h += 2;
                    treeCnt += 2;
                    hol[hc] = root;
                    hc++;
            } else {
                    if (gmes[0].freq < ghol[0].freq) {
                        root = addHollowNode(tree, treeCnt, gmes[0], ghol[0]);
                    } else {
                        root = addHollowNode(tree, treeCnt, ghol[0], gmes[0]);
                    }
                    treeCnt += 2;
                    hol[hc] = root;
                    hc++;
                    h++;
                    ghol = getNext(hol, hc, h);
                    gmes[0] = null;
            }
            } else {
                if (gmes[1].freq < ghol[0].freq) {
                    root = addHollowNode(tree, treeCnt, gmes[0], gmes[1]);
                    treeCnt += 2;
                    hol[hc] = root;
                    hc++;
                    gmes[0] = null;
                    gmes[1] = null;
                } else {
                    if (gmes[0].freq >= ghol[0].freq) {
                        root = addHollowNode(tree, treeCnt, ghol[0], gmes[0]);
                    } else {
                        root = addHollowNode(tree, treeCnt, gmes[0], ghol[0]);
                    }
                    m--;
                    treeCnt += 2;
                    hol[hc] = root;
                    hc++;
                    h++;
                    ghol = getNext(hol, hc, h);
                }
            }
        }
        tree[treeCnt + 1] = root;
        return tree;
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
    public static String writeCode(final String[] st, final char[] input) {

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
    private static Huffman[] makeNode(final String str) {
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
    private static Huffman[] getNext(final Huffman[] arr, final int count, final int cur) {
        Huffman [] curArr = new Huffman[2];
        if (cur < count) {
            curArr[0] = arr[cur];
            if (cur + 1 < count) {
                curArr[1] = arr [cur + 1];
            } else {
                curArr[1] = null;
            }
        }
        return curArr;
    }
}
