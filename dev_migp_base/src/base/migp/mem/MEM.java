/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.mem;

/**
 *
 * @author chejf
 */
public abstract class MEM {

    public byte getMEM;
    public byte setMEM;
    public int addr;
    public int length;

    public MEM Add(int offset) {
        MEM mem = new MEM() {
        };
        mem.addr = this.addr + offset;
        mem.getMEM = this.getMEM;
        mem.setMEM = this.setMEM;
        mem.length = this.length;
        
        return mem;
    }
}
