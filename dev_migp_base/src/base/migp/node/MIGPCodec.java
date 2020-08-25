/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.migp.node;

import base.migp.mem.NVPA;
import base.migp.mem.SRA;
import base.migp.reg.FMEG;
import base.migp.reg.IMEG;
import base.pro.convert.NahonConvert;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chejf
 */
public class MIGPCodec {

    /**
     * Message Packet Form:
     * |Head(4)|DstDev(1)|LocalDev(1)|CMD(1)|data(x)|CRC(1)|Tail(4)|
     *
     * @author jiche
     */
    public static final byte[] head = {(byte) 0x55, (byte) 0xAA, (byte) 0x7b, (byte) 0x7b};
    public static final byte[] tail = {(byte) 0x55, (byte) 0xAA, (byte) 0x7d, (byte) 0x7d};
    public static final int HeadLength = head.length;
    public static final int DstDevLength = 1;
    public static final int SrcDevLength = 1;
    public static final int CMDLength = 1;
    public static final int LRCLength = 1;
    public static final int TailLength = tail.length;

    /**
     * 打包数据,将数据打包成MIGP格式
     *
     * @param packet
     * @return
     */
    public static byte[] EncodeBuffer(MIGPPacket packet) {
        int offset = 0;
        byte[] buffer = new byte[HeadLength + DstDevLength
                + SrcDevLength + CMDLength + packet.data.length
                + LRCLength + TailLength];

        //添加包头Head(4)：55 AA 7B 7B
        System.arraycopy(head, 0, buffer, offset, HeadLength);
        offset += HeadLength;

        //添加目的地址DstDev(1)
        buffer[offset] = packet.dstAddress;
        offset += DstDevLength;

        //添加源地址LocalDev(1)
        buffer[offset] = packet.srcAddress;
        offset += SrcDevLength;

        //添加命令字CMD(1)
        buffer[offset] = packet.CMD;
        offset += CMDLength;

        //拷贝内容
        System.arraycopy(packet.data, 0, buffer, offset, packet.data.length);
        offset += packet.data.length;

        //LRC效验，累加所有字节
        for (int i = 0; i < packet.data.length; i++) {
            buffer[offset] += packet.data[i];
        }
        offset += LRCLength;

        //添加包尾Tail(4):55 AA 7D 7D
        System.arraycopy(tail, 0, buffer, offset, tail.length);
        offset += TailLength;

        return buffer;
    }

    /**
     * 解MIGP包数据
     *
     * @param rcbuffer
     * @param len
     * @return
     */
    public static MIGPPacket DecodeBuffer(byte[] rcbuffer, int len) {
        if (len > rcbuffer.length) {
            return null;
        }
        /* Get a complete data from rcbuffer*/
        byte[] migpFrame = FindeMigpFrameBuffer(rcbuffer, len);

        /* Check DevNum and LRC */
        if (CheckFrameLength(migpFrame) && CheckLRC(migpFrame)) {
            byte[] tmp = new byte[migpFrame.length - (HeadLength + DstDevLength
                    + SrcDevLength + CMDLength + LRCLength + TailLength)];
            /* Get MIGP packet data flow */
            System.arraycopy(migpFrame,
                    (HeadLength + DstDevLength + SrcDevLength + CMDLength), tmp, 0, tmp.length);

            return new MIGPPacket(migpFrame[4], migpFrame[5], migpFrame[6], tmp);
        } else {
            return null;
        }
    }

    /**
     * check migp packet frame length
     *
     * @param packet
     * @return
     */
    private static boolean CheckFrameLength(byte[] tmpbuffer) {
        if (tmpbuffer == null) {
            return false;
        }

        // a complete migp packet must contain head, dstdev, srcdev, cmd, lrc, tail
        if (tmpbuffer.length < ((HeadLength + DstDevLength
                + SrcDevLength + CMDLength + LRCLength + TailLength))) {
            Logger.getGlobal().log(Level.SEVERE, "Packet length is less than minpacket length.");
            return false;
        }

        return true;
    }

    /**
     * check migp packet LRC
     *
     * @param packet
     * @return
     */
    private static boolean CheckLRC(byte[] tmpbuffer) {
        int lrc = 0x00;

        /* check the lrc */
        for (int i = (HeadLength + DstDevLength + SrcDevLength + CMDLength);
                i < tmpbuffer.length - (LRCLength + TailLength); i++) {
            lrc += 0xFF & tmpbuffer[i];
        }

        /* if crc is not correct, the tmpbuffer will be dropped */
        if ((byte) lrc != tmpbuffer[(tmpbuffer.length - (LRCLength + TailLength))]) {
            Logger.getGlobal().log(Level.SEVERE, "Packet LRC is wrong!");
            return false;
        }
        return true;
    }

    /**
     * Find out a complete MIGP packet from data buffer
     *
     * @param packet
     * @return a complete migp packet
     */
    private static byte[] FindeMigpFrameBuffer(byte[] rcbuffer, int length) {
//        byte[] tmp = rcbuffer;

        int startpoint = 0;
        boolean startfounded = false;
        boolean endfounded = false;
        int endpoint = 0;

        for (int i = 0; i < length; i++) {
            /* must bigger than a headlength */
            if (i + head.length > length) {
                break;
            }

            /* find migp packet head position */
            if (!startfounded) {
                if ((rcbuffer[i] == head[0])
                        && (rcbuffer[i + 1] == head[1])
                        && (rcbuffer[i + 2] == head[2])
                        && (rcbuffer[i + 3] == head[3])) {
                    startpoint = i;
                    startfounded = true;
                }
            }

            /* found migp packet end position */
            if (!endfounded) {
                if ((rcbuffer[i] == tail[0])
                        && (rcbuffer[i + 1] == tail[1])
                        && (rcbuffer[i + 2] == tail[2])
                        && (rcbuffer[i + 3] == tail[3])) {
                    endpoint = i + 4;
                    endfounded = true;
                    if (startfounded) {
                        break;
                    }
                }
            }
        }

        if (endpoint > startpoint) {
            /* remove founded migp packet and data before it form rcbuffer pool */
//            for (int i = 0; i < endpoint; i++) {
//                rcbuffer.poll();
//            }

            /* copy the data buffer */
            byte[] rcpacket = new byte[endpoint - startpoint];
            System.arraycopy(rcbuffer, startpoint, rcpacket, 0, endpoint - startpoint);
            return rcpacket;
        } else {
            return null;
        }
    }

    public static void main(String... args) throws Exception {
        FMEG testa = new FMEG(new NVPA(0, 4), "");
        testa.SetValue(Float.valueOf("0.0"));
        byte[] aaa = testa.ToBytes();
//        String test = "55 AA 7B 7B F0 02 E0 00 00 00 00 00 00 00 00 00 00 BD B8 3C F3 42 18 F4 76 F7 DF FD DF FF 00 00 00 00 00 00 00 00 00 00 00 40 55 AA 7D 7D";
        String test = "55 AA 7B 7B F0 0A F0 00 00 00 0C 00 68 C7 EE 41 F1 49 7C 20 55 AA 7D 7D";
        String[] inputs = test.split(" ");
        byte[] data = new byte[inputs.length];
        byte ret = 0;
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) Integer.parseInt(inputs[i], 16);
            System.out.print(data[i] + " ");
            ret += data[i];
        }

        MIGPPacket DecodeBuffer = MIGPCodec.DecodeBuffer(data, data.length);
        System.out.println();
        System.out.println(NahonConvert.ByteArrayToUShort(new byte[]{(byte) 0xC7, (byte) 0xEE}, 0));
        IMEG treg = new IMEG(new SRA(0x0C, 2), "原始光强信号(高电平)");
        treg.LoadBytes(new byte[]{(byte) 0xC7, (byte) 0xEE}, 0);
        System.out.println(treg.GetValue());
    }

}
