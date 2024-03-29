package com.yang.serialport.ui;

/* DataWindow.java */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JFrame;

import java.text.DateFormat;
import java.util.Date;

/**
* Create a new window to graph the sensor readings in.
*
* @author Ron Goldman
*/
public class DataWindow extends JFrame {
private static final int MAX_SAMPLES = 10000;
private int index = 0;
private long[] time = new long[MAX_SAMPLES];
private int[] val = new int[MAX_SAMPLES];
DateFormat fmt = DateFormat.getDateTimeInstance();

/** Creates new form DataWindow */
public DataWindow() {
initComponents();
Point p = GraphicsEnvironment.getLocalGraphicsEnvironment()
.getCenterPoint();

setLocation(p.x - 500 / 2-6,390);
}

public DataWindow(String ieee) {
initComponents();
setTitle(ieee);
setLocation(500,350);
}

public void addData(long t, int v) {
time[index] = t;
val[index++] = v;
dataTextArea.append(fmt.format(new Date(t)) + "    value = " + v + "\n");
dataTextArea.setCaretPosition(dataTextArea.getText().length());
repaint();
}

// Graph the sensor values in the dataPanel JPanel
public void paint(Graphics g) {
super.paint(g);
int left = dataPanel.getX() + 10;       // get size of pane
int top = dataPanel.getY() + 30;
int right = left + dataPanel.getWidth() - 20;
int bottom = top + dataPanel.getHeight() - 20;

int y0 = bottom - 20;                   // leave some room for margins
int yn = top;
int x0 = left + 53;
int xn = right;
double vscale = (yn - y0) / 60.0;      // light values range from 0 to 800
double tscale = 1.0 / 1000.0;           // 1 pixel = 1 seconds = 1000 milliseconds

// draw X axis = time
g.setColor(Color.BLACK);
g.drawLine(x0, yn, x0, y0);
g.drawLine(x0, y0, xn, y0);
int tickInt = 60/2;
for (int xt = x0 + tickInt; xt < xn; xt += tickInt) {   // tick every 1 minute
g.drawLine(xt, y0 + 5, xt, y0 - 5);
int min = (xt - x0) / (60 / 2);
g.drawString(Integer.toString(min), xt - (min < 10 ? 3 : 7) , y0 + 20);
}

// draw Y axis = sensor reading
g.setColor(Color.RED);
for (int vt = 50; vt > 0; vt -=10) {         // tick every 200
int v = y0 + (int)(vt * vscale);
g.drawLine(x0 - 5, v, x0 + 5, v);
g.drawString(Integer.toString(vt), x0 - 38 , v + 5);
}

// graph sensor values
int xp = -1;
int vp = -1;
for (int i = 0; i < index; i++) {
int x = x0 + (int)((time[i] - time[0]) * tscale);
int v = y0 + (int)(val[i] * vscale);
if (xp > 0) {
g.drawLine(xp, vp, x, v);
}
xp = x;
vp = v;
}
}
public static void main (String args[]) {
java.awt.EventQueue.invokeLater(new Runnable() {
public void run() {
new DataWindow().setVisible(true);
}
});
}
/** This method is called from within the constructor to
* initialize the form.
* WARNING: Do NOT modify this code. The content of this method is
* always regenerated by the Form Editor.
*/
//@SuppressWarnings("unchecked")//////////////////////////////
// //GEN-BEGIN:initComponents
private void initComponents() {

dataPanel = new javax.swing.JPanel();
jScrollPane1 = new javax.swing.JScrollPane();
dataTextArea = new javax.swing.JTextArea();

dataPanel.setBackground(new java.awt.Color(255,255,255));
dataPanel.setMinimumSize(new java.awt.Dimension(494, 250));
dataPanel.setPreferredSize(new java.awt.Dimension(494, 250));
getContentPane().add(dataPanel, java.awt.BorderLayout.CENTER);

jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
jScrollPane1.setMinimumSize(new java.awt.Dimension(494, 60));
jScrollPane1.setPreferredSize(new java.awt.Dimension(494, 60));

dataTextArea.setColumns(20);
dataTextArea.setEditable(false);
dataTextArea.setRows(4);
jScrollPane1.setViewportView(dataTextArea);

getContentPane().add(jScrollPane1, java.awt.BorderLayout.SOUTH);

pack();
}// //GEN-END:initComponents

// Variables declaration - do not modify//GEN-BEGIN:variables
private javax.swing.JPanel dataPanel;
private javax.swing.JTextArea dataTextArea;
private javax.swing.JScrollPane jScrollPane1;
// End of variables declaration//GEN-END:variables

}

