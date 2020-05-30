package com.example.imageConverter;

//for image reading
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Image.*;
import java.io.*;

//<------------------------------------------------------------------------------------------>>
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.collections.*;



//for Image Processing
import javax.imageio.ImageIO;



//<-------------------------------------------------------------------------------------------->>
class ImageConverterProgram{
    public void resize(File inputFile, File outputFile, int scaleWidth, int scaleHeight, String extension){



        try (InputStream is = new FileInputStream(inputFile)) {
            BufferedImage image = ImageIO.read(is);

            String path = outputFile.getPath()+"/picture."+extension;
            File output = new File(path);

            try (OutputStream os = new FileOutputStream(output)) {
                BufferedImage outputImage = new BufferedImage(scaleWidth, scaleHeight, image.getType());

                ImageIO.write(outputImage, extension, os);

            } catch (Exception exp) {
                exp.printStackTrace();
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public void converter(File inputFile, File outputFile, String extension){

        try (InputStream is = new FileInputStream(inputFile)) {
            BufferedImage image = ImageIO.read(is);

            String path = outputFile.getPath()+"/picture."+extension;
            File output = new File(path);

            try (OutputStream os = new FileOutputStream(output)) {

                ImageIO.write(image, extension, os);

            } catch (Exception exp) {
                exp.printStackTrace();
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }


}


class Help{

    //Stage Variable declaration

    static Stage stage;
    static public void helpMsg(){

        //Main Program for Help msg
        stage = new Stage();
        String msgTop = "This is Image Formate Converter Prgram \n\n\n";
        String msgMedium = "Choose Directory from ImageOption menu,\n Choose Image File,\n Choose Image Format, \nPress Convert\n\n\n\n\n\n";
        String msgBottom = "Creator :- Lucky@Bairagi";
        Text msg = new Text(msgTop);
        msg.setFont(new Font("Monospaced", 14));
        msg.setWrappingWidth(200);

        Text msg2 = new Text(msgMedium);
        msg2.setFont(new Font("Monospaced", 12));

        Text msg3 = new Text(msgBottom);
        msg3.setFont(new Font("Inconsolata", 10));


        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(msg, msg2, msg3);

        Scene scene = new Scene(pane, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Help");
        stage.show();

    }
}


//<--------------------------------------------------------------------------------------------------------------------->
public class ImageConverter extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    File selectedFile;
    File outputFile;

    int scaleWidth = 0;
    int scaleHeight = 0;


    @Override
    public void start(Stage stage){


        //Converter

        ImageConverterProgram imageConverterProgram = new ImageConverterProgram();
        //File Chooser and Directory Chooser
        FileChooser fileChooser = new FileChooser();
        DirectoryChooser directoryChooser = new DirectoryChooser();

        //Button
        Button btn = new Button("Open Folder");

        //Menu Bar
        MenuBar menuBar = new MenuBar();

        //MenuBar Items
        Menu ImageOption = new Menu("_ImageOption");
        Menu help = new Menu("_Help");

        //About MenuItem
        MenuItem about = new MenuItem("About");
        about.setOnAction(e -> {
            Help.helpMsg();
        });
        //----------------------------------------
        //Help menuItem
        help.getItems().add(about);
        MenuItem chooseOutPutFolder = new MenuItem("_Choose OutPut Folder");
        chooseOutPutFolder.setOnAction(e -> {
            outputFile = directoryChooser.showDialog(stage);

            System.out.println(outputFile.getPath());

        });
        //-------------------------------------------
        //Preference Menu
        Menu preference = new Menu("_Preference");
        Menu size = new Menu("_Size");
        MenuItem increase = new MenuItem("+");
        increase.setOnAction(e ->{

        });
        MenuItem decrease = new MenuItem("-");


        preference.getItems().add(size);
        size.getItems().addAll(increase, decrease);


        MenuItem quit = new MenuItem("_Quit");
        quit.setOnAction(e -> stage.close());

        ImageOption.getItems().addAll(chooseOutPutFolder, preference);

        ImageOption.getItems().add(new SeparatorMenuItem());
        ImageOption.getItems().add(quit);

        menuBar.getMenus().addAll(ImageOption, help);


        TextField inputText = new TextField();
        inputText.getStyleClass().getClass().getResourceAsStream("textStyle");


        TextField imageWidth = new TextField();
        imageWidth.setPromptText("Width in px");


        TextField imageHeight = new TextField();
        imageHeight.setPromptText("Height in px");

        //File Chooser Filter
        fileChooser.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("JPEG file", "*.jpg"),
          new FileChooser.ExtensionFilter("PNG file", "*.png"),
          new FileChooser.ExtensionFilter("GIF file", "*.gif")
        );

        //Icon for Button
        Image icon = new Image(getClass().getResourceAsStream("folder.png"));
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(20);
        iconView.setFitWidth(20);


        btn.setGraphic(iconView);
        btn.setOnAction(e -> {
             selectedFile = fileChooser.showOpenDialog(stage);
             if(selectedFile != null){
                 inputText.setText(selectedFile.getPath());
             }
        });
        //End-------------------------------------------------

        //Convert Button
        Button convert = new Button("Convert");
        ComboBox<String> fileExtenions = new ComboBox<String>();
        fileExtenions.getItems().addAll("PNG", "JPG", "GIF");
        fileExtenions.setPromptText("Choose File Extension");



        //End -----------------------------------------------------------------------------

        convert.setOnAction(e -> {
            scaleWidth = Integer.parseInt(imageWidth.getText());
            scaleHeight = Integer.parseInt(imageHeight.getText());
            if(scaleHeight != 0 || scaleHeight != 0)
                imageConverterProgram.resize(selectedFile, outputFile, scaleWidth, scaleHeight, fileExtenions.getValue().toLowerCase());
            else
                imageConverterProgram.converter(selectedFile, outputFile, fileExtenions.getValue().toLowerCase());
        });
        //Scene and Panes

        HBox inputPane = new HBox(10);
        inputPane.getChildren().addAll(inputText, btn);
        inputPane.setAlignment(Pos.CENTER);

        VBox fullPane = new VBox(10);
        fullPane.setAlignment(Pos.CENTER);
        fullPane.getChildren().addAll(inputPane, fileExtenions,imageHeight,imageWidth, convert);

        BorderPane pane = new BorderPane();
        pane.setTop(menuBar);
        pane.setCenter(fullPane);


        Scene scene = new Scene(pane, 350, 350);
        scene.getStylesheets().getClass().getResourceAsStream("style.css");

        stage.setTitle("Image Converter");
        stage.setScene(scene);
        stage.show();
    }
}


