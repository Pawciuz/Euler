package edu.euler;

import observer.CConsoleObserver;
import observer.CFileObserver;
import observer.CJListObserver;
import solver.CSolver;
import solver.CSolverCreator;
import solver.CStepData;
import solver.ESolverType;

import javax.swing.*;

public class CMainForm extends JFrame {
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuOptions;
    private JMenuItem itemExit;
    private JMenuItem itemSolve;
    private JMenuItem itemAbout;
    private JRadioButton rbFirst;
    private JRadioButton rbSecond;
    private JRadioButton rbFourth;
    private JTextField tkTextField;
    private JTextField alphaTextField;
    private JTextField omegaTextField;
    private JCheckBox cbPanel;
    private JCheckBox cbFile;
    private JCheckBox cbConsole;
    private JList list1;
    private DefaultListModel<Object> model;

    public CMainForm(String title) {
        super(title);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        model = new DefaultListModel<>();
        list1.setModel(model);
        itemExit.addActionListener(e -> CMainForm.this.dispose());
        itemAbout.addActionListener(e -> JOptionPane.showMessageDialog(CMainForm.this, "Euler's method app", "O programie", JOptionPane.INFORMATION_MESSAGE));
        itemSolve.addActionListener(e -> solveActionPerformed());
    }

    public void solveActionPerformed() {
        CSolverCreator sc = new CSolverCreator();

        ESolverType st = ESolverType.FIRST_ORDER;
        if (rbSecond.isSelected()) st = ESolverType.SECOND_ORDER;
        else if (rbFourth.isSelected()) st = ESolverType.FOURTH_ORDER;

        CStepData init = new CStepData(
                Double.parseDouble(tkTextField.getText().trim()),
                Double.parseDouble(alphaTextField.getText().trim()),
                Double.parseDouble(omegaTextField.getText().trim())
        );
        CSolver solverObj = sc.getSolver(st, init);

        if (cbPanel.isSelected()) solverObj.addObserver(new CJListObserver(model));
        if (cbConsole.isSelected()) solverObj.addObserver(new CConsoleObserver());
        if (cbFile.isSelected()) solverObj.addObserver(new CFileObserver());

        solverObj.solve();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
