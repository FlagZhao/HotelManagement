package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.panels.*;
import db.DBHelper;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel TOP_panel;
	private JButton checkout_panel;
	private JButton swaproom_panel;
	private JButton checkin_panel;
	private JButton Room_panel;
	private CardLayout card;
	private JButton vipbutton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 651, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Room_panel = new JButton("\u5BA2\u623F\u60C5\u51B5");
		Room_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.show(TOP_panel, "rooms");
			}
		});
		Room_panel.setBounds(10, 10, 86, 23);
		contentPane.add(Room_panel);
		
		checkin_panel = new JButton("\u5165\u4F4F\u767B\u8BB0");
		checkin_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.show(TOP_panel, "checkin");
			}
		});
		checkin_panel.setBounds(106, 10, 86, 23);
		contentPane.add(checkin_panel);
		
		swaproom_panel = new JButton("\u6362\u623F");
		swaproom_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.show(TOP_panel, "swap");
			}
		});
		swaproom_panel.setBounds(202, 10, 86, 23);
		contentPane.add(swaproom_panel);
		
		checkout_panel = new JButton("\u7ED3\u8D26\u9000\u623F");
		checkout_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.show(TOP_panel, "checkout");

			}
		});
		checkout_panel.setBounds(298, 10, 86, 23);
		contentPane.add(checkout_panel);
		
		card=new CardLayout(0,0);
		TOP_panel = new JPanel(card);
		TOP_panel.setBounds(10, 43, 615, 407);
		TOP_panel.setLayout(card);
		TOP_panel.add(new Rooms(), "rooms");
		TOP_panel.add(new checkin(),"checkin");
		TOP_panel.add(new checkout(),"checkout");
		TOP_panel.add(new swap(),"swap");
		TOP_panel.add(new vip(),"vip");
		
		card.show(TOP_panel, "rooms");
		contentPane.add(TOP_panel);
		
		vipbutton = new JButton("\u6CE8\u518C\u4F1A\u5458");
		vipbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.show(TOP_panel, "vip");
			}
		});
		vipbutton.setBounds(394, 10, 93, 23);
		contentPane.add(vipbutton);
	}
	
}
