package po23s.view;

import com.formdev.flatlaf.FlatLightLaf;
import po23s.http.BookApi;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;


@SuppressWarnings("FieldCanBeLocal")
public class TelaPrincipal extends javax.swing.JFrame {

    BookApi api;
    private final BookTableModel tableModel;


    public TelaPrincipal() {
        api = new BookApi();
        tableModel = new BookTableModel();

        initComponents();
        campoBusca.setText("Java");
        SwingUtilities.invokeLater(() -> {
            campoBusca.requestFocus();
        });
        // invoke search 1second from now
    }

    @SuppressWarnings({"unchecked", "Convert2Lambda", "Anonymous2MethodRef"})
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        botaoBusca = new javax.swing.JButton();
        campoBusca = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        botaoBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoBuscaActionPerformed(evt);
            }
        });

        campoBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoBuscaActionPerformed(evt);
            }
        });

        jTable1.setModel(tableModel);
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(campoBusca, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(botaoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(15, 15, 15)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(botaoBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(campoBusca)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE).addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void realizarBusca() {

        String query = campoBusca.getText();
        api.pesquisar(query, (result, erro) -> {
            if (erro != null) {
                erro.printStackTrace();
                return;
            }
            tableModel.setListaLivros(result.getItems());
            tableModel.fireTableDataChanged();
            campoBusca.setEnabled(true);
        });

    }

    private void botaoBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBuscaActionPerformed
        realizarBusca();
    }//GEN-LAST:event_botaoBuscaActionPerformed

    private void campoBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoBuscaActionPerformed
        realizarBusca();
    }//GEN-LAST:event_campoBuscaActionPerformed

    public static void main(String args[]) {
        FlatLightLaf.setup();


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setLocationRelativeTo(null);
            telaPrincipal.setVisible(true);
        });


    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoBusca;
    private javax.swing.JTextField campoBusca;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
