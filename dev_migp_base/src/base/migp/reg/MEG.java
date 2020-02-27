/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.reg;

import base.migp.mem.MEM;

/**
 *
 * @author chejf
 * @param <T>
 */
public abstract class MEG<T> {

    public MEG(MEM memory, String info) {
        this.memory = memory;
        this.info = info;
    }

    // <editor-fold defaultstate="collapsed" desc="属性"> 
    private final MEM memory;
    private String info = "";

    //寄存器个数
    public MEM GetMEM() {
        return this.memory;
    }

    public abstract void SetValue(T value) throws Exception;

    //获取值
    public abstract T GetValue();

    //获取byte值
    public abstract byte[] ToBytes() throws Exception;

    //加载byte值
    public abstract void LoadBytes(byte[] mem, int pos) throws Exception;

    //获取描述信息
    @Override
    public String toString() {
        return info;
    }
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="公共接口"> 
    public abstract T Convert(String value) throws Exception;

    public abstract boolean ConmpareTo(T value);
    // </editor-fold>  
}
