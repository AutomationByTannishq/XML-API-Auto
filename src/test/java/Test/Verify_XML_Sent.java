package Test;

import Configuration.Initialization;
import org.testng.annotations.Test;

public class Verify_XML_Sent extends Initialization {

    @Test
    public void Verify_XML_Sent_Msg() throws Exception {
        com.Update_XML_File();
        com.verify_XML_Sent_response();
    }

}