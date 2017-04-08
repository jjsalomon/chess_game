package com.frames.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by azkei on 01/04/2017.
 * Changes by Francis on 08/04/2017
 */
public class Account extends JFrame {

    public JPanel accprof;
    public JList online;
    private JLabel username;
    private JLabel loss;
    private JLabel coins;
    private JButton skinsButton;
    private JLabel rank;
    private JLabel win;
    private JLabel dbcoin;
    private JLabel dbwin;
    private JLabel dbrank;
    private JLabel dbloss;
    private JButton Refresh;
    private JLabel OnlinePlayers;

    private ArrayList onlineStreams = new ArrayList();

    public Account(String stream){
        super("Chess Master - ");
        //splits stream into data[] username starts at data[2]
        String[] data = stream.split(":");

        //dummy variables for arraylist online players
        onlineStreams.add("jelo");
        onlineStreams.add("john");
        onlineStreams.add("francis");
        onlineStreams.add("Pamela");
        onlineStreams.add("Ben");
        onlineStreams.add("Mark");

        accprof = new JPanel();
        accprof.setLayout(new GridBagLayout());

        //Reference:http://www.newthinktank.com/2012/03/java-video-tutorial-29/
        //Explains what each variable does in the gridbaglayout
        // You create a GridBagContraints object that defines
        // DEFAULTS for your components
        GridBagConstraints gridConstraints = new GridBagConstraints();
        // Define the x position of the component
        gridConstraints.gridx = 1;
        // Define the y position of the component
        gridConstraints.gridy = 1;
        // Number of columns the component takes up
        gridConstraints.gridwidth = 1;
        // Number of rows the component takes up
        gridConstraints.gridheight = 1;
        // Gives the layout manager a hint on how to adjust
        // component width (0 equals fixed)
        gridConstraints.weightx = 0;
        // Gives the layout manager a hint on how to adjust
        // component height (0 equals fixed)
        gridConstraints.weighty = 0;
        // Defines padding top, left, bottom, right
        gridConstraints.insets = new Insets(3, 3, 3, 3);
        // Defines where to place components if they don't
        // fill the space: CENTER, NORTH, SOUTH, EAST, WEST
        // NORTHEAST, etc.
        gridConstraints.anchor = GridBagConstraints.NORTH;
        // How should the component be stretched to fill the
        // space: NONE, HORIZONTAL, VERTICAL, BOTH
        gridConstraints.fill = GridBagConstraints.BOTH;
        //End Reference:

        //Adding and setting up components to panel accprof gridbaglayout
        username = new JLabel(data[2]);
        gridConstraints.gridwidth = 2;
        accprof.add(username,gridConstraints);

        OnlinePlayers = new JLabel("Online Players");
        gridConstraints.gridx = 3;
        gridConstraints.gridwidth = 1;
        accprof.add(OnlinePlayers,gridConstraints);

        Refresh = new JButton("Refresh");
        gridConstraints.gridx = 4;
        accprof.add(Refresh,gridConstraints);

        rank = new JLabel("Rank#");
        gridConstraints.gridy = 2;
        gridConstraints.gridx = 1;
        accprof.add(rank,gridConstraints);

        dbrank = new JLabel(data[3]); //user rank data from database
        gridConstraints.gridx = 2;
        accprof.add(dbrank,gridConstraints);

        win = new JLabel("Win");
        gridConstraints.gridy = 3;
        gridConstraints.gridx = 1;
        accprof.add(win,gridConstraints);

        dbwin = new JLabel(data[4]); //user win data from database
        gridConstraints.gridx = 2;
        accprof.add(dbwin,gridConstraints);

        loss = new JLabel("Loss");
        gridConstraints.gridy = 4;
        gridConstraints.gridx = 1;
        accprof.add(loss,gridConstraints);

        dbloss = new JLabel(data[5]); //user loss data from database
        gridConstraints.gridx = 2;
        accprof.add(dbloss,gridConstraints);

        coins = new JLabel("Coins");
        gridConstraints.gridy = 5;
        gridConstraints.gridx = 1;
        accprof.add(coins,gridConstraints);

        dbcoin = new JLabel(data[6]); //user coin data from database
        gridConstraints.gridx = 2;
        accprof.add(dbcoin,gridConstraints);

/*        skin = new JLabel("Skins"); //server can only send to data[6]? give null pointer.. + this feature don't know how it works
        gridConstraints.gridy = 6;
        gridConstraints.gridx = 1;
        accprof.add(skins,gridConstraints);*/

        //change this to onlineStreams
        online = new JList(onlineStreams.toArray());
        //how many options can they see
        online.setVisibleRowCount(5);
        //can only select one thing on the list at the time
        online.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gridConstraints.gridy = 2;
        gridConstraints.gridx = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.gridheight = 4;
        accprof.add(new JScrollPane(online),gridConstraints);

        //add panel accprof to Jframe
        add(accprof);

        Account.Handlers handler = new Account.Handlers();
        Refresh.addActionListener(handler);
    }

    private class Handlers implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            //if user clicks on refresh button
            if(e.getSource() == Refresh){
                refreshList();
            }

            //if user clicks on a list item
            if(e.getSource() == online){
                System.out.println(onlineStreams.get(online.getSelectedIndex()));
                refreshList();
            }
        }
    }

    //
    public void refreshList() {
        online = new JList(onlineStreams.toArray());
        //how many options can they see
        online.setVisibleRowCount(5);
        //can only select one thing on the list at the time
        online.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accprof.add(new JScrollPane(online));
        add(accprof);
    }
}
