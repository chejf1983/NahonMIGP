/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.dev.window;

import nahon.comm.tool.convert.MyConvert;
import test.dev.basedev.MockDev;
import nahon.comm.event.EventListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import base.pro.data.EquipmentInfo;
import static base.migp.impl.MIGPEia.*;
import java.awt.Toolkit;
import java.util.concurrent.ExecutorService;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import test.dev.basedev.TCPHost;

/**
 *
 * @author jiche
 */
public final class MockeDevForm extends javax.swing.JFrame {

    private MockDev mockdev;

    //<editor-fold defaultstate="collapsed" desc=" 初始化 ">
    /**
     * Creates new form MockerDevice
     */
    public MockeDevForm() {
        initComponents();

        this.InitMockeDevForm();

        setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/test/dev/resource/disconnect_1.png")));

        //居中显示
        setLocationRelativeTo(null);

    }

    private void InitMockeDevForm() {
        this.SetMockDev(new MockDev((byte) 0x03));

        this.ComboBox_MEM.removeAllItems();
        for (String it : MemoryConvert.GetCMDList()) {
            this.ComboBox_MEM.addItem(it);
        }
        this.ComboBox_Type.removeAllItems();
        for (String it : MemoryConvert.GetInputType()) {
            this.ComboBox_Type.addItem(it);
        }

        try {
            this.HostIP.setText(InetAddress.getLocalHost().getHostAddress());
            this.PortNum.setText("2000");
        } catch (UnknownHostException ex) {
            Logger.getLogger(MockeDevForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SetMockDev(MockDev dev) {
        this.mockdev = dev;
    }
    // </editor-fold>     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        HostIP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        PortNum = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        SET1 = new javax.swing.JButton();
        GET = new javax.swing.JButton();
        ToggleButtonRadom = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        InputMem = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        ComboBox_MEM = new javax.swing.JComboBox();
        MEM_ADDR = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        MEM_LEN = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ComboBox_Type = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        EIA_Table = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        Menu_Open = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("主机控制"));

        HostIP.setText("jTextField1");

        jLabel7.setText("IP地址:");

        jLabel8.setText("端口号:");

        PortNum.setText("jTextField1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(HostIP, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(PortNum))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {HostIP, PortNum});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(HostIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(PortNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("内存修改"));

        SET1.setText("设置");
        SET1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SET1ActionPerformed(evt);
            }
        });

        GET.setText("读取");
        GET.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GETActionPerformed(evt);
            }
        });

        ToggleButtonRadom.setText("随机输入");
        ToggleButtonRadom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToggleButtonRadomActionPerformed(evt);
            }
        });

        InputMem.setColumns(20);
        InputMem.setRows(5);
        jScrollPane1.setViewportView(InputMem);

        jLabel3.setText("MEM");

        ComboBox_MEM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        MEM_ADDR.setText("2");

        jLabel4.setText("Addr");

        MEM_LEN.setText("4");

        jLabel6.setText("len");

        jLabel5.setText("type");

        ComboBox_Type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboBox_MEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(MEM_ADDR, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(MEM_LEN, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboBox_Type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ToggleButtonRadom, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SET1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GET, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox_MEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MEM_ADDR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MEM_LEN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBox_Type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SET1)
                    .addComponent(GET)
                    .addComponent(ToggleButtonRadom)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("EIA信息"));

        EIA_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "标题 1", "标题 2"
            }
        ));
        jScrollPane2.setViewportView(EIA_Table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        Menu_Open.setText("打开");
        Menu_Open.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Menu_OpenMouseClicked(evt);
            }
        });
        jMenuBar1.add(Menu_Open);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc=" 内存设置 ">
    private void ToggleButtonRadomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToggleButtonRadomActionPerformed
        if (this.ToggleButtonRadom.isSelected()) {
            this.ComboBox_Type.setSelectedIndex(3);
            Executors.newSingleThreadExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    Random R = new Random();
                    while (ToggleButtonRadom.isSelected()) {
                        float input = R.nextFloat() * 100;
                        InputMem.setText(new DecimalFormat("0.00").format(input));
                        SetValue();
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (Exception ex) {
                        }
                    }
                }
            });
        }
    }//GEN-LAST:event_ToggleButtonRadomActionPerformed

    private void GETActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GETActionPerformed
        try {
            byte[] value = mockdev.memory.GetValue(ConvertCMD(ComboBox_MEM.getSelectedItem().toString()),
                    Integer.valueOf(MEM_ADDR.getText()), Integer.valueOf(MEM_LEN.getText()));

            String output = MemoryConvert.ConvertByteToString(this.ComboBox_Type.getSelectedItem().toString(), value);
            this.InputMem.setText(output);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(MockeDevForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_GETActionPerformed

    private byte[] ConvertCMD(String cmd) {
        switch (cmd) {
            case "EIA":
                return this.mockdev.memory.EIA;
            case "VPA":
                return this.mockdev.memory.VPA;
            case "NVPA":
                return this.mockdev.memory.NVPA;
            case "MDA":
                return this.mockdev.memory.MDA;
            case "SRA":
                return this.mockdev.memory.SRA;
        }
        return this.mockdev.memory.EIA;
    }

    private void SetValue() {
        try {
            mockdev.memory.SetValue(ConvertCMD(ComboBox_MEM.getSelectedItem().toString()),
                    Integer.valueOf(MEM_ADDR.getText()), Integer.valueOf(MEM_LEN.getText()),
                    new MemoryConvert().ConvertMemToByte(ComboBox_Type.getSelectedItem().toString(), InputMem.getText()));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(MockeDevForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void SET1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SET1ActionPerformed
        try {
            mockdev.memory.SetValue(ConvertCMD(ComboBox_MEM.getSelectedItem().toString()),
                    Integer.valueOf(MEM_ADDR.getText()), Integer.valueOf(MEM_LEN.getText()),
                    new MemoryConvert().ConvertMemToByte(this.ComboBox_Type.getSelectedItem().toString(), InputMem.getText()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(MockeDevForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SET1ActionPerformed
    // </editor-fold>  

    //<editor-fold defaultstate="collapsed" desc=" 主机控制 ">
    private TCPHost hosttcp = new TCPHost();

    public void Open() throws Exception {
        if (this.hosttcp.IsClosed()) {
            this.hosttcp.Listen(this.HostIP.getText(),
                    Integer.valueOf(this.PortNum.getText()),
                    mockdev);
            this.UpdateEIAInfo();
            Menu_Open.setText("关闭");
            setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/test/dev/resource/connect_1.png")));
            //ConnectStateIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/test/dev/resource/connect_1.png")));
        }
    }

    public void Close() {
        if (!this.hosttcp.IsClosed()) {
            this.hosttcp.Close();
            Menu_Open.setText("打开");
            setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/test/dev/resource/disconnect_1.png")));
            //ConnectStateIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/test/dev/resource/disconnect_1.png")));
        }
    }

    private EquipmentInfo eia;

    ExecutorService thread = Executors.newSingleThreadExecutor();

    public void UpdateEIAInfo() {
        this.eia = this.ReadEia();
        this.EIA_Table = new JTable();
        this.EIA_Table.setTableHeader(null);
        this.EIA_Table.setModel(new AbstractTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return true;
            }

            @Override
            public int getRowCount() {
                return 5;
            }

            @Override
            public int getColumnCount() {
                return 1;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    byte[] tmp;
                    switch (rowIndex) {
                        case 0:
                            return eia.DeviceName;
                        case 1:
                            return eia.BuildDate;
                        case 2:
                            return eia.BuildSerialNum;
                        case 3:
                            return eia.Hardversion;
                        case 4:
                            return eia.SoftwareVersion;
                        case 5:
                            break;
                    }
                }
                return null;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                if (columnIndex == 0) {
                    try {
                        switch (rowIndex) {
                            case 0:
                                mockdev.memory.SetValue(mockdev.memory.EIA, DeviceName.addr, DeviceName.length,
                                        MyConvert.StringToByte(aValue.toString(), DeviceName.length));
                                break;
                            case 1:
                                mockdev.memory.SetValue(mockdev.memory.EIA, BuildDate.addr, BuildDate.length,
                                        MyConvert.StringToByte(aValue.toString(), BuildDate.length));
                                break;
                            case 2:
                                mockdev.memory.SetValue(mockdev.memory.EIA, BuildSerialNum.addr, BuildSerialNum.length,
                                        MyConvert.StringToByte(aValue.toString(), BuildSerialNum.length));
                                break;
                            case 3:
                                mockdev.memory.SetValue(mockdev.memory.EIA, Hardversion.addr, Hardversion.length,
                                        MyConvert.StringToByte(aValue.toString(), Hardversion.length));
                                break;
                            case 4:
                                mockdev.memory.SetValue(mockdev.memory.EIA, SoftwareVersion.addr, SoftwareVersion.length,
                                        MyConvert.StringToByte(aValue.toString(), SoftwareVersion.length));
                                break;
                            case 5:
                                break;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(MockeDevForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        this.jScrollPane2.setViewportView(this.EIA_Table);
        mockdev.memory.UpdateMem.RegeditListener(new EventListener() {
            @Override
            public void recevieEvent(nahon.comm.event.Event event) {
                if (event.GetEvent() == mockdev.memory.EIA) {
                    SwingUtilities.invokeLater(() -> {
                        eia = ReadEia();
                        EIA_Table.updateUI();
                    });
                }
            }
        });
    }

    private EquipmentInfo ReadEia() {
        EquipmentInfo teia = new EquipmentInfo();
        try {
            byte[] tmp;
            tmp = mockdev.memory.GetValue(mockdev.memory.EIA, DeviceName.addr, DeviceName.length);
            teia.DeviceName = MyConvert.ByteArrayToString(tmp, 0, tmp.length);
            tmp = mockdev.memory.GetValue(mockdev.memory.EIA, BuildDate.addr, BuildDate.length);
            teia.BuildDate = MyConvert.ByteArrayToString(tmp, 0, tmp.length);

            tmp = mockdev.memory.GetValue(mockdev.memory.EIA, BuildSerialNum.addr, BuildSerialNum.length);
            teia.BuildSerialNum = MyConvert.ByteArrayToString(tmp, 0, tmp.length);

            tmp = mockdev.memory.GetValue(mockdev.memory.EIA, Hardversion.addr, Hardversion.length);
            teia.Hardversion = MyConvert.ByteArrayToString(tmp, 0, tmp.length);

            tmp = mockdev.memory.GetValue(mockdev.memory.EIA, SoftwareVersion.addr, SoftwareVersion.length);
            teia.SoftwareVersion = MyConvert.ByteArrayToString(tmp, 0, tmp.length);
        } catch (Exception ex) {

        }

        return teia;
    }
    private void Menu_OpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Menu_OpenMouseClicked
        if (this.hosttcp.IsClosed()) {
            try {
                Open();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else {
            this.Close();
        }
    }//GEN-LAST:event_Menu_OpenMouseClicked
    // </editor-fold>   

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//            de.javasoft.plaf.synthetica.
            javax.swing.UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MockeDevForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MockeDevForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ComboBox_MEM;
    private javax.swing.JComboBox ComboBox_Type;
    private javax.swing.JTable EIA_Table;
    private javax.swing.JButton GET;
    private javax.swing.JTextField HostIP;
    private javax.swing.JTextArea InputMem;
    private javax.swing.JTextField MEM_ADDR;
    private javax.swing.JTextField MEM_LEN;
    private javax.swing.JMenu Menu_Open;
    private javax.swing.JTextField PortNum;
    private javax.swing.JButton SET1;
    private javax.swing.JToggleButton ToggleButtonRadom;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
