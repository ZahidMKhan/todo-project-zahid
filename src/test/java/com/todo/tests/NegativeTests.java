package com.todo.tests;

import com.todo.base.BasePage;
import com.todo.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class NegativeTests extends BasePage {

    HomePage homePage;
    SoftAssert softAssert;

    String todo = "ride a llama";
    String badTodoText = "    ";

    @BeforeClass
    @Parameters({"browser", "mode"})
    public void setUp(@Optional("chrome") String browser, @Optional("start-maximized") String mode) {

        homePage = launchApp(browser, mode);
        softAssert = new SoftAssert();
    }


    @Test(description = "creating a todo with no text should not create a todo")
    public void verifyTodoCreationFailsWithBadData() {

        int beforeTodoCount = homePage.getAllTodoCount();

        Assert.assertEquals(homePage.addTodo(badTodoText)
                .getAllTodoCount(),
                beforeTodoCount);
    }

    @Test(description = "editing a todo to blank should fail and keep the overall count unchanged")
    public void verifyEditingTodoToBadTextFails() {

        int beforeTodoCount = homePage.addTodo(todo).getAllTodoCount();

        Assert.assertEquals(homePage.editTodo(1, badTodoText)
                .getAllTodoCount(),
                beforeTodoCount,
                "error : total todo count was found different after emptying an existing todo");
    }

    @Test(description = "clear completed button should not remove active todos")
    public void verifyClearingCompletedTodos() {

        int activeTodoCount = homePage.addTodo("get groceries")
                .addTodo("take out trash")
                .addTodo("brush your teeth")
                .completeTodo(1)
                .getActiveTodosLeftCount();

        Assert.assertEquals(homePage.clickClearCompleted()
                .getActiveTodosLeftCount(),
                activeTodoCount,
                "error : clicking clear completed button deleted active todos as well");
    }


    @AfterClass
    public void tearDownAll() {

        quit();
    }


}
