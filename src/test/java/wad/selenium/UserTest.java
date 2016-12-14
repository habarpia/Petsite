package wad.selenium;

import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest extends FluentTest{
    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }
    
    @LocalServerPort
    private Integer port;
    
    @Test
    public void signupOnnistuu() {
//        goTo("http://localhost:"+ port + "/signup");
//        
//        assertEquals("Petsite - signup", title());
//                
//        fill(find("#username")).with("NewUser");
//        fill(find("#email")).with("example@gmail.com");
//        fill(find("#password")).with("password");
//        submit(find("form").first());
//        
//        assertEquals("Petsite", title());
//        
//        assertTrue(pageSource().contains("NewUser"));
    }    
}
