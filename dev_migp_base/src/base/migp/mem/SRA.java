/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.mem;

import static base.migp.node.MIGP_CmdSend.GETSRA;
import static base.migp.node.MIGP_CmdSend.SETSRA;

/**
 *
 * @author chejf
 */
public class SRA extends MEM {

    public SRA(int addr, int memlength) {
        this.getMEM = GETSRA;
        this.setMEM = SETSRA;
        this.addr = addr;
        this.length = memlength;
    }
}
