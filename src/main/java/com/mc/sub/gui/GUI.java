package com.mc.sub.gui;

import com.mc.sub.Utils;
import com.mc.sub.converter.AddSpaceConverter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class GUI extends JFrame implements LogPrinter {
    private static final String BAR = "----------------------------------";
    private static final String NULL = "null";

    private JButton addSpaceBtn;
    private JTextArea logTextArea;
    private JScrollPane logScrollPane;

    private AddSpaceConverter addSpaceConverter;

    public GUI() {
        super("SUB-TOOL Coin Card LLC");
    }

    public void start() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());

        logTextArea = new JTextArea(20, 20);
        logScrollPane = new JScrollPane(logTextArea);
        logTextArea.setEditable(true);

        addSpaceBtn = new JButton("Add Space");
        addSpaceBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(Utils.getDesktop()));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("SRT FILES", "srt");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedFilepath = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    addSpaceConverter.submitAddSpace(selectedFilepath);
                } catch (Exception ioException) {
                    ioException.printStackTrace();
                    println("Error when process: " + ioException.getMessage());
                } finally {
                    newBlock();
                }
            }
        });

        this.getContentPane().add(addSpaceBtn, BorderLayout.NORTH);
        this.getContentPane().add(logScrollPane, BorderLayout.CENTER);

        addSpaceConverter = new AddSpaceConverter(this);

        this.setVisible(true);
    }

    @Override
    public void newBlock() {
        this.println(BAR);
    }

    @Override
    public void print(Object s) {
        String line = NULL;
        if (s != null) {
            line = s.toString();
        }
        logTextArea.append(line);
        System.out.println(line);
    }

    @Override
    public void println(Object s) {
        this.print(s);
        this.print("\n");
    }
}
