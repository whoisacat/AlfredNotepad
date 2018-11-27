import javax.swing.*;
import java.io.IOException;

abstract class MyMenuBarBuilder extends JMenuBar{

    abstract void delText();
    abstract void pastText();
    abstract void copyText();
    abstract void cutText();
    abstract String getFileDir();
    abstract void openFile(String fullFileName);
    abstract void saveFile();
    abstract void createTextArea();
    abstract void createOrRecreateFile(String fullFileName);

    public JMenuBar build(){

        JMenu menuFile = createMenuFile();
        JMenu menuEdit = createMenuEdit();

        JMenuBar JMenuBar = new JMenuBar();
        JMenuBar.add(menuFile);
        JMenuBar.add(menuEdit);

        return JMenuBar;
    }

    private JMenu createMenuEdit(){
        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(e->{
            cutText();
        });

        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(e->{
            copyText();
        });

        JMenuItem pastItem = new JMenuItem("Past");
        pastItem.addActionListener(e->{
            pastText();
        });

        JMenuItem delItem = new JMenuItem("Delete");
        delItem.addActionListener(e->{
            delText();
        });

        JMenu menuEdit = new JMenu("Edit");
        menuEdit.add(cutItem);
        menuEdit.add(cutItem);
        menuEdit.add(pastItem);
        menuEdit.add(delItem);
        return menuEdit;
    }

    private JMenu createMenuFile(){
        JMenuItem createNewFileItem = new JMenuItem("Create new file");
        createNewFileItem.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showDialog(null,"Ok");
            if(ret == JFileChooser.APPROVE_OPTION){
                String fullFileName = fileChooser.getSelectedFile().getAbsolutePath();
                createOrRecreateFile(fullFileName);
                createTextArea();
            }
        });

        JMenuItem openExistingFileItem = new JMenuItem("Open existing file");
        openExistingFileItem.addActionListener(e->{

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

        JMenuItem saveFileItem = new JMenuItem("Save file");
        saveFileItem.addActionListener(e->saveFile());

        JMenuItem saveFileAsItem = new JMenuItem("Save file as...");
        saveFileAsItem.addActionListener(e->{
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

        JMenu JMenuFile = new JMenu("File");
        JMenuFile.add(createNewFileItem);
        JMenuFile.add(openExistingFileItem);
        JMenuFile.add(saveFileItem);
        JMenuFile.add(saveFileAsItem);
        return JMenuFile;
    }
}
