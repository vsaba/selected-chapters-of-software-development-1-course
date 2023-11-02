package hr.fer.zemris.java.gui.layouts;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class RCPositionTest {

    @Test
    void test1() {
        assertThrows(CalcLayoutException.class, ()->{
            JPanel p = new JPanel(new CalcLayout(3));
            p.add(new JButton("1,1"), new RCPosition(0,2));
        });
    }


    @Test
    void test2() {
        assertThrows(CalcLayoutException.class, ()->{
            JPanel p = new JPanel(new CalcLayout(3));
            p.add(new JButton("1,1"), new RCPosition(1,2));
        });
    }

    @Test
    void test3() {
        assertThrows(CalcLayoutException.class, ()->{
            JPanel p = new JPanel(new CalcLayout(3));
            p.add(new JButton("1,1"), new RCPosition(1,1));
            p.add(new JButton("1,1"), new RCPosition(1,1));
        });
    }

    
    @Test
    void test4() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(10,30));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(20,15));
        p.add(l1, new RCPosition(2,2));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();
        if(dim.width != 152 || dim.height != 158) fail();
    }


    @Test
    void test5() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();
        if(dim.width != 152 || dim.height != 158) fail();
    }
}