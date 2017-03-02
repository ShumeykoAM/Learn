package jagru;

import org.junit.*;

public class TestService
{
    @Test
    public void testMessage()
    {
        String str = new Service().getMessage();
        System.out.println("MESSAGE " + str);
        Assert.assertEquals("2 b || ! 2 b", str);
    }
}