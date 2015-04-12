package ohtu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tester {

    public static void main(String[] args) {
        aloitusKayttajanLuonti();
        vaaraKirjautuminenSalasana();
        vaaraKirjautuminenEiTunnusta();
        uusiKayttajaJaUlosKirjaus();
    }

    private static void aloitusKayttajanLuonti() {
        WebDriver driver = new HtmlUnitDriver();

        driver.get("http://localhost:8090");
        System.out.println(driver.getPageSource());
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        System.out.println("==");

        System.out.println(driver.getPageSource());
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkep");
        element = driver.findElement(By.name("login"));
        element.submit();

        System.out.println("==");
        System.out.println(driver.getPageSource());
        System.out.println("==");
    }

    private static void vaaraKirjautuminenSalasana() {
        WebDriver driver = new HtmlUnitDriver();

        driver.get("http://localhost:8090");
        System.out.println(driver.getPageSource());
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        System.out.println("==");

        System.out.println(driver.getPageSource());
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("asdasdasdas");
        element = driver.findElement(By.name("login"));
        element.submit();

        System.out.println("==");
        System.out.println(driver.getPageSource());
        System.out.println("==");
    }

    private static void vaaraKirjautuminenEiTunnusta() {
        WebDriver driver = new HtmlUnitDriver();

        driver.get("http://localhost:8090");
        System.out.println(driver.getPageSource());
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        System.out.println("==");

        System.out.println(driver.getPageSource());
        element = driver.findElement(By.name("username"));
        element.sendKeys("dddd");
        element = driver.findElement(By.name("password"));
        element.sendKeys("asdasdasdas");
        element = driver.findElement(By.name("login"));
        element.submit();

        System.out.println("==");
        System.out.println(driver.getPageSource());
        System.out.println("==");
    }

    private static void uusiKayttajaJaUlosKirjaus() {
        WebDriver driver = new HtmlUnitDriver();

        driver.get("http://localhost:8090");
        System.out.println(driver.getPageSource());
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();

        System.out.println("==");

        System.out.println(driver.getPageSource());
        element = driver.findElement(By.name("username"));
        element.sendKeys("testiHeeb2");
        element = driver.findElement(By.name("password"));
        element.sendKeys("testaile1");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("testaile1");
        element = driver.findElement(By.name("add"));
        element.submit();

        System.out.println("==");
        System.out.println(driver.getPageSource());
        WebElement toMain = driver.findElement(By.linkText("continue to application mainpage"));
        toMain.click();

        System.out.println("==");

        System.out.println(driver.getPageSource());
        WebElement logout = driver.findElement(By.linkText("logout"));
        logout.click();
        System.out.println("==");

        System.out.println(driver.getPageSource());
        System.out.println("==");
    }
}
