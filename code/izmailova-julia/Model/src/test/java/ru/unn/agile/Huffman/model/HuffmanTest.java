package ru.unn.agile.Huffman.model;
import org.junit.Test;

import static org.junit.Assert.*;

public class HuffmanTest {


    @Test
    public void canFindTroubleWithBuildTreeWithWrongMessage() {
        Huffman[] huffmans = Huffman.buildTree("");
        assertNull(huffmans);
    }

    @Test
     public void canBildTreeWithMessage() {
        Huffman[] huffmans = Huffman.buildTree("some message");
        assertNotNull(huffmans);
    }

    @Test
    public void canBildTreeWithOneSimbol() {
        Huffman[] huffmans = Huffman.buildTree("s");
        assertNotNull(huffmans);
    }

    @Test
    public void canBildCodeWithMessage() {
        String str = "some message";
        Huffman[] huffmans = Huffman.buildTree(str);
        String s = "";
        String[] st = new String[256];
        boolean res = Huffman.buildCode(st, huffmans, s, str.length(), -1);
        assertTrue(res);
    }

    @Test
    public void canBildCorrectCode() {
        String str = "01";
        Huffman[] huffmans = Huffman.buildTree(str);
        String s = "";
        String[] st = new String[256];
        Huffman.buildCode(st, huffmans, s, str.length(), -1);
        assertEquals(st[48], "0");
    }

    @Test
    public void findMisstakeWithBuildTreeWithoutTree() {

        Huffman[] huffmans = null;
        String s = "";
        boolean res;
        String[] st = new String[256];
        res = Huffman.buildCode(st, huffmans, s, 0, -1);
        assertFalse(res);
    }

    @Test
    public void canGetCodeWithMessage() {
        String str = "some message";
        String code;
        String[] st = new String[256];
        String s = "";
        Huffman[] huffmans = Huffman.buildTree(str);
        Huffman.buildCode(st, huffmans, s, str.length(), -1);
        code = Huffman.getcode(st);
        assertNotEquals(code, "");
    }

    @Test
    public void canGetCodeWithoutCodes() {
        String code;
        String[] st = new String[256];
        String s = "";
        Huffman.buildCode(st, null, s, 0, -1);
        code = Huffman.getcode(st);
        assertEquals(code, "");
    }

    @Test
    public void canGetCorrectCodeWithMessage() {
        String str = "a";
        String corcode;
        String[] st = new String[256];
        String s = "";
        Huffman[] huffmans = Huffman.buildTree(str);
        Huffman.buildCode(st, huffmans, s, str.length(), -1);
        corcode = Huffman.getcode(st);
        assertNotEquals(corcode, "a 0\n");
    }

    @Test
    public void canWriteCodeWithSomeMessage() {
        String str = "some message";
        Huffman[] huffmans = Huffman.buildTree(str);
        String s = "";
        String[] st = new String[256];
        Huffman.buildCode(st, huffmans, s, str.length(), -1);
        s = Huffman.writeCode(st, str.toCharArray());
        assertNotNull(s);
    }

    @Test
    public void canWriteCorrectCode() {
        String str = "01";
        Huffman[] tree = Huffman.buildTree(str);
        String s = "";
        String[] st = new String[256];
        Huffman.buildCode(st, tree, s, str.length(), -1);
        s = Huffman.writeCode(st, str.toCharArray());
        assertEquals(s, st[48] + " " + st[49] + " ");
    }

    @Test
    public void canfindMistakeWithoutCodes() {
        String str = "01";
        String[] st = null;
        String s;
        s = Huffman.writeCode(st, str.toCharArray());
        assertNull(s);
    }
}
