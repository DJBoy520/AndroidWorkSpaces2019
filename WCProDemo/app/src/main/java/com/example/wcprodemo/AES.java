package com.example.wcprodemo;

public class AES {
    public static final int[][] s1 = new int[][]{
            {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
            {0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0},
            {0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15},
            {0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
            {0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84},
            {0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf},
            {0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8},
            {0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2},
            {0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73},
            {0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb},
            {0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79},
            {0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08},
            {0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a},
            {0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e},
            {0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf},
            {0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}};
    public static final int[][] s2 = new int[][]{
            {0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb},
            {0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb},
            {0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e},
            {0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25},
            {0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92},
            {0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84},
            {0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06},
            {0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b},
            {0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73},
            {0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e},
            {0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b},
            {0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4},
            {0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f},
            {0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef},
            {0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61},
            {0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d}};

    AES() {
    }

    public static int getLeft4Bit(int num) {// �õ�һ���ֽڵĸ���λ
        int left = num & 0x000000f0;
        return left >> 4;
    }

    public static int getRight4Bit(int num) {// �ĵõ�һ���ֽڵĵ���λ
        return num & 0x0000000f;
    }

    public static int getNumFromSBox(int index) {// ������������S���л��Ԫ��
        int row = getLeft4Bit(index);
        int col = getRight4Bit(index);
        return s1[row][col];
    }

    public static int getIntFromChar(char c) {// ��һ���ַ�ת�������
        int result = Integer.valueOf(c);
        return result & 0x000000ff;
    }

    public static void convertToIntArray(String str, int[][] pa) {// ���ַ�ת����������ֵ
        int k = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                pa[j][i] = getIntFromChar(str.charAt(k));
                k++;
            }
    }

    public static int getWordFromStr(String str) {// ��һ���ĸ��ֽڵ��ַ�������ĸ��ֽڵ�������ֵ
        int one, two, three, four;
        one = getIntFromChar(str.charAt(0));
        one = one << 24;
        two = getIntFromChar(str.charAt(1));
        two = two << 16;
        three = getIntFromChar(str.charAt(2));
        three = three << 8;
        four = getIntFromChar(str.charAt(3));
        return one | two | three | four;
    }

    public static void splitIntToArray(int num, int array[]) {// ��һ�����ֽ������ĸ��ֽڷֱ�ȡ������һ������
        int one, tow, three;
        one = num >> 24;
        tow = num >> 24;
        three = num >> 24;
        array[0] = one & 0x000000ff;
        array[1] = tow & 0x000000ff;
        array[2] = three & 0x000000ff;
        array[3] = num & 0x000000ff;
    }

    public static void leftLoop4int(int array[], int step) {// ��һ�������е���������stepλ
        int temp[] = new int[4];
        for (int i = 0; i < 4; i++) {
            temp[i] = array[i];
        }
        int index = step % 4 == 0 ? 0 : step % 4;
        for (int i = 0; i < 4; i++) {
            array[i] = temp[index];
            index++;
            index = index % 4;
        }
    }

    public static int mergeArrayToInt(int array[]) {// ��һ����λ�������飬���ӳ�һ����λ�ֽڵ���
        int one = array[0] << 24;
        int two = array[1] << 16;
        int three = array[2] << 8;
        int four = array[3];
        return one | two | three | four;
    }

    public static final int[] Rcon = new int[]{0x01000000, 0x02000000, 0x04000000, 0x08000000, 0x10000000, 0x20000000,
            0x40000000, 0x80000000, 0x1b000000, 0x36000000};

    public static int T(int num, int round) {// T�����㷨
        int[] numArray = new int[4];
        splitIntToArray(num, numArray);
        leftLoop4int(numArray, 1);
        for (int i = 0; i < 4; i++) {
            numArray[i] = getNumFromSBox(numArray[i]);
        }
        int result = mergeArrayToInt(numArray);
        return result ^ Rcon[round];
    }

    public static int w[] = new int[44];// ��չ��Կ,ȫ44��

    public static void extendKey(String key) {// ������չ��Կ��������44��
        for (int i = 0; i < 4; i++) {
            w[i] = getWordFromStr(key.substring(i * 4, i * 4 + 4));
        }
        for (int i = 4, j = 0; i < 44; i++) {
            if (i % 4 == 0) {
                w[i] = w[i - 4] ^ T(w[i - 1], j);
                j++;// ��һ��
            } else {
                w[i] = w[i - 4] ^ w[i - 1];
            }
        }
    }

    public static void addRoundKey(int array[][], int round) {// ����Կ��
        int warray[] = new int[4];
        for (int i = 0; i < 4; i++) {
            splitIntToArray(w[round * 4 + i], warray);
            for (int j = 0; j < 4; j++) {
                array[j][i] = array[j][i] ^ warray[j];
            }
        }
    }

    public static void subBytes(int array[][]) {// �ֽڴ���
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                array[i][j] = getNumFromSBox(array[i][j]);
            }
        }
    }

    public static void shiftRows(int array[][]) {// ����λ
        int rowTwo[] = new int[4], rowThree[] = new int[4], rowFour[] = new int[4];
        for (int i = 0; i < 4; i++) {
            rowTwo[i] = array[1][i];
            rowThree[i] = array[2][i];
            rowFour[i] = array[3][i];
        }
        leftLoop4int(rowTwo, 1);
        leftLoop4int(rowThree, 2);
        leftLoop4int(rowFour, 3);

        for (int i = 0; i < 4; i++) {
            array[1][i] = rowTwo[i];
            array[2][i] = rowThree[i];
            array[3][i] = rowFour[i];
        }
    }

    // �л�Ͼ���
    public static final int[][] colM = new int[][]{{2, 3, 1, 1}, {1, 2, 3, 1}, {1, 1, 2, 3}, {3, 1, 1, 2}};

    public static int GFMul2(int s) {
        int result = s << 1;
        int a7 = result & 0x00000100;

        if (a7 != 0) {
            result = result & 0x000000ff;
            result = result ^ 0x1b;
        }

        return result;
    }

    static int GFMul3(int s) {
        return GFMul2(s) ^ s;
    }

    static int GFMul4(int s) {
        return GFMul2(GFMul2(s));
    }

    static int GFMul8(int s) {
        return GFMul2(GFMul4(s));
    }

    static int GFMul9(int s) {
        return GFMul8(s) ^ s;
    }

    static int GFMul11(int s) {
        return GFMul9(s) ^ GFMul2(s);
    }

    static int GFMul12(int s) {
        return GFMul8(s) ^ GFMul4(s);
    }

    static int GFMul13(int s) {
        return GFMul12(s) ^ s;
    }

    static int GFMul14(int s) {
        return GFMul12(s) ^ GFMul2(s);
    }

    static int GFMul(int n, int s) {
        int result = 0;
        if (n == 1)
            result = s;
        else if (n == 2)
            result = GFMul2(s);
        else if (n == 3)
            result = GFMul3(s);
        else if (n == 0x9)
            result = GFMul9(s);
        else if (n == 0xb)// 11
            result = GFMul11(s);
        else if (n == 0xd)// 13
            result = GFMul13(s);
        else if (n == 0xe)// 14
            result = GFMul14(s);
        return result;
    }

    public static void mixColumns(int array[][]) {// �л��
        int[][] tempArray = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempArray[i][j] = array[i][j];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                array[i][j] = GFMul(colM[i][0], tempArray[0][j]) ^ GFMul(colM[i][1], tempArray[1][j])
                        ^ GFMul(colM[i][2], tempArray[2][j]) ^ GFMul(colM[i][3], tempArray[3][j]);
            }
        }
    }

    /**
     * ��4X4����ת���ַ���
     */
    public static String convertArrayToStr(int array[][]) {
        String result = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result += Character.toString((char) array[j][i]);
            }
        }
        return result;
    }

    /**
     * �����Կ����
     */
    public static int checkKeyLen(int len) {
        if (len == 16)
            return 1;
        else
            return 0;
    }

    /**
     * ���� p: ���ĵ��ַ������顣 ���� plen: ���ĵĳ��ȡ� ���� key: ��Կ���ַ������顣
     */
    public static String Aes(String p, String key) {
        String result = "";
        int keylen = key.length();
        int plen = p.length();
        int[][] pArray = new int[4][4];
        if (plen == 0 || plen % 16 != 0) {
            int length = plen % 16;
            String temp = "";
            for (int i = 0; i < 16 - length; i++) {
                temp += " ";
            }
            p += temp;
        }

        if (checkKeyLen(keylen) != 1) {
            for (int i = 0; i < 16 - keylen; i++) {
                key += "a";
            }
        }

        extendKey(key);

        for (int k = 0; k < plen; k += 16) {
            String temp = p.substring(k, k + 16);
            convertToIntArray(temp, pArray);
            addRoundKey(pArray, 0);
            for (int i = 1; i < 10; i++) {
                subBytes(pArray);
                shiftRows(pArray);
                mixColumns(pArray);
                addRoundKey(pArray, i);
            }

            subBytes(pArray);
            shiftRows(pArray);
            addRoundKey(pArray, 10);
            result += convertArrayToStr(pArray);
        }
        return result;
    }

    public static int getNumFromS2Box(int index) {
        int row = getLeft4Bit(index);
        int col = getRight4Bit(index);
        return s2[row][col];
    }

    public static void deSubBytes(int array[][]) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                array[i][j] = getNumFromS2Box(array[i][j]);
    }

    /**
     * ��4��Ԫ�ص�����ѭ������stepλ
     */
    public static void rightLoop4int(int array[], int step) {
        int temp[] = new int[4];
        for (int i = 0; i < 4; i++) {
            temp[i] = array[i];
        }

        int index = step % 4 == 0 ? 0 : step % 4;
        index = 3 - index;
        for (int i = 3; i >= 0; i--) {
            array[i] = temp[index];
            index--;
            index = index == -1 ? 3 : index;
        }
    }

    /**
     * ������λ
     */
    public static void deShiftRows(int array[][]) {
        int rowTwo[] = new int[4], rowThree[] = new int[4], rowFour[] = new int[4];
        for (int i = 0; i < 4; i++) {
            rowTwo[i] = array[1][i];
            rowThree[i] = array[2][i];
            rowFour[i] = array[3][i];
        }

        rightLoop4int(rowTwo, 1);
        rightLoop4int(rowThree, 2);
        rightLoop4int(rowFour, 3);

        for (int i = 0; i < 4; i++) {
            array[1][i] = rowTwo[i];
            array[2][i] = rowThree[i];
            array[3][i] = rowFour[i];
        }
    }

    /**
     * ���л���õ��ľ���
     */
    public static final int[][] deColM = new int[][]{{0xe, 0xb, 0xd, 0x9}, {0x9, 0xe, 0xb, 0xd},
            {0xd, 0x9, 0xe, 0xb}, {0xb, 0xd, 0x9, 0xe}};

    /**
     * ���л��
     */
    public static void deMixColumns(int array[][]) {
        int[][] tempArray = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempArray[i][j] = array[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                array[i][j] = GFMul(deColM[i][0], tempArray[0][j]) ^ GFMul(deColM[i][1], tempArray[1][j])
                        ^ GFMul(deColM[i][2], tempArray[2][j]) ^ GFMul(deColM[i][3], tempArray[3][j]);
            }
        }
    }

    /**
     * ������4X4����������
     */
    public static void addRoundTowArray(int aArray[][], int bArray[][]) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                aArray[i][j] = aArray[i][j] ^ bArray[i][j];
            }
        }
    }

    /**
     * ��4��32λ����Կ���л��4X4���飬 ���ڽ������л��
     */
    public static void getArrayFrom4W(int i, int array[][]) {
        int colOne[] = new int[4], colTwo[] = new int[4], colThree[] = new int[4], colFour[] = new int[4];
        int index = i * 4;
        splitIntToArray(w[index], colOne);
        splitIntToArray(w[index + 1], colTwo);
        splitIntToArray(w[index + 2], colThree);
        splitIntToArray(w[index + 3], colFour);

        for (int j = 0; j < 4; j++) {
            array[j][0] = colOne[j];
            array[j][1] = colTwo[j];
            array[j][2] = colThree[j];
            array[j][3] = colFour[j];
        }

    }

    /**
     * ���� c: ���ĵ��ַ������顣 ���� clen: ���ĵĳ��ȡ� ���� key: ��Կ���ַ������顣
     */
    public static String deAes(String c, String key) {
        StringBuilder result = new StringBuilder();
        int[][] cArray = new int[4][4];
        int keylen = key.length();
        int clen = c.length();

        if (checkKeyLen(keylen) != 1) {
            for (int i = 0; i < 16 - keylen; i++) {
                key += "a";
            }
        }

        if (clen == 0 || clen % 16 != 0) {
            int length = clen % 16;
            String temp = " ";
            for (int i = 0; i < 16 - length - 1; i++) {
                temp += " ";
            }
            c += temp;
        }

        extendKey(key);

        for (int k = 0; k < clen; k += 16) {
            int wArray[][] = new int[4][4];
            String temp = c.substring(k, k + 16);
            convertToIntArray(temp, cArray);
            addRoundKey(cArray, 10);

            for (int i = 9; i >= 1; i--) {
                deSubBytes(cArray);
                deShiftRows(cArray);
                deMixColumns(cArray);
                getArrayFrom4W(i, wArray);
                deMixColumns(wArray);
                addRoundTowArray(cArray, wArray);
            }

            deSubBytes(cArray);
            deShiftRows(cArray);
            addRoundKey(cArray, 0);
            result.append(convertArrayToStr(cArray));
        }
        return result.toString();
    }
}
