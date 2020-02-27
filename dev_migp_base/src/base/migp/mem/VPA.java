/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.mem;

import static base.migp.node.MIGP_CmdSend.GETVPA;
import static base.migp.node.MIGP_CmdSend.SETVPA;

/**
 *
 * @author chejf
 */
public class VPA extends MEM {

    public VPA(int addr, int memlength) {
        this.getMEM = GETVPA;
        this.setMEM = SETVPA;
        this.addr = addr;
        this.length = memlength;
    }
}
