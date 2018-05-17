/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public final class AppTest {

    @Test
    public final void test() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        final B a = new A();
        System.out.println(a.toString());
        Class clazz = a.getClass();
        B o = (A) clazz.getDeclaredConstructor().newInstance();
        System.out.println(o.toString());
    }
}