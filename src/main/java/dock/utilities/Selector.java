package dock.utilities;

import javax.annotation.Nonnull;

import org.openqa.selenium.By;

public class Selector {

    /**
     * @see By.ById#findElements(org.openqa.selenium.SearchContext)
     */
    @Nonnull
    public static By contentDesc(@Nonnull String desc) {
        return By.xpath(".//*[@content-desc = '" + desc + "']");
    }

    @Nonnull
    public static By contentResourceID(@Nonnull String id) {
        return By.xpath(".//*[@resource-id = '" + id + "']");
    }
}
