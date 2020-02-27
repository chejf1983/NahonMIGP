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
    private final byte[] rcbuffer = new byte[MAX_RCBUFFER_SIZE];
    private static final int TMP_BUFFER_SIZE = 20480;
    private static final int MAX_RCBUFFER_SIZE = 102400;
    private static final int MAXTIMEOUT = 100;
    private boolean iscanceled = false;

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
            byte[] tmpdata = new byte[TMP_BUFFER_SIZE];
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
//
//    public synchronized MIGPPacket ReceiveMIGPPacket(int timeout) throws Exception{
//        this.nodelocker.lock();
//        MIGPPacket packet = null;
//
//        try {
//            int timeindex = 0; //时间计数器
//            int rclen = 0;
////            this.iscanceled = false; //是否取消
////            int lag = 0;
//            int len = 0;
//            byte[] tmpdata = new byte[TMP_BUFFER_SIZE];
//
//            while (!iscanceled) {
//                //receive data
//                len = physicalInterface.ReceiveData(tmpdata, MAXTIMEOUT);
//
//                if (len > 0) {
//                    //move tmp data to recevei buffer pool
//                    System.arraycopy(tmpdata, 0, rcbuffer, rclen, len);
//                    rclen += len;
//                    //寻找有效包
//                    packet = MIGPCodec.DecodeBuffer(rcbuffer);
//                    //找到包，跳出循环，返回有效包
//                    if (packet != null) {
//                        break;
//                    }
//                } else {
//                    //计时器增加
//                    timeindex += MAXTIMEOUT;
//                    if (timeindex >= timeout) {
//                        //超时，返回空包
//                        //System.out.println("超时" + timeout);
//                        return null;
//                    } 
//                }                
//            }
//
////            if (this.iscanceled) {
////                // this.nodelocker.unlock();
////                throw new MCancelException("Canceled");
////            }
//
//            //no packet found, means timeout
//            return packet;
//        } finally {
//            this.nodelocker.unlock();
//        }
//    }

//    public boolean IsCanceled() {
//        return this.iscanceled;
//    }
//    
//    public void Cancel() {
//        this.iscanceled = true;
//
//        while (this.nodelocker.isLocked()) {
//            //只要nodelock还锁住，表示node还在接数据，将canceled设置成true;
//            this.iscanceled = true;
//            try {
//                TimeUnit.MILLISECONDS.sleep(10);
//            } catch (InterruptedException ex) {
//
//            }
//        }
//    }
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
