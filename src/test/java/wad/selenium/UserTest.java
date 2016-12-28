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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.repository.UserRepository;

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
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void signupOnnistuu() {
        goTo("http://localhost:"+ port);
        webDriver.findElement(By.linkText("Sign Up Now")).click();
        
        assertEquals("Petsite - signup", title());
                
        fill(find("#username")).with("NewUser");
        fill(find("#email")).with("example@gmail.com");
        fill(find("#password")).with("password");
        submit(find("form").first());
        
        assertEquals("Petsite - login", title());
        
        fill(find("#username")).with("NewUser");
        fill(find("#password")).with("password");
        submit(find("form").first());
        
        assertEquals("Petsite", title());
        webDriver.findElement(By.linkText("Users")).click();
        assertTrue(pageSource().contains("NewUser"));
    }   
    
    @Test
    public void tyhjaKayttajanimiEiKelpaaSignupissa() {
        goTo("http://localhost:"+ port);
        webDriver.findElement(By.linkText("Sign Up Now")).click();
        
        assertEquals("Petsite - signup", title());
                
        fill(find("#email")).with("e@mail.com");
        fill(find("#password")).with("password");
        submit(find("form").first());
        
        assertEquals("Petsite - signup", title());
        
        assertTrue(pageSource().contains("length must be between 1 and 20"));
        assertTrue(pageSource().contains("may not be empty"));
        
        assertTrue(userRepository.findByEmail("e@mail.com") == null);
    }
    
    @Test
    public void liianPitkaKayttajanimiEiKelpaaSignupissa() {
        goTo("http://localhost:"+ port);
        webDriver.findElement(By.linkText("Sign Up Now")).click();
        
        assertEquals("Petsite - signup", title());
                
        fill(find("#username")).with("LiianPitkäKäyttäjänimi");
        fill(find("#email")).with("e@mail.com");
        fill(find("#password")).with("password");
        submit(find("form").first());
        
        assertEquals("Petsite - signup", title());
        
        assertTrue(pageSource().contains("length must be between 1 and 20"));
        
        assertTrue(userRepository.findByUsername("LiianPitkäKäyttäjänimi") == null);
    }
    
    @Test
    public void tyhjaEmailEiKelpaaSignupissa() {
        goTo("http://localhost:"+ port);
        webDriver.findElement(By.linkText("Sign Up Now")).click();
        
        assertEquals("Petsite - signup", title());
        
        fill(find("#username")).with("emptyEmailUser");
        fill(find("#password")).with("password");
        submit(find("form").first());
        
        assertEquals("Petsite - signup", title());
        
        assertTrue(pageSource().contains("may not be empty"));
        
        assertTrue(userRepository.findByUsername("emptyEmailUser") == null);
    }
    
    @Test
    public void emailinOltavaValidiSignupissa() {
        goTo("http://localhost:"+ port);
        webDriver.findElement(By.linkText("Sign Up Now")).click();
        
        assertEquals("Petsite - signup", title());
        
        fill(find("#username")).with("invalidEmailUser");
        fill(find("#email")).with("höpölöpö");
        fill(find("#password")).with("password");
        submit(find("form").first());
        
        assertEquals("Petsite - signup", title());
        
        assertTrue(pageSource().contains("not a well-formed email address"));
        
        assertTrue(userRepository.findByUsername("invalidEmailUser") == null);
    }
    
    @Test
    public void eiKahtaSamaaKayttajanimeaSignupissa() {
        goTo("http://localhost:"+ port);
        webDriver.findElement(By.linkText("Sign Up Now")).click();
        
        assertEquals("Petsite - signup", title());
        
        fill(find("#username")).with("attemptedUser");
        fill(find("#email")).with("e@mail.com");
        fill(find("#password")).with("password");
        submit(find("form").first());
        
        webDriver.findElement(By.linkText("Sign Up Now")).click();
                
        fill(find("#username")).with("attemptedUser");
        fill(find("#email")).with("email@mail.com");
        fill(find("#password")).with("password");
        submit(find("form").first());
        
        assertEquals("Petsite - signup", title());
        
        assertTrue(pageSource().contains("An account already exists for this username."));
        
        assertTrue(userRepository.findByEmail("email@mail.com") == null);
    } 
    
    
    @Test
    public void eiKahtaSamaaEmailiaSignupissa() {
        goTo("http://localhost:"+ port);
        webDriver.findElement(By.linkText("Sign Up Now")).click();
        
        assertEquals("Petsite - signup", title());
        
        fill(find("#username")).with("sameEmailUser");
        fill(find("#email")).with("samamail@mail.com");
        fill(find("#password")).with("password");
        submit(find("form").first());
        
        webDriver.findElement(By.linkText("Sign Up Now")).click();
                
        fill(find("#username")).with("sameEmailUser2");
        fill(find("#email")).with("samamail@mail.com");
        fill(find("#password")).with("password");
        submit(find("form").first());
        
        assertEquals("Petsite - signup", title());
        
        assertTrue(pageSource().contains("An account already exists for this email."));
        
        assertTrue(userRepository.findByUsername("sameEmailUser2") == null);
    }
}
