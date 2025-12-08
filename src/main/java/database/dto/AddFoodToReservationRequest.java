package database.dto;

public class AddFoodToReservationRequest {

    private FoodDTO food;
    private ReservationDTO reservation;

    public AddFoodToReservationRequest() {} // required for Jackson

    public FoodDTO getFood() {
        return food;
    }

    public void setFood(FoodDTO food) {
        this.food = food;
    }

    public ReservationDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDTO reservation) {
        this.reservation = reservation;
    }
}
