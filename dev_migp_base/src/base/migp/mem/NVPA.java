/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.mem;

import static base.migp.node.MIGP_CmdSend.GETNVPA;
import static base.migp.node.MIGP_CmdSend.SETNVPA;

/**
 *
 * @author chejf
 */
public class NVPA extends MEM {

    public NVPA(int addr, int memlength) {
        this.getMEM = GETNVPA;
        this.setMEM = SETNVPA;
        this.addr = addr;
        this.length = memlength;
    }
}
