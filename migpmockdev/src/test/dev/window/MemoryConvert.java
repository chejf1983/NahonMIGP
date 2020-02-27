/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.dev.window;

import base.pro.convert.NahonConvert;


/**
 *
 * @author Administrator
 */
public class MemoryConvert {

    public static String[] GetCMDList() {
        return new String[]{"EIA", "VPA", "NVPA", "MDA", "SRA"};
    }

    public static final String[] GetInputType() {
        return new String[]{
            "byte[]",
            String.class.getSimpleName(),
            int.class.getSimpleName(),
            float.class.getSimpleName(),
            short.class.getSimpleName(),
            byte.class.getSimpleName()
            };
    }

    public byte[] ConvertMemToByte(String type, String mem) throws Exception {
        if (type.contentEquals(String.class.getSimpleName())) {
            return NahonConvert.StringToByte(mem, mem.length());
        } else if (type.contentEquals(int.class.getSimpleName())) {
            int tmp = Integer.valueOf(mem);
            return NahonConvert.IntegerToByteArray(tmp);
        } else if (type.contentEquals(float.class.getSimpleName())) {
            float tmp = Float.valueOf(mem);
            return NahonConvert.FloatToByteArray(tmp);
        } else if (type.contentEquals(short.class.getSimpleName())) {
            int tmp = Short.valueOf(mem);
            return NahonConvert.UShortToByteArray(tmp);
        } else if (type.contentEquals(byte.class.getSimpleName())) {
            byte tmp = Byte.valueOf(mem);
            return new byte[]{tmp};
        } else if (type.contentEquals("byte[]")) {

            String[] sbytes = mem.trim().split(" ");
            byte[] tmp = new byte[sbytes.length];
            for(int i = 0; i < tmp.length; i++){
                String hex = sbytes[i].trim();
                hex = hex.substring(hex.indexOf("x")+1);
                tmp[i] = (byte)Integer.parseInt(hex, 16);
            }
            return tmp;
        } else {
            throw new Exception("Not Support");
        }
    }    
    
    public static String ConvertByteToString(String type, byte[] mem) throws Exception {
        if (type.contentEquals(String.class.getSimpleName())) {
            return NahonConvert.ByteArrayToString(mem, 0, mem.length);
        } else if (type.contentEquals(int.class.getSimpleName())) {
            return "" + NahonConvert.ByteArrayToInteger(mem, 0);
        } else if (type.contentEquals(float.class.getSimpleName())) {
            return "" + NahonConvert.ByteArrayToFloat(mem, 0);
        } else if (type.contentEquals(short.class.getSimpleName())) {
            return "" + NahonConvert.ByteArrayToUShort(mem, 0);
        } else if (type.contentEquals(byte.class.getSimpleName())) {
            return "" + mem[0];
        } else if (type.contentEquals("byte[]")) {
            String output = "";
            int i = 0;
            for (byte b : mem) {
                output += String.format(" 0x%02x", b);
                i++;
                if (i % 10 == 0) {
                    output += "\r\n";
                }
            }
            return output;
        } else {
            throw new Exception("Not Support");
        }
    }
}
