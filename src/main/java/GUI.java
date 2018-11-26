import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public abstract class GUI{
    private JFrame mJFrame;
    private JMenuBar mMenuBar;
    private JTextArea mJTextArea;
    private JScrollPane mJScrollPane;

    public GUI(){

        mMenuBar = new MyMenuBarBuilder(){
            protected void openFile(String fullFileName){
                String text = getTextFromExisting(fullFileName);
                makeWrightableTextArea();
                mJTextArea.setText(text);
            }

            protected void saveFile(){
                String s = mJTextArea.getText();
                wrightInFile(s);
            }

            void createTextArea(){
                makeWrightableTextArea();
            }

            void createOrRecreateFile(String fullFileName){
                getNewFile(fullFileName);
            }
        }.build();

        mJFrame = new JFrame();
        mJFrame.setJMenuBar(mMenuBar);

        mJFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mJFrame.setSize(300, 300);
        mJFrame.setVisible(true);
        mJFrame.addWindowListener(new WindowAdapter(){
            @Override public void windowClosing(WindowEvent e){

                try{
                    if(!mJTextArea.getText().equals(getFileContent()))
                        onClosingWindow();
                    else stopApp(false,"");
                }catch(IOException ioe){
                    stopApp(false,null);
                    System.out.println(ioe.toString() + " \n" + ioe.getStackTrace());
                }catch(NullPointerException npe){
                    stopApp(false,null);
                    System.out.println(npe.toString() + " \n" + npe.getStackTrace());
                }
            }
        });
    }

    protected abstract String getFileContent() throws IOException;

    private void onClosingWindow(){
        int result = JOptionPane.showConfirmDialog(null, "Should I save the changes?", "Closing the app", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        switch (result) {
            case JOptionPane.YES_OPTION:
                saveIfThereWasAnyChanges();
                break;
            case JOptionPane.NO_OPTION :
                stopApp(false, null);
                break;
            case JOptionPane.CANCEL_OPTION :
        }
    }

    private void saveIfThereWasAnyChanges(){
        stopApp(true, mJTextArea.getText());
    }

    private void makeWrightableTextArea(){
        mJTextArea = new JTextArea();
        mJScrollPane = new JScrollPane(mJTextArea);
        mJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        mJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mJFrame.add(mJScrollPane);
        mJFrame.setVisible(true);
    }

    abstract String getTextFromExisting(String fullFileName);
    abstract void stopApp(boolean save, String text);
    abstract void wrightInFile(String s);
    abstract void getNewFile(String fullFileName);

    public void closeDialog(){
        mJFrame.dispose();
    }
}
