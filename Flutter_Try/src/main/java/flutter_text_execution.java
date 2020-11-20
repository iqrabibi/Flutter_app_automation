



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.io.File;

import org.openqa.selenium.OutputType;
import io.appium.java_client.MobileElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pro.truongsinh.appium_flutter.FlutterFinder;
public class flutter_text_execution extends fltter_test{
    protected FlutterFinder find;
    @BeforeTest
    public void setUp() throws Exception {
        super.setUp();
        find = new FlutterFinder(driver);
    }
    @Test
    public void basicTest () throws InterruptedException {
        MobileElement counterTextFinder = find.byValueKey("counter");
        MobileElement buttonFinder = find.byValueKey("increment");

        validateElementPosition(buttonFinder);

        Assert.assertEquals(driver.executeScript("flutter:checkHealth"), "ok");
        driver.executeScript("flutter:clearTimeline");
        driver.executeScript("flutter:forceGC");

        Map renderObjectDiagnostics = (Map) driver.executeScript(
                "flutter:getRenderObjectDiagnostics",
                counterTextFinder.getId(),
                new HashMap<String, Object>() {{
                    put("includeProperties", true);
                    put("subtreeDepth", 2);
                }}
        );

        Assert.assertEquals(renderObjectDiagnostics.get("type"), "DiagnosticableTreeNode");
        Assert.assertEquals(((List)renderObjectDiagnostics.get("children")).size(), 1);

        Object semanticsId = driver.executeScript(
                "flutter:getSemanticsId",
                counterTextFinder
        );
        Assert.assertEquals(semanticsId, 4L);

        String treeString = (String) driver.executeScript("flutter: getRenderTree");
        Assert.assertEquals(treeString.substring(0, 11), "RenderView#");


        driver.context("NATIVE_APP");
        File f1 = driver.getScreenshotAs(OutputType.FILE);
        f1.renameTo(new File("./native-screenshot.png"));
        driver.context("FLUTTER");
        File f2 = driver.getScreenshotAs(OutputType.FILE);
        f2.renameTo(new File("./flutter-screenshot.png"));

        Assert.assertEquals(counterTextFinder.getText(), "0");

        buttonFinder.click();
        // @todo tap not working?
        // buttonFinder.tap(1, 100);
        buttonFinder.click();
        Assert.assertEquals(counterTextFinder.getText(), "2");

        find.byTooltip("Increment").click();

        Assert.assertEquals(find.descendant(find.byTooltip("counter_tooltip"), find.byValueKey("counter")).getText(), "3");

        find.byType("FlatButton").click();
        driver.executeScript("flutter:waitForAbsent", buttonFinder);

        Assert.assertEquals(find.text("This is 2nd route").getText(), "This is 2nd route");

        driver.executeScript("flutter:scrollUntilVisible", find.byType("ListView"), new HashMap<String, Object>() {{
            put("item", find.byType("TextField"));
            put("dxScroll", 90);
            put("dyScroll", -400);
        }});

        driver.executeScript("flutter:scroll", find.byType("ListView"), new HashMap<String, Object>() {{
            put("item", find.byType("TextField"));
            put("dx", 50);
            put("dy", 100);
            put("durationMilliseconds", 200);
            put("frequency", 30);
        }});

        driver.executeScript("flutter:scrollIntoView", find.byType("ListView"), new HashMap<String, Object>() {{
            put("alignment", 0.1);
        }});

        find.byType("TextField").sendKeys("I can enter text"); // enter text
        driver.executeScript("flutter:waitFor", find.text("I can enter text")); // verify text appears on UI

        find.pageBack().click();
        driver.executeScript("flutter:waitFor", buttonFinder);

        find.descendant(
                find.ancestor(
                        find.bySemanticsLabel(Pattern.compile("counter_semantic")),
                        find.byType("Tooltip")
                ),
                find.byType("Text")
        )
                .click()
        ;

        driver.quit();
    }

    private void validateElementPosition(MobileElement buttonFinder) {
        Map bottomLeft = (Map) driver.executeScript("flutter:getBottomLeft", buttonFinder);
        Assert.assertEquals(bottomLeft.get("dx") instanceof Long, true);
        Assert.assertEquals(bottomLeft.get("dy") instanceof Long, true);

        Map bottomRight = (Map) driver.executeScript("flutter:getBottomRight", buttonFinder);
        Assert.assertEquals(bottomRight.get("dx") instanceof Long, true);
        Assert.assertEquals(bottomRight.get("dy") instanceof Long, true);

        Map center = (Map) driver.executeScript("flutter:getCenter", buttonFinder);
        Assert.assertEquals(center.get("dx") instanceof Long, true);
        Assert.assertEquals(center.get("dy") instanceof Long, true);

        Map topLeft = (Map) driver.executeScript("flutter:getTopLeft", buttonFinder);
        Assert.assertEquals(topLeft.get("dx") instanceof Long, true);
        Assert.assertEquals(topLeft.get("dy") instanceof Long, true);

        Map topRight = (Map) driver.executeScript("flutter:getTopRight", buttonFinder);
        Assert.assertEquals(topRight.get("dx") instanceof Long, true);
        Assert.assertEquals(topRight.get("dy") instanceof Long, true);
    }
}
