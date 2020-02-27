/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.node;

import base.migp.mem.MEM;
import base.migp.reg.MEG;
import base.pro.convert.NahonConvert;
import base.pro.absractio.AbstractIO;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author jiche
 */
public class MIGP_CmdSend extends MIGPNode {

    public MIGP_CmdSend(AbstractIO physicalInterface, byte localAddr, byte dstAddr) {
        super(physicalInterface, localAddr);
        this.dstAddr = dstAddr;
        this.maxbufferlen = physicalInterface.MaxBuffersize();
    }

    // <editor-fold defaultstate="collapsed" desc="MIGP Information">
    private byte dstAddr;

    public byte GetDstAddr() {
        return this.dstAddr;
    }

    public void SetDstAddr(byte dstAddr) {
        this.dstAddr = dstAddr;
    }

    private int maxbufferlen = 65535;

    // <editor-fold defaultstate="collapsed" desc="MIGP Common CMD Key">
    public static final byte GET_DEV_STATE_CMD = 0x03;
    public static final byte SET_DEVNUM = 0x05;
    public static final byte REBOOT_CMD = 0x06;
    public static final byte HALT_BOOTMODE_CMD = 0x20;
    public static final byte IC_ADDR_QEURE_CMD = 0x21;
    public static final byte SYSTEM_JUMP_CMD = 0x22;
    public static final byte FLASH_CLEAN_CMD = 0x28;
    public static final byte FLASH_WRITE_CMD = 0x29;
    public static final byte FLASH_READ_CMD = 0x2A;

    public static final byte GETEIA = 0x30;
    public static final byte SETEIA = 0x31;
    public static final byte GETVPA = 0x40;//vpa sra
    public static final byte SETVPA = 0x41;
    public static final byte GETNVPA = 0x50;//nvpa spa
    public static final byte SETNVPA = 0x51;
    public static final byte GETMDA = 0x60;
    public static final byte SETMDA = 0x61;
    public static final byte GETSRA = 0x70;
    public static final byte SETSRA = 0x71;

    public static final byte TRUE = 0x66;
    public static final byte FALSE = (byte) 0x88;

    public static int FLASH_SPAN_LENGTH = 2048;
    // </editor-fold> 

    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="migp data send interface"> 
    /**
     * check packet cmd
     *
     * @param CMD
     * @param packet
     * @return
     * @throws Exception
     */
    private boolean CheckrPktCMD(int CMD, MIGPPacket packet) {
        if (packet == null) {
            return false;
        }
        //receive cmd must equal to send cmd | 0x80
        return packet.CMD == (byte) (CMD | 0x80);
    }

    public synchronized byte[] SendRPC(byte cmd, byte[] data, int retry, int timeout) throws Exception, TimeoutException {
        if (retry > 10) {
            throw new Exception("重试过多");
        }
        for (int i = 0; i < retry; i++) {
            this.nodelocker.lock();
            try {
                this.SendCMD(cmd, data);
                byte[] ret = this.ReceiveCMD(cmd, timeout);
                if (ret.length > 0) {
                    return ret;
                }
            } catch (Exception ex) {
                if (ex instanceof TimeoutException) {
                } else {
                    throw ex;
                }
            } finally {
                this.nodelocker.unlock();
            }
        }

        throw new TimeoutException("超时" + retry);
    }

    public synchronized void SendCMD(byte cmd, byte[] data) throws Exception {
        this.SendMIGPPacket(dstAddr, cmd, data);
    }

    public synchronized byte[] ReceiveCMD(final int cmd, int timeout) throws Exception, TimeoutException {
        //if receive an error migp packet, retry 3 times
//        for (int i = 0; i < 3; i++) {
        //if receive timout, it will break here, 
        MIGPPacket rePkt = ReceiveMIGPPacket(timeout);

        if (rePkt == null) {
            //如果没有数据，超时
            throw new TimeoutException("超时");
        }

        //check cmd
        if (CheckrPktCMD(cmd, rePkt) && this.checkPktSrcAddr(rePkt)) {
            //找到数据，break
            return rePkt.data;
        }
        throw new TimeoutException("超时");
    }

    private boolean checkPktSrcAddr(MIGPPacket pkt) {
        if (pkt == null) {
            return false;
        }

        if (pkt.srcAddress == this.dstAddr || this.dstAddr == 0) {
            return true;
        } else {
            return false;
        }
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="memory interface"> 
//    public boolean SetMEM(MEM MEM_ID, byte[] data, int timeout) throws TimeoutException, Exception {
//        return this.SetMEM(MEM_ID, MEM_ID.length, data, timeout);
//    }
    /**
     *
     * @param MEM_ID
     * @param MEM_Length
     * @param timeout
     * @param retry
     * @param data
     * @return
     * @throws java.util.concurrent.TimeoutException
     */
    public synchronized boolean SetMEM(MEM MEM_ID, int MEM_Length, byte[] data, int retry, int timeout) throws TimeoutException, Exception {
        for (int sendlen = 0; sendlen < MEM_Length; sendlen += maxbufferlen) {
            int slicelen = 0;

            if (MEM_Length - sendlen > maxbufferlen) {
                //if left buffer is bigger than one max usb slice send a complete slice
                slicelen = maxbufferlen;
            } else {
                //send left buffer
                slicelen = MEM_Length - sendlen;
            }

            byte[] slicedata = new byte[slicelen];
            System.arraycopy(data, sendlen, slicedata, 0, slicelen);

            //if one slice failed, all failed
            if (!this.SetSingelMemory(MEM_ID.setMEM, MEM_ID.addr + sendlen, slicelen, slicedata, retry, timeout)) {
                return false;
            }
        }
        return true;
    }

    private boolean SetSingelMemory(byte MEM_ID, int MEM_Addr, int MEM_Length, byte[] data, int retry, int timeout) throws TimeoutException, Exception {
        byte[] sbuffer = new byte[8 + MEM_Length];
        System.arraycopy(NahonConvert.IntegerToByteArray(MEM_Addr), 0, sbuffer, 0, 4);
        System.arraycopy(NahonConvert.IntegerToByteArray(MEM_Length), 0, sbuffer, 4, 4);

        System.arraycopy(data, 0, sbuffer, 8, MEM_Length);

        byte[] rcdata = this.SendRPC(MEM_ID, sbuffer, retry, timeout);

        return rcdata[0] == TRUE;
    }

//    public byte[] GetMEM(MEM MEM_ID, int timeout) throws Exception, TimeoutException {
//        return this.GetMEM(MEM_ID, MEM_ID.length, timeout);
//    }
//
//    public synchronized byte[] GetMEM(MEM MEM_ID, int MEM_Length, int timeout) throws Exception, TimeoutException {
//        return this.GetMEM(MEM_ID, MEM_Length, 3, timeout);
//    }
    public synchronized byte[] GetMEM(MEM MEM_ID, int MEM_Length, int retry, int timeout) throws Exception, TimeoutException {
        byte[] sbuffer = new byte[8];
        System.arraycopy(NahonConvert.IntegerToByteArray(MEM_ID.addr), 0, sbuffer, 0, 4);
        System.arraycopy(NahonConvert.IntegerToByteArray(MEM_Length), 0, sbuffer, 4, 4);
        byte[] data = this.SendRPC(MEM_ID.getMEM, sbuffer, retry, timeout);
        int memaddr = NahonConvert.ByteArrayToInteger(data, 0);

        //check memaddr and length
        if (memaddr == MEM_ID.addr && data.length == MEM_Length + 4) {
            byte[] tmp = new byte[MEM_Length];
            System.arraycopy(data, 4, tmp, 0, MEM_Length);
            return tmp;
        } else {
            throw new Exception("返回数据异常");
        }
    }

    //批量读寄存器
    public void ReadMEG(int retry_time, int timeout, MEG... reg) throws Exception {
        MEG min_reg = reg[0];
        MEG max_reg = reg[0];

        //找到最小最大寄存器位置
        for (MEG treg : reg) {
            if (min_reg.GetMEM().addr > treg.GetMEM().addr) {
                min_reg = treg;
            }
            if (max_reg.GetMEM().addr < treg.GetMEM().addr) {
                max_reg = treg;
            }
        }
        //读取最小最大寄存器对应的内容
        byte[] memory = this.GetMEM(min_reg.GetMEM(), max_reg.GetMEM().addr + max_reg.GetMEM().length - min_reg.GetMEM().addr, retry_time, timeout);
        for (MEG treg : reg) {
            //每个寄存器初始化内存，地址和内存有2倍关系
            treg.LoadBytes(memory, (treg.GetMEM().addr - min_reg.GetMEM().addr));
        }
    }

    //批量读寄存器
    public void SetMEG(int retry_time, int timeout, MEG... reg) throws Exception {
        MEG min_reg = reg[0];
        MEG max_reg = reg[0];

        //找到最小最大寄存器位置
        for (MEG treg : reg) {
            if (min_reg.GetMEM().addr > treg.GetMEM().addr) {
                min_reg = treg;
            }
            if (max_reg.GetMEM().addr < treg.GetMEM().addr) {
                max_reg = treg;
            }
        }
        //开辟写内存大小，等于最大最小寄存器地址差+最大寄存器长度 * 2
        byte[] memory = new byte[(max_reg.GetMEM().addr + max_reg.GetMEM().length - min_reg.GetMEM().addr)];
        for (MEG treg : reg) {
            //复制出内存
            System.arraycopy(treg.ToBytes(), 0, memory, (treg.GetMEM().addr - min_reg.GetMEM().addr), treg.GetMEM().length);
        }
        this.SetMEM(min_reg.GetMEM(), memory.length, memory, retry_time, timeout);
    }
    // </editor-fold> 
}
