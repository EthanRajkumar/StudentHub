import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    private InputStream originalSystemIn;

    @BeforeEach
    void setup() {
        originalSystemIn = System.in;   //stores original System.in
    }

    @AfterEach
    void takeDown() {
        System.setIn(originalSystemIn); //restores original System.in
    }

    @Test
    void createCourse() {

        String simulatedInput = "1\nTest Cases 101\n2\nBSCO\n3\n77777\n4\n12300315\n5\n2\n4\n0\n6\n2\n0\n7\n2025\n8\n3\n9\n20\n0\n";
        InputStream testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testInputStream);

    }

    @Test
    void deleteCourse() {
    }
}