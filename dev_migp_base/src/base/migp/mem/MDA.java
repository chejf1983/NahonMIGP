/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.mem;

import static base.migp.node.MIGP_CmdSend.GETMDA;
import static base.migp.node.MIGP_CmdSend.SETMDA;

/**
 *
 * @author chejf
 */
public class MDA extends MEM {

    public MDA(int addr, int memlength) {
        this.getMEM = GETMDA;
        this.setMEM = SETMDA;
        this.addr = addr;
        this.length = memlength;
    }
}
