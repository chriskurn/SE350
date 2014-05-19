package building.common;

public class FloorFactory {

    public static Floor build(int floorNumber) {
        return new FloorImpl(floorNumber);
    }

}
