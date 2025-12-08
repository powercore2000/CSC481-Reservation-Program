package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResturantLists
{

    @FXML private Button signInButton;
    @FXML private Button signOutButton;
    @FXML private Button viewReservationButton;
    @FXML private Button backButton;

    @FXML private Button restaurantButton1;
    @FXML private Button restaurantButton2;
    @FXML private Button restaurantButton3;
    @FXML private Button restaurantButton4;
    @FXML private Button restaurantButton5;

    private final List<RestaurantDTO> restaurants = new ArrayList<>();

    @FXML
    public void initialize()
    {

        // --- restore sign-in UI state ---
        if (AppState.isSignedIn()) {
            signInButton.setText("👤");
            signOutButton.setVisible(true);
            viewReservationButton.setVisible(true);
        } else {
            signInButton.setText("Sign In");
            signInButton.setStyle("-fx-background-color: white; -fx-text-fill: #c0392b; -fx-font-weight: bold;");
            signOutButton.setVisible(false);
            viewReservationButton.setVisible(false);
        }

        // --- default restaurant list (can be changed later or filtered) ---
        restaurants.add(new RestaurantDTO("Andies",
                "123 Oak St, Carson, CA", "10am - 12am"));
        restaurants.add(new RestaurantDTO("Jay's Sushi Palace",
                "99 Pine St, Gardena, CA", "11am - 1am"));
        restaurants.add(new RestaurantDTO("Mama Rosa's Italian Kitchen",
                "88 Italian Way, Carson, CA", "9am - 11pm"));
        restaurants.add(new RestaurantDTO("Golden Dragon BBQ",
                "320 China Ave, Compton, CA", "10am - 12am"));
        restaurants.add(new RestaurantDTO("The Garden Vegan Bistro",
                "401 Greenleaf Rd, Carson, CA", "8am - 10pm"));

        // apply them to the buttons
        applyRestaurants(restaurants);
    }

    /**
     * 👉 Function that controls which restaurants are visible on home-view,
     * using a List<RestaurantDTO>.
     */
    public void applyRestaurants(List<RestaurantDTO> list) {
        Button[] buttons = {
                restaurantButton1,
                restaurantButton2,
                restaurantButton3,
                restaurantButton4,
                restaurantButton5
        };

        for (int i = 0; i < buttons.length; i++) {
            if (i < list.size()) {
                RestaurantDTO r = list.get(i);
                buttons[i].setText(r.getName());
                buttons[i].setVisible(true);
                buttons[i].setManaged(true);
            } else {
                // hide unused buttons if list smaller than 5
                buttons[i].setVisible(false);
                buttons[i].setManaged(false);
            }
        }
    }

    private List<RestaurantDTO> restaurantList = List.of(
            new RestaurantDTO("Andies", "123 Oak St, Carson, CA", "10AM - 12AM"),
            new RestaurantDTO("Jay's Sushi Palace", "901 Sushi Rd, Carson CA", "10AM - 11PM"),
            new RestaurantDTO("Mama Rosa's Italian Kitchen", "22 Roma Blvd, Carson CA", "11AM - 10PM"),
            new RestaurantDTO("Golden Dragon BBQ", "17 Fire Grill, Carson CA", "9AM - 12AM"),
            new RestaurantDTO("The Garden Vegan Bistro", "5 Green Leaf, Carson CA", "9AM - 9PM")
    );


    // ========== NAVIGATION HELPERS ==========

    private Stage getStage() {
        return (Stage) signInButton.getScene().getWindow();
    }

    @FXML
    private void onBackClick() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        SceneNavigator.switchScene(stage, "SelectResturantType.fxml", "Smart N Dine");
    }

    // sign in → login screen
    @FXML
    private void onSignInClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.switchScene(stage, "login-view.fxml", "Sign In");
    }

    @FXML
    protected void onSignOutClick() {
        AppState.setSignedIn(false);
        signInButton.setText("Sign In");
        signInButton.setStyle("-fx-background-color: white; -fx-text-fill: #c0392b; -fx-font-weight: bold;");
        viewReservationButton.setVisible(false);
        signOutButton.setVisible(false);
    }

    // ======= restaurant selection =======

    private void goToRestaurant(int index) {
        try {
            RestaurantDTO r = restaurants.get(index);
            AppState.setSelectedRestaurant(index + 1);   // 1-based id if you need it
            AppState.setSelectedRestaurantName(r.getName());

            SceneNavigator.switchScene(
                    getStage(),
                    "restaurant-view.fxml",   // relative to frontend package
                    r.getName()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void onSelectRestaurant1() { goToRestaurant(0); }
    @FXML protected void onSelectRestaurant2() { goToRestaurant(1); }
    @FXML protected void onSelectRestaurant3() { goToRestaurant(2); }
    @FXML protected void onSelectRestaurant4() { goToRestaurant(3); }
    @FXML protected void onSelectRestaurant5() { goToRestaurant(4); }

    // ======= view reservations =======

    @FXML
    protected void onViewReservationClick() {
        try {
            SceneNavigator.switchScene(
                    getStage(),
                    "my-reservations.fxml",
                    "My Reservations"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class RestaurantDTO
    {

        private final String name;
        private final String address;
        private final String hours;

        public RestaurantDTO(String name, String address, String hours) {
            this.name = name;
            this.address = address;
            this.hours = hours;
        }

        public String getName()   { return name; }
        public String getAddress(){ return address; }
        public String getHours()  { return hours; }

    }
}
