package pom;

import caps.DesiredCaps;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static io.appium.java_client.touch.offset.PointOption.point;


/**
 * BasePage is an page object model containing all general methods that will be used throughout entire app automation flow
 */
public class CommonBasePage extends DesiredCaps {

    public CommonBasePage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    /**
     * Get text content from desired element
     *
     * @param e Mobile Element
     * @return text
     */
    public String getText(MobileElement e) {
        return e.getText();
    }

    /**
     * Get boolean value if desired element is displayed
     *
     * @param e Mobile Element
     * @return true if element is displayed, otherwise false
     */
    public boolean isElementDisplayed(MobileElement e) {
        try {
            return e.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Check is element not displayed
     *
     * @param e Mobile Element
     * @return true If element is not displayed, otherwise false
     */
    public boolean isElementNotDisplayed(MobileElement e) {
        try {
            return (!e.isDisplayed());
        } catch (Exception ex) {
            return true;
        }
    }

    /**
     * Check is element not enabled
     *
     * @param e Mobile Element
     * @return true If element is not enabled, otherwise false
     */
    public boolean isElementNotEnabled(MobileElement e) {
        try {
            return (!e.isEnabled());
        } catch (Exception ex) {
            return true;
        }
    }


    /**
     * Waits for desired element to be visible
     *
     * @param e                    Mobile Element
     * @param timeoutInSeconds     how many seconds to wait to element become visible
     * @param pollingEveryInMillis polling to check is element displayed each 'pollingEveryInMillis' miliseconds
     * @return true if element visible, otherwise false
     */
    public boolean waitForElementToBeVisible(final MobileElement e, final long timeoutInSeconds, final long pollingEveryInMillis) {
        return driverWaitFor(driver, ExpectedConditions.visibilityOf(e), timeoutInSeconds, pollingEveryInMillis);
    }

    /**
     * Get boolean value if desired element is null or blank
     *
     * @param s String
     * @return boolean
     */
    public boolean isNullOrBlank(String s) {
        return (s == null || s.trim().equals(""));
    }

    /**
     * Get boolean value if desired element is enabled
     *
     * @param e Mobile Element
     * @return boolean
     */
    public boolean isElementEnabled(MobileElement e) {
        try {
            return e.isEnabled();
        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * Waits for desired element to be enabled
     *
     * @param e                    Mobile Element
     * @param timeoutInSeconds     how many seconds to wait to element become enabled
     * @param pollingEveryInMillis polling to check is element enabled each 'pollingEveryInMillis' milliseconds
     * @return true if element enabled, otherwise false
     */
    public boolean waitForElementToBeEnabled(final MobileElement e, final long timeoutInSeconds, final long pollingEveryInMillis) {
        final WebDriverWait driverWait = new WebDriverWait(driver, timeoutInSeconds, pollingEveryInMillis);
        return driverWait.until((ExpectedCondition<Boolean>) d -> e.isEnabled());
    }

    /**
     * Get boolean value if desired element is selected
     *
     * @param e Mobile Element
     * @return boolean
     */
    public boolean isElementSelected(MobileElement e) {
        try {
            return e.isSelected();
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Click on desired element
     *
     * @param e Mobile Element
     * @return void
     */
    public void click(MobileElement e) {
        try {
            e.click();
        } catch (Exception ex) {
            throw new RuntimeException("Click failed", ex);
        }
    }

    /**
     * Waits for desired element to be clickable
     *
     * @param e                    Mobile Element
     * @param timeoutInSeconds     how many seconds to wait to element become clickable
     * @param pollingEveryInMillis polling to check is element clickable each 'pollingEveryInMillis' milliseconds
     * @return true if element clickable, otherwise false
     */
    public boolean waitForElementToBeClickable(final MobileElement e,
                                               final long timeoutInSeconds, final long pollingEveryInMillis) {
        return driverWaitFor(driver, ExpectedConditions.elementToBeClickable(e), timeoutInSeconds,
                pollingEveryInMillis);
    }

    /**
     * Wait and click on desired element
     *
     * @param e                    Mobile Element
     * @param timeoutInSeconds     how many seconds to wait to element become clickable
     * @param pollingEveryInMillis polling to check is element clickable each 'pollingEveryInMillis' milliseconds
     * @return true if element clickable, otherwise false
     */
    public boolean waitAndClick(final MobileElement e,
                                final long timeoutInSeconds, final long pollingEveryInMillis) {
        boolean isClickable = waitForElementToBeClickable(e, timeoutInSeconds, pollingEveryInMillis);
        if (isClickable) {
            e.click();
        }
        return isClickable;
    }

    /**
     * Explicit wait
     *
     * @param timeInMilliseconds Number of miliseconds
     * @return void
     */
    public void wait(int timeInMilliseconds) throws InterruptedException {
        Thread.sleep(timeInMilliseconds);
    }

    /**
     * Scroll down the screen as many times as needed
     *
     * @param numOfTimesToScrollDown Number of times to scroll down the screen
     * @param offsetFromScreenHeight Offset from screen height
     * @param endPointToMoveTo       Moves current touch to a Y coordinate
     * @return void
     */
    public void scrollDown(int numOfTimesToScrollDown, int offsetFromScreenHeight, int endPointToMoveTo) {
        Dimension size = driver.manage().window().getSize();
        int anchor = size.width / 2;
        // Swipe up to scroll down
        int startPoint = size.height - offsetFromScreenHeight;
        for (int i = 0; i < numOfTimesToScrollDown; i++) {
            new TouchAction(driver)
                    .longPress(point(anchor, startPoint))
                    .moveTo(point(anchor, endPointToMoveTo))
                    .release()
                    .perform();
        }
    }

    /**
     * Scroll down the screen as many times as needed to find element
     *
     * @param numOfTimesToScrollDown Number of times to scroll down the screen
     * @param offsetFromScreenHeight Offset from screen height
     * @param endPointToMoveTo       Moves current touch to a Y coordinate
     * @param findElement            Mobile Element which we want to find while perform scrolling
     * @return true if element is found during scroll, otherwise false
     */
    public boolean scrollDownTillElement(int numOfTimesToScrollDown, int offsetFromScreenHeight, int endPointToMoveTo, MobileElement findElement) {

        Dimension size = driver.manage().window().getSize();
        int anchor = size.width / 2;
        // Swipe up to scroll down
        int startPoint = size.height - offsetFromScreenHeight;

        boolean isElementFound = false;
        for (int i = 0; i < numOfTimesToScrollDown; i++) {
            new TouchAction(driver)
                    .longPress(point(anchor, startPoint))
                    .moveTo(point(anchor, endPointToMoveTo))
                    .release()
                    .perform();

            try {
                if (isElementEnabled(findElement)) {
                    isElementFound = true;
                    break;
                }
            } catch (NoSuchElementException e) {

            }
        }

        return isElementFound;

    }

    /**
     * Scroll down or up for calculated size as many times as needed to find element
     *
     * @param numOfTimesToScroll Number of times to scroll down the screen
     * @param isScrollDown       true - scroll down, false - scroll up
     * @param startElement
     * @param endElement
     * @param findElement        Mobile Element which we want to find while perform scrolling
     * @return true if element is found during scroll, otherwise false
     */
    public boolean scrollDownOrUpTillElement(int numOfTimesToScroll, boolean isScrollDown, MobileElement startElement, MobileElement endElement, MobileElement findElement) {

        int startElementSize = startElement.getSize().getHeight();
        int startElementTopYCoordinate = startElement.getLocation().getY();

        int endElementTopYCoordinate = endElement.getLocation().getY();

        Dimension size = driver.manage().window().getSize();
        int anchor = size.width / 2;

        int sizeOfChunkToScroll = (endElementTopYCoordinate - (startElementSize + startElementTopYCoordinate)) / 3;

        int startPoint = 0;
        int endPoint = 0;

        // Swipe up to scroll down
        if (isScrollDown) {
            startPoint = sizeOfChunkToScroll + startElementSize + startElementTopYCoordinate;
            endPoint = startElementSize + startElementTopYCoordinate;
        } else {
            startPoint = endElementTopYCoordinate - sizeOfChunkToScroll;
            endPoint = endElementTopYCoordinate;
        }

        boolean isElementFound = false;
        for (int i = 0; i < numOfTimesToScroll; i++) {
            new TouchAction(driver)
                    .longPress(point(anchor, startPoint))
                    .moveTo(point(anchor, endPoint))
                    .release()
                    .perform();

            try {
                if (isElementEnabled(findElement)) {
                    isElementFound = true;
                    break;
                }
            } catch (NoSuchElementException e) {

            }
        }

        return isElementFound;

    }

    /**
     * Scroll down and up for calculated size as many times as needed to find element
     *
     * @param numOfTimesToScroll Number of times to scroll down or up the screen
     * @param startElement
     * @param endElement
     * @param findElement        Mobile Element which we want to find while perform scrolling
     * @return true if element is found during scroll, otherwise false
     */
    public boolean scrollUpAndDownTillFindElement(int numOfTimesToScroll, MobileElement startElement, MobileElement endElement, MobileElement findElement) {

        boolean isElementVisible = false;

        try {
            waitForElementToBeVisible(findElement, 3, 500);
            return true;
        } catch (TimeoutException e) {
        }

        // scroll down to find 'findElement'
        try {
            isElementVisible = scrollDownOrUpTillElement(numOfTimesToScroll, true, startElement, endElement, findElement);
        } catch (NoSuchElementException e) {
        }

        //scroll up to find 'findElement'
        if (!isElementVisible) {
            isElementVisible = scrollDownOrUpTillElement(numOfTimesToScroll, false, startElement, endElement, findElement);
        }

        return isElementVisible;
    }


    /**
     * Scroll vertically the screen as many times as needed
     *
     * @param numOfTimesToScroll     Number of times to scroll the screen
     * @param startElement           Mobile element from where scrolling by Y coordinate will start
     * @param offsetFromScreenHeight Offset from screen height
     * @return void
     */
    public void scrollVerticallyFromElement(int numOfTimesToScroll, MobileElement startElement, int offsetFromScreenHeight) {

        Dimension size = driver.manage().window().getSize();
        int anchor = size.width / 2;

        Point p = startElement.getLocation();
        int startPoint = p.y;

        int endPointToMoveTo = size.height - offsetFromScreenHeight;

        for (int i = 0; i < numOfTimesToScroll; i++) {
            new TouchAction(driver).longPress(point(anchor, startPoint)).moveTo(point(anchor, endPointToMoveTo)).release().perform();
        }

    }

    /**
     * Scroll down vertically the screen as many times as needed from coordinate till element
     * Method contains: Allure step
     *
     * @param numOfTimesToScroll     Number of times to scroll down the screen
     * @param offsetFromScreenHeight Offset from screen height
     * @param endElement             Mobile element from where scrolling by Y coordinate will end
     * @return void
     */
    public void scrollVerticallyFromPointToElement(int numOfTimesToScroll, int offsetFromScreenHeight, MobileElement endElement) {
        Dimension size = driver.manage().window().getSize();
        int anchor = size.width / 2;

        Point p = endElement.getLocation();
        int endPoint = p.y;

        int startPoint = size.height - offsetFromScreenHeight;

        for (int i = 0; i < numOfTimesToScroll; i++) {
            new TouchAction(driver).longPress(point(anchor, startPoint)).moveTo(point(anchor, endPoint)).release().perform();
        }
    }


    /**
     * Scroll vertically the screen as many times as needed until find specific element
     *
     * @param numOfTimesToScroll     Number of times to scroll the screen
     * @param startElement           Mobile element from where scrolling by Y coordinate will start
     * @param offsetFromScreenHeight Offset from screen height
     * @param findElement            Element till scroll is done
     * @return true if element is found during scroll, otherwise false
     */
    public boolean scrollVerticallyTillElement(int numOfTimesToScroll, MobileElement startElement, int offsetFromScreenHeight, MobileElement findElement) {

        Dimension size = driver.manage().window().getSize();
        int anchor = size.width / 2;

        Point p = startElement.getLocation();
        int startPoint = p.y;

        int endPointToMoveTo = size.height - offsetFromScreenHeight;

        boolean isElementFound = false;
        for (int i = 0; i < numOfTimesToScroll; i++) {
            new TouchAction(driver).longPress(point(anchor, startPoint)).moveTo(point(anchor, endPointToMoveTo)).release().perform();
            try {
                if (isElementEnabled(findElement)) {
                    isElementFound = true;
                    break;
                }
            } catch (NoSuchElementException e) {

            }
        }

        return isElementFound;
    }

    /**
     * Scroll vertically the screen as many times as needed until find specific element
     *
     * @param numOfTimesToScroll     Number of times to scroll the screen
     * @param endElement             Mobile element from where scrolling by Y coordinate will end
     * @param offsetFromScreenHeight Offset from screen height
     * @param findElement            Element till scroll is done
     * @return true if element is found during scroll, otherwise false
     */
    public boolean scrollVerticallyFromPointToElementTillFindElement(int numOfTimesToScroll, int offsetFromScreenHeight, MobileElement endElement, MobileElement findElement) {

        Dimension size = driver.manage().window().getSize();
        int anchor = size.width / 2;

        Point p = endElement.getLocation();
        int endPoint = p.y;

        int startPoint = size.height - offsetFromScreenHeight;

        boolean isElementFound = false;
        for (int i = 0; i < numOfTimesToScroll; i++) {
            new TouchAction(driver).longPress(point(anchor, startPoint)).moveTo(point(anchor, endPoint)).release().perform();
            try {
                if (isElementEnabled(findElement)) {
                    isElementFound = true;
                    break;
                }
            } catch (NoSuchElementException e) {

            }
        }

        return isElementFound;
    }


    /**
     * Scroll vertically from one element to another using longPress
     *
     * @param startElement Mobile Element from where scrolling by Y coordinate will start
     * @param endElement   Mobile Element to where scrolling by Y coordinate will end
     */
    public void scrollVerticallyFromElementToElement(MobileElement startElement, MobileElement endElement) {

        Dimension size = driver.manage().window().getSize();
        int anchor = size.width / 2;

        Point start = startElement.getLocation();
        Point end = endElement.getLocation();
        int startPoint = start.y;
        int endPoint = end.y;

        if (driver instanceof AndroidDriver) {
            new TouchAction(driver).longPress(point(anchor, startPoint)).moveTo(point(anchor, endPoint)).release().perform();
        } else {
            new TouchAction(driver).press(point(anchor, startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(point(anchor, endPoint)).perform();
        }
    }

    /**
     * Scroll vertically from one element to another using press and WaitAction
     *
     * @param startElement Mobile Element from where scrolling by Y coordinate will start
     * @param endElement   Mobile Element to where scrolling by Y coordinate will end
     */
    public void scrollVerticallyPressAndWaitFromElementToElement(MobileElement startElement, MobileElement endElement) {

        Dimension size = driver.manage().window().getSize();
        int anchor = size.width / 2;

        Point start = startElement.getLocation();
        Point end = endElement.getLocation();
        int startPoint = start.y;
        int endPoint = end.y;

        new TouchAction(driver).press(point(anchor, startPoint)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(point(anchor, endPoint)).release().perform();
    }

    /**
     * Scroll into view by uiautomatorText to find mobile element
     * Method is only for ANDROID!!!
     * <p>
     * uiautomatorText can be: new UiScrollable(new UiSelector().resourceId(\"com.talpa.kijk.dev:id/clExpandingView\"))
     * .scrollIntoView(resourceId(\"com.talpa.kijk.dev:id/ivHomeNextEpisode\"))
     *
     * @param uiautomatorText
     * @return true if mobile element is found, otherwise return false
     */
    public boolean scrollIntoViewANDROID(String uiautomatorText) {
        MobileElement e = driver.findElement(MobileBy.AndroidUIAutomator(uiautomatorText));
        return (e != null);
    }

    /**
     * Tap on the screen by X and Y coordinates
     *
     * @param x X coordinate
     * @param y X coordinate
     */
    public void tap(int x, int y) {
        new TouchAction(driver).tap(point(x, y)).perform();
    }

    /**
     * Get attribute value of mobile element
     * Usage: getAttributeOfElement(listView, "scrollable");
     *
     * @param element       Mobile Element
     * @param attributeName Attribute name
     * @return string
     */
    public String getAttributeOfElement(MobileElement element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    /**
     * Get boolean value of element's attribute
     *
     * @param e             Mobile Element
     * @param attributeName String
     * @return true if attribute string value is "true", otherwise false
     */
    public boolean getAttributeValue(MobileElement e, String attributeName) {
        String value = getAttributeOfElement(e, attributeName);

        return (value.equalsIgnoreCase("true"));
    }

    /**
     * Waits for text to be present in element
     *
     * @param e                    Mobile Element
     * @param text                 Desired text
     * @param timeoutInSeconds     how many seconds to wait to element become visible
     * @param pollingEveryInMillis polling to check is element displayed each 'pollingEveryInMillis' milliseconds
     * @return true if desired text is found, otherwise false
     */
    public boolean waitForTextToBeVisible(final MobileElement element,
                                          final String text, final long timeoutInSeconds, final long pollingEveryInMillis) {
        return driverWaitFor(driver, ExpectedConditions.textToBePresentInElement(element, text), timeoutInSeconds,
                pollingEveryInMillis);
    }

    /**
     * Wait and send keys on desired element
     *
     * @param e                    Mobile Element
     * @param keysToSend           keys to send
     * @param timeoutInSeconds     how many seconds to wait to element become clickable
     * @param pollingEveryInMillis polling to check is element clickable each 'pollingEveryInMillis' milliseconds
     * @return true if element clickable, otherwise false
     */
    public boolean waitAndSendKeys(final MobileElement element,
                                   final String keysToSend, final long timeoutInSeconds, final long pollingEveryInMillis) {
        boolean isClickable = waitForElementToBeClickable(element, timeoutInSeconds, pollingEveryInMillis);

        if (isClickable) {
            element.sendKeys(keysToSend);
        }
        return isClickable;
    }

    /**
     * Send keys on desired element
     *
     * @param e          Mobile Element
     * @param keysToSend keys to send
     * @return true if element clickable, otherwise false
     */
    public void sendKeys(final MobileElement e, final String keysToSend) {
        e.sendKeys(keysToSend);
    }


    /**
     * Wait, clear and send keys on desired element
     *
     * @param e                    Mobile Element
     * @param keysToSend           keys to send
     * @param timeoutInSeconds     how many seconds to wait to element become clickable
     * @param pollingEveryInMillis polling to check is element clickable each 'pollingEveryInMillis' milliseconds
     * @return true if element clickable, otherwise false
     */
    public boolean waitClearKeysAndSendKeys(final MobileElement e,
                                            final String keysToSend, final long timeoutInSeconds, final long pollingEveryInMillis) {
        boolean isClickable = waitForElementToBeClickable(e, timeoutInSeconds, pollingEveryInMillis);
        if (!isClickable) {
            return false;
        }
        clickAndClear(e);
        isClickable = waitForElementToBeClickable(e, timeoutInSeconds, pollingEveryInMillis);
        if (isClickable) {
            e.sendKeys(keysToSend);
        }
        return isClickable;
    }


    /**
     * Clear and send keys on desired element
     * Method contains: Allure step
     *
     * @param e          Mobile Element
     * @param keysToSend keys to send
     * @return void
     */
    public void clearKeysAndSendKeys(final MobileElement e, final String keysToSend) {
        clickAndClear(e);
        sendKeys(e, keysToSend);
    }

    /**
     * Checks if element (Text Field preferred) has text and clears it until its empty.
     * Implemented because classic 'clear()' method on iOS doesn't clear the whole text field.
     *
     * @param element Mobile Element
     */
    public void clickAndClear(MobileElement element) {
        // 'i' counts how many times app cleared text field, just as precautionary not to get into the endless loop. Five times should be more than enough to clear email address.
        int i = 0;
        while (!element.getText().isEmpty() && i < 5) {
            click(element);
            element.clear();
            i++;
        }
    }

    /**
     * Waits for desired element not to be enabled
     *
     * @param e                    Mobile Element
     * @param timeoutInSeconds     how many seconds to wait to element become enabled
     * @param pollingEveryInMillis polling to check is element enabled each 'pollingEveryInMillis' milliseconds
     * @return true if element enabled, otherwise false
     */
    public boolean waitForElementNotToBeEnabled(
            final MobileElement element, final long timeoutInSeconds, final long pollingEveryInMillis) {
        return driverWaitFor(driver, ExpectedConditions.stalenessOf(element), timeoutInSeconds, pollingEveryInMillis);
    }

    private boolean waitForExpectedCondition(AppiumDriver<MobileElement> driver,
                                             final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds,
                                             final long pollingEveryInMillis) {
        final WebDriverWait driverWait = new WebDriverWait(driver, timeoutInSeconds, pollingEveryInMillis);

        if (driverWait.until(expectedCondition) instanceof List<?>) {
            List<MobileElement> mobileElements = (List<MobileElement>) driverWait.until(expectedCondition);
            return !mobileElements.isEmpty();
        } else if (driverWait.until(expectedCondition) instanceof MobileElement) {
            MobileElement mobileElement = (MobileElement) driverWait.until(expectedCondition);
            return (mobileElement != null);
        }
        return (Boolean) driverWait.until(expectedCondition);
    }

    private MobileElement waitElementForExpectedCondition(AppiumDriver<MobileElement> driver,
                                                          final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds,
                                                          final long pollingEveryInMillis) {
        final WebDriverWait driverWait = new WebDriverWait(driver, timeoutInSeconds, pollingEveryInMillis);

        return (MobileElement) driverWait.until(expectedCondition);
    }

    private List<MobileElement> waitElementsForExpectedCondition(AppiumDriver<MobileElement> driver,
                                                                 final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds,
                                                                 final long pollingEveryInMillis) {
        final WebDriverWait driverWait = new WebDriverWait(driver, timeoutInSeconds, pollingEveryInMillis);

        return (List<MobileElement>) driverWait.until(expectedCondition);
    }

    private boolean driverWaitFor(AppiumDriver<MobileElement> driver,
                                  final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds,
                                  final long pollingEveryInMillis) {

        return waitForExpectedCondition(driver, expectedCondition, timeoutInSeconds, pollingEveryInMillis);

    }

    private MobileElement driverWaitForElement(AppiumDriver<MobileElement> driver,
                                               final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds,
                                               final long pollingEveryInMillis) {

        return waitElementForExpectedCondition(driver, expectedCondition, timeoutInSeconds, pollingEveryInMillis);
    }

    private List<MobileElement> driverWaitForElements(AppiumDriver<MobileElement> driver,
                                                      final ExpectedCondition<?> expectedCondition, final long timeoutInSeconds,
                                                      final long pollingEveryInMillis) {

        return waitElementsForExpectedCondition(driver, expectedCondition, timeoutInSeconds, pollingEveryInMillis);
    }

    /**
     * The number of mobile elements
     *
     * @param elements List of mobile elements
     * @return the number of mobile elements
     */
    public int numberOfMobileElements(List<MobileElement> elements) {
        if (elements.isEmpty())
            return 0;
        return elements.size();
    }

}
