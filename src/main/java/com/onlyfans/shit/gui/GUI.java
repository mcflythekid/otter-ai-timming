package com.onlyfans.shit.gui;

import com.onlyfans.shit.converter.TheVoid;
import com.onlyfans.shit.converter.TheSmart;
import com.onlyfans.shit.util.Utils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class GUI extends JFrame implements LogPrinter {
    private static final String BAR = "----------------------------------";
    private static final String NULL = "null";

    private JButton addSpaceBtn;
    private JTextField addSmartField;
    private JButton addSmartBtn;
    private JTextArea logTextArea;
    private JScrollPane logScrollPane;

    private TheVoid theVoid;

    public static void main(String[] args) {
        String s = "\n\nabv   xyxzzz\n\n";
        s = s.trim();
        System.out.println("[" + s + "]");
    }

    public GUI() {
        super("ZedSub v70.1");
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
                    theVoid.submitAddSpace(selectedFilepath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    println("Error when process: " + ex.getMessage());
                    println(ExceptionUtils.getStackTrace(ex));
                } finally {
                    newBlock();
                }
            }
        });

        addSmartField = new JTextField("80");
        addSmartField.setColumns(5);
        addSmartBtn = new JButton("Smart Join");
        addSmartBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(Utils.getDesktop()));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("SRT FILES", "srt");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String inPath = fileChooser.getSelectedFile().getAbsolutePath();
                    String outPath = Utils.generateOutPath(inPath, "joined");
                    int maxChars = Integer.parseInt(addSmartField.getText());
                    TheSmart.submit(inPath, outPath, maxChars);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    println("Error when process: " + ex.getMessage());
                    println(ExceptionUtils.getStackTrace(ex));
                } finally {
                    newBlock();
                }
            }
        });


        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1));
        //-
        JPanel spacePanel = new JPanel();
        spacePanel.add(addSpaceBtn);
        //-
        JPanel smartPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        smartPanel.add(new JLabel("Chars-per-line"));
        smartPanel.add(addSmartField);
        smartPanel.add(addSmartBtn);
        //-
        controlPanel.add(spacePanel);
        controlPanel.add(smartPanel);


        this.getContentPane().add(controlPanel, BorderLayout.NORTH);
        this.getContentPane().add(logScrollPane, BorderLayout.CENTER);

        theVoid = new TheVoid(this);

        TheSmart.setLogPrinter(this);
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
