package frontend;

public class AppState
{
    private static boolean signedIn = false;

    public static boolean isSignedIn() {

        return signedIn;
    }
//If you want to actually remember the username, you can add this
//private static String currentUserName;
//public static void setCurrentUserName(String name) { currentUserName = name; }
//public static String getCurrentUserName() { return currentUserName; }

    public static void setSignedIn(boolean value)
    {
        signedIn = value;
    }
    private static String selectedRestaurantName = "";

    public static void setSelectedRestaurantName(String name)
    {
        selectedRestaurantName = name;
    }

    public static String getSelectedRestaurantName()
    {
        return selectedRestaurantName;
    }
    private static int selectedRestaurant = 0;

    public static int getSelectedRestaurant()
    {
        return selectedRestaurant;
    }

    public static void setSelectedRestaurant(int id)
    {
        selectedRestaurant = id;
    }

    private static int selectedMenuItem = 0;

    public static int getSelectedMenuItem()
    {
        return selectedMenuItem;
    }

    public static void setSelectedMenuItem(int id)
    {
        selectedMenuItem = id;
    }

}
