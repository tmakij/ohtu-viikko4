import ohtu.*
import ohtu.authentication.*
import org.openqa.selenium.*
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

description """A new user account can be created 
              if a proper unused username 
              and a proper password are given"""

scenario "creation succesfull with correct username and password", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver()
        driver.get("http://localhost:8090")
        register = driver.findElement(By.linkText("register new user"))  
        register.click()
    }
 
    when 'a valid username and password are entered', {
        name = driver.findElement(By.name("username"))
        name.sendKeys("testi23")
        password = driver.findElement(By.name("password"))
        password.sendKeys("testi3as")
        passwordConfirmation = driver.findElement(By.name("passwordConfirmation"))
        passwordConfirmation.sendKeys("testi3as")
        add = driver.findElement(By.name("add"))
        add.submit()
    }

    then 'new user is registered to system', {
        driver.getPageSource().contains("Welcome to Ohtu App!").shouldBe true
    }
}

scenario "can login with succesfully generated account", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver()
        driver.get("http://localhost:8090")
        register = driver.findElement(By.linkText("register new user"))
        register.click()
    }
 
    when 'a valid username and password are entered', {
        name = driver.findElement(By.name("username"))
        name.sendKeys("testi43")
        password = driver.findElement(By.name("password"))
        password.sendKeys("testi3as")
        passwordConfirmation = driver.findElement(By.name("passwordConfirmation"))
        passwordConfirmation.sendKeys("testi3as")
        add = driver.findElement(By.name("add"))
        add.submit()
        toMain = driver.findElement(By.linkText("continue to application mainpage"))
        toMain.click()
        logout = driver.findElement(By.linkText("logout"))
        logout.click()

        loginPage = driver.findElement(By.linkText("login"))
        loginPage.click()

        loginUserName = driver.findElement(By.name("username"))
        loginUserName.sendKeys("testi43")
        loginPassword = driver.findElement(By.name("password"))
        loginPassword.sendKeys("testi3as")
        login = driver.findElement(By.name("login"))
        login.submit()
    }

    then  'new credentials allow logging in to system', {
        driver.getPageSource().contains("Welcome to Ohtu Application!").shouldBe true
    }
}

scenario "creation fails with correct username and too short password", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver()
        driver.get("http://localhost:8090")
        register = driver.findElement(By.linkText("register new user"))
        register.click()
    }

    when 'a valid username and too short password are entered', {
        name = driver.findElement(By.name("username"))
        name.sendKeys("tesi234")
        password = driver.findElement(By.name("password"))
        password.sendKeys("23")
        passwordConfirmation = driver.findElement(By.name("passwordConfirmation"))
        passwordConfirmation.sendKeys("23")
        add = driver.findElement(By.name("add"))
        add.submit()
    }

    then 'new user is not be registered to system', {
        driver.getPageSource().contains("length greater or equal to 8").shouldBe true
    }
}

scenario "creation fails with correct username and pasword consisting of letters", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver()
        driver.get("http://localhost:8090")
        register = driver.findElement(By.linkText("register new user"))
        register.click()
    }

    when 'a valid username and password consisting of letters are entered', {
        name = driver.findElement(By.name("username"))
        name.sendKeys("tsti623")
        password = driver.findElement(By.name("password"))
        password.sendKeys("asdasdasd")
        passwordConfirmation = driver.findElement(By.name("passwordConfirmation"))
        passwordConfirmation.sendKeys("asdasdasd")
        add = driver.findElement(By.name("add"))
        add.submit()
    }

    then 'new user is not be registered to system', {
        driver.getPageSource().contains("must contain one character that is not a letter").shouldBe true
    }
}

scenario "creation fails with too short username and valid pasword", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver()
        driver.get("http://localhost:8090")
        register = driver.findElement(By.linkText("register new user"))
        register.click()
    }
    when 'a too sort username and valid password are entered', {
        name = driver.findElement(By.name("username"))
        name.sendKeys("a")
        password = driver.findElement(By.name("password"))
        password.sendKeys("asdasdasd123")
        passwordConfirmation = driver.findElement(By.name("passwordConfirmation"))
        passwordConfirmation.sendKeys("asdasdasd123")
        add = driver.findElement(By.name("add"))
        add.submit()
    }
    then 'new user is not be registered to system', {
        driver.getPageSource().contains("length 5-10").shouldBe true
    }
}

scenario "creation fails with already taken username and valid pasword", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver()
        driver.get("http://localhost:8090")
        register = driver.findElement(By.linkText("register new user"))
        register.click()
    }
    when 'a already taken username and valid password are entered', {
        name = driver.findElement(By.name("username"))
        name.sendKeys("pekka")
        password = driver.findElement(By.name("password"))
        password.sendKeys("asdasdasd123")
        passwordConfirmation = driver.findElement(By.name("passwordConfirmation"))
        passwordConfirmation.sendKeys("asdasdasd123")
        add = driver.findElement(By.name("add"))
        add.submit()
    }
    then 'new user is not be registered to system', {
        driver.getPageSource().contains("username or password invalid").shouldBe true
    }
}

scenario "can not login with account that is not succesfully created", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver()
        driver.get("http://localhost:8090")
        register = driver.findElement(By.linkText("register new user"))
        register.click()
    }
    when 'a invalid username/password are entered', {
        name = driver.findElement(By.name("username"))
        name.sendKeys("a")
        password = driver.findElement(By.name("password"))
        password.sendKeys("asdasdasd123")
        passwordConfirmation = driver.findElement(By.name("passwordConfirmation"))
        passwordConfirmation.sendKeys("asdasdasd123")
        add = driver.findElement(By.name("add"))
        add.submit()

        main = driver.findElement(By.linkText("back to home"))
        main.click()
        login = driver.findElement(By.linkText("login"));
        login.click();

        loginUserName = driver.findElement(By.name("username"));
        loginUserName.sendKeys("a");
        loginPassword = driver.findElement(By.name("password"));
        loginPassword.sendKeys("asdasdasd123");
        login = driver.findElement(By.name("login"));
        login.submit();
    }
    then  'new credentials do not allow logging in to system', {
        driver.getPageSource().contains("wrong username or password").shouldBe true
    }
}
