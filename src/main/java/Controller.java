import java.io.IOException;

class Controller{

    private GUI mGUI;
    FileManager mFileManager;

    public void run(){
        mGUI = new GUI(){
            @Override protected String getFileContent() throws IOException{
                return mFileManager.getTextFromExistingFile();
            }
            protected String getTextFromExisting(String fullFileName){
                mFileManager = new FileManager(fullFileName,true);
                String text = null;
                try{
                    text = mFileManager.getTextFromExistingFile();
                } catch(IOException e){
                    e.printStackTrace();
                }
                return text;
            }
            protected void stopApp(boolean save, String text){
                stop(save, text);
            }
            protected void wrightInFile(String s){
                mFileManager.wrightInFile(s);
            }
            void getNewFile(String fullFileName){
                mFileManager = new FileManager(fullFileName,false);
            }
        };
    }

    public void stop(boolean doSave, String text){
        if(doSave){
            if(text != null) mFileManager.wrightInFile(text);
        }
        mGUI.closeDialog();
        System.exit(0);
    }
}

