/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.impl;

import base.pro.data.ICaddr;
import java.util.concurrent.TimeUnit;
import base.pro.convert.NahonConvert;
import base.migp.node.MIGP_CmdSend;
import static base.migp.node.MIGP_CmdSend.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chejf
 */
public class MIGPBoot {

    private final MIGP_CmdSend migpsend;
    private int retry_time = 3;

    public MIGPBoot(MIGP_CmdSend migpnode) {
        this.migpsend = migpnode;
    }

    public Boolean HaltBoot(int timeout) throws Exception {
        try {
            byte[] data = this.migpsend.SendRPC(HALT_BOOTMODE_CMD, new byte[]{0x00}, 1, timeout);

            return data[0] == TRUE;
        } catch (Exception ex) {
            if (ex instanceof TimeoutException) {
                return false;
            } else {
                throw ex;
            }
        }
    }

    public ICaddr GetICInfomation(int timeout) throws Exception {
        byte[] data = this.migpsend.SendRPC(IC_ADDR_QEURE_CMD, new byte[]{0x00}, retry_time, timeout);
        //根据返回长度，初始化icaddr，默认返回所有核的IC地址组
        ICaddr icaddr = new ICaddr(data.length / 8);

        for (int i = 0; i < icaddr.corenum; i++) {
            icaddr.startaddr[i] = NahonConvert.ByteArrayToInteger(data, 0 + i * 8);
            icaddr.endaddr[i] = NahonConvert.ByteArrayToInteger(data, 4 + i * 8);

            //如果当前地址组，开始，结束地址相同，表示从次核开始，都为空地址，不存在
            if (icaddr.startaddr == icaddr.endaddr) {
                icaddr.corenum = i;
                break;
            }
        }
        return icaddr;
    }

    public Boolean ClearFlash(int blockNum, int timeout) throws Exception {
        byte[] data = this.migpsend.SendRPC(FLASH_CLEAN_CMD, NahonConvert.UShortToByteArray(blockNum), retry_time, timeout);

        return data[0] == TRUE;
    }

    public byte[] ReadFlash(int blockNum, int timeout) throws Exception {

        byte[] data = this.migpsend.SendRPC(FLASH_READ_CMD, NahonConvert.UShortToByteArray(blockNum), retry_time, timeout);

        if (data.length == 1 && data[0] == FALSE) {
            throw new Exception("Read Flash block num: " + blockNum + " failed");
        } else if (data.length <= FLASH_SPAN_LENGTH) {
            return data;
        } else {
            throw new Exception("Read Flash block num: " + blockNum + " failed");
        }
    }

    public Boolean WriteFlash(int blockNum, byte[] data, int timeout) throws Exception {
        byte[] tmp = new byte[2 + data.length];
        System.arraycopy(NahonConvert.UShortToByteArray((short) blockNum), 0, tmp, 0, 2);
        System.arraycopy(data, 0, tmp, 2, data.length);

        try {
            byte[] rcdata = this.migpsend.SendRPC(FLASH_WRITE_CMD, tmp, retry_time, timeout);
            return rcdata[0] == TRUE;
        } catch (Exception ex) {
            if (ex instanceof TimeoutException) {
                Logger.getGlobal().log(Level.SEVERE, ex.getMessage());
                return false;
            } else {
                throw ex;
            }
        }
    }

    public void StartApp(int timeout) throws Exception {
        this.migpsend.SendCMD(SYSTEM_JUMP_CMD, new byte[]{0x00});
        TimeUnit.MILLISECONDS.sleep(timeout);
    }

    public void ReBoot(int timeout) throws Exception {
        this.migpsend.SendCMD(REBOOT_CMD, new byte[]{0x00});
        TimeUnit.MILLISECONDS.sleep(timeout);
    }

    public boolean SetDevNum(byte devaddr) throws Exception {
        int timeout = 100;

        byte[] data = this.migpsend.SendRPC(SET_DEVNUM, new byte[]{devaddr}, retry_time, timeout);

        return data[0] == TRUE;
    }
}
