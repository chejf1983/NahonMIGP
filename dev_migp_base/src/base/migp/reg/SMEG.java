/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.reg;

import base.migp.mem.MEM;
import base.pro.convert.NahonConvert;

/**
 *
 * @author chejf
 */
public class SMEG extends MEG<String> {

    private String value = "";

    public SMEG(MEM memory, String info) {
        super(memory, info);
    }

    // <editor-fold defaultstate="collapsed" desc="公共接口"> 
    @Override
    public String GetValue() {
        return value;
    }

    @Override
    public void SetValue(String value) throws Exception {
        if (value.length() > this.GetMEM().length) {
            throw new Exception("字符串长度过长:" + value.length());
        }
        this.value = value;
    }

    @Override
    public void LoadBytes(byte[] mem, int pos) throws Exception {
        if (mem.length < pos + this.GetMEM().length) {
            throw new Exception("内存长度不足，无法初始化数据");
        }
        this.value = NahonConvert.ByteArrayToString(mem, pos, this.GetMEM().length);
    }

    @Override
    public byte[] ToBytes() throws Exception {
        return NahonConvert.StringToByte(value, this.GetMEM().length);
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="公共接口"> 
    @Override
    public String Convert(String value) throws Exception {
        if (value.length() > this.GetMEM().length) {
            throw new Exception("字符串长度过长:" + value.length());
        }
        return value;
    }

    @Override
    public boolean ConmpareTo(String value) {
        return this.value.contentEquals(value);
    }
    // </editor-fold>  

}
