package com.todo.tests;

import com.todo.base.BasePage;
import com.todo.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class TodoTests extends BasePage {

    HomePage homePage;
    SoftAssert softAssert;

    String todo = "ride a llama";

    @Parameters({"browser", "mode"})
    @BeforeClass
    public void setUp(@Optional("chrome") String browser, @Optional("start-maximized") String mode) {

        homePage = launchApp(browser, mode);
        softAssert = new SoftAssert();
    }


    @Test(priority = 1)
    public void verifyHomePageTitle() {

        Assert.assertEquals(homePage.getTitle(), "todos");
    }
    @Test(priority = 2)
    public void verifyAddingTodo() {

        Assert.assertEquals(homePage.addTodo(todo)
                .getAllTodosText().get(0),
                todo);
    }

    @Test(priority = 4, dependsOnMethods = "verifyAddingTodo")
    public void verifyEditingTodo() {

        int todoPosition = 0;
        String newText = "updated text";

        Assert.assertEquals(homePage.editTodo(todoPosition, newText)
                .getTodoText(todoPosition),
                newText);
    }

    @Test(priority = 5, dependsOnMethods = "verifyAddingTodo")
    public void verifyCompletingTodo() {

        int activeBefore, activeAfter;

        activeBefore = homePage.getActiveTodosLeftCount();
        activeAfter = homePage.completeTodo(0).getActiveTodosLeftCount();

        softAssert.assertEquals(homePage.getTodoStatus(0), "completed");
        softAssert.assertEquals(activeBefore - activeAfter, 1);
        softAssert.assertAll();
    }

    @Test(priority = 6, dependsOnMethods = "verifyAddingTodo")
    public void verifyDeletingTodo() {

        int countBefore, countAfter;

        countBefore = homePage.getAllTodoCount();
        countAfter = homePage.deleteTodo(0).getAllTodoCount();

        Assert.assertEquals(countBefore - countAfter, 1);
    }


    @AfterClass
    public void tearDownAll() {

        quit();
    }
}
