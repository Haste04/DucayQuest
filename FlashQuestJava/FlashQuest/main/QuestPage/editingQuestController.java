package QuestPage;

import AccountPage.AccountPage;
import Backend.Controller.FlashQuestController;
import Backend.Model.Flashcard;
import Backend.Model.Folder;
import CreateFolderPage.createFolderPage;
import MenuPage.menu;
import javafx.stage.Stage;

public class editingQuestController {
    private final Stage stage;
    private final FlashQuestController flashQuestController;

    public editingQuestController(Stage stage, FlashQuestController flashQuestController) {
        this.stage = stage;
        this.flashQuestController = flashQuestController;
    }
    public void clickMenuButton() {
        menu Menu = new menu(stage, flashQuestController);
        Menu.show();
    }
    public void clickSmithFolderButton() {
        createFolderPage Smithfolder = new createFolderPage(stage, flashQuestController);
        Smithfolder.show();
    }
    public void clickQuestButton() {
        quest Quest = new quest(stage, flashQuestController);
        Quest.show();
    }
    public void clickUserButton() {
        // TODO HAGGAI
        AccountPage accountPage = new AccountPage(stage, flashQuestController);
        accountPage.show();
    }

    public void clickSaveBtn(String newQuestion, String newAnswer, Folder folder, Flashcard flashcard) {
        flashQuestController.editFlashcardByFolderIdAndFlashcardId(newQuestion, newAnswer, folder, flashcard);
        editQuest EditQuestPage = new editQuest(stage, flashQuestController, folder);
        EditQuestPage.show();
    }
}
