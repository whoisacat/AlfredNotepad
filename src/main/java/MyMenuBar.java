import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

abstract class MyMenuBarBuilder extends JMenuBar{

    private JMenuBar mJMenuBar;

    private JMenu mJMenuFile;

    private JMenuItem mCreateNewFileItem;
    private JMenuItem mOpenExistingFileItem;
    private JMenuItem mSaveFileItem;
    private JMenuItem mSaveFileAsItem;

    private JMenu mMenuEdit;

    private JMenuItem mCutItem;
    private JMenuItem mCopyItem;
    private JMenuItem mPastItem;
    private JMenuItem mDelItem;

    public JMenuBar build(){

        mCreateNewFileItem = new JMenuItem("Create new file");
        mCreateNewFileItem.addActionListener(e->{
            String fullFileName = getFullFileNameFromFileDialog();
            createOrRecreateFile(fullFileName);
            createTextArea();
        });

        mOpenExistingFileItem = new JMenuItem("Open existing file");
        mOpenExistingFileItem.addActionListener(e->{

            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showDialog(null,"Open file");
            if (ret == JFileChooser.APPROVE_OPTION){
                try{
                    String fullFileName = fileChooser.getSelectedFile().getCanonicalPath();
                    openFile(fullFileName);
                } catch(IOException e1){
                    e1.printStackTrace();
                }
            }
        });

        mSaveFileItem = new JMenuItem("Save file");
        mSaveFileItem.addActionListener(e->saveFile());

        mSaveFileAsItem = new JMenuItem("Save file as...");
        mSaveFileAsItem.addActionListener(e->{
            String fullFileName = getFullFileNameFromFileDialog();
            createOrRecreateFile(fullFileName);
            saveFile();
        });

        mJMenuFile = new JMenu("File");
        System.out.println(mJMenuFile.getDisplayedMnemonicIndex());
        mJMenuFile.add(mCreateNewFileItem);
        mJMenuFile.add(mOpenExistingFileItem);
        mJMenuFile.add(mSaveFileItem);
        mJMenuFile.add(mSaveFileAsItem);

        mCutItem = new JMenuItem("Cut");
        mCutItem.addActionListener(e->{

        });

        mCopyItem = new JMenuItem("Copy");
        mCopyItem.addActionListener(e->{

        });

        mPastItem = new JMenuItem("Past");
        mPastItem.addActionListener(e->{

        });

        mDelItem = new JMenuItem("Delete");
        mDelItem.addActionListener(e->{

        });

        mMenuEdit = new JMenu("Edit");
        mMenuEdit.add(mCutItem);
        mMenuEdit.add(mCutItem);
        mMenuEdit.add(mPastItem);
        mMenuEdit.add(mDelItem);

        mJMenuBar = new JMenuBar();
        mJMenuBar.add(mJMenuFile);
        mJMenuBar.add(mMenuEdit);
        System.out.println("mJMenuBar.getComponentIndex(mJMenuFile)");
        System.out.println(mJMenuBar.getComponentIndex(mJMenuFile));
        System.out.println("mJMenuBar.getComponentIndex(mSaveFileItem");
        System.out.println(mJMenuBar.getComponentIndex(mSaveFileItem));

        return mJMenuBar;
    }

    abstract void openFile(String fullFileName);
    abstract void saveFile();
    abstract void createTextArea();
    abstract void createOrRecreateFile(String fullFileName);

    private String getFullFileNameFromFileDialog(){
        FileDialog fd = new FileDialog(new JFrame(), " Save", FileDialog.SAVE);
        fd.setVisible(true);
        return fd.getDirectory().concat(fd.getFile());
    }
}
