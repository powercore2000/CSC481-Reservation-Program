package frontend;

import java.util.List;

import database.dto.FoodDTO;
import frontend.clients.ReservationApiClient;

public class AppState {

    // ===== SIGN IN STATE =====
    private static boolean signedIn = false;

    public static boolean isSignedIn() {
        return signedIn;
    }

    public static void setSignedIn(boolean value) {
        signedIn = value;
    }

    // ===== RESTAURANT SELECTION =====
    private static int selectedRestaurant = 0;
    private static String selectedRestaurantName = "";

    public static int getSelectedRestaurant() {
        return selectedRestaurant;
    }

    public static void setSelectedRestaurant(int id) {
        selectedRestaurant = id;
    }

    public static String getSelectedRestaurantName() {
        return selectedRestaurantName;
    }

    public static void setSelectedRestaurantName(String name) {
        selectedRestaurantName = name;
    }

    // ===== MENU ITEM SELECTION =====
    // -1 means "none selected yet"
    private static long selectedMenuItemID = -1;
    private static FoodDTO selectedFoodItem;
    
    public static FoodDTO getSelectedFoodItem() {
        return selectedFoodItem;
    }
    public static void setSelectedFoodItem(FoodDTO f) {
        selectedFoodItem = f;
        setSelectedMenuItemID(f.getId());
    }
    
    public static long getSelectedMenuItemID() {
        return selectedMenuItemID;
    }

    public static void setSelectedMenuItemID(long id) {
    	selectedMenuItemID = id;
    }
    
    private static Boolean buyMode = false;
    public static void setBuyMode(Boolean b) {buyMode = b;}
    public static Boolean getBuyMode() {return buyMode;}

    // ===== CART STATE =====
    //private static final List<FoodDTO> cartItems = new ArrayList<>();

    public static void addToCart(FoodDTO item) {
        if (item == null) {
            return;
        }

        if (!ReservationApiClient.addFoodToCurrentReservation(item)) {
            System.err.println("Failed to add item to cart: " + item.getName());
        }
    }

    public static List<FoodDTO> getCartItems() {
        return ReservationApiClient.getAllFoodFromCurrentReservation();
    }

    public static void clearCart() {
        //cartItems.clear();
    }

    public static boolean hasCartItems() {

        if(ReservationApiClient.getCachedReservation() != null) {
            return !ReservationApiClient.getAllFoodFromCurrentReservation().isEmpty();
        }
            else {
                return false;
            }
    }
}