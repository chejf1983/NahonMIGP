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
public class DMEG extends MEG<Double> {


    public DMEG(MEM memory, String info) {
        super(memory, info);
        min = -Double.MAX_VALUE;
        max = Double.MAX_VALUE;
    }

    public DMEG(MEM memory,String info, double min, double max) {
        super(memory, info);
        this.min = min;
        this.max = max;
    }

    // <editor-fold defaultstate="collapsed" desc="属性"> 
    private double value = 0;
    public double min = 0;
    public double max = 0;
    @Override
    public Double GetValue() {
        return value;
    }

    @Override
    public void SetValue(Double value) throws Exception {
        if (value < this.min || value > this.max) {
            throw new Exception(value + "超出量程:" + this.min + "-" + this.max);
        }
        this.value = value;
    }

    @Override
    public byte[] ToBytes() throws Exception {
        return NahonConvert.DoubleToByteArray(value);
    }

    @Override
    public void LoadBytes(byte[] mem, int pos) throws Exception {
        if (mem.length < pos + this.GetMEM().length) {
            throw new Exception("内存长度不足，无法初始化数据");
        }
        this.value = NahonConvert.ByteArrayToDouble(mem, pos);
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="公共接口"> 

    @Override
    public Double Convert(String value) throws Exception{
        return Double.valueOf(value);
    }

    @Override
    public boolean ConmpareTo(Double value) {
        return value.compareTo(this.value) == 0;
    }
    // </editor-fold> 

}
