import XWindow.XWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GoldRatio {
    boolean compute = true;
    int holdTextSize = 20;
    boolean holdTextSizeUp = true;
    Thread holdTextThread = new Thread() {
        public void run() {
            while (true) {
                if (holdTextSizeUp) {
                    holdTextSize++;
                    if (holdTextSize >= 30) {
                        holdTextSizeUp = false;
                    }
                } else {
                    holdTextSize--;
                    if (holdTextSize <= 20) {
                        holdTextSizeUp = true;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    };
    JLabel slower = new JLabel(">");
    JLabel faster = new JLabel(">>>");
    JLabel speedControler = new JLabel();
    int speedControlerPos = 0;
    Point speedControlerMouseClickPoint = new Point(0, 0);

    int n = 1;
    BigDecimal a = new BigDecimal(1);
    BigDecimal b = new BigDecimal(1);
    BigDecimal c = b.divide(a, 100, RoundingMode.HALF_UP);
    Thread FibonacciNumberCalculateThread = new Thread() {
        public void run() {
            while (true) {
                if (speedControlerPos == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        break;
                    }
                    continue;
                }
                try {
                    if (speedControlerPos == 100) {
                    } else {
                        Thread.sleep(201 - speedControlerPos * 2L);
                    }
                } catch (InterruptedException e) {
                    break;
                }
                n++;
                BigDecimal temp = a.add(b);
                a = b;
                b = temp;
                c = b.divide(a, 100, RoundingMode.HALF_UP);
            }
        }
    };

    public static void main(String[] args) {
        XWindow xWindow = new XWindow(1000, 500, "Golden Ratio Proof Software", true);
        GoldRatio Fibonacci = new GoldRatio();
        Fibonacci.holdTextThread.start();
        Fibonacci.FibonacciNumberCalculateThread.start();

        JPanel panel = new JPanel(null) {

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (Fibonacci.speedControlerPos == 0) {
                    g.setColor(new Color(200, 50, 50));
                    g.setFont(g.getFont().deriveFont((float) Fibonacci.holdTextSize));
                    g.drawString("Hold", 10, getHeight() - 30);
                }

                g.setColor(Color.white);
                g.setFont(new Font("", 0, 20));
                g.drawString(Fibonacci.n + "번째 항: " + Fibonacci.a.toString(), 10, 210);
                g.drawString((Fibonacci.n + 1) + "번째 항: " + Fibonacci.b.toString(), 10, 150);
                g.drawString(Fibonacci.b.divide(Fibonacci.a, 100, RoundingMode.HALF_UP).toString(), 10, 300);

                g.setFont(new Font("", 0, 40));
                g.drawString("a", getWidth() - 100, 50);
                g.drawString("a", getWidth() - 100, 100);

                g.setFont(new Font("", 0, 20));
                g.drawString("n+1", getWidth() - 80, 50);
                g.drawString("n", getWidth() - 80, 100);

                g.drawLine(getWidth() - 110, 60, getWidth() - 40, 60);
                g.drawLine(getWidth() - 200, 110, getWidth(), 110);
                g.drawLine(getWidth() - 200, 110, getWidth() - 200, 0);
                g.drawString("알고리듬", getWidth() - 200, 70);

                g.drawLine(
                        10 + Math.max(g.getFontMetrics().stringWidth(Fibonacci.n + "번째 항: "),
                                g.getFontMetrics().stringWidth((Fibonacci.n + 1) + "번째 항: ")),
                        175,
                        Math.max(g.getFontMetrics().stringWidth(Fibonacci.a.toString()),
                                g.getFontMetrics().stringWidth(Fibonacci.b.toString())) + 10
                                + Math.max(g.getFontMetrics().stringWidth(Fibonacci.n + "번째 항: "),
                                g.getFontMetrics().stringWidth((Fibonacci.n + 1) + "번째 항: ")),
                        175);

                Fibonacci.slower.setBounds(getWidth() - 190, getHeight() - 30, 40, 20);
                Fibonacci.faster.setBounds(getWidth() - 50, getHeight() - 30, 40, 20);
                Fibonacci.speedControler.setBounds(getWidth() - 155 + Fibonacci.speedControlerPos, getHeight() - 30, 10,
                        20);

                repaint();
            }
        };
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() == panel) {
                    Fibonacci.n = 1;
                    Fibonacci.a = new BigDecimal(1);
                    Fibonacci.b = new BigDecimal(1);
                    Fibonacci.c = Fibonacci.b.divide(Fibonacci.a, 100, RoundingMode.HALF_UP);
                }
            }
        };
        panel.addMouseListener(mouseAdapter);
        panel.setBackground(new Color(64, 64, 64));
        xWindow.add(panel);

        panel.add(Fibonacci.slower);
        Fibonacci.slower.setForeground(Color.white);
        Fibonacci.slower.setBackground(null);
        Fibonacci.slower.setFont(new Font("", 0, 20));
        Fibonacci.slower.setHorizontalAlignment(JLabel.RIGHT);

        panel.add(Fibonacci.faster);
        Fibonacci.faster.setForeground(Color.white);
        Fibonacci.faster.setBackground(null);
        Fibonacci.faster.setFont(new Font("", 0, 20));
        Fibonacci.faster.setHorizontalAlignment(JLabel.LEFT);

        panel.add(Fibonacci.speedControler);
        Fibonacci.speedControler.setOpaque(true);
        Fibonacci.speedControler.setBackground(Color.white);

        Fibonacci.speedControler.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Fibonacci.speedControlerMouseClickPoint = e.getPoint();
            }
        });

        Fibonacci.speedControler.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - Fibonacci.speedControlerMouseClickPoint.x;
                Fibonacci.speedControlerPos += deltaX;
                Fibonacci.speedControlerMouseClickPoint = e.getPoint();
                if (Fibonacci.speedControlerPos < 0) {
                    Fibonacci.speedControlerPos = 0;
                }
                if (Fibonacci.speedControlerPos > 100) {
                    Fibonacci.speedControlerPos = 100;
                }
            }
        });
        Fibonacci.slower.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Fibonacci.speedControlerPos = 0;
                }
            }
        });
        Fibonacci.faster.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Fibonacci.speedControlerPos = 100;
                }
            }
        });

        xWindow.setDefaultCloseOperation(3);

        xWindow.setVisible(true);
    }
}
