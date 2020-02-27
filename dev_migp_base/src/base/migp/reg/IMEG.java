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
public class IMEG extends MEG<Integer> {

    private int value = 0;
    public int min = 0;
    public int max = 0;

    public IMEG(MEM memory, String info) {
        super(memory, info);
        switch (this.GetMEM().length) {
            case 1:
                max = 255;
                break;
            case 2:
                max = 65535;
                break;
            case 4:
                max = Integer.MAX_VALUE;
                break;
            default:
                max = 0x0FFFFFFFF;
        }
        min = -max;
    }

    public IMEG(MEM memory, String info, int min, int max) {
        super(memory, info);
        this.min = min;
        this.max = max;
    }

    // <editor-fold defaultstate="collapsed" desc="属性"> 
    @Override
    public Integer GetValue() {
        return value;
    }

    @Override
    public void SetValue(Integer value) throws Exception {
        if (value < this.min || value > this.max) {
            throw new Exception("超出量程:" + this.min + "-" + this.max);
        }
        this.value = value;
    }

    @Override
    public byte[] ToBytes() throws Exception {
        switch (this.GetMEM().length) {
            case 1:
                return new byte[]{(byte) value};
            case 2:
                return NahonConvert.UShortToByteArray(value);
            case 4:
                return NahonConvert.IntegerToByteArray(value);
            default:
                throw new Exception("无法识别的整数寄存器");
        }
    }

    @Override
    public void LoadBytes(byte[] mem, int pos) throws Exception {
        if (mem.length < pos + this.GetMEM().length) {
            throw new Exception("内存长度不足，无法初始化数据");
        }
        switch (this.GetMEM().length) {
            case 1:
                this.value = mem[pos];
                break;
            case 2:
                this.value = NahonConvert.ByteArrayToUShort(mem, pos);
                break;
            case 4:
                this.value = NahonConvert.ByteArrayToInteger(mem, pos);
                break;
            default:
                throw new Exception("无法识别的整数寄存器");
        }
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="公共接口"> 
    @Override
    public Integer Convert(String value) throws Exception {
        return Integer.valueOf(value);
    }

    @Override
    public boolean ConmpareTo(Integer value) {
        return value.compareTo(this.value) == 0;
    }
    // </editor-fold>  

}
