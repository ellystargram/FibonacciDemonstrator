import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.JPanel;
import java.awt.event.MouseWheelListener;

import XWindow.XWindow;

public class FibonacciArray {
    XWindow xWindow = new XWindow(1000, 800, "Fibonacci Array", true);
    int scroll = 0;
    final int rowsPerPage = 10;
    ArrayList<BigInteger> fibonacciArray = new ArrayList<BigInteger>();
    ArrayList<BigInteger> importantMarking = new ArrayList<BigInteger>();

    public static void main(String[] args) {
        new FibonacciArray();
    }

    public FibonacciArray() {
        importantMarking.add(new BigInteger("3"));
        importantMarking.add(new BigInteger("4"));
        importantMarking.add(new BigInteger("5"));
        importantMarking.add(new BigInteger("6"));
        importantMarking.add(new BigInteger("9"));
        importantMarking.add(new BigInteger("10"));
        importantMarking.add(new BigInteger("11"));
        importantMarking.add(new BigInteger("69"));
        for (int i = 1; i <= 140; i++) {
            fibonacciArray.add(getFibonacci(new BigInteger(i + "")));
        }
        JPanel legend = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setFont(new Font("", 0, 30));
                g.setColor(Color.white);
                int legendHeight = 40;
                int chartHeight = getHeight() - legendHeight;
                for (int i = scroll; i < scroll + rowsPerPage; i++) {
                    if(importantMarking.contains(new BigInteger((i + 1) + ""))){
                        g.setColor(Color.yellow);
                    }
                    else{
                        g.setColor(Color.white);
                    }
                    g.drawString((i + 1) + "번째 항", 10, 40 + (i - scroll) * chartHeight / rowsPerPage + legendHeight);
                    g.drawString(fibonacciArray.get(i).toString(), 500, 40 + (i - scroll) * chartHeight / rowsPerPage + legendHeight);
                    g.drawLine(0,  (i - scroll) * chartHeight / rowsPerPage+ legendHeight, getWidth(), (i - scroll) * chartHeight / rowsPerPage+ legendHeight);
                    g.drawLine(0,  (i - scroll + 1) * chartHeight / rowsPerPage - 1+ legendHeight, getWidth(), (i - scroll + 1) * chartHeight / rowsPerPage - 1+ legendHeight);
                }
                g.setColor(Color.white);
                g.drawLine(0, legendHeight, getWidth(), legendHeight);
                g.drawString("n번째 항", 10, 30);
                g.drawString("값", 500, 30);
                repaint();
            }
        };
        legend.setBackground(new Color(64, 64, 64));
        legend.setOpaque(false);
        xWindow.add(legend);

        MouseWheelListener mouseWheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                scroll += e.getWheelRotation();
                if (scroll < 0) {
                    scroll = 0;
                }
                if (scroll > fibonacciArray.size() - rowsPerPage) {
                    scroll = fibonacciArray.size() - rowsPerPage;
                }
                xWindow.repaint();
            }
        };
        xWindow.addMouseWheelListener(mouseWheelListener);

        xWindow.setVisible(true);
    }

    BigInteger getFibonacci(BigInteger n) {
        if (n.intValue() < 1) {
            System.out.println("Invalid input");
            return BigInteger.ZERO;
        }
        if (n.intValue() <= 2) {
            return BigInteger.ONE;
        }
        BigInteger[] fibonacci = new BigInteger[n.intValue()];
        fibonacci[0] = new BigInteger("1");
        fibonacci[1] = new BigInteger("1");
        for (int i = 2; i < n.intValue(); i++) {
            fibonacci[i] = new BigInteger(fibonacci[i - 1].add(fibonacci[i - 2]).toString());
        }
        return fibonacci[n.intValue() - 1];
    }
}
