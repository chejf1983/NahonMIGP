/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.mem;

import static base.migp.node.MIGP_CmdSend.GETEIA;
import static base.migp.node.MIGP_CmdSend.SETEIA;

/**
 *
 * @author chejf
 */
public class EIA extends MEM {

    public EIA(int addr, int memlength) {
        getMEM = GETEIA;
        setMEM = SETEIA;
        this.addr = addr;
        this.length = memlength;
    }
}

