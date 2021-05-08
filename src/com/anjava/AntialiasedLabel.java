package com.anjava;

import java.awt.*;

import javax.swing.*;

class AntialiasedLabel extends JLabel {
	
	

	String text;

    public AntialiasedLabel(String text) {
		// TODO Auto-generated constructor stub
        super(text);

        this.text = text;
	}

	public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        super.paintComponent(g2d);
    }

}