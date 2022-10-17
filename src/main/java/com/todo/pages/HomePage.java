package com.todo.pages;

import com.todo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {

    private static final By title = By.tagName("h1");
    private static final By txt_newTodo = By.cssSelector("input.new-todo");
    private static final By list_todoList = By.cssSelector("ul.todo-list li");
    private static final By btn_complete = By.cssSelector("input.toggle");
    private static final By btn_delete = By.cssSelector("button.destroy");
    private static final By txt_itemsLeft = By.cssSelector("span.todo-count");
    private static final By btn_clearCompleted = By.cssSelector("button.clear-completed");


    public String getTitle() {

        return getText(title);
    }

    public HomePage addTodo(String toDoText) {

        setText(txt_newTodo, toDoText).sendKeys(Keys.ENTER);
        return this;
    }

    private List<WebElement> getAllTodos() {

        return driver.findElements(list_todoList);
    }

    public int getAllTodoCount() {

        try {
            return getAllTodos().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getCompletedTodoCount() {

        try {
            return driver.findElements(By.cssSelector("li.completed")).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getAllTodosText() {

        ArrayList<String> allTodosText = new ArrayList<>();

        for (WebElement ele : getAllTodos())
            allTodosText.add(getText(ele));

        return allTodosText;
    }

    private WebElement getTodoByPosition(int pos) {

        return getAllTodos().get(pos);
    }

    public String getTodoText(int todoPosition) {

        return getText(getTodoByPosition(todoPosition));
    }

    public String getTodoStatus(int todoPosition) {

        String classValue = getTodoByPosition(todoPosition).getAttribute("class");

        return (classValue.equals("completed")) ? classValue : "active";

    }

    public HomePage completeTodo(int todoPosition) {

        getTodoByPosition(todoPosition).findElement(btn_complete).click();
        return this;
    }

    public HomePage deleteTodo(int todoPosition) {

        new Actions(driver).moveToElement(getTodoByPosition(todoPosition)).perform();
        getTodoByPosition(todoPosition).findElement(btn_delete).click();
        return this;
    }

    public int getActiveTodosLeftCount() {

        return Integer.parseInt(getText(txt_itemsLeft).split(" ")[0]);
    }

    public HomePage editTodo(int todoPosition, String newTodoText) {

        WebElement editable = enableTodoEdit(todoPosition);
        clearText(editable);
        setText(editable, newTodoText).sendKeys(Keys.ENTER);
        return this;
    }

    private WebElement enableTodoEdit(int todoPosition) {

        WebElement edit = getTodoByPosition(todoPosition);
        new Actions(driver).doubleClick(waitForElementToBeVisible(edit)).perform();
        return edit.findElement(By.cssSelector("input.edit"));
    }

    public HomePage clickClearCompleted() {

        click(btn_clearCompleted);
        return this;
    }

    public HomePage clearAndTabAway(int pos) {

        WebElement edit = enableTodoEdit(pos);
        clearText(edit);
        setText(edit, Keys.TAB);
        return this;
    }


}
