package com.reportportal.ta.pages.components;

import static java.util.stream.Collectors.toList;

import com.reportportal.ta.annotations.PageObject;
import com.reportportal.ta.webdriver.WebDriverHelper;
import com.reportportal.ta.webdriver.WebDriverWrapper;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@PageObject
public class DashboardTable {

    @Autowired
    @Lazy
    private WebDriverWrapper webDriverWrapper;
    @Autowired
    @Lazy
    private WebDriverHelper driverHelper;

    private final By tableLocator = By.xpath("//div[@class='grid__grid--W4yQA dashboardTable__dashboard-table--oK3Zi']");
    private final By headerLocator = By.cssSelector(".gridHeader__grid-header--KArbb .headerCell__header-cell--DCFQq");
    private final By rowsLocator = By.cssSelector(".gridRow__grid-row-wrapper--xj8DG");


    public List<String> getValuesHeaders() {
        return webDriverWrapper.findElements(headerLocator).stream().map(WebElement::getText).collect(toList());
    }

    public List<WebElement> getRows() {
        return webDriverWrapper.findElements(rowsLocator);
    }

    public int getColumnIndexByName(String headerName) {
        List<String> headers = getValuesHeaders();

        return IntStream.range(0, headers.size())
                        .filter(i -> headers.get(i).equalsIgnoreCase(headerName))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Column with header '" + headerName + "' not found"));
    }

    public List<String> getColumnValuesByIndex(int columnIndex) {
        return getRows()
            .stream()
            .map(row -> row.findElements(By.cssSelector(".gridCell__grid-cell--EIqeC")).get(columnIndex).getText())
            .collect(Collectors.toList());
    }

    public List<String> getColumnValuesByHeaderName(String headerName) {
        return getColumnValuesByIndex(getColumnIndexByName(headerName));
    }
}
