package co.com.training.web.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.*;
import java.util.stream.Collectors;

public class TablePage extends BasePage<WebDriver> {


    @FindBy(id = "input1")
    private WebElement payeeInput;

    @FindBy(id = "input2")
    private WebElement dropDownByAccount;

    @FindBy(id = "input3")
    private WebElement dropDownByType;

    @FindBy(id = "input4")
    private  WebElement ExpenditurePayeesInput;

    @FindBy(css = "div.col-md-8.col-md-offset-2 table thead tr th")
    private List<WebElement> headersTable;

    @FindBy(css = "div.col-md-8.col-md-offset-2 table tbody tr")
    private WebElement rowTable;

    public TablePage(WebDriver driver) {
        super(driver);
    }

    private void selectByType(String typeName) {
        Select select = new Select(dropDownByType);
        select.selectByVisibleText(typeName);
    }
    private void selectByAccount(String accountName) {
        Select select = new Select(dropDownByAccount);
        select.selectByVisibleText(accountName);
    }

    public TablePage searchByAccount(String account) {
        if(isNotNullOrEmpty(account)){
            selectByAccount(account);
        }
        return this;
    }

    public TablePage searchByType(String type) {
        if(isNotNullOrEmpty(type)){
            selectByType(type);
        }
        return this;
    }

    public TablePage searchByPayeeName(String name) {
        if(isNotNullOrEmpty(name)){
            type(payeeInput, name);
        }
        return this;
    }

    public List<Map<String, String>> getSearchResults() {
        List<Map<String, String>> table = new ArrayList<>();
        List<String> headers = headersTable.stream().map(WebElement::getText).collect(Collectors.toList());
        int numberRows = rowTable.findElements(By.cssSelector("tr")).size();
        for(int row = 1; row <= numberRows; row++) {
            Iterator<String> iterator = headers.iterator();
            for (int column = 1 ; column <= headers.size(); column++) {
                Map<String, String> register = new HashMap<>();
                WebElement item = rowTable.findElement(By.cssSelector(String.format("tr:nth-child(%s) td:nth-child(%s)", row, column)));
                register.put(iterator.next(),item.getText());
                table.add(register);
            }
        }
        return table;
    }

    private boolean isNotNullOrEmpty(String str){
       return str != null && !str.trim().isEmpty();
    }
}
