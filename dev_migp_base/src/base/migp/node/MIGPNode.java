/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.node;

import java.util.concurrent.locks.ReentrantLock;
import base.pro.absractio.AbstractIO;

/**
 * MIGP IO one migp io bind one real io one real io can be bind to serveral migp
 * io
 *
 * @author jiche
 */
public class MIGPNode {

    /**
     * Physical Interface which already Opened. That can be used to send and
     * receive data directly.
     */
    // <editor-fold defaultstate="collapsed" desc="IO Control"> 
    protected final AbstractIO physicalInterface;
    protected final byte localAddr;
    protected final ReentrantLock nodelocker = new ReentrantLock();

    /**
     *
     * @param physicalInterface
     * @param localAddr
     */
    public MIGPNode(AbstractIO physicalInterface, byte localAddr) {
        this.physicalInterface = physicalInterface;
        this.localAddr = localAddr;
    }

    public byte GetLocalAddr() {
        return this.localAddr;
    }

    public AbstractIO GetIO() {
        return this.physicalInterface;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="migp packet process"> 
    private byte[] rcbuffer = new byte[MAX_RCBUFFER_SIZE];
    private byte[] tmpdata = new byte[TMP_BUFFER_SIZE];
    private static final int TMP_BUFFER_SIZE = 2078;//2048+head < 2078
    private static final int MAX_RCBUFFER_SIZE = 5000;
    private static final int MAXTIMEOUT = 50;

    /**
     *
     * @param dstAddress
     * @param cmd
     * @param data
     * @throws java.lang.Exception
     */
    public synchronized void SendMIGPPacket(byte dstAddress, byte cmd, byte[] data) throws Exception {
        this.nodelocker.lock();
        try {
//            rcbuffer.clear();
            this.physicalInterface.SendData(
                    MIGPCodec.EncodeBuffer(new MIGPPacket(dstAddress, this.GetLocalAddr(), cmd, data)));
        } finally {
            this.nodelocker.unlock();
        }
    }

    /**
     * receive a migp packet from real io.
     *
     * @param timeout
     * @return
     * @throws java.lang.Exception
     */
    public synchronized MIGPPacket ReceiveMIGPPacket(int timeout) throws Exception {
        this.nodelocker.lock();

        try {
            int rc_len = 0;
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < timeout) {
                //receive data
                int len = physicalInterface.ReceiveData(tmpdata, timeout);
                System.arraycopy(tmpdata, 0, rcbuffer, rc_len, len);
                rc_len += len;
                MIGPPacket pkt = MIGPCodec.DecodeBuffer(rcbuffer, rc_len);
                if (pkt != null) {
                    return pkt;
                }
//                System.out.println(timeout);
            }
            return null;
        } finally {
            this.nodelocker.unlock();
        }
    }

    private boolean checkAddr(MIGPPacket pkt) {
        if (pkt == null) {
            return false;
        }

        if (pkt.dstAddress == 0 || pkt.dstAddress == this.localAddr) {
            return true;
        } else {
            return false;

        }
    }
    // </editor-fold> 
}
