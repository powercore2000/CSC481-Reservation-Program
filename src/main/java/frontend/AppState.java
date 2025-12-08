package frontend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private static int selectedMenuItem = -1;

    public static int getSelectedMenuItem() {
        return selectedMenuItem;
    }

    public static void setSelectedMenuItem(int id) {
        selectedMenuItem = id;
    }

    // ===== CART STATE =====
    private static final List<CartItem> cartItems = new ArrayList<>();

    public static void addToCart(CartItem item) {
        if (item != null) {
            cartItems.add(item);
        }
    }

    public static List<CartItem> getCartItems() {
        return Collections.unmodifiableList(cartItems);
    }

    public static void clearCart() {
        cartItems.clear();
    }

    public static boolean hasCartItems() {
        return !cartItems.isEmpty();
    }
}
