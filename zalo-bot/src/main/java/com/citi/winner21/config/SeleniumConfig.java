package com.citi.winner21.config;


import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SeleniumConfig {

    private static final Logger logger = Logger.getLogger(SeleniumConfig.class.getName());
    private List<String> browserOptions = new ArrayList<>();


    @PostConstruct
    public void init() {
        browserOptions.addAll(Arrays.asList(
                "--window-size=1920,1080",
                "--kiosk",
                "--no-sandbox", // Bypass OS security model, MUST BE THE VERY FIRST OPTION
                "--headless",
                "--disable-infobars", // disabling info bars
                "--disable-extensions", // disabling extensions
                "--disable-dev-shm-usage", // overcome limited resource problems);
                "--profile-directory=C:/Users/alex.huynh/AppData/Local/Mozilla/Firefox/Profiles/7bcdnv40.fbBot"
        ));
        WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
        WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "false");
    }

  

    public WebDriver getFireFoxWebDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(browserOptions);
        options.setLogLevel(FirefoxDriverLogLevel.ERROR);
        options.addPreference("app.update.auto", false);
        options.addPreference("app.update.enabled", false);
        options.addPreference("general.useragent.override", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
        return new FirefoxDriver(options);
    }

    public WebDriver getChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:/Users/alex.huynh/AppData/Local/Google/Chrome/User Data/Profile 23/");
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }


}
