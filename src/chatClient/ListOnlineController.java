package chatClient;

public class ListOnlineController {
    ListOnlineModel model;
    ListOnlineView view;
    private String userHandle;

    public ListOnlineController(String userHandle) {
        this.userHandle = userHandle;
        view = new ListOnlineView();
        model = new ListOnlineModel(view, this.userHandle);
    }
}
