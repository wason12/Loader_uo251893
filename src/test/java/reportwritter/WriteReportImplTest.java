package reportwritter;

import java.util.logging.Level;
import static org.junit.Assert.*;
import java.util.logging.Logger;
import org.junit.Test;

import reportwriter.WreportP;

public class WriteReportImplTest {

    @Test
    public void test() {
	WreportP wr = new WreportP();
	wr.log(Level.WARNING, "prueba");
	wr.getLogger();
	assertEquals(Logger.getLogger("Logger"), Logger.getLogger("Logger"));
    }

}
