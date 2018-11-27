import javax.swing.*;
import java.awt.*;
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
            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showDialog(null,"Ok");
            if(ret == JFileChooser.APPROVE_OPTION){
                String fullFileName = fileChooser.getSelectedFile().getAbsolutePath();
                createOrRecreateFile(fullFileName);
                createTextArea();
            }
        });

        mOpenExistingFileItem = new JMenuItem("Open existing file");
        mOpenExistingFileItem.addActionListener(e->{

            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showOpenDialog(null);
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
            try{
                JFileChooser fileChooser = new JFileChooser(getFileDir());
                if(fileChooser.showDialog(null,"Save as") == JFileChooser.APPROVE_OPTION){
                    String fullFileName = fileChooser.getSelectedFile().getCanonicalPath();
                    createOrRecreateFile(fullFileName);
                    saveFile();
                }
            } catch(IOException ioe){
                ioe.printStackTrace();
            }
        });

        mJMenuFile = new JMenu("File");
        System.out.println(mJMenuFile.getDisplayedMnemonicIndex());
        mJMenuFile.add(mCreateNewFileItem);
        mJMenuFile.add(mOpenExistingFileItem);
        mJMenuFile.add(mSaveFileItem);
        mJMenuFile.add(mSaveFileAsItem);

        mCutItem = new JMenuItem("Cut");
        mCutItem.addActionListener(e->{
            cutText();
        });

        mCopyItem = new JMenuItem("Copy");
        mCopyItem.addActionListener(e->{
            copyText();
        });

        mPastItem = new JMenuItem("Past");
        mPastItem.addActionListener(e->{
            pastText();
        });

        mDelItem = new JMenuItem("Delete");
        mDelItem.addActionListener(e->{
            delText();
        });

        mMenuEdit = new JMenu("Edit");
        mMenuEdit.add(mCutItem);
        mMenuEdit.add(mCutItem);
        mMenuEdit.add(mPastItem);
        mMenuEdit.add(mDelItem);

        mJMenuBar = new JMenuBar();
        mJMenuBar.add(mJMenuFile);
        mJMenuBar.add(mMenuEdit);
        return mJMenuBar;
    }

    protected abstract void delText();

    protected abstract void pastText();

    protected abstract void copyText();

    protected abstract void cutText();

    protected abstract String getFileDir();

    abstract void openFile(String fullFileName);
    abstract void saveFile();
    abstract void createTextArea();
    abstract void createOrRecreateFile(String fullFileName);
}
