/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.impl;

import base.pro.convert.NahonConvert;
import base.pro.data.EquipmentInfo;
import base.migp.mem.EIA;
import base.migp.node.MIGP_CmdSend;
import base.migp.reg.IMEG;
import base.migp.reg.SMEG;

/**
 *
 * @author chejf
 */
public class MIGPEia {

    public static EIA DeviceName = new EIA(0x00, 0x10);
    public static EIA Hardversion = new EIA(0x10, 0x08);
    public static EIA SoftwareVersion = new EIA(0x18, 0x08);
    public static EIA BuildSerialNum = new EIA(0x20, 0x10);
    public static EIA BuildDate = new EIA(0x30, 0x10);
//
    public static EIA DevNum = new EIA(0x44, 0x01);
    
    public  SMEG EDEVNAME = new SMEG(new EIA(0x00, 0x10), "设备名称");
    public  SMEG EHWVER = new SMEG(new EIA(0x10, 0x01), "硬件版本");
    public  SMEG ESWVER =new SMEG( new EIA(0x18, 0x04), "软件版本");
    public  SMEG EBUILDSER = new SMEG(new EIA(0x20, 0x10), "序列号");
    public  SMEG EBUILDDATE = new SMEG(new EIA(0x30, 0xA), "生产日期");
//
    public  IMEG DEVNUM = new IMEG(new EIA(0x44, 0x01), "设备地址");
//    public static REG_STR DeviceName = new REG_STR(new EIA(0x00, 0x10));
//    public static REG_STR Hardversion = new REG_STR(new EIA(0x10, 0x08));
//    public static REG_STR SoftwareVersion = new REG_STR(new EIA(0x18, 0x08));
//    public static REG_STR BuildSerialNum = new REG_STR(new EIA(0x20, 0x10));
//    public static REG_STR BuildDate = new REG_STR(new EIA(0x30, 0x10));
//
//    public static EIA DevNum = new EIA(0x44, 0x01);

    private final MIGP_CmdSend migpsend;

    public MIGPEia(MIGP_CmdSend migpnode) {
        this.migpsend = migpnode;
    }

    //设备信息
    public EquipmentInfo GetEquipmentInfo(int timeout) throws Exception {
        int EIAInfo_length = 0x44;
        byte[] tmpdata = this.migpsend.GetMEM(DeviceName, EIAInfo_length, 3, timeout);
        EquipmentInfo EIA = new EquipmentInfo();

        EIA.DeviceName = NahonConvert.ByteArrayToString(tmpdata, DeviceName.addr, DeviceName.length);
        EIA.BuildDate = NahonConvert.ByteArrayToString(tmpdata, BuildDate.addr, BuildDate.length);
        EIA.BuildSerialNum = NahonConvert.ByteArrayToString(tmpdata, BuildSerialNum.addr, BuildSerialNum.length);
        EIA.Hardversion = NahonConvert.ByteArrayToString(tmpdata, Hardversion.addr, Hardversion.length);
        EIA.SoftwareVersion = NahonConvert.ByteArrayToString(tmpdata, SoftwareVersion.addr, SoftwareVersion.length);
        return EIA;
    }

    //设备信息
    public EquipmentInfo GetEquipmentInfo_Fast(int timeout) throws Exception {
        int EIAInfo_length = 0x44;
        byte[] tmpdata = this.migpsend.GetMEM(DeviceName, EIAInfo_length, 1, timeout);
        EquipmentInfo EIA = new EquipmentInfo();

        EIA.DeviceName = NahonConvert.ByteArrayToString(tmpdata, DeviceName.addr, DeviceName.length);
        EIA.BuildDate = NahonConvert.ByteArrayToString(tmpdata, BuildDate.addr, BuildDate.length);
        EIA.BuildSerialNum = NahonConvert.ByteArrayToString(tmpdata, BuildSerialNum.addr, BuildSerialNum.length);
        EIA.Hardversion = NahonConvert.ByteArrayToString(tmpdata, Hardversion.addr, Hardversion.length);
        EIA.SoftwareVersion = NahonConvert.ByteArrayToString(tmpdata, SoftwareVersion.addr, SoftwareVersion.length);
        return EIA;
    }

    public boolean SetEquipmentInfo(EquipmentInfo eiaInfo, int timeout) throws Exception {
        int EIAInfo_length = 0x44;
        byte[] tmpdata = new byte[EIAInfo_length];

        byte[] tmp = NahonConvert.StringToByte(eiaInfo.DeviceName, DeviceName.length);
        System.arraycopy(tmp, 0, tmpdata, DeviceName.addr, tmp.length);

        tmp = NahonConvert.StringToByte(eiaInfo.BuildDate, BuildDate.length);
        System.arraycopy(tmp, 0, tmpdata, BuildDate.addr, tmp.length);

        tmp = NahonConvert.StringToByte(eiaInfo.BuildSerialNum, BuildSerialNum.length);
        System.arraycopy(tmp, 0, tmpdata, BuildSerialNum.addr, tmp.length);

        tmp = NahonConvert.StringToByte(eiaInfo.Hardversion, Hardversion.length);
        System.arraycopy(tmp, 0, tmpdata, Hardversion.addr, tmp.length);

        tmp = NahonConvert.StringToByte(eiaInfo.SoftwareVersion, SoftwareVersion.length);
        System.arraycopy(tmp, 0, tmpdata, SoftwareVersion.addr, tmp.length);

        return this.migpsend.SetMEM(DeviceName, EIAInfo_length, tmpdata, 3, timeout);
    }

    //设备地址
    public byte GetDevAddr(int timeout) throws Exception {
        return this.migpsend.GetMEM(DevNum, DevNum.length, 3, timeout)[0];
    }

    public boolean SetDevAddr(byte devaddr, int timeout) throws Exception {
        return this.migpsend.SetMEM(DevNum, DevNum.length, new byte[]{devaddr}, 3, timeout);
    }
}
