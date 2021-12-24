package dock.android;

import javax.annotation.Nonnull;

import org.openqa.selenium.By;

@SuppressWarnings("WeakerAccess")
public class Selector {

    /**
     * @see By.ById#findElements(org.openqa.selenium.SearchContext)
     */
    @Nonnull
    public static By contentDesc(@Nonnull String desc) {
        return By.xpath(".//*[@content-desc = '" + desc + "']");
    }

}
