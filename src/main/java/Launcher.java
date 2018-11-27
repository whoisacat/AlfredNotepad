import javax.swing.*;

public class Launcher{

    private static Controller sMController;

    public static void main(String[] args){

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch(InstantiationException e){
            e.printStackTrace();
        } catch(IllegalAccessException e){
            e.printStackTrace();
        } catch(UnsupportedLookAndFeelException e){
            e.printStackTrace();
        }

        sMController = new Controller();
        sMController.run();
    }
}
