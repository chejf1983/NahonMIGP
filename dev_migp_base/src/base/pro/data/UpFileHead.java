/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.pro.data;

import base.pro.convert.NahonConvert;


/**
 *
 * @author jiche
 */
public final class UpFileHead {

    public final int magicnumber = MagincNumber;   //4byte
    public String DeviceName;                      //16byte
    public String Hardversion;                     //4byte
    public String SoftwareVersion;                 //4byte
    public int BinFileNumbier;                     //4byte

    public UpFileHead() {
        DeviceName = "Unknow";
        Hardversion = "0";
        SoftwareVersion = "UN";
        BinFileNumbier = 0;
    }
    
    public byte[] ToBytesArray() throws Exception{
        byte[] tmp = new byte[32];
        
        System.arraycopy(NahonConvert.IntegerToByteArray(magicnumber), 0, tmp, 0, 4);
        System.arraycopy(NahonConvert.StringToByte(DeviceName, UpFileHead.DeviceName_Length), 0, tmp, 4, 16);
        System.arraycopy(NahonConvert.StringToByte(Hardversion, UpFileHead.Hardversion_Length), 0, tmp, 20, 4);
        System.arraycopy(NahonConvert.StringToByte(SoftwareVersion, UpFileHead.SoftwareVersion_Length), 0, tmp, 24, 4);
        System.arraycopy(NahonConvert.IntegerToByteArray(BinFileNumbier), 0, tmp, 28, 4);
        
        return tmp;
    }
    
    public UpFileHead(byte[] input) throws Exception{
        if(input.length < Head_Length){
            throw new Exception("input data is too short");
        }
        
        if(NahonConvert.ByteArrayToInteger(input, 0) != MagincNumber){
            throw new Exception("Illigel file header");
        }
        
        this.DeviceName = NahonConvert.ByteArrayToString(input, 4, UpFileHead.DeviceName_Length);
        this.Hardversion = NahonConvert.ByteArrayToString(input, 20, UpFileHead.Hardversion_Length);
        this.SoftwareVersion = NahonConvert.ByteArrayToString(input, 24, UpFileHead.SoftwareVersion_Length);
        this.BinFileNumbier = NahonConvert.ByteArrayToInteger(input, 28);
    }

    public static int MagincNumber = 0xAABBFFFF;
    public static int FileEndMark = 0xED0FAABB;
    public static int BinFileHeadLength = 0x20;   //32byte

    public static int DeviceName_Length = 0x10;
    public static int Hardversion_Length = 0x04;
    public static int SoftwareVersion_Length = 0x04;
    
    public static int Head_Length = 0x20;

}
