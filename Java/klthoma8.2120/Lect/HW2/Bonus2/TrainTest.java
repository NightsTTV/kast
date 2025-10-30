import org.junit.Test;
import static org.junit.Assert.*;

public class TrainTest {

    @Test
    public void testValidTrainStructure() {
        Caboose caboose = new Caboose(0);
        BoxCar box2 = new BoxCar(caboose, 0);
        BoxCar box1 = new BoxCar(box2, 0);
        Engine engine = new Engine(box1, 0);
        Train train = new Train(engine);

        assertTrue(train.isValidTrain());
    }

    @Test
    public void testInvalidTrain_NoCaboose() {
        BoxCar box = new BoxCar(null, 0);
        Engine engine = new Engine(box, 0);
        Train train = new Train(engine);

        assertFalse(train.isValidTrain());
    }

    @Test
    public void testAdvanceTrain() {
        Caboose caboose = new Caboose(1);
        BoxCar box = new BoxCar(caboose, 2);
        Engine engine = new Engine(box, 3);
        Train train = new Train(engine);

        train.moveTrain(5);

        assertEquals(6.0, caboose.itsDistanceFromHome, 0.001);
        assertEquals(7.0, box.itsDistanceFromHome, 0.001);
        assertEquals(8.0, engine.itsDistanceFromHome, 0.001);
    }
}

