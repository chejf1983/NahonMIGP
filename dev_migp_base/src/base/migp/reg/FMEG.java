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
public class FMEG extends MEG<Float> {

    private float value = 0;
    public float min = 0;
    public float max = 0;

    public FMEG(MEM memory, String info) {
        super(memory, info);
        min = -Float.MAX_VALUE;
        max = Float.MAX_VALUE;
    }

    public FMEG(MEM memory, String info, float min, float max) {
        super(memory, info);
        this.min = min;
        this.max = max;
    }

    // <editor-fold defaultstate="collapsed" desc="属性"> 
    @Override
    public Float GetValue() {
        return value;
    }

    @Override
    public void SetValue(Float value) throws Exception {
        if (value < this.min || value > this.max) {
            throw new Exception("超出量程:" + this.min + "-" + this.max);
        }
        this.value = value;
    }

    @Override
    public byte[] ToBytes() throws Exception {
        return NahonConvert.FloatToByteArray(value);
    }

    @Override
    public void LoadBytes(byte[] mem, int pos) throws Exception {
        if (mem.length < pos + this.GetMEM().length) {
            throw new Exception("内存长度不足，无法初始化数据");
        }
        this.value = NahonConvert.ByteArrayToFloat(mem, pos);
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="公共接口"> 
    @Override
    public Float Convert(String value) throws Exception {
        return Float.valueOf(value);
    }

    @Override
    public boolean ConmpareTo(Float value) {
        return value.compareTo(this.value) == 0;
    }
    // </editor-fold> 
}
