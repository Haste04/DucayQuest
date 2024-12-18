package QuestPage;

import Backend.Controller.FlashQuestController;
import Backend.Model.Folder;
import StartingPage.StartingPageController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;

import java.util.Optional;


public class quest {
    private Stage stage;
    private FlashQuestController flashQuestController;

    public quest(Stage stage, FlashQuestController flashQuestController) {
        this.stage = stage;  // Store the Stage passed to the constructor
        this.flashQuestController = flashQuestController;
    }
    public void show() {
        questController controller = new questController(stage, flashQuestController);
        Font vcrFont = Font.loadFont(getClass().getResource("/StartingPage/VCR-OSD-MONO.ttf").toExternalForm(), 130);
        Image image = new Image(getClass().getResource("/QuestPage/QuestPage.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.35);
        imageView.setEffect(colorAdjust);
        imageView.setFitWidth(1280);
        imageView.setFitHeight(750);

        //For the sidebar icon
        Image quest = new Image(getClass().getResource("Quest.png").toExternalForm());
        Image note = new Image(getClass().getResource("Note.png").toExternalForm());
        Image menu = new Image(getClass().getResource("Content.png").toExternalForm());
        Image user = new Image(getClass().getResource("User.png").toExternalForm());

        // Creator of Sidebar menu (VBox)
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");

        Text title = new Text("FlashQuest");
        title.getStyleClass().add("Title");
        // Button items in the sidebar
        Button menuButton = sideBarIcons("Menu", menu, 40, 40);
        menuButton.getStyleClass().add("menu-button");
        Button smithCardButton = sideBarIcons("SmithFolder", note, 40, 40);
        smithCardButton.getStyleClass().add("menu-button");
        Button questButton = sideBarIcons("Quest", quest, 40, 40);
        questButton.getStyleClass().add("menu-button");
        Button userButton = sideBarIcons("Zyche", user, 30, 40);
        userButton.getStyleClass().add("menu-button");

        // TODO QUEST BTNS
        menuButton.setOnAction(e -> controller.clickMenuButton());
        smithCardButton.setOnAction(e -> controller.clickSmithFolderButton());
        questButton.setOnAction(e -> controller.clickQuestButton());
        userButton.setOnAction(e -> controller.clickUserButton());

        // Add a spacer to push the userButton to the bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        sidebar.getChildren().addAll(title, menuButton, smithCardButton, questButton, spacer, userButton);

        //Setting alignment for sideBar
        BorderPane root = new BorderPane();
        root.setLeft(sidebar); // Sidebar on the left
        root.getChildren().add(imageView); // Add the background image to the root

        VBox questLayout = new VBox(5);
        questLayout.setAlignment(Pos.TOP_CENTER);

//        Text part1 = new Text("Choose a ");
//        Text part2 = new Text("Quest");
//        TextFlow title1 = new TextFlow(part1, part2);
//        title1.setTranslateY(30); // Sets the text down a little bit
//        title1.setTranslateX(140); // Sets the text to the center
//        part1.setStyle("-fx-fill: white;");
//        part2.setStyle("-fx-fill: #FFCF0E;");
//        title1.getStyleClass().add("Title");
//        questLayout.getChildren().add(title1);

        //Creates buttons based on how many the user has added folders
        for (Folder folder : flashQuestController.getAllFolders()) {
            if (flashQuestController.getAllFolders().isEmpty()) {
                // Show a text messge saying its empty
            }
            HBox questItem = questShow(folder);
            questLayout.getChildren().add(questItem);
            questItem.setFocusTraversable(false); // Prevent focus on questLayout
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(questLayout);
        scrollPane.setFitToWidth(true); // Ensure the VBox stretches to the ScrollPane's width
        scrollPane.setMaxSize(700, 550); // Constrain maximum size
        scrollPane.setFocusTraversable(false); // Prevent focus on questLayout
        scrollPane.setStyle("-fx-background-radius: 5; -fx-border-radius: 5;"); // Optional styling
        scrollPane.getStyleClass().add("scroll-pane");


        StackPane boxlayout = new StackPane(scrollPane);
        boxlayout.getChildren().addAll(questLayout);


        imageView.toBack();
        root.setCenter(boxlayout);


        // Scene
        Scene scene = new Scene(root, 1280, 620);
        String css = this.getClass().getResource("QuestPage.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Set up the stage
        stage.setTitle("FlashQuest");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Helper method to create a sidebar button with an image
    private Button sideBarIcons(String text, Image image, double height, double width) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        Button button = new Button(text);
        button.setGraphic(imageView);
        button.setPrefWidth(260);
        button.setPrefHeight(40);
        return button;
    }
    //Responsible for showing the quest
    private HBox questShow(Folder folder) {
        questController controller = new questController(stage, flashQuestController);

        // Create a text node for the folder name
        Text text = new Text(folder.getFolderName());
        text.setWrappingWidth(200);
        text.getStyleClass().add("question");

        // Create buttons
        Button view = new Button(" View ");
        Button start = new Button(" Start ");
        Button delete = new Button(" X "); // Delete button

        start.setPrefWidth(200);
        start.setPrefHeight(40);
        view.setPrefWidth(200);
        view.setPrefHeight(40);
        delete.setPrefWidth(40);
        delete.setPrefHeight(40);

        start.getStyleClass().add("start-button");
        view.getStyleClass().add("view-button");
        delete.getStyleClass().add("delete-button2"); // Assign a specific style class for delete button

        // Prevent focus on the buttons
        view.setFocusTraversable(false);
        start.setFocusTraversable(false);
        delete.setFocusTraversable(false);

        // Add functionality to buttons
        view.setOnAction(e -> controller.clickViewButton(folder));
        start.setOnAction(e -> controller.clickStartButton(folder));

        // Handle delete button click
        delete.setOnAction(e -> {
            // Create a confirmation dialog
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Folder");
            confirmationAlert.setHeaderText("Are you sure you want to delete this folder?");
            confirmationAlert.setContentText("Folder: " + folder.getFolderName());
            // TODO gotta make this preety

            // Show the alert and capture the user's response
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Perform deletion
                controller.clickDeleteFolderBtn(folder);
                // referesh the UI
            }
        });

        // Layout
        HBox layout = new HBox(25, text, view, start, delete);
        layout.setTranslateY(50);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10;"); // Optional spacing around the box
        layout.getStyleClass().add("quest-layout");

        return layout;
    }

}
